package com.longcheng.volunteer.modular.mine.changeinviter.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.volunteer.modular.index.login.bean.SendCodeBean;
import com.longcheng.volunteer.modular.mine.changeinviter.bean.InviteDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditListDataBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ChangeInviterPresenterImp<T> extends ChangeInviterContract.Presenter<ChangeInviterContract.View> {

    private Context mContext;
    private ChangeInviterContract.View mView;
    private ChangeInviterContract.Model mModel;

    public ChangeInviterPresenterImp(Context mContext, ChangeInviterContract.View mView) {
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
     * 获取邀请人信息
     *
     * @param user_id
     */
    public void getInviteInfo(String user_id) {
        mView.showDialog();
        Observable<InviteDataBean> observable = Api.getInstance().service.getInviteInfo(user_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<InviteDataBean>() {
                    @Override
                    public void accept(InviteDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.getInviteInfoSuccess(responseBean);
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
     * 搜索邀请人
     *
     * @param user_id
     */
    public void SearchInvite(String user_id, String phone) {
        mView.showDialog();
        Observable<InviteDataBean> observable = Api.getInstance().service.SearchInvite(user_id,
                phone, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<InviteDataBean>() {
                    @Override
                    public void accept(InviteDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.SearchInviteSuccess(responseBean);
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
     * 变更邀请人操作
     *
     * @param user_id
     */
    public void changeInvite(String user_id, String code, String commend_user_id) {
        mView.showDialog();
        Observable<EditListDataBean> observable = Api.getInstance().service.changeInvite(user_id, code,
                commend_user_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditListDataBean>() {
                    @Override
                    public void accept(EditListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ChangeInviteSuccess(responseBean);
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
