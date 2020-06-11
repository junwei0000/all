package com.longcheng.lifecareplan.modular.mine.activatenergy.activity;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.GetEnergyListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ActivatEnergyPresenterImp<T> extends ActivatEnergyContract.Presenter<ActivatEnergyContract.View> {

    private Context mContext;
    private ActivatEnergyContract.View mView;
    private ActivatEnergyContract.Model mModel;

    public ActivatEnergyPresenterImp(Context mContext, ActivatEnergyContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * @param
     * @name 激活生命能量数据
     * @time 2017/12/8 17:56
     * @author MarkShuai
     */
    public void getRechargeInfo(String user_id) {
        mView.showDialog();
        Observable<GetEnergyListDataBean> observable = Api.getInstance().service.getRechargeInfo(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<GetEnergyListDataBean>() {
                    @Override
                    public void accept(GetEnergyListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
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
