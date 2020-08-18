package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;

import butterknife.BindView;

public class ResultActivity extends BaseActivity {

    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.tv_finish)
    TextView tv_finish;
    @BindView(R.id.tv_sucess)
    TextView tv_sucess;
    private String title;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
            case R.id.pagetop_layout_left:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                back();
                break;

        }
    }

    private void back() {
        doFinish();
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_result;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        setOrChangeTranslucentColor(toolbar, null);
        if (getIntent().hasExtra("result")) {
            pageTopTvName.setText(getIntent().getStringExtra("result"));
            tv_sucess.setText(getIntent().getStringExtra("result"));
        } else {
            pageTopTvName.setText("成功");
            tv_sucess.setText("成功");

        }
    }
}
