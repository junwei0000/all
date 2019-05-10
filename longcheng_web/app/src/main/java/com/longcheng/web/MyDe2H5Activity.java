package com.longcheng.web;

import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import butterknife.BindView;

/**
 *
 */

public class MyDe2H5Activity extends WebAct {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
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
    }


    @Override
    public void initDataAfter() {
        super.initDataAfter();
        loadUrl("http://t.admin.asdyf.com/ESale/Manager/index");

    }


    private void back() {
        if (mBridgeWebView != null && mBridgeWebView.canGoBack()) {
            mBridgeWebView.goBack();
        } else {
            backs();
        }
    }

    /**
     * 退出监听
     */
    public void onBackPressed() {
        backs();
    }
}
