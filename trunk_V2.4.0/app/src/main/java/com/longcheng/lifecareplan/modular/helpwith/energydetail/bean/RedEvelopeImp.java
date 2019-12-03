package com.longcheng.lifecareplan.modular.helpwith.energydetail.bean;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/9/8.
 */

public class RedEvelopeImp<T> extends RedEvelopeContract.Presenter<RedEvelopeContract.View> {

    private Context mContext;
    private RedEvelopeContract.View mView;
    private RedEvelopeContract.Model mModel;

    public RedEvelopeImp(Context mContext, RedEvelopeContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }


    /**
     * 获取红包信息
     *
     * @param user_id
     * @param one_order_id
     */
    @Override
    public void getRedEnvelopeData(String user_id, String one_order_id) {
        mView.showDialog();
        Observable<PayDataBean> observable = Api.getInstance().service.getRedEnvelopeData(user_id,
                one_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<PayDataBean>() {
                    @Override
                    public void accept(PayDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if ("200".equals(responseBean.getStatus())) {
                            mView.getRedEnvelopeSuccess(responseBean);
                        } else if ("400".equals(responseBean.getStatus())) {
                            mView.onGetRedEnvelopeError(responseBean.getMsg());
                        }
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.onGetRedEnvelopeError("");
                    }
                });
    }

    /**
     * 开红包
     *
     * @param user_id
     * @param one_order_id
     */
    @Override
    public void openRedEnvelope(String user_id, String one_order_id) {
        mView.showDialog();
        Observable<OpenRedDataBean> observable = Api.getInstance().service.openRedEnvelope(user_id,
                one_order_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<OpenRedDataBean>() {
                    @Override
                    public void accept(OpenRedDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if ("200".equals(responseBean.getStatus())) {
                            mView.OpenRedEnvelopeSuccess(responseBean);
                        } else if ("400".equals(responseBean.getStatus())) {
                            mView.onOpenRedEnvelopeError(responseBean.getMsg());
                        }

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.onOpenRedEnvelopeError("");
                    }
                });
    }
}
