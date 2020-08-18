package com.longcheng.lifecareplan.modular.mine.bill.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.bill.bean.BillResultBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/8/30.
 */

public class BillPresenterImp<T> extends BillContract.Presenter<BillContract.View> {

    private Context mContext;
    private BillContract.View mView;

    public BillPresenterImp(Context mContext, BillContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    /**
     * @param userId
     * @param page
     * @param pageSize
     * @param source_type
     * @param month
     */
    public void getData(String userId, int page, int pageSize, int source_type, String month, int type) {
        mView.showDialog();
        Observable<BillResultBean> observable = null;
        if (type == BasicssActivity.TYPE_BILL) {
            observable = Api.getInstance().service.getBillList(userId, page, pageSize,
                    source_type, month, ExampleApplication.token);
        } else if (type == BasicssActivity.TYPE_WAKESKB) {
            observable = Api.getInstance().service.getWakeSkbList(userId, page, pageSize,
                    source_type, month, ExampleApplication.token);
        } else if (type == BasicssActivity.TYPE_SLEEPSKB) {
            observable = Api.getInstance().service.getSleepSkbList(userId, page, pageSize,
                    source_type, month, ExampleApplication.token);
        } else if (type == BasicssActivity.TYPE_ENGRY) {
            observable = Api.getInstance().service.getEngryRecordList(userId, page, pageSize,
                    source_type, month, ExampleApplication.token);
        } else if (type == BasicssActivity.TYPE_SLEEPENGRY) {
            observable = Api.getInstance().service.getSleepEngryRecordList(userId, page, pageSize,
                    source_type, month, ExampleApplication.token);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<BillResultBean>() {
                    @Override
                    public void accept(BillResultBean responseBean) throws Exception {
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
