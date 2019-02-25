package com.longcheng.lifecareplan.modular.helpwith.myDedication.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.HelpEnergyListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.bean.MyDedicationListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyDedicationPresenterImp<T> extends MyDedicationContract.Presenter<MyDedicationContract.View> {

    private Context mContext;
    private MyDedicationContract.View mView;
    private MyDedicationContract.Model mModel;

    public MyDedicationPresenterImp(Context mContext, MyDedicationContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * @param
     * @name 获取ViewPager的数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void setListViewData(String user_id,
                                int page,
                                int page_size) {
        mView.showDialog();
        Observable<MyDedicationListDataBean> observable = Api.getInstance().service.getMyDedicationList(user_id,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyDedicationListDataBean>() {
                    @Override
                    public void accept(MyDedicationListDataBean responseBean) throws Exception {
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

    /**
     * @param
     * @name 明细
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getDetail(String user_id, String h_user_id,
                          int page,
                          int page_size) {
        mView.showDialog();
        Observable<MyDedicationListDataBean> observable = Api.getInstance().service.getDetail(user_id,
                h_user_id, page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyDedicationListDataBean>() {
                    @Override
                    public void accept(MyDedicationListDataBean responseBean) throws Exception {
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
