package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
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
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.CommentListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.RankListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;

public class LivePushActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

    @BindView(R.id.preview_view)
    TXCloudVideoView mPusherView;
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
    SupplierEditText edtContent;
    @BindView(R.id.btn_liwu)
    ImageView btnLiwu;
    @BindView(R.id.btn_camera)
    ImageView btnCamera;
    @BindView(R.id.relat_push)
    RelativeLayout relatPush;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
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

    private static final String URL_KEY = "pushUrl_key";
    private String mPushUrl = null;
    boolean rankOpenStatus = false;
    String live_room_id;
    ShareUtils mShareUtils;
    String title, User_name, Cover_url;

    @Override
    public void onClick(View view) {

    }

    public static void startActivity(Activity activity, String url, String live_room_id) {
        Intent intent = new Intent(activity, LivePushActivity.class);
        intent.putExtra(URL_KEY, url);
        intent.putExtra("live_room_id", live_room_id);
        activity.startActivity(intent);
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
//        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        btnLiwu.setVisibility(View.GONE);
        fragTvFollow.setVisibility(View.GONE);
        btnCamera.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
        fragLayoutRank.setOnClickListener(onClickListener);
        lvRankdata.getBackground().setAlpha(50);
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
    }

    @Override
    public void initDataAfter() {
        String city = new LocationUtils().getAddressCity(this);
        fragTvCity.setText("" + city);
        fragTvJieqi.setText(HomeFragment.jieqi_name + "节气");
        Intent intent = getIntent();
        mPushUrl = intent.getStringExtra(URL_KEY);
        initPusher();
        initListener();
        startRTMPPush();
        live_room_id = intent.getStringExtra("live_room_id");
        mPresent.setLiveOnlineNumber(live_room_id, 1);
        initTimer();
    }
    Thread thread;
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
    /**
     * **********************************腾讯云直播设置************************************************
     */

    private TXLivePushConfig mLivePushConfig;                // SDK 推流 config
    private TXLivePusher mLivePusher;                    // SDK 推流类
    /**
     * 其他参数
     */
    private int mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960; // 当前分辨率
    private boolean mIsPushing;                     // 当前是否正在推流
    /**
     * 默认美颜参数
     */
    private int mBeautyLevel = 4;            // 美颜等级
    private int mBeautyStyle = TXLiveConstants.BEAUTY_STYLE_SMOOTH; // 美颜样式
    private int mWhiteningLevel = 3;            // 美白等级
    private int mRuddyLevel = 2;            // 红润等级
    private boolean mFrontCamera = false;
    TXPhoneStateListener mPhoneListener;

    /**
     * 初始化 SDK 推流器
     */
    private void initPusher() {
        mLivePusher = new TXLivePusher(this);
        mLivePushConfig = new TXLivePushConfig();
        mLivePushConfig.setVideoEncodeGop(5);
        mLivePusher.setConfig(mLivePushConfig);
    }

    /**
     * 开始 RTMP 推流
     * <p>
     * 推荐使用方式：
     * 1. 配置好 {@link TXLivePushConfig} ， 配置推流参数
     * 2. 调用 {@link TXLivePusher#setConfig(TXLivePushConfig)} ，设置推流参数
     * 3. 调用 {@link TXLivePusher#startCameraPreview(TXCloudVideoView)} ， 开始本地预览
     * 4. 调用 {@link TXLivePusher#startPusher(String)} ， 发起推流
     * <p>
     * 注：步骤 3 要放到 2 之后，否则横屏推流、聚焦曝光、画面缩放功能配置不生效
     *
     * @return
     */
    private boolean startRTMPPush() {
        if (TextUtils.isEmpty(mPushUrl) || (!mPushUrl.trim().toLowerCase().startsWith("rtmp://"))) {
            ToastUtils.showToast("直播地址不合法");
            return false;
        }
        // 显示本地预览的View
        mPusherView.setVisibility(View.VISIBLE);
        // 添加播放回调
        mLivePusher.setPushListener(new ITXLivePushListener() {
            @Override
            public void onPushEvent(int event, Bundle param) {
                String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
                String pushEventLog = "receive event: " + event + ", " + msg;
                Log.d(TAG, pushEventLog);
                // Toast错误内容
                if (event < 0) {
                    ToastUtils.showToast(param.getString(TXLiveConstants.EVT_DESCRIPTION));
                }
                if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT
                        || event == TXLiveConstants.PUSH_ERR_INVALID_ADDRESS
                        || event == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL
                        || event == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL) {
                    // 遇到以上错误，则停止推流
//                    stopRTMPPush();
                } else if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
                    // 开启硬件加速失败
                    ToastUtils.showToast(param.getString(TXLiveConstants.EVT_DESCRIPTION));
                    mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
                    mLivePusher.setConfig(mLivePushConfig);
                } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_RESOLUTION) {
                    Log.d(TAG, "change resolution to " + param.getInt(TXLiveConstants.EVT_PARAM2) + ", bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
                } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_BITRATE) {
                    Log.d(TAG, "change bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
                } else if (event == TXLiveConstants.PUSH_WARNING_NET_BUSY) {
                    ToastUtils.showToast(R.string.net_tishi);
                } else if (event == TXLiveConstants.PUSH_EVT_START_VIDEO_ENCODER) {
                } else if (event == TXLiveConstants.PUSH_EVT_OPEN_CAMERA_SUCC) {
                    // 只有后置摄像头可以打开闪光灯，若默认需要开启闪光灯。 那么在打开摄像头成功后，才可以进行配置。 若果当前是前置，设定无效；若是后置，打开闪光灯。
                    mLivePusher.turnOnFlashLight(false);
                }
            }

            @Override
            public void onNetStatus(Bundle status) {
                String str = getStatus(status);
                Log.d(TAG, "Current status, CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                        ", RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                        ", SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                        ", FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                        ", ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                        ", VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
            }
        });
        // 添加后台垫片推流参数
        Bitmap bitmap = decodeResource(getResources(), R.mipmap.pause_publish);
        mLivePushConfig.setPauseImg(bitmap);
        mLivePushConfig.setPauseImg(300, 5);
        mLivePushConfig.setPauseFlag(TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO);// 设置暂停时，只停止画面采集，不停止声音采集。
        // 设置推流分辨率
        mLivePushConfig.setVideoResolution(mCurrentVideoResolution);
        // 设置美颜
        mLivePusher.setBeautyFilter(mBeautyStyle, mBeautyLevel, mWhiteningLevel, mRuddyLevel);
        // 开启麦克风推流相关--是否静音
        mLivePusher.setMute(false);
        // 横竖屏推流相关
        mLivePushConfig.setHomeOrientation(TXLiveConstants.VIDEO_ANGLE_HOME_DOWN);
        mLivePusher.setRenderRotation(0);
        // 是否开启观众端镜像观看
        mLivePusher.setMirror(false);
        // 是否打开调试信息
        mPusherView.showLog(false);
        // 是否添加水印
        mLivePushConfig.setWatermark(null, 0, 0, 0);
        // 是否打开曝光对焦
        mLivePushConfig.setTouchFocus(true);
        // 是否打开手势放大预览画面
        mLivePushConfig.setEnableZoom(false);
        // 设置推流配置
        mLivePusher.setConfig(mLivePushConfig);
        // 设置混响
        mLivePusher.setReverb(0);
        // 设置变声
        mLivePusher.setVoiceChangerType(0);
        // 设置场景
        setPushScene(TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION, true);
        // 设置本地预览View
        mLivePusher.startCameraPreview(mPusherView);
        if (!mFrontCamera)
            mLivePusher.switchCamera();
        // 发起推流
        int ret = mLivePusher.startPusher(mPushUrl);
        if (ret == -5) {
            ToastUtils.showToast("直播信息获取失败");
            return false;
        }
        ToastUtils.showToast("开始直播");
        mIsPushing = true;
        return true;
    }

    /**
     * 停止 RTMP 推流
     */
    private void stopRTMPPush() {
        // 停止BGM
        mLivePusher.stopBGM();
        // 停止本地预览
        mLivePusher.stopCameraPreview(true);
        // 移除监听
        mLivePusher.setPushListener(null);
        // 停止推流
        mLivePusher.stopPusher();
        // 移除垫片图像
        mLivePushConfig.setPauseImg(null);
        mIsPushing = false;
    }

    private void setCameraSwitch() {
        mLivePusher.switchCamera();
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
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            LiveDetailInfo mLiveDetailInfo = responseBean.getData();
            if (mLiveDetailInfo != null) {
                LiveDetailItemInfo info = mLiveDetailInfo.getInfo();
                if (info != null) {
                    title = info.getTitle();
                    User_name = info.getUser_name();
                    Cover_url = info.getCover_url();
                    fragTvSharenum.setText("" + info.getForward_number());
                    tv_onlinenum.setText("在线人数：" + info.getOnline_number());
                    fragTvPlaystatus.setText("直播中：" + title);
                    fragTvCity.setText("" + info.getAddress());
                    fragTvJieqi.setText(info.getCurrent_jieqi_cn() + "节气");
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
        } else {
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
    public void Error() {
    }

    protected static final int updateView = 5;
    protected static final int addForwardNum = 6;
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
            }
        }
    };

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    /**
     * 电话监听
     */
    private static class TXPhoneStateListener extends PhoneStateListener {
        WeakReference<TXLivePusher> mPusher;

        public TXPhoneStateListener(TXLivePusher pusher) {
            mPusher = new WeakReference<TXLivePusher>(pusher);
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            TXLivePusher pusher = mPusher.get();
            switch (state) {
                //电话等待接听
                case TelephonyManager.CALL_STATE_RINGING:
                    if (pusher != null) pusher.pausePusher();
                    break;
                //电话接听
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (pusher != null) pusher.pausePusher();
                    break;
                //电话挂机
                case TelephonyManager.CALL_STATE_IDLE:
                    if (pusher != null) pusher.resumePusher();
                    break;
            }
        }
    }

    /**
     * 获取资源图片
     *
     * @param resources
     * @param id
     * @return
     */
    private Bitmap decodeResource(Resources resources, int id) {
        TypedValue value = new TypedValue();
        resources.openRawResource(id, value);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inTargetDensity = value.density;
        return BitmapFactory.decodeResource(resources, id, opts);
    }

    /**
     * 设置推流场景
     * <p>
     * SDK 内部将根据具体场景，进行推流 分辨率、码率、FPS、是否启动硬件加速、是否启动回声消除 等进行配置
     * <p>
     * 适用于一般客户，方便快速进行配置
     * <p>
     * 专业客户，推荐通过 {@link TXLivePushConfig} 进行逐一配置
     */
    public void setPushScene(int type, boolean enableAdjustBitrate) {
        Log.i(TAG, "setPushScene: type = " + type + " enableAdjustBitrate = " + enableAdjustBitrate);
        // 码率、分辨率自适应都关闭
        boolean autoBitrate = enableAdjustBitrate;
        boolean autoResolution = false;
        switch (type) {
            case TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION: /*360p*/
                if (mLivePusher != null) {
                    mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION, autoBitrate, autoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640;
                }
                break;
            case TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION: /*540p*/
                if (mLivePusher != null) {
                    mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION, autoBitrate, autoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960;
                }
                break;
            case TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION: /*720p*/
                if (mLivePusher != null) {
                    mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION, autoBitrate, autoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_720_1280;
                }
                break;
            case TXLiveConstants.VIDEO_QUALITY_LINKMIC_MAIN_PUBLISHER: /*连麦大主播*/
                if (mLivePusher != null) {
                    mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_LINKMIC_MAIN_PUBLISHER, autoBitrate, autoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_540_960;
                }
                break;
            case TXLiveConstants.VIDEO_QUALITY_LINKMIC_SUB_PUBLISHER: /*连麦小主播*/
                if (mLivePusher != null) {
                    mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_LINKMIC_SUB_PUBLISHER, autoBitrate, autoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_320_480;
                }
                break;
            case TXLiveConstants.VIDEO_QUALITY_REALTIEM_VIDEOCHAT: /*实时*/
                if (mLivePusher != null) {
                    mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_REALTIEM_VIDEOCHAT, autoBitrate, autoResolution);
                    mCurrentVideoResolution = TXLiveConstants.VIDEO_RESOLUTION_TYPE_360_640;
                }
                break;
            default:
                break;
        }
        // 设置场景化配置后，SDK 内部会根据场景自动选择相关的配置参数，所以我们这里把内部的config获取出来，赋值到外部。
        mLivePushConfig = mLivePusher.getConfig();
        // 是否开启硬件加速
        mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_HARDWARE);
        mLivePusher.setConfig(mLivePushConfig);
    }

    /**
     * 获取当前推流状态
     *
     * @param status
     * @return
     */
    private String getStatus(Bundle status) {
        String str = String.format("%-14s %-14s %-12s\n%-8s %-8s %-8s %-8s\n%-14s %-14s %-12s\n%-14s %-14s",
                "CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE),
                "RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT),
                "SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps",
                "JIT:" + status.getInt(TXLiveConstants.NET_STATUS_NET_JITTER),
                "FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS),
                "GOP:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP) + "s",
                "ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps",
                "QUE:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_CACHE) + "|" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE),
                "DRP:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_DROP) + "|" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_DROP),
                "VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps",
                "SVR:" + status.getString(TXLiveConstants.NET_STATUS_SERVER_IP),
                "AUDIO:" + status.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO));
        return str;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPusherView != null) {
            mPusherView.onResume();
        }

        if (mIsPushing && mLivePusher != null) {
            // 如果当前是隐私模式，那么不resume
            mLivePusher.resumePusher();
            mLivePusher.resumeBGM();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mPusherView != null) {
            mPusherView.onPause();
        }

        if (mIsPushing && mLivePusher != null) {
            // 如果当前已经是隐私模式，那么则不pause
            mLivePusher.pausePusher();
            mLivePusher.pauseBGM();
        }

    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        BaoZhangActitvty.shareBackType = "";
        BaoZhangActitvty.life_repay_id = "";
        if (thread != null) {
            mHandler.removeCallbacks(thread);
        }
        stopRTMPPush(); // 停止推流
        if (mPusherView != null) {
            mPusherView.onDestroy(); // 销毁 View
        }
        unInitPhoneListener();
        super.onDestroy();
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mRxAppCompatActivity, this);
    }

    /**
     * 初始化电话监听、系统是否打开旋转监听
     */
    private void initListener() {
        mPhoneListener = new TXPhoneStateListener(mLivePusher);
        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 销毁
     */
    private void unInitPhoneListener() {
        TelephonyManager tm = (TelephonyManager) getApplicationContext().getSystemService(Service.TELEPHONY_SERVICE);
        tm.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE);
    }

    /**
     * *******************************************************************************
     */

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_exit:
                    back();
                    break;
                case R.id.edt_content:
                    break;
                case R.id.btn_camera:
                    setCameraSwitch();
                    break;
                case R.id.btn_liwu:
                    ToastUtils.showToast("功能开发中...");
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
    };

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


    private void back() {
        mPresent.setLiveRoomBroadcastStatus(live_room_id, 2);
        mPresent.setLiveOnlineNumber(live_room_id, 2);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 如果正在推流，先停止推流，再退出
                if (mIsPushing) {
                    stopRTMPPush();
                }
                doFinish();
            }
        }, 100);//秒后执行Runnable中的run方法
    }

    @Override
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
