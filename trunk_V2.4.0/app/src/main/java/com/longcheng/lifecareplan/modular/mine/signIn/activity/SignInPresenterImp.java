package com.longcheng.lifecareplan.modular.mine.signIn.activity;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.mine.signIn.bean.SignInDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class SignInPresenterImp<T> extends SignInContract.Presenter<SignInContract.View> {

    private Context mContext;
    private SignInContract.View mView;
    private SignInContract.Model mModel;

    public SignInPresenterImp(Context mContext, SignInContract.View mView) {
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
    public void getShowInfo(String user_id) {
        mView.showDialog();
        Observable<SignInDataBean> observable = Api.getInstance().service.getSignInfo(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<SignInDataBean>() {
                    @Override
                    public void accept(SignInDataBean responseBean) throws Exception {
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

    /**
     * 签到
     *
     * @param user_id
     */
    public void signIn(String user_id) {
        mView.showDialog();
        Observable<SignInDataBean> observable = Api.getInstance().service.signIn(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<SignInDataBean>() {
                    @Override
                    public void accept(SignInDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.signInSuccess(responseBean);
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
