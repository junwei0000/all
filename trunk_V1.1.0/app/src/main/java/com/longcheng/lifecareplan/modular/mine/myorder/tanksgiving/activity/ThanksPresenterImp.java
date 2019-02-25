package com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.activity;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderListDataBean;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.ThanksAfterBean;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.ThanksListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ThanksPresenterImp<T> extends ThanksContract.Presenter<ThanksContract.View> {

    private Context mContext;
    private ThanksContract.View mView;
    private ThanksContract.Model mModel;

    public ThanksPresenterImp(Context mContext, ThanksContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void getListViewData(String user_id, String order_id, int type, int page, int pageSize) {
        mView.showDialog();
        Observable<ThanksListDataBean> observable;
        if (type == 2) {
            observable = Api.getInstance().service.getabilityGrateful(user_id, order_id,
                    page, pageSize, ExampleApplication.token);
        } else {
            observable = Api.getInstance().service.gethelpGoodsGrateful(user_id, order_id,
                    page, pageSize, ExampleApplication.token);
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ThanksListDataBean>() {
                    @Override
                    public void accept(ThanksListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ListSuccess(responseBean, page);
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
