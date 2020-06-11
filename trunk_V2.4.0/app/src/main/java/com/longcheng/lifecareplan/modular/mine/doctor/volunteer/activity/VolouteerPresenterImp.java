package com.longcheng.lifecareplan.modular.mine.doctor.volunteer.activity;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.DocRewardListDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.bean.RewardDataBean;
import com.longcheng.lifecareplan.modular.mine.doctor.volunteer.bean.AYRecordListDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class VolouteerPresenterImp<T> extends VolouteerContract.Presenter<VolouteerContract.View> {

    private Context mContext;
    private VolouteerContract.View mView;

    public VolouteerPresenterImp(Context mContext, VolouteerContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    public void getAYRecordList(int page, int page_size) {
        mView.showDialog();
        Observable<AYRecordListDataBean> observable = Api.getInstance().service.getAYRecordList(UserUtils.getUserId(mContext), page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<AYRecordListDataBean>() {
                    @Override
                    public void accept(AYRecordListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.AYRecordListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.ListError();
                    }
                });

    }

    public void getVolRewardList(int page, int page_size) {
        mView.showDialog();
        Observable<DocRewardListDataBean> observable = Api.getInstance().service.getVolRewardList(UserUtils.getUserId(mContext), page, page_size, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<DocRewardListDataBean>() {
                    @Override
                    public void accept(DocRewardListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
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

    public void volunteerAsset() {
        mView.showDialog();
        Observable<RewardDataBean> observable = Api.getInstance().service.volunteerAsset(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<RewardDataBean>() {
                    @Override
                    public void accept(RewardDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.GetRewardInfoSuccess(responseBean);
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
