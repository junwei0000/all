package com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.webView.WebAct;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;

import butterknife.BindView;

/**
 * 健康速递列表
 * Created by Burning on 2018/9/13.
 */

public class HealthyDeliveryDetailAct extends WebAct {
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
    private ShareUtils mShareUtils;
    private String url;
    private String cont, title;
    private String img;

    @Override
    public void initView(View view) {
        super.initView(view);
        pageTopTvName.setText("健康速递");
        pagetopIvRigth.setBackgroundResource(R.mipmap.wisheachdetails_share);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.pagetop_layout_rigth:
                if (mShareUtils == null) {
                    mShareUtils = new ShareUtils(mActivity);
                }
                mShareUtils.setShare(cont, img, url, title);
                break;
        }
    }

    @Override
    public void initDataAfter() {
        super.initDataAfter();

        url = getIntent().getStringExtra("detail_url");
        if (!url.contains("/is_app/1")) {
            url = url + "/is_app/1";
        }
        cont = getIntent().getStringExtra("cont");
        title = getIntent().getStringExtra("title");
        img = getIntent().getStringExtra("img");
        loadUrl(url);
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
