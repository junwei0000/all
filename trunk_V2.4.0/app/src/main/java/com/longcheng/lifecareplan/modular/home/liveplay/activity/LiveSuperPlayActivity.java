package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
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
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.rtmp.TXLiveConstants;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 超级播放器--点播
 */
public class LiveSuperPlayActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

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

    @BindView(R.id.preview_view)
    SuperPlayerView mSuperPlayerView;
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
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_superplay;
    }


    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), true);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public void setListener() {
        iv_notLive.setVisibility(View.GONE);
        fragTvFollow.setVisibility(View.GONE);
        btnLiwu.setVisibility(View.GONE);
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
        String Rebroadcast_url = getIntent().getStringExtra("Rebroadcast_url");
        palyVideo(Rebroadcast_url);
        String live_room_id = getIntent().getStringExtra("live_room_id");
        mPresent.getLivePlayInfo(live_room_id);
    }

    /**
     * 播放视频
     */
    private void palyVideo(String playurl) {
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
        mSuperPlayerView.setPlayerViewCallback(new SuperPlayerView.OnSuperPlayerViewCallback() {
            @Override
            public void onStartFullScreenPlay() {

            }

            @Override
            public void onStopFullScreenPlay() {

            }

            @Override
            public void onClickFloatCloseBtn() {

            }

            @Override
            public void onClickSmallReturnBtn() {

            }

            @Override
            public void onStartFloatWindowPlay() {

            }
        });
        final SuperPlayerModel superPlayerModelV3 = new SuperPlayerModel();
        superPlayerModelV3.url = playurl;
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

    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mRxAppCompatActivity, this);
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
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            LiveDetailInfo mLiveDetailInfo = responseBean.getData();
            if (mLiveDetailInfo != null) {
                LiveDetailItemInfo info = mLiveDetailInfo.getInfo();
                if (info != null) {
                    fragTvCity.setText("" + info.getAddress());
                    fragTvJieqi.setText(info.getCurrent_jieqi_cn() + "节气");
                }
            }
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
    }

    @Override
    public void BackLiveListSuccess(BasicResponse<VideoDataInfo> responseBean, int backPage) {

    }

    @Override
    public void BackVideoListSuccess(BasicResponse<ArrayList<VideoItemInfo>> responseBean, int backPage) {

    }

    @Override
    public void Error() {
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
