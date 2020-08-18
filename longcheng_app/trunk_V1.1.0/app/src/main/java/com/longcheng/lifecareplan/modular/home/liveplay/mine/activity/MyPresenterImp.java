package com.longcheng.lifecareplan.modular.home.liveplay.mine.activity;

import android.content.Context;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */
public class MyPresenterImp<T> extends MyContract.Presenter<MyContract.View> {

    private Context mContext;
    private MyContract.View mView;
    private MyContract.Model mModel;

    public MyPresenterImp(Context mContext, MyContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

}
