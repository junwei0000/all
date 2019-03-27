package com.longcheng.volunteer.modular.mine.rebirth.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.rebirth.bean.RebirthDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class RebirthPresenterImp<T> extends RebirthContract.Presenter<RebirthContract.View> {

    private Context mContext;
    private RebirthContract.View mView;

    public RebirthPresenterImp(Context mContext, RebirthContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 获取重生卡信息
     *
     * @param user_id
     */
    public void getRenascenceInfo(String user_id) {
        mView.showDialog();
        Observable<RebirthDataBean> observable = Api.getInstance().service.getRenascenceInfo(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<RebirthDataBean>() {
                    @Override
                    public void accept(RebirthDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.getRebirthInfoSuccess(responseBean);
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
     * 设置重生
     *
     * @param user_id
     */
    public void setRenascence(String user_id, String code) {
        mView.showDialog();
        Observable<ResponseBean> observable = Api.getInstance().service.setRenascence(user_id,
                code, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.RebirthSuccess(responseBean);
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

}
