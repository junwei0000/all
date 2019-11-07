package com.longcheng.lifecareplan.modular.home.liveplay.mine.activity;

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
     * 获取直播列表
     */
    public void getLivePlayList() {
        mView.showDialog();
        Observable<LivePushDataInfo> observable = ApiLive.getInstance().service.getLivePlay(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LivePushDataInfo>() {
                    @Override
                    public void accept(LivePushDataInfo responseBean) throws Exception {
                        mView.dismissDialog();
                        Log.d("getLivePush",""+responseBean.getPushurl());
                        mView.BackPlayListSuccess(responseBean);
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
     * 获取播流列表
     */
    public void getVideoPlayList() {
        mView.showDialog();
        Observable<LivePushDataInfo> observable = ApiLive.getInstance().service.getLivePlay(UserUtils.getUserId(mContext), ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<LivePushDataInfo>() {
                    @Override
                    public void accept(LivePushDataInfo responseBean) throws Exception {
                        mView.dismissDialog();
                        Log.d("getLivePush",""+responseBean.getPushurl());
                        mView.BackVideoListSuccess(responseBean);
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
