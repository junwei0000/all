package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.component.custom.AlivcLivePushCustomDetect;
import com.alivc.component.custom.AlivcLivePushCustomFilter;
import com.alivc.live.detect.TaoFaceFilter;
import com.alivc.live.filter.TaoBeautyFilter;
import com.alivc.live.pusher.AlivcBeautyLevelEnum;
import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePushInfoListener;
import com.alivc.live.pusher.AlivcLivePushNetworkListener;
import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.AlivcPreviewDisplayMode;
import com.alivc.live.pusher.AlivcPreviewOrientationEnum;
import com.alivc.live.pusher.AlivcQualityModeEnum;
import com.alivc.live.pusher.AlivcResolutionEnum;
import com.alivc.live.pusher.SurfaceStatus;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.utils.network.NetUtils;
import com.longcheng.lifecareplan.widget.Immersive;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;

import static com.alivc.live.pusher.AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK;
import static com.alivc.live.pusher.AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT;
import static com.alivc.live.pusher.AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_LEFT;
import static com.alivc.live.pusher.AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_RIGHT;
import static com.alivc.live.pusher.AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT;

public class LivePushActivity extends BaseActivity {

    @BindView(R.id.preview_view)
    SurfaceView previewView;
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
    @BindView(R.id.frag_tv_follow)
    TextView fragTvFollow;
    @BindView(R.id.frag_tv_sharenum)
    TextView fragTvSharenum;
    @BindView(R.id.frag_layout_share)
    LinearLayout fragLayoutShare;
    @BindView(R.id.layout_gn)
    LinearLayout layoutGn;

    private static final String URL_KEY = "url_key";
    private static final int REQ_CODE_PUSH = 0x1112;
    private AlivcLivePushConfig mAlivcLivePushConfig;
    private AlivcLivePusher mAlivcLivePusher = null;
    private String mPushUrl = null;
    private boolean mAsync = true;
    private boolean mAudioOnly = false;
    private boolean mVideoOnly = false;
    private SurfaceStatus mSurfaceStatus = SurfaceStatus.UNINITED;
    private boolean isPause = false;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private boolean mFlash = false;
    private boolean mMixExtern = false;
    private boolean mMixMain = false;
    private String mAuthTime = "";
    private String mPrivacyKey = "";
    private boolean videoThreadOn = false;
    private boolean audioThreadOn = false;
    private int mNetWork = 0;
    TaoFaceFilter taoFaceFilter;
    TaoBeautyFilter taoBeautyFilter;


    boolean rankOpenStatus = false;

