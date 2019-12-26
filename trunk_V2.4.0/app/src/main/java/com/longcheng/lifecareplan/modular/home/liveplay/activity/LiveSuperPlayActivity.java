package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.rtmp.TXLiveConstants;

import butterknife.BindView;

/**
 * 超级播放器--点播
 */
public class LiveSuperPlayActivity extends BaseActivity {

    @BindView(R.id.preview_view)
    SuperPlayerView mSuperPlayerView;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_superplay;
    }


    @Override
    public void initView(View view) {
//        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), true);
    }

    public void setListener() {
    }


    @Override
    public void initDataAfter() {
        String Rebroadcast_url = getIntent().getStringExtra("Rebroadcast_url");
        String title = getIntent().getStringExtra("title");
        palyVideo(Rebroadcast_url, title);
    }

    /**
     * 播放视频
     */
    private void palyVideo(String playurl, String title) {
        // 播放器配置
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // 开启悬浮窗播放
        prefs.enableFloatWindow = false;
        // 设置悬浮窗的初始位置和宽高
        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
        rect.x = 0;
        rect.y = 0;
        rect.width = DensityUtil.screenWith(mContext);
        rect.height = DensityUtil.screenHigh(mContext);
        prefs.floatViewRect = rect;
        // 播放器默认缓存个数
        prefs.maxCacheItem = 5;
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN;
        mSuperPlayerView.getmVodControllerLarge().setDanMuVisibility(false);
        mSuperPlayerView.setPlayerViewCallback(new SuperPlayerView.OnSuperPlayerViewCallback() {
            @Override
            public void onStartFullScreenPlay() {
                // 隐藏其他元素实现全屏
            }

            @Override
            public void onStopFullScreenPlay() {
                // 恢复原有元素
            }

            @Override
            public void onClickFloatCloseBtn() {
                // 点击悬浮窗关闭按钮，那么结束整个播放
            }

            @Override
            public void onClickSmallReturnBtn() {
                // 点击小窗模式下返回按钮，开始悬浮播放
                Log.e("setPlayerViewCallback", "onClickFloatCloseBtn");
                back();
            }

            @Override
            public void onStartFloatWindowPlay() {
                // 开始悬浮播放后，直接返回到桌面，进行悬浮播放
            }
        });
        final SuperPlayerModel superPlayerModelV3 = new SuperPlayerModel();
        superPlayerModelV3.url = playurl;
        superPlayerModelV3.title = title;
        mSuperPlayerView.playWithModel(superPlayerModelV3);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSuperPlayerView != null)
            if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
                mSuperPlayerView.onPause();
            }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mSuperPlayerView != null)
            if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
                Log.i(TAG, "onResume state :" + mSuperPlayerView.getPlayState());
                mSuperPlayerView.onResume();
                if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
                    mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
                }
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSuperPlayerView != null) {
            mSuperPlayerView.release();
            if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
                mSuperPlayerView.resetPlayer();
            }
        }
        Log.d(TAG, "vrender onDestroy");
    }


    private void back() {
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
