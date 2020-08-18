package com.longcheng.lifecareplan.modular.mine.treasurebowl.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.home.domainname.activity.DomainMenuActivity;
import com.longcheng.lifecareplan.modular.home.domainname.mycomm.activity.CommListActivity;

import butterknife.BindView;

public class CornucopiaSucessActicity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_skip)
    TextView tvSkip;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:
            case R.id.tv_skip:
                doFinish();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.cornucop_sucess_layout;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tvSkip.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        pageTopTvName.setText("赋能完成");
        try {
            String groupnum = getIntent().getStringExtra("groupnum");
            tvAddress.setText(String.format(mActivity.getResources().getString(R.string.hinit_cornucop_sucess), groupnum + ""));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
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
