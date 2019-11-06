package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Intent;
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

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.SupplierEditText;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

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
    SupplierEditText edtContent;
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
    @BindView(R.id.frag_iv_follow)
    ImageView fragIvFollow;
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
        setTrans(false);
    }


    private void setTrans(boolean playstatus) {
        if (playstatus) {
            Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent));
        } else {
            setOrChangeTranslucentColor(toolbar, null);
        }

    }

    public void setListener() {
        mSurfaceView.getHolder().addCallback(new MyCallBack());
        btnLiwu.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnCamera.setVisibility(View.GONE);
        fragTvFollow.setVisibility(View.VISIBLE);
        fragIvFollow.setVisibility(View.GONE);
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
        String live_name = intent.getStringExtra("live_name");
        String playTitle = intent.getStringExtra("playTitle");
        if (!TextUtils.isEmpty(playTitle)) {
            fragTvPlaystatus.setText("直播中: " + playTitle);
        }
        String uid = intent.getStringExtra("uid");
        mPresent.getLivePlay(uid);
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


    @Override
    public void BackPlayListSuccess(LivePushDataInfo responseBean) {

    }

    @Override
    public void Error() {
    }


    class MyCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            Log.d("MyCallBack", "surfaceCreated");

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d("MyCallBack", "surfaceCreated");
            //准备播放
            initPlay();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d("MyCallBack", "surfaceDestroyed");
            //防止退到后台重置问题
//            stopPlay();
//            if (mSurfaceView != null) {
//                mSurfaceView.setVisibility(View.GONE);
//                layout_notlive.setVisibility(View.VISIBLE);
//            }
        }
    }

    AliVcMediaPlayer mPlayer;

    private void initPlay() {
        stopPlay();
        //创建播放器的实例
        mPlayer = new AliVcMediaPlayer(this, mSurfaceView);
        if (mPlayer != null) {
            mPlayer.prepareAndPlay(playurl);
        }
        mPlayer.play();
        //填充效果
        mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        //开启循环播放
        mPlayer.setCirclePlay(false);
        //设置缺省编码类型：0表示硬解；1表示软解；
        //如果缺省为硬解，在使用硬解时如果解码失败，会尝试使用软解
        //如果缺省为软解，则一直使用软解，软解较为耗电，建议移动设备尽量使用硬解
        mPlayer.setDefaultDecoder(0);
        //准备完成
        mPlayer.setPreparedListener(new MediaPlayer.MediaPlayerPreparedListener() {
            @Override
            public void onPrepared() {
                Log.i("MyCallBack", "setPreparedListener");
                mPlayer.play();
                if (relat_push != null) {
                    relat_push.setVisibility(View.VISIBLE);
                    layoutNotlive.setVisibility(View.GONE);
                    setTrans(true);
                }
            }
        });
        mPlayer.setCompletedListener(new MediaPlayer.MediaPlayerCompletedListener() {
            @Override
            public void onCompleted() {
                Log.i("MyCallBack", "onCompleted");
                //视频正常播放完成时触发
                if (relat_push != null) {
                    relat_push.setVisibility(View.GONE);
                    layoutNotlive.setVisibility(View.VISIBLE);
                    setTrans(false);
                }
            }
        });
    }

    /**
     * 播放视频
     */
    private void startPlay() {
        initPlay();
    }

    /**
     * 停止播放  (其他的，暂停等功能自己可以定义方法实现)
     */
    private void stopPlay() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.destroy();
            mPlayer = null;
        }
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
