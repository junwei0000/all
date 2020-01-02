package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushContract;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.widget.Immersive;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

/**
 * Created by hans on 2017/12/5.
 */
public class TCVideoDetailNewActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View, ITXVodPlayListener {
    private static final String TAG = "TCVodPlayerActivity";
    @BindView(R.id.vertical_view_pager)
    VerticalViewPager mVerticalViewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;

    // 发布者id 、视频地址、 发布者名称、 头像URL、 封面URL
    private ArrayList<VideoItemInfo> mTCLiveInfoList;
    private int mInitTCLiveInfoPosition;

    VideoDownLoadUtils mVideoDownLoadUtils;
    int mVideoDuration;
    String show_video_id = "";
    String clickType = "";


    /**
     * SDK播放器以及配置
     */
    private TXVodPlayer mTXVodPlayer;
    private MyPagerAdapter mPagerAdapter;
    private TXCloudVideoView mTXCloudVideoView;
    private ImageView mIvCover;
    ImageButton record_preview;
    TextView frag_tv_dianzannum, frag_tv_sharenum;
    ImageView iv_dianzan;
    private int mCurrentPosition;

    class PlayerInfo {
        public TXVodPlayer txVodPlayer;
        public String playURL;
        public boolean isBegin;
        public View playerView;
        public int pos;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.layout_left) {
            doFinish();
        } else if (id == R.id.record_preview) {
            switchPlay();
        } else if (id == R.id.frag_iv_dashuang) {
            Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("html_url", "" + Config.BASE_HEAD_URL + "home/reward/pay/userRewardVideoId/1/isAppVideo/1");
            startActivity(intent);
        } else if (id == R.id.frag_layout_dianzan) {
            VideoItemInfo mMVideoItemInfo = (VideoItemInfo) view.getTag();
            int is_follow = mMVideoItemInfo.getIs_follow();
            String show_video_id = mMVideoItemInfo.getVideo_id();
            int type;
            if (is_follow == 0) {
                type = 1;
            } else {
                type = 2;
            }
            clickType = "dianzan";
            mPresent.addVideoFollow(show_video_id, type);
        } else if (id == R.id.frag_layout_comment) {
//            showCommentDialog();
        } else if (id == R.id.frag_layout_share) {
            VideoItemInfo mMVideoItemInfo = (VideoItemInfo) view.getTag();
            if (mVideoDownLoadUtils == null) {
                mVideoDownLoadUtils = new VideoDownLoadUtils(mActivity, mHandler, download);
            }
            if (!mVideoDownLoadUtils.loading) {
                clickType = "DOWNLOAD";
                show_video_id = mMVideoItemInfo.getVideo_id();
                mVideoDownLoadUtils.setInit(mMVideoItemInfo.getVideo_url(), mMVideoItemInfo.getCover_url(), mVideoDuration);
                mVideoDownLoadUtils.DOWNLOAD();
            } else {
                ToastUtils.showToast("视频下载中");
            }
        }
    }

    protected final int download = 7;
    protected static final int followItem = 8;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case download:
                    mVideoDownLoadUtils.initCommonContentValues();
                    mPresent.addForwardNum(show_video_id);
                    break;
                case followItem:
                    int is_follow = msg.arg1;
                    String short_video_comment_id = (String) msg.obj;
                    int type;
                    if (is_follow == 0) {
                        type = 1;
                    } else {
                        type = 2;
                    }
                    mPresent.addFollowItem(short_video_comment_id, type);
                    break;
            }
        }
    };

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_shortvideo_detailnew;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
    }

    public static void skipVideoDetail(Activity mActivity, ArrayList<VideoItemInfo> mTCLiveInfoList, int selectPosition) {
        Intent intent = new Intent(mActivity, TCVideoDetailNewActivity.class);
        intent.putExtra(TCConstants.TCLIVE_INFO_LIST, mTCLiveInfoList);
        intent.putExtra(TCConstants.TCLIVE_INFO_POSITION, selectPosition);
        mActivity.startActivity(intent);
    }

    @Override
    public void initDataAfter() {
        initDatas();
        initViews();
    }

    private void initDatas() {
        Intent intent = getIntent();
        mTCLiveInfoList = (ArrayList<VideoItemInfo>) intent.getSerializableExtra(TCConstants.TCLIVE_INFO_LIST);
        mInitTCLiveInfoPosition = intent.getIntExtra(TCConstants.TCLIVE_INFO_POSITION, 0);
    }

    private void initViews() {
        mVerticalViewPager.setOffscreenPageLimit(2);
        mVerticalViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                TXLog.i(TAG, "mVerticalViewPager, onPageScrolled position = " + position);
//                mCurrentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                TXLog.i(TAG, "mVerticalViewPager, onPageSelected position = " + position);
                mCurrentPosition = position;
                // 滑动界面，首先让之前的播放器暂停，并seek到0
                TXLog.i(TAG, "滑动后，让之前的播放器暂停，mTXVodPlayer = " + mTXVodPlayer);
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.seek(0);
                    mTXVodPlayer.pause();
                }
                if (position == mTCLiveInfoList.size() - 1) {
                    mTCLiveInfoList.addAll(mTCLiveInfoList);
//                    mPagerAdapter.instantiatePlayerInfo(0);
                    mPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mVerticalViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                TXLog.i(TAG, "mVerticalViewPager, transformPage pisition = " + position + " mCurrentPosition" + mCurrentPosition);
                if (position != 0) {
                    return;
                }
                ViewGroup viewGroup = (ViewGroup) page;
                mTXCloudVideoView = (TXCloudVideoView) viewGroup.findViewById(R.id.video_view);
                mIvCover = (ImageView) viewGroup.findViewById(R.id.player_iv_cover);
                record_preview = (ImageButton) viewGroup.findViewById(R.id.record_preview);
                iv_dianzan = (ImageView) viewGroup.findViewById(R.id.iv_dianzan);
                frag_tv_dianzannum = (TextView) viewGroup.findViewById(R.id.frag_tv_dianzannum);
                frag_tv_sharenum = (TextView) viewGroup.findViewById(R.id.frag_tv_sharenum);
                PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (playerInfo != null) {
                    playerInfo.txVodPlayer.resume();
                    record_preview.setImageResource(R.mipmap.record_pause);
                    record_preview.setImageResource(0);
                    mTXVodPlayer = playerInfo.txVodPlayer;
                }
            }
        });

        mPagerAdapter = new MyPagerAdapter();
        mVerticalViewPager.setAdapter(mPagerAdapter);
        mVerticalViewPager.setCurrentItem(mInitTCLiveInfoPosition);
    }

    class MyPagerAdapter extends PagerAdapter {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();

        public PlayerInfo instantiatePlayerInfo(int position) {
            TXCLog.d(TAG, "instantiatePlayerInfo " + position);

            PlayerInfo playerInfo = new PlayerInfo();
            TXVodPlayer vodPlayer = new TXVodPlayer(mContext);
            vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
            vodPlayer.setVodListener(TCVideoDetailNewActivity.this);
            TXVodPlayConfig config = new TXVodPlayConfig();
            config.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() + "/longcheng_txcache");
            config.setMaxCacheItems(5);
            vodPlayer.setConfig(config);
            vodPlayer.setAutoPlay(false);

            VideoItemInfo tcLiveInfo = mTCLiveInfoList.get(position);
            playerInfo.playURL = tcLiveInfo.getVideo_url();
            playerInfo.txVodPlayer = vodPlayer;
            playerInfo.pos = position;
            playerInfoList.add(playerInfo);

            return playerInfo;
        }

        protected void destroyPlayerInfo(int position) {
            while (true) {
                PlayerInfo playerInfo = findPlayerInfo(position);
                if (playerInfo == null)
                    break;
                playerInfo.txVodPlayer.stopPlay(true);
                playerInfoList.remove(playerInfo);

                TXCLog.d(TAG, "destroyPlayerInfo " + position);
            }
        }

        public PlayerInfo findPlayerInfo(int position) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.pos == position) {
                    return playerInfo;
                }
            }
            return null;
        }

        public PlayerInfo findPlayerInfo(TXVodPlayer player) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.txVodPlayer == player) {
                    return playerInfo;
                }
            }
            return null;
        }

        public void onDestroy() {
            for (PlayerInfo playerInfo : playerInfoList) {
                playerInfo.txVodPlayer.stopPlay(true);
            }
            playerInfoList.clear();
        }

        @Override
        public int getCount() {
            return mTCLiveInfoList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.live_shortvideo_detailnew_item, null);
            view.setId(position);
            ImageButton record_preview = (ImageButton) view.findViewById(R.id.record_preview);
            ImageView player_iv_cover = (ImageView) view.findViewById(R.id.player_iv_cover);
            ImageView iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_cont = (TextView) view.findViewById(R.id.tv_cont);

            ImageView frag_iv_dashuang = (ImageView) view.findViewById(R.id.frag_iv_dashuang);
            LinearLayout frag_layout_dianzan = (LinearLayout) view.findViewById(R.id.frag_layout_dianzan);
            ImageView iv_dianzan = (ImageView) view.findViewById(R.id.iv_dianzan);
            TextView frag_tv_dianzannum = (TextView) view.findViewById(R.id.frag_tv_dianzannum);
            LinearLayout frag_layout_comment = (LinearLayout) view.findViewById(R.id.frag_layout_comment);
            TextView frag_tv_commentnum = (TextView) view.findViewById(R.id.frag_tv_commentnum);
            LinearLayout frag_layout_share = (LinearLayout) view.findViewById(R.id.frag_layout_share);
            TextView frag_tv_sharenum = (TextView) view.findViewById(R.id.frag_tv_sharenum);
            VideoItemInfo mMVideoItemInfo = mTCLiveInfoList.get(position);

            frag_iv_dashuang.setTag(mMVideoItemInfo);
            frag_layout_dianzan.setTag(mMVideoItemInfo);
            frag_layout_comment.setTag(mMVideoItemInfo);
            frag_layout_share.setTag(mMVideoItemInfo);
            record_preview.setOnClickListener(TCVideoDetailNewActivity.this);
            frag_iv_dashuang.setOnClickListener(TCVideoDetailNewActivity.this);
            frag_layout_dianzan.setOnClickListener(TCVideoDetailNewActivity.this);
            frag_layout_comment.setOnClickListener(TCVideoDetailNewActivity.this);
            frag_layout_share.setOnClickListener(TCVideoDetailNewActivity.this);

            tv_name.setText("" + mMVideoItemInfo.getUser_name());
            tv_cont.setText("" + mMVideoItemInfo.getContent());
            frag_tv_dianzannum.setText("" + mMVideoItemInfo.getTotal_number());
            frag_tv_commentnum.setText("" + mMVideoItemInfo.getComment_number());
            frag_tv_sharenum.setText("" + mMVideoItemInfo.getForward_number());
            GlideDownLoadImage.getInstance().loadCircleImage(mMVideoItemInfo.getAvatar(), iv_avatar);
            Glide.with(mContext).load(Uri.fromFile(new File(mMVideoItemInfo.getCover_url())))
                    .into(player_iv_cover);
            record_preview.setImageResource(R.mipmap.record_pause);
            record_preview.setImageResource(0);
            int is_follow = mMVideoItemInfo.getIs_follow();
            if (is_follow == 0) {
                iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_bai);
            } else {
                iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_hong1);
            }

            // 获取此player
            TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.video_view);
            PlayerInfo playerInfo = instantiatePlayerInfo(position);
            playerInfo.playerView = playView;
            playerInfo.txVodPlayer.setPlayerView(playView);
            playerInfo.txVodPlayer.startPlay(playerInfo.playURL);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            TXCLog.i(TAG, "MyPagerAdapter destroyItem, position = " + position);

            destroyPlayerInfo(position);

            container.removeView((View) object);
        }
    }

    boolean mVideoPause = false;

    private void switchPlay() {
        if (mVideoPause) {
            mTXVodPlayer.resume();
            record_preview.setImageResource(R.mipmap.record_pause);
            record_preview.setImageResource(0);
            mVideoPause = false;
        } else {
            mTXVodPlayer.pause();
            record_preview.setImageResource(R.mipmap.record_start);
            mVideoPause = true;
        }
    }

    private void restartPlay() {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
            record_preview.setImageResource(R.mipmap.record_pause);
            record_preview.setImageResource(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onResume();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.resume();
            record_preview.setImageResource(R.mipmap.record_pause);
            record_preview.setImageResource(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onPause();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.pause();
            record_preview.setImageResource(R.mipmap.record_start);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
            mTXCloudVideoView = null;
        }
        mPagerAdapter.onDestroy();
        stopPlay(true);
        mTXVodPlayer = null;
    }


    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.stopPlay(clearLastFrame);
        }
    }

    @Override
    public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
        if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
            ToastUtils.showToast(R.string.net_tishi);
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
            restartPlay();
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
            int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
            mVideoDuration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION) * 1000;//单位为s
        } else if (event == TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME) {// 视频I帧到达，开始播放

            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null) {
                playerInfo.isBegin = true;
            }
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event I FRAME, player = " + player);
                mIvCover.setVisibility(View.GONE);
            }
        } else if (event == TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED) {
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);
                restartPlay();
            }
        } else if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
            PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(player);
            if (playerInfo != null && playerInfo.isBegin) {
                mIvCover.setVisibility(View.GONE);
                TXCLog.i(TAG, "onPlayEvent, event begin, cover remove");
            }
            if (player.getHeight() > player.getWidth() * 1.5) {
                Log.e("onPlayEvent", "" + player.getDuration());
                mTXVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
            }
        } else if (event < 0) {
            if (mTXVodPlayer == player) {
                TXLog.i(TAG, "onPlayEvent, event prepared, player = " + player);

                String desc = null;
                switch (event) {
                    case TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL:
                        desc = "获取加速拉流地址失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_FILE_NOT_FOUND:
                        desc = "文件不存在";
                        break;
                    case TXLiveConstants.PLAY_ERR_HEVC_DECODE_FAIL:
                        desc = "h265解码失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_HLS_KEY:
                        desc = "HLS解密key获取失败";
                        break;
                    case TXLiveConstants.PLAY_ERR_GET_PLAYINFO_FAIL:
                        desc = "获取点播文件信息失败";
                        break;
                }
            }
            Toast.makeText(this, "event:" + event, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer player, Bundle status) {

    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mRxAppCompatActivity, this);
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

    }

    @Override
    public void BackVideoListSuccess(BasicResponse<ArrayList<VideoItemInfo>> responseBean, int backPage) {

    }

    @Override
    public void sendLCommentSuccess(BasicResponse responseBean) {
        if (mTCLiveInfoList != null && mTCLiveInfoList.size() > 0) {
            if (clickType.equals("dianzan")) {
                String Follow_number = mTCLiveInfoList.get(mCurrentPosition).getTotal_number();
                int is_follow = mTCLiveInfoList.get(mCurrentPosition).getIs_follow();
                String newnum;
                if (is_follow == 0) {
                    mTCLiveInfoList.get(mCurrentPosition).setIs_follow(1);
                    newnum = PriceUtils.getInstance().gteAddSumPrice(Follow_number, "1");
                } else {
                    mTCLiveInfoList.get(mCurrentPosition).setIs_follow(0);
                    newnum = PriceUtils.getInstance().gteSubtractSumPrice("1", Follow_number);
                }
                if (!TextUtils.isEmpty(newnum) && Integer.parseInt(newnum) < 0) {
                    newnum = "0";
                }
                mTCLiveInfoList.get(mCurrentPosition).setTotal_number(newnum);
                is_follow = mTCLiveInfoList.get(mCurrentPosition).getIs_follow();
                if (is_follow == 0) {
                    iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_bai);
                } else {
                    iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_hong1);
                }
                frag_tv_dianzannum.setText("" + newnum);
            } else if (clickType.equals("DOWNLOAD")) {
                String mForward_number = mTCLiveInfoList.get(mCurrentPosition).getForward_number();
                String mnum = PriceUtils.getInstance().gteAddSumPrice(mForward_number, "1");
                mTCLiveInfoList.get(mCurrentPosition).setForward_number(mnum);
                frag_tv_sharenum.setText(mnum);
            }
            mPagerAdapter.notifyDataSetChanged();
        }
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

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }
}
