package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.ApplyXieYiActitvty;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushContract;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.BaseScrollPickerView;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.RecordMode;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.RecordState;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.StringScrollPicker;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2019/11/11 17:44
 * 意图：短视频
 */
public class ShortVideoActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {
    @BindView(R.id.preview_view)
    TXCloudVideoView previewView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.layout_rigth)
    LinearLayout layoutRigth;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.layout_time)
    LinearLayout layoutTime;
    @BindView(R.id.layout_del)
    LinearLayout layoutDel;
    @BindView(R.id.layout_start)
    LinearLayout layoutStart;
    @BindView(R.id.layout_ok)
    LinearLayout layoutOk;
    @BindView(R.id.video_pickerview)
    StringScrollPicker videoPickerview;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.layout_shortVideo)
    LinearLayout layoutShortVideo;
    @BindView(R.id.layout_live)
    LinearLayout layoutLive;
    @BindView(R.id.layout_livetitle)
    LinearLayout layoutLivetitle;
    @BindView(R.id.iv_thumb)
    RoundedImageView ivThumb;
    @BindView(R.id.tv_livetitle)
    EditText tvLivetitle;
    @BindView(R.id.layout_livecity)
    LinearLayout layoutLivecity;
    @BindView(R.id.tv_livecity)
    TextView tvLivecity;
    @BindView(R.id.tv_livestart)
    TextView tvLivestart;

    /**
     * 功能模式
     */
    private RecordMode recordMode = RecordMode.short_video;
    /**
     * 录制状态，开始、暂停、录制中,只是针对UI变化
     */
    private RecordState recordState = RecordState.READY;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_left:
                doFinish();
                break;
            case R.id.layout_ok:
                recordState = RecordState.READY;
                updateRecordBtnView();
                break;
            case R.id.layout_del:
                recordState = RecordState.READY;
                updateRecordBtnView();
                break;
            case R.id.layout_start:
                if (recordState == RecordState.READY) {
                    recordState = RecordState.COUNT_DOWN_RECORDING;
                } else if (recordState == RecordState.COUNT_DOWN_RECORDING) {
                    recordState = RecordState.PAUSE;
                } else if (recordState == RecordState.PAUSE) {

                }
                updateRecordBtnView();
                break;
            case R.id.iv_thumb:
                ToastUtils.showToast("请选择图片");
                break;
            case R.id.tv_livestart:
                mPresent.getUserLiveStatus();
                break;
        }

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_shortvideo;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
        layoutStart.setOnClickListener(this);
        layoutDel.setOnClickListener(this);
        layoutOk.setOnClickListener(this);
        ivThumb.setOnClickListener(this);
        tvLivestart.setOnClickListener(this);
        ConfigUtils.getINSTANCE().setEditTextInhibitInputSpace(tvLivetitle, 40);
        videoPickerview.setOnSelectedListener(new BaseScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(BaseScrollPickerView baseScrollPickerView, int position) {
                Log.i(TAG, "onSelected:" + position);
                if (position == 0) {
                    layoutShortVideo.setVisibility(View.GONE);
                    layoutLive.setVisibility(View.GONE);
                    recordMode = RecordMode.Upload;
                    ToastUtils.showToast("请选择本地上传");
                } else if (position == 1) {
                    layoutShortVideo.setVisibility(View.VISIBLE);
                    layoutLive.setVisibility(View.GONE);
                    recordMode = RecordMode.short_video;
                } else if (position == 2) {
                    layoutShortVideo.setVisibility(View.GONE);
                    layoutLive.setVisibility(View.VISIBLE);
                    recordMode = RecordMode.live;
                    String city = new LocationUtils().getAddressCity(mContext);
                    tvLivecity.setText("" + city);
                }
            }
        });
    }

    private void getLivePush() {
        LivePushActivity.startActivity(this, "", "ceshi");
    }

    TXLivePusher mLivePusher;

    @Override
    public void initDataAfter() {
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        mLivePusher = new TXLivePusher(this);
        // 一般情况下不需要修改 config 的默认配置
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.startCameraPreview(previewView);
        // 设置美颜
        mLivePusher.setBeautyFilter(TXLiveConstants.BEAUTY_STYLE_SMOOTH, 4, 3, 2);
        List<String> strings = new ArrayList<>(3);
        strings.add("上传");
        strings.add("拍摄");
        strings.add("直播");
        videoPickerview.setDrawAllItem(true);
        videoPickerview.setData(strings);

        layoutLivetitle.getBackground().setAlpha(50);
        layoutLivecity.getBackground().setAlpha(50);
    }

    /**
     * 更新录制按钮状态
     */
    private void updateRecordBtnView() {
        if (recordState == RecordState.READY) {
            tvTime.setText("00:00");
            layoutTime.setVisibility(View.GONE);
            layoutDel.setVisibility(View.GONE);
            layoutOk.setVisibility(View.GONE);
            layoutBottom.setVisibility(View.VISIBLE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_start);
        } else if (recordState == RecordState.COUNT_DOWN_RECORDING) {
            layoutTime.setVisibility(View.VISIBLE);
            layoutBottom.setVisibility(View.INVISIBLE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_pause);
        } else if (recordState == RecordState.PAUSE) {
            layoutDel.setVisibility(View.VISIBLE);
            layoutOk.setVisibility(View.VISIBLE);
            layoutStart.setBackgroundResource(R.mipmap.shortvideo_restart);
        }
    }


    @Override
    protected LivePushPresenterImp<LivePushContract.View> createPresent() {
        return new LivePushPresenterImp<>(mRxAppCompatActivity, this);
    }


    @Override
    public void getUserLiveStatusSuccess(BasicResponse responseBean) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {

        } else if (errcode == -1) {
            ToastUtils.showToast("" + responseBean.getMsg());
            Intent intent = new Intent(mActivity, ApplyXieYiActitvty.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
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
    public void Error() {

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
    public void onResume() {
        super.onResume();
        if (previewView != null) {
            previewView.onResume();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (previewView != null) {
            previewView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        if (previewView != null) {
            previewView.onDestroy(); // 销毁 View
        }
        super.onDestroy();
    }

}
