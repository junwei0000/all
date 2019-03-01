package com.longcheng.lifecareplan.modular.helpwith.myGratitude.activity;

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

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailHelpDialogUtils;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;

import java.util.List;

import butterknife.BindView;

/**
 *
 */

public class MyGraH5Activity extends WebAct {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;


    private String kn_url;

    //康农祝福

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
        pageTopTvName.setText("我的恩人");
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
            kn_url = "http://dev.t.asdyf.com/home/help/my_gratitude";
        }
        loadUrl(kn_url);

    }

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
