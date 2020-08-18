package com.longcheng.lifecareplan.modular.helpwith.reportbless.blesscircle;

import android.content.Context;

import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.FQDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.MessageListDataBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportDataBean;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */
public class MyCirclePresenterImp<T> extends MyCircleContract.Presenter<MyCircleContract.View> {

    private RxAppCompatActivity mContext;
    private MyCircleContract.View mView;

    public MyCirclePresenterImp(Context mContext, MyCircleContract.View mView) {
        this.mContext = (RxAppCompatActivity) mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     *
     */
    public void getMyCircleList() {
        mView.showDialog();
        Observable<ReportDataBean> observable = Api.getInstance().service.getMyCircleList(UserUtils.getUserId(mContext),
               ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<ReportDataBean>() {
                    @Override
                    public void accept(ReportDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.ListSuccess(responseBean, 1);
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
     * type:1, // 1私聊，2 群聊
     */
    public void getMyMessageList(int type) {
        mView.showDialog();
        Observable<MessageListDataBean> observable = Api.getInstance().service.getMyMessageCircleList(UserUtils.getUserId(mContext),
                type,  ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<MessageListDataBean>() {
                    @Override
                    public void accept(MessageListDataBean responseBean) throws Exception {
                        mView.dismissDialog();
                        if (!UserLoginBack403Utils.getInstance().login499Or500(responseBean.getStatus()))
                            mView.MessageListSuccess(responseBean);
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
