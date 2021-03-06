package com.longcheng.lifecareplan.modular.home.liveplay.framgent;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.base.BaseRecyclerAdapter;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePlayActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushContract;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.LiveListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.decoration.SpacesItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2018/9/22 15:33
 * 意图：
 */

public class LiveFramgent extends BaseFragmentMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {
    @BindView(R.id.layout_notlive)
    LinearLayout layout_notlive;
    @BindView(R.id.notdata_iv_img)
    ImageView notdata_iv_img;
    @BindView(R.id.play_view)
    RecyclerView playView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private int page = 0;
    private int pageSize = 20;

    boolean isLoadingData = false;
    boolean isLoadBottom = true;

    @Override
    public int bindLayout() {
        return R.layout.live_hom_fram_live;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initView(View view) {
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        playView.setLayoutManager(layoutManager);
        int space = DensityUtil.dip2px(mContext, 3);
        playView.addItemDecoration(new SpacesItemDecoration(space));
//        playView.addItemDecoration(new DividerGridItemDecoration(getContext()));
        notdata_iv_img.setBackgroundResource(R.mipmap.live_quexing);
        layout_notlive.setBackgroundColor(mActivity.getResources().getColor(R.color.black_live));

        mSwipeRefreshLayout.setProgressViewOffset(false, 150, 220);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.black,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                Log.e("onPageSelected", "onRefresh=" + page);
                getList(1);
            }
        });
        playView.setOnScrollListener(new OnRcvScrollListener() {
            @Override
            public void onBottom() {
                super.onBottom();
                // 到底部自动加载
                Log.e("onPageSelected", "onBottom=" + page);
                if (isLoadBottom)
                    getList(page + 1);
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        getList(1);
    }

    @Override
    public void widgetClick(View v) {

    }

    private void getList(int page) {
        if (!isLoadingData) {
            isLoadingData = true;
            mPresent.getLivePlayList(page, pageSize);
        }
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mActivity, this);
    }


    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void upLoadVideoSuccess(BasicResponse responseBean) {

    }

    @Override
    public void backSignSuccess(BasicResponse<VideoGetSignatureInfo> responseBean) {

    }

    @Override
    public void setFollowLiveSuccess(BasicResponse responseBean) {

    }

    @Override
    public void applyLiveSuccess(BasicResponse responseBean) {

    }

    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {

    }

    @Override
    public void openRoomPaySuccess(BasicResponse<LiveStatusInfo> responseBean) {

    }

    @Override
    public void getUserLiveStatusSuccess(BasicResponse<LiveStatusInfo> responseBean) {

    }

    @Override
    public void BackLiveDetailSuccess(BasicResponse<LiveDetailInfo> responseBean) {

    }

    ArrayList<VideoItemInfo> mAllList = new ArrayList<>();
    LiveListAdapter mAdapter;

    @Override
    public void BackLiveListSuccess(BasicResponse<VideoDataInfo> responseBean, int backPage) {
        if (playView == null) {
            return;
        }
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            VideoDataInfo mVideoDataInfo = responseBean.getData();
            if (mVideoDataInfo != null) {
                ArrayList<VideoItemInfo> mList = mVideoDataInfo.getLiveRoomList();
                int size = mList == null ? 0 : mList.size();
                if (backPage == 1) {
                    mAllList.clear();
                    if (size == 0) {
                        mAdapter = null;
                    }
                }
                page = backPage;
                if (size > 0) {
                    isLoadBottom = true;
                    mAllList.addAll(mList);
                    if (size < pageSize) {
                        isLoadBottom = false;
                    }
                } else {
                    isLoadBottom = false;
                }
                if (mAdapter == null) {
                    mAdapter = new LiveListAdapter(getActivity(), mList);
                    playView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            try {
                                if (mAllList != null && mAllList.size() > 0) {
                                    Intent intent = new Intent(mActivity, LivePlayActivity.class);
                                    intent.putExtra("live_room_id", mAllList.get(position).getVideo_id());
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    if (backPage == 1) {
                        mAdapter.reloadListView(mList, true);
                    } else {
                        mAdapter.reloadListView(mList, false);
                    }
                }
                Log.e("onPageSelected", "page=" + page + "  mAllList=" + mAllList.size());
            }
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
        RefreshComplete();
    }

    @Override
    public void BackVideoListSuccess(BasicResponse<ArrayList<VideoItemInfo>> responseBean, int backPage) {

    }

    @Override
    public void BackMyVideoListSuccess(BasicResponse<MVideoDataInfo> responseBean, int back_page) {

    }

    @Override
    public void sendLCommentSuccess(BasicResponse responseBean) {

    }

    @Override
    public void sendVideoCommentSuccess(BasicResponse responseBean) {

    }

    @Override
    public void giveGiftSuccess(BasicResponse responseBean) {

    }

    @Override
    public void videoDetailSuccess(BasicResponse<MVideoItemInfo> responseBean, int backindex) {

    }

    @Override
    public void showGiftDialog() {

    }

    @Override
    public void videoDetCommentListSuccess(BasicResponse<ArrayList<MVideoItemInfo>> responseBean, int backPage) {

    }

    @Override
    public void CommentListError() {

    }

    @Override
    public void Error() {
        RefreshComplete();
    }

    private void RefreshComplete() {
        isLoadingData = false;
        mSwipeRefreshLayout.setRefreshing(false);
        ListUtils.getInstance().setNotDateViewR(mAdapter, layout_notlive);
    }
}
