package com.longcheng.volunteer.modular.mine.myorder.ordertracking.activity;

import android.content.Context;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.mine.myorder.ordertracking.bean.TrankListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class TrankPresenterImp<T> extends TrankContract.Presenter<TrankContract.View> {

    private Context mContext;
    private TrankContract.View mView;
    private TrankContract.Model mModel;

    public TrankPresenterImp(Context mContext, TrankContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void getListViewData(String user_id, String order_id) {
        mView.showDialog();
        Observable<TrankListDataBean> observable;
        observable = Api.getInstance().service.getOrderTrankList(user_id, order_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<TrankListDataBean>() {
                    @Override
                    public void accept(TrankListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ListSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });
    }
}
