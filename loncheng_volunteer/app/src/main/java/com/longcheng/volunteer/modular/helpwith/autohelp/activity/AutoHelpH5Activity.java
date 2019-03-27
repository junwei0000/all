package com.longcheng.volunteer.modular.helpwith.autohelp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.volunteer.modular.webView.WebAct;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.widget.jswebview.browse.CallBackFunction;

import butterknife.BindView;

/**
 *
 */

public class AutoHelpH5Activity extends WebAct {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;


    private String kn_url;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
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
        pageTopTvName.setText("智能互祝");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        super.setListener();
        pagetopIvRigth.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        pagetopIvRigth.setBackgroundResource(R.mipmap.wisheachdetails_share);
    }


    @Override
    public void initDataAfter() {
        super.initDataAfter();
        kn_url = getIntent().getStringExtra("html_url");
        if (TextUtils.isEmpty(kn_url)) {
            kn_url = HelpWithFragmentNew.automationHelpUrl;
        }
        loadUrl(kn_url);

    }

    private void autohelpRefresh() {
        //智能互祝----刷新页面数据
        mBridgeWebView.callHandler("autohelp_refresh", "", new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
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
        filter.addAction(ConstantManager.BroadcastReceiver_KNP_ACTION);
        registerReceiver(mReceiver, filter);
    }

    public static final int knpPaySuccessBack = 11;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 100);
            if (errCode == knpPaySuccessBack) {
                autohelpRefresh();
            }
        }
    };

    private void back() {
        if (mBridgeWebView != null && mBridgeWebView.canGoBack()) {
            mBridgeWebView.goBack();
        } else {
            doFinish();
        }
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
