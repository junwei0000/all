package com.longcheng.lifecareplan.modular.mine.fragment;

import android.content.Context;
import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;

/**
 * @author MarkShuai
 * @name 我的页面
 * @time 2017/11/23 17:24
 */
public class MineFragment extends BaseFragmentMVP<MineContract.View, MinePresenterImp<MineContract.View>> implements MineContract.View {

    @Override
    public int bindLayout() {
        return R.layout.fragment_my_page;
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
    protected MinePresenterImp<MineContract.View> createPresent() {
        return new MinePresenterImp<>(this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
