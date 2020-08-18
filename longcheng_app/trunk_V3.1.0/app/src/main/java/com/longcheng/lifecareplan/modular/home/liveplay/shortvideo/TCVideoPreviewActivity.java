package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.apiLive.ApiLive;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.http.api.DefaultObserver;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoGetSignatureInfo;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.FileCache;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.tencent.liteav.demo.videouploader.videoupload.TXUGCPublish;
import com.tencent.liteav.demo.videouploader.videoupload.TXUGCPublishTypeDef;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 短视频处理完成后的预览界面
 * Created by carolsuo on 2017/3/21.
 */

public class TCVideoPreviewActivity extends BaseActivity {
    public static final String TAG = "TCVideoPreviewActivity";
    @BindView(R.id.video_view)
    TXCloudVideoView mTXCloudVideoView;
    @BindView(R.id.cover)
    ImageView mImageViewBg;
    @BindView(R.id.record_preview)
    ImageView mStartPreview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.layout_rigth)
    LinearLayout layoutRigth;
    @BindView(R.id.record_delete)
    ImageView recordDelete;
    @BindView(R.id.record_download)
    ImageView recordDownload;
    @BindView(R.id.record_to_edit)
    ImageView mIvToEdit;
    @BindView(R.id.publishLayout)
    RelativeLayout publishLayout;
    @BindView(R.id.seekbar)
    SeekBar mSeekBar;
    @BindView(R.id.progress_time)
    TextView mProgressTime;
    @BindView(R.id.button)
    Button mButtonThumbnail;

    boolean mVideoPlay = false;
    boolean mVideoPause = false;
    boolean mAutoPause = false;
    private int mVideoSource; // 视频来源
    private String mVideoPath;
    private String mCoverImagePath;
    //视频时长（ms）
    private long mVideoDuration;
    private TXVodPlayer mTXVodPlayer = null;
    private TXVodPlayConfig mTXPlayConfig = null;
    private long mTrackingTouchTS = 0;
    private boolean mStartSeek = false;
    //录制界面传过来的视频分辨率
    private int mVideoResolution;


    public static Activity mPrActivity;

    String skip_type;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.record_delete) {
            deleteVideo();
            FileUtils.deleteFile(mCoverImagePath);
        } else if (id == R.id.record_download) {
            downloadRecord();
        } else if (id == R.id.record_preview) {
            if (mVideoPlay) {
                if (mVideoPause) {
                    mTXVodPlayer.resume();
                    mStartPreview.setBackgroundResource(R.mipmap.record_pause);
                    mVideoPause = false;
                } else {
                    mTXVodPlayer.pause();
                    mStartPreview.setBackgroundResource(R.mipmap.record_start);
                    mVideoPause = true;
                }
            } else {
                startPlay();
            }
        } else if (id == R.id.record_to_edit) {
        } else if (id == R.id.layout_left) {
            doFinish();
        } else if (id == R.id.button) {
            if (!TextUtils.isEmpty(skip_type) && skip_type.equals("DoctorApplySelectVedio")) {
                getUploadVideoSignature();
            } else {
                Intent intent = new Intent(getApplicationContext(), UpLoadVideoActivity.class);
                intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, mVideoSource);
                intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, mVideoPath);
                intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mCoverImagePath);
                intent.putExtra(TCConstants.VIDEO_RECORD_DURATION, mVideoDuration);
                intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, mVideoResolution);
                startActivity(intent);
            }
        }

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_shortvideo_preview;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        mPrActivity = this;
        layoutLeft.setOnClickListener(this);
        mIvToEdit.setOnClickListener(this);
        mButtonThumbnail.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        skip_type = getIntent().getStringExtra("skip_type");
        if (!TextUtils.isEmpty(skip_type) && skip_type.equals("DoctorApplySelectVedio")) {
            mButtonThumbnail.setText("确认");
        } else {
            mButtonThumbnail.setText("下一步");
        }


        mVideoSource = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
        mVideoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        mCoverImagePath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
        mVideoDuration = getIntent().getLongExtra(TCConstants.VIDEO_RECORD_DURATION, 0);
        mVideoResolution = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_RESOLUTION, -1);
        Log.i(TAG, "onCreate: mVideoPath = " + mVideoPath + ",mVideoDuration = " + mVideoDuration);
        if (mCoverImagePath != null && !mCoverImagePath.isEmpty()) {
            Glide.with(this).load(Uri.fromFile(new File(mCoverImagePath)))
                    .into(mImageViewBg);
        }
        if (mVideoSource == TCConstants.VIDEO_RECORD_TYPE_UGC_RECORD) {
            mIvToEdit.setVisibility(View.VISIBLE);
        }
        mTXVodPlayer = new TXVodPlayer(this);
        mTXPlayConfig = new TXVodPlayConfig();
        mTXCloudVideoView.disableLog(true);
        mTXVodPlayer.enableHardwareDecode(false);
        mTXVodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mTXVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);

        mTXVodPlayer.setConfig(mTXPlayConfig);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean bFromUser) {
                if (mProgressTime != null) {
                    mProgressTime.setText(String.format(Locale.CHINA, "%02d:%02d/%02d:%02d", (progress) / 60, (progress) % 60, (seekBar.getMax()) / 60, (seekBar.getMax()) % 60));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mStartSeek = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mTXVodPlayer != null) {
                    mTXVodPlayer.seek(seekBar.getProgress());
                }
                mTrackingTouchTS = System.currentTimeMillis();
                mStartSeek = false;
            }
        });

    }


    private boolean startPlay() {
        mStartPreview.setBackgroundResource(R.mipmap.record_pause);
        mTXVodPlayer.setPlayerView(mTXCloudVideoView);
        mTXVodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
                if (mTXCloudVideoView != null) {
                    mTXCloudVideoView.setLogText(null, param, event);
                }
                if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
                    if (mStartSeek) {
                        return;
                    }
                    if (mImageViewBg.isShown()) {
                        mImageViewBg.setVisibility(View.GONE);
                    }
                    int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
                    int duration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);//单位为s
                    long curTS = System.currentTimeMillis();
                    // 避免滑动进度条松开的瞬间可能出现滑动条瞬间跳到上一个位置
                    if (Math.abs(curTS - mTrackingTouchTS) < 500) {
                        return;
                    }
                    mTrackingTouchTS = curTS;

                    if (mSeekBar != null) {
                        mSeekBar.setProgress(progress);
                    }
                    if (mProgressTime != null) {
                        mProgressTime.setText(String.format(Locale.CHINA, "%02d:%02d/%02d:%02d",
                                (progress) / 60, progress % 60, (duration) / 60, duration % 60));
                    }

                    if (mSeekBar != null) {
                        mSeekBar.setMax(duration);
                    }
                } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    ToastUtils.showToast(R.string.net_tishi);
                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
                    mTXVodPlayer.resume(); // 播放结束后，可以直接resume()，如果调用stop和start，会导致重新播放会黑一下
                }
            }

            @Override
            public void onNetStatus(TXVodPlayer player, Bundle bundle) {

            }
        });
        int result = mTXVodPlayer.startPlay(mVideoPath); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        if (result != 0) {
            mStartPreview.setBackgroundResource(R.mipmap.record_start);
            return false;
        }

        mVideoPlay = true;
        return true;
    }

    private static ContentValues initCommonContentValues(File saveFile) {
        ContentValues values = new ContentValues();
        long currentTimeInSeconds = System.currentTimeMillis();
        values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATE_ADDED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATA, saveFile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.SIZE, saveFile.length());

        return values;
    }

    private void downloadRecord() {
        File file = new File(mVideoPath);
        if (file.exists()) {
            try {
                File dir = new File(FileCache.path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File newFile = new File(FileCache.path + file.getName());

                file.renameTo(newFile);
                mVideoPath = newFile.getAbsolutePath();

                ContentValues values = initCommonContentValues(newFile);
                values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, System.currentTimeMillis());
                values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
                values.put(MediaStore.Video.VideoColumns.DURATION, mVideoDuration);//时长
                this.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

                insertVideoThumb(newFile.getPath(), mCoverImagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    /**
     * 插入视频缩略图
     *
     * @param videoPath
     * @param coverPath
     */
    private void insertVideoThumb(String videoPath, String coverPath) {
        //以下是查询上面插入的数据库Video的id（用于绑定缩略图）
        //根据路径查询
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Thumbnails._ID},//返回id列表
                String.format("%s = ?", MediaStore.Video.Thumbnails.DATA), //根据路径查询数据库
                new String[]{videoPath}, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
                //查询到了Video的id
                ContentValues thumbValues = new ContentValues();
                thumbValues.put(MediaStore.Video.Thumbnails.DATA, coverPath);//缩略图路径
                thumbValues.put(MediaStore.Video.Thumbnails.VIDEO_ID, videoId);//video的id 用于绑定
                //Video的kind一般为1
                thumbValues.put(MediaStore.Video.Thumbnails.KIND,
                        MediaStore.Video.Thumbnails.MINI_KIND);
                //只返回图片大小信息，不返回图片具体内容
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(coverPath, options);
                if (bitmap != null) {
                    thumbValues.put(MediaStore.Video.Thumbnails.WIDTH, bitmap.getWidth());//缩略图宽度
                    thumbValues.put(MediaStore.Video.Thumbnails.HEIGHT, bitmap.getHeight());//缩略图高度
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
                this.getContentResolver().insert(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, //缩略图数据库
                        thumbValues);
            }
            cursor.close();
        }
    }

    private void deleteVideo() {
        stopPlay(true);
        //删除文件
        FileUtils.deleteFile(mVideoPath);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onResume();
        }
        if (mVideoPlay && mAutoPause) {
            mTXVodPlayer.resume();
            mAutoPause = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onPause();
        }
        if (mVideoPlay && !mVideoPause) {
            mTXVodPlayer.pause();
            mAutoPause = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (mTXCloudVideoView != null) {
            mTXCloudVideoView.onDestroy();
        }
        stopPlay(true);
        mTXVodPlayer = null;
        mTXPlayConfig = null;
        mTXCloudVideoView = null;
        if (mSeekBar != null) {
            mSeekBar.setOnSeekBarChangeListener(null);
        }
        if (mTXugcPublish != null) {
            mTXugcPublish.canclePublish();
            if (mVideoDownLoadUtils != null)
                mVideoDownLoadUtils.isCancelPublish = false;
        }
        super.onDestroy();
    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.setVodListener(null);
            mTXVodPlayer.stopPlay(clearLastFrame);
            mVideoPlay = false;
        }
    }


    //****************************************

    /**
     * 创建视频文件上传签名
     */
    public void getUploadVideoSignature() {
        ApiLive.getInstance().service.getUploadVideoSignature(UserUtils.getUserId(mContext))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<VideoGetSignatureInfo>>(this) {
                    @Override
                    public void onSuccess(BasicResponse<VideoGetSignatureInfo> response) {
                        String signature = response.getData().getSignature();
                        if (!TextUtils.isEmpty(signature)) {
                            publish(signature);
                        } else {
                            ToastUtils.showToast("获取签名信息错误");
                        }
                    }

                    @Override
                    public void onError() {
                        ToastUtils.showToast("上传失败");
                    }
                });
    }

    private TXUGCPublish mTXugcPublish;

    VideoDownLoadUtils mVideoDownLoadUtils;

    protected final int cancelPublish = 7;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case cancelPublish:
                    if (mTXugcPublish != null) {
                        mTXugcPublish.canclePublish();
                    }
                    break;
            }
        }
    };

    private void publish(String signature) {
        if (mVideoDownLoadUtils == null) {
            mVideoDownLoadUtils = new VideoDownLoadUtils(mActivity, mHandler, cancelPublish);
        }
        mVideoDownLoadUtils.showPopupWindow(cancelPublish);
        if (mTXugcPublish == null) {
            mTXugcPublish = new TXUGCPublish(this.getApplicationContext());
        }
        mTXugcPublish.setListener(new TXUGCPublishTypeDef.ITXVideoPublishListener() {
            @Override
            public void onPublishProgress(long uploadBytes, long totalBytes) {
                TXLog.d(TAG, "onPublishProgress [" + uploadBytes + "/" + totalBytes + "]");
                if (mVideoDownLoadUtils != null && mVideoDownLoadUtils.isCancelPublish) {
                    return;
                }
                if (mVideoDownLoadUtils != null && mVideoDownLoadUtils.uploader_pb_loading != null)
                    mVideoDownLoadUtils.uploader_pb_loading.setProgress((int) ((uploadBytes * 100) / totalBytes));
            }

            @Override
            public void onPublishComplete(TXUGCPublishTypeDef.TXPublishResult result) {
                TXLog.d(TAG, "onPublishComplete [" + result.retCode + "/" + (result.retCode == 0 ? result.videoURL : result.descMsg) + "]");
                if (mVideoDownLoadUtils != null && mVideoDownLoadUtils.selectDialog != null) {
                    mVideoDownLoadUtils.selectDialog.dismiss();
                }
                if (mVideoDownLoadUtils != null && mVideoDownLoadUtils.isCancelPublish) {
                    return;
                }
                // 这里可以把上传返回的视频信息以及自定义的视频信息上报到自己的业务服务器
//                reportVideoInfo(result);
                // 注意：如果取消发送时，是取消的剩余未上传的分片发送，如果视频比较小，分片已经进入任务队列了是无法取消的。此时不跳转到下一个界面。
                if (result.retCode == TXUGCPublishTypeDef.PUBLISH_RESULT_OK) {
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("coverURL", result.coverURL);
                        jsonObject.put("videoURL", result.videoURL);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.e("registerHandler", "" + jsonObject);
                    ConfigUtils.getINSTANCE().function.onCallBack(jsonObject.toString());
                    doFinish();
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
        param.fileName = "行动申请-爱友视频";
        mTXugcPublish.publishVideo(param);
    }
}
