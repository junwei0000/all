package com.longcheng.lifecareplantv.modular.login.activity;

import android.app.Activity;
import android.util.Log;

import com.longcheng.lifecareplantv.api.Api;
import com.longcheng.lifecareplantv.base.ExampleApplication;
import com.longcheng.lifecareplantv.bean.Bean;
import com.longcheng.lifecareplantv.http.api.DefaultObserver;
import com.longcheng.lifecareplantv.http.basebean.BasicResponse;
import com.longcheng.lifecareplantv.modular.login.bean.LoginAfterBean;
import com.longcheng.lifecareplantv.utils.ToastUtils;

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

    private Activity mContext;
    private LoginContract.View mView;
    private LoginContract.Model mModel;

    public LoginPresenterImp(Activity mContext, LoginContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

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

        Observable<BasicResponse<Bean>> observable = Api.getInstance().service.userSendCode(phoneNum, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<Bean>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<Bean> response) {
                        Bean results = response.getData();
                        ToastUtils.showToast(results.toString());
                        Log.e("Observable", "https://www.bestdo.com/new-bd-app/2.7.0/============" + results.toString());
                    }

                    @Override
                    public void onError() {
                        Log.e("Observable", "onErrorUser");
                    }
                });
    }

    public void pUsePhoneLogin(String phoneNum, String code) {
        mView.showDialog();
        String token = ExampleApplication.token;
        Log.e("Observable", phoneNum + "   " + code + "  " + token);

        Observable<BasicResponse<LoginAfterBean>> observable = Api.getInstance().service.userPhoneLogin(phoneNum, code, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LoginAfterBean>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<LoginAfterBean> response) {
                        LoginAfterBean results = response.getData();
                        ToastUtils.showToast(results.toString());
                        Log.e("Observable", "https://www.bestdo.com/new-bd-app/2.7.0/============" + results.toString());
                    }

                    @Override
                    public void onError() {
                        Log.e("Observable", "onErrorUser");
                    }
                });
    }


}
