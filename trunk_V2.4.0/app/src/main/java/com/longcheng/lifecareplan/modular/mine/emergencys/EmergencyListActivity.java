package com.longcheng.lifecareplan.modular.mine.emergencys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.mine.emergencys.facerecognition.FaceRecognitionUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.CanScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EmergencyListActivity extends BaseActivityMVP<EmergencyContract.View, EmergencyPresenterImp<EmergencyContract.View>> implements EmergencyContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.view_pager)
    CanScrollViewPager viewPager;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.tvFinish)
    TextView tvFinish;
    @BindView(R.id.tv_apply_help)
    TextView tv_apply_help;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;

    private String user_id = "";


    private String msg = "";
    private String card = "";
    private String name = "";
    private boolean isClick = true;

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_emergency_list;
    }

    @Override
    public void initView(View view) {

    }

    @Override
    public void initDataAfter() {
        setOrChangeTranslucentColor(toolbar, null);
        pageTopTvName.setText("救急列表");
        user_id = UserUtils.getUserId(mContext);
        LogUtils.e("user_id" + user_id);
        LogUtils.e("user_id" + ExampleApplication.token);
        mPresent.indexExtend(user_id, 1);
        mPresent.isCertify(user_id);
        initData();
    }

    private void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ProgressFragment.newInstance());
        fragmentList.add(FinishFragment.newInstance());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        //viewPager事件
        viewPager.setScanScroll(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    tvFinish.setTextColor(getResources().getColor(R.color.black));
                    tvProgress.setTextColor(getResources().getColor(R.color.red));
                    viewPager.setCurrentItem(0);
                } else if (position == 1) {
                    tvFinish.setTextColor(getResources().getColor(R.color.red));
                    tvProgress.setTextColor(getResources().getColor(R.color.black));
                    viewPager.setCurrentItem(1);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void back() {
        doFinish();
    }

    FaceRecognitionUtils mFaceRecognitionUtils;

    @Override
    public void setListener() {
        tvProgress.setOnClickListener(this);
        tvFinish.setOnClickListener(this);
        tv_apply_help.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        mFaceRecognitionUtils = new FaceRecognitionUtils(mActivity, mHandler);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvFinish:
                tvFinish.setTextColor(getResources().getColor(R.color.red));
                tvProgress.setTextColor(getResources().getColor(R.color.black));
                viewPager.setCurrentItem(1);
                break;
            case R.id.tvProgress:
                tvFinish.setTextColor(getResources().getColor(R.color.black));
                tvProgress.setTextColor(getResources().getColor(R.color.red));
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_apply_help:
                if (isClick) {
                    mFaceRecognitionUtils.requestCameraPerm();
                } else {
                    ToastUtils.showToast(msg);
                }
                break;
            case R.id.pagetop_layout_left:
                ConfigUtils.getINSTANCE().closeSoftInput(mActivity);
                back();
                break;
        }
    }

    @Override
    protected EmergencyPresenterImp<EmergencyContract.View> createPresent() {
        return new EmergencyPresenterImp<>(mContext, this);
    }

    private void gotoFinish() {
        mPresent.applyEmergencys(user_id);
    }


    @Override
    public void ListSuccess(EmergencyListBean responseBean) {

        EmergencyListUserBean userBean = responseBean.getData();
        showData(userBean);


    }

    private void showData(EmergencyListUserBean userBean) {
        LogUtils.e("getIsCanApply" + userBean.getIsCanApply());
        if (userBean.getIsCanApply() == 0) {
            tv_apply_help.setVisibility(View.GONE);
        } else {
            tv_apply_help.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void ApplySucess(ApplyEmergencyBean str) {
        if (str.getStatus().equals("200")) {
            tv_apply_help.setVisibility(View.GONE);
            LogUtils.e(str.getMsg() + "," + str.getData() + "," + str.getStatus());
            ToastUtils.showToast(str.getMsg());
            Intent it = new Intent(EmergencyListActivity.this, ResultActivity.class);
            it.putExtra("result", "申请成功");
            startActivity(it);
        }

    }

    @Override
    public void getCard(CertifyBean str) {
        isClick = true;
        card = str.getData().getIdNo();
        name = str.getData().getName();
        mFaceRecognitionUtils.setCardInfo(card, name);
    }

    @Override
    public void getCardError(String msg) {
        this.msg = msg;
        ToastUtils.showToast(msg);
        isClick = false;
    }

    @Override
    public void ListError() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {
    }


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FaceRecognitionUtils.faceSucecess:
                    gotoFinish();
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (mFaceRecognitionUtils != null)
            mFaceRecognitionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
