package com.longcheng.lifecareplan.modular.mine.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.activity.PerfectInfoDialog;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.FuBaoMenuNewActivity;
import com.longcheng.lifecareplan.modular.mine.absolutelyclear.activity.AbsolutelyclearAct;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.lifecareplan.modular.mine.bill.activity.EngryRecordActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.SleepEngryActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.SleepSkbActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.WakeSkbActivity;
import com.longcheng.lifecareplan.modular.mine.cashflow.activity.CashFlowActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.activity.DocListActivity;
import com.longcheng.lifecareplan.modular.mine.fragment.genius.FunctionAdapter;
import com.longcheng.lifecareplan.modular.mine.fragment.genius.FunctionGVItemBean;
import com.longcheng.lifecareplan.modular.mine.goodluck.activity.GoodLuckActivity;
import com.longcheng.lifecareplan.modular.mine.invitation.activity.InvitationAct;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.activity.PioneerMenuActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.activity.PioneerOpenActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.activity.PioneerOpenZoreActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.exittutor.ExitutorActionActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.financialmanage.activity.FinanManageListActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.activity.PionOpenSetActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.activity.PionZFBActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.jieqibao.activity.PionUserJQBActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.activity.PionRecoverCashNewActivity;
import com.longcheng.lifecareplan.modular.mine.record.activity.IncreaseRecordActivity;
import com.longcheng.lifecareplan.modular.mine.record.fargment.BaseRecordFrag;
import com.longcheng.lifecareplan.modular.mine.relationship.activity.RelationshipAccountAct;
import com.longcheng.lifecareplan.modular.mine.rewardcenters.activity.RewardCentersActivity;
import com.longcheng.lifecareplan.modular.mine.set.activity.SetActivity;
import com.longcheng.lifecareplan.modular.mine.signIn.activity.SignInH5Activity;
import com.longcheng.lifecareplan.modular.mine.starinstruction.StarInstructionAct;
import com.longcheng.lifecareplan.modular.mine.treasurebowl.activity.CornucopiaPageActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.lifecareplan.push.jpush.message.PionPairingUtils;
import com.longcheng.lifecareplan.push.jpush.message.ReceiveFubaoUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.ImmersionBarUtils;
import com.longcheng.lifecareplan.widget.Immersive;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * @author MarkShuai
 * @name 我的页面
 * @time 2017/11/23 17:24
 */
public class MineFragment extends BaseFragmentMVP<MineContract.View, MinePresenterImp<MineContract.View>> implements MineContract.View {
    @BindView(R.id.mycenter_iv_head)
    CircleImageView mycenterIvHead;
    @BindView(R.id.mycenter_tv_name)
    TextView mycenterTvName;
    @BindView(R.id.mycenter_tv_jieeqi)
    TextView mycenter_tv_jieeqi;
    @BindView(R.id.mycenter_tv_stars)
    TextView mycenterTvStars;
    @BindView(R.id.mycenter_tv_starsTiShi)
    TextView mycenter_tv_starsTiShi;

    @BindView(R.id.mycenter_iv_stars)
    ImageView mycenter_iv_stars;
    @BindView(R.id.mycenter_iv_starsarrow)
    ImageView mycenterIvStarsarrow;
    @BindView(R.id.mycenter_relat_stars)
    LinearLayout mycenterRelatStars;
    @BindView(R.id.mycenter_relat_shenfen)
    LinearLayout mycenter_relat_shenfen;
    @BindView(R.id.mycenter_layout_humanity)
    LinearLayout mycenterLayoutHumanity;
    @BindView(R.id.mycentertop_tv_awakeskb)
    TextView mycentertopTvAwakeskb;
    @BindView(R.id.mycentertop_tv_sleepskb)
    TextView mycentertopTvSleepskb;
    @BindView(R.id.ll_mycenter_wakeskb)
    LinearLayout ll_mycenter_wakeskb;
    @BindView(R.id.ll_mycenter_sleepskb)
    LinearLayout ll_mycenter_sleepskb;
    @BindView(R.id.ll_mycenter_ennergy)
    LinearLayout llEnergy;//激活的生命能量
    @BindView(R.id.mycentertop_tv_lifeenergy)
    TextView mycentertopTvLifeenergy;
    //
    @BindView(R.id.mycenter_layout_activatenergy)
    LinearLayout mycenterLayoutActivatenergy;
    //
    @BindView(R.id.mycenter_layout_myka)
    LinearLayout mycenter_layout_myka;
    //
    @BindView(R.id.usercenter_relay_zodiacdescripte)
    LinearLayout usercenterRelayzodiacdescripte;

    @BindView(R.id.home_sv)
    MyScrollView homeSv;
    @BindView(R.id.mycentertop_tv_sleeplifeenergy)
    TextView mycentertopTvSleeplifeenergy;
    @BindView(R.id.ll_mycenter_sleepennergy)
    LinearLayout llMycenterSleepennergy;


    @BindView(R.id.gongnengn_gv1)
    MyGridView gongnengn_gv1;
    @BindView(R.id.mycenter_iv_jieqi)
    ImageView mycenter_iv_jieqi;

    @BindView(R.id.mycenter_layout_loveVideo)
    LinearLayout mycenter_layout_loveVideo;

    @BindView(R.id.tv_jintiebill)
    TextView tv_jintiebill;
    @BindView(R.id.mycenter_tv_jintie)
    TextView mycenter_tv_jintie;

