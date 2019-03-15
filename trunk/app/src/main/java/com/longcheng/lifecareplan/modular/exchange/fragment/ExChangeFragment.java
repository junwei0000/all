package com.longcheng.lifecareplan.modular.exchange.fragment;

import android.content.Context;
import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：兑换页面
 */

public class ExChangeFragment extends BaseFragmentMVP<ExChangeContract.View, ExChangePresenterImp<ExChangeContract.View>> implements ExChangeContract.View {

    @Override
    public int bindLayout() {
        return R.layout.fragment_exchange;
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
    protected ExChangePresenterImp<ExChangeContract.View> createPresent() {
        return new ExChangePresenterImp<>(this);
    }


    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

}
