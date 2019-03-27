package com.longcheng.volunteer.modular.index.forgetPw.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 15:42
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ForgetPWPresenterImp<T> extends ForgetPWContract.Presenter<ForgetPWContract.View> {

    private Context mContext;
    private ForgetPWContract.View mView;
    private ForgetPWContract.Model mModel;

    public ForgetPWPresenterImp(Context mContext, ForgetPWContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    public void findPassword(String phoneNum, String code, String password, String confirm_password) {
        mView.showDialog();
        password = ConfigUtils.getINSTANCE().MD5(password);
        confirm_password = ConfigUtils.getINSTANCE().MD5(confirm_password);
        Observable<EditDataBean> observable = Api.getInstance().service.findPassword(phoneNum, code, password, confirm_password, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean dataBean) throws Exception {
                        mView.dismissDialog();
                        mView.checkPhoneSuccess(dataBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.loginFail();
                    }
                });
    }


    public void ForgetPWcheckphone(String phoneNum) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.ForgetPWcheckphone(phoneNum, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean dataBean) throws Exception {
                        mView.dismissDialog();
                        mView.checkPhoneSuccess(dataBean);
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
        String token = ExampleApplication.token;
        Log.e("Observable", phoneNum + "   " + 4 + "  " + token);

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
}
