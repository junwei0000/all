package com.longcheng.lifecareplan.modular.mine.relief;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FinishPresenterImp<T> extends FinishContract.Present<FinishContract.View> {

    private Context mContext;
    private FinishContract.View mView;

    public FinishPresenterImp(Context mContext, FinishContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void getLitsData(String user_id, String token, String page, String type, String keyworld) {
        mView.showDialog();

        Observable<ReliefItemBean> observable = Api.getInstance().service.getReliefList(user_id, token, page, type, keyworld);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    mView.dismissDialog();
                    if (responseBean != null) {

                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            if (responseBean.data != null && responseBean.data.size() > 0) {
                                mView.getReliefListSuccess(responseBean.data, page);
                            }
                        }
                    }
                    Log.e("Observable", "" + responseBean.toString());
                }, throwable -> {
                    mView.dismissDialog();
//                    mView.error();
                });
    }

    @Override
    public void getReliefBottom(String type) {
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
                            mView.bottomInfoSuccess(responseBean.data, type);

                        } else {
                            Toast.makeText(mContext, responseBean.msg + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Log.e("Observable", "" + responseBean.toString());
                }, throwable -> {
                    mView.dismissDialog();
                });
    }

    @Override
    public void extractEnergy() {
        mView.showDialog();
        String user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        String token = ExampleApplication.token;

        Observable<ReliefBottomInfoBean> observable = Api.getInstance().service.extractEnergy(user_id, token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBean -> {
                    mView.dismissDialog();
                    if (responseBean != null) {
                        if (!TextUtils.isEmpty(responseBean.status) && responseBean.status.equals("200")) {
                            ToastUtils.showToast(responseBean.msg);
                        } else {
                            Toast.makeText(mContext, responseBean.msg + "", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                    }
                    Log.e("Observable", "" + responseBean.toString());
                }, throwable -> {
                    mView.dismissDialog();
                });
    }

    @Override
    public void fetch() {

    }
}
