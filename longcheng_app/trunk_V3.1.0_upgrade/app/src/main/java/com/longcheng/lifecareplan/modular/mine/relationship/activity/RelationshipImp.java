package com.longcheng.lifecareplan.modular.mine.relationship.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.relationship.bean.RelationshipBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/9/1.
 */

public class RelationshipImp<T> extends RelationshipContract.Presenter<RelationshipContract.View> {
    private Context mContext;
    private RelationshipContract.View mView;
    private RelationshipContract.Model mModel;

    public RelationshipImp(Context mContext, RelationshipContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    @Override
    void doRefresh(String userId, String token) {
        mView.showDialog();
        Observable<RelationshipBean> observable = Api.getInstance().service.getRelationshipAccount(userId, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<RelationshipBean>() {
                    @Override
                    public void accept(RelationshipBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            if ("200".equals(responseBean.getStatus())) {
                                mView.onSuccess(responseBean);
                            } else {
                                mView.onError(responseBean.getMsg());
                            }
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
