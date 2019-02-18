package com.longcheng.lifecareplan.modular.transaction.fragment;

import android.content.Context;
import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;

/**
 * @author MarkShuai
 * @name 交易页面
 * @time 2017/11/23 17:24
 */
public class TransactionFragment extends BaseFragmentMVP<TransactionContract.View, TransactionPresenterImp<TransactionContract.View>> implements TransactionContract.View {

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_transation;
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
    protected TransactionPresenterImp<TransactionContract.View> createPresent() {
        return new TransactionPresenterImp<>(this);
    }
}
