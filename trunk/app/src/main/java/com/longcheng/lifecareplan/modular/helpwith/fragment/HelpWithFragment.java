package com.longcheng.lifecareplan.modular.helpwith.fragment;

import android.content.Context;
import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：互助页面
 */

public class HelpWithFragment extends BaseFragmentMVP<HelpWithContract.View, HelpWithPresenterImp<HelpWithContract.View>> implements HelpWithContract.View {

    @Override
    public int bindLayout() {
        return R.layout.fragment_help_with;
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
    protected HelpWithPresenterImp<HelpWithContract.View> createPresent() {
        return new HelpWithPresenterImp<>(this);
    }


    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
