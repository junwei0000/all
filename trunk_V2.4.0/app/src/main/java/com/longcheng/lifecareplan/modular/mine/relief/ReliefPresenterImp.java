package com.longcheng.lifecareplan.modular.mine.relief;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReliefPresenterImp<T> extends ReliefContract.Presenter<ReliefContract.View> {

    private Context mContext;
    private ReliefContract.View mView;

    public ReliefPresenterImp(Context mContext, ReliefContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }


    @Override
    public void fetch() {

    }

    @Override
    public void applyRelief() {
        mView.showDialog();
        String user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        String token = ExampleApplication.token;
        Observable<ApplyReliefBean> observable = Api.getInstance().service.applyRelief(user_id, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    mView.dismissDialog();
                    if (responseBean != null) {
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            mView.applySuccess();
                        } else {
                            Toast.makeText(mContext, responseBean.msg + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, throwable -> {
                    mView.dismissDialog();
//                    mView.error();
                });
    }

    @Override
    public void getReliefBottom() {
        mView.showDialog();
        String user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        String token = ExampleApplication.token;

        Observable<ReliefBottomInfoBean> observable = Api.getInstance().service.getReliefBottom(user_id, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    mView.dismissDialog();
                    if (responseBean != null) {
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            mView.bottomInfoSuccess(responseBean.data);

                        } else {
                            Toast.makeText(mContext, responseBean.msg + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Log.e("Observable", "" + responseBean.toString());
                }, throwable -> {
                    mView.dismissDialog();
//                    mView.error();
                });
    }
}
