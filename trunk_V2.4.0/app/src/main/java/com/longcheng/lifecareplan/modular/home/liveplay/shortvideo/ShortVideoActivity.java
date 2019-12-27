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
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.ApplyXieYiActitvty;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushContract;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
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
 * 意图：短视频
 */
public class ShortVideoActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {
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
    @BindView(R.id.video_pickerview)
    StringScrollPicker videoPickerview;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.layout_shortVideo)
    LinearLayout layoutShortVideo;
    @BindView(R.id.layout_live)
    LinearLayout layoutLive;
    @BindView(R.id.layout_livetitle)
    LinearLayout layoutLivetitle;
    @BindView(R.id.iv_thumb)
    RoundedImageView ivThumb;
    @BindView(R.id.tv_livetitle)
    EditText tvLivetitle;
    @BindView(R.id.layout_livecity)
    LinearLayout layoutLivecity;
    @BindView(R.id.tv_livecity)
    TextView tvLivecity;
    @BindView(R.id.tv_livestart)
    TextView tvLivestart;
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
            case R.id.iv_thumb:
                mAblumUtils.onAddAblumClick();
                break;
            case R.id.tv_livestart:
                mPresent.getUserLiveStatus();
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
        ivThumb.setOnClickListener(this);
        tvLivestart.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(tvLivetitle, 40);
        videoPickerview.setOnSelectedListener(new BaseScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(BaseScrollPickerView baseScrollPickerView, int position) {
                Log.i(TAG, "onSelected:" + position);
                if (position == 0) {
                    layoutShortVideo.setVisibility(View.GONE);
                    layoutLive.setVisibility(View.GONE);
//                    ToastUtils.showToast("功能开发中");
                    layoutRigth.setVisibility(View.GONE);
                    Intent intent = new Intent(mActivity, TCVideoJoinChooseActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("CHOOSE_TYPE", TCVideoJoinChooseActivity.TYPE_PUBLISH_CHOOSE);
                    startActivity(intent);
                } else if (position == 1) {
                    layoutShortVideo.setVisibility(View.VISIBLE);
                    layoutLive.setVisibility(View.GONE);
                    layoutRigth.setVisibility(View.VISIBLE);
                } else if (position == 2) {
                    layoutRigth.setVisibility(View.GONE);
                    layoutShortVideo.setVisibility(View.GONE);
                    layoutLive.setVisibility(View.VISIBLE);
                    if (mLocationUtils == null) {
                        mLocationUtils = new LocationUtils();
                    }
                    city = mLocationUtils.getAddressCity(mContext);
                    double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mContext);
                    phone_user_latitude = mLngAndLat[0];
                    phone_user_longitude = mLngAndLat[1];
                    tvLivecity.setText("" + city);
                }
            }
        });
    }

    AblumUtils mAblumUtils;

    @Override
    public void initDataAfter() {
        setScreenWake();
        List<String> strings = new ArrayList<>(3);
        strings.add("上传");
        strings.add("拍摄");
        strings.add("直播");
        videoPickerview.setDrawAllItem(true);
        videoPickerview.setData(strings);

        layoutLivetitle.getBackground().setAlpha(50);
        layoutLivecity.getBackground().setAlpha(50);
        mAblumUtils = new AblumUtils(this, mHandler, UPDATEABLUM);
        mAblumUtils.setCropStaus(false);
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
            layoutBottom.setVisibility(View.VISIBLE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_start);
        } else if (mRecording && !mPause) {//录制中
            layoutTime.setVisibility(View.VISIBLE);
            layoutDel.setVisibility(View.GONE);
            layoutOk.setVisibility(View.GONE);
            circleProgressBar.setVisibility(View.VISIBLE);
            layoutBottom.setVisibility(View.INVISIBLE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_pause);
        } else if (mRecording && mPause) {//暂停
            layoutTime.setVisibility(View.VISIBLE);
            layoutDel.setVisibility(View.VISIBLE);
            layoutOk.setVisibility(View.VISIBLE);
            layoutBottom.setVisibility(View.INVISIBLE);
            circleProgressBar.setVisibility(View.VISIBLE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_restart);
        }
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
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            cover_url = responseBean.getData();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void openRoomPaySuccess(BasicResponse<LiveStatusInfo> responseBean) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            LiveStatusInfo mLiveStatusInfo = responseBean.getData();
            if (mLiveStatusInfo != null) {
                String pushUrl = mLiveStatusInfo.getPushUrl();
                String live_room_id = mLiveStatusInfo.getLive_room_id();
                LivePushActivity.startActivity(this, pushUrl, live_room_id);
            }
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
    }

    @Override
    public void getUserLiveStatusSuccess(BasicResponse<LiveStatusInfo> responseBean) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            LiveStatusInfo mLiveStatusInfo = (LiveStatusInfo) responseBean.getData();
            if (mLiveStatusInfo != null) {
                int status = mLiveStatusInfo.getStatus();
                if (status == 1) {
                    int is_charge = mLiveStatusInfo.getIs_charge();//是否收费 1：收费 2：免费
                    cost = mLiveStatusInfo.getCost();
                    if (TextUtils.isEmpty(cost)) {
                        cost = "0";
                    }
                    if (is_charge == 1) {
                        showPopupWindow();
                    } else if (is_charge == 2) {
                        mHandler.sendEmptyMessage(openRoomPay);
                    }

                } else if (status == -1) {
                    ToastUtils.showToast("申请已驳回");
                } else {
                    ToastUtils.showToast("申请审核中");
                }
            }
        } else if (errcode == -1) {
            ToastUtils.showToast("" + responseBean.getMsg());
            Intent intent = new Intent(mActivity, ApplyXieYiActitvty.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
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

    }

    @Override
    public void showDialog() {
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
        LoadingDialogAnim.dismiss(mContext);
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
            startActivity(intent);
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
        setScreenRelease();
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
    TextView tvskb;
    MyDialog selectDialog;

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_live_pay);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            tvskb = (TextView) selectDialog.findViewById(R.id.tvskb);
            TextView tv_cancel = (TextView) selectDialog.findViewById(R.id.tv_cancel);
            TextView tv_sure = (TextView) selectDialog.findViewById(R.id.tv_sure);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    mHandler.sendEmptyMessage(openRoomPay);
                }
            });
        } else {
            selectDialog.show();
        }
        tvskb.setText("" + cost);
    }

    /*
     * 调用相册
     */
    protected static final int UPDATEABLUM = 3;
    protected static final int SETRESULT = 4;
    protected static final int openRoomPay = 5;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case openRoomPay:
                    if (TextUtils.isEmpty(cover_url)) {
                        ToastUtils.showToast("请选择封面图");
                        break;
                    }
                    String title = tvLivetitle.getText().toString();
                    if (TextUtils.isEmpty(title)) {
                        ToastUtils.showToast("请输入直播主题");
                        break;
                    }
                    mPresent.openRoomPay(title, cover_url, city, phone_user_longitude, phone_user_latitude, cost);
                    break;
                case UPDATEABLUM:
                    Bitmap mBitmap = (Bitmap) msg.obj;
                    ivThumb.setImageBitmap(mBitmap);
                    //上传头像
                    String file = mAblumUtils.Bitmap2StrByBase64(mBitmap);
                    mPresent.uploadImg(file);
                    break;
                case SETRESULT:
                    int requestCode = msg.arg1;
                    int resultCode = msg.arg2;
                    Intent data = (Intent) msg.obj;
                    mAblumUtils.setResult(requestCode, resultCode, data);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == mAblumUtils.RESULTCAMERA || requestCode == mAblumUtils.RESULTGALLERY
                    || requestCode == mAblumUtils.RESULTCROP) {
                Message msgMessage = new Message();
                msgMessage.arg1 = requestCode;
                msgMessage.arg2 = resultCode;
                msgMessage.obj = data;
                msgMessage.what = SETRESULT;
                mHandler.sendMessage(msgMessage);
                msgMessage = null;
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
