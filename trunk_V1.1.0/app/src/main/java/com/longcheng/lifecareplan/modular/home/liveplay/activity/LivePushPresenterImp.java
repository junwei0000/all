package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Context;
import android.util.Log;

import com.longcheng.lifecareplan.apiLive.ApiLive;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
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
public class LivePushPresenterImp<T> extends LivePushContract.Presenter<LivePushContract.View> {

    private Context mContext;
    private LivePushContract.View mView;
    private LivePushContract.Model mModel;

    public LivePushPresenterImp(Context mContext, LivePushContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 获取推流数据
     */

    public void getLivePush() {
        mView.showDialog();
        Observable<LivePushDataInfo> observable = ApiLive.getInstance().service.getLivePush(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LivePushDataInfo>() {
                    @Override
                    public void accept(LivePushDataInfo responseBean) throws Exception {
                        mView.dismissDialog();
                        Log.d("getLivePush",""+responseBean.getPushurl());
                        mView.BackPushSuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                        Log.d("getLivePush",""+throwable.toString());
                    }
                });

    }
    /**
     * 获取播流数据
     */

    public void getLivePlay() {
        mView.showDialog();
        Observable<LivePushDataInfo> observable = ApiLive.getInstance().service.getLivePlay(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LivePushDataInfo>() {
                    @Override
                    public void accept(LivePushDataInfo responseBean) throws Exception {
                        mView.dismissDialog();
                        Log.d("getLivePush",""+responseBean.getPushurl());
                        mView.BackPlaySuccess(responseBean);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.dismissDialog();
                        mView.Error();
                        Log.d("getLivePush",""+throwable.toString());
                    }
                });

    }
}
