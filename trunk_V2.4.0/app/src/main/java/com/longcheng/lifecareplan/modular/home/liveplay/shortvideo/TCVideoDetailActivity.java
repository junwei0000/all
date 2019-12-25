package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.longcheng.lifecareplan.utils.FileCache;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.widget.Immersive;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import cn.finalteam.okhttpfinal.FileDownloadCallback;
import cn.finalteam.okhttpfinal.HttpRequest;

/**
 * 短视频详情
 */

public class TCVideoDetailActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

    @BindView(R.id.video_view)
    TXCloudVideoView videoView;
    @BindView(R.id.record_preview)
    ImageButton recordPreview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_cont)
    TextView tvCont;
    @BindView(R.id.frag_tv_dianzannum)
    TextView fragTvDianzannum;
    @BindView(R.id.frag_layout_dianzan)
    LinearLayout fragLayoutDianzan;
    @BindView(R.id.frag_tv_commentnum)
    TextView fragTvCommentnum;
    @BindView(R.id.frag_layout_comment)
    LinearLayout fragLayoutComment;
    @BindView(R.id.frag_tv_sharenum)
    TextView fragTvSharenum;
    @BindView(R.id.frag_layout_share)
    LinearLayout fragLayoutShare;
    @BindView(R.id.layout_right)
    LinearLayout layoutRight;
    @BindView(R.id.iv_dianzan)
    ImageView iv_dianzan;
    private String mVideoPath, show_video_id, mCoverImagePath;
    private TXVodPlayer mTXVodPlayer = null;
    private TXVodPlayConfig mTXPlayConfig = null;
    boolean mVideoPlay = false;
    boolean mVideoPause = false;
    boolean mAutoPause = false;

    int mVideoDuration;
    int is_follow;

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_left) {
            doFinish();
        } else if (id == R.id.record_preview) {
            switchPlay();
        } else if (id == R.id.frag_layout_dianzan) {
            int type;
            if (is_follow == 0) {
                type = 1;
            } else {
                type = 2;
            }
            mPresent.addVideoFollow(show_video_id, type);
        } else if (id == R.id.frag_layout_comment) {

        } else if (id == R.id.frag_layout_share) {
            DOWNLOAD();
        }

    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_shortvideo_detail;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
    }

    public static void skipVideoDetail(Activity mActivity, String mCoverImagePath, String videoPath, String show_video_id) {
        Intent intent = new Intent(mActivity, TCVideoDetailActivity.class);
        intent.putExtra(TCConstants.VIDEO_RECORD_VIDEPATH, videoPath);
        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mCoverImagePath);
        intent.putExtra("show_video_id", show_video_id);
        mActivity.startActivity(intent);
    }

    @Override
    public void initDataAfter() {
        mVideoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        mCoverImagePath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
        show_video_id = getIntent().getStringExtra("show_video_id");
        initPlay();
        getDetailInfo();
    }

    private void getDetailInfo() {
        mPresent.videoDetail(show_video_id);
    }

    private void initPlay() {
        mTXVodPlayer = new TXVodPlayer(this);
        mTXPlayConfig = new TXVodPlayConfig();
        videoView.disableLog(true);
        mTXVodPlayer.enableHardwareDecode(false);
        mTXVodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mTXVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        mTXVodPlayer.setConfig(mTXPlayConfig);
        startPlay();
    }

    private void switchPlay() {
        if (mVideoPlay) {
            if (mVideoPause) {
                mTXVodPlayer.resume();
                recordPreview.setBackgroundResource(R.mipmap.record_pause);
                mVideoPause = false;
            } else {
                mTXVodPlayer.pause();
                recordPreview.setBackgroundResource(R.mipmap.record_start);
                mVideoPause = true;
            }
        } else {
            startPlay();
        }
    }

    private boolean startPlay() {
        recordPreview.setBackgroundResource(R.mipmap.record_pause);
        mTXVodPlayer.setPlayerView(videoView);
        mTXVodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    ToastUtils.showToast(R.string.net_tishi);
                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
                    mTXVodPlayer.pause();
                    recordPreview.setBackgroundResource(R.mipmap.record_start);
                    mVideoPause = true;
                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_PROGRESS) {
                    if (cover != null && cover.isShown()) {
                        cover.setVisibility(View.GONE);
                    }
                    int progress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);
                    mVideoDuration = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);//单位为s
                }
            }

            @Override
            public void onNetStatus(TXVodPlayer player, Bundle bundle) {
            }
        });
        int result = mTXVodPlayer.startPlay(mVideoPath); // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        if (result != 0) {
            recordPreview.setBackgroundResource(R.mipmap.record_start);
            return false;
        }
        mVideoPlay = true;
        return true;
    }

    protected void stopPlay(boolean clearLastFrame) {
        if (mTXVodPlayer != null) {
            mTXVodPlayer.setVodListener(null);
            mTXVodPlayer.stopPlay(clearLastFrame);
            mVideoPlay = false;
        }
    }


    // 下载具体操作
    private void DOWNLOAD() {
        File appDir = new File(FileCache.path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        // 下载后的文件名
        int i = mVideoPath.lastIndexOf("/"); // 取的最后一个斜杠后的字符串为名
        String fileName = mVideoPath.substring(i);
        File saveFile = new File(appDir, fileName);
        if (saveFile.exists()) {
            // 如果已经存在, 就不下载了, 去播放
            ToastUtils.showToast("已经下载该视频");
        } else {
            ToastUtils.showToast("视频下载中…");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpRequest.download(mVideoPath, saveFile, new FileDownloadCallback() {

                        //开始下载
                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        //下载进度
                        @Override
                        public void onProgress(int progress, long networkSpeed) {
                            super.onProgress(progress, networkSpeed);
                        }

                        //下载失败
                        @Override
                        public void onFailure() {
                            super.onFailure();
                            Toast.makeText(getBaseContext(), "下载失败", Toast.LENGTH_SHORT).show();
                        }

                        //下载完成（下载成功）
                        @Override
                        public void onDone() {
                            super.onDone();
                            Toast.makeText(getBaseContext(), "下载成功", Toast.LENGTH_SHORT).show();
                            file_ = saveFile;
                            mHandler.sendEmptyMessage(download);
                        }
                    });
                }
            }).start();
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

    /**
     * 是否添加到相册
     *
     * @param saveFile
     * @return
     */
    private ContentValues initCommonContentValues(File saveFile) {
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

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.onResume();
        }
        if (mVideoPlay) {
            mTXVodPlayer.resume();
            recordPreview.setBackgroundResource(R.mipmap.record_pause);
            mAutoPause = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.onPause();
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
        if (videoView != null) {
            videoView.onDestroy();
        }
        stopPlay(true);
        mTXVodPlayer = null;
        mTXPlayConfig = null;
        videoView = null;
        super.onDestroy();
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
        mHandler.sendEmptyMessage(updateInfo);
    }

    @Override
    public void giveGiftSuccess(BasicResponse responseBean) {

    }

    @Override
    public void videoDetailSuccess(BasicResponse<MVideoItemInfo> responseBean) {
        MVideoItemInfo mMVideoItemInfo = responseBean.getData();
        if (mMVideoItemInfo != null) {
            if (TextUtils.isEmpty(mVideoPath)) {
                mVideoPath = mMVideoItemInfo.getVideo_url();
                startPlay();
            }
            tvName.setText("" + mMVideoItemInfo.getUser_name());
            tvCont.setText("" + mMVideoItemInfo.getContent());
            fragTvDianzannum.setText("" + mMVideoItemInfo.getFollow_number());
            fragTvCommentnum.setText("" + mMVideoItemInfo.getComment_number());
            fragTvSharenum.setText("" + mMVideoItemInfo.getForward_number());
            is_follow = mMVideoItemInfo.getIs_follow();
            if (is_follow == 0) {
                iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_bai);
            } else {
                iv_dianzan.setBackgroundResource(R.mipmap.zhibo_zan_hong1);
            }
            GlideDownLoadImage.getInstance().loadCircleImage(mMVideoItemInfo.getAvatar(), ivAvatar);
        }
    }

    @Override
    public void showGiftDialog() {

    }

    @Override
    public void Error() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    File file_;
    protected final int updateInfo = 6;
    protected final int download = 7;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case updateInfo:
                    getDetailInfo();
                    break;
                case download:
                    ToastUtils.showToast("下载成功");
                    ContentValues values = initCommonContentValues(file_);
                    values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, System.currentTimeMillis());
                    values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
                    values.put(MediaStore.Video.VideoColumns.DURATION, mVideoDuration);//时长
                    mActivity.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
                    insertVideoThumb(file_.getPath(), mCoverImagePath);
                    mPresent.addForwardNum(show_video_id);
                    break;
            }
        }
    };
}
