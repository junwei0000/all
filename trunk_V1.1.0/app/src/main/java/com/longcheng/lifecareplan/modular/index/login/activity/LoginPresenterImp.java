package com.longcheng.lifecareplan.modular.index.login.activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.api.ServerException;
import com.longcheng.lifecareplan.base.ExampleApplication;
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

public class LoginPresenterImp<T> extends LoginContract.Presenter<LoginContract.View> {

    private Context mContext;
    private LoginContract.View mView;
    private LoginContract.Model mModel;

    public LoginPresenterImp(Context mContext, LoginContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    public void pGetList(String uid, int page, int pageSize) {

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

    public void pUsePhoneLogin(String phoneNum, String code) {
        mView.showDialog();
        String token = ExampleApplication.token;
        Log.e("Observable", phoneNum + "   " + code + "  " + token);

        Observable<LoginDataBean> observable = Api.getInstance().service.userPhoneLogin(phoneNum, code, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LoginDataBean>() {
                    public void accept(LoginDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.loginSuccess(responseBean);
                        Log.e("Observable", "   " + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.loginFail();
                    }
                });
    }

    public void pUserAccountLogin(String phoneNum, String pw) {
        mView.showDialog();
        if (!TextUtils.isEmpty(pw)) {
            pw = ConfigUtils.getINSTANCE().MD5(pw);
        }
        String token = ExampleApplication.token;
        Log.e("Observable", phoneNum + "   " + pw + "  " + token);
        Observable<LoginDataBean> observable = Api.getInstance().service.userAccountLogin(phoneNum, pw, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LoginDataBean>() {
                    public void accept(LoginDataBean dataBean) throws Exception {
                        mView.dismissDialog();
                        mView.loginSuccess(dataBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.loginFail();
                        Log.e("Observable", throwable.getMessage());
                    }
                });
    }

    public void pUseWXLogin(String openid, String unionid, String nick_name, String headimgurl
            , String sex, String province, String city, String access_token) {
        mView.showDialog();
        String token = ExampleApplication.token;

        Observable<LoginDataBean> observable = Api.getInstance().service.useWXLogin(openid, unionid, nick_name, headimgurl, sex, province, city, access_token, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LoginDataBean>() {
                    public void accept(LoginDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.loginSuccess(responseBean);
                        Log.e("Observable", "   " + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.loginFail();
                        Log.e("Observable", "   " + throwable.toString());
                    }
                });
    }

    /**
     * 完善信息
     *
     * @param user_id
     * @param code
     * @param phone
     * @param pwd
     */
    public void bindPhone(String user_id, String code, String phone, String pwd) {
        mView.showDialog();
        Observable<LoginDataBean> observable = Api.getInstance().service.bindPhone(user_id, code, phone, pwd, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LoginDataBean>() {
                    public void accept(LoginDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.bindPhoneSuccess(responseBean);
                        Log.e("Observable", "   " + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.loginFail();
                    }
                });
    }

    /**
     * 修改密码
     *
     * @param user_id
     * @param code
     * @param phone
     * @param pwd
     */
    public void updatePW(String user_id, String code, String phone, String pwd, String conf_pwd) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.updatePW(user_id, code, phone, pwd, conf_pwd, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.updatepwSuccess(responseBean);
                        Log.e("Observable", "   " + responseBean.toString());
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.loginFail();
                    }
                });
    }
}
