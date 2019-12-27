package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.CommentListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.RankListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

/**
 * 直播
 */
public class LivePlayActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.frag_tv_playstatus)
    TextView fragTvPlaystatus;
    @BindView(R.id.frag_tv_jieqi)
    TextView fragTvJieqi;
    @BindView(R.id.frag_tv_city)
    TextView fragTvCity;
    @BindView(R.id.frag_layout_city)
    LinearLayout fragLayoutCity;
    @BindView(R.id.frag_layout_rank)
    LinearLayout fragLayoutRank;
    @BindView(R.id.rank_iv_arrow)
    ImageView rank_iv_arrow;
    @BindView(R.id.btn_exit)
    ImageView btnExit;
    @BindView(R.id.lv_rankdata)
    ListView lvRankdata;
    @BindView(R.id.lv_msg)
    ListView lvMsg;
    @BindView(R.id.edt_content)
    TextView edtContent;
    @BindView(R.id.btn_liwu)
    ImageView btnLiwu;
    @BindView(R.id.btn_camera)
    ImageView btnCamera;
    @BindView(R.id.relat_push)
    RelativeLayout relat_push;


    @BindView(R.id.preview_view)
    TXCloudVideoView mSurfaceView;
    @BindView(R.id.iv_notLive)
    ImageView iv_notLive;
    @BindView(R.id.tv_onlinenum)
    TextView tv_onlinenum;
    @BindView(R.id.frag_tv_follow)
    TextView fragTvFollow;
    @BindView(R.id.frag_tv_sharenum)
    TextView fragTvSharenum;
    @BindView(R.id.frag_layout_share)
    LinearLayout fragLayoutShare;
    @BindView(R.id.layout_gn)
    LinearLayout layoutGn;
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;

    boolean isPlaying = false;
    boolean rankOpenStatus = false;
    ShareUtils mShareUtils;
    String title, User_name, Cover_url;
    liWuDialogUtils mliWuDialogUtils;
    ArrayList<LiveDetailItemInfo> gift;

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.btn_exit:
                back();
                break;
            case R.id.btn_liwu:
                rankOpenStatus = false;
                setRankListOpenStatus();
                if (gift != null && gift.size() > 0) {
                    if (mliWuDialogUtils == null) {
                        mliWuDialogUtils = new liWuDialogUtils(mActivity, mHandler, liwu);
                    }
                    mliWuDialogUtils.setGift(gift);
                    mliWuDialogUtils.showPopupWindow();
                } else {
                    ToastUtils.showToast("获取礼物信息异常");
                }
                break;
            case R.id.frag_tv_follow:
                mPresent.setFollowLive(UserUtils.getUserId(mContext), live_room_id);
                break;
            case R.id.frag_layout_rank:
                if (rankOpenStatus) {
                    rankOpenStatus = false;
                } else {
                    rankOpenStatus = true;
                }
                setRankListOpenStatus();
                break;
            case R.id.frag_layout_share:
                //分享
                if (mShareUtils == null) {
                    mShareUtils = new ShareUtils(mActivity);
                }
                BaoZhangActitvty.shareBackType = "Live";
                BaoZhangActitvty.life_repay_id = "Live";
                String wx_share_url = Config.BASE_HEAD_URL + "/home/app/index";
                if (!TextUtils.isEmpty(wx_share_url)) {
                    mShareUtils.setShare("直播中：" + title, Cover_url, wx_share_url, User_name);
                }
                break;
            default:
                break;
        }
    }

    private void setRankListOpenStatus() {
        if (rankOpenStatus) {
            rank_iv_arrow.setBackgroundResource(R.mipmap.live_jiantou_2);
            fragLayoutRank.setBackgroundResource(R.mipmap.live_beijingi_2);
            lvRankdata.setVisibility(View.VISIBLE);
        } else {
            rank_iv_arrow.setBackgroundResource(R.mipmap.live_jiantou_3);
            fragLayoutRank.setBackgroundResource(R.mipmap.live_beijingi_1);
            lvRankdata.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_push;
    }


    @Override
    public void initView(View view) {
//        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), true);
    }

    // 软键盘的显示状态
    private boolean ShowKeyboard;

    public void setListener() {
        iv_notLive.setVisibility(View.VISIBLE);
        btnCamera.setVisibility(View.GONE);
        lvRankdata.getBackground().setAlpha(50);
        btnLiwu.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        fragTvFollow.setOnClickListener(this);
        fragLayoutRank.setOnClickListener(this);
        fragLayoutShare.setOnClickListener(this);
        edtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!TextUtils.isEmpty(edtContent.getText().toString().trim())) {
                        String content = edtContent.getText().toString().trim();
                        edtContent.setText("");
                        mPresent.setLComment(live_room_id, content);
                    }
                    ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                    return true;
                }
                return false;
            }
        });
        relat_push.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // 应用可以显示的区域。此处包括应用占用的区域，包括标题栏不包括状态栏
                Rect r = new Rect();
                relat_push.getWindowVisibleDisplayFrame(r);
                // 键盘最小高度
                int minKeyboardHeight = 150;
                // 获取状态栏高度
                int statusBarHeight = Immersive.getStatusBarHeight(mContext);
                // 屏幕高度,不含虚拟按键的高度
                int screenHeight = relat_push.getRootView().getHeight();
                // 在不显示软键盘时，height等于状态栏的高度
                int height = screenHeight - (r.bottom - r.top);


                if (ShowKeyboard) {
                    // 如果软键盘是弹出的状态，并且height小于等于状态栏高度，
                    // 说明这时软键盘已经收起
                    if (height - statusBarHeight < minKeyboardHeight) {
                        ShowKeyboard = false;
//                        Toast.makeText(mContext, "键盘隐藏了", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 如果软键盘是收起的状态，并且height大于状态栏高度，
                    // 说明这时软键盘已经弹出
                    if (height - statusBarHeight > minKeyboardHeight) {
                        ShowKeyboard = true;
//                        Toast.makeText(mContext, "键盘显示了", Toast.LENGTH_SHORT).show();
                        rankOpenStatus = false;
                        setRankListOpenStatus();
                    }
                }
            }
        });
    }

    String live_room_id;

    @Override
    public void initDataAfter() {
        initPlay();
        live_room_id = getIntent().getStringExtra("live_room_id");
        mPresent.setLiveOnlineNumber(live_room_id, 1);
        initTimer();
    }

    /**
     * 开始计时
     */
    private void initTimer() {
        if (thread == null) {
            thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        try {
                            mHandler.sendEmptyMessage(updateView);
                            Thread.sleep(10000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.start();
        }
    }

    Thread thread;
    TXLivePlayer mLivePlayer;

    private void initPlay() {
        setScreenWake();
        //创建 player 对象
        mLivePlayer = new TXLivePlayer(mActivity);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mSurfaceView);
        // 设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle param) {
                Log.e("onPlayEvent", "event=" + event + " ;;param==" + param.toString());
                if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
                    iv_notLive.setVisibility(View.GONE);
                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
                    stopPlay();
                } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    ToastUtils.showToast("直播已断开");
                    iv_notLive.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNetStatus(Bundle status) {
                Log.d(TAG, "Current status, CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                        ", RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                        ", SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                        ", FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                        ", ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                        ", VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
            }
        });
    }

    /**
     * 播放视频
     */
    private void palyVideo(String playurl) {
        mLivePlayer.startPlay(playurl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV 成熟度高、高并发无压力
    }

    private void stopPlay() {
        if (mLivePlayer != null) {
            mLivePlayer.stopRecord();
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLivePlayer != null) {
            // 暂停
            mLivePlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLivePlayer != null) {
            // 继续
            mLivePlayer.resume();
        }
    }

    @Override
    public void onDestroy() {
        setScreenRelease();
        unregisterReceiver(mReceiver);
        BaoZhangActitvty.shareBackType = "";
        BaoZhangActitvty.life_repay_id = "";
        if (thread != null) {
            mHandler.removeCallbacks(thread);
        }
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mSurfaceView != null) {
            mSurfaceView.onDestroy();
            mSurfaceView = null;
        }
        Log.d(TAG, "vrender onDestroy");
        super.onDestroy();
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mRxAppCompatActivity, this);
    }


    @Override
    public void showDialog() {
        if (!isPlaying)
            LoadingDialogAnim.show(mContext);
    }

    @Override
    public void showGiftDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void videoDetCommentListSuccess(BasicResponse<ArrayList<MVideoItemInfo>> responseBean, int backPage) {

    }

    @Override
    public void CommentListError() {

    }

    @Override
    public void dismissDialog() {
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
        if (fragTvFollow != null) {
            fragTvFollow.setVisibility(View.GONE);
        }
        ToastUtils.showToast("已关注");
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
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            LiveDetailInfo mLiveDetailInfo = responseBean.getData();
            if (mLiveDetailInfo != null) {
                LiveDetailItemInfo PlayUrl = mLiveDetailInfo.getPlayUrl();
                if (!isPlaying && PlayUrl != null) {
                    isPlaying = true;
                    gift = mLiveDetailInfo.getGift();
                    String playurl = PlayUrl.getFlvurl();
                    palyVideo(playurl);
                }
                LiveDetailItemInfo info = mLiveDetailInfo.getInfo();
                if (info != null) {
                    int is_user_follow = info.getIs_user_follow();
                    if (is_user_follow != 0) {
                        fragTvFollow.setVisibility(View.GONE);
                    } else {
                        fragTvFollow.setVisibility(View.VISIBLE);
                    }
                    title = info.getTitle();
                    User_name = info.getUser_name();
                    Cover_url = info.getCover_url();
                    fragTvSharenum.setText("" + info.getForward_number());
                    tv_onlinenum.setText("在线人数：" + info.getOnline_number());
                    fragTvPlaystatus.setText("直播中：" + title);
                    fragTvCity.setText("" + info.getAddress());
                    fragTvJieqi.setText(info.getCurrent_jieqi_cn() + "节气");
                    GlideDownLoadImage.getInstance().loadCircleImage(info.getAvatar(), iv_avatar);
                }
                ArrayList<LiveDetailItemInfo> comment = mLiveDetailInfo.getComment();
                if (comment != null && comment.size() > 0) {
                    Collections.reverse(comment);//这行就是将list的内容反转，下面再装进adapter里，就可以倒序显示了
                    CommentListAdapter mCommentListAdapter = new CommentListAdapter(mActivity, comment);
                    lvMsg.setAdapter(mCommentListAdapter);
                }
                ArrayList<LiveDetailItemInfo> ranklist = mLiveDetailInfo.getRanking();
                if (ranklist != null && ranklist.size() > 0) {
                    RankListAdapter mCAdapter = new RankListAdapter(mActivity, ranklist);
                    lvRankdata.setAdapter(mCAdapter);
                }
            }
        } else if (errcode == -100) {
            back();
            ToastUtils.showToast("" + responseBean.getMsg());
        }else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
    }


    @Override
    public void BackLiveListSuccess(BasicResponse<VideoDataInfo> responseBean, int backPage) {

    }

    @Override
    public void BackVideoListSuccess(BasicResponse<ArrayList<VideoItemInfo>> responseBean, int backPage) {

    }

    @Override
    public void sendLCommentSuccess(BasicResponse responseBean) {
        mHandler.sendEmptyMessage(updateView);
    }

    @Override
    public void sendVideoCommentSuccess(BasicResponse responseBean) {

    }

    @Override
    public void giveGiftSuccess(BasicResponse responseBean) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            mHandler.sendEmptyMessage(updateView);
        } else {
            setNotSkbDialog();
        }
    }

    @Override
    public void videoDetailSuccess(BasicResponse<MVideoItemInfo> responseBean) {

    }

    @Override
    public void Error() {
    }

    MyDialog selectDialog;

    public void setNotSkbDialog() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_livenotskb);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            TextView myexit_text_off = (TextView) selectDialog.findViewById(R.id.myexit_text_off);
            TextView myexit_text_sure = (TextView) selectDialog.findViewById(R.id.myexit_text_sure);
            myexit_text_off.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            myexit_text_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    Intent intent = new Intent(mContext, HelpWithEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("skiptype", "Thanks");
                    startActivity(intent);
                }
            });
        } else {
            selectDialog.show();
        }
    }

    protected static final int updateView = 5;
    protected static final int addForwardNum = 6;
    protected static final int liwu = 7;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case updateView:
                    mPresent.getLivePlayInfo(live_room_id);
                    break;
                case addForwardNum:
                    mPresent.addForwardNumber(live_room_id);
                    break;
                case liwu:
                    int help_number = msg.arg1;
                    String live_gift_id = (String) msg.obj;
                    mPresent.giveGift(live_room_id, live_gift_id, help_number);
                    break;
            }
        }
    };

    private void back() {
        mPresent.setLiveOnlineNumber(live_room_id, 2);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopPlay();
                doFinish();
            }
        }, 100);//秒后执行Runnable中的run方法
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

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConstantManager.BroadcastReceiver_LIVE_ACTION);
        registerReceiver(mReceiver, filter);
    }

    /**
     * 微信支付回调广播
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int errCode = intent.getIntExtra("errCode", 0);
            if (errCode == 10000) {
                mHandler.sendEmptyMessage(addForwardNum);
            }
        }
    };

}
