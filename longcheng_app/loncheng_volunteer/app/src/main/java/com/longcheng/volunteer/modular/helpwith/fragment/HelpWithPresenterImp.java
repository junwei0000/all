package com.longcheng.volunteer.modular.helpwith.fragment;


import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.helpwith.bean.HelpIndexDataBean;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:23
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HelpWithPresenterImp<T> extends HelpWithContract.Present<HelpWithContract.View> {

    private HelpWithContract.View mView;
    private HelpWithContract.Model model;

    public HelpWithPresenterImp(HelpWithContract.View view) {
        this.mView = view;
        model = new HelpWithModelImp();
    }

    @Override
    public void fetch() {

    }

    public void getHelpInfo(String user_id) {
        mView.showDialog();
        Observable<HelpIndexDataBean> observable = Api.getInstance().service.getHelpInfo(user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<HelpIndexDataBean>() {
                    @Override
                    public void accept(HelpIndexDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.getHelpIndexSuccess(responseBean);
                        Log.e("Observable", "" + responseBean.toString());
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
