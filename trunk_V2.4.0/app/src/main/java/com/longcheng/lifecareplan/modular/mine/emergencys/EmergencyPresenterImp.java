package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class EmergencyPresenterImp<T> extends EmergencyContract.Presenter<EmergencyContract.View> {
    @Override
    public void fetch() {

    }

    private Context mContext;
    private EmergencyContract.View mView;

    public EmergencyPresenterImp(Context mContext, EmergencyContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void indexExtend(String user_id,
                            int search_type
    ) {
        mView.showDialog();
        Observable<EmergencyListBean> observable = Api.getInstance().service.indexExtend(user_id,
                search_type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EmergencyListBean>() {
                    @Override
                    public void accept(EmergencyListBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", throwable.toString());
                    }
                });

    }

    public void applyEmergencys(String use_id) {
        mView.showDialog();

        Observable<ApplyEmergencyBean> observable = Api.getInstance().service.applyEmergency(use_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ApplyEmergencyBean>() {
                    @Override
                    public void accept(ApplyEmergencyBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (responseBean.getStatus().equals("200")) {
                            mView.ApplySucess(responseBean);
                        } else {
                            ToastUtils.showToast(responseBean.getMsg());
                        }

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }

    public void isCertify(String use_id) {
        mView.showDialog();
        Observable<CertifyBean> observable = Api.getInstance().service.isCertify(use_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CertifyBean>() {
                    @Override
                    public void accept(CertifyBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (responseBean.getStatus().equals("200")) {
                            mView.getCard(responseBean);
                        } else {
                            mView.getCardError(responseBean.getMsg());

                        }

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();

                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }
}
