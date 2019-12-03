package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.io.IOException;

import butterknife.BindView;

/**
 * 直播
 */
public class LivePlayActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

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
    @BindView(R.id.btn_exit)
    ImageView btnExit;
    @BindView(R.id.lv_rankdata)
    ListView lvRankdata;
    @BindView(R.id.lv_msg)
    ListView lvMsg;
    @BindView(R.id.edt_content)
    TextView edtContent;
    @BindView(R.id.btn_liwu)
    ImageView btnLiwu;
    @BindView(R.id.btn_camera)
    ImageView btnCamera;

    String playurl;
    @BindView(R.id.preview_view)
    SurfaceView mSurfaceView;
    @BindView(R.id.relat_push)
    RelativeLayout relat_push;
    @BindView(R.id.layout_notlive)
    LinearLayout layoutNotlive;
    @BindView(R.id.frag_tv_follow)
    TextView fragTvFollow;
    @BindView(R.id.frag_tv_sharenum)
    TextView fragTvSharenum;
    @BindView(R.id.frag_layout_share)
    LinearLayout fragLayoutShare;
    @BindView(R.id.layout_gn)
    LinearLayout layoutGn;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit:
                back();
                break;
            case R.id.btn_liwu:
                ToastUtils.showToast("功能开发中...");
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_play;
    }


    @Override
    public void initView(View view) {
        relat_push.setVisibility(View.GONE);
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), true);

    }

    public void setListener() {
        btnLiwu.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnCamera.setVisibility(View.GONE);
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
        String city = new LocationUtils().getAddressCity(this);
        fragTvCity.setText("" + city);
        fragTvJieqi.setText(HomeFragment.jieqi_name + "节气");
        Intent intent = getIntent();
        String playTitle = intent.getStringExtra("playTitle");
        if (!TextUtils.isEmpty(playTitle)) {
            fragTvPlaystatus.setText("直播中: " + playTitle);
        }
        String uid = intent.getStringExtra("playuid");
        mPresent.getLivePlay(uid);
    }

    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;

    /**
     * 播放视频
     */
    private void palyVideo(String filePath) {
        //surfaceHolder可以通过surfaceview的getHolder()方法获得
        surfaceHolder = mSurfaceView.getHolder();
        //给surfaceHolder设置一个callback
        surfaceHolder.addCallback(new SHCallBack());
        mediaPlayer = new MediaPlayer();
        try {
            //设置要播放的资源，可以是文件、文件路径、或者URL
            mediaPlayer.setDataSource(this, Uri.parse(filePath));
            //调用MediaPlayer.prepare()来准备
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    //调用MediaPlayer.start()来播放视频
                    mediaPlayer.start();
                    mediaPlayer.setLooping(true);
                    if (relat_push != null) {
                        relat_push.setVisibility(View.VISIBLE);
                        layoutNotlive.setVisibility(View.GONE);
                    }
                    Log.i("MyCallBack", "setOnPreparedListener");
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //视频正常播放完成时触发
                    if (relat_push != null) {
                        relat_push.setVisibility(View.GONE);
                        layoutNotlive.setVisibility(View.VISIBLE);
                    }
                    Log.i("MyCallBack", "onCompletion");
                }
            });
            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    Log.i("MyCallBack", "onSeekComplete");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlay() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
        }
    }

    private class SHCallBack implements SurfaceHolder.Callback {
        /**
         * 在SurfaceHolder被创建的时候回调，
         * 在这里可以做一些初始化的操作
         *
         * @param holder
         */
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            //调用MediaPlayer.setDisplay(holder)设置surfaceHolder，surfaceHolder可以通过surfaceview的getHolder()方法获得
            mediaPlayer.setDisplay(holder);
            Log.d("MyCallBack", "surfaceCreated");
        }

        /**
         * 当SurfaceHolder的尺寸发生变化的时候被回调
         *
         * @param holder
         * @param format
         * @param width
         * @param height
         */
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.d("MyCallBack", "surfaceChanged");
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d("MyCallBack", "surfaceDestroyed");
        }
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }

    @Override
    public void BackPushSuccess(LivePushDataInfo responseBean) {
    }

    @Override
    public void BackPlaySuccess(LivePushDataInfo responseBean) {
        playurl = responseBean.getM3u8url();
        startPlay();
    }

    /**
     * 播放视频
     */
    private void startPlay() {
        palyVideo(playurl);
    }

    @Override
    public void BackPlayListSuccess(LivePushDataInfo responseBean) {

    }

    @Override
    public void BackVideoListSuccess(LivePushDataInfo responseBean) {

    }

    @Override
    public void Error() {
    }


    private void back() {
        stopPlay();
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
