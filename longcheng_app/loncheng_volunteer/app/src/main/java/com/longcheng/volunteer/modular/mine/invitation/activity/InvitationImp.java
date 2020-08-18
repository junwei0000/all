package com.longcheng.volunteer.modular.mine.invitation.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.volunteer.modular.mine.invitation.bean.InvitationResultBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/9/5.
 */

public class InvitationImp<T> extends InvitationContract.Presenter<InvitationContract.View> {
    private Context mContext;
    private InvitationContract.View mView;
    private InvitationContract.Model mModel;

    public InvitationImp(Context mContext, InvitationContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {

    }

    @Override
    void doRefresh(int page, int pageSize, String userId, String token) {
        mView.showDialog();
        Observable<InvitationResultBean> observable = Api.getInstance().service.getInvitation(userId, page, pageSize, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<InvitationResultBean>() {
                    @Override
                    public void accept(InvitationResultBean responseBean) throws Exception {
                        Log.e("aaa", "accept --> " + responseBean.toString());
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            if ("200".equals(responseBean.getStatus())) {
                                mView.onSuccess(responseBean, page);
                            } else {
                                mView.onError(responseBean.getMsg());
                            }

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.onError("");
                        Log.e("aaa", "accept --> " + throwable.toString());
                    }
                });
    }
}
