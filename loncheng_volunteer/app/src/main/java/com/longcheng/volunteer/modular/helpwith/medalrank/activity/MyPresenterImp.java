package com.longcheng.volunteer.modular.helpwith.medalrank.activity;

import android.content.Context;

import com.longcheng.volunteer.api.Api;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.modular.helpwith.medalrank.bean.MyRankListDataBean;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginBack403Utils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyPresenterImp<T> extends MyContract.Presenter<MyContract.View> {

    private Context mContext;
    private MyContract.View mView;
    private MyContract.Model mModel;

    public MyPresenterImp(Context mContext, MyContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * @param user_id
     */
    public void getPersonalList(String user_id, int type, int page, int page_size, int sort) {
        mView.showDialog();
        Observable<MyRankListDataBean> observable = Api.getInstance().service.getMutualRankingList(user_id,
                type, page, page_size, sort, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyRankListDataBean>() {
                    @Override
                    public void accept(MyRankListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.PersonalListSuccess(responseBean, page);
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
     * @param user_id
     */
    public void getCommuneList(String user_id, int type, int page, int page_size, int sort) {
        mView.showDialog();
        Observable<MyRankListDataBean> observable = Api.getInstance().service.getMutualRankingList(user_id,
                type, page, page_size, sort, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MyRankListDataBean>() {
                    @Override
                    public void accept(MyRankListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        mView.CommuneListSuccess(responseBean, page);
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
