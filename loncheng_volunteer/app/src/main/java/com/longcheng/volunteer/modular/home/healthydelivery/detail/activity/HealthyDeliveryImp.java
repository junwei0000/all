package com.longcheng.volunteer.modular.home.healthydelivery.detail.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.modular.home.healthydelivery.list.bean.HealthyDeliveryResultBean;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/9/13.
 */

public class HealthyDeliveryImp<T> extends HealthyDeliveryContract.Presenter<HealthyDeliveryContract.View> {

    private Context mContext;
    private HealthyDeliveryContract.View mView;

    public HealthyDeliveryImp(Context mContext, HealthyDeliveryContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    @Override
    public void doRefresh(int page, int pageSize, int type) {
        Log.e("aaa", "doRefresh page = " + page + " ,size = " + pageSize + " , type = " + type);
        mView.showDialog();
        Observable<HealthyDeliveryResultBean> observable = Api.getInstance().service.getNewsList(page, pageSize, type);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<HealthyDeliveryResultBean>() {
                    @Override
                    public void accept(HealthyDeliveryResultBean responseBean) throws Exception {
                        Log.e("aaa", "accept --> " + responseBean.toString());
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            if ("200".equals(responseBean.getStatus())) {
                                mView.onSuccess(responseBean, page);
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
