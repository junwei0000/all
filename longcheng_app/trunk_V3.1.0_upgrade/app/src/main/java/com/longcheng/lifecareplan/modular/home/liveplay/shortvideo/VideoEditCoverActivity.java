package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.edit.PlayState;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.edit.TCVideoEditerWrapper;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.edit.VideoProgressController;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.edit.VideoProgressView;
import com.longcheng.lifecareplan.utils.FileCache;
import com.longcheng.lifecareplan.widget.Immersive;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;
import com.tencent.ugc.TXVideoInfoReader;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2019/11/11 17:44
 * 意图：编辑封面
 */
public class VideoEditCoverActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.iv_cover)
    FrameLayout ivCover;
    @BindView(R.id.editer_video_progress_view)
    VideoProgressView mVideoProgressView;
    @BindView(R.id.editer_ib_play)
    ImageView editer_ib_play;

    private String mVideoPath;
    private String mCoverImagePath;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_left:
                back();
                break;
            case R.id.tv_sure:
                stopPlay();
                Bitmap bitmap_ = TXVideoInfoReader.getInstance().getSampleImage(mPreviewAtTime, mVideoPath);
                File file = FileCache.saveBitmapFile(bitmap_);
                if (file != null) {
                    mCoverImagePath = file.getPath();
                }
                Log.e("onThumbnail", "file.getPath()=" + file.getPath() + "    mCoverImagePath=" + mCoverImagePath);
                back();
                break;
            case R.id.editer_ib_play:
                playVideo(false);
                break;
        }

    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_shortvideo_editcover;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
        tvSure.setOnClickListener(this);
        editer_ib_play.setOnClickListener(this);
        ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tvSure, R.color.red);
    }


    @Override
    public void initDataAfter() {
        mVideoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        mCoverImagePath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);

        initVideo();
        initVideoProgressLayout();
        initPlayerLayout();
    }

    private VideoProgressController mVideoProgressController;
    private TCVideoEditerWrapper mEditerWrapper;
    // 短视频SDK获取到的视频信息
    private TXVideoEditer mTXVideoEditer;
    private long mVideoDuration;                            // 视频的总时长
    private Handler mMainHandler;
    private int mCurrentState = PlayState.STATE_NONE;       // 播放器当前状态
    public boolean isPreviewFinish;
    private long mPreviewAtTime;                            // 当前单帧预览的时间

    TCVideoEditerWrapper.TXVideoPreviewListenerWrapper mTXVideoPreviewListenerWrapper = new TCVideoEditerWrapper.TXVideoPreviewListenerWrapper() {
        @Override// 预览进度回调
        public void onPreviewProgressWrapper(int timeMs) {
            // 视频的进度回调是异步的，如果不是处于播放状态，那么无需修改进度
            if (mCurrentState == PlayState.STATE_RESUME || mCurrentState == PlayState.STATE_PLAY) {
                mVideoProgressController.setCurrentTimeMs(timeMs);
                mPreviewAtTime = timeMs;
            }
        }

        @Override // 预览完成回调
        public void onPreviewFinishedWrapper() {
            TXCLog.d(TAG, "---------------onPreviewFinished-----------------");
            isPreviewFinish = true;
            // 如果是在单帧预览条件下结束的，不要开始播放
            if (mCurrentState == PlayState.STATE_PREVIEW_AT_TIME) {
                return;
            }
            stopPlay();
            startPlay(getCutterStartTime(), getCutterEndTime());
        }

        @Override
        public void onPreviewError(TXVideoEditConstants.TXPreviewError error) {

        }
    };

    private void initVideo() {
        mMainHandler = new Handler(Looper.getMainLooper());
        mEditerWrapper = TCVideoEditerWrapper.getInstance();
        mEditerWrapper.addTXVideoPreviewListenerWrapper(mTXVideoPreviewListenerWrapper);
        if (mTXVideoEditer == null) {
            mTXVideoEditer = new TXVideoEditer(this);
            int ret = mTXVideoEditer.setVideoPath(mVideoPath);
            mEditerWrapper.setEditer(mTXVideoEditer);
        }
        mTXVideoEditer = mEditerWrapper.getEditer();
        Log.e("initVideo", "" + mTXVideoEditer.toString());
        if (mEditerWrapper.getTXVideoInfo() == null) {
            TXVideoEditConstants.TXVideoInfo info = TXVideoInfoReader.getInstance().getVideoFileInfo(mVideoPath);
            TCVideoEditerWrapper.getInstance().setTXVideoInfo(info);
        }
        mVideoDuration = mEditerWrapper.getTXVideoInfo().duration;
        mEditerWrapper.setCutterStartTime(0, mVideoDuration);
        mTXVideoEditer.getThumbnail(TCVideoEditerWrapper.mThumbnailCount, 100, 100, false, mThumbnailListener);
    }

    private void initVideoProgressLayout() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        int screenWidth = point.x;
        mVideoProgressView.setViewWidth(screenWidth);
        List<Bitmap> thumbnailList = mEditerWrapper.getAllThumbnails();
        mVideoProgressView.setThumbnailData();
        if (thumbnailList != null || thumbnailList.size() > 0) {
            mVideoProgressView.addAllThumbnail(thumbnailList);
        }
        mVideoProgressController = new VideoProgressController(mVideoDuration);
        mVideoProgressController.setVideoProgressView(mVideoProgressView);
        mVideoProgressController.setThumbnailPicListDisplayWidth(TCVideoEditerWrapper.mThumbnailCount);
        mVideoProgressController.setVideoProgressSeekListener(mVideoProgressSeekListener);
        mVideoProgressController.setVideoProgressDisplayWidth(screenWidth);
    }

    private void initPlayerLayout() {
        TXVideoEditConstants.TXPreviewParam param = new TXVideoEditConstants.TXPreviewParam();
        param.videoView = ivCover;
        param.renderMode = TXVideoEditConstants.PREVIEW_RENDER_MODE_FILL_SCREEN;
        mTXVideoEditer.initWithPreview(param);
    }

    private VideoProgressController.VideoProgressSeekListener mVideoProgressSeekListener = new VideoProgressController.VideoProgressSeekListener() {
        @Override
        public void onVideoProgressSeek(long currentTimeMs) {
            TXCLog.d("onThumbnail", "onVideoProgressSeek, currentTimeMs = " + currentTimeMs);
            previewAtTime(currentTimeMs);
        }

        @Override
        public void onVideoProgressSeekFinish(long currentTimeMs) {
            TXCLog.d("onThumbnail", "onVideoProgressSeekFinish, currentTimeMs = " + currentTimeMs);
            previewAtTime(currentTimeMs);
        }
    };
    private TXVideoEditer.TXThumbnailListener mThumbnailListener = new TXVideoEditer.TXThumbnailListener() {
        @Override

        public void onThumbnail(int index, long timeMs, final Bitmap bitmap) {
            Log.i("onThumbnail", "onThumbnail: index = " + index + ",timeMs:" + timeMs);
            TCVideoEditerWrapper.getInstance().addThumbnailBitmap(timeMs, bitmap);
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    mVideoProgressView.addThumbnail(bitmap);
                }
            });
        }
    };

    /**
     * 调用mTXVideoEditer.previewAtTime后，需要记录当前时间，下次播放时从当前时间开始
     * x
     *
     * @param timeMs
     */
    public void previewAtTime(long timeMs) {
        pausePlay();
        isPreviewFinish = false;
        mTXVideoEditer.previewAtTime(timeMs);
        mPreviewAtTime = timeMs;
        mCurrentState = PlayState.STATE_PREVIEW_AT_TIME;
    }

    /**
     * 给子Fragment调用 （子Fragment不在意Activity中对于播放器的生命周期）
     */
    public void restartPlay() {
        stopPlay();
        startPlay(getCutterStartTime(), getCutterEndTime());
    }

    public void pausePlay() {
        if (mCurrentState == PlayState.STATE_RESUME || mCurrentState == PlayState.STATE_PLAY) {
            mTXVideoEditer.pausePlay();
            mCurrentState = PlayState.STATE_PAUSE;
            editer_ib_play.setBackgroundResource(R.mipmap.record_start);
        }
    }

    public void stopPlay() {
        if (mCurrentState == PlayState.STATE_RESUME || mCurrentState == PlayState.STATE_PLAY ||
                mCurrentState == PlayState.STATE_PREVIEW_AT_TIME || mCurrentState == PlayState.STATE_PAUSE) {
            mTXVideoEditer.stopPlay();
            mCurrentState = PlayState.STATE_STOP;
            editer_ib_play.setBackgroundResource(R.mipmap.record_start);
        }
    }

    public void resumePlay() {
        if (mCurrentState == PlayState.STATE_PAUSE) {
            mTXVideoEditer.resumePlay();
            mCurrentState = PlayState.STATE_RESUME;
            editer_ib_play.setBackgroundResource(R.mipmap.record_pause);
        }
    }

    public void startPlay(long startTime, long endTime) {
        if (mCurrentState == PlayState.STATE_NONE || mCurrentState == PlayState.STATE_STOP || mCurrentState == PlayState.STATE_PREVIEW_AT_TIME) {
            mTXVideoEditer.startPlayFromTime(startTime, endTime);
            mCurrentState = PlayState.STATE_PLAY;
            isPreviewFinish = false;
            editer_ib_play.setBackgroundResource(R.mipmap.record_pause);
        }
    }

    /**
     * 如果是滤镜特效的界面调用：
     * 1、在播放状态下，按住滤镜不会停止播放
     * 2、播放到末尾了，按住时，不会重新播放
     *
     * @param isMotionFilter
     */
    public void playVideo(boolean isMotionFilter) {
        TXCLog.i(TAG, "editer_ib_play clicked, mCurrentState = " + mCurrentState);
        if (mCurrentState == PlayState.STATE_NONE || mCurrentState == PlayState.STATE_STOP) {
            startPlay(getCutterStartTime(), getCutterEndTime());
        } else if ((mCurrentState == PlayState.STATE_RESUME || mCurrentState == PlayState.STATE_PLAY) && !isMotionFilter) {
            pausePlay();
        } else if (mCurrentState == PlayState.STATE_PAUSE) {
            resumePlay();
        } else if (mCurrentState == PlayState.STATE_PREVIEW_AT_TIME) {
            if ((mPreviewAtTime >= getCutterEndTime() || mPreviewAtTime <= getCutterStartTime()) && !isMotionFilter) {
                startPlay(getCutterStartTime(), getCutterEndTime());
            } else if (!TCVideoEditerWrapper.getInstance().isReverse()) {
                startPlay(mPreviewAtTime, getCutterEndTime());
            } else {
                startPlay(getCutterStartTime(), mPreviewAtTime);
            }
        }
    }

    private long getCutterStartTime() {
        return mEditerWrapper.getCutterStartTime();
    }

    private long getCutterEndTime() {
        return mEditerWrapper.getCutterEndTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TXCLog.i(TAG, "onResume");
        mEditerWrapper.addTXVideoPreviewListenerWrapper(mTXVideoPreviewListenerWrapper);
        playVideo(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlay();
        mEditerWrapper.removeTXVideoPreviewListenerWrapper(mTXVideoPreviewListenerWrapper);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTXVideoEditer != null) {
            // 注意释放，不然容易内存泄漏
            mTXVideoEditer.setThumbnailListener(null);
            mTXVideoEditer.setVideoProcessListener(null);
            mTXVideoEditer.cancel();
            mTXVideoEditer.release();
            mTXVideoEditer = null;
        }
        // 清除对TXVideoEditer的引用以及相关配置
        mEditerWrapper.removeTXVideoPreviewListenerWrapper(mTXVideoPreviewListenerWrapper);
        mEditerWrapper.cleaThumbnails();
        mEditerWrapper.clear();
    }

    private void back() {
        Intent intent = new Intent();
        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, mCoverImagePath);
        setResult(1, intent);
        doFinish();
    }

    @Override
    public void onBackPressed() {
        back();
    }


}
