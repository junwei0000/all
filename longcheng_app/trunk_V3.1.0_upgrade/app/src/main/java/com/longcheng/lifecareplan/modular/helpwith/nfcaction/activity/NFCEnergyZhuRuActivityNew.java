package com.longcheng.lifecareplan.modular.helpwith.nfcaction.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.nfcaction.bean.NFCDetailListDataBean;
import com.longcheng.lifecareplan.push.jpush.message.EasyMessage;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * nfc行动-能量注入
 */
public class NFCEnergyZhuRuActivityNew extends BaseActivityMVP<NFCDetailContract.View, NFCDetailPresenterImp<NFCDetailContract.View>> implements NFCDetailContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.video_view)
    fullScreenVideoView mTXCloudVideoView;
    @BindView(R.id.relat_info)
    FrameLayout relat_info;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.layout_topok)
    LinearLayout layout_topok;

    ArrayList<NFCDetailItemBean> list = new ArrayList<>();
    ArrayList<NFCDetailItemBean> zuobiaoList = new ArrayList<>();
    int num = 20;

    double phone_user_latitude;
    double phone_user_longitude;
    String city, order_id, nfc_sn;
    LocationUtils mLocationUtils;

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
        return R.layout.nfc_detail_zhuru_new;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("生命能量导入");
        setOrChangeTranslucentColor(toolbar, null);
    }


    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.nfc_daoru;
        mTXCloudVideoView.setVideoURI(Uri.parse(uri));
        mTXCloudVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START)
                            mTXCloudVideoView.setBackgroundColor(Color.TRANSPARENT);
                        return true;
                    }
                });
            }
        });
    }

    long time;
    boolean show = true;
    private Thread thread;

    /**
     * 开始计时
     */
    private void initTimer() {
        if (thread == null) {
            thread = new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (show) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("sunAnim", "" + (System.currentTimeMillis() - time));
                        if (System.currentTimeMillis() - time >= 5 * 1000) {
                            mHandler.sendEmptyMessage(11);
                            show = false;
                        }
                    }
                }
            };
            thread.start();
        }
    }

    boolean destory = false;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destory = true;
        if (thread != null) {
            mHandler.removeCallbacks(thread);
        }
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
        nfc_sn = intent.getStringExtra("nfc_sn");
        if (mLocationUtils == null) {
            mLocationUtils = new LocationUtils();
        }
        double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mContext);
        phone_user_latitude = mLngAndLat[0];
        phone_user_longitude = mLngAndLat[1];
        city = mLocationUtils.getAddressCity(mContext, phone_user_latitude, phone_user_longitude);
        mPresent.getNFCEnergyImport(order_id);
        int width = DensityUtil.screenWith(mContext) * 3 / 5;

        zuobiaoList.add(new NFCDetailItemBean(-width * 10 / 10, -width * 0 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 9 / 10, -width * 2 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 8 / 10, -width * 3 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 5 / 10, -width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 6 / 10, -width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 8 / 10, width * 5 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 7 / 10, width * 6 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 6 / 10, width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 5 / 10, width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 6 / 10, width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 7 / 10, width * 6 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 7 / 10, -width * 5 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 5 / 10, -width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 0 / 10, -width * 10 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 5 / 10, -width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 7 / 10, -width * 6 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 8 / 10, -width * 5 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 10 / 10, -width * 0 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 8 / 10, width * 5 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 7 / 10, width * 6 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 5 / 10, width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 0 / 10, width * 10 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 8 / 10, -width * 3 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 5 / 10, -width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 5 / 10, width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(width * 6 / 10, width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 5 / 10, width * 8 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 6 / 10, width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 6 / 10, width * 7 / 10));
        zuobiaoList.add(new NFCDetailItemBean(-width * 8 / 10, width * 5 / 10));
        num = zuobiaoList.size();
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
            time = System.currentTimeMillis();
            mTXCloudVideoView.start();
            initTimer();

            NFCDetailAfterBean data = responseBean.getData();
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

    int i;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 33:
                    doFinish();
                    break;
                case 11:
                    if (list != null && !destory) {
                        float fromX = zuobiaoList.get(i).getFromX();
                        float fromY = zuobiaoList.get(i).getFromY();
                        View view = LayoutInflater.from(mContext).inflate(R.layout.nfc_detail_daoruinfoitem, null);
                        ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
                        TextView tv_name = view.findViewById(R.id.tv_name);
                        TextView tv_price = view.findViewById(R.id.tv_price);
                        iv_thumb.setVisibility(View.GONE);
                        tv_price.setVisibility(View.GONE);
                        String name = list.get(i).getUser_name();
                        if (!TextUtils.isEmpty(name) && name.length() > 3) {
                            name = name.substring(0, 3);
                        }
                        tv_name.setText(CommonUtil.setName(name));
                        int[] loc = new int[2];
                        loc[0] = (int) fromX;
                        loc[1] = (int) fromY;
                        Log.e("playAnimation", "loc==" + loc[0] + "    " + loc[1]);
                        setAnimNew(view, fromX, fromY, i);
                        i++;
                        if (i < num) {
                            mHandler.sendEmptyMessageDelayed(11, 116);
                        }
                    }
                    break;
            }
        }
    };


    private void setAnimNew(View view, float fromX, float fromY, int i) {
        if (relat_info != null) {
            relat_info.addView(view);
        }
        //位移
        ObjectAnimator translationX = new ObjectAnimator().ofFloat(view, "translationX", fromX, 0);
        ObjectAnimator translationY = new ObjectAnimator().ofFloat(view, "translationY", fromY, 0);
        //旋转
        ObjectAnimator ra = ObjectAnimator.ofFloat(view, "rotation", 0f, 40f);
        //缩放
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1, 0.1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1, 0.1f);
        //透明
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.1f);
        anim.setDuration(3000);
        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translationX, translationY, ra, scaleX, scaleY, anim); //设置动画
        animatorSet.setDuration(2200);  //设置动画时间
        animatorSet.start(); //启动
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
                if (i == num - 1) {
                    showTopOk();
                    mPresent.addRecord(order_id, phone_user_longitude, phone_user_latitude, city, nfc_sn);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 用户信息导入
     *
     * @param view
     * @param fromX
     * @param fromY
     * @param i
     */
    private void setAnim(View view, float fromX, float fromY, int i) {
        if (relat_info != null) {
            relat_info.addView(view);
        }
        //组合
        AnimationSet set = new AnimationSet(true);
        //旋转
        Animation animation = new RotateAnimation(0, 20);
        set.addAnimation(animation);
        //位移
        TranslateAnimation translateAnimation = new TranslateAnimation(fromX, fromX * 3 / 5, fromY, fromY * 3 / 5);
        set.addAnimation(translateAnimation);
        //缩放
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1, 0.1f, 1, 0.1f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        set.addAnimation(scaleAnimation);

        set.setDuration(2500);
        set.setRepeatMode(Animation.INFINITE);
        view.startAnimation(set);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
                if (i == num - 1) {
                    showTopOk();
                    mPresent.addRecord(order_id, phone_user_longitude, phone_user_latitude, city, nfc_sn);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 导入成功
     */
    private void showTopOk() {
        layout_topok.setVisibility(View.VISIBLE);
        Animation atop = AnimationUtils.loadAnimation(
                getApplicationContext(), R.anim.nfc_daoruok_set);
        layout_topok.startAnimation(atop);
        atop.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.sendEmptyMessageDelayed(33, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        doFinish();
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
