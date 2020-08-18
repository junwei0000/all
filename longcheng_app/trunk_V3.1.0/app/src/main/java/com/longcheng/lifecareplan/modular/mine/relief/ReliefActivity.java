package com.longcheng.lifecareplan.modular.mine.relief;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.mine.emergencys.MyFragmentPagerAdapter;
import com.longcheng.lifecareplan.widget.CanScrollViewPager;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReliefActivity extends BaseActivityMVP<ReliefContract.View, ReliefPresenterImp<ReliefContract.View>> implements ReliefContract.View {


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
    @BindView(R.id.pageTop_tv_rigth)
    TextView pageTopTvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.tv_lines)
    TextView tvLines;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.tvFinish)
    TextView tvFinish;
    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.view_pager)
    CanScrollViewPager viewPager;
    @BindView(R.id.tv_apply_help)
    TextView tvApplyHelp;
    @BindView(R.id.fl)
    FrameLayout fl;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_relief;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("救济列表");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void initDataAfter() {
        initData();
        mPresent.getReliefBottom();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected ReliefPresenterImp<ReliefContract.View> createPresent() {
        return new ReliefPresenterImp<>(mContext, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresent.getReliefBottom();
    }

    private void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ProgressFragment.newInstance());
        fragmentList.add(FinishFragment.newInstance());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        //viewPager事件
        viewPager.setScanScroll(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tvFinish.setTextColor(getResources().getColor(R.color.black));
                    tvProgress.setTextColor(getResources().getColor(R.color.red));
                    viewPager.setCurrentItem(0);
                } else if (position == 1) {
                    tvFinish.setTextColor(getResources().getColor(R.color.red));
                    tvProgress.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);

    }


    @OnClick({R.id.pagetop_iv_left, R.id.tv_apply_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pagetop_iv_left:
                doFinish();
                break;
            case R.id.tv_apply_help:
                mPresent.applyRelief();
                break;

        }
    }


    @OnClick({R.id.tvProgress, R.id.tvFinish})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvFinish:
                tvFinish.setTextColor(getResources().getColor(R.color.red));
                tvProgress.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(1);
                break;
            case R.id.tvProgress:
                tvFinish.setTextColor(getResources().getColor(R.color.black));
                tvProgress.setTextColor(getResources().getColor(R.color.red));
                viewPager.setCurrentItem(0);
                break;
        }
    }

    @Override
    public void applySuccess() {
        Toast.makeText(mActivity, "申请成功", Toast.LENGTH_SHORT).show();
        //隐藏按钮
        tvApplyHelp.setVisibility(View.GONE);

    }

    @Override
    public void bottomInfoSuccess(ReliefBottomInfoBean.DataBean data) {
        if (data == null) return;
        String status = TextUtils.isEmpty(data.status) ? "" : data.status;
        //申请救济按钮是否显示
        if ("1".equals(status)) {
            tvApplyHelp.setVisibility(View.VISIBLE);
        } else {
            tvApplyHelp.setVisibility(View.GONE);
        }

    }

}
