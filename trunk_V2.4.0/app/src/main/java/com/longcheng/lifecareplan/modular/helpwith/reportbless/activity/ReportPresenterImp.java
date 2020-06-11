package com.longcheng.lifecareplan.modular.helpwith.reportbless.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.apiLive.ApiLive;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.http.api.DefaultBackObserver;
import com.longcheng.lifecareplan.http.api.DefaultObserver;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderListDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */
public class ReportPresenterImp<T> extends ReportContract.Presenter<ReportContract.View> {

    private RxAppCompatActivity mContext;
    private ReportContract.View mView;

    public ReportPresenterImp(Context mContext, ReportContract.View mView) {
        this.mContext = (RxAppCompatActivity) mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 福券
     */
    public void getFuQuanList(String user_id, int type, int page, int page_size) {
        mView.showDialog();
        Observable<ReportDataBean> observable;
        if (type == 0) {
            observable = Api.getInstance().service.getFuQuanByJieQi(user_id,
                    page, page_size, ExampleApplication.token);
        } else {
            observable = Api.getInstance().service.getFuQuanByDay(user_id,
                    page, page_size, ExampleApplication.token);
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ReportDataBean>() {
                    @Override
                    public void accept(ReportDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    /**
     * 福气
     */
    public void getFuQiList(String user_id, int type, int page, int page_size) {
        mView.showDialog();
        Observable<ReportDataBean> observable;
        if (type == 0) {
            observable = Api.getInstance().service.getFuQiByReceive(user_id,
                    page, page_size, ExampleApplication.token);
        } else {
            observable = Api.getInstance().service.getFuQiBySponsor(user_id,
                    page, page_size, ExampleApplication.token);
        }

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ReportDataBean>() {
                    @Override
                    public void accept(ReportDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    /**
     * 福利
     */
    public void getFuLiList(String user_id, int type, int page, int page_size) {
        mView.showDialog();
        Observable<ReportDataBean> observable = Api.getInstance().service.getFuLiList(user_id,
                page, page_size, type, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ReportDataBean>() {
                    @Override
                    public void accept(ReportDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, page);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }

    /**
     * 立即领取 福利  未领页面点击领取-提现
     */
    public void getLingQu(String user_id, String bless_id) {
        mView.showDialog();
        Observable<EditDataBean> observable = Api.getInstance().service.getLingQu(user_id,
                bless_id, ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<EditDataBean>() {
                    @Override
                    public void accept(EditDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.lingQuSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });

    }
}
