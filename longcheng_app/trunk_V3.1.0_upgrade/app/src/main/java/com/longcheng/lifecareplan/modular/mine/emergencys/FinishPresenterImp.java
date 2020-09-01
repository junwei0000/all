package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CashInfoBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HeloneedIndexBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.ResultApplyBean;
import com.longcheng.lifecareplan.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FinishPresenterImp<T> extends FinishContract.Present<FinishContract.View> {
    private Context mContext;
    private FinishContract.View mView;

    @Override
    public void fetch() {

    }

    public FinishPresenterImp(Context mContext, FinishContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void helpneed_index(String user_id, int search_type, String keyword,
                               int page,
                               int page_size) {
        mView.showDialog();
        Observable<HeloneedIndexBean> observable = Api.getInstance().service.helpneed_index(user_id,
                search_type, page, page_size, keyword, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<HeloneedIndexBean>() {
                    @Override
                    public void accept(HeloneedIndexBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
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

    public void cashInfo(String user_id, int help_need_id
    ) {
        mView.showDialog();
        Observable<CashInfoBean> observable = Api.getInstance().service.cashInfo(user_id,
                help_need_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CashInfoBean>() {
                    @Override
                    public void accept(CashInfoBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.getUserCard(responseBean);
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

    public void ajaxCashMoney(String user_id, int help_need_id
    ) {
        mView.showDialog();
        Observable<ResultApplyBean> observable = Api.getInstance().service.helpneed_ajaxCashMoney(user_id,
                help_need_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResultApplyBean>() {
                    @Override
                    public void accept(ResultApplyBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            if (responseBean.getStatus().equals("200")) {
                                mView.appSuccess(responseBean);
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
                            mView.CardError(responseBean.getMsg());
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
