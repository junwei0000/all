package com.longcheng.volunteer.modular.mine.starinstruction;


import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.modular.webView.WebAct;

import butterknife.BindView;


/**
 * 星级说明
 * Created by Burning on 2018/9/11.
 */

public class StarInstructionAct extends WebAct {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
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
        pageTopTvName.setText("星级说明");
        pagetopLayoutLeft.setOnClickListener(this);
        setOrChangeTranslucentColor(toolbar, null);

    }

    @Override
    public void initDataAfter() {
        super.initDataAfter();
        String url = getIntent().getStringExtra("starturl");
        loadUrl(url);
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }
}


