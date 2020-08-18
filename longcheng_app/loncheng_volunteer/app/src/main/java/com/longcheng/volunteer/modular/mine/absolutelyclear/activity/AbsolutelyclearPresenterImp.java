package com.longcheng.volunteer.modular.mine.absolutelyclear.activity;

import android.content.Context;

import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

/**
 * Created by Burning on 2018/8/30.
 */

public class AbsolutelyclearPresenterImp<T> extends AbsolutelyclearContract.Presenter<AbsolutelyclearContract.View> {

    private Context mContext;
    private AbsolutelyclearContract.View mView;
    private AbsolutelyclearContract.Model mModel;

    public AbsolutelyclearPresenterImp(Context mContext, AbsolutelyclearContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    @Override
    void setData(int page, int pageSize) {
        mView.onSuccess(UserUtils.getUserName(mContext));
    }
}
