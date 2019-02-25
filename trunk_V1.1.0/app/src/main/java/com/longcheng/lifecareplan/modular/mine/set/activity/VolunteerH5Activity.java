package com.longcheng.lifecareplan.modular.mine.set.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;

import butterknife.BindView;

/**
 * 志愿者/坐堂医
 */
public class VolunteerH5Activity extends WebAct {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        super.setListener();
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        super.initDataAfter();
        String about_me_url = getIntent().getStringExtra("html_url");
        loadUrl(about_me_url);
        firstComIn = false;
    }

    boolean firstComIn = true;

    @Override
    protected void onResume() {
        super.onResume();
        //坐堂医 回来刷新
        if (!firstComIn) {
            Log.e("callHandler", "doctor_taskReload");
            mBridgeWebView.callHandler("doctor_taskReload", "", new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        }

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
