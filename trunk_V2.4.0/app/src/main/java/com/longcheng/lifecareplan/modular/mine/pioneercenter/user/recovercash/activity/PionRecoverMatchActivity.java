package com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.mine.activatenergy.adapter.PushQueueAdapter;
import com.longcheng.lifecareplan.modular.mine.activatenergy.bean.EnergyItemBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.CertifyBean;
import com.longcheng.lifecareplan.modular.mine.emergencys.LoveBroadcasts;
import com.longcheng.lifecareplan.modular.mine.emergencys.facerecognition.FaceRecognitionUtils;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.TiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.cz.PionCZRecordActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.tx.PionTiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.recovercash.activity.RecoverCashNewActivity;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountAfterBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountInfoDataBean;
import com.longcheng.lifecareplan.modular.mine.recovercash.bean.AcountItemBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 提现---匹配
 */
public class PionRecoverMatchActivity extends BaseActivityMVP<PionRecoverCashContract.View, PionRecoverCashPresenterImp<PionRecoverCashContract.View>> implements PionRecoverCashContract.View {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.item_tv_cancel)
    TextView itemTvCancel;
    @BindView(R.id.tv_poeplenum)
    TextView tv_poeplenum;
    @BindView(R.id.horizontal_layout)
    LinearLayout horizontalLayout;
    @BindView(R.id.item_iv_thumb)
    ImageView itemIvThumb;
    @BindView(R.id.item_tv_name)
    TextView itemTvName;
    @BindView(R.id.item_tv_jieeqi)
    TextView itemTvJieeqi;
    @BindView(R.id.item_tv_phone)
    TextView itemTvPhone;
    @BindView(R.id.item_layout_shenfen)
    LinearLayout itemLayoutShenfen;
    @BindView(R.id.item_tv_money)
    TextView itemTvMoney;
    @BindView(R.id.item_tv_time)
    TextView itemTvTime;
    @BindView(R.id.iv_gif)
    GifImageView iv_gif;
    @BindView(R.id.horizontal)
    HorizontalScrollView horizontal;

    @BindView(R.id.tv_ranktitlte)
    TextView tv_ranktitlte;
    @BindView(R.id.tv_rank)
    TextView tv_rank;
    @BindView(R.id.vp)
    ViewFlipper vp;

    String user_zhufubao_order_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                back();
                break;
            case R.id.item_tv_cancel:
                showCDialog();
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
        return R.layout.pioneer_recovermatch;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("订单匹配");
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetopLayoutLeft.setOnClickListener(this);
        itemTvCancel.setOnClickListener(this);
        mTimeTaskScroll = new TimeTaskScroll(this, horizontal);
        tv_ranktitlte.setTextColor(Color.argb(155, 255, 255, 255));  //文字透明度
        tv_rank.setTextColor(Color.argb(155, 255, 255, 255));  //文字透明度
    }


    @Override
    public void initDataAfter() {
        GifDrawable gifDrawable = (GifDrawable) iv_gif.getDrawable();
        gifDrawable.setLoopCount(0); // 设置无限循环播放
    }

    MyDialog mPayTypeDialog;


    public void showCDialog() {
        if (mPayTypeDialog == null) {
            mPayTypeDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_pion_ordertishi);// 创建Dialog并设置样式主题
            mPayTypeDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mPayTypeDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mPayTypeDialog.show();
            WindowManager m = getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mPayTypeDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mPayTypeDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_ok = mPayTypeDialog.findViewById(R.id.tv_ok);
            TextView tv_tishi = mPayTypeDialog.findViewById(R.id.tv_tishi);
            tv_tishi.setText("正在为您匹配\n取消后将重新排队匹配");
            tv_ok.setText("取消");
            LinearLayout layout_sure = mPayTypeDialog.findViewById(R.id.layout_sure);
            layout_sure.setVisibility(View.VISIBLE);
            tv_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                }
            });
            layout_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPayTypeDialog.dismiss();
                    mPresent.cancelPiPei(user_zhufubao_order_id);
                }
            });
        } else {
            mPayTypeDialog.show();
        }
    }

    TimeTaskScroll mTimeTaskScroll;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeTaskScroll != null) {
            mTimeTaskScroll.cancel();
            mHandler.removeCallbacks(mTimeTaskScroll);
        }
    }

    private Handler mHandler = new Handler();

    public class TimeTaskScroll extends TimerTask {
        private HorizontalScrollView listView;

        public TimeTaskScroll(Context context, HorizontalScrollView listView) {
            this.listView = listView;
        }

        int scrollPos;
        private Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                //  控制速度
                Log.e("getwalletSuccess", "Message-----");
                if (scrollPos > listView.getScrollX() && (scrollPos != 1)) {
                    listView.smoothScrollTo(0, 0);
                    scrollPos = 0;
                } else {
                    scrollPos = (int) (listView.getScrollX() + 2.0);
                    listView.smoothScrollTo(scrollPos, 0);
                }
            }
        };

        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            handler.sendMessage(msg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (vp != null && !vp.isFlipping()) {
            vp.startFlipping();
        }
        refreshData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (vp != null && vp.isFlipping()) {
            vp.stopFlipping();
        }
    }

    private void refreshData() {
        mPresent.getwalletOrderInfo(UserUtils.getUserId(mContext));
    }

    @Override
    protected PionRecoverCashPresenterImp<PionRecoverCashContract.View> createPresent() {
        return new PionRecoverCashPresenterImp<>(mContext, this);
    }

    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void getCardInfoSuccess(CertifyBean responseBean) {

    }

    @Override
    public void getwalletSuccess(AcountInfoDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            AcountAfterBean mEnergyAfterBean = responseBean.getData();
            String desc = "敬售成功<font color=\"#e60c0c\">" + mEnergyAfterBean.getCashNum() + "</font>人";
            tv_poeplenum.setText("" + Html.fromHtml(desc));
            tv_rank.setText("" + mEnergyAfterBean.getRanking());
            showInfo(mEnergyAfterBean);
            Match(mEnergyAfterBean);
        }
    }

    private void showInfo(AcountAfterBean mEnergyAfterBean) {
        AcountItemBean userMatchInfo = mEnergyAfterBean.getUserMatchInfo();
        user_zhufubao_order_id = userMatchInfo.getUser_zhufubao_order_id();
        itemTvName.setText(CommonUtil.setName(userMatchInfo.getCurrent_user_name()));
        itemTvJieeqi.setText(userMatchInfo.getCurrent_user_jieqi_name());
        itemTvPhone.setText(userMatchInfo.getCurrent_user_phone());
        itemTvTime.setText(userMatchInfo.getCreate_time());
        itemTvMoney.setText(userMatchInfo.getPrice());
        GlideDownLoadImage.getInstance().loadCircleImage(userMatchInfo.getCurrent_user_avatar(), itemIvThumb);
        itemLayoutShenfen.removeAllViews();
        ArrayList<String> user_identity_imgs = userMatchInfo.getCurrent_user_identity_images();
        if (user_identity_imgs != null && user_identity_imgs.size() > 0) {
            for (String url : user_identity_imgs) {
                LinearLayout linearLayout = new LinearLayout(mActivity);
                ImageView imageView = new ImageView(mActivity);
                int dit = DensityUtil.dip2px(mActivity, 16);
                int jian = DensityUtil.dip2px(mActivity, 3);
                linearLayout.setPadding(0, 2, jian, 2);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, url, imageView);
                linearLayout.addView(imageView);
                itemLayoutShenfen.addView(linearLayout);
            }
        }
    }

    private void Match(AcountAfterBean mEnergyAfterBean) {
        ArrayList<EnergyItemBean> cashInfo = mEnergyAfterBean.getCashInfo();
        horizontalLayout.removeAllViews();
        if (cashInfo != null && cashInfo.size() > 0) {
            for (EnergyItemBean mEnergyItemBean : cashInfo) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.pioneer_recovermatch_item, null);
                ImageView iv_avatar = view.findViewById(R.id.iv_avatar);
                TextView tv_name = view.findViewById(R.id.tv_name);
                TextView item_tv_price = view.findViewById(R.id.item_tv_price);
                tv_name.setText(mEnergyItemBean.getUser_name());
                item_tv_price.setText(mEnergyItemBean.getPrice());
                GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, mEnergyItemBean.getAvatar(), iv_avatar);
                horizontalLayout.addView(view);
            }
            //设定任务
            new Timer().schedule(mTimeTaskScroll, 0, 50);

            fillView(cashInfo);

        }
    }

    private void fillView(List<EnergyItemBean> topmsg) {
        vp.removeAllViews();
        for (int i = 0; i < topmsg.size(); i++) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.text_item3, null);
            TextView textView = view.findViewById(R.id.tv_cont);
            TextView tv_cont2 = view.findViewById(R.id.tv_cont2);
            ImageView iv_head = view.findViewById(R.id.iv_head);
            GlideDownLoadImage.getInstance().loadCircleImage(topmsg.get(i).getAvatar(), iv_head);
            textView.setTextColor(mActivity.getResources().getColor(R.color.white));
            tv_cont2.setTextColor(mActivity.getResources().getColor(R.color.white));
            textView.setText(topmsg.get(i).getUser_name() + "成功敬售" + topmsg.get(i).getPrice() + "福祺宝");
            tv_cont2.setText("刚刚");
            vp.addView(view);
        }
        //是否自动开始滚动
        vp.setAutoStart(true);
        //滚动时间
        vp.setFlipInterval(2600);
        //开始滚动
        vp.startFlipping();
        //出入动画
        vp.setOutAnimation(mActivity, R.anim.push_bottom_outvp);
        vp.setInAnimation(mActivity, R.anim.push_bottom_in);
    }

    @Override
    public void cancelPiPeiSuccess(ResponseBean responseBean) {
        if (responseBean.getStatus().equals("200")) {
            PionTiXianRecordActivity.editOrderStatus = true;
            back();
            ToastUtils.showToast(responseBean.getMsg());
        } else {
            ToastUtils.showToast(responseBean.getMsg());
        }
    }


    @Override
    public void ListError() {
    }

    private void back() {
        Intent intent = new Intent(mActivity, PionTiXianRecordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
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
