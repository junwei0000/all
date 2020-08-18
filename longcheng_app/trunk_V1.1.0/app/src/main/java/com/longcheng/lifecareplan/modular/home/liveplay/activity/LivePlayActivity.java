package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.utils.share.ShareUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;


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
    @BindView(R.id.rank_iv_arrow)
    ImageView rank_iv_arrow;
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

    @BindView(R.id.preview_view)
    TXCloudVideoView mSurfaceView;
    @BindView(R.id.iv_notLive)
    ImageView iv_notLive;
    @BindView(R.id.frag_tv_follow)
    TextView fragTvFollow;
    @BindView(R.id.frag_tv_sharenum)
    TextView fragTvSharenum;
    @BindView(R.id.frag_layout_share)
    LinearLayout fragLayoutShare;
    @BindView(R.id.layout_gn)
    LinearLayout layoutGn;

    boolean isPlaying = false;
    boolean rankOpenStatus = false;
    ShareUtils mShareUtils;
    String title, User_name, Cover_url;

    @Override
    public void onClick(View v) {
        ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
        switch (v.getId()) {
            case R.id.btn_exit:
                back();
                break;
            case R.id.btn_liwu:
                ToastUtils.showToast("开发中...");
                break;
            case R.id.frag_tv_follow:
                ToastUtils.showToast("开发中...");
                break;
            case R.id.frag_layout_rank:
                ToastUtils.showToast("开发中...");
                break;
            case R.id.frag_layout_share:
                ToastUtils.showToast("开发中...");
                break;
            default:
                break;
        }
    }

    private void setRankListOpenStatus() {
        if (rankOpenStatus) {
            rank_iv_arrow.setBackgroundResource(R.mipmap.live_jiantou_2);
            fragLayoutRank.setBackgroundResource(R.mipmap.live_beijingi_2);
            lvRankdata.setVisibility(View.VISIBLE);
        } else {
            rank_iv_arrow.setBackgroundResource(R.mipmap.live_jiantou_3);
            fragLayoutRank.setBackgroundResource(R.mipmap.live_beijingi_1);
            lvRankdata.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_push;
    }


    @Override
    public void initView(View view) {
//        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), true);
    }

    public void setListener() {
        iv_notLive.setVisibility(View.VISIBLE);
        btnCamera.setVisibility(View.GONE);
        lvRankdata.getBackground().setAlpha(50);
        btnLiwu.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        fragTvFollow.setOnClickListener(this);
        fragLayoutRank.setOnClickListener(this);
        fragLayoutShare.setOnClickListener(this);
        edtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!TextUtils.isEmpty(edtContent.getText().toString().trim())) {
                        edtContent.setText("");
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
        initPlay();
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

    TXLivePlayer mLivePlayer;

    private void initPlay() {
        setScreenWake();
        //创建 player 对象
        mLivePlayer = new TXLivePlayer(mActivity);
        //关键 player 对象与界面 view
        mLivePlayer.setPlayerView(mSurfaceView);
        // 设置填充模式
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        mLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int event, Bundle param) {
                Log.e("onPlayEvent", "event=" + event + " ;;param==" + param.toString());
                if (event == TXLiveConstants.PLAY_EVT_PLAY_BEGIN) {
                    iv_notLive.setVisibility(View.GONE);
                } else if (event == TXLiveConstants.PLAY_EVT_PLAY_END) {
                    stopPlay();
                } else if (event == TXLiveConstants.PLAY_ERR_NET_DISCONNECT) {
                    ToastUtils.showToast("直播已断开");
                    iv_notLive.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNetStatus(Bundle status) {
                Log.d(TAG, "Current status, CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                        ", RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                        ", SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                        ", FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                        ", ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                        ", VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
            }
        });
    }

    /**
     * 播放视频
     */
    private void palyVideo(String playurl) {
        mLivePlayer.startPlay(playurl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV 成熟度高、高并发无压力
    }

    private void stopPlay() {
        if (mLivePlayer != null) {
            mLivePlayer.stopRecord();
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLivePlayer != null) {
            // 暂停
            mLivePlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLivePlayer != null) {
            // 继续
            mLivePlayer.resume();
        }
    }

    @Override
    public void onDestroy() {
        setScreenRelease();
        BaoZhangActitvty.shareBackType = "";
        BaoZhangActitvty.life_repay_id = "";
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay(true);
            mLivePlayer = null;
        }
        if (mSurfaceView != null) {
            mSurfaceView.onDestroy();
            mSurfaceView = null;
        }
        Log.d(TAG, "vrender onDestroy");
        super.onDestroy();
    }

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mRxAppCompatActivity, this);
    }


    @Override
    public void showDialog() {
        if (!isPlaying)
            LoadingDialogAnim.show(mContext);
    }


    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void BackPushSuccess(BasicResponse responseBean) {

    }

    @Override
    public void BackPlaySuccess(BasicResponse<LivePushDataInfo> responseBean) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            String playurl = responseBean.getData().getPlayUrl().getFlvurl();
            palyVideo(playurl);
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }


    @Override
    public void Error() {
    }


    protected static final int updateView = 5;
    protected static final int addForwardNum = 6;
    protected static final int liwu = 7;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case updateView:
                    break;
                case addForwardNum:
                    break;
                case liwu:
                    int help_number = msg.arg1;
                    String live_gift_id = (String) msg.obj;
                    break;
            }
        }
    };

    private void back() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopPlay();
                doFinish();
            }
        }, 100);//秒后执行Runnable中的run方法
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
