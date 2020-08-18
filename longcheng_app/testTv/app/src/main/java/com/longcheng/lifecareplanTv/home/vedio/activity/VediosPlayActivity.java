package com.longcheng.lifecareplanTv.home.vedio.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
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
import com.longcheng.lifecareplanTv.home.set.SetActivity;
import com.longcheng.lifecareplanTv.home.vedio.adapter.VediosAdapter;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;
import com.longcheng.lifecareplanTv.utils.ConfigUtils;
import com.longcheng.lifecareplanTv.utils.DatesUtils;
import com.longcheng.lifecareplanTv.utils.PlayerUtils;
import com.longcheng.lifecareplanTv.utils.ToastUtilsNew;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.SearchResultModel;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvGridLayoutManager;
import com.longcheng.lifecareplanTv.utils.tvrecyclerview.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 视频播放
 */
public class VediosPlayActivity extends BaseActivityMVP<VediosContract.View, VediosPresenterImp<VediosContract.View>> implements VediosContract.View {


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
        return R.layout.vedio_play;
    }

    @Override
    public void initView(View view) {
        initTimer();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setListener() {
    }

    @Override
    public void initDataAfter() {
    }


    @Override
    public void setDateInfo() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_set:
                break;
            default:
                break;
        }
    }

    @Override
    protected VediosPresenterImp<VediosContract.View> createPresent() {
        return new VediosPresenterImp<>(mActivity, this);
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
