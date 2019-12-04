package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import com.longcheng.lifecareplan.apiLive.ApiLive;
import com.longcheng.lifecareplan.http.api.DefaultBackObserver;
import com.longcheng.lifecareplan.http.api.DefaultObserver;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.ArrayList;

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
     * 用户申请直播详情
     */
    public void getUserLiveStatus() {
        mView.showDialog();
        ApiLive.getInstance().service.getUserLiveStatus(UserUtils.getUserId(mContext))
                .compose(mContext.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultBackObserver<BasicResponse>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        mView.dismissDialog();
                        mView.getUserLiveStatusSuccess(response);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     * 获取直播间详情
     */
    public void getLivePlayInfo(String live_room_id) {
        mView.showDialog();
        ApiLive.getInstance().service.getLivePlayInfo(live_room_id)
                .compose(mContext.<BasicResponse<LiveDetailInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LiveDetailInfo>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<LiveDetailInfo> response) {
                        mView.dismissDialog();
                        mView.BackLiveDetailSuccess(response);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     * 获取直播列表
     */
    public void getLivePlayList(int page, int page_size) {
        mView.showDialog();
        ApiLive.getInstance().service.getLiveList(UserUtils.getUserId(mContext),
                page, page_size)
                .compose(mContext.<BasicResponse<VideoDataInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<VideoDataInfo>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<VideoDataInfo> response) {
                        mView.dismissDialog();
                        mView.BackLiveListSuccess(response, page);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     * 获取短视频列表
     */
    public void getVideoPlayList(int page, int page_size) {
        mView.showDialog();
        ApiLive.getInstance().service.getVideoList(UserUtils.getUserId(mContext),
                page, page_size)
                .compose(mContext.<BasicResponse<ArrayList<VideoItemInfo>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<ArrayList<VideoItemInfo>>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<ArrayList<VideoItemInfo>> response) {
                        mView.dismissDialog();
                        mView.BackVideoListSuccess(response, page);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }
}
