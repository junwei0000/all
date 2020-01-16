package com.longcheng.lifecareplan.modular.home.liveplay.framgent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.home.liveplay.VideoMenuActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushContract;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MineActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.VideoCommentAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.VideoDownLoadUtils;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
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
 *
 */
public class VideoFramgent extends BaseFragmentMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View, ITXVodPlayListener {
    private String TAG = "TCVodPlayerActivity";
    @BindView(R.id.vertical_view_pager)
    VerticalViewPager mVerticalViewPager;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_notlive)
    LinearLayout layout_notlive;
    @BindView(R.id.notdata_iv_img)
    ImageView notdata_iv_img;
    // 发布者id 、视频地址、 发布者名称、 头像URL、 封面URL
    private ArrayList<VideoItemInfo> mAllList = new ArrayList<>();

    private VideoDownLoadUtils mVideoDownLoadUtils;
    private int mVideoDuration;
    private String show_video_id = "";
    private String clickType = "";


    /**
     * SDK播放器以及配置
     */
    private TXVodPlayer mTXVodPlayer;
    private MyPagerAdapter mPagerAdapter;
    private TXCloudVideoView mTXCloudVideoView;
    private ImageView mIvCover;
    private ImageButton record_preview;
    private TextView frag_tv_dianzannum, frag_tv_commentnum, frag_tv_sharenum;
    private ImageView iv_dianzan;
    private ImageView iv_follow;
    private int mCurrentPosition;
    private int mdelPosition = -1;
    private String video_user_id;

    public String showPageType_video = "showPageType_video";
    public String showPageType_follow = "showPageType_follow";
    public String showPageType = "";
    UpLoadDialogUtils mUpLoadDialogUtils;

    class PlayerInfo {
        public TXVodPlayer txVodPlayer;
        public String playURL;
        public boolean isBegin;
        public View playerView;
        ImageButton record_preview;
        public ImageView iv_follow;
        public ImageView iv_dianzan;
        public TextView frag_tv_dianzannum;
        public int pos;
    }

    protected static final int startdownload = 4;
    protected static final int delVideo = 5;
    protected static final int shareVideo = 6;
    protected static final int downloaded = 7;
    public static final int followItem = 8;
    protected static final int addShareNum = 9;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case startdownload:
                    if (mVideoDownLoadUtils == null) {
                        mVideoDownLoadUtils = new VideoDownLoadUtils(mActivity, mHandler, downloaded);
                    }
                    if (!mVideoDownLoadUtils.loading) {
                        VideoItemInfo mMVideoItemInfo = mAllList.get(mCurrentPosition);
                        show_video_id = mMVideoItemInfo.getVideo_id();
                        mVideoDownLoadUtils.setInit(mMVideoItemInfo.getVideo_url(), mMVideoItemInfo.getCover_url(), mVideoDuration);
                        mVideoDownLoadUtils.DOWNLOAD();
                    } else {
                        ToastUtils.showToast("视频下载中");
                    }
                    break;
                case downloaded:
                    mVideoDownLoadUtils.initCommonContentValues();
                    mHandler.sendEmptyMessage(addShareNum);
                    break;
                case addShareNum:
                    clickType = "DOWNLOAD";
                    mPresent.addForwardNum(show_video_id);
                    break;
                case delVideo:
                    mdelPosition = mCurrentPosition;
                    show_video_id = mAllList.get(mCurrentPosition).getVideo_id();
                    mPresent.delVideo(show_video_id);
                    break;
                case shareVideo:
                    if (mUpLoadDialogUtils == null) {
                        mUpLoadDialogUtils = new UpLoadDialogUtils(mActivity, mHandler);
                    }
                    VideoItemInfo mMVideoItemInfo = mAllList.get(mCurrentPosition);
                    show_video_id = mMVideoItemInfo.getVideo_id();
                    String codeurl = Config.PAY_URL + "home/live/info/short_video_id/" + show_video_id +
                            "/publisher_user_id/" + mMVideoItemInfo.getUser_id() +
                            "/forwarder_user_id/" + UserUtils.getUserId(mContext);
                    mUpLoadDialogUtils.setShareDialog(mMVideoItemInfo.getCover_url(), mMVideoItemInfo.getUser_name(), mMVideoItemInfo.getTitle(), codeurl);
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
    public int bindLayout() {
        return R.layout.live_hom_fram_video;
    }

    @Override
    public void initView(View view) {
    }

    public VideoItemInfo getCurrentInfo() {
        if (mAllList != null && mAllList.size() > 0) {
            return mAllList.get(mCurrentPosition);
        } else {
            return null;
        }
    }

    @Override
    public void widgetClick(View v) {
        int id = v.getId();
        if (id == R.id.record_preview) {
            switchPlay();
        } else if (id == R.id.frag_iv_dashuang) {
            VideoItemInfo mMVideoItemInfo = mAllList.get(mCurrentPosition);
            show_video_id = mMVideoItemInfo.getVideo_id();
            String payurl = Config.PAY_URL + "home/live/pay/short_video_id/" + show_video_id +
                    "/publisher_user_id/" + mMVideoItemInfo.getUser_id() +
                    "/forwarder_user_id/" + UserUtils.getUserId(mContext);
            Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("html_url", payurl);
            startActivity(intent);
        } else if (id == R.id.frag_layout_dianzan) {
            VideoItemInfo mMVideoItemInfo = (VideoItemInfo) v.getTag();
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
            showCommentDialog();
        } else if (id == R.id.frag_layout_share) {
            if (mUpLoadDialogUtils == null) {
                mUpLoadDialogUtils = new UpLoadDialogUtils(mActivity, mHandler);
            }
            video_user_id = mAllList.get(mCurrentPosition).getUser_id();
            mUpLoadDialogUtils.setDialog(video_user_id);
        } else if (id == R.id.iv_avatar) {
            video_user_id = mAllList.get(mCurrentPosition).getUser_id();
            Intent intent = new Intent(mActivity, MineActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("video_user_id", video_user_id);
            startActivity(intent);
        } else if (id == R.id.frag_layout_zhufu) {
            String help_url = mAllList.get(mCurrentPosition).getHelp_url();
            if (!TextUtils.isEmpty(help_url) && help_url.contains("/home/user/index")) {
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_CENTER);
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                ActivityManager.getScreenManager().popAllActivityOnlyMain();
                onPauseVideo();
            } else {
                Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", help_url);
                startActivity(intent);
            }
        } else if (id == R.id.iv_follow) {
            String follow_user_id = mAllList.get(mCurrentPosition).getUser_id();
            mPresent.setFollow(follow_user_id);
        }
    }

    @Override
    public void doBusiness(Context mContext) {
        notdata_iv_img.setBackgroundResource(R.mipmap.live_quexing);
        layout_notlive.setBackgroundColor(mActivity.getResources().getColor(R.color.black_live));
        mSwipeRefreshLayout.setProgressViewOffset(false, 150, 220);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.black,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                getList(1);
            }
        });
        initViews();
    }


    private void initViews() {
        mVerticalViewPager.setOffscreenPageLimit(5);
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
                    updateDetailInfo();
                }
                if (position == 0) {
                    mSwipeRefreshLayout.setEnabled(true);
                } else {
                    mSwipeRefreshLayout.setEnabled(false);
                }
                /**
                 * 分页加载数据
                 */
                if (position == mAllList.size() - 1) {
                    getList(page + 1);
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
                iv_follow = (ImageView) viewGroup.findViewById(R.id.iv_follow);
                frag_tv_dianzannum = (TextView) viewGroup.findViewById(R.id.frag_tv_dianzannum);
                frag_tv_commentnum = (TextView) viewGroup.findViewById(R.id.frag_tv_commentnum);
                frag_tv_sharenum = (TextView) viewGroup.findViewById(R.id.frag_tv_sharenum);
                PlayerInfo playerInfo = mPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (playerInfo != null) {
                    mTXVodPlayer = playerInfo.txVodPlayer;
                    restartPlay();
                }
            }
        });
        getList(1);
    }

    private void setPagerAdapter() {
        mPagerAdapter = new MyPagerAdapter();
        mVerticalViewPager.setAdapter(mPagerAdapter);
        mVerticalViewPager.setCurrentItem(mCurrentPosition);
    }


    class MyPagerAdapter extends PagerAdapter {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();

        public PlayerInfo instantiatePlayerInfo(int position, ImageButton record_preview_) {
            TXCLog.d(TAG, "instantiatePlayerInfo " + position);

            PlayerInfo playerInfo = new PlayerInfo();
            TXVodPlayer vodPlayer = new TXVodPlayer(mContext);
            vodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
            vodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
            vodPlayer.setVodListener(VideoFramgent.this);
            TXVodPlayConfig config = new TXVodPlayConfig();
            config.setCacheFolderPath(Environment.getExternalStorageDirectory().getPath() + "/longcheng_txcache");
            config.setMaxCacheItems(5);
            vodPlayer.setConfig(config);
            vodPlayer.setAutoPlay(false);

            VideoItemInfo tcLiveInfo = mAllList.get(position);
            playerInfo.playURL = tcLiveInfo.getVideo_url();
            playerInfo.txVodPlayer = vodPlayer;
            playerInfo.pos = position;
            playerInfo.record_preview = record_preview_;
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
                playerInfo.record_preview.setVisibility(View.VISIBLE);
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
        public int getItemPosition(Object object) {
            // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
            View view = (View) object;
            if (view.getId() == mdelPosition) {
                mdelPosition = -1;
                return POSITION_NONE;
            } else {
                return POSITION_UNCHANGED;
            }
        }

        @Override
        public int getCount() {
            return mAllList.size();
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

            LinearLayout frag_layout_zhufu = (LinearLayout) view.findViewById(R.id.frag_layout_zhufu);
            TextView frag_tv_zhufu = (TextView) view.findViewById(R.id.frag_tv_zhufu);
            LinearLayout frag_layout_jieqi = (LinearLayout) view.findViewById(R.id.frag_layout_jieqi);
            TextView frag_tv_jieqi = (TextView) view.findViewById(R.id.frag_tv_jieqi);
            LinearLayout frag_layout_city = (LinearLayout) view.findViewById(R.id.frag_layout_city);
            TextView frag_tv_city = (TextView) view.findViewById(R.id.frag_tv_city);
            ImageView iv_follow = (ImageView) view.findViewById(R.id.iv_follow);
            frag_layout_jieqi.getBackground().setAlpha(60);
            frag_layout_city.getBackground().setAlpha(60);

            VideoItemInfo mMVideoItemInfo = mAllList.get(position);
            frag_iv_dashuang.setTag(mMVideoItemInfo);
            frag_layout_dianzan.setTag(mMVideoItemInfo);
            frag_layout_comment.setTag(mMVideoItemInfo);
            frag_layout_share.setTag(mMVideoItemInfo);
            record_preview.setOnClickListener(VideoFramgent.this);
            frag_iv_dashuang.setOnClickListener(VideoFramgent.this);
            frag_layout_dianzan.setOnClickListener(VideoFramgent.this);
            frag_layout_comment.setOnClickListener(VideoFramgent.this);
            frag_layout_share.setOnClickListener(VideoFramgent.this);
            iv_avatar.setOnClickListener(VideoFramgent.this);
            frag_layout_zhufu.setOnClickListener(VideoFramgent.this);
            iv_follow.setOnClickListener(VideoFramgent.this);

            String jieqi_branch_name = mMVideoItemInfo.getJieqi_branch_name();
            frag_tv_jieqi.setText(jieqi_branch_name);
            if (TextUtils.isEmpty(jieqi_branch_name)) {
                frag_layout_jieqi.setVisibility(View.GONE);
            } else {
                frag_layout_jieqi.setVisibility(View.VISIBLE);
            }
            String city = mMVideoItemInfo.getAddress();
            if (TextUtils.isEmpty(city)) {
                city = "北京市";
            }
            frag_tv_city.setText("" + city);
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
            int is_user_follow = mMVideoItemInfo.getIs_user_follow();
            if (is_user_follow == 1 || mMVideoItemInfo.getUser_id().equals(UserUtils.getUserId(mContext))) {
                iv_follow.setVisibility(View.GONE);
            } else {
                iv_follow.setVisibility(View.VISIBLE);
            }
            int is_display = mMVideoItemInfo.getIs_display();
            if (is_display == 0) {
                frag_layout_zhufu.setVisibility(View.GONE);
            } else {
                frag_tv_zhufu.setText(mMVideoItemInfo.getHelp_title());
                frag_layout_zhufu.setVisibility(View.VISIBLE);
            }
            // 获取此player
            TXCloudVideoView playView = (TXCloudVideoView) view.findViewById(R.id.video_view);
            PlayerInfo playerInfo = instantiatePlayerInfo(position, record_preview);
            playerInfo.playerView = playView;
            playerInfo.txVodPlayer.setPlayerView(playView);
            playerInfo.iv_dianzan = iv_dianzan;
            playerInfo.iv_follow = iv_follow;
            playerInfo.frag_tv_dianzannum = frag_tv_dianzannum;
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
        if ((showPageType.equals(showPageType_video) && VideoMenuActivity.position == VideoMenuActivity.tab_position_home && HomFramgemt.position == 0)
                || (showPageType.equals(showPageType_follow) && VideoMenuActivity.position == VideoMenuActivity.tab_position_Follow)) {
            if (mTXVodPlayer != null) {
                mTXVodPlayer.resume();
                record_preview.setImageResource(R.mipmap.record_pause);
                record_preview.setImageResource(0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onResumeVideo();
    }

    public void onResumeVideo() {
        if ((showPageType.equals(showPageType_video) && VideoMenuActivity.position == VideoMenuActivity.tab_position_home && HomFramgemt.position == 0)
                || (showPageType.equals(showPageType_follow) && VideoMenuActivity.position == VideoMenuActivity.tab_position_Follow)) {
            if (mTXCloudVideoView != null) {
                mTXCloudVideoView.onResume();
            }
            if (mTXVodPlayer != null) {
                mTXVodPlayer.resume();
                record_preview.setImageResource(R.mipmap.record_pause);
                record_preview.setImageResource(0);
            }
            updateDetailInfo();
        }
    }

    private void updateDetailInfo() {
        if (mAllList != null && mAllList.size() > 0) {
            show_video_id = mAllList.get(mCurrentPosition).getVideo_id();
            mPresent.videoDetail(show_video_id, mCurrentPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onPauseVideo();
    }

    public void onPauseVideo() {
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onPause();
        }
        if (mTXVodPlayer != null) {
            mTXVodPlayer.pause();
            record_preview.setImageResource(R.mipmap.record_start);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
            mTXCloudVideoView = null;
        }
        if (mPagerAdapter != null) {
            mPagerAdapter.onDestroy();
        }
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
//            if (mTXVodPlayer == player) {
//                int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
//                mVideoDuration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION) * 1000;//单位为s
//                TXLog.i("onProgress", "mVideoDuration= " + mVideoDuration);
//            }
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
            mVideoDuration = (int) player.getDuration() * 1000;//单位为s
            Log.e("onPlayEvent", "mVideoDuration=" + mVideoDuration);
            if (player.getHeight() > player.getWidth() * 1.5) {
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
                ToastUtils.showToast(desc);
            }
        }
    }

    @Override
    public void onNetStatus(TXVodPlayer player, Bundle status) {

    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mContext, this);
    }

    @Override
    public void upLoadVideoSuccess(BasicResponse responseBean) {
        if (mAllList != null && mAllList.size() > 0 && mCurrentPosition < mAllList.size()) {
            mAllList.remove(mCurrentPosition);
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void backSignSuccess(BasicResponse<VideoGetSignatureInfo> responseBean) {

    }

    @Override
    public void setFollowLiveSuccess(BasicResponse responseBean) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            if (mPagerAdapter != null) {
                Log.e("onPageSelected", "errcode" + errcode);
                mAllList.get(mCurrentPosition).setIs_user_follow(1);
                iv_follow.setVisibility(View.GONE);
                mPagerAdapter.notifyDataSetChanged();
            }
        }
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
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            ArrayList<VideoItemInfo> mList = responseBean.getData();
            int size = mList == null ? 0 : mList.size();
            if (backPage == 1) {
                mAllList.clear();
            }
            if (size > 0) {
                mAllList.addAll(mList);
            }
            page = backPage;
            if (backPage == 1) {
                setPagerAdapter();
            } else {
                mPagerAdapter.notifyDataSetChanged();
            }
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
        mSwipeRefreshLayout.setRefreshing(false);
        ListUtils.getInstance().setNotDateViewV(mPagerAdapter, layout_notlive);
    }

    @Override
    public void BackMyVideoListSuccess(BasicResponse<MVideoDataInfo> responseBean, int back_page) {

    }


    @Override
    public void sendLCommentSuccess(BasicResponse responseBean) {
        if (mAllList != null && mAllList.size() > 0) {
            if (clickType.equals("dianzan")) {
                String Follow_number = mAllList.get(mCurrentPosition).getTotal_number();
                int is_follow = mAllList.get(mCurrentPosition).getIs_follow();
                String newnum;
                if (is_follow == 0) {
                    mAllList.get(mCurrentPosition).setIs_follow(1);
                    newnum = PriceUtils.getInstance().gteAddSumPrice(Follow_number, "1");
                } else {
                    mAllList.get(mCurrentPosition).setIs_follow(0);
                    newnum = PriceUtils.getInstance().gteSubtractSumPrice("1", Follow_number);
                }
                if (!TextUtils.isEmpty(newnum) && Integer.parseInt(newnum) < 0) {
                    newnum = "0";
                }
                mAllList.get(mCurrentPosition).setTotal_number(newnum);
                is_follow = mAllList.get(mCurrentPosition).getIs_follow();
                if (is_follow == 0) {
                    iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_bai);
                } else {
                    iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_hong1);
                }
                frag_tv_dianzannum.setText("" + newnum);
            } else if (clickType.equals("DOWNLOAD")) {
                String mForward_number = mAllList.get(mCurrentPosition).getForward_number();
                String mnum = PriceUtils.getInstance().gteAddSumPrice(mForward_number, "1");
                mAllList.get(mCurrentPosition).setForward_number(mnum);
                frag_tv_sharenum.setText(mnum);
            }
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void sendVideoCommentSuccess(BasicResponse responseBean) {
        getCommentList(1);
        String comment_number = PriceUtils.getInstance().gteAddSumPrice(mAllList.get(mCurrentPosition).getComment_number(), "1");
        mAllList.get(mCurrentPosition).setComment_number(comment_number);
        frag_tv_commentnum.setText("" + comment_number);
        setCommentAllNum();
    }

    @Override
    public void giveGiftSuccess(BasicResponse responseBean) {

    }

    @Override
    public void videoDetailSuccess(BasicResponse<MVideoItemInfo> responseBean, int back_mCurrentPosition) {
        if (mAllList != null && mAllList.size() > 0 && back_mCurrentPosition < mAllList.size()) {
            MVideoItemInfo mMVideoItemInfo = responseBean.getData();
            int is_follow = mMVideoItemInfo.getIs_follow();
            int is_user_follow = mMVideoItemInfo.getIs_user_follow();
            mAllList.get(back_mCurrentPosition).setIs_follow(is_follow);
            mAllList.get(back_mCurrentPosition).setIs_user_follow(is_user_follow);
            mAllList.get(back_mCurrentPosition).setTotal_number(mMVideoItemInfo.getFollow_number());
            PlayerInfo mPlayerInfo = mPagerAdapter.playerInfoList.get(back_mCurrentPosition);
            if (is_follow == 0) {
                mPlayerInfo.iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_bai);
            } else {
                mPlayerInfo.iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_hong1);
            }
            if (is_user_follow == 1 || mAllList.get(back_mCurrentPosition).getUser_id().equals(UserUtils.getUserId(mContext))) {
                mPlayerInfo.iv_follow.setVisibility(View.GONE);
            } else {
                mPlayerInfo.iv_follow.setVisibility(View.VISIBLE);
            }
            mPlayerInfo.frag_tv_dianzannum.setText("" + mMVideoItemInfo.getFollow_number());
            mPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showGiftDialog() {

    }

    @Override
    public void videoDetCommentListSuccess(BasicResponse<ArrayList<MVideoItemInfo>> responseBean, int backPage) {
        ListUtils.getInstance().RefreshCompleteL(date_listview);
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            ArrayList<MVideoItemInfo> mList = responseBean.getData();
            int size = mList == null ? 0 : mList.size();
            if (backPage == 1) {
                mAdapter = null;
            }
            if (size > 0) {
            }
            if (mAdapter == null) {
                mAdapter = new VideoCommentAdapter(mContext, mList, mHandler);
                date_listview.setAdapter(mAdapter);
            } else {
                mAdapter.reloadListView(mList, false);
            }
            commentpage = backPage;
            if (size < pageSize) {
                ScrowUtil.listViewDownConfig(date_listview);
                if (size > 0 || (page > 1 && size >= 0)) {
//                showNoMoreData(true, dateListview.getRefreshableView());
                }
            } else {
                ScrowUtil.listViewConfigAll(date_listview);
            }
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
    }

    @Override
    public void CommentListError() {

    }

    @Override
    public void Error() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    int special_search = 1;// 默认1 0:普通搜索 1：特殊搜索

    public void getList(int page) {
        if (showPageType.equals(showPageType_follow)) {
            mPresent.getVideoFollowList(page, pageSize);
        } else if (showPageType.equals(showPageType_video)) {
            mPresent.getVideoPlayList(special_search, page, pageSize);
        }

    }

    private int page = 0;
    private int pageSize = 20;
    VideoCommentAdapter mAdapter;
    private int commentpage = 1;
    private TextView tv_count;
    private MyDialog selectDialog;
    PullToRefreshListView date_listview;
    SupplierEditText et_content;

    public void showCommentDialog() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_videocommentlist);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);

            final EditText et = new EditText(mContext);
            et.setHint("说点啥吧");
            selectDialog.setView(et);//给对话框添加一个EditText输入文本框
            selectDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                public void onShow(DialogInterface dialog) {
                    InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
                }
            });
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = (LinearLayout) selectDialog.findViewById(R.id.layout_cancel);
            tv_count = (TextView) selectDialog.findViewById(R.id.tv_count);
            date_listview = (PullToRefreshListView) selectDialog.findViewById(R.id.date_listview);
            et_content = (SupplierEditText) selectDialog.findViewById(R.id.et_content);
            TextView tv_send = (TextView) selectDialog.findViewById(R.id.tv_send);
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_send, R.color.red);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ConfigUtils.getINSTANCE().closeSoftInput(et_content);
                    et_content.setText("");
                    selectDialog.dismiss();
                }
            });
            tv_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    send();
                }
            });
            et_content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId,
                                              KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND
                            || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                        send();
                        return true;
                    }
                    return false;
                }
            });
            ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(et_content, 40);
            date_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    getCommentList(1);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    getCommentList(commentpage + 1);
                }
            });
        } else {
            selectDialog.show();
        }
        if (et_content != null) {
            et_content.setText("");
        }
        setCommentAllNum();
        getCommentList(1);
    }

    private void send() {
        show_video_id = mAllList.get(mCurrentPosition).getVideo_id();
        String cont = et_content.getText().toString();
        if (!TextUtils.isEmpty(cont)) {
            ConfigUtils.getINSTANCE().closeSoftInput(et_content);
            et_content.setText("");
//            String cont_ = ConfigUtils.getINSTANCE().getString(cont);
            mPresent.setVideoSendComment(show_video_id, cont);
        } else {
            ToastUtils.showToast("请输入评论");
        }
    }


    private void setCommentAllNum() {
        String comment_number = mAllList.get(mCurrentPosition).getComment_number();
        if (selectDialog != null && selectDialog.isShowing() && tv_count != null)
            tv_count.setText("" + comment_number + "条评论");
    }

    private void getCommentList(int commentpage) {
        show_video_id = mAllList.get(mCurrentPosition).getVideo_id();
        mPresent.getCommentList(show_video_id, commentpage, pageSize);
    }
}
