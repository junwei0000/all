package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/****
 * 福圈简介页面
 * 新增 Gerry
 *
 */
public class FuQSynopsisActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pageTop_layout_num)
    LinearLayout pageTopLayoutNum;
    @BindView(R.id.top_tv_num)
    TextView topTvNum;
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.fuqaun_hinit_dingyi)
    TextView fuqaunHinitDingyi;
    @BindView(R.id.fuquan_dingyi_congent)
    TextView fuquanDingyiCongent;
    @BindView(R.id.fuqaun_hinit_used)
    TextView fuqaunHinitUsed;
    @BindView(R.id.fuquan_used_congent)
    TextView fuquanUsedCongent;
    @BindView(R.id.fuqaun_hinit_get)
    TextView fuqaunHinitGet;
    @BindView(R.id.fuquan_get_congent_1)
    TextView fuquanGetCongent1;
    @BindView(R.id.fuquan_get_congent_2)
    TextView fuquanGetCongent2;
    @BindView(R.id.fuqaun_hinit_out)
    TextView fuqaunHinitOut;
    @BindView(R.id.fuquan_out_congent)
    TextView fuquanOutCongent;
    @BindView(R.id.fuqaun_hinit_in)
    TextView fuqaunHinitIn;
    @BindView(R.id.fuquan_in_congent)
    TextView fuquanInCongent;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pagetop_layout_left:

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
        return R.layout.fuqsynopsis_layout;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pageTopTvName.setText(getResources().getString(R.string.fuquan_title_activity));
        pagetopLayoutLeft.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {

    }

}
