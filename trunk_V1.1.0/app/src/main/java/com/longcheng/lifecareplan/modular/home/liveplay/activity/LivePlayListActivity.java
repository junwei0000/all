package com.longcheng.lifecareplan.modular.home.liveplay.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alivc.live.pusher.AlivcLivePushConfig;
import com.alivc.live.pusher.AlivcLivePushConstants;
import com.alivc.live.pusher.AlivcPreviewOrientationEnum;
import com.alivc.live.pusher.AlivcResolutionEnum;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.home.liveplay.adapter.PlayListAdapter;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePlayItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LivePushDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.fragment.SharedPreferenceUtils;
import com.longcheng.lifecareplan.utils.DatesUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;

import butterknife.BindView;

import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_AUDIO_RETRY_COUNT;
import static com.alivc.live.pusher.AlivcLivePushConstants.DEFAULT_VALUE_INT_RETRY_INTERVAL;
import static com.alivc.live.pusher.AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT;

/**
 * 直播列表
 */
public class LivePlayListActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.play_view)
    ListView playView;
    @BindView(R.id.layout_notlive)
    LinearLayout layout_notlive;
    @BindView(R.id.layout_live_push)
    LinearLayout layoutLivePush;

    private String uid;
    private String Pushurl;
    private AlivcLivePushConfig mAlivcLivePushConfig;
    private AlivcResolutionEnum mDefinition = AlivcResolutionEnum.RESOLUTION_720P;
    private boolean mAsyncValue = true;
    private boolean mAudioOnlyPush = false;
    private boolean mVideoOnlyPush = false;
    private AlivcPreviewOrientationEnum mOrientationEnum = ORIENTATION_PORTRAIT;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private boolean isFlash = false;

    private String mAuthTimeStr = "";
    private String mPrivacyKeyStr = "";
    private boolean mMixStream = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.layout_live_push:
                mPresent.getLivePush(uid);
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_play_list;
    }


    @Override
    public void initView(View view) {
        pageTopTvName.setText("直播列表");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        layout_notlive.setVisibility(View.GONE);
        pagetopLayoutLeft.setOnClickListener(this);
        layoutLivePush.setOnClickListener(this);
        Intent intent = getIntent();
        int IsLiveBroadcast = intent.getIntExtra("IsLiveBroadcast", 0);
        if (IsLiveBroadcast == 0) {
            layoutLivePush.setVisibility(View.GONE);
        } else {
            layoutLivePush.setVisibility(View.VISIBLE);
        }
        playView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (playList != null && playList.size() > 0) {
                    Intent intent = new Intent(mActivity, LivePlayActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("uid", playList.get(position).getUid());
                    intent.putExtra("playTitle", playList.get(position).getPlayTile());
                    intent.putExtra("live_name", playList.get(position).getName());
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void initDataAfter() {
        uid = UserUtils.getUserId(mContext);
        mAlivcLivePushConfig = new AlivcLivePushConfig();
        AlivcLivePushConfig.setMediaProjectionPermissionResultData(null);
        mPresent.getLivePlayList();
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
        Pushurl = responseBean.getPushurl();
        String playTile = "";
        String live_name="";
        if (playList != null) {
            for (LivePlayItemInfo livePlayItemInfo : playList) {
                if (uid.equals(livePlayItemInfo.getUid())) {
                    playTile = livePlayItemInfo.getPlayTile();
                    live_name=livePlayItemInfo.getName();
                    break;
                }
            }
        }
        if (getPushConfig() != null) {
            LivePushActivity.startActivity(this, mAlivcLivePushConfig, Pushurl,
                    mAsyncValue, mAudioOnlyPush, mVideoOnlyPush, mOrientationEnum,
                    mCameraId, isFlash, mAuthTimeStr, mPrivacyKeyStr, mMixStream,
                    mAlivcLivePushConfig.isExternMainStream(), playTile,live_name);
        }
    }

    @Override
    public void BackPlaySuccess(LivePushDataInfo responseBean) {

    }

    ArrayList<LivePlayItemInfo> playList;

    @Override
    public void BackPlayListSuccess(LivePushDataInfo responseBean) {
        playList = responseBean.getPlayList();
        String time = DatesUtils.getInstance().getNowTime("yyyy-MM-dd HH:mm:ss");
        if (playList == null) {
            playList = new ArrayList<>();
            playList.add(new LivePlayItemInfo("113", R.mipmap.zhang, "生命呵护计划-海南调研", "张秋利", HomeFragment.jieqi_name, time));
            playList.add(new LivePlayItemInfo("134", R.mipmap.yun, "国际大数据与数据科学进展主题论坛", "云莉雅", HomeFragment.jieqi_name, time));
        }
        PlayListAdapter mAdapter = new PlayListAdapter(mContext, playList);
        playView.setAdapter(mAdapter);
        playView.setVisibility(View.VISIBLE);
    }

    @Override
    public void Error() {

    }

    private AlivcLivePushConfig getPushConfig() {
        if (TextUtils.isEmpty(Pushurl)) {
            Toast.makeText(this, "请重新获取直播信息", Toast.LENGTH_LONG).show();
            return null;
        }
        mAlivcLivePushConfig.setConnectRetryInterval(DEFAULT_VALUE_INT_RETRY_INTERVAL);
        mAlivcLivePushConfig.setConnectRetryCount(DEFAULT_VALUE_INT_AUDIO_RETRY_COUNT);
        mAlivcLivePushConfig.setResolution(mDefinition);
        mAlivcLivePushConfig.setInitialVideoBitrate(Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P.DEFAULT_VALUE_INT_INIT_BITRATE.getBitrate())));
        mAlivcLivePushConfig.setAudioBitRate(1000 * 64);
        mAlivcLivePushConfig.setMinVideoBitrate(Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P_RESOLUTION_FIRST.DEFAULT_VALUE_INT_MIN_BITRATE.getBitrate())));
        SharedPreferenceUtils.setMinBit(getApplicationContext(), Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P_RESOLUTION_FIRST.DEFAULT_VALUE_INT_MIN_BITRATE.getBitrate())));
        mAlivcLivePushConfig.setTargetVideoBitrate(Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P_FLUENCY_FIRST.DEFAULT_VALUE_INT_TARGET_BITRATE.getBitrate())));
        SharedPreferenceUtils.setTargetBit(getApplicationContext(), Integer.valueOf(String.valueOf(AlivcLivePushConstants.BITRATE_540P_FLUENCY_FIRST.DEFAULT_VALUE_INT_TARGET_BITRATE.getBitrate())));

        SharedPreferenceUtils.setHintTargetBit(getApplicationContext(), AlivcLivePushConstants.BITRATE_540P.DEFAULT_VALUE_INT_TARGET_BITRATE.getBitrate());
        SharedPreferenceUtils.setHintMinBit(getApplicationContext(), AlivcLivePushConstants.BITRATE_540P.DEFAULT_VALUE_INT_MIN_BITRATE.getBitrate());
        return mAlivcLivePushConfig;
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
