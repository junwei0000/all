package com.longcheng.lifecareplan.modular.mine.awordofgold.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.awordofgold.bean.AWordOfGoldResponseBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/9/4.
 */

public class AWordOfGoldImp<T> extends AWordOfGoldContract.Presenter<AWordOfGoldContract.View> {

    private Context mContext;
    private AWordOfGoldContract.View mView;
    private AWordOfGoldContract.Model mModel;

    public AWordOfGoldImp(Context mContext, AWordOfGoldContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    @Override
    void doRefresh(String userId, String token, String selectUserId) {
        mView.showDialog();
        Observable<AWordOfGoldResponseBean> observable = Api.getInstance().service.getAWordOfGold(userId, ExampleApplication.token, selectUserId);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AWordOfGoldResponseBean>() {
                    @Override
                    public void accept(AWordOfGoldResponseBean responseBean) throws Exception {
                        Log.e("aaa", "accept --> " + responseBean.toString());
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.onSuccess(responseBean);
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
