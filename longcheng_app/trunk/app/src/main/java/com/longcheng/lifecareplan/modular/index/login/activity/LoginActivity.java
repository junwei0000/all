package com.longcheng.lifecareplan.modular.index.login.activity;

import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;

public class LoginActivity extends BaseActivityMVP<LoginContract.View, LoginPresenterImp<LoginContract.View>> implements LoginContract.View {

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
        return R.layout.activity_login;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void widgetClick(View v) {

    }

    @Override
    protected LoginPresenterImp<LoginContract.View> createPresent() {
        return new LoginPresenterImp<>(mContext, this);
    }
}
