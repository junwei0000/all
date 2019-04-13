package com.longcheng.lifecareplanTv.home.picture.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.api.BasicResponse;
import com.longcheng.lifecareplanTv.base.BaseActivityMVP;
import com.longcheng.lifecareplanTv.home.picture.adapter.PictureAdapter;
import com.longcheng.lifecareplanTv.home.set.SetActivity;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;
import com.longcheng.lifecareplanTv.utils.ConfigUtils;
import com.longcheng.lifecareplanTv.utils.DatesUtils;
import com.longcheng.lifecareplanTv.utils.ToastUtilsNew;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.SearchResultModel;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvGridLayoutManager;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerView;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerViewList;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerViewPic;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 图片
 */
public class PictureActivity extends BaseActivityMVP<PictureContract.View, PicturePresenterImp<PictureContract.View>> implements PictureContract.View {

    @BindView(R.id.pageTop_tv_time)
    TextView pageTopTvTime;
    @BindView(R.id.pageTop_tv_date)
    TextView pageTopTvDate;
    @BindView(R.id.pageTop_tv_week)
    TextView pageTopTvWeek;
    @BindView(R.id.pagetop_layout_set)
    LinearLayout pagetopLayoutSet;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pageTop_tv_phone)
    TextView pageTopTvPhone;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.pageTop_iv_thumb)
    ImageView pageTopIvThumb;
    @BindView(R.id.search_result_recyclerView)
    TvRecyclerViewPic mRecyclerView;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.layout_right)
    LinearLayout layoutRight;
    private TvGridLayoutManager mLayoutManager;
    private PictureAdapter mAdapter;

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.picture;
    }

    @Override
    public void initView(View view) {
        initTimer();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setListener() {
        pagetopLayoutRigth.setVisibility(View.VISIBLE);
        pagetopLayoutSet.setOnClickListener(this);
        layoutLeft.setOnClickListener(this);
        layoutRight.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setSelectFouseText(pagetopLayoutSet);
        ConfigUtils.getINSTANCE().setSelectFouseText(layoutLeft);
        ConfigUtils.getINSTANCE().setSelectFouseText(layoutRight);
        pagetopLayoutSet.setFocusable(false);
    }

    @Override
    public void initDataAfter() {
        mRecyclerView.setBtnFouse(pagetopLayoutSet);
        //去掉动画,否则当notify数据的时候,焦点会丢失
        mRecyclerView.setItemAnimator(null);
        mLayoutManager = new TvGridLayoutManager(this, 6);
        mLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setSelectedItemOffset(0, 0);
        mRecyclerView.setSelectedItemAtCentered(true);
        initData(1);
    }

    int page = 1;
    int pageSize = 12;

    int totalPage = 10;

    public void initData(int page_) {
        this.page = page_;
        List<SearchResultModel> dataList = new ArrayList<>();
        for (int i = (page - 1) * pageSize; i < (page) * pageSize; i++) {
            dataList.add(new SearchResultModel(i + 1, "title : " + (i + 1)));
        }
        mAdapter = new PictureAdapter(this, mRecyclerView, mLayoutManager, dataList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setDateInfo() {
        String date = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        pageTopTvDate.setText(date);
        String week = DatesUtils.getInstance().getNowTime("EE");
        pageTopTvWeek.setText(week);
        String time = DatesUtils.getInstance().getNowTime("HH:mm");
        pageTopTvTime.setText(time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_set:
                Intent intent = new Intent(this, SetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_left:
                if (page > 1) {
                    initData(page - 1);
                } else {
                    ToastUtilsNew.showToast("已到第一页");
                }
                break;
            case R.id.layout_right:
                if (page < totalPage) {
                    initData(page + 1);
                } else {
                    ToastUtilsNew.showToast("已到最后一页");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected PicturePresenterImp<PictureContract.View> createPresent() {
        return new PicturePresenterImp<>(mActivity, this);
    }


    @Override
    public void getMenuInfoSuccess(BasicResponse<LoginAfterBean> responseBean) {

    }


    @Override
    public void onError() {
        ToastUtilsNew.showToast(getString(R.string.net_tishi));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
        }
        return false;
    }
}
