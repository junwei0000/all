package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.emergencys.bean.HeloneedIndexBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProgressPresenterImp<T> extends ProgressContract.Present<ProgressContract.View> {
    @Override
    public void fetch() {

    }

    private Context mContext;
    private ProgressContract.View mView;

    public ProgressPresenterImp(Context mContext, ProgressContract.View mView) {
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

}
