package com.longcheng.lifecareplan.modular.home.liveplay.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alivc.live.pusher.AlivcLivePushError;
import com.alivc.live.pusher.AlivcLivePushErrorListener;
import com.alivc.live.pusher.AlivcLivePushInfoListener;
import com.alivc.live.pusher.AlivcLivePushNetworkListener;
import com.alivc.live.pusher.AlivcLivePusher;
import com.alivc.live.pusher.AlivcSnapshotListener;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushActivity;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static com.alivc.live.pusher.AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK;
import static com.alivc.live.pusher.AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT;

public class LivePushFragment extends Fragment implements Runnable {
    public static final String TAG = "LivePushFragment";

    private static final String URL_KEY = "url_key";
    private static final String ASYNC_KEY = "async_key";
    private static final String AUDIO_ONLY_KEY = "audio_only_key";
    private static final String VIDEO_ONLY_KEY = "video_only_key";
    private static final String QUALITY_MODE_KEY = "quality_mode_key";
    private static final String CAMERA_ID = "camera_id";
    private static final String FLASH_ON = "flash_on";
    private static final String AUTH_TIME = "auth_time";
    private static final String PRIVACY_KEY = "privacy_key";
    private static final String MIX_EXTERN = "mix_extern";
    private static final String MIX_MAIN = "mix_main";
    private final long REFRESH_INTERVAL = 2000;

    private LinearLayout mExit;
    private ImageView mCamera;
    private ImageView mSnapshot;

    private Button mPushButton;
    private Button mOperaButton;
    private AlivcLivePusher mAlivcLivePusher = null;
    private String mPushUrl = null;
    private SurfaceView mSurfaceView = null;
    private boolean mAsync = false;

    private boolean isPushing = false;
    private Handler mHandler = new Handler();

    private LivePushActivity.PauseState mStateListener = null;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private boolean isFlash = false;
    private boolean mMixExtern = false;
    private boolean mMixMain = false;
    private boolean flashState = true;

    private int snapshotCount = 0;

