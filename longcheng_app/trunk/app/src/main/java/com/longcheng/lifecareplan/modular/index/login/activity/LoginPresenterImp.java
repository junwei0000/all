package com.longcheng.lifecareplan.modular.index.login.activity;

import android.content.Context;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 15:42
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class LoginPresenterImp<T> extends LoginContract.Presenter<LoginContract.View> {

    private Context mContext;
    private LoginContract.View mView;
    private LoginContract.Model mModel;

    public LoginPresenterImp(Context mContext, LoginContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }
}
