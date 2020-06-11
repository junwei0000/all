package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CSRecordImp<T> extends CSRecordContract.Presenter<CSRecordContract.View> {
    private Context mContext;
    private CSRecordContract.View mView;

    @Override
    public void fetch() {

    }

    public CSRecordImp(Context mContext, CSRecordContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void helpneed_customerList(String user_id, int search_type,
                                      int page, int page_size
    ) {
        mView.showDialog();
        Observable<CSRecordBean> observable = Api.getInstance().service.helpneed_customerList(user_id,
                search_type, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<CSRecordBean>() {
                    @Override
                    public void accept(CSRecordBean responseBean) throws Exception {
                        mView.dismissDialog();
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