    private int mQualityMode = 0;
    ScheduledExecutorService mExecutorService = new ScheduledThreadPoolExecutor(5,
            new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
    private boolean videoThreadOn = false;
    private boolean videoThreadOn2 = false;
    private boolean videoThreadOn3 = false;
    private boolean audioThreadOn = false;

//    private MusicDialog mMusicDialog = null;

    private String mAuthString = "?auth_key=%1$d-%2$d-%3$d-%4$s";
    private String mMd5String = "%1$s-%2$d-%3$d-%4$d-%5$s";
    private String mTempUrl = null;
    private String mAuthTime = "";
    private String mPrivacyKey = "";

    Vector<Integer> mDynamicals = new Vector<>();

    public static LivePushFragment newInstance(String url, boolean async, boolean mAudio, boolean mVideoOnly, int cameraId, boolean isFlash, int mode, String authTime, String privacyKey, boolean mixExtern, boolean mixMain) {
        LivePushFragment livePushFragment = new LivePushFragment();
        Bundle bundle = new Bundle();
        bundle.putString(URL_KEY, url);
        bundle.putBoolean(ASYNC_KEY, async);
        bundle.putBoolean(AUDIO_ONLY_KEY, mAudio);
        bundle.putBoolean(VIDEO_ONLY_KEY, mVideoOnly);
        bundle.putInt(QUALITY_MODE_KEY, mode);
        bundle.putInt(CAMERA_ID, cameraId);
        bundle.putBoolean(FLASH_ON, isFlash);
        bundle.putString(AUTH_TIME, authTime);
        bundle.putString(PRIVACY_KEY, privacyKey);
        bundle.putBoolean(MIX_EXTERN, mixExtern);
        bundle.putBoolean(MIX_MAIN, mixMain);
        livePushFragment.setArguments(bundle);
        return livePushFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPushUrl = getArguments().getString(URL_KEY);
            mTempUrl = mPushUrl;
            mAsync = getArguments().getBoolean(ASYNC_KEY, false);
            mCameraId = getArguments().getInt(CAMERA_ID);
            isFlash = getArguments().getBoolean(FLASH_ON, false);
            mMixExtern = getArguments().getBoolean(MIX_EXTERN, false);
            mMixMain = getArguments().getBoolean(MIX_MAIN, false);
            mQualityMode = getArguments().getInt(QUALITY_MODE_KEY);
            mAuthTime = getArguments().getString(AUTH_TIME);
            mPrivacyKey = getArguments().getString(PRIVACY_KEY);
            flashState = isFlash;
        }
        if (mAlivcLivePusher != null) {
            mAlivcLivePusher.setLivePushInfoListener(mPushInfoListener);
            mAlivcLivePusher.setLivePushErrorListener(mPushErrorListener);
            mAlivcLivePusher.setLivePushNetworkListener(mPushNetworkListener);
            isPushing = mAlivcLivePusher.isPushing();
        }

        if (mMixExtern) {
            //startYUV(getActivity());
            //startYUV2(getActivity());
            //startYUV3(getActivity());
        }
        Log.d("MyCallBack","onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.live_push_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mExit = (LinearLayout) view.findViewById(R.id.exit);
        mCamera = (ImageView) view.findViewById(R.id.camera);
        mSnapshot = (ImageView) view.findViewById(R.id.snapshot);
        mCamera.setSelected(true);
        mSnapshot.setSelected(true);
        mPushButton = (Button) view.findViewById(R.id.push_button);
        mPushButton.setSelected(true);
        mOperaButton = (Button) view.findViewById(R.id.opera_button);
        mOperaButton.setSelected(false);
        mExit.setOnClickListener(onClickListener);
        mCamera.setOnClickListener(onClickListener);
        mSnapshot.setOnClickListener(onClickListener);
        mPushButton.setOnClickListener(onClickListener);
        mOperaButton.setOnClickListener(onClickListener);
        if (mMixMain) {
            mCamera.setVisibility(View.GONE);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int id = view.getId();

            if (mAlivcLivePusher == null) {
                if (getActivity() != null) {
                    mAlivcLivePusher = ((LivePushActivity) getActivity()).getLivePusher();
                }

                if (mAlivcLivePusher == null) {
                    return;
                }
            }

            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        switch (id) {
                            case R.id.exit:
                                getActivity().finish();
                                break;
                            case R.id.camera:
                                if (mCameraId == CAMERA_TYPE_FRONT.getCameraId()) {
                                    mCameraId = CAMERA_TYPE_BACK.getCameraId();
                                } else {
                                    mCameraId = CAMERA_TYPE_FRONT.getCameraId();
                                }
                                mAlivcLivePusher.switchCamera();
                                break;
                            case R.id.push_button:
                                final boolean isPush = mPushButton.isSelected();
                                if (isPush) {
                                    startPlay();
                                } else {
//                                    mAlivcLivePusher.stopPush();
//                                    stopPcm();
//                                    mOperaButton.post(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            mOperaButton.setText(getString(R.string.pause_button));
//                                            mOperaButton.setSelected(false);
//                                        }
//                                    });
//                                    if (mStateListener != null) {
//                                        mStateListener.updatePause(false);
//                                    }
                                    getActivity().finish();
                                }

                                mPushButton.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mPushButton.setText(isPush ? "结束" : "开始");
                                        mPushButton.setSelected(!isPush);
                                    }
                                });

                                break;
                            case R.id.opera_button:
                                final boolean isPause = mOperaButton.isSelected();
                                finize(isPause);
                                break;
                            case R.id.snapshot:
                                mAlivcLivePusher.snapshot(1, 0, new AlivcSnapshotListener() {
                                    @Override
                                    public void onSnapshot(Bitmap bmp) {
                                        String dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss-SS").format(new Date());
                                        File f = new File("/sdcard/", "snapshot-" + dateFormat + ".png");
                                        if (f.exists()) {
                                            f.delete();
                                        }
                                        try {
                                            FileOutputStream out = new FileOutputStream(f);
                                            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
                                            out.flush();
                                            out.close();
                                        } catch (FileNotFoundException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        showDialog("截图已保存：" + "/sdcard/snapshot-" + dateFormat + ".png");
                                    }
                                });
                                break;
                            default:
                                break;
                        }
                    } catch (IllegalArgumentException e) {
                        showDialog(e.getMessage());
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        showDialog(e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

        }
    };

