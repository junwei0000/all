package com.longcheng.lifecareplan.modular.mine.rewardcenters.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.rewardcenters.bean.RewardCentersResultBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/9/4.
 */

public class RewardCentersImp<T> extends RewardCentersContract.Presenter<RewardCentersContract.View> {
    private Context mContext;
    private RewardCentersContract.View mView;
    private RewardCentersContract.Model mModel;

    public RewardCentersImp(Context mContext, RewardCentersContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    @Override
    void doRefresh(int page, int pageSize, String userId, String token) {
        mView.showDialog();
        Observable<RewardCentersResultBean> observable = Api.getInstance().service.getReward(userId, page, pageSize, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<RewardCentersResultBean>() {
                    @Override
                    public void accept(RewardCentersResultBean responseBean) throws Exception {
                        Log.e("aaa", "accept --> " + responseBean.toString());
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.onSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.onError("");
                    }
                });
    }
}
