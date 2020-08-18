package com.longcheng.lifecareplan.modular.home.domainname.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.home.domainname.mycomm.activity.CommListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class GrabSuccessActivity extends BaseActivity {


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

    int level;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.tv_skip:
                Intent intent = new Intent(mActivity, CommListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if (level == DomainMenuActivity.level_commue) {
                    intent.putExtra("position", 0);
                } else {
                    intent.putExtra("position", 1);
                }
                startActivity(intent);
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
        return R.layout.domain_grabsuccess;
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
        pageTopTvName.setText("抢注成功");
        String show_content = getIntent().getStringExtra("show_content");
        level = getIntent().getIntExtra("level", DomainMenuActivity.level_commue);
        tvAddress.setText("获得" + show_content);
        if(level==DomainMenuActivity.level_commue){
            tvSkip.setText("查看我的公社");
        }else{
            tvSkip.setText("查看我的大队");
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