    /**
     * 开启
     */
    public void  startPlay(){
        if (mAsync) {
            mAlivcLivePusher.startPushAysnc(getAuthString(mAuthTime));
        } else {
            mAlivcLivePusher.startPush(getAuthString(mAuthTime));
        }
        if (mMixExtern) {
            //startMixPCM(getActivity());
        } else if (mMixMain) {
            startPCM(getActivity());
        }
    }

    /**
     * 离开和恢复
     * @param isPause
     */
    public void finize(boolean isPause){
        if (!isPause) {
            mAlivcLivePusher.pause();
        } else {
            if (mAsync) {
                mAlivcLivePusher.resumeAsync();
            } else {
                mAlivcLivePusher.resume();
            }
        }

        if (mStateListener != null) {
            mStateListener.updatePause(!isPause);
        }
        mOperaButton.post(new Runnable() {
            @Override
            public void run() {
                mOperaButton.setText(isPause ? getString(R.string.pause_button) : getString(R.string.resume_button));
                mOperaButton.setSelected(!isPause);
            }
        });
    }
    public void setAlivcLivePusher(AlivcLivePusher alivcLivePusher) {
        this.mAlivcLivePusher = alivcLivePusher;
    }

    public void setStateListener(LivePushActivity.PauseState listener) {
        this.mStateListener = listener;
    }

