package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.widget.Immersive;
import com.tencent.rtmp.ITXVodPlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import butterknife.BindView;

public class VideoPlayActivity extends BaseActivity {

    @BindView(R.id.video_view)
    TXCloudVideoView videoView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_content)
    ImageView iv_content;

    private TXVodPlayer mTXVodPlayer = null;
    private TXVodPlayConfig mTXPlayConfig = null;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_left:
                doFinish();
                break;
        }
    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_playvideo;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
    }

    String video_1;
    String video_2;

    @Override
    public void initDataAfter() {
        String mVideoPath = getIntent().getStringExtra("video_url");
        String title = getIntent().getStringExtra("title");
        video_1 = getIntent().getStringExtra("video_1");
        video_2 = getIntent().getStringExtra("video_2");
        tvTitle.setText(title);
        int wid = DensityUtil.screenWith(mContext) ;
        iv_content.setLayoutParams(new LinearLayout.LayoutParams(wid, (int) (wid * 1.31)));
        GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, video_1, iv_content, 0);
        showGuiZeDialog();
        mTXVodPlayer = new TXVodPlayer(this);
        mTXPlayConfig = new TXVodPlayConfig();
        mTXVodPlayer.enableHardwareDecode(false);
        mTXVodPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mTXVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        mTXVodPlayer.setConfig(mTXPlayConfig);
        mTXVodPlayer.setPlayerView(videoView);
        mTXVodPlayer.setVodListener(new ITXVodPlayListener() {
            @Override
            public void onPlayEvent(TXVodPlayer player, int event, Bundle param) {
                if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
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
            ToastUtils.showToast("视频播放失败");
        }
        mVideoPlay = true;
    }

    boolean mVideoPlay = false;


    MyDialog mXuFeiDialog;
    ImageView iv_content_;
    WindowManager.LayoutParams p;

    public void showGuiZeDialog() {
        if (mXuFeiDialog == null) {
            mXuFeiDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_playvideo_tishi);// 创建Dialog并设置样式主题
            mXuFeiDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mXuFeiDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mXuFeiDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            p = mXuFeiDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            mXuFeiDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_cancel = mXuFeiDialog.findViewById(R.id.iv_cancel);
            iv_content_ = mXuFeiDialog.findViewById(R.id.iv_content);
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mXuFeiDialog.dismiss();
                }
            });
        } else {
            mXuFeiDialog.show();
        }
        iv_content_.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 3.23)));
        GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, video_2, iv_content_, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoView != null) {
            videoView.onResume();
        }
        if (mVideoPlay) {
            mTXVodPlayer.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoView != null) {
            videoView.onPause();
        }
        if (mVideoPlay) {
            mTXVodPlayer.pause();
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
        if (mTXVodPlayer != null) {
            mTXVodPlayer.setVodListener(null);
            mTXVodPlayer.stopPlay(true);
            mVideoPlay = false;
        }
        mTXVodPlayer = null;
        mTXPlayConfig = null;
        videoView = null;
        super.onDestroy();
    }


}
