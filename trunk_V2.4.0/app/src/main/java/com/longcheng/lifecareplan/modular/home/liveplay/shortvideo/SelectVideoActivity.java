package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.ApplyXieYiActitvty;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushContract;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.upload.TCVideoJoinChooseActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.BaseScrollPickerView;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.StringScrollPicker;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.AblumUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.CircleProgressBar;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.ugc.TXRecordCommon;
import com.tencent.ugc.TXUGCRecord;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2019/11/11 17:44
 * 意图：h5上传视频
 */
public class SelectVideoActivity extends BaseActivity {
    @BindView(R.id.preview_view)
    TXCloudVideoView previewView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.layout_rigth)
    LinearLayout layoutRigth;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetop_iv_rigth;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.layout_time)
    LinearLayout layoutTime;
    @BindView(R.id.layout_del)
    LinearLayout layoutDel;
    @BindView(R.id.layout_start)
    LinearLayout layoutStart;
    @BindView(R.id.layout_ok)
    LinearLayout layoutOk;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.circleProgressBar)
    CircleProgressBar circleProgressBar;

    String cover_url;
    String city;
    double phone_user_latitude;
    double phone_user_longitude;
    String cost;
    LocationUtils mLocationUtils;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_left:
                back();
                break;
            case R.id.layout_rigth:
                switchCamera();
                break;
            case R.id.layout_ok:
                stopRecord();
                break;
            case R.id.layout_del:
                delRecord();
                break;
            case R.id.layout_start:
                switchRecord();
                break;
        }

    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_shortvideo;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        pagetop_iv_rigth.setVisibility(View.VISIBLE);
        layoutLeft.setOnClickListener(this);
        layoutRigth.setOnClickListener(this);
        layoutStart.setOnClickListener(this);
        layoutDel.setOnClickListener(this);
        layoutOk.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        layoutBottom.setVisibility(View.INVISIBLE);
    }

    /**
     * 更新录制按钮状态
     */
    private void updateRecordBtnView() {
        Log.e("updateRecordBtnView", "mRecording=" + mRecording + "  ;mPause=" + mPause);
        if (!mRecording && !mPause) {//初始化
            tvTime.setText("00:00");
            circleProgressBar.setVisibility(View.GONE);
            layoutTime.setVisibility(View.GONE);
            layoutDel.setVisibility(View.GONE);
            layoutOk.setVisibility(View.GONE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_start);
        } else if (mRecording && !mPause) {//录制中
            layoutTime.setVisibility(View.VISIBLE);
            layoutDel.setVisibility(View.GONE);
            layoutOk.setVisibility(View.GONE);
            circleProgressBar.setVisibility(View.VISIBLE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_pause);
        } else if (mRecording && mPause) {//暂停
            layoutTime.setVisibility(View.VISIBLE);
            layoutDel.setVisibility(View.VISIBLE);
            layoutOk.setVisibility(View.VISIBLE);
            circleProgressBar.setVisibility(View.VISIBLE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_restart);
        }
    }


    /**
     * **************************************************************************************
     */
    private int mMinDuration = 3 * 1000;
    private int mMaxDuration = 90 * 1000;
    private boolean mRecording = false;
    private boolean mPause = false;
    private long mDuration; // 视频总时长
    private TXUGCRecord mTXCameraRecord;
    private boolean mStartPreview = false;
    private long mLastClickTime;
    private TXRecordCommon.TXRecordResult mTXRecordResult;
    private boolean mFront = true;

    private void startCameraPreview() {
        if (mStartPreview) {
            return;
        }
        mStartPreview = true;
        if (mTXCameraRecord == null) {
            mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());
            mTXCameraRecord.setVideoRecordListener(new TXRecordCommon.ITXVideoRecordListener() {
                @Override
                public void onRecordEvent(int event, Bundle param) {
                    TXCLog.d(TAG, "onRecordEvent event id = " + event);
                    if (event == TXRecordCommon.EVT_ID_PAUSE) {
//                    mRecordProgressView.clipComplete();
                    } else if (event == TXRecordCommon.EVT_CAMERA_CANNOT_USE) {
//                    ToastUtils.showToast("摄像头打开失败，请检查权限");
                        mStartPreview = false;
                        startCameraPreview();
                    } else if (event == TXRecordCommon.EVT_MIC_CANNOT_USE) {
//                    ToastUtils.showToast("摄像头打开失败，请检查权限");
                        mStartPreview = false;
                        startCameraPreview();
                    } else if (event == TXRecordCommon.EVT_ID_RESUME) {

                    }
                }

                @Override
                public void onRecordProgress(long milliSecond) {
                    TXCLog.i(TAG, "onRecordProgress, milliSecond = " + milliSecond);
                    if (circleProgressBar == null) {
                        return;
                    }
                    float timeSecondFloat = milliSecond / 1000f;
                    circleProgressBar.setProgress((int) timeSecondFloat);
                    int timeSecond = Math.round(timeSecondFloat);
                    String m = String.format(Locale.CHINA, "%02d", timeSecond / 60);
                    String s = String.format(Locale.CHINA, "%02d", timeSecond % 60);
                    tvTime.setText(m + ":" + s);
                }

                @Override
                public void onRecordComplete(TXRecordCommon.TXRecordResult result) {
                    mTXRecordResult = result;
                    TXCLog.i("startPreview", "onRecordComplete, result retCode = " + result.retCode + ", descMsg = " + result.descMsg + ", videoPath = " + result.videoPath + ", coverPath = " + result.coverPath);
                    if (mTXRecordResult.retCode < 0) {
                        mRecording = false;
                        if (mTXCameraRecord != null) {
                            int timeSecond = mTXCameraRecord.getPartsManager().getDuration() / 1000;
                            String m = String.format(Locale.CHINA, "%02d", timeSecond / 60);
                            String s = String.format(Locale.CHINA, "%02d", timeSecond % 60);
                            tvTime.setText(m + ":" + s);
                        }
                        ToastUtils.showToast("录制失败");
                    } else {
                        mDuration = mTXCameraRecord.getPartsManager().getDuration();
                        delRecord();
//                    if (mNeedEditer) {
//                        startEditVideo();
//                    } else {
                        startPreview();
//                    }
                    }
                }
            });
        }
        TXRecordCommon.TXUGCSimpleConfig simpleConfig = new TXRecordCommon.TXUGCSimpleConfig();
        simpleConfig.videoQuality = TXRecordCommon.VIDEO_QUALITY_MEDIUM;
        simpleConfig.minDuration = mMinDuration;
        simpleConfig.maxDuration = mMaxDuration;
        simpleConfig.isFront = mFront;
        simpleConfig.touchFocus = false; // 手动对焦和自动对焦切换需要重新开启预览
        simpleConfig.needEdit = false;
        mTXCameraRecord.setRecordSpeed(TXRecordCommon.RECORD_SPEED_NORMAL);
        mTXCameraRecord.startCameraSimplePreview(simpleConfig, previewView);
        mTXCameraRecord.getBeautyManager().setBeautyStyle(0);
        mTXCameraRecord.getBeautyManager().setBeautyLevel(5);
        mTXCameraRecord.getBeautyManager().setWhitenessLevel(1);
        mTXCameraRecord.getBeautyManager().setRuddyLevel(0);
        mTXCameraRecord.getBeautyManager().setFaceSlimLevel(0);
        mTXCameraRecord.getBeautyManager().setEyeScaleLevel(0);
        mTXCameraRecord.setSpecialRatio(5 / 10.0f);
        mTXCameraRecord.getBeautyManager().setFaceBeautyLevel(0);

    }

    /**
     * 完成后跳转预览
     */
    private void startPreview() {
        if (mTXRecordResult != null && (mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK
                || mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK_REACHED_MAXDURATION
                || mTXRecordResult.retCode == TXRecordCommon.RECORD_RESULT_OK_LESS_THAN_MINDURATION)) {
            Log.e("startPreview", "coverPath=" + mTXRecordResult.coverPath);
            Intent intent = new Intent(getApplicationContext(), TCVideoPreviewActivity.class);
            intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD);
            intent.putExtra(TCConstants.VIDEO_RECORD_RESULT, mTXRecordResult.retCode);
            intent.putExtra(TCConstants.VIDEO_RECORD_DESCMSG, mTXRecordResult.descMsg);
            intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, mTXRecordResult.videoPath);
            intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mTXRecordResult.coverPath);
            intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, mDuration);
            intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, TXRecordCommon.VIDEO_RESOLUTION_540_960);
            intent.putExtra("skip_type", "DoctorApplySelectVedio");
            startActivity(intent);
            doFinish();
        } else {
            ToastUtils.showToast("录制失败");
        }
    }

    private void switchCamera() {
        mFront = !mFront;
        if (mTXCameraRecord != null) {
            mTXCameraRecord.switchCamera(mFront);
        }
    }

    private void switchRecord() {
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - mLastClickTime < 200) {
            return;
        }
        Log.e("updateRecordBtnView", "currentClickTime    =" + currentClickTime + "  ;mLastClickTime=" + mLastClickTime);
        if (mRecording) {
            if (mPause) {
                if (mTXCameraRecord == null) {
                    return;
                }
                if (mTXCameraRecord.getPartsManager().getPartsPathList().size() == 0) {
                    startRecord();
                } else {
                    resumeRecord();
                }
            } else {
                if (currentClickTime - mLastClickTime < mMinDuration) {
                    ToastUtils.showToast("视频录制太短");
                    return;
                }
                pauseRecord();
            }
        } else {
            startRecord();
        }
    }

    private void resumeRecord() {
        if (mTXCameraRecord == null) {
            return;
        }
        int startResult = mTXCameraRecord.resumeRecord();
        if (startResult != TXRecordCommon.START_RECORD_OK) {
            TXCLog.i(TAG, "resumeRecord, startResult = " + startResult);
            if (startResult == TXRecordCommon.START_RECORD_ERR_NOT_INIT) {
                ToastUtils.showToast("别着急，画面还没出来");
            } else if (startResult == TXRecordCommon.START_RECORD_ERR_IS_IN_RECORDING) {
                ToastUtils.showToast("还有录制的任务没有结束");
            }
            return;
        }
        mPause = false;
        updateRecordBtnView();
    }

    private void pauseRecord() {
        Log.e("updateRecordBtnView", "pauseRecord    =" + mRecording + "  ;mPause=" + mPause);
        if (mTXCameraRecord != null) {
            mTXCameraRecord.pauseRecord();
        }
        mPause = true;
        updateRecordBtnView();
    }

    private void stopRecord() {
        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopBGM();
            mTXCameraRecord.stopRecord();
        }
        mRecording = false;
        mPause = false;
        updateRecordBtnView();
    }

    private void delRecord() {
        if (mTXCameraRecord != null) {
            mTXCameraRecord.getPartsManager().deleteAllParts();
        }
        circleProgressBar.setProgress(0);
        mRecording = false;
        mPause = false;
        updateRecordBtnView();
    }

    private void startRecord() {
        // 在开始录制的时候，就不能再让activity旋转了，否则生成视频出错
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int mobileRotation = this.getWindowManager().getDefaultDisplay().getRotation();
            switch (mobileRotation) {
                case Surface.ROTATION_90:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    break;
                case Surface.ROTATION_270:
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
                    break;
                default:
                    break;
            }
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        if (mTXCameraRecord == null) {
            mTXCameraRecord = TXUGCRecord.getInstance(this.getApplicationContext());
        }
        mLastClickTime = System.currentTimeMillis();
        String customVideoPath = getCustomVideoOutputPath();
        String customCoverPath = customVideoPath.replace(".mp4", ".jpg");
        String customPartFolder = getCustomVideoPartFolder();

        int result = mTXCameraRecord.startRecord(customVideoPath, customPartFolder, customCoverPath);
        if (result != TXRecordCommon.START_RECORD_OK) {
            if (result == TXRecordCommon.START_RECORD_ERR_NOT_INIT) {
                ToastUtils.showToast("别着急，画面还没出来");
            } else if (result == TXRecordCommon.START_RECORD_ERR_IS_IN_RECORDING) {
                ToastUtils.showToast("还有录制的任务没有结束");
            } else if (result == TXRecordCommon.START_RECORD_ERR_VIDEO_PATH_IS_EMPTY) {
                ToastUtils.showToast("传入的视频路径为空");
            } else if (result == TXRecordCommon.START_RECORD_ERR_API_IS_LOWER_THAN_18) {
                ToastUtils.showToast("版本太低");
            } else if (result == TXRecordCommon.START_RECORD_ERR_LICENCE_VERIFICATION_FAILED) {
                // 增加了TXUgcSDK.licence校验的返回错误码
                ToastUtils.showToast("licence校验失败");
            }
            return;
        }
        mRecording = true;
        mPause = false;
        updateRecordBtnView();
    }

    //自定义分段视频存储目录
    private String getCustomVideoPartFolder() {
        String outputDir = Environment.getExternalStorageDirectory() + File.separator + "longcheng_video";
        return outputDir;
    }

    private String getCustomVideoOutputPath() {
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time = sdf.format(new Date(currentTime));
        String outputDir = Environment.getExternalStorageDirectory() + File.separator + "longcheng_video";
        File outputFolder = new File(outputDir);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        String tempOutputPath = outputDir + File.separator + "TXUGC_" + time + ".mp4";
        return tempOutputPath;
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTXCameraRecord != null) {
            mTXCameraRecord.setVideoProcessListener(null); // 这里要取消监听，否则在上面的回调中又会重新开启预览
            mTXCameraRecord.stopCameraPreview();
            mStartPreview = false;
        }
        if (mRecording && !mPause) {
            pauseRecord();
        }
        if (mTXCameraRecord != null) {
            mTXCameraRecord.pauseBGM();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void releaseRecord() {
        if (mTXCameraRecord != null) {
            mTXCameraRecord.stopBGM();
            mTXCameraRecord.stopCameraPreview();
            mTXCameraRecord.setVideoRecordListener(null);
            mTXCameraRecord.getPartsManager().deleteAllParts();
            mTXCameraRecord.release();
            mTXCameraRecord = null;
            mStartPreview = false;
        }
    }

    private void back() {
        if (!mRecording) {
            releaseRecord();
            doFinish();
            return;
        }
        if (mPause) {
            releaseRecord();
            doFinish();
        } else {
            pauseRecord();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }


    //*********************************************************************************************

}
