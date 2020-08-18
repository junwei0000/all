package com.longcheng.lifecareplan.modular.home.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.modular.home.adapter.BannerPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HomeFragment extends BaseFragmentMVP<HomeContract.View, HomePresenterImp<HomeContract.View>> implements HomeContract.View {


    @BindView(R.id.vp_home)
    ViewPager mViewPagerBanner;

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected HomePresenterImp<HomeContract.View> createPresent() {
        return new HomePresenterImp<>(mContext,this);
    }


    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void BannerListSuccess(List<Drawable> list) {
        BannerPagerAdapter adapter = new BannerPagerAdapter(mContext, list);
        Log.i(TAG, "BannerListSuccess: " + list.size());
        mViewPagerBanner.setAdapter(adapter);
    }

    @Override
    public void BannerListError(String code) {

    }
}