    public void setSurfaceView(SurfaceView surfaceView) {
        this.mSurfaceView = surfaceView;
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

    AlivcLivePushErrorListener mPushErrorListener = new AlivcLivePushErrorListener() {

        @Override
        public void onSystemError(AlivcLivePusher livePusher, AlivcLivePushError error) {
        }

        @Override
        public void onSDKError(AlivcLivePusher livePusher, AlivcLivePushError error) {
            if (error != null) {
            }
        }
    };

    AlivcLivePushNetworkListener mPushNetworkListener = new AlivcLivePushNetworkListener() {
        @Override
        public void onNetworkPoor(AlivcLivePusher pusher) {
            showNetWorkDialog(getString(R.string.network_poor));
        }

        @Override
        public void onNetworkRecovery(AlivcLivePusher pusher) {
            showToast(getString(R.string.network_recovery));
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
        public void onConnectionLost(AlivcLivePusher pusher) {
//            showToast("推流已断开");
        }

        @Override
        public String onPushURLAuthenticationOverdue(AlivcLivePusher pusher) {
//            showDialog("流即将过期，请更换url");
            return getAuthString(mAuthTime);
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


    @Override
    public void onDestroy() {
        //stopPcm();
        //stopYUV();
        super.onDestroy();
        if (mExecutorService != null && !mExecutorService.isShutdown()) {
            mExecutorService.shutdown();
        }
    }

    private void showToast(final String text) {
        if (getActivity() == null || text == null) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void showToastShort(final String text) {
        if (getActivity() == null || text == null) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
    }

    private void showDialog(final String message) {
        if (getActivity() == null || message == null) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle(getString(R.string.dialog_title));
                    dialog.setMessage(message);
                    dialog.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    private void showNetWorkDialog(final String message) {
        if (getActivity() == null || message == null) {
            return;
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle(getString(R.string.dialog_title));
                    dialog.setMessage(message);
                    dialog.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    dialog.setNeutralButton(getString(R.string.reconnect), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try {
                                mAlivcLivePusher.reconnectPushAsync(null);
                            } catch (IllegalStateException e) {

                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    @Override
    public void run() {
        if (mAlivcLivePusher != null) {
            try {
                isPushing = mAlivcLivePusher.isNetworkPushing();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        mHandler.postDelayed(this, REFRESH_INTERVAL);

    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.post(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(this);
    }

    public interface BeautyListener {
        void onBeautySwitch(boolean beauty);
    }


    private String getMD5(String string) {

        byte[] hash;

        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    private String getUri(String url) {
        String result = "";
        String temp = url.substring(7);
        if (temp != null && !temp.isEmpty()) {
            result = temp.substring(temp.indexOf("/"));
        }
        return result;
    }

//    private void showTimeDialog() {
//        final EditText et = new EditText(getActivity());
//        et.setInputType(InputType.TYPE_CLASS_NUMBER);
//        new AlertDialog.Builder(getContext()).setTitle("输入流鉴权时间")
//                .setView(et)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String input = et.getText().toString();
//                        getAuthString(input);
//                    }
//                })
//                .setNegativeButton("取消", null)
//                .show();
//    }

    private String getAuthString(String time) {
        if (!time.isEmpty() && !mPrivacyKey.isEmpty()) {
            long tempTime = (System.currentTimeMillis() + Integer.valueOf(time)) / 1000;
            String tempprivacyKey = String.format(mMd5String, getUri(mPushUrl), tempTime, 0, 0, mPrivacyKey);
            String auth = String.format(mAuthString, tempTime, 0, 0, getMD5(tempprivacyKey));
            mTempUrl = mPushUrl + auth;
        } else {
            mTempUrl = mPushUrl;
        }
        return mTempUrl;
    }

    /*public void startYUV(final Context context) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(mMixMain) {
                    //mAlivcLivePusher.setMainStreamPosition(0.5f, 0.5f, 0.5f, 0.5f);
                }
                videoThreadOn = true;
                byte[] yuv;
                int mixvideId = 0;
                InputStream myInput = null;
                try {
                    File f = new File("/sdcard/alivc_resource/me2.yuv");
                    myInput = new FileInputStream(f);
                    mixvideId = mAlivcLivePusher.addMixVideo(AlivcImageFormat.IMAGE_FORMAT_YUVNV12,1080,720,0,0.35f,0.78f,0.3f,0.2f);
                    byte[] buffer = new byte[1080*720*3/2];
                    int length = myInput.read(buffer);
                    //发数据
                    while(length > 0 && videoThreadOn)
                    {
                        mAlivcLivePusher.inputMixVideoData(mixvideId,buffer,1080,720, 1080*720*3/2,System.nanoTime()/1000,0);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //发数据
                        length = myInput.read(buffer);
                        if(length < 1080*720*3/2)
                        {
                            myInput.close();
                            myInput = new FileInputStream(f);
                            length = myInput.read(buffer);
                        }
                    }
                    mAlivcLivePusher.removeMixVideo(mixvideId);
                    myInput.close();
                    videoThreadOn = false;
                    mAlivcLivePusher.removeMixVideo(mixvideId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void startYUV2(final Context context) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int mixvideId = 0;
                videoThreadOn2 = true;
                byte[] yuv;
                InputStream myInput = null;
                try {
                    File f = new File("/sdcard/alivc_resource/screenrecord3.yuv");
                    myInput = new FileInputStream(f);
                    mixvideId = mAlivcLivePusher.addMixVideo(AlivcImageFormat.IMAGE_FORMAT_YUVNV12,720,1064,0,0.7f,0.78f,0.2f,0.2f);
                    byte[] buffer = new byte[1064*720*3/2];
                    int length = myInput.read(buffer);
                    //发数据
                    while(length > 0 && videoThreadOn2)
                    {
                        mAlivcLivePusher.inputMixVideoData(mixvideId,buffer,720, 1064, 720*1064*3/2,System.nanoTime()/1000,0);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //发数据
                        length = myInput.read(buffer);
                        if(length < 720*1064*3/2)
                        {
                            myInput.close();
                            myInput = new FileInputStream(f);
                            length = myInput.read(buffer);
                        }
                    }
                    mAlivcLivePusher.removeMixVideo(mixvideId);
                    myInput.close();
                    videoThreadOn2 = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void startYUV3(final Context context) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                videoThreadOn3 = true;
                byte[] yuv;
                int mixvideId = 0;
                int framecount = 0;
                InputStream myInput = null;
                try {
                    File f = new File("/sdcard/alivc_resource/he3.yuv");
                    myInput = new FileInputStream(f);
                    mixvideId = mAlivcLivePusher.addMixVideo(AlivcImageFormat.IMAGE_FORMAT_YUVNV12,1080,720,0,0.0f,0.78f,0.3f,0.2f);
                    byte[] buffer = new byte[1080*720*3/2];
                    int length = myInput.read(buffer);
                    //发数据
                    while(length > 0 && videoThreadOn3)
                    {
                        mAlivcLivePusher.inputMixVideoData(mixvideId,buffer,1080, 720, 1080*720*3/2,System.nanoTime()/1000,0);
                        try {
                            Thread.sleep(40);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        framecount++;
                        if(framecount == 125) {
                            mAlivcLivePusher.mixStreamRequireMain(mixvideId, true);
                        }
                        //发数据
                        length = myInput.read(buffer);
                        if(length < 1080*720*3/2)
                        {
                            myInput.close();
                            myInput = new FileInputStream(f);
                            length = myInput.read(buffer);
                            //mAlivcLivePusher.removeMixVideo(mixvideId);
                            //break;
                        }
                    }
                    mAlivcLivePusher.removeMixVideo(mixvideId);
                    myInput.close();
                    videoThreadOn3 = false;
                    mAlivcLivePusher.removeMixVideo(mixvideId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }*/

    /*private void stopYUV() {
        videoThreadOn = false;
        videoThreadOn2 = false;
        videoThreadOn3 = false;
    }

    private void startMixPCM(final Context context) {
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                audioThreadOn = true;
                byte[] pcm;
                int offset = 0;
                int mixAudioId = 0;
                InputStream myInput = null;
                OutputStream myOutput = null;
                boolean reUse = false;
                try {
                    File f = new File("/sdcard/alivc_resource/441.pcm");
                    myInput = new FileInputStream(f);
                    mixAudioId = mAlivcLivePusher.addMixAudio(1, AlivcSoundFormat.SOUND_FORMAT_S16,44100);
                    byte[] buffer = new byte[2048];
                    int length = myInput.read(buffer,0,2048);
                    offset += length;
                    while(length > 0 && audioThreadOn)
                    {
                        reUse = mAlivcLivePusher.inputMixAudioData(mixAudioId,buffer,length, System.nanoTime()/1000);
                        if(reUse) {
                            //发数据
                            length = myInput.read(buffer);
                            offset += length;
                            if (length < 2048) {
                                myInput.close();
                                offset = 0;
                                myInput = new FileInputStream(f);
                                length = myInput.read(buffer);
                                offset += length;
                            }
                        }
                        try {
                            Thread.sleep(3);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    myInput.close();
                    mAlivcLivePusher.removeMixAudio(mixAudioId);
                    audioThreadOn = false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

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

    public interface DynamicListern {
        void onAddDynamic();

        void onRemoveDynamic();
    }

    public static void saveBitmap(Bitmap pBitmap, File savePath, String fileName, Bitmap.CompressFormat format) {
        if (format == null) {
            format = Bitmap.CompressFormat.JPEG;
        }
        // 保存图片
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(savePath, fileName));
            if (fos != null) {
                pBitmap.compress(format, 100, fos);
                fos.flush();
            }
        } catch (IOException pE) {
        } finally {
            try {
                fos.close();
            } catch (IOException e) {

            }
        }
    }
}
