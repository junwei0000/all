package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import com.longcheng.lifecareplan.apiLive.ApiLive;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.http.api.DefaultObserver;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */
public class LivePushPresenterImp<T> extends LivePushContract.Presenter<LivePushContract.View> {

    private RxAppCompatActivity mContext;
    private LivePushContract.View mView;
    private LivePushContract.Model mModel;

    public LivePushPresenterImp(RxAppCompatActivity mContext, LivePushContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 获取推流数据
     */

    public void getLivePush(String uid) {
        mView.showDialog();
        ApiLive.getInstance().service.getLivePush(uid, ExampleApplication.token)
                .compose(mContext.<BasicResponse<LivePushDataInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LivePushDataInfo>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<LivePushDataInfo> response) {
                        mView.dismissDialog();
                        mView.BackPushSuccess(response);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }
    /**
     * 获取
     */

    public void getLivePlay(String uid) {
        mView.showDialog();
        ApiLive.getInstance().service.getLivePlay(uid, ExampleApplication.token)
                .compose(mContext.<BasicResponse<LivePushDataInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LivePushDataInfo>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<LivePushDataInfo> response) {
                        mView.dismissDialog();
                        mView.BackPlaySuccess(response);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }
}
