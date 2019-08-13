package com.longcheng.lifecareplan.modular.index.register.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.index.login.bean.LoginDataBean;
import com.longcheng.lifecareplan.modular.index.login.bean.SendCodeBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 15:42
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class RegisterPresenterImp<T> extends RegisterContract.Presenter<RegisterContract.View> {

    private Context mContext;
    private RegisterContract.View mView;
    private RegisterContract.Model mModel;

    public RegisterPresenterImp(Context mContext, RegisterContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }


    public void checkphone(String phoneNum) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.registerCheckphone(phoneNum, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean dataBean) throws Exception {
                        mView.dismissDialog();
                        mView.CheckPhoneSuccess(dataBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.loginFail();
                    }
                });
    }

    /**
     * 1：注册 2：找回密码 3.修改手机号 4.快捷登陆 默认是4 5.绑定手机号
     *
     * @param phoneNum
     * @param type
     */
    public void pUseSendCode(String phoneNum, String type) {
        mView.showDialog();
        Observable<SendCodeBean> observable = Api.getInstance().service.userSendCode(phoneNum, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<SendCodeBean>() {
                    public void accept(SendCodeBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.getCodeSuccess(responseBean);
                        Log.e("Observable", "   " + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        Log.e("Observable", throwable.getMessage() + "   " + throwable.toString());
                    }
                });
    }

    /**
     * 注册
     *
     * @param phoneNum
     * @param code
     * @param password
     * @param confirm_password
     */
    public void register(String phoneNum, String code, String password, String confirm_password) {
        mView.showDialog();
        password = ConfigUtils.getINSTANCE().MD5(password);
        confirm_password = ConfigUtils.getINSTANCE().MD5(confirm_password);
        Observable<LoginDataBean> observable = Api.getInstance().service.register(phoneNum, code, password, confirm_password, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LoginDataBean>() {
                    @Override
                    public void accept(LoginDataBean dataBean) throws Exception {
                        mView.dismissDialog();
                        mView.registerSuccess(dataBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.loginFail();
                        Log.e("Observable",throwable.toString());
                    }
                });
    }

}
