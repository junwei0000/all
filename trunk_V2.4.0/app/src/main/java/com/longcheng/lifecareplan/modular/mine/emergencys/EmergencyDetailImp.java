package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EmergencyDetailImp<T> extends EmergencyDetailContract.Presenter<EmergencyDetailContract.View> {
    private Context mContext;
    private EmergencyDetailContract.View mView;

    @Override
    public void fetch() {

    }

    public EmergencyDetailImp(Context mContext, EmergencyDetailContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void helpneed_info(String user_id,
                              int help_need_id
    ) {
        mView.showDialog();
        Observable<EmergencyDetaiBean> observable = Api.getInstance().service.helpneed_info(user_id,
                help_need_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EmergencyDetaiBean>() {
                    @Override
                    public void accept(EmergencyDetaiBean responseBean) throws Exception {
                        mView.dismissDialog();
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

    public void helpneed_pay(String user_id,
                             int help_need_id, int super_ability
    ) {
        mView.showDialog();
        Observable<EmergencysPayBean> observable = Api.getInstance().service.helpneed_pay(user_id,
                help_need_id, super_ability, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EmergencysPayBean>() {
                    @Override
                    public void accept(EmergencysPayBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.pay(responseBean);

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


    public void helpneed_order(String user_id,
                               int help_need_id, int page, int page_size
    ) {
        mView.showDialog();
        Observable<HarvestBean> observable = Api.getInstance().service.helpneed_order(user_id,
                help_need_id, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<HarvestBean>() {
                    @Override
                    public void accept(HarvestBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.order(responseBean, page);
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
}
