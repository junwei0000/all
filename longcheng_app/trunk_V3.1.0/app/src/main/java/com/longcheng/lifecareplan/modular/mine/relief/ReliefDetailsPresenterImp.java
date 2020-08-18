package com.longcheng.lifecareplan.modular.mine.relief;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.mine.emergencys.EmergencysPayBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReliefDetailsPresenterImp<T> extends ReliefDetailsContract.Presenter<ReliefDetailsContract.View> {

    private Context mContext;
    private ReliefDetailsContract.View mView;

    public ReliefDetailsPresenterImp(Context mContext, ReliefDetailsContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }


    @Override
    public void fetch() {

    }


    @Override
    public void getDetailsTop(String checkUid) {
        mView.showDialog();
        String user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        String token = ExampleApplication.token;
        Observable<ReliefDetailsBean> observable = Api.getInstance().service.getReliefDetailsTop(user_id, token, checkUid);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    mView.dismissDialog();
                    if (responseBean != null) {

                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            mView.getReliefDetailSuccess(responseBean.data);
                        }
                    }
                    Log.e("Observable", "" + responseBean.toString());
                }, throwable -> {
                    mView.dismissDialog();
//                    mView.error();
                });
    }

    @Override
    public void getDetailsList(String checkUid, String page) {
        mView.showDialog();
        String user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        String token = ExampleApplication.token;
        Observable<ReliefDetailsListBean> observable = Api.getInstance().service.getReliefListDetails(user_id, token, checkUid, page);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    mView.dismissDialog();
                    if (responseBean != null) {
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            mView.getListSuccess(responseBean.data, page);

                        }
                    }

                }, throwable -> {
                    mView.dismissDialog();
//                    mView.error();
                });
    }

    public void helpneed_pay(int help_need_id, int super_ability) {
        mView.showDialog();
        Observable<EmergencysPayBean> observable = Api.getInstance().service.helpneed_pay(UserUtils.getUserId(mContext), help_need_id, super_ability, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    mView.dismissDialog();
//                        mView.pay(responseBean);

                }, throwable -> {
                    mView.dismissDialog();

                    Log.e("Observable", throwable.toString());
                });

    }
}

