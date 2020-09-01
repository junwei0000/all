package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.fulist;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;
import com.longcheng.lifecareplan.bean.fupackage.MyFuListBean;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.bean.MyListDataBean;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.activity.RankCornucopiaContract;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.bean.RankListBean;
import com.longcheng.lifecareplan.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FuListPresenterImp<V> extends FuListContract.Presenter<FuListContract.View> {

    private Context mContext;
    private FuListContract.View mView;

    @Override
    public void fetch() {

    }

    public FuListPresenterImp(Context mContext, FuListContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    public void getFuList(String user_id,
                          int type,
                          int page,
                          int page_size) {
        mView.showDialog();
        Observable<MyFuListBean> observable = Api.getInstance().service.getFuPackageList(user_id, type,
                page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyFuListBean>() {
                    @Override
                    public void accept(MyFuListBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.ListSuccess(responseBean, page);

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
