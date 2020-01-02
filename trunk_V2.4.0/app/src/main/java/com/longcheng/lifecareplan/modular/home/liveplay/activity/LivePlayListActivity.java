package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.PlayListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MineActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.ShortVideoActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.TCVideoDetailNewActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 直播列表
 */
public class LivePlayListActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.play_view)
    PullToRefreshGridView playView;
    @BindView(R.id.layout_notlive)
    LinearLayout layout_notlive;
    @BindView(R.id.tv_playlist_video)
    TextView tvPlaylistVideo;
    @BindView(R.id.tv_playlist_video_line)
    TextView tvPlaylistVideoLine;
    @BindView(R.id.layout_playlist_video)
    LinearLayout layoutPlaylistVideo;
    @BindView(R.id.tv_playlist_live)
    TextView tvPlaylistLive;
    @BindView(R.id.tv_playlist_live_line)
    TextView tvPlaylistLiveLine;
    @BindView(R.id.layout_playlist_live)
    LinearLayout layoutPlaylistLive;
    @BindView(R.id.layout_playlist_mine)
    LinearLayout layoutPlaylistMine;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;

    /**
     * 是否选中直播
     */
    private boolean liveSeleStatus = false;
    private int page = 0;
    private int pageSize = 20;
    boolean refreshStatus = false;
    ArrayList<VideoItemInfo> mAllList = new ArrayList<>();
    PlayListAdapter mAdapter;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.pagetop_layout_rigth:
                intent = new Intent(mActivity, ShortVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.layout_playlist_video:
                liveSeleStatus = false;
                changeData();
                break;
            case R.id.layout_playlist_live:
                liveSeleStatus = true;
                changeData();
                break;
            case R.id.layout_playlist_mine:
                intent = new Intent(mActivity, MineActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_play_list;
    }


    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }


    @Override
    public void setListener() {
        layout_notlive.setVisibility(View.GONE);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        layoutPlaylistVideo.setOnClickListener(this);
        layoutPlaylistLive.setOnClickListener(this);
        layoutPlaylistMine.setOnClickListener(this);
        playView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                refreshStatus = true;
                getList(1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                refreshStatus = true;
                getList(page + 1);
            }
        });
        playView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAllList != null && mAllList.size() > 0) {
                    Intent intent;
                    if (liveSeleStatus) {
                        intent = new Intent(mActivity, LivePlayActivity.class);
                        intent.putExtra("live_room_id", mAllList.get(position).getVideo_id());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    } else {
//                        TCVideoDetailActivity.skipVideoDetail(mActivity, mAllList.get(position).getCover_url(),
//                                mAllList.get(position).getVideo_url(), mAllList.get(position).getVideo_id());
                        TCVideoDetailNewActivity.skipVideoDetail(mActivity, mAllList, position);
                    }

                }
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("Observable", "onNewINtent执行了");
        setIntent(intent);
        liveSeleStatus = false;
        changeData();
    }


    @Override
    public void initDataAfter() {
        liveSeleStatus = false;
        changeData();
    }

    private void changeData() {
        tvPlaylistVideoLine.setBackgroundResource(R.drawable.corners_bg_blackxingji);
        tvPlaylistVideo.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        tvPlaylistLiveLine.setBackgroundResource(R.drawable.corners_bg_blackxingji);
        tvPlaylistLive.setTextColor(getResources().getColor(R.color.text_biaoTi_color));
        if (liveSeleStatus) {
            tvPlaylistVideoLine.setVisibility(View.INVISIBLE);
            tvPlaylistLiveLine.setVisibility(View.VISIBLE);
            tvPlaylistLiveLine.setBackgroundResource(R.drawable.corners_bg_red);
            tvPlaylistLive.setTextColor(getResources().getColor(R.color.red));
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvPlaylistLiveLine, R.color.red);
        } else {
            tvPlaylistVideoLine.setVisibility(View.VISIBLE);
            tvPlaylistLiveLine.setVisibility(View.INVISIBLE);
            tvPlaylistVideoLine.setBackgroundResource(R.drawable.corners_bg_red);
            tvPlaylistVideo.setTextColor(getResources().getColor(R.color.red));
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvPlaylistVideoLine, R.color.red);
        }
        getList(1);
    }

    private void getList(int page) {
        if (liveSeleStatus) {
            mPresent.getLivePlayList(page, pageSize);
        } else {
            mPresent.getVideoPlayList(page, pageSize);
        }
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mRxAppCompatActivity, this);
    }


    @Override
    public void showDialog() {
        if (!refreshStatus)
            LoadingDialogAnim.show(mContext);
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
    public void dismissDialog() {
        refreshStatus = false;
        LoadingDialogAnim.dismiss(mContext);
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

    @Override
    public void BackLiveListSuccess(BasicResponse<VideoDataInfo> responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteG(playView);
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            VideoDataInfo mVideoDataInfo = responseBean.getData();
            if (mVideoDataInfo != null) {
                ArrayList<VideoItemInfo> mList = mVideoDataInfo.getLiveRoomList();
                int size = mList == null ? 0 : mList.size();
                if (backPage == 1) {
                    mAllList.clear();
                    mAdapter = null;
//            showNoMoreData(false);
                }
                if (size > 0) {
                    mAllList.addAll(mList);
                }
                if (mAdapter == null) {
                    mAdapter = new PlayListAdapter(mContext, mList, liveSeleStatus);
                    playView.setAdapter(mAdapter);
                } else {
                    mAdapter.reloadListView(mList, false);
                }
                page = backPage;
                checkLoadOver(size);
            }
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
        ListUtils.getInstance().setNotDateViewL(mAdapter, layout_notlive);
    }

    @Override
    public void BackVideoListSuccess(BasicResponse<ArrayList<VideoItemInfo>> responseBean, int backPage) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            ArrayList<VideoItemInfo> mList = responseBean.getData();
            int size = mList == null ? 0 : mList.size();
            if (backPage == 1) {
                mAllList.clear();
                mAdapter = null;
//            showNoMoreData(false);
            }
            if (size > 0) {
                mAllList.addAll(mList);
            }
            if (mAdapter == null) {
                mAdapter = new PlayListAdapter(mContext, mList, liveSeleStatus);
                playView.setAdapter(mAdapter);
            } else {
                mAdapter.reloadListView(mList, false);
            }
            page = backPage;
            checkLoadOver(size);
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
        RefreshComplete();
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
    public void videoDetailSuccess(BasicResponse<MVideoItemInfo> responseBean) {

    }

    @Override
    public void Error() {
        RefreshComplete();
        checkLoadOver(0);
    }

    private void RefreshComplete() {
        ListUtils.getInstance().RefreshCompleteG(playView);
        ListUtils.getInstance().setNotDateViewL(mAdapter, layout_notlive);
    }

    private void checkLoadOver(int size) {
        if (size < pageSize) {
            ScrowUtil.gridViewDownConfig(playView);
            if (size > 0 || (page > 1 && size >= 0)) {
//                showNoMoreData(true);
            }
        } else {
            ScrowUtil.gridViewConfigAll(playView);
        }
    }

    /**
     * @param flag true:显示footer；false 不显示footer
     */
    public void showNoMoreData(boolean flag) {
        if (layout_notlive != null) {
            if (flag) {
                layout_notlive.setVisibility(View.VISIBLE);
            } else {
                layout_notlive.setVisibility(View.GONE);
            }
        }
    }

    private void back() {
        doFinish();
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            back();
        }
        return false;
    }
}
