package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.BaseScrollPickerView;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.RecordMode;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.RecordState;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.view.StringScrollPicker;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.AblumUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
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
    String cover_url;
    String city;
    double phone_user_latitude;
    double phone_user_longitude;
    String cost;
    LocationUtils mLocationUtils;

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
                mAblumUtils.onAddAblumClick();
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
                    if (mLocationUtils == null) {
                        mLocationUtils = new LocationUtils();
                    }
                    city = mLocationUtils.getAddressCity(mContext);
                    double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mContext);
                    phone_user_latitude = mLngAndLat[0];
                    phone_user_longitude = mLngAndLat[1];
                    tvLivecity.setText("" + city);
                }
            }
        });
    }

    TXLivePusher mLivePusher;
    AblumUtils mAblumUtils;

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
        mAblumUtils = new AblumUtils(this, mHandler, UPDATEABLUM);
        mAblumUtils.setCropStaus(false);
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
    public void setFollowLiveSuccess(BasicResponse responseBean) {

    }

    @Override
    public void applyLiveSuccess(BasicResponse responseBean) {

    }


    @Override
    public void editAvatarSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            cover_url = responseBean.getData();
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void openRoomPaySuccess(BasicResponse<LiveStatusInfo> responseBean) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            LiveStatusInfo mLiveStatusInfo = responseBean.getData();
            if (mLiveStatusInfo != null) {
                String pushUrl = mLiveStatusInfo.getPushUrl();
                String live_room_id = mLiveStatusInfo.getLive_room_id();
                LivePushActivity.startActivity(this, pushUrl, live_room_id);
            }
        } else {
            ToastUtils.showToast("" + responseBean.getMsg());
        }
    }

    @Override
    public void getUserLiveStatusSuccess(BasicResponse<LiveStatusInfo> responseBean) {
        int errcode = responseBean.getStatus();
        if (errcode == 0) {
            LiveStatusInfo mLiveStatusInfo = (LiveStatusInfo) responseBean.getData();
            if (mLiveStatusInfo != null) {
                int status = mLiveStatusInfo.getStatus();
                if (status == 1) {
                    int is_charge = mLiveStatusInfo.getIs_charge();//是否收费 1：收费 2：免费
                    cost = mLiveStatusInfo.getCost();
                    if (TextUtils.isEmpty(cost)) {
                        cost = "0";
                    }
                    if (is_charge == 1) {
                        showPopupWindow();
                    } else if (is_charge == 2) {
                        mHandler.sendEmptyMessage(openRoomPay);
                    }

                } else if (status == -1) {
                    ToastUtils.showToast("申请已驳回");
                } else {
                    ToastUtils.showToast("申请审核中");
                }
            }
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
    public void sendLCommentSuccess(BasicResponse responseBean) {

    }

    @Override
    public void giveGiftSuccess(BasicResponse responseBean) {

    }

    @Override
    public void Error() {

    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void showGiftDialog() {

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

    TextView tvskb;
    MyDialog selectDialog;

    public void showPopupWindow() {
        if (selectDialog == null) {
            selectDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_live_pay);// 创建Dialog并设置样式主题
            selectDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = selectDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            selectDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = selectDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            selectDialog.getWindow().setAttributes(p); //设置生效
            tvskb = (TextView) selectDialog.findViewById(R.id.tvskb);
            TextView tv_cancel = (TextView) selectDialog.findViewById(R.id.tv_cancel);
            TextView tv_sure = (TextView) selectDialog.findViewById(R.id.tv_sure);
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                }
            });
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectDialog.dismiss();
                    mHandler.sendEmptyMessage(openRoomPay);
                }
            });
        } else {
            selectDialog.show();
        }
        tvskb.setText("" + cost);
    }

    /*
     * 调用相册
     */
    protected static final int UPDATEABLUM = 3;
    protected static final int SETRESULT = 4;
    protected static final int openRoomPay = 5;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case openRoomPay:
                    if (TextUtils.isEmpty(cover_url)) {
                        ToastUtils.showToast("请选择封面图");
                        break;
                    }
                    String title = tvLivetitle.getText().toString();
                    if (TextUtils.isEmpty(title)) {
                        ToastUtils.showToast("请输入直播主题");
                        break;
                    }
                    mPresent.openRoomPay(title, cover_url, city, phone_user_longitude, phone_user_latitude, cost);
                    break;
                case UPDATEABLUM:
                    Bitmap mBitmap = (Bitmap) msg.obj;
                    ivThumb.setImageBitmap(mBitmap);
                    //上传头像
                    String file = mAblumUtils.Bitmap2StrByBase64(mBitmap);
                    mPresent.uploadImg(file);
                    break;
                case SETRESULT:
                    int requestCode = msg.arg1;
                    int resultCode = msg.arg2;
                    Intent data = (Intent) msg.obj;
                    mAblumUtils.setResult(requestCode, resultCode, data);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == mAblumUtils.RESULTCAMERA || requestCode == mAblumUtils.RESULTGALLERY
                    || requestCode == mAblumUtils.RESULTCROP) {
                Message msgMessage = new Message();
                msgMessage.arg1 = requestCode;
                msgMessage.arg2 = resultCode;
                msgMessage.obj = data;
                msgMessage.what = SETRESULT;
                mHandler.sendMessage(msgMessage);
                msgMessage = null;
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
