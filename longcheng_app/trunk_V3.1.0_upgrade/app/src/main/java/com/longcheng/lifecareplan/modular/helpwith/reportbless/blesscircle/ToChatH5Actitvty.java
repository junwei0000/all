package com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.bean.MessageEvent;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.activity.ReportMenuActivity;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ToChatH5Actitvty extends WebAct {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    String group_id;
    String title;
    int type;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                Back();
                break;
            case R.id.pagetop_layout_rigth:
                if (type == 2) {
                    Intent intent = new Intent(mActivity, GroupListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("title", title);
                    intent.putExtra("group_id", group_id);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        pagetopLayoutLeft.setOnClickListener(this);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        super.initDataAfter();
        String url = getIntent().getStringExtra("html_url");
        title = getIntent().getStringExtra("title");
        group_id = getIntent().getStringExtra("group_id");
        type = getIntent().getIntExtra("type", 1);
        if (type == 2) {
            pagetopLayoutRigth.setOnClickListener(this);
            pagetopIvRigth.setBackgroundResource(R.mipmap.chat_more);
        }
        pageTopTvName.setText(title);
        loadUrl(url);
    }

    private void Back() {
        Intent intent = new Intent();
        setResult(ConstantManager.REFRESHDATA, intent);
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Back();
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantManager.BroadcastReceiver_CHAT);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 微信支付回调广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra("type", 1);
            if (type == 1) {
                String chatmsg = intent.getStringExtra("chatmsg");
                Log.e("BroadcastReceiver", "chatmsg==" + chatmsg);
                sendChatMessage(chatmsg);
            }
        }
    };
}


