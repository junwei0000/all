package com.longcheng.lifecareplan.modular.home.liveplay.mine.activity;

import android.content.Context;

import com.longcheng.lifecareplan.apiLive.ApiLive;
import com.longcheng.lifecareplan.http.api.DefaultBackObserver;
import com.longcheng.lifecareplan.http.api.DefaultObserver;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MineItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 作者：MarkShuai
 * 时间：2017/12/8 16:08
 * 邮箱：MarkShuai@163.com
 * 意图：
 */
public class MyPresenterImp<T> extends MyContract.Presenter<MyContract.View> {

    private RxAppCompatActivity mContext;
    private MyContract.View mView;
    private MyContract.Model mModel;

    public MyPresenterImp(Context mContext, MyContract.View mView) {
        this.mContext = (RxAppCompatActivity) mContext;
        this.mView = mView;
    }

    @Override
    public void fetch() {
    }

    /**
     * 获取我的页面信息
     */
    public void getMineInfo(String user_id) {
        mView.showDialog();
        ApiLive.getInstance().service.getMineInfo(user_id)
                .compose(mContext.<BasicResponse<MineItemInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<MineItemInfo>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<MineItemInfo> response) {
                        mView.dismissDialog();
                        mView.getMineInfoSuccess(response);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     * 修改个性签名
     */
    public void updateShowTitle(String changeShowTitle) {
        mView.showDialog();
        ApiLive.getInstance().service.updateShowTitle(UserUtils.getUserId(mContext), changeShowTitle)
                .compose(mContext.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        mView.dismissDialog();
                        mView.updateShowTitleSuccess(response);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }

    /**
     * 我的视频
     */
    public void getMineVideoList(String user_id,int page, int page_size) {
        mView.showDialog();
        ApiLive.getInstance().service.getMineVideoList(user_id, page, page_size)
                .compose(mContext.<BasicResponse<MVideoDataInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultBackObserver<BasicResponse<MVideoDataInfo>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<MVideoDataInfo> response) {
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

    /**
     * 我的直播
     */
    public void getMineLiveList(String video_user_id,int page, int page_size) {
        mView.showDialog();
        ApiLive.getInstance().service.getMineLiveList(video_user_id, page, page_size)
                .compose(mContext.<BasicResponse<MVideoDataInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultBackObserver<BasicResponse<MVideoDataInfo>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<MVideoDataInfo> response) {
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

    /**
     * 我的关注
     */
    public void getMineFollowList(String video_user_id,int page, int page_size) {
        mView.showDialog();
        ApiLive.getInstance().service.getMineFollowList(video_user_id, page, page_size)
                .compose(mContext.<BasicResponse<MVideoDataInfo>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultBackObserver<BasicResponse<MVideoDataInfo>>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse<MVideoDataInfo> response) {
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

    /**
     * 直播- 取消关注
     */
    public void setCancelFollowLive(String follow_user_id) {
        mView.showDialog();
        ApiLive.getInstance().service.setCancelFollowLive(UserUtils.getUserId(mContext), follow_user_id)
                .compose(mContext.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(mContext) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        mView.dismissDialog();
                        mView.cancelFollowSuccess(response);
                    }

                    @Override
                    public void onError() {
                        mView.dismissDialog();
                        mView.Error();
                    }
                });
    }
}
