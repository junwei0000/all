package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushContract;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.bean.MVideoItemInfo;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.demo.videouploader.common.view.NumberProgressBar;
import com.tencent.liteav.demo.videouploader.videoupload.TXUGCPublish;
import com.tencent.liteav.demo.videouploader.videoupload.TXUGCPublishTypeDef;
import com.tencent.rtmp.TXLog;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2019/11/11 17:44
 * 意图：上传短视频
 */
public class UpLoadVideoActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.layout_rigth)
    LinearLayout layoutRigth;
    @BindView(R.id.iv_thumb)
    RoundedImageView ivThumb;
    @BindView(R.id.tv_cont)
    EditText tv_cont;
    @BindView(R.id.tv_title)
    EditText tv_title;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.layout_city)
    LinearLayout layoutCity;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.tv_lines)
    TextView tv_lines;

    LocationUtils mLocationUtils;
    String city;
    double phone_user_latitude;
    double phone_user_longitude;
    private int mVideoSource; // 视频来源
    private String mVideoPath;
    private String mCoverImagePath;
    //录制界面传过来的视频分辨率
    private int mVideoResolution;
    //视频时长（ms）
    private long mVideoDuration;

    private String title, cont;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_left:
                back();
                break;
            case R.id.iv_thumb:
                Intent intent = new Intent(getApplicationContext(), VideoEditCoverActivity.class);
                intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, mVideoSource);
                intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, mVideoPath);
                intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mCoverImagePath);
                intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, mVideoDuration);
                intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, mVideoResolution);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_upload:
                title = tv_title.getText().toString();
                if (TextUtils.isEmpty(title)) {
                    ToastUtils.showToast("请输入主题");
                    break;
                }
                cont = tv_cont.getText().toString();
                if (TextUtils.isEmpty(cont)) {
                    ToastUtils.showToast("说点什么…");
                    break;
                }
                mPresent.getUploadVideoSignature();
                break;
        }

    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_shortvideo_upload;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
        tvUpload.setOnClickListener(this);
        ivThumb.setOnClickListener(this);
        tv_lines.getBackground().setAlpha(60);
    }


    @Override
    public void initDataAfter() {
        if (mLocationUtils == null) {
            mLocationUtils = new LocationUtils();
        }
        city = mLocationUtils.getAddressCity(mContext);
        double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mContext);
        phone_user_latitude = mLngAndLat[0];
        phone_user_longitude = mLngAndLat[1];
        tvCity.setText("" + city);

        mVideoSource = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
        mVideoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        mVideoDuration = getIntent().getLongExtra(TCConstants.VIDEO_RECORD_DURATION, 0);
        mVideoResolution = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_RESOLUTION, -1);
        mCoverImagePath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
        setCover();
    }

    private void setCover() {
        TXCLog.d("onThumbnail", "mCoverImagePath = " + mCoverImagePath);
        if (mCoverImagePath != null && !mCoverImagePath.isEmpty()) {
            Glide.with(this).load(Uri.fromFile(new File(mCoverImagePath)))
                    .into(ivThumb);
        }
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mRxAppCompatActivity, this);
    }


    @Override
    public void upLoadVideoSuccess(BasicResponse responseBean) {
        ToastUtils.showToast("发布成功");
        TCVideoPreviewActivity.mPrActivity.finish();
        doFinish();
    }

    @Override
    public void backSignSuccess(BasicResponse<VideoGetSignatureInfo> responseBean) {
        String signature = responseBean.getData().getSignature();
        if (!TextUtils.isEmpty(signature)) {
            publish(signature);
        } else {
            ToastUtils.showToast("获取签名信息错误");
        }
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

    }

    @Override
    public void giveGiftSuccess(BasicResponse responseBean) {

    }

    @Override
    public void videoDetailSuccess(BasicResponse<MVideoItemInfo> responseBean) {

    }

    @Override
    public void Error() {
        ToastUtils.showToast(R.string.net_tishi);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void showGiftDialog() {

    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    private void back() {
        doFinish();
    }

    @Override
    public void onBackPressed() {
        back();
    }


    /**
     * ********************************发布视频***********************************
     */
    @Override
    public void onDestroy() {
        if (mTXugcPublish != null) {
            mTXugcPublish.canclePublish();
            isCancelPublish = false;
        }
        super.onDestroy();
    }

    private TXUGCPublish mTXugcPublish;
    private boolean isCancelPublish = false;
    MyDialog selectDialog;
    NumberProgressBar uploader_pb_loading;

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.live_videouploader_progress);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效

            ImageView uploader_iv_stop = (ImageView) selectDialog.findViewById(R.id.uploader_iv_stop);
            uploader_pb_loading = (NumberProgressBar) selectDialog.findViewById(R.id.uploader_pb_loading);
            uploader_pb_loading.setReachedBarColor(getResources().getColor(R.color.red));
            uploader_pb_loading.setProgressTextColor(getResources().getColor(R.color.white));
            uploader_iv_stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTXugcPublish != null) {
                        mTXugcPublish.canclePublish();
                        isCancelPublish = true;
                        uploader_pb_loading.setProgress(0);
                        selectDialog.dismiss();
                    }
                }
            });
        } else {
            selectDialog.show();
        }
        isCancelPublish = false;
        uploader_pb_loading.setProgress(0);
    }

    private void publish(String signature) {
        showPopupWindow();
        if (mTXugcPublish == null) {
            mTXugcPublish = new TXUGCPublish(this.getApplicationContext());
        }
        mTXugcPublish.setListener(new TXUGCPublishTypeDef.ITXVideoPublishListener() {
            @Override
            public void onPublishProgress(long uploadBytes, long totalBytes) {
                TXLog.d(TAG, "onPublishProgress [" + uploadBytes + "/" + totalBytes + "]");
                if (isCancelPublish) {
                    return;
                }
                if (uploader_pb_loading != null)
                    uploader_pb_loading.setProgress((int) ((uploadBytes * 100) / totalBytes));
            }

            @Override
            public void onPublishComplete(TXUGCPublishTypeDef.TXPublishResult result) {
                TXLog.d(TAG, "onPublishComplete [" + result.retCode + "/" + (result.retCode == 0 ? result.videoURL : result.descMsg) + "]");
                if (selectDialog != null) {
                    selectDialog.dismiss();
                }
                if (isCancelPublish) {
                    return;
                }
                // 这里可以把上传返回的视频信息以及自定义的视频信息上报到自己的业务服务器
//                reportVideoInfo(result);
                // 注意：如果取消发送时，是取消的剩余未上传的分片发送，如果视频比较小，分片已经进入任务队列了是无法取消的。此时不跳转到下一个界面。
                if (result.retCode == TXUGCPublishTypeDef.PUBLISH_RESULT_OK) {
                    result_ = result;
                    Log.d("result.videoId", "result.videoId=" + result.videoId + "   result.coverURL=" + result.coverURL);
                    mHandler.sendEmptyMessage(uploadVideo);
                } else {
                    if (result.descMsg.contains("java.net.UnknownHostException") || result.descMsg.contains("java.net.ConnectException")) {
                        ToastUtils.showToast("网络连接断开，视频上传失败");
                    } else {
                        ToastUtils.showToast("发布失败");
                    }
                }
            }
        });
        TXUGCPublishTypeDef.TXPublishParam param = new TXUGCPublishTypeDef.TXPublishParam();
        param.signature = signature;
        param.videoPath = mVideoPath;
        param.coverPath = mCoverImagePath;
        param.fileName = title;
        mTXugcPublish.publishVideo(param);
    }

    TXUGCPublishTypeDef.TXPublishResult result_;
    protected final int uploadVideo = 6;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case uploadVideo:
                    if (result_ != null)
                        mPresent.UploadVideoInfo(title, cont, result_.coverURL, city,
                                phone_user_longitude, phone_user_latitude, result_.videoId, result_.videoURL);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (resultCode == 1) {
                mCoverImagePath = data.getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
                setCover();
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
