package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.home.bangdan.BangDanDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EmergencyPresenterBangImp<T> extends EmergencysBangContract.Presenter<EmergencysBangContract.View> {
    private Context mContext;
    private EmergencysBangContract.View mView;

    @Override
    public void fetch() {

    }

    public EmergencyPresenterBangImp(Context mContext, EmergencysBangContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void blessCardRanking(String user_id,
                                 int page,
                                 int type,
                                 int page_size) {
        LogUtils.e("page" + page);
        mView.showDialog();
        Observable<EmergencyBangDanBean> observable = Api.getInstance().service.blessCardRanking(user_id,
                page, page_size, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EmergencyBangDanBean>() {
                    @Override
                    public void accept(EmergencyBangDanBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ListSuccess(responseBean);

                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                        Log.e("Observable", throwable.toString());
                    }
                });

    }
}
