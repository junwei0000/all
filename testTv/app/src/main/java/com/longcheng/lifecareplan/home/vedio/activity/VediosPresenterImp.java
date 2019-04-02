package com.longcheng.lifecareplan.home.vedio.activity;

import android.app.Activity;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.api.BasicResponse;
import com.longcheng.lifecareplan.api.DefaultObserver;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.login.bean.LoginAfterBean;
import com.longcheng.lifecareplan.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 15:42
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class VediosPresenterImp<T> extends VediosContract.Presenter<VediosContract.View> {

    private Activity mContext;
    private VediosContract.View mView;

    public VediosPresenterImp(Activity mContext, VediosContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }


    public void pUsePhoneLogin(String phoneNum, String code) {
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


    @Override
    public void fetch() {

    }
}
