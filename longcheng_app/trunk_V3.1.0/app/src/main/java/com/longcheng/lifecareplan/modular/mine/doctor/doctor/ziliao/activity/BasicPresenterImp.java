package com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.activity;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.AYApplyListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.BasicInfoListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class BasicPresenterImp<T> extends BasicContract.Presenter<BasicContract.View> {

    private BasicContract.View mView;

    public BasicPresenterImp(BasicContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void getBasicInfoList(String user_id, int page, int page_size) {
        mView.showDialog();
        Observable<BasicInfoListDataBean> observable = Api.getInstance().service.getBasicInfoList(user_id,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<BasicInfoListDataBean>() {
                    @Override
                    public void accept(BasicInfoListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
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


    public void getAYApplyList(String user_id, int page, int page_size) {
        mView.showDialog();
        Observable<AYApplyListDataBean> observable = Api.getInstance().service.getAYApplyList(user_id,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AYApplyListDataBean>() {
                    @Override
                    public void accept(AYApplyListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ayapplySuccess(responseBean, page);
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
