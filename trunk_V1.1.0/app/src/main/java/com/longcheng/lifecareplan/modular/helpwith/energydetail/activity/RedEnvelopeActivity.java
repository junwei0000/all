package com.longcheng.lifecareplan.modular.helpwith.energydetail.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.SkipHelpUtils;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.OpenRedDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.PayAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.PayDataBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.PayItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.RedEvelopeContract;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.bean.RedEvelopeImp;
import com.longcheng.lifecareplan.modular.mine.phosphor.PhosphorAct;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.wxapi.WXPayEntryActivity;

import butterknife.BindView;

/**
 * 红包
 */
public class RedEnvelopeActivity extends BaseActivityMVP<RedEvelopeContract.View, RedEvelopeImp<RedEvelopeContract.View>> implements RedEvelopeContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_cancel)
    LinearLayout layoutCancel;
    @BindView(R.id.iv_spokesman)
    ImageView ivSpokesMan;
    @BindView(R.id.tv_partner_name)
    TextView tvName;
    @BindView(R.id.tv_partner)
    TextView tvCont;
    //打开红包前的红包内容
    @BindView(R.id.iv_redtop)
    ImageView ivRedtop_BeforeOpen;
    //    @BindView(R.id.iv_redbottom)
//    ImageView ivRedbottom;
    //打开红包后的红包内容
    @BindView(R.id.frl_get_redenvelope_detail)
    FrameLayout frl_redevelopeDetail;
    @BindView(R.id.tv_red_detail)
    TextView tvRedDetail;
    @BindView(R.id.tv_redtype)
    TextView tvRedtype;
    @BindView(R.id.iv_represent)
    ImageView iv_represent;
    //底部按钮
    @BindView(R.id.btn_backlist)
    TextView btnBacklist;
    @BindView(R.id.btn_proceed)
    TextView btnProceed;

    @BindView(R.id.llredenvelope)
    LinearLayout llRedEnvelope_beforeOpen;
    @BindView(R.id.frl_clicktopen)
    FrameLayout frl_clikToOpen;

    private String backToPrePage;
    private String isDisplayCho = "-1";//1 展示成为cho按钮 ， 0 则展示继续互助他

    boolean startRed = false;
    private String one_order_id, user_id;


    /**
     * 标记是否在请求读取红包数据，
     * 这里可以取消这个标记，在重新定义imp，在onError里面设置回调
     */
    private boolean requestResEnvelope = false;

    /**
     * type = 1 和 2时，现在H5是用 URL参数来跳转到对应的地址。
     * <p>
     * APP会有所不同。 行动商品ID  用于 type =3时 点击广告图片跳转到行动申请页面时使用，H5是直接用URL跳转的。
     */
    private int skiptype;
    private String skipurl;
    private int action_goods_id;
    private String qiming_user_id;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_cancel:
                finish();
                ConfigUtils.getINSTANCE().setPageLoginBackAnim(mActivity);
                break;
            case R.id.btn_backlist:
                back();
                break;
            case R.id.btn_proceed:
                if (!TextUtils.isEmpty(isDisplayCho) && isDisplayCho.equals("0")) {
                    /**
                     * 祝福完成后刷新列表，防止微信回调不刷新
                     */
                    Intent intent = new Intent();
                    intent.setAction(ConstantManager.BroadcastReceiver_ENGRYLIST_ACTION);
                    intent.putExtra("errCode", WXPayEntryActivity.PAYDETAIL_SHUAXIN);
                    sendBroadcast(intent);//发送普通广播
                    finish();
                    ConfigUtils.getINSTANCE().setPageLoginBackAnim(mActivity);
                } else if (!TextUtils.isEmpty(isDisplayCho) && isDisplayCho.equals("1")) {
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.llredenvelope:
                if (!startRed) {
                    stopShake();
                    requestResEnvelope = true;
                    mPresent.openRedEnvelope(user_id, one_order_id);
                }
                break;
            case R.id.iv_represent:
                Intent intent;
                if (skiptype == 3) {
                    SkipHelpUtils.getInstance().skipIntent(mActivity, action_goods_id);
                } else {
                    //跳转h5 ,返回互祝列表 skipurl
                    intent = new Intent(mContext, PhosphorAct.class);
                    intent.putExtra("starturl", skipurl);
                    intent.putExtra("action_goods_id", "" + action_goods_id);
                    intent.putExtra("qiming_user_id", "" + qiming_user_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                finish();
                ConfigUtils.getINSTANCE().setPageLoginBackAnim(mActivity);
                break;

        }
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.dialog_redenvelope2;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null, this.getResources().getColor(R.color.hongbaoget_bg));
    }

    @Override
    public void setListener() {
        layoutCancel.setOnClickListener(this);
        btnBacklist.setOnClickListener(this);
        btnProceed.setOnClickListener(this);
        llRedEnvelope_beforeOpen.setOnClickListener(this);
        ivSpokesMan.setOnClickListener(this);
        iv_represent.setOnClickListener(this);
    }

    @Override
    public void initDataAfter() {
        Intent intent = getIntent();
        one_order_id = intent.getStringExtra("one_order_id");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        mPresent.getRedEnvelopeData(user_id, one_order_id);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showRedEvelope();
            }
        }, 500);
    }

    @Override
    protected RedEvelopeImp<RedEvelopeContract.View> createPresent() {
        return new RedEvelopeImp<>(mContext, this);
    }

    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    /**
     * 拆红包后展示数据和动画
     */
    private void openRedEvelope(OpenRedAfterBean data) {
        startRed = true;
        int type = data.getType();
        tvRedDetail.setText(data.getRed_packet_money());
        //红包类型 1:money, 2:skb,
        if (type == 1) {
            tvRedtype.setText("现金");
        } else {
            tvRedtype.setText("寿康宝");
        }
        startOpenAnimation();
        showRedEvelopeDetail();
    }

    /**
     * 展示红包的动画（晃动）
     */
    private void showRedEvelope() {
        if (llRedEnvelope_beforeOpen != null) {
            llRedEnvelope_beforeOpen.setVisibility(View.VISIBLE);
            frl_redevelopeDetail.setVisibility(View.INVISIBLE);
            RotateAnimation ra = new RotateAnimation(6f, -6f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            ra.setDuration(200);
            ra.setRepeatCount(-1);
            llRedEnvelope_beforeOpen.clearAnimation();
            ra.setRepeatMode(Animation.REVERSE);
            llRedEnvelope_beforeOpen.startAnimation(ra);
        }
    }

    /**
     * 停止红包展示动画
     */
    private void stopShake() {
        llRedEnvelope_beforeOpen.clearAnimation();
    }

    /**
     * 打开红包的过渡动画
     */
    private void startOpenAnimation() {
        int duration = 500;
        llRedEnvelope_beforeOpen.clearAnimation();
        ScaleAnimation sa = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);

        TranslateAnimation translateAnimationTop = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.1f);

        AnimationSet asTop = new AnimationSet(true);
        asTop.addAnimation(translateAnimationTop);
        asTop.addAnimation(aa);
        asTop.addAnimation(sa);
        asTop.setDuration(duration);

        ivRedtop_BeforeOpen.clearAnimation();
        ivRedtop_BeforeOpen.startAnimation(asTop);

        TranslateAnimation translateAnimationButtom = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0.05f);

        AnimationSet asButtom = new AnimationSet(true);
        asButtom.addAnimation(translateAnimationButtom);
        asButtom.addAnimation(aa);
        asButtom.addAnimation(sa);
        asButtom.setDuration(duration);
        asButtom.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                llRedEnvelope_beforeOpen.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        frl_clikToOpen.clearAnimation();
        frl_clikToOpen.startAnimation(asButtom);
    }

    private void showRedEvelopeDetail() {
        frl_redevelopeDetail.setVisibility(View.VISIBLE);
        ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);

        AnimationSet asTop = new AnimationSet(true);
        asTop.setDuration(500);
        asTop.addAnimation(aa);
        asTop.addAnimation(sa);
        frl_redevelopeDetail.clearAnimation();
        frl_redevelopeDetail.startAnimation(asTop);
    }

    @Override
    public void getRedEnvelopeSuccess(PayDataBean responseBean) {
        PayAfterBean mPayAfterBean = (PayAfterBean) responseBean.getData();
        //显示 数据
        if (mPayAfterBean != null) {
            isDisplayCho = mPayAfterBean.getIsDisplayCho();
            backToPrePage = mPayAfterBean.getBackToPrePage();
            PayItemBean endorsement_star = mPayAfterBean.getEndorsement_star();
            if (endorsement_star != null) {
                skiptype = endorsement_star.getType();
                skipurl = endorsement_star.getUrl();
                qiming_user_id = endorsement_star.getQiming_user_id();
                action_goods_id = endorsement_star.getAction_goods_id();
                int width = frl_redevelopeDetail.getMeasuredWidth() - DensityUtil.dip2px(mContext, 40);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) iv_represent.getLayoutParams();
                lp.width = width;
                lp.height = DensityUtil.dip2px(mContext, 65);
                lp.bottomMargin = DensityUtil.dip2px(mContext, 20);
                iv_represent.setLayoutParams(lp);
                GlideDownLoadImage.getInstance().loadCircleImageRole(mContext, endorsement_star.getAdvertisement_photo_url(), iv_represent, ConstantManager.image_angle);
                GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, endorsement_star.getPhoto(), ivSpokesMan);
                tvName.setText(endorsement_star.getStart_name());
                tvCont.setText(endorsement_star.getSlogan());
            }
            if (!TextUtils.isEmpty(isDisplayCho) && isDisplayCho.equals("0")) {
                btnProceed.setText(R.string.help_again);
            } else {
                btnProceed.setText(R.string.becom_cho);
            }
            if (!TextUtils.isEmpty(backToPrePage) && backToPrePage.equals("1")) {
                btnBacklist.setText(R.string.back_to_help_list);
            } else if (!TextUtils.isEmpty(backToPrePage) && backToPrePage.equals("2")) {
                btnBacklist.setText(R.string.back_to_application);
            }
        }
    }

    @Override
    public void OpenRedEnvelopeSuccess(OpenRedDataBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
            showRedEvelope();
        } else if (status.equals("200")) {
            OpenRedAfterBean mPayAfterBean = (OpenRedAfterBean) responseBean.getData();
            //显示 数据
            if (mPayAfterBean != null) {
                openRedEvelope(responseBean.getData());
            } else {
                showRedEvelope();
            }
        } else {
            showRedEvelope();
        }
    }

    @Override
    public void onGetRedEnvelopeError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onOpenRedEnvelopeError(String msg) {
        ToastUtils.showToast(msg);
        showRedEvelope();
    }

    /**
     * 返回上一页按钮 默认 1 返回互助列表,2 返回互助申请页面
     */
    private void back() {
        if (!TextUtils.isEmpty(backToPrePage) && backToPrePage.equals("1")) {
            Intent intent = new Intent(mActivity, HelpWithEnergyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("skiptype", "redbao");
            startActivity(intent);
        } else if (!TextUtils.isEmpty(backToPrePage) && backToPrePage.equals("2")) {
            Intent intent = new Intent(mActivity, ApplyHelpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        finish();
        ConfigUtils.getINSTANCE().setPageLoginBackAnim(mActivity);
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
