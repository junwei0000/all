package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePushConstants;
import com.alivc.live.pusher.AlivcPreviewOrientationEnum;
import com.alivc.live.pusher.AlivcResolutionEnum;
import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.fragment.SharedPreferenceUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import butterknife.BindView;

import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_AUDIO_RETRY_COUNT;
import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_RETRY_INTERVAL;
import static com.alivc.live.pusher.AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT;

/**
 * 直播首页
 */
public class LivePushMenuActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.layout_live_push)
    LinearLayout layout_live_push;
    @BindView(R.id.play_view)
    SurfaceView mSurfaceView;
    @BindView(R.id.layout_notlive)
    LinearLayout layout_notlive;

    private String Pushurl;
    private AlivcLivePushConfig mAlivcLivePushConfig;
    private AlivcResolutionEnum mDefinition = AlivcResolutionEnum.RESOLUTION_720P;
    private boolean mAsyncValue = true;
    private boolean mAudioOnlyPush = false;
    private boolean mVideoOnlyPush = false;
    private AlivcPreviewOrientationEnum mOrientationEnum = ORIENTATION_PORTRAIT;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private boolean isFlash = false;

    private String mAuthTimeStr = "";
    private String mPrivacyKeyStr = "";
    private boolean mMixStream = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.layout_live_push:
                mPresent.getLivePush();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_push_menu;
    }


    @Override
    public void initView(View view) {
        pageTopTvName.setText("直播大厅");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        layout_live_push.setOnClickListener(this);
        mSurfaceView.getHolder().addCallback(new MyCallBack());
        Intent intent = getIntent();
        int IsLiveBroadcast = intent.getIntExtra("IsLiveBroadcast", 0);
        if (IsLiveBroadcast == 0) {
            layout_live_push.setVisibility(View.GONE);
        } else {
            layout_live_push.setVisibility(View.VISIBLE);
        }
        mPresent.getLivePlay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MyCallBack", "onResume");
    }

    @Override
    public void initDataAfter() {
        mAlivcLivePushConfig = new AlivcLivePushConfig();
        AlivcLivePushConfig.setMediaProjectionPermissionResultData(null);
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    public void BackPushSuccess(LivePushDataInfo responseBean) {
        Pushurl = responseBean.getPushurl();
        if (getPushConfig() != null) {
            LivePushActivity.startActivity(this, mAlivcLivePushConfig, Pushurl,
                    mAsyncValue, mAudioOnlyPush, mVideoOnlyPush, mOrientationEnum,
                    mCameraId, isFlash, mAuthTimeStr, mPrivacyKeyStr, mMixStream,
                    mAlivcLivePushConfig.isExternMainStream());
        }
    }

    String flvurl;

    @Override
    public void BackPlaySuccess(LivePushDataInfo responseBean) {
        flvurl = responseBean.getM3u8url();
        startPlay();
    }

    @Override
    public void Error() {
    }


    class MyCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.d("MyCallBack", "surfaceCreated");

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d("MyCallBack", "surfaceCreated");
            //准备播放
            initPlay();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d("MyCallBack", "surfaceDestroyed");
            //防止退到后台重置问题
//            stopPlay();
//            if (mSurfaceView != null) {
//                mSurfaceView.setVisibility(View.GONE);
//                layout_notlive.setVisibility(View.VISIBLE);
//            }
        }
    }

    AliVcMediaPlayer mPlayer;

    private void initPlay() {
        stopPlay();
        //创建播放器的实例
        mPlayer = new AliVcMediaPlayer(this, mSurfaceView);
        if (mPlayer != null) {
            mPlayer.prepareAndPlay(flvurl);
        }
        mPlayer.play();
        //填充效果
        mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        //开启循环播放
        mPlayer.setCirclePlay(false);
        //设置缺省编码类型：0表示硬解；1表示软解；
        //如果缺省为硬解，在使用硬解时如果解码失败，会尝试使用软解
        //如果缺省为软解，则一直使用软解，软解较为耗电，建议移动设备尽量使用硬解
        mPlayer.setDefaultDecoder(0);
        //准备完成
        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {
                Log.i("MyCallBack", "setPreparedListener");
                mPlayer.play();
                if (mSurfaceView != null) {
                    mSurfaceView.setVisibility(View.VISIBLE);
                    layout_notlive.setVisibility(View.GONE);
                }
            }
        });
        mPlayer.setCompletedListener(new MediaPlayer.MediaPlayerCompletedListener() {
            @Override
            public void onCompleted() {
                Log.i("MyCallBack", "onCompleted");
                //视频正常播放完成时触发
                if (mSurfaceView != null) {
                    mSurfaceView.setVisibility(View.GONE);
                    layout_notlive.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 播放视频
     */
    private void startPlay() {

        initPlay();
    }

    /**
     * 停止播放  (其他的，暂停等功能自己可以定义方法实现)
     */
    private void stopPlay() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.destroy();
            mPlayer = null;
        }
    }

    private AlivcLivePushConfig getPushConfig() {
        if (TextUtils.isEmpty(Pushurl)) {
            Toast.makeText(this, "请重新获取直播信息", Toast.LENGTH_LONG).show();
            return null;
        }
        mAlivcLivePushConfig.setConnectRetryInterval(DEFAULT_VALUE_INT_RETRY_INTERVAL);
        mAlivcLivePushConfig.setConnectRetryCount(DEFAULT_VALUE_INT_AUDIO_RETRY_COUNT);
        mAlivcLivePushConfig.setResolution(mDefinition);
        mAlivcLivePushConfig.setInitialVideoBitrate(Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P.DEFAULT_VALUE_INT_INIT_BITRATE.getBitrate())));
        mAlivcLivePushConfig.setAudioBitRate(1000 * 64);
        mAlivcLivePushConfig.setMinVideoBitrate(Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P_RESOLUTION_FIRST.DEFAULT_VALUE_INT_MIN_BITRATE.getBitrate())));
        SharedPreferenceUtils.setMinBit(getApplicationContext(), Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P_RESOLUTION_FIRST.DEFAULT_VALUE_INT_MIN_BITRATE.getBitrate())));
        mAlivcLivePushConfig.setTargetVideoBitrate(Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P_FLUENCY_FIRST.DEFAULT_VALUE_INT_TARGET_BITRATE.getBitrate())));
        SharedPreferenceUtils.setTargetBit(getApplicationContext(), Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P_FLUENCY_FIRST.DEFAULT_VALUE_INT_TARGET_BITRATE.getBitrate())));

        SharedPreferenceUtils.setHintTargetBit(getApplicationContext(), AlivcLivePushConstants.BITRATE_540P.DEFAULT_VALUE_INT_TARGET_BITRATE.getBitrate());
        SharedPreferenceUtils.setHintMinBit(getApplicationContext(), AlivcLivePushConstants.BITRATE_540P.DEFAULT_VALUE_INT_MIN_BITRATE.getBitrate());
        return mAlivcLivePushConfig;
    }

    private void back() {
        stopPlay();
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
