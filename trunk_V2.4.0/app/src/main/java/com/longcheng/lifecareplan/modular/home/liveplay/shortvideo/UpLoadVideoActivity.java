package com.longcheng.lifecareplan.modular.home.liveplay.shortvideo;

import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.http.basebean.BasicResponse;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushContract;
import com.longcheng.lifecareplan.modular.home.liveplay.activity.LivePushPresenterImp;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveStatusInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoDataInfo;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.VideoItemInfo;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * 作者：jun on
 * 时间：2019/11/11 17:44
 * 意图：上传短视频
 */
public class UpLoadVideoActivity extends BaseActivityMVP<LivePushContract.View, LivePushPresenterImp<LivePushContract.View>> implements LivePushContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_left)
    LinearLayout layoutLeft;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.layout_rigth)
    LinearLayout layoutRigth;
    @BindView(R.id.iv_thumb)
    RoundedImageView ivThumb;
    @BindView(R.id.tv_cont)
    EditText tv_cont;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.layout_city)
    LinearLayout layoutCity;
    @BindView(R.id.tv_upload)
    TextView tvUpload;

    LocationUtils mLocationUtils;
    String city;
    double phone_user_latitude;
    double phone_user_longitude;
    private int mVideoSource; // 视频来源
    private String mVideoPath;
    private String mCoverImagePath;
    //录制界面传过来的视频分辨率
    private int mVideoResolution;
    //视频时长（ms）
    private long mVideoDuration;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_left:
                back();
                break;
            case R.id.tv_upload:

                break;
        }

    }


    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.live_shortvideo_upload;
    }

    @Override
    public void initView(View view) {
        Immersive.setOrChangeTranslucentColorTransparent(mActivity, toolbar, getResources().getColor(R.color.transparent), false);
    }

    @Override
    public void setListener() {
        layoutLeft.setOnClickListener(this);
        tvUpload.setOnClickListener(this);
    }


    @Override
    public void initDataAfter() {
        layoutTitle.getBackground().setAlpha(50);
        layoutCity.getBackground().setAlpha(50);
        if (mLocationUtils == null) {
            mLocationUtils = new LocationUtils();
        }
        city = mLocationUtils.getAddressCity(mContext);
        double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mContext);
        phone_user_latitude = mLngAndLat[0];
        phone_user_longitude = mLngAndLat[1];
        tvCity.setText("" + city);

        mVideoSource = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
        mVideoPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_VIDEPATH);
        mCoverImagePath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
        mVideoDuration = getIntent().getLongExtra(TCConstants.VIDEO_RECORD_DURATION, 0);
        mVideoResolution = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_RESOLUTION, -1);
        if (mCoverImagePath != null && !mCoverImagePath.isEmpty()) {
            Glide.with(this).load(Uri.fromFile(new File(mCoverImagePath)))
                    .into(ivThumb);
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

    }

    @Override
    public void openRoomPaySuccess(BasicResponse<LiveStatusInfo> responseBean) {

    }

    @Override
    public void getUserLiveStatusSuccess(BasicResponse<LiveStatusInfo> responseBean) {

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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void back() {
        doFinish();
    }

    @Override
    public void onBackPressed() {
        back();
    }

}
