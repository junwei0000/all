package com.longcheng.lifecareplanTv.home.menu.activity;

import android.app.Activity;
import android.util.Log;

import com.longcheng.lifecareplanTv.api.Api;
import com.longcheng.lifecareplanTv.api.BasicResponse;
import com.longcheng.lifecareplanTv.api.DefaultObserver;
import com.longcheng.lifecareplanTv.base.MyApplication;
import com.longcheng.lifecareplanTv.login.bean.LoginAfterBean;
import com.longcheng.lifecareplanTv.utils.ToastUtilsNew;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 15:42
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MenuPresenterImp<T> extends MenuContract.Presenter<MenuContract.View> {

    private Activity mContext;
    private MenuContract.View mView;

    public MenuPresenterImp(Activity mContext, MenuContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }


    public void pUsePhoneLogin(String phoneNum, String code) {
        String token = MyApplication.token;
        Log.e("Observable", phoneNum + "   " + code + "  " + token);

        Observable<BasicResponse<LoginAfterBean>> observable = Api.getInstance().service.userPhoneLogin(phoneNum, code, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LoginAfterBean>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<LoginAfterBean> response) {
                        LoginAfterBean results = response.getData();
                        ToastUtilsNew.showToast(results.toString());
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
