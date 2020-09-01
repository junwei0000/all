package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailListDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * nfc行动-能量注入
 */
public class NFCEnergyZhuRuActivity extends BaseActivityMVP<NFCDetailContract.View, NFCDetailPresenterImp<NFCDetailContract.View>> implements NFCDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.iv_thumb)
    ImageView ivThumb;
    @BindView(R.id.iv_wuxing)
    ImageView ivWuxing;
    @BindView(R.id.relat_info)
    FrameLayout relat_info;
    @BindView(R.id.layout_topok)
    LinearLayout layout_topok;
    @BindView(R.id.iv_bowen)
    WaveView mWaveView;

    @BindView(R.id.layout_progress)
    LinearLayout layout_progress;
    @BindView(R.id.tv_progesss)
    TextView tv_progesss;
    @BindView(R.id.progesssbar)
    ProgressBar progesssbar;


    ArrayList<NFCDetailItemBean> list = new ArrayList<>();
    ArrayList<NFCDetailItemBean> zuobiaoList = new ArrayList<>();
    int num = 20;

    int WaveIndex = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.nfc_detail_zhuru;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("生命能量导入");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);

    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        String order_id = intent.getStringExtra("order_id");
        mPresent.getNFCEnergyImport(order_id);
        int width = DensityUtil.screenWith(mContext) / 2;
        int height = DensityUtil.screenHigh(mContext) / 2;


        zuobiaoList.add(new NFCDetailItemBean(-width * 10 / 10, -width * 0 / 10));
        //左上
        zuobiaoList.add(new NFCDetailItemBean(-width * 9 / 10, -width * 2 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 8 / 10, -width * 3 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 7 / 10, -width * 5 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 5 / 10, -width * 8 / 10));

        zuobiaoList.add(new NFCDetailItemBean(-width * 0 / 10, -width * 10 / 10));
        //右上
        zuobiaoList.add(new NFCDetailItemBean(width * 5 / 10, -width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 6 / 10, -width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 7 / 10, -width * 6 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 8 / 10, -width * 5 / 10));

        zuobiaoList.add(new NFCDetailItemBean(width * 10 / 10, -width * 0 / 10));
        //右下
        zuobiaoList.add(new NFCDetailItemBean(width * 8 / 10, width * 5 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 7 / 10, width * 6 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 6 / 10, width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 5 / 10, width * 8 / 10));

        zuobiaoList.add(new NFCDetailItemBean(width * 0 / 10, width * 10 / 10));
        //左下
        zuobiaoList.add(new NFCDetailItemBean(-width * 5 / 10, width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 6 / 10, width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 7 / 10, width * 6 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 8 / 10, width * 5 / 10));

    }

    @Override
    protected NFCDetailPresenterImp<NFCDetailContract.View> createPresent() {
        return new NFCDetailPresenterImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }

    @Override
    public void DetailSuccess(NFCDetailDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("200")) {
            NFCDetailAfterBean data = responseBean.getData();
            NFCDetailItemBean OrderUserInfo = data.getOrderUserInfo();
            if (OrderUserInfo != null) {
                GlideDownLoadImage.getInstance().loadCircleImage(OrderUserInfo.getAvatar(), ivThumb);
            }
            wuxinganimation = AnimationUtils.loadAnimation(this, R.anim.wuxing_whirl);
            wuxinganimation.setInterpolator(new LinearInterpolator());//实现匀速动画
            //动画执行完毕后是否停在结束时的角度上
            wuxinganimation.setFillAfter(true);
            ivWuxing.startAnimation(wuxinganimation);

            mHandler.sendEmptyMessageDelayed(33, 1800 * 0);
            mHandler.sendEmptyMessageDelayed(33, 1800 * 1);
            mHandler.sendEmptyMessageDelayed(33, 1800 * 2);
            mHandler.sendEmptyMessageDelayed(33, 1800 * 3);
            mHandler.sendEmptyMessageDelayed(33, 1800 * 4);


            ArrayList<NFCDetailItemBean> list_ = data.getHelpAbilityRanking();
            if (list_ != null && list_.size() > 0) {
                Collections.shuffle(list_);//打乱排序 每次展示出来的都是随机的
                if (list_.size() < num) {
                    int num_ = num / list_.size() + 1;
                    for (int j = 0; j < num_; j++) {
                        Collections.shuffle(list_);
                        list.addAll(list_);
                    }
                } else {
                    list.addAll(list_);
                }
                Log.e("onAnimationEnd", "" + list.size());
                mHandler.sendEmptyMessage(11);
                mTimeTaskScroll = new TimeTaskScroll();
                new Timer().schedule(mTimeTaskScroll, 0, 80);
            }
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }

    @Override
    public void DetailListSuccess(NFCDetailDataBean responseBean, int backpage) {

    }

    @Override
    public void PayHelpSuccess(ResponseBean responseBean) {

    }

    @Override
    public void DetailRecordSuccess(NFCDetailListDataBean responseBean) {

    }

    @Override
    public void ListError() {
    }


    TimeTaskScroll mTimeTaskScroll;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressCancel();
        if (mWaveView != null) {
            mWaveView.stop();
        }
    }

    private void progressCancel() {
        if (mTimeTaskScroll != null) {
            mTimeTaskScroll.cancel();
            mHandler.removeCallbacks(mTimeTaskScroll);
        }
    }

    public class TimeTaskScroll extends TimerTask {


        private Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                //  控制速度
                setPos();
                Log.e("getwalletSuccess", "Message-----");
            }
        };

        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    }

    int progress;

    /**
     * 设置进度显示在对应的位置
     */
    public void setPos() {
        progress++;
        progesssbar.setProgress(progress);
        int w = getWindowManager().getDefaultDisplay().getWidth();
        Log.e("progress==", "" + progress);
        int contentlen = DensityUtil.dip2px(mContext, 30);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                contentlen,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        int progresslen = DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 60);
        int progress = progesssbar.getProgress();
        float left = progresslen * progress / 100;
        if (progress > 0 && progress <= 5) {
            left = left;
        } else if (progress > 5 && progress <= 10) {
            left = left - contentlen / 5;
        } else if (progress > 10 && progress <= 20) {
            left = left - contentlen / 4;
        } else if (progress > 20 && progress <= 50) {
            left = left - contentlen / 3;
        } else if (progress > 50 && progress <= 95) {
            left = left - contentlen / 2;
        } else {
            left = progresslen - contentlen;
        }
        params.leftMargin = (int) left;
        params.setMargins((int) left, 0, 0, 0);
        tv_progesss.setLayoutParams(params);
        String mCurrentDrawText = String.format("%d", progress * 100 / 100);
        mCurrentDrawText = mCurrentDrawText + "%";
        tv_progesss.setText(mCurrentDrawText);
        if (progress == 100) {
            progressCancel();
            layout_progress.setVisibility(View.INVISIBLE);
        }
    }

    int i;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 11:
                    float fromX = zuobiaoList.get(i).getFromX();
                    float fromY = zuobiaoList.get(i).getFromY();
                    View view = LayoutInflater.from(mContext).inflate(R.layout.nfc_detail_daoruinfoitem, null);
                    ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
                    TextView tv_name = view.findViewById(R.id.tv_name);
                    TextView tv_price = view.findViewById(R.id.tv_price);
                    tv_name.setText(CommonUtil.setName(list.get(i).getUser_name()));
                    tv_price.setText(list.get(i).getPrice());
                    GlideDownLoadImage.getInstance().loadCircleImage(list.get(i).getAvatar(), iv_thumb);
                    if (relat_info != null) {
                        relat_info.addView(view);
                    }
                    setAnim(view, fromX, fromY, i);
                    i++;
                    if (i < num) {
                        mHandler.sendEmptyMessageDelayed(11, 380);
                    }
                    break;
                case 22:
                    AnimationSet set = new AnimationSet(true);
                    TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0.0f, 50);
                    translateAnimation.setFillAfter(true);
                    set.addAnimation(translateAnimation);
                    ScaleAnimation scaleAnimation = new ScaleAnimation(
                            1f, 0.9f, 1f, 0.9f,
                            Animation.RELATIVE_TO_SELF, 0.5f,
                            Animation.RELATIVE_TO_SELF, 0.5f);
                    set.addAnimation(scaleAnimation);
                    set.setDuration(300);
                    set.setRepeatMode(Animation.INFINITE);
                    layout_topok.startAnimation(set);
                    break;
                case 33:
                    if (mWaveView != null) {
                        mWaveView.setDuration(4000);
                        mWaveView.setStyle(Paint.Style.FILL);
                        if (WaveIndex == 0) {
                            mWaveView.setColor(Color.parseColor("#00ff03"));
                        } else if (WaveIndex == 1) {
                            mWaveView.setColor(Color.parseColor("#ff0012"));
                        } else if (WaveIndex == 2) {
                            mWaveView.setColor(Color.parseColor("#a35916"));
                        } else if (WaveIndex == 3) {
                            mWaveView.setColor(Color.parseColor("#fdf5e5"));
                        } else if (WaveIndex == 4) {
                            mWaveView.setColor(Color.parseColor("#0673fb"));
                        }
                        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
                        mWaveView.start();
                        Log.e("onAnimationEnd", "WaveIndex=" + WaveIndex);
                        WaveIndex++;
                        WaveIndex = WaveIndex % 5;
                    }
                    break;
            }
        }
    };
    Animation wuxinganimation;

    /**
     * 用户信息导入
     *
     * @param view
     * @param fromX
     * @param fromY
     * @param i
     */
    private void setAnim(View view, float fromX, float fromY, int i) {
        //组合
        AnimationSet set = new AnimationSet(true);
        //位移
        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, 0.0f, fromY, 0.0f);
        set.addAnimation(translateAnimation);
        //缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1, 0.5f, 1, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(scaleAnimation);

        set.setDuration(800);
        set.setRepeatMode(Animation.INFINITE);
        view.startAnimation(set);
        if (i == num - 1) {
            translateAnimation.setAnimationListener(animationListener);
        }
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.e("onAnimationEnd", "onAnimationEnd");
            showTopOk();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };


    /**
     * 导入成功
     */
    private void showTopOk() {
        mWaveView.stop();
        mWaveView.setVisibility(View.INVISIBLE);
        layout_topok.setVisibility(View.VISIBLE);
        Animation atop = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.nfc_daoruok_set);
        layout_topok.startAnimation(atop);
        mHandler.sendEmptyMessageDelayed(22, 700);
    }


    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doFinish();
        }
        return false;
    }

}