    @Override
    public void onClick(View view) {

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
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent));
    }

    @Override
    public void setListener() {
        previewView.getHolder().addCallback(mCallback);
        btnLiwu.setVisibility(View.GONE);
        btnCamera.setOnClickListener(onClickListener);
        btnExit.setOnClickListener(onClickListener);
//        fragLayoutRank.setOnClickListener(onClickListener);
        edtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!TextUtils.isEmpty(edtContent.getText().toString().trim())) {
                        String content = edtContent.getText().toString().trim();
                        edtContent.setText("");
                        ToastUtils.showToast("功能开发中...");
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
        initData();
        String city = new LocationUtils().getAddressCity(this);
        fragTvCity.setText("" + city);
        fragTvJieqi.setText(HomeFragment.jieqi_name + "节气");
        Intent intent = getIntent();
        String playTitle = intent.getStringExtra("playTitle");
        if (!TextUtils.isEmpty(playTitle)) {
            fragTvPlaystatus.setText("直播中: " + playTitle);
        }
    }

    public static void startActivity(Activity activity, String url,String playTitle) {
        Intent intent = new Intent(activity, LivePushActivity.class);
        intent.putExtra(URL_KEY, url);
        intent.putExtra("playTitle", playTitle);
        activity.startActivityForResult(intent, REQ_CODE_PUSH);
    }

    private void initData() {
        mPushUrl = getIntent().getStringExtra(URL_KEY);
        mAlivcLivePushConfig = new AlivcLivePushConfig();
        mAlivcLivePusher = new AlivcLivePusher();
        try {
            mMixMain = mAlivcLivePushConfig.isExternMainStream();
            mAlivcLivePushConfig.setMediaProjectionPermissionResultData(null);
            mAlivcLivePushConfig.setResolution(AlivcResolutionEnum.RESOLUTION_540P);
            mAlivcLivePushConfig.setQualityMode(AlivcQualityModeEnum.QM_RESOLUTION_FIRST);//显示模式 清晰度优先
            mAlivcLivePushConfig.setPreviewDisplayMode(AlivcPreviewDisplayMode.ALIVC_LIVE_PUSHER_PREVIEW_ASPECT_FIT);//横屏适配
            mAlivcLivePushConfig.setBeautyOn(true);// 开启美颜
            mAlivcLivePushConfig.setBeautyLevel(AlivcBeautyLevelEnum.BEAUTY_Professional);//设定为高级美颜
            mAlivcLivePusher.init(getApplicationContext(), mAlivcLivePushConfig);
            mAlivcLivePusher.setAutoFocus(true);//自动对焦
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        mNetWork = NetUtils.getAPNType(this);
        mAlivcLivePusher.setLivePushInfoListener(mPushInfoListener);
        mAlivcLivePusher.setLivePushNetworkListener(mPushNetworkListener);
        //人脸识别回调（只接入标准版美颜可不需要调用此接口）
        mAlivcLivePusher.setCustomDetect(new AlivcLivePushCustomDetect() {
            @Override
            public void customDetectCreate() {
                taoFaceFilter = new TaoFaceFilter(getApplicationContext());
                taoFaceFilter.customDetectCreate();
            }

            @Override
            public long customDetectProcess(long data, int width, int height, int rotation, int format, long extra) {
                if (taoFaceFilter != null) {
                    return taoFaceFilter.customDetectProcess(data, width, height, rotation, format, extra);
                }
                return 0;
            }

            @Override
            public void customDetectDestroy() {
                if (taoFaceFilter != null) {
                    taoFaceFilter.customDetectDestroy();
                }
            }
        });
        //美颜回调
        mAlivcLivePusher.setCustomFilter(new AlivcLivePushCustomFilter() {
            @Override
            public void customFilterCreate() {
                taoBeautyFilter = new TaoBeautyFilter();
                taoBeautyFilter.customFilterCreate();
            }

            @Override
            public void customFilterUpdateParam(float fSkinSmooth, float fWhiten, float fWholeFacePink, float fThinFaceHorizontal, float fCheekPink, float fShortenFaceVertical, float fBigEye) {
                if (taoBeautyFilter != null) {
                    taoBeautyFilter.customFilterUpdateParam(fSkinSmooth, fWhiten, fWholeFacePink, fThinFaceHorizontal, fCheekPink, fShortenFaceVertical, fBigEye);
                }
            }

            @Override
            public void customFilterSwitch(boolean on) {
                if (taoBeautyFilter != null) {
                    taoBeautyFilter.customFilterSwitch(on);
                }
            }

            @Override
            public int customFilterProcess(int inputTexture, int textureWidth, int textureHeight, long extra) {
                if (taoBeautyFilter != null) {
                    return taoBeautyFilter.customFilterProcess(inputTexture, textureWidth, textureHeight, extra);
                }
                return inputTexture;
            }

            @Override
            public void customFilterDestroy() {
                if (taoBeautyFilter != null) {
                    taoBeautyFilter.customFilterDestroy();
                }
                taoBeautyFilter = null;
            }
        });
    }


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BoEditPopupUtils.CONTENT:
                    break;
                case BoEditPopupUtils.SENDLIWU:
                    break;
                case BoEditPopupUtils.CAMERA:
                    setCameraSwitch();
                    break;
                case BoEditPopupUtils.EXIT:
                    finish();
                    break;
            }
        }
    };
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_exit:
                    finish();
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

    private void setCameraSwitch() {
        if (mCameraId == CAMERA_TYPE_FRONT.getCameraId()) {
            mCameraId = CAMERA_TYPE_BACK.getCameraId();
        } else {
            mCameraId = CAMERA_TYPE_FRONT.getCameraId();
        }
        mAlivcLivePusher.switchCamera();
    }

    /**
     * 开始推流
     */
    public void startPlay() {
        if (mAsync) {
            mAlivcLivePusher.startPushAysnc(mPushUrl);
        } else {
            mAlivcLivePusher.startPush(mPushUrl);
        }
    }

    SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            if (mSurfaceStatus == SurfaceStatus.UNINITED) {
                mSurfaceStatus = SurfaceStatus.CREATED;
                if (mAlivcLivePusher != null) {
                    try {
                        if (mAsync) {
                            mAlivcLivePusher.startPreviewAysnc(previewView);
                        } else {
                            mAlivcLivePusher.startPreview(previewView);
                        }
                        if (mAlivcLivePushConfig.isExternMainStream()) {
                            startYUV(getApplicationContext());
                            startPCM(getApplicationContext());
                        }
                        mAlivcLivePusher.setBeautyBuffing(60);// 磨皮范围0-100
                        mAlivcLivePusher.setBeautyWhite(50);// 美白范围0-100
                        mAlivcLivePusher.setBeautyBigEye(30);// 大眼设置范围0-100
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startPlay();
                            }
                        }, 2200);
                    } catch (IllegalArgumentException e) {
                        e.toString();
                    } catch (IllegalStateException e) {
                        e.toString();
                    }
                }
            } else if (mSurfaceStatus == SurfaceStatus.DESTROYED) {
                mSurfaceStatus = SurfaceStatus.RECREATED;
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            mSurfaceStatus = SurfaceStatus.CHANGED;
            Log.d("MyCallBack", "surfaceChangedsurfaceChanged");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            mSurfaceStatus = SurfaceStatus.DESTROYED;
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        if (mAlivcLivePusher != null) {
            try {
                if (!isPause) {
                    if (mAsync) {
                        mAlivcLivePusher.resumeAsync();
                    } else {
                        mAlivcLivePusher.resume();
                    }
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAlivcLivePusher != null) {
            try {
                if (mAlivcLivePusher != null/*.isPushing()*/) {
                    mAlivcLivePusher.pause();
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        videoThreadOn = false;
        audioThreadOn = false;
        if (mAlivcLivePusher != null) {
            //停止推流
            try {
                mAlivcLivePusher.stopPush();
            } catch (Exception e) {
            }
            //停止预览
            try {
                mAlivcLivePusher.stopPreview();
            } catch (Exception e) {
            }
            //释放推流
            try {
                mAlivcLivePusher.destroy();
                mAlivcLivePusher.setLivePushInfoListener(null);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        previewView = null;
        mAlivcLivePushConfig = null;
        mAlivcLivePusher = null;
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        AlivcPreviewOrientationEnum orientationEnum;
        if (mAlivcLivePusher != null) {
            switch (rotation) {
                case Surface.ROTATION_0:
                    orientationEnum = ORIENTATION_PORTRAIT;
                    break;
                case Surface.ROTATION_90:
                    orientationEnum = ORIENTATION_LANDSCAPE_HOME_RIGHT;
                    break;
                case Surface.ROTATION_270:
                    orientationEnum = ORIENTATION_LANDSCAPE_HOME_LEFT;
                    break;
                default:
                    orientationEnum = ORIENTATION_PORTRAIT;
                    break;
            }
            try {
                mAlivcLivePusher.setPreviewOrientation(orientationEnum);
            } catch (IllegalStateException e) {

            }
        }
    }

    public AlivcLivePusher getLivePusher() {
        return this.mAlivcLivePusher;
    }

    public SurfaceView getPreviewView() {
        return this.previewView;
    }

    public interface PauseState {
        void updatePause(boolean state);
    }

    private PauseState mStateListener = new PauseState() {
        @Override
        public void updatePause(boolean state) {
            isPause = state;
        }
    };

    class ConnectivityChangedReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                if (mNetWork != NetUtils.getAPNType(context)) {
                    mNetWork = NetUtils.getAPNType(context);
                    if (mAlivcLivePusher != null) {
                        if (mAlivcLivePusher.isPushing()) {
                            try {
                                mAlivcLivePusher.reconnectPushAsync(null);
                            } catch (IllegalStateException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

        }
    }

    private void showToast(final String text) {
        if (text == null) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(LivePushActivity.this, text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    AlivcLivePushInfoListener mPushInfoListener = new AlivcLivePushInfoListener() {
        @Override
        public void onPreviewStarted(AlivcLivePusher pusher) {
        }

        @Override
        public void onPreviewStoped(AlivcLivePusher pusher) {
        }

        @Override
        public void onPushStarted(AlivcLivePusher pusher) {
            showToast(getString(R.string.start_button));
        }

        @Override
        public void onPushStoped(AlivcLivePusher pusher) {
            showToast(getString(R.string.stop_button));
        }

        @Override
        public void onFirstAVFramePushed(AlivcLivePusher pusher) {
        }

        @Override
        public void onPushPauesed(AlivcLivePusher pusher) {
            showToast(getString(R.string.pause_button));
        }

        @Override
        public void onPushResumed(AlivcLivePusher pusher) {
            showToast(getString(R.string.resume_button));
        }


        /**
         * 推流重启通知
         *
         * @param pusher AlivcLivePusher实例
         */
        @Override
        public void onPushRestarted(AlivcLivePusher pusher) {
//            showToast(getString(R.string.restart_success));
        }

        @Override
        public void onFirstFramePreviewed(AlivcLivePusher pusher) {
        }

        @Override
        public void onDropFrame(AlivcLivePusher pusher, int countBef, int countAft) {
        }

        @Override
        public void onAdjustBitRate(AlivcLivePusher pusher, int curBr, int targetBr) {
        }

        @Override
        public void onAdjustFps(AlivcLivePusher pusher, int curFps, int targetFps) {
        }
    };

    AlivcLivePushNetworkListener mPushNetworkListener = new AlivcLivePushNetworkListener() {
        @Override
        public void onNetworkPoor(AlivcLivePusher pusher) {
            showToast(getString(R.string.network_poor));
        }

        @Override
        public void onNetworkRecovery(AlivcLivePusher pusher) {
            showToast(getString(R.string.network_recovery));
            mAlivcLivePusher.reconnectPushAsync(null);
        }

        @Override
        public void onReconnectStart(AlivcLivePusher pusher) {
//            showToastShort(getString(R.string.reconnect_start));
        }

        @Override
        public void onReconnectFail(AlivcLivePusher pusher) {

//            showDialog(getString(R.string.reconnect_fail));
        }

        @Override
        public void onReconnectSucceed(AlivcLivePusher pusher) {
//            showToast(getString(R.string.reconnect_success));
        }

        @Override
        public void onSendDataTimeout(AlivcLivePusher pusher) {
//            showDialog(getString(R.string.senddata_timeout));
        }

        @Override
        public void onConnectFail(AlivcLivePusher pusher) {
//            showDialog(getString(R.string.connect_fail));
        }

        @Override
        public String onPushURLAuthenticationOverdue(AlivcLivePusher alivcLivePusher) {
            return null;
        }

        @Override
        public void onConnectionLost(AlivcLivePusher pusher) {
//            showToast("推流已断开");
        }


        @Override
        public void onSendMessage(AlivcLivePusher pusher) {
//            showToast(getString(R.string.send_message));
        }

        @Override
        public void onPacketsLost(AlivcLivePusher pusher) {
//            showToast("推流丢包通知");
        }
    };

    public void startYUV(final Context context) {
        new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            private AtomicInteger atoInteger = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("LivePushActivity-readYUV-Thread" + atoInteger.getAndIncrement());
                return t;
            }
        }).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                videoThreadOn = true;
                byte[] yuv;
                InputStream myInput = null;
                try {
                    File f = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "alivc_resource/capture0.yuv");
                    myInput = new FileInputStream(f);
                    byte[] buffer = new byte[1280 * 720 * 3 / 2];
                    int length = myInput.read(buffer);
                    //发数据
                    while (length > 0 && videoThreadOn) {
                        mAlivcLivePusher.inputStreamVideoData(buffer, 720, 1280, 720, 1280 * 720 * 3 / 2, System.nanoTime() / 1000, 0);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //发数据
                        length = myInput.read(buffer);
                        if (length <= 0) {
                            myInput.close();
                            myInput = new FileInputStream(f);
                            length = myInput.read(buffer);
                        }
                    }
                    myInput.close();
                    videoThreadOn = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void stopYUV() {
        videoThreadOn = false;
    }

    private void startPCM(final Context context) {
        new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            private AtomicInteger atoInteger = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setName("LivePushActivity-readPCM-Thread" + atoInteger.getAndIncrement());
                return t;
            }
        }).execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                audioThreadOn = true;
                byte[] pcm;
                int allSended = 0;
                int sizePerSecond = 44100 * 2;
                InputStream myInput = null;
                OutputStream myOutput = null;
                boolean reUse = false;
                long startPts = System.nanoTime() / 1000;
                try {
                    File f = new File("/sdcard/alivc_resource/441.pcm");
                    myInput = new FileInputStream(f);
                    byte[] buffer = new byte[2048];
                    int length = myInput.read(buffer, 0, 2048);
                    while (length > 0 && audioThreadOn) {
                        long pts = System.nanoTime() / 1000;
                        mAlivcLivePusher.inputStreamAudioData(buffer, length, 44100, 1, pts);
                        allSended += length;
                        if ((allSended * 1000000L / sizePerSecond - 50000) > (pts - startPts)) {
                            try {
                                Thread.sleep(45);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        length = myInput.read(buffer);
                        if (length < 2048) {
                            myInput.close();
                            myInput = new FileInputStream(f);
                            length = myInput.read(buffer);
                        }
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    myInput.close();
                    audioThreadOn = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void stopPcm() {
        audioThreadOn = false;
    }

}