    @BindView(R.id.tv_myenergycenter)
    TextView tv_myenergycenter;
    @BindView(R.id.mycenter_layout_pioneercenter)
    LinearLayout mycenter_layout_pioneercenter;
    @BindView(R.id.mycenter_layout_moneyjieqibao)
    LinearLayout mycenter_layout_moneyjieqibao;
    @BindView(R.id.mycenter_tv_moneyjieqibao)
    TextView mycenterTvMoneyjieqibao;
    @BindView(R.id.mycenter_layout_moneyfqb)
    LinearLayout mycenter_layout_moneyfqb;
    @BindView(R.id.mycenter_tv_moneyfqb)
    TextView mycenter_tv_moneyfqb;
    @BindView(R.id.mycenter_layout_moneyzfb)
    LinearLayout mycenter_layout_moneyzfb;
    @BindView(R.id.mycenter_tv_moneyzfb)
    TextView mycenter_tv_moneyzfb;
    @BindView(R.id.mycenter_iv_set)
    ImageView mycenter_iv_set;
    @BindView(R.id.iv_meiqia)
    ImageView iv_meiqia;
    @BindView(R.id.layout_center_bg)
    LinearLayout layout_center_bg;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_input)
    FrameLayout layoutInput;
    @BindView(R.id.iv_gif)
    GifImageView ivGif;
    @BindView(R.id.tv_treasurenum)
    TextView tv_treasurenum;


    /**
     * 是否有未拆的红包 0：没有 1：有 需要谈层
     */
    private int isUnopenedRedPackage;
    /**
     * 是否有升级弹窗  用户首页是否有等级弹窗 0不显示；1显示
     */
    int chatuserStarLevelId;
    private GetHomeInfoBean data = new GetHomeInfoBean();
    private String star_level_illustrate_url;
    private String is_cho;
    private String user_id;
    private String about_me_url;
    PerfectInfoDialog mPerfectInfoDialog;
    public static String Certification_url = "", ka_url = "";
    public static ArrayList<GetHomeInfoBean> user_identity_imgs;

    private int isopencor = 0; // 默认未开启
    private int cornum = 0;// 聚宝盆存储数量
    GifDrawable gifRiceDrawable;

    @Override
    public int bindLayout() {
        return R.layout.fragment_mycenter;
    }

    @Override
    public void initView(View view) {
        mycenter_layout_pioneercenter.setOnClickListener(this);
        tv_jintiebill.setOnClickListener(this);
        iv_meiqia.setOnClickListener(this);
        mycenter_iv_set.setOnClickListener(this);
        mycenterIvHead.setOnClickListener(this);
        mycenter_layout_myka.setOnClickListener(this);
        mycenterLayoutActivatenergy.setOnClickListener(this);
        mycenterLayoutHumanity.setOnClickListener(this);
        usercenterRelayzodiacdescripte.setOnClickListener(this);
        ll_mycenter_wakeskb.setOnClickListener(this);
        ll_mycenter_sleepskb.setOnClickListener(this);
        llMycenterSleepennergy.setOnClickListener(this);
        llEnergy.setOnClickListener(this);
        mycenterRelatStars.setOnClickListener(this);
        mycenter_relat_shenfen.setOnClickListener(this);
        mycenter_layout_loveVideo.setOnClickListener(this);
        mycenter_layout_moneyjieqibao.setOnClickListener(this);
        mycenter_layout_moneyfqb.setOnClickListener(this);
        mycenter_layout_moneyzfb.setOnClickListener(this);
        int wid = DensityUtil.screenWith(mContext);
        int width = (wid - DensityUtil.dip2px(mContext, 16)) / 3;
        mycenter_layout_moneyjieqibao.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * 1.165)));
        mycenter_layout_moneyfqb.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * 1.165)));
        mycenter_layout_moneyzfb.setLayoutParams(new LinearLayout.LayoutParams(width, (int) (width * 1.165)));
        layout_center_bg.setLayoutParams(new LinearLayout.LayoutParams(wid, (int) (wid * 0.65)));

        /// gerry add
        layoutInput.setOnClickListener(this);
        int gifhig = (wid * 178) / 750;
        ivGif.setLayoutParams(new FrameLayout.LayoutParams(wid, gifhig));
        gifRiceDrawable = (GifDrawable) ivGif.getDrawable();
        gifRiceDrawable.setLoopCount(0);
        gifRiceDrawable.reset();

        gongnengn_gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (FunctionGVlist1 != null && FunctionGVlist1.size() > 0) {
                    String viewId = FunctionGVlist1.get(position).getViewId();
                    GVItemClick(viewId);
                }
            }
        });
        mycenterTvName.setFocusable(true);
        mycenterTvName.setFocusableInTouchMode(true);
        mycenterTvName.requestFocus();
        initContext();
        showFunctionGVData();
        initUserInfo();
    }

    @Override
    public void doBusiness(Context mActivity) {

    }


    ArrayList<FunctionGVItemBean> FunctionGVlist1;

    /**
     * 初始化显示宫格常用功能
     */
    private void showFunctionGVData() {
        FunctionGVlist1 = new ArrayList();
        int is_company_flow_pool_user = data.getIs_company_flow_pool_user();
        if (is_company_flow_pool_user != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("流水", "layout_cashflow", R.mipmap.my_liushuin_icon));
        }
        int is_alipay_pool_user = data.getIs_alipay_pool_user();
        if (is_alipay_pool_user != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("水库", "layout_pool", R.mipmap.my_pool_icon));
        }
        int isShowZhufubaoRewardCustomer = data.getIsShowZhufubaoRewardCustomer();
        if (isShowZhufubaoRewardCustomer != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("财务管理", "mycenter_layout_financialmanage", R.mipmap.my_icon_caiwu));
        }
        int isShowEntrepreneursConfirmCustomer = data.getIsShowEntrepreneursConfirmCustomer();
        if (isShowEntrepreneursConfirmCustomer != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("创业名额", "layout_pionopenauthority", R.mipmap.my_order_icon));
        }
        int isexit = data.getIsSettlement();
        if (isexit != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("创业导师退出", "layout_exittutor", R.mipmap.my_order_exitotur_icon));
        }

        FunctionGVlist1.add(new FunctionGVItemBean("我的订单", "layout_orderlist", R.mipmap.my_order_icon));
        int isBlessedTeacher = data.getIsBlessedTeacher();
        if (isBlessedTeacher == 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("申请祝福师", "usercenter_relay_applybless", R.mipmap.my_zhufushi_icon));
        } else {
            FunctionGVlist1.add(new FunctionGVItemBean("我是祝福师", "usercenter_relay_applybless", R.mipmap.my_zhufushi_icon));
        }
        int isDoctorIdentity = data.getIsDoctorIdentity();//是否是坐堂医 0不是；1 是
        int isVolunteerIdentity = data.getIsVolunteerIdentity();//是否是志愿者 0不是；1 是
        String Voname = "";
        String Doname = "";
        if (isVolunteerIdentity == 0) {
            Voname = "申请志愿者";
        } else {
            Voname = "我是志愿者";
        }
        if (isDoctorIdentity == 0) {
            Doname = "申请坐堂医";
        } else {
            Doname = "我是坐堂医";
        }
        FunctionGVlist1.add(new FunctionGVItemBean(Voname, "usercenter_relay_volunteer", R.mipmap.my_volunteersr_icon));
        FunctionGVlist1.add(new FunctionGVItemBean(Doname, "usercenter_relay_doctor", R.mipmap.my_doctor_icon));
        int is_patient = data.getIs_patient();
        if (is_patient != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("我是爱友", "usercenter_relay_aiyou", R.mipmap.my_icon_aiyou));
        }
        FunctionGVlist1.add(new FunctionGVItemBean("实名认证", "layout_auth", R.mipmap.my_auth_icon));
        FunctionGVlist1.add(new FunctionGVItemBean("奖励中心", "mycenter_layout_reward", R.mipmap.homepage_icon_jianglizhongxin));
        int hasDiagnosticRecord = data.getHasDiagnosticRecord();
        if (hasDiagnosticRecord != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("就诊记录", "layout_jiuzhen", R.mipmap.my_treatment_icon));
        }
        int isVolunteerCreditor = data.getIsVolunteerCreditor();
        if (isVolunteerCreditor != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("债权人", "layout_creditor", R.mipmap.my_debt_icon));
        }
        FunctionGVlist1.add(new FunctionGVItemBean("好运来", "usercenter_relay_goodluck", R.mipmap.usercenter_goodluck_icon));
        FunctionGVlist1.add(new FunctionGVItemBean("邀请记录", "mycenter_layout_invitationrecord", R.mipmap.homepage_icon_invitation));
        FunctionGVlist1.add(new FunctionGVItemBean("一目了然", "usercenter_relay_appexplanation", R.mipmap.usercenter_appexplanation_icon));
        FunctionAdapter mFunctionAdapter1 = new FunctionAdapter(mActivity, FunctionGVlist1);
        gongnengn_gv1.setAdapter(mFunctionAdapter1);
    }

    private void GVItemClick(String viewId) {
        Intent intent;
        switch (viewId) {
            case "layout_cashflow"://流水
                intent = new Intent(mActivity, CashFlowActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "layout_pool"://水库
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getAlipay_pool_user_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "mycenter_layout_financialmanage"://财务管理
                intent = new Intent(mActivity, FinanManageListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "layout_pionopenauthority":// 创业名额
                intent = new Intent(mActivity, PionOpenSetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "layout_exittutor":// 创业导师退出
                intent = new Intent(mActivity, ExitutorActionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "layout_orderlist"://我的订单
                intent = new Intent(mActivity, OrderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "usercenter_relay_applybless"://申请祝福师
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getZhufu_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "usercenter_relay_volunteer"://志愿者
                int isVolunteerIdentity = data.getIsVolunteerIdentity();//是否是志愿者 0不是；1 是
                if (isVolunteerIdentity == 0) {
                    String is_cho = (String) SharedPreferencesHelper.get(mActivity, "is_cho", "");
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        intent = new Intent(mActivity, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getBecome_volunteer_url());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    } else {
                        showNotCHODialog();
                    }
                } else {
                    intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + data.getAlready_volunteer_url());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case "usercenter_relay_doctor"://坐堂医
                int isDoctorIdentity = data.getIsDoctorIdentity();//是否是坐堂医 0不是；1 是
                if (isDoctorIdentity == 0) {
                    String is_cho = (String) SharedPreferencesHelper.get(mActivity, "is_cho", "");
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        intent = new Intent(mActivity, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getBecome_doctor_url());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    } else {
                        showNotCHODialog();
                    }
                } else {
                    intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + data.getAlready_doctor_url());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case "usercenter_relay_aiyou"://我是爱友
                intent = new Intent(mActivity, DocListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "layout_auth"://实名认证
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + Certification_url);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "mycenter_layout_reward"://奖励中心
                intent = new Intent(mActivity, RewardCentersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "layout_jiuzhen"://就诊记录
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getPatient_record_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "layout_creditor"://债权人
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getVolunteerCreditorUrl());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "usercenter_relay_goodluck"://好运来
                intent = new Intent(mActivity, GoodLuckActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "mycenter_layout_invitationrecord"://邀请记录
                intent = new Intent(mActivity, InvitationAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case "usercenter_relay_appexplanation"://一目了然
                intent = new Intent(mActivity, AbsolutelyclearAct.class);
//                intent = new Intent(mActivity, IncreaseRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            default:
                break;
        }
    }

    /**
     * @param v
     */
    @Override
    public void widgetClick(View v) {
        int viewId = v.getId();
        viewClick(viewId);
    }


    private void viewClick(int viewId) {
        Intent intent;
        switch (viewId) {
            case R.id.iv_meiqia:
                conversationWrapper();
                break;
            case R.id.mycenter_layout_moneyjieqibao://节气宝
                intent = new Intent(mActivity, PionUserJQBActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_moneyfqb://福祺宝
                intent = new Intent(mActivity, PionRecoverCashNewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_moneyzfb://祝福宝
                intent = new Intent(mActivity, PionZFBActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_pioneercenter://创业中心
                //是否创业者 1；是 0：否
                int isShowEntrepreneurs = data.getIsShowEntrepreneurs();
                if (isShowEntrepreneurs == 1) {
                    intent = new Intent(mActivity, PioneerMenuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else {
                    intent = new Intent(mActivity, PioneerOpenZoreActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.mycenter_iv_set://设置
                intent = new Intent(mActivity, SetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("about_me_url", "" + about_me_url);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_myka://我的卡包
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + ka_url);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_loveVideo://爱心奉献
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getLoveVideo_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.tv_jintiebill://节日津贴
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getHoliday_asset_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_iv_head://用户信息
                intent = new Intent(mActivity, UserInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;

            case R.id.mycenter_layout_humanity://人情账
                intent = new Intent(mActivity, RelationshipAccountAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_activatenergy://激活能量
                intent = new Intent(mActivity, ActivatEnergyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_zodiacdescripte://一字千金
                signInGetInfo();
                break;
            case R.id.ll_mycenter_sleepskb:
                intent = new Intent(mActivity, SleepSkbActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.ll_mycenter_wakeskb:
                // Gerry
                intent = new Intent(mActivity, WakeSkbActivity.class);
//                intent = new Intent(mActivity, CornucopiaPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.ll_mycenter_sleepennergy:
                intent = new Intent(mActivity, SleepEngryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.ll_mycenter_ennergy://激活记录
                intent = new Intent(mActivity, EngryRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_relat_shenfen://星级布局
                if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                    if (!TextUtils.isEmpty(star_level_illustrate_url)) {
                        intent = new Intent(mActivity, StarInstructionAct.class);
                        intent.putExtra("starturl", "" + star_level_illustrate_url);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                }
                break;
            case R.id.mycenter_relat_stars://成为cho
                if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                } else {
                    intent = new Intent(mActivity, UserInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;

            case R.id.layout_input:
                intent = new Intent(mActivity, CornucopiaPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("opened", isopencor);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());

                break;
            default:
                break;
        }
    }

    /**
     * 获取用户签到信息
     */
    public void signInGetInfo() {
        user_id = (String) SharedPreferencesHelper.get(mActivity, "user_id", "");
        if (mPresent != null)
            mPresent.checkUserInfo(user_id);
    }

    @Override
    public void checkUserInfoSuccess(EditDataBean responseBean) {
        String status = responseBean.getStatus();
        boolean oldUserPerfectInfoStatus = true;
        if (status.equals("400")) {
            oldUserPerfectInfoStatus = false;
        } else if (status.equals("200")) {
            oldUserPerfectInfoStatus = true;
        }
        AWordOfGoldStatus(oldUserPerfectInfoStatus);
    }

    private void AWordOfGoldStatus(boolean oldUserPerfectInfoStatus) {
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            if (oldUserPerfectInfoStatus) {
                Intent intent = new Intent(mActivity, AWordOfGoldAct.class);
                intent.putExtra("otherId", UserUtils.getUserId(mActivity));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
            } else {
                if (mPerfectInfoDialog == null) {
                    mPerfectInfoDialog = new PerfectInfoDialog(getActivity(), mHandler, SkipEDIT);
                }
                mPerfectInfoDialog.showEditDialog("请完善信息后查看一字千金哦~", "完善资料");
            }
        } else {
            if (mPerfectInfoDialog == null) {
                mPerfectInfoDialog = new PerfectInfoDialog(getActivity(), mHandler, SkipEDIT);
            }
            mPerfectInfoDialog.showEditDialog("成为CHO后查看一字千金哦~", "完善资料");
        }
    }

    private final int SkipEDIT = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SkipEDIT:
                    Intent intent = new Intent(mActivity, UserInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    break;
            }
        }
    };

    @Override
    protected MinePresenterImp<MineContract.View> createPresent() {
        return new MinePresenterImp<>(this);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void dismissDialog() {

    }

    boolean firstConIn = true;

    @Override
    public void onResume() {
        super.onResume();
        Log.e("initUserInfo", "onResume   firstConIn=" + firstConIn);
        if (!firstConIn) {
            initUserInfo();
        }
    }

    public void initUserInfo() {
        showInfoView();
        if (!TextUtils.isEmpty(user_id) && mPresent != null) {
            mPresent.getUserHomeInfo(user_id);
        }
        Log.e("initUserInfo", "user_id=" + user_id + " \nis_cho= " + is_cho);
    }

    private void showInfoView() {
        if (mycenterTvName == null) {
            return;
        }
        initContext();
        String avatar = (String) SharedPreferencesHelper.get(mActivity, "avatar", "");
        is_cho = (String) SharedPreferencesHelper.get(mActivity, "is_cho", "");
        String phone = (String) SharedPreferencesHelper.get(mActivity, "phone", "");
        user_id = (String) SharedPreferencesHelper.get(mActivity, "user_id", "");
        String user_name = (String) SharedPreferencesHelper.get(mActivity, "user_name", "");
        //1:是  ；0：不是
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            mycenterRelatStars.setVisibility(View.GONE);
            mycenter_relat_shenfen.setVisibility(View.VISIBLE);
        } else {
            mycenterTvStars.setText(getString(R.string.becomeCHO));
            mycenter_tv_starsTiShi.setText(getString(R.string.becomeCHO_tishi));
            mycenter_iv_stars.setVisibility(View.GONE);
            mycenterRelatStars.setVisibility(View.VISIBLE);
            mycenter_relat_shenfen.setVisibility(View.GONE);
        }
        mycenterTvName.setText(user_name);
        GlideDownLoadImage.getInstance().loadCircleHeadImageCenter(mActivity, avatar, mycenterIvHead);
        GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mActivity, data.getJieqi_pic(), mycenter_iv_jieqi);
        showFunctionGVData();
    }

    private void saveNewInfo(GetHomeInfoBean mGetHomeInfoBean) {
        SharedPreferencesHelper.put(mActivity, "real_name", mGetHomeInfoBean.getReal_name());
        SharedPreferencesHelper.put(mActivity, "user_name", mGetHomeInfoBean.getUser_name());
        SharedPreferencesHelper.put(mActivity, "avatar", mGetHomeInfoBean.getAvatar());
        SharedPreferencesHelper.put(mActivity, "star_level", "" + CurrentStartLevel);
        SharedPreferencesHelper.put(mActivity, "is_cho", is_cho);
        SharedPreferencesHelper.put(mActivity, "birthday", mGetHomeInfoBean.getBirthday());
        SharedPreferencesHelper.put(mActivity, "lunar_calendar_birthday", "" + mGetHomeInfoBean.getLunar_calendar_birthday());
        SharedPreferencesHelper.put(mActivity, "political_status", "" + mGetHomeInfoBean.getPolitical_status());
        SharedPreferencesHelper.put(mActivity, "is_military_service", "" + mGetHomeInfoBean.getIs_military_service());
        SharedPreferencesHelper.put(mActivity, "pid", "" + mGetHomeInfoBean.getPid());
        SharedPreferencesHelper.put(mActivity, "cid", "" + mGetHomeInfoBean.getCid());
        SharedPreferencesHelper.put(mActivity, "aid", "" + mGetHomeInfoBean.getAid());
        SharedPreferencesHelper.put(mActivity, "area", "" + mGetHomeInfoBean.getArea());

        String ability = mGetHomeInfoBean.getDisparity();
        String is_show = mGetHomeInfoBean.getIs_show();//ability生命能量 number次数
        String nextStartLevel = mGetHomeInfoBean.getNextStartLevel();
        String cont = "";
        if (!TextUtils.isEmpty(is_show) && is_show.endsWith("number")) {
            cont = ability + " 次祝福啦！";
        } else {
            cont = ability + " 个生命能量啦！";
        }
        String jieqi = mGetHomeInfoBean.getJieqi_branch_name();
        mycenter_tv_jieeqi.setText(jieqi);
        if (TextUtils.isEmpty(jieqi)) {
            mycenter_tv_jieeqi.setVisibility(View.GONE);
        } else {
            mycenter_tv_jieeqi.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            String showT = "亲，升到 " + nextStartLevel + " 星只需送出 " + cont;
            mycenter_tv_starsTiShi.setText(Html.fromHtml(showT));
            if (ability.equals("-1") && CurrentStartLevel == 9) {
                mycenter_tv_starsTiShi.setText("您就是最强王者！");
            }
            mycenterRelatStars.setVisibility(View.GONE);
            mycenter_relat_shenfen.setVisibility(View.VISIBLE);
            mycenter_relat_shenfen.removeAllViews();
            user_identity_imgs = mGetHomeInfoBean.getUser_identity_imgs();
            if (user_identity_imgs != null && user_identity_imgs.size() > 0) {
                for (GetHomeInfoBean info : user_identity_imgs) {
                    LinearLayout linearLayout = new LinearLayout(mActivity);
                    ImageView imageView = new ImageView(mActivity);
                    int dit = DensityUtil.dip2px(mActivity, 14);
                    int jian = DensityUtil.dip2px(mActivity, 2);
                    linearLayout.setPadding(0, 2, jian, 2);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(dit, dit));
                    GlideDownLoadImage.getInstance().loadCircleImageCommune(mActivity, info.getImage(), imageView);
                    linearLayout.addView(imageView);
                    mycenter_relat_shenfen.addView(linearLayout);
                }
            }
        }
    }

    MyDialog LevelDialog;
    int CurrentStartLevel;
    TextView tv_cont;

    public void dismissAllDialog() {
        if (toDoctorDialog != null && toDoctorDialog.isShowing()) {
            toDoctorDialog.dismiss();
        }
        if (mJinTieDialog != null && mJinTieDialog.isShowing()) {
            mJinTieDialog.dismiss();
        }
        if (LevelDialog != null && LevelDialog.isShowing()) {
            LevelDialog.dismiss();
        }
        if (actionDialog != null && actionDialog.isShowing()) {
            actionDialog.dismiss();
        }
        if (myKaDialog != null && myKaDialog.isShowing()) {
            myKaDialog.dismiss();
        }
        if (redBaoDialog != null && redBaoDialog.isShowing()) {
            redBaoDialog.dismiss();
        }
        if (CrediterCashDialog != null && CrediterCashDialog.isShowing()) {
            CrediterCashDialog.dismiss();
        }
        if (LifeBasicCashDialog != null && LifeBasicCashDialog.isShowing()) {
            LifeBasicCashDialog.dismiss();
        }
        if (LifeBasicDialog != null && LifeBasicDialog.isShowing()) {
            LifeBasicDialog.dismiss();
        }
        if (mJieQiQuanDialog != null && mJieQiQuanDialog.isShowing()) {
            mJieQiQuanDialog.dismiss();
        }
    }

    /**
     * 是否是坐堂医交押金成功回到我家提示弹层
     */
    public static boolean showDoctorDialogStatus = false;

    private void showAllDialog() {
        String loginStatus = (String) SharedPreferencesHelper.get(mActivity, "loginStatus", "");
        if (!loginStatus.equals(ConstantManager.loginStatus)
                || BottomMenuActivity.position != BottomMenuActivity.tab_position_mine
                || BottomMenuActivity.updatedialogstatus) {
            dismissAllDialog();
            return;
        }
        // 福包领取
        ReceiveFubaoUtils.getINSTANCE().getFubaoList();

        PionPairingUtils.getINSTANCE().layerZybUserTeamInfo();

        int isShowLifeBasicFestival = data.getIsShowLifeBasicFestival();
        if (isShowLifeBasicFestival != 0) {
            showJieQiQuanDialog();
            return;
        } else {
            if (mJieQiQuanDialog != null && mJieQiQuanDialog.isShowing()) {
                mJieQiQuanDialog.dismiss();
            }
        }

        //坐堂医支付成功弹层  1
        if (showDoctorDialogStatus) {
            showDoctorDialogStatus = false;
            showDoctorDialog();
            return;
        } else {
            //防止等级弹层提示后，黑屏后重新打开刷新没关闭问题
            if (toDoctorDialog != null && toDoctorDialog.isShowing()) {
                toDoctorDialog.dismiss();
            }
        }
        int displayHolidayTips = data.getDisplayHolidayTips();
        if (displayHolidayTips != 0) {
            Log.e("GetHomeInfoBean", "userHolidayTips");
            showJinTieDialog();
            return;
        } else {
            Log.e("GetHomeInfoBean", "data");
            if (mJinTieDialog != null && mJinTieDialog.isShowing()) {
                mJinTieDialog.dismiss();
            }
        }
        //等级提示  2
        if (chatuserStarLevelId > 0) {
            showLevelDialog();
            return;
        } else {
            if (LevelDialog != null && LevelDialog.isShowing()) {
                LevelDialog.dismiss();
            }
        }
        //我的卡包  3
        if (data.getIs_myka() > 0) {
            showMyKaDialog();
            return;
        } else {
            if (myKaDialog != null && myKaDialog.isShowing()) {
                myKaDialog.dismiss();
            }
        }
        //基础保障  4
        if (data.getDisplayLifeBasic() > 0) {
            showLifeBasiDialog();
            return;
        } else {
            if (LifeBasicDialog != null && LifeBasicDialog.isShowing()) {
                LifeBasicDialog.dismiss();
            }
        }

        //天才行动 5
        if (data.getIs_commonweal_activity() > 0) {
            showActionDialog();
            return;
        } else {
            if (actionDialog != null && actionDialog.isShowing()) {
                actionDialog.dismiss();
            }
        }
        //基础保障未提现  6
        if (data.getIsLifeBasicApplyCash() > 0) {
            showLifeBasicCashDialog();
            return;
        } else {
            if (LifeBasicCashDialog != null && LifeBasicCashDialog.isShowing()) {
                LifeBasicCashDialog.dismiss();
            }
        }
        //志愿者关联债权人提现  7
        if (data.getIsDisplayCrediterCash() > 0) {
            showCrediterCashDialog();
            return;
        } else {
            if (CrediterCashDialog != null && CrediterCashDialog.isShowing()) {
                CrediterCashDialog.dismiss();
            }
        }

        //红包 8
        if (isUnopenedRedPackage > 0) {
            showRedBaoDialog();
            return;
        } else {
            if (redBaoDialog != null && redBaoDialog.isShowing()) {
                redBaoDialog.dismiss();
            }
        }
    }


    MyDialog mJieQiQuanDialog;
    TextView tv_jieqiQuanname;

    public void showJieQiQuanDialog() {
        if (mJieQiQuanDialog == null) {
            mJieQiQuanDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_min_jieqiquan);// 创建Dialog并设置样式主题
            mJieQiQuanDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mJieQiQuanDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mJieQiQuanDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mJieQiQuanDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 4 / 5; //宽度设置为屏幕
            mJieQiQuanDialog.getWindow().setAttributes(p); //设置生效
            tv_jieqiQuanname = mJieQiQuanDialog.findViewById(R.id.tv_jieqiname);
            ImageView iv_img = mJieQiQuanDialog.findViewById(R.id.iv_img);
            LinearLayout layout_cancel = mJieQiQuanDialog.findViewById(R.id.layout_cancel);
            iv_img.setLayoutParams(new FrameLayout.LayoutParams(p.width, (int) (p.width * 1.56)));
            iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mJieQiQuanDialog.dismiss();
                    String life_basic_festival_config_id = data.getLife_basic_festival_config_id();
                    mPresent.recieiveFestivalCard(user_id, life_basic_festival_config_id);
                }
            });
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mJieQiQuanDialog.dismiss();
                }
            });
        } else {
            mJieQiQuanDialog.show();
        }
        tv_jieqiQuanname.setText("" + data.getShowLifeBasicFestivalName());
    }


    TextView tv_asset;
    MyDialog mJinTieDialog;

    public void showJinTieDialog() {
        if (mJinTieDialog == null) {
            mJinTieDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_centerjintie);// 创建Dialog并设置样式主题
            mJinTieDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mJinTieDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mJinTieDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mJinTieDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            mJinTieDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_xingji = mJinTieDialog.findViewById(R.id.iv_xingji);
            iv_xingji.setLayoutParams(new FrameLayout.LayoutParams(p.width, (int) (p.width * 1.2327)));
            tv_asset = mJinTieDialog.findViewById(R.id.tv_asset);
            iv_xingji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mJinTieDialog.dismiss();
                    mPresent.cancelHolidayTips(user_id);
                }
            });
        } else {
            mJinTieDialog.show();
        }
        tv_asset.setText("" + data.getDisplayHolidayTipsAsset() + "元");
    }

    public void showLevelDialog() {
        //每次升星级都提示并传给后台已读
        mPresent.doStarLevelRemind(user_id);
        if (LevelDialog == null) {
            LevelDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_centerlevel);// 创建Dialog并设置样式主题
            LevelDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = LevelDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            LevelDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = LevelDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            LevelDialog.getWindow().setAttributes(p); //设置生效
            tv_cont = LevelDialog.findViewById(R.id.tv_cont);
            ImageView iv_cancel = LevelDialog.findViewById(R.id.iv_cancel);
            ImageView iv_xingji = LevelDialog.findViewById(R.id.iv_xingji);
            tv_cont.setText("恭喜您成为" + CurrentStartLevel + "星CHO~");
            iv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LevelDialog.dismiss();
                }
            });
            iv_xingji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LevelDialog.dismiss();
                    Intent intent = new Intent(mActivity, StarInstructionAct.class);
                    intent.putExtra("starturl", star_level_illustrate_url);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            });
        } else {
            tv_cont.setText("恭喜您成为" + CurrentStartLevel + "星CHO~");
            LevelDialog.show();
        }
    }

    MyDialog LifeBasicDialog;

    /**
     * 基础保障弹层
     */
    public void showLifeBasiDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (LifeBasicDialog == null) {
                LifeBasicDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_connon);// 创建Dialog并设置样式主题
                LifeBasicDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = LifeBasicDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                LifeBasicDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = LifeBasicDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 3 / 4;
                LifeBasicDialog.getWindow().setAttributes(p); //设置生效
                ImageView fram_bg = LifeBasicDialog.findViewById(R.id.fram_bg);
                fram_bg.setBackgroundResource(R.mipmap.my_basic_bj);
                fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.423)));
                LinearLayout layout_cancel = LifeBasicDialog.findViewById(R.id.layout_cancel);

                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LifeBasicDialog.dismiss();
                    }
                });
                fram_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LifeBasicDialog.dismiss();/**/
                        Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getDisplayLifeBasicUrl());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                });
            } else {
                LifeBasicDialog.show();
            }
        }
    }

    MyDialog myKaDialog;

    /**
     * 基础保障弹层
     */
    public void showMyKaDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (myKaDialog == null) {
                myKaDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_myka);// 创建Dialog并设置样式主题
                myKaDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = myKaDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                myKaDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = myKaDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 3 / 4;
                myKaDialog.getWindow().setAttributes(p); //设置生效
                TextView btn_jihuo = myKaDialog.findViewById(R.id.btn_jihuo);
                LinearLayout layout_cancel = myKaDialog.findViewById(R.id.layout_cancel);

                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myKaDialog.dismiss();
                    }
                });
                btn_jihuo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myKaDialog.dismiss();/**/
                        Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getMyka_url());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                });
            } else {
                myKaDialog.show();
            }
        }
    }


    MyDialog LifeBasicCashDialog;

    /**
     * 基础保障未提现弹层
     */
    public void showLifeBasicCashDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (LifeBasicCashDialog == null) {
                LifeBasicCashDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_connon);// 创建Dialog并设置样式主题
                LifeBasicCashDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = LifeBasicCashDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                LifeBasicCashDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = LifeBasicCashDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 3 / 4;
                LifeBasicCashDialog.getWindow().setAttributes(p); //设置生效
                ImageView fram_bg = LifeBasicCashDialog.findViewById(R.id.fram_bg);
                fram_bg.setBackgroundResource(R.mipmap.my_lifebasiccash_bg);
                fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.316)));
                LinearLayout layout_cancel = LifeBasicCashDialog.findViewById(R.id.layout_cancel);

                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LifeBasicCashDialog.dismiss();
                    }
                });
                fram_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LifeBasicCashDialog.dismiss();/**/
                        Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getLifeBasicApplyCashUrl());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                });
            } else {
                LifeBasicCashDialog.show();
            }
        }
    }

    MyDialog CrediterCashDialog;

    /**
     * 债务未提现提示弹层
     */
    public void showCrediterCashDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (CrediterCashDialog == null) {
                CrediterCashDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_connon);// 创建Dialog并设置样式主题
                CrediterCashDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = CrediterCashDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                CrediterCashDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = CrediterCashDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 3 / 4;
                CrediterCashDialog.getWindow().setAttributes(p); //设置生效
                ImageView fram_bg = CrediterCashDialog.findViewById(R.id.fram_bg);
                fram_bg.setBackgroundResource(R.mipmap.my_credite_bg);
                fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.316)));
                LinearLayout layout_cancel = CrediterCashDialog.findViewById(R.id.layout_cancel);

                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CrediterCashDialog.dismiss();
                    }
                });
                fram_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CrediterCashDialog.dismiss();/**/
                        Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getVolunteerCreditorUrl());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                });
            } else {
                CrediterCashDialog.show();
            }
        }
    }

    MyDialog actionDialog;

    /**
     * 天才行动弹层
     */
    public void showActionDialog() {
        String loginStatus = (String) SharedPreferencesHelper.get(mActivity, "loginStatus", "");
        if (BottomMenuActivity.position != BottomMenuActivity.tab_position_mine || !loginStatus.equals(ConstantManager.loginStatus)) {
            return;
        }
        if (actionDialog == null) {
            actionDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_mine_action);// 创建Dialog并设置样式主题
            actionDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = actionDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            actionDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = actionDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4;
            actionDialog.getWindow().setAttributes(p); //设置生效
            ImageView fram_bg = actionDialog.findViewById(R.id.fram_bg);
            fram_bg.setBackgroundResource(R.mipmap.my_action_icon);
            fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.423)));
            LinearLayout layout_cancel = actionDialog.findViewById(R.id.layout_cancel);

            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionDialog.dismiss();
                }
            });
            fram_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionDialog.dismiss();
                    Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + data.getCommonweal_activity_url());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            });
        } else {
            actionDialog.show();
        }
    }

    MyDialog redBaoDialog;

    /**
     * 红包弹层
     */
    public void showRedBaoDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (redBaoDialog == null) {
                redBaoDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_centeropenredbao);// 创建Dialog并设置样式主题
                redBaoDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = redBaoDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                redBaoDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = redBaoDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth(); //宽度设置为屏幕
                redBaoDialog.getWindow().setAttributes(p); //设置生效

                RelativeLayout relat_redbao = redBaoDialog.findViewById(R.id.relat_redbao);
                LinearLayout layout_cancel = redBaoDialog.findViewById(R.id.layout_cancel);
                ImageView iv_cancel = redBaoDialog.findViewById(R.id.iv_cancel);
                ImageView iv_xingji = redBaoDialog.findViewById(R.id.iv_xingji);

                int bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.my_goodluckto_popupwindow).getWidth();// 获取图片宽度
                layout_cancel.setPadding(0, 0, (d.getWidth() - bmpW) / 2, 0);
                iv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redBaoDialog.dismiss();
                    }
                });
                iv_xingji.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        redBaoDialog.dismiss();
                        Intent intent = new Intent(mActivity, GoodLuckActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                });
            } else {
                redBaoDialog.show();
            }
        }
    }

    MyDialog notCHODialog;

    /**
     * 申请志愿者 不是cho弹层
     */
    public void showNotCHODialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (notCHODialog == null) {
                notCHODialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_mycenter_notcho);// 创建Dialog并设置样式主题
                notCHODialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = notCHODialog.getWindow();
                window.setGravity(Gravity.CENTER);
                notCHODialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = notCHODialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
                notCHODialog.getWindow().setAttributes(p); //设置生效
                LinearLayout layout_cancel = notCHODialog.findViewById(R.id.layout_cancel);
                TextView btn_upgrade = notCHODialog.findViewById(R.id.btn_upgrade);

                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notCHODialog.dismiss();
                    }
                });
                btn_upgrade.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notCHODialog.dismiss();/**/
                        mHandler.sendEmptyMessage(SkipEDIT);
                    }
                });
            } else {
                notCHODialog.show();
            }
        }
    }

    MyDialog toDoctorDialog;

    /**
     * 成为坐堂医交押金成功弹层
     */
    public void showDoctorDialog() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            if (toDoctorDialog == null) {
                toDoctorDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_mycenter_todoctor);// 创建Dialog并设置样式主题
                toDoctorDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = toDoctorDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                toDoctorDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = toDoctorDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
                toDoctorDialog.getWindow().setAttributes(p); //设置生效
                LinearLayout layout_cancel = toDoctorDialog.findViewById(R.id.layout_cancel);
                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDoctorDialog.dismiss();
                    }
                });
            } else {
                toDoctorDialog.show();
            }
        }
    }

    @Override
    public void getHomeInfoSuccess(GetHomeInfoDataBean responseBean) {
        firstConIn = false;
        String status = responseBean.getStatus();
        if (status.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status.equals("200")) {
            GetHomeInfoBean mGetHomeInfoBean = responseBean.getData();
            if (mGetHomeInfoBean != null) {
                chacheMap(mGetHomeInfoBean);
                setLoadData(mGetHomeInfoBean);

                GetHomeInfoBean.CornucopiaBean cornucopiaBeanitem = mGetHomeInfoBean.getCornucopia();
                if (cornucopiaBeanitem != null) {
                    showCornuCopia(cornucopiaBeanitem);
                }
            }
        } else if (status.equals("499")) {
            logoutInit();
        }
    }

    private void showCornuCopia(GetHomeInfoBean.CornucopiaBean cornucopiaBean) {
        isopencor = cornucopiaBean.getIs_open_cornucopia();
        cornum = cornucopiaBean.getSuper_shoukangyuan();
        tv_treasurenum.setText("已有 " + cornum + " 枚宝贝");
    }

    private void setLoadData(GetHomeInfoBean mGetHomeInfoBean) {
        data = mGetHomeInfoBean;
        ka_url = data.getMyka_url();
        Certification_url = data.getCertification_url();
        about_me_url = mGetHomeInfoBean.getAbout_me_url();
        isUnopenedRedPackage = mGetHomeInfoBean.getIsUnopenedRedPackage();
        star_level_illustrate_url = mGetHomeInfoBean.getStar_level_illustrate_url();
        CurrentStartLevel = mGetHomeInfoBean.getCurrentStartLevel();
        chatuserStarLevelId = mGetHomeInfoBean.getChatuserStarLevelId();
        is_cho = mGetHomeInfoBean.getIs_cho();
        saveNewInfo(mGetHomeInfoBean);
        showInfoView();
        String jintie = mGetHomeInfoBean.getHoliday_asset();
        if (TextUtils.isEmpty(jintie) || (!TextUtils.isEmpty(jintie) && jintie.equals("null"))) {
            jintie = "0.00";
        }
        mycenter_tv_jintie.setText("" + jintie);
        String jieqibao = mGetHomeInfoBean.getJieqibao();
        mycenterTvMoneyjieqibao.setText(jieqibao);
        String fuqibao = mGetHomeInfoBean.getFuqibao();
        mycenter_tv_moneyfqb.setText(fuqibao);
        String uzhufubao = mGetHomeInfoBean.getUzhufubao();
        mycenter_tv_moneyzfb.setText(uzhufubao);

        String shoukangyuan = mGetHomeInfoBean.getShoukangyuan();
        mycentertopTvAwakeskb.setText(shoukangyuan);
        String dongjie = mGetHomeInfoBean.getDongjie();
        mycentertopTvSleepskb.setText(dongjie);
        String super_ability = mGetHomeInfoBean.getSuper_ability();
        super_ability = PriceUtils.getInstance().seePrice(super_ability);
        mycentertopTvSleeplifeenergy.setText(super_ability);
        String shengmingnengliang = mGetHomeInfoBean.getShengmingnengliang();
        shengmingnengliang = PriceUtils.getInstance().seePrice(shengmingnengliang);
        mycentertopTvLifeenergy.setText(shengmingnengliang);
        UserUtils.saveWXToken(mActivity, mGetHomeInfoBean.getWxToken());
        showAllDialog();
    }

    private void logoutInit() {
        chatuserStarLevelId = 0;
        isUnopenedRedPackage = 0;
    }


    @Override
    public void doStarLevelRemindSuccess(ResponseBean responseBean) {
        String status = responseBean.getStatus();
        if (status.equals("400")) {
        } else if (status.equals("200")) {
            chatuserStarLevelId = 0;
        }
    }

    @Override
    public void error() {
        firstConIn = false;
        MineAfterDataMap.clear();
        MineAfterDataMap.putAll(SharedPreferencesUtil.getInstance().getHashMapData("mMineAfterData_" + user_id, GetHomeInfoBean.class));
        if (MineAfterDataMap != null && MineAfterDataMap.size() > 0) {
            GetHomeInfoBean mHomeAfterBean_ = MineAfterDataMap.get("mGetHomeInfoBean");
            setLoadData(mHomeAfterBean_);
        }
    }

    HashMap<String, GetHomeInfoBean> MineAfterDataMap = new HashMap<>();

    /**
     * 缓存当前用户数据
     *
     * @param mHomeAfterBean
     */
    private void chacheMap(GetHomeInfoBean mHomeAfterBean) {
        MineAfterDataMap.clear();
        MineAfterDataMap.put("mGetHomeInfoBean", mHomeAfterBean);
        SharedPreferencesUtil.getInstance().putHashMapData("mMineAfterData_" + user_id, MineAfterDataMap);
    }

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    /**
     * 兼容Android6.0动态权限
     * 咨询客服
     */
    private void conversationWrapper() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            conversation();
        }
    }

    /**
     * 启动对话界面
     * 顾客上线的时候，能够上传（或者更新）一些用户的自定义信息
     */
    private void conversation() {
        String avatar = (String) SharedPreferencesHelper.get(mActivity, "avatar", "");
        String phone = (String) SharedPreferencesHelper.get(mActivity, "phone", "");
        String user_name = (String) SharedPreferencesHelper.get(mActivity, "user_name", "");
        HashMap<String, String> updateInfo = new HashMap<>();
        updateInfo.put("name", user_name);
        updateInfo.put("avatar", avatar);
        updateInfo.put("tel", phone);
        user_id = (String) SharedPreferencesHelper.get(mActivity, "user_id", "");
        Intent intent = new MQIntentBuilder(getActivity())
                .setCustomizedId(user_id) // 相同的 id 会被识别为同一个顾客
                //.setClientInfo(clientInfo) // 设置顾客信息 PS: 这个接口只会生效一次,如果需要更新顾客信息,需要调用更新接口
                .updateClientInfo(updateInfo) // 更新顾客信息 PS: 如果客服在工作台更改了顾客信息，更新接口会覆盖之前的内容
                .build();
        startActivity(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    conversationWrapper();
                } else {
                    ToastUtils.showToast(com.meiqia.meiqiasdk.R.string.mq_sdcard_no_permission);
                }
                break;
            }
        }
    }
}
