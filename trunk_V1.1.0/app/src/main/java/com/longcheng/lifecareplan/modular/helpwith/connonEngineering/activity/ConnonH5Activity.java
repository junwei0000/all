package com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
 * 康农
 */

public class ConnonH5Activity extends WebAct {

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
    private String kn_url;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.pagetop_layout_rigth:
                //分享
                if (mShareUtils == null) {
                    mShareUtils = new ShareUtils(mActivity);
                }
                if (!TextUtils.isEmpty(kn_url)) {
                    String title = "开启生命与财富能量！" + qiming_name + "邀请您";
                    String text = "这是一堂觉醒的课程，也是一项对生命、健康和财富的全方位公益教育。《财富启明》——开启你生命智慧的明灯，照亮你璀璨富足的人生。";

                    String showurl = kn_url;
                    if (kn_url.contains("is_app/1")) {
                        showurl = kn_url.substring(0, kn_url.length() - 1) + "0";
                    }
                    mShareUtils.setShare(text, "", showurl, title);
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
        pageTopTvName.setText("康农工程");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        super.setListener();
//        pagetopLayoutRigth.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopIvRigth.setVisibility(View.GONE);
        pagetopIvRigth.setBackgroundResource(R.mipmap.wisheachdetails_share);
    }


    @Override
    public void initDataAfter() {
        super.initDataAfter();
        kn_url = getIntent().getStringExtra("kn_url");
        Log.e("kn_url", "kn_url=[" + kn_url);
        loadUrl(kn_url);
        //康农工程-----刷新头部标题
        mBridgeWebView.registerHandler("public_TitleChange", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                pageTopTvName.setText(data);
            }
        });
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
