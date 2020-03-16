package com.longcheng.lifecareplan.modular.mine.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.bean.ResponseBean;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.activity.MyGraH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.activity.PerfectInfoDialog;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.index.login.activity.UpdatePwActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.mine.absolutelyclear.activity.AbsolutelyclearAct;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.lifecareplan.modular.mine.bill.activity.BillActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.EngryRecordActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.SleepEngryActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.SleepSkbActivity;
import com.longcheng.lifecareplan.modular.mine.bill.activity.WakeSkbActivity;
import com.longcheng.lifecareplan.modular.mine.changeinviter.activity.ChangeInviterActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.TiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.fragment.genius.FunctionAdapter;
import com.longcheng.lifecareplan.modular.mine.fragment.genius.FunctionGVItemBean;
import com.longcheng.lifecareplan.modular.mine.goodluck.activity.GoodLuckActivity;
import com.longcheng.lifecareplan.modular.mine.invitation.activity.InvitationAct;
import com.longcheng.lifecareplan.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.lifecareplan.modular.mine.phosphor.PhosphorAct;
import com.longcheng.lifecareplan.modular.mine.rebirth.activity.RebirthActivity;
import com.longcheng.lifecareplan.modular.mine.relationship.activity.RelationshipAccountAct;
import com.longcheng.lifecareplan.modular.mine.rewardcenters.activity.RewardCentersActivity;
import com.longcheng.lifecareplan.modular.mine.set.activity.SetActivity;
import com.longcheng.lifecareplan.modular.mine.signIn.activity.SignInH5Activity;
import com.longcheng.lifecareplan.modular.mine.starinstruction.StarInstructionAct;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.GetHomeInfoDataBean;
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
import com.longcheng.lifecareplan.widget.Immersive;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * @author MarkShuai
 * @name 我的页面
 * @time 2017/11/23 17:24
 */
public class MineFragment extends BaseFragmentMVP<MineContract.View, MinePresenterImp<MineContract.View>> implements MineContract.View {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
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
    @BindView(R.id.mycenter_tv_money)
    TextView mycenterTvMoney;
    @BindView(R.id.mycenter_tv_bill)
    TextView mycenterTvBill;
    @BindView(R.id.mycenter_tv_withdraw)
    TextView mycenterTvWithdraw;
    @BindView(R.id.mycenter_layout_activatenergy)
    LinearLayout mycenterLayoutActivatenergy;
    //
    @BindView(R.id.mycenter_tv_totalallowance)
    TextView mycenterTvTotalallowance;
    @BindView(R.id.mycenter_tv_monthallowance)
    TextView mycenterTvMonthallowance;
    @BindView(R.id.mycenter_tv_yesterdayallowance)
    TextView mycenterTvYesterdayallowance;
    @BindView(R.id.mycenter_iv_moneyarrow)
    ImageView mycenterIvMoneyarrow;
    @BindView(R.id.mycenter_layout_allowance)
    LinearLayout mycenterLayoutAllowance;
    @BindView(R.id.mycenter_layout_allowancearrow)
    LinearLayout mycenterLayoutallowancearrow;
    @BindView(R.id.mycenter_layout_allowanceout)
    LinearLayout mycenter_layout_allowanceout;

    //
    @BindView(R.id.tv_jieqiname)
    TextView tv_jieqiname;
    @BindView(R.id.mycenter_layout_jieqisignin)
    RelativeLayout mycenterLayoutJieqisignin;
    @BindView(R.id.mycenter_layout_smallpusher)
    RelativeLayout mycenterLayoutsmallpusher;


    @BindView(R.id.mycenter_layout_myka)
    LinearLayout mycenter_layout_myka;
    @BindView(R.id.mycenter_layout_reward)
    LinearLayout mycenterLayoutreward;
    @BindView(R.id.mycenter_layout_invitationrecord)
    LinearLayout mycenterLayoutinvitationrecord;
    //

    @BindView(R.id.usercenter_relay_myenren)
    RelativeLayout usercenterRelayMyenren;
    @BindView(R.id.usercenter_relay_myStar)
    RelativeLayout usercenterRelayMyStar;
    @BindView(R.id.ll_mystar)
    LinearLayout dividerStar;
    @BindView(R.id.usercenter_relay_zodiacdescripte)
    LinearLayout usercenterRelayzodiacdescripte;
    @BindView(R.id.usercenter_relay_appexplanation)
    RelativeLayout usercenterRelayappexplanation;
    @BindView(R.id.usercenter_relay_goodluck)
    RelativeLayout usercenterRelaygoodluck;
    @BindView(R.id.usercenter_relay_address)
    RelativeLayout usercenterRelayAddress;
    @BindView(R.id.usercenter_relay_updatepw)
    RelativeLayout usercenterRelayupdatepw;

    @BindView(R.id.usercenter_layout_rebirth)
    LinearLayout usercenter_layout_rebirth;
    @BindView(R.id.usercenter_layout_changeinviter)
    LinearLayout usercenter_layout_changeinviter;

    @BindView(R.id.usercenter_relay_changeinviter)
    RelativeLayout usercenter_relay_changeinviter;
    @BindView(R.id.home_sv)
    MyScrollView homeSv;
    @BindView(R.id.mycentertop_tv_sleeplifeenergy)
    TextView mycentertopTvSleeplifeenergy;
    @BindView(R.id.ll_mycenter_sleepennergy)
    LinearLayout llMycenterSleepennergy;

    @BindView(R.id.usercenter_tv_volunteer)
    TextView usercenterTvVolunteer;
    @BindView(R.id.usercenter_relay_volunteer)
    RelativeLayout usercenterRelayVolunteer;
    @BindView(R.id.usercenter_tv_doctor)
    TextView usercenterTvDoctor;
    @BindView(R.id.usercenter_relay_doctor)
    RelativeLayout usercenterRelayDoctor;
    @BindView(R.id.layout_jiuzhen)
    LinearLayout layoutJiuzhen;

    @BindView(R.id.mycenter_tv_recharge)
    TextView mycenter_tv_recharge;

    @BindView(R.id.layout_volunteerlist)
    LinearLayout layout_volunteerlist;

    @BindView(R.id.layout_partygroup)
    LinearLayout layout_partygroup;
    @BindView(R.id.layout_creditor)
    LinearLayout layout_creditor;
    @BindView(R.id.usercenter_layout_tel)
    LinearLayout usercenter_layout_tel;
    @BindView(R.id.layout_functionstatus)
    LinearLayout layout_functionstatus;
    @BindView(R.id.tv_functionstatus)
    TextView tv_functionstatus;
    @BindView(R.id.layout_gongnengnlist)
    LinearLayout layout_gongnengnlist;
    @BindView(R.id.layout_auth)
    LinearLayout layout_auth;
    @BindView(R.id.layout_pool)
    LinearLayout layout_pool;

    @BindView(R.id.layout_gongnengngv)
    LinearLayout layout_gongnengngv;
    @BindView(R.id.gongnengn_gv1)
    MyGridView gongnengn_gv1;
    @BindView(R.id.gongnengn_gv2)
    MyGridView gongnengn_gv2;
    @BindView(R.id.mycenter_iv_jieqi)
    ImageView mycenter_iv_jieqi;
    @BindView(R.id.iv_meiqia)
    ImageView iv_meiqia;
    @BindView(R.id.layout_commissioner)
    LinearLayout layout_commissioner;

    @BindView(R.id.layout_publicize)
    LinearLayout layout_publicize;
    @BindView(R.id.mycenter_layout_loveVideo)
    LinearLayout mycenter_layout_loveVideo;

    @BindView(R.id.layout_jintie)
    LinearLayout layout_jintie;
    @BindView(R.id.mycenter_tv_jintie)
    TextView mycenter_tv_jintie;
    @BindView(R.id.iv_banner)
    ImageView iv_banner;
    @BindView(R.id.usercenter_relay_applybless)
    RelativeLayout usercenter_relay_applybless;
    @BindView(R.id.usercenter_tv_applybless)
    TextView usercenter_tv_applybless;
    @BindView(R.id.layout_orderlist)
    LinearLayout layout_orderlist;

    @BindView(R.id.tv_myenergycenter)
    TextView tv_myenergycenter;
    @BindView(R.id.usercenter_relay_myenergycenter)
    LinearLayout usercenter_relay_myenergycenter;


    private String is_cho;
    private String user_id;

    private GetHomeInfoBean data = new GetHomeInfoBean();
    private int star_level;
    private String star_level_illustrate_url;
    /**
     * 是否有未拆的红包 0：没有 1：有 需要谈层
     */
    private int isUnopenedRedPackage;
    /**
     * 是否有升级弹窗  用户首页是否有等级弹窗 0不显示；1显示
     */
    int chatuserStarLevelId;
    /**
     * 是否是大队长或主任  0：不是 ；1 是
     */
    private int isDirectorOrTeamLeader;
    private String about_me_url, complaints_hotline;
    private String qimingaction_goods_id;
    boolean mAllowanceLayoutShow = false;
    PerfectInfoDialog mPerfectInfoDialog;

    public static String Certification_url = "";

    @Override
    public int bindLayout() {
        return R.layout.fragment_mycenter;
    }

    @Override
    public void initView(View view) {
        Immersive.setBarH(getActivity(), toolbar);
        pageTopTvName.setText("我家");
        pagetopIvLeft.setVisibility(View.INVISIBLE);
        pagetopIvLeft.setBackgroundResource(R.mipmap.usercenter_info_icon);
        pagetopIvRigth.setBackgroundResource(R.mipmap.usercenter_set_icon);
        layout_jintie.setOnClickListener(this);
        layout_orderlist.setOnClickListener(this);
        usercenterRelayVolunteer.setOnClickListener(this);
        usercenterRelayDoctor.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        mycenterIvHead.setOnClickListener(this);
        layout_auth.setOnClickListener(this);
        mycenter_layout_myka.setOnClickListener(this);
        mycenterTvWithdraw.setOnClickListener(this);
        usercenterRelayAddress.setOnClickListener(this);
        mycenterLayoutActivatenergy.setOnClickListener(this);
        mycenterLayoutHumanity.setOnClickListener(this);
        usercenter_relay_applybless.setOnClickListener(this);
        mycenterLayoutallowancearrow.setOnClickListener(this);
        mycenterLayoutAllowance.setOnClickListener(this);
        usercenterRelayappexplanation.setOnClickListener(this);
        mycenterTvBill.setOnClickListener(this);
        usercenterRelayupdatepw.setOnClickListener(this);
        usercenterRelayzodiacdescripte.setOnClickListener(this);
        usercenterRelayMyenren.setOnClickListener(this);
        usercenterRelayMyStar.setOnClickListener(this);
        mycenterLayoutreward.setOnClickListener(this);
        mycenterLayoutinvitationrecord.setOnClickListener(this);
        usercenter_relay_myenergycenter.setOnClickListener(this);
        ll_mycenter_wakeskb.setOnClickListener(this);
        ll_mycenter_sleepskb.setOnClickListener(this);
        llMycenterSleepennergy.setOnClickListener(this);
        llEnergy.setOnClickListener(this);
        usercenterRelaygoodluck.setOnClickListener(this);
        mycenterLayoutJieqisignin.setOnClickListener(this);
        mycenterRelatStars.setOnClickListener(this);
        mycenter_relat_shenfen.setOnClickListener(this);
        mycenterLayoutsmallpusher.setOnClickListener(this);
        usercenter_layout_rebirth.setOnClickListener(this);
        usercenter_relay_changeinviter.setOnClickListener(this);
        layoutJiuzhen.setOnClickListener(this);
        mycenter_tv_recharge.setOnClickListener(this);
        layout_volunteerlist.setOnClickListener(this);
        layout_partygroup.setOnClickListener(this);
        layout_creditor.setOnClickListener(this);
        layout_functionstatus.setOnClickListener(this);
        layout_commissioner.setOnClickListener(this);
        layout_publicize.setOnClickListener(this);
        usercenter_layout_tel.setOnClickListener(this);
        layout_pool.setOnClickListener(this);
        iv_meiqia.setOnClickListener(this);
        iv_banner.setOnClickListener(this);
        mycenter_layout_loveVideo.setOnClickListener(this);
        gongnengn_gv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (FunctionGVlist1 != null && FunctionGVlist1.size() > 0) {
                    int viewId = FunctionGVlist1.get(position).getViewId();
                    viewClick(viewId);
                }
            }
        });
        gongnengn_gv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (FunctionGVlist2 != null && FunctionGVlist2.size() > 0) {
                    int viewId = FunctionGVlist2.get(position).getViewId();
                    viewClick(viewId);
                }
            }
        });
        mycenterTvName.setFocusable(true);
        mycenterTvName.setFocusableInTouchMode(true);
        mycenterTvName.requestFocus();
        initContext();
        setFunctionType();
        showFunctionGVData();
        initUserInfo();
    }

    @Override
    public void doBusiness(Context mActivity) {

    }

    /**
     * 设置常用功能显示类型
     */
    private void setFunctionType() {
        if (tv_functionstatus == null) {
            return;
        }
        boolean MineFuunctiontypeList = MySharedPreferences.getInstance().getMineFuunctiontypeList();
        if (MineFuunctiontypeList) {
            tv_functionstatus.setText("宫格展示");
            layout_gongnengnlist.setVisibility(View.VISIBLE);
            layout_gongnengngv.setVisibility(View.GONE);
        } else {
            tv_functionstatus.setText("列表展示");
            layout_gongnengnlist.setVisibility(View.GONE);
            layout_gongnengngv.setVisibility(View.VISIBLE);
        }
    }

    ArrayList<FunctionGVItemBean> FunctionGVlist1;
    ArrayList<FunctionGVItemBean> FunctionGVlist2;

    /**
     * 初始化显示宫格常用功能
     */
    private void showFunctionGVData() {
        FunctionGVlist1 = new ArrayList();
        FunctionGVlist2 = new ArrayList();

        FunctionGVlist1.add(new FunctionGVItemBean("我的订单", R.id.layout_orderlist, R.mipmap.my_order_icon));

        int is_alipay_pool_user = data.getIs_alipay_pool_user();
        if (is_alipay_pool_user != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("水库", R.id.layout_pool, R.mipmap.my_pool_icon));
        }

        int isBlessedTeacher = data.getIsBlessedTeacher();
        if (isBlessedTeacher == 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("申请祝福师", R.id.usercenter_relay_applybless, R.mipmap.my_zhufushi_icon));
        } else {
            FunctionGVlist1.add(new FunctionGVItemBean("我是祝福师", R.id.usercenter_relay_applybless, R.mipmap.my_zhufushi_icon));
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
        FunctionGVlist1.add(new FunctionGVItemBean(Voname, R.id.usercenter_relay_volunteer, R.mipmap.my_volunteersr_icon));
        FunctionGVlist1.add(new FunctionGVItemBean(Doname, R.id.usercenter_relay_doctor, R.mipmap.my_doctor_icon));
        FunctionGVlist1.add(new FunctionGVItemBean("实名认证", R.id.layout_auth, R.mipmap.my_auth_icon));
        FunctionGVlist1.add(new FunctionGVItemBean("奖励中心", R.id.mycenter_layout_reward, R.mipmap.homepage_icon_jianglizhongxin));
        int isCommissionerIdentity = data.getIsCommissionerIdentity();
        if (isCommissionerIdentity != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("我是特派员", R.id.layout_commissioner, R.mipmap.my_commissioner_icon));
        }
        int hasDiagnosticRecord = data.getHasDiagnosticRecord();
        if (hasDiagnosticRecord != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("就诊记录", R.id.layout_jiuzhen, R.mipmap.my_treatment_icon));
        }
        FunctionGVlist1.add(new FunctionGVItemBean("我的恩人", R.id.usercenter_relay_myenren, R.mipmap.my_benefactor_icon));
        FunctionGVlist1.add(new FunctionGVItemBean("好运来", R.id.usercenter_relay_goodluck, R.mipmap.usercenter_goodluck_icon));
        FunctionGVlist1.add(new FunctionGVItemBean(HomeFragment.jieqi_name + "小推手", R.id.mycenter_layout_smallpusher, R.mipmap.my_smallpusher_icon));
        FunctionGVlist1.add(new FunctionGVItemBean("每日签到", R.id.mycenter_layout_jieqisignin, R.mipmap.my_signin_icon));
        if (data.issQimingSponsorUser()) {
            FunctionGVlist1.add(new FunctionGVItemBean("启明星", R.id.usercenter_relay_myStar, R.mipmap.my_star_icon));
        }
        int isPartyGroupLeader = data.getIsPartyGroupLeader();
        if (isPartyGroupLeader != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("党小组审批", R.id.layout_partygroup, R.mipmap.my_thegroupleader_icon));
        }
        int isPartymember = data.getIsPartymember();
        if (isPartymember != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("志愿者列表", R.id.layout_volunteerlist, R.mipmap.my_volunteers_icon));
        }
        int isVolunteerCreditor = data.getIsVolunteerCreditor();
        if (isVolunteerCreditor != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("债权人", R.id.layout_creditor, R.mipmap.my_debt_icon));
        }
        int displayLifeAdReward = data.getDisplayLifeAdReward();
        if (displayLifeAdReward != 0) {
            FunctionGVlist1.add(new FunctionGVItemBean("广告收益", R.id.layout_publicize, R.mipmap.my_ad_icon));
        }
        FunctionGVlist1.add(new FunctionGVItemBean("邀请记录", R.id.mycenter_layout_invitationrecord, R.mipmap.homepage_icon_invitation));

        FunctionAdapter mFunctionAdapter1 = new FunctionAdapter(mActivity, FunctionGVlist1);
        gongnengn_gv1.setAdapter(mFunctionAdapter1);


        FunctionGVlist2.add(new FunctionGVItemBean("一目了然", R.id.usercenter_relay_appexplanation, R.mipmap.usercenter_appexplanation_icon));
        FunctionGVlist2.add(new FunctionGVItemBean("地址管理", R.id.usercenter_relay_address, R.mipmap.usercenter_address_icon));
        FunctionGVlist2.add(new FunctionGVItemBean("修改密码", R.id.usercenter_relay_updatepw, R.mipmap.usercenter_updatepw_icon));
        int is_show_invitation = data.getIs_show_invitation();
        if (is_show_invitation != 0) {
            FunctionGVlist2.add(new FunctionGVItemBean("变更邀请人", R.id.usercenter_relay_changeinviter, R.mipmap.my_change_icon));
        }
        //是否显示复活卡  0：不显示  1：显示
        int isResetCard = data.getIsResetCard();
        if (isResetCard != 0) {
            FunctionGVlist2.add(new FunctionGVItemBean("重生卡", R.id.usercenter_layout_rebirth, R.mipmap.my_rebirth_icon));
        }
        FunctionGVlist2.add(new FunctionGVItemBean("投诉电话", R.id.usercenter_layout_tel, R.mipmap.my_tel_icon));
        FunctionAdapter mFunctionAdapter2 = new FunctionAdapter(mActivity, FunctionGVlist2);
        gongnengn_gv2.setAdapter(mFunctionAdapter2);
    }


    /**
     * @param v
     */
    @Override
    public void widgetClick(View v) {
        int viewId = v.getId();
        viewClick(viewId);
    }

    /**
     * 跳转人情账
     */
    private void skipRQZUrl() {
        Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("html_url", Config.BASE_HEAD_URL + "home/mybook/newyearmybook/user_id/" + user_id);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
    }

    private void viewClick(int viewId) {
        Intent intent;
        switch (viewId) {
            case R.id.iv_meiqia:
                conversationWrapper();
                break;
            case R.id.iv_banner:
                String url = data.getNewyear_mybook_url();
                if (!TextUtils.isEmpty(url)) {
                    intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + url);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.usercenter_relay_applybless://申请祝福师
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getZhufu_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;

            case R.id.pagetop_layout_rigth://设置
                intent = new Intent(mActivity, SetActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("about_me_url", "" + about_me_url);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_functionstatus://切换功能显示类型
                boolean MineFuunctiontypeList = MySharedPreferences.getInstance().getMineFuunctiontypeList();
                if (MineFuunctiontypeList) {
                    MySharedPreferences.getInstance().saveMineFuunctiontypeList(false);
                } else {
                    MySharedPreferences.getInstance().saveMineFuunctiontypeList(true);
                }
                setFunctionType();
                break;

            case R.id.mycenter_tv_recharge://充值
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getUserasset_recharge_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_volunteerlist://志愿者申请列表
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getPartymember_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;

            case R.id.layout_publicize://广告收益
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getDisplayLifeAdRewardUrl());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_creditor://债权人
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getVolunteerCreditorUrl());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_partygroup://党小组审批
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getPartyGroupLeaderUrl());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_pool://水池
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getAlipay_pool_user_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_volunteer://志愿者
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
            case R.id.layout_auth://实名认证
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + Certification_url);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_myka://我的卡包
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getMyka_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_doctor://坐堂医
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
            case R.id.layout_jiuzhen://就诊记录
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getPatient_record_url());
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
            case R.id.layout_commissioner://我是特派员
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getCommissioner_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_jintie://节日津贴
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getHoliday_asset_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_smallpusher://小推手
                ToastUtils.showToast("程序猿正在攻坚中…");
                break;
            case R.id.mycenter_iv_head://用户信息
                intent = new Intent(mActivity, UserInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_layout_tel:
                onTelClick();
                break;
            case R.id.mycenter_layout_allowancearrow://津贴
                if (mycenterIvMoneyarrow == null) {
                    break;
                }
                if (mAllowanceLayoutShow) {
                    mAllowanceLayoutShow = false;
                    mycenterIvMoneyarrow.setBackgroundResource(R.mipmap.jintie_arrow_right);
                    mycenterLayoutAllowance.setVisibility(View.GONE);
                    // 向左边移出
                    Animation mHiddenAction = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out);
                    mycenterLayoutAllowance.startAnimation(mHiddenAction);
//                    mycenter_layout_allowanceout.setAnimation(AnimationUtils.makeOutAnimation(getActivity(), false));
                } else {
                    mAllowanceLayoutShow = true;
                    mycenterIvMoneyarrow.setBackgroundResource(R.mipmap.jintie_arrow_left);
                    mycenterLayoutAllowance.setVisibility(View.VISIBLE);
                    // 向右边移入
                    mycenter_layout_allowanceout.setAnimation(AnimationUtils.makeInAnimation(getActivity(), true));
                }
                break;
            case R.id.mycenter_layout_allowance:
                break;
            case R.id.mycenter_layout_humanity://人情账
                intent = new Intent(mActivity, RelationshipAccountAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_jieqisignin://签到
                signInGetInfo("signIn");
                break;
            case R.id.mycenter_tv_withdraw://提现
                int isVolunteerCreditor = data.getIsVolunteerCreditor();
                if (isVolunteerCreditor == 0) {
                    if (isDirectorOrTeamLeader == 0 || (isDirectorOrTeamLeader == 1 && CurrentStartLevel >= 6)) {
                        intent = new Intent(mActivity, BaoZhangActitvty.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getDebtwithdrawUrl());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    } else {
                        ToastUtils.showToast("大队长及主任达到六星级才可提现");
                    }
                } else {//债权人 可以直接进入提现
                    intent = new Intent(mActivity, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + data.getDebtwithdrawUrl());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }

                break;
            case R.id.usercenter_relay_address://地址管理
                intent = new Intent(mActivity, AddressListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("receive_user_id", user_id);
                intent.putExtra("skiptype", "MineFragment");
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_activatenergy://激活能量
                intent = new Intent(mActivity, ActivatEnergyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_orderlist://订单
                intent = new Intent(mActivity, OrderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_myenergycenter://能量中心
                intent = new Intent(mActivity, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getIsEnergyCenter_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_appexplanation://一目了然
                intent = new Intent(mActivity, AbsolutelyclearAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_tv_bill://账单
                intent = new Intent(mActivity, BillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_updatepw://修改密码
                intent = new Intent(mActivity, UpdatePwActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_zodiacdescripte://一字千金
                signInGetInfo("AWordOfGold");
                break;
            case R.id.usercenter_relay_myenren://恩人
                intent = new Intent(mActivity, MyGraH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + HomeFragment.my_gratitude_url);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_reward://奖励中心
                intent = new Intent(mActivity, RewardCentersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_invitationrecord://邀请记录
                intent = new Intent(mActivity, InvitationAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_myStar://启明星
                if (data != null && data.issQimingSponsorUser() && !TextUtils.isEmpty(data.getQimingSponsorUserUrl())) {
                    intent = new Intent(mActivity, PhosphorAct.class);
                    intent.putExtra("starturl", data.getQimingSponsorUserUrl());
                    intent.putExtra("action_goods_id", "" + qimingaction_goods_id);
                    intent.putExtra("qiming_user_id", "" + user_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.ll_mycenter_sleepskb:
                intent = new Intent(mActivity, SleepSkbActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.ll_mycenter_wakeskb:
                intent = new Intent(mActivity, WakeSkbActivity.class);
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
            case R.id.usercenter_relay_goodluck://好运来
                intent = new Intent(mActivity, GoodLuckActivity.class);
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
            case R.id.usercenter_layout_rebirth://重生卡
                intent = new Intent(mActivity, RebirthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_changeinviter://变更邀请人
                intent = new Intent(mActivity, ChangeInviterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            default:
                break;
        }
    }

    MyDialog telDialog;
    TextView tv_tel;

    public void onTelClick() {
        if (telDialog == null) {
            telDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_tel);// 创建Dialog并设置样式主题
            telDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = telDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.showBottomDialog);
            telDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = telDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth(); //宽度设置为屏幕
            telDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_tel = (LinearLayout) telDialog.findViewById(R.id.layout_tel);
            TextView tv_cancel = (TextView) telDialog.findViewById(R.id.tv_cancel);
            tv_tel = (TextView) telDialog.findViewById(R.id.tv_tel);

            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telDialog.dismiss();
                }
            });
            layout_tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    telDialog.dismiss();
                    DensityUtil.getPhoneToKey(mContext, complaints_hotline);
                }
            });
        } else {
            telDialog.show();
        }
        if (TextUtils.isEmpty(complaints_hotline)) {
            complaints_hotline = "400-6124365";
        }
        if (tv_tel != null) {
            tv_tel.setText(complaints_hotline);
        }
    }

    /**
     * 状态：每日签到（signIn）；一字千金（AWordOfGold）
     */
    String clickHetInfoStatus = "";

    /**
     * 获取用户签到信息
     */
    public void signInGetInfo(String clickHetInfo_) {
        this.clickHetInfoStatus = clickHetInfo_;
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
        if (clickHetInfoStatus.equals("AWordOfGold")) {
            AWordOfGoldStatus(oldUserPerfectInfoStatus);
        } else if (clickHetInfoStatus.equals("signIn")) {
            signinstatus(oldUserPerfectInfoStatus);
        }

    }

    /**
     * 每日签到
     *
     * @param oldUserPerfectInfoStatus_ 老用户是否完善信息
     */
    private void signinstatus(boolean oldUserPerfectInfoStatus_) {
        is_cho = (String) SharedPreferencesHelper.get(mActivity, "is_cho", "");
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            if (oldUserPerfectInfoStatus_) {
                Intent intent = new Intent(mActivity, SignInH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
            } else {
                if (mPerfectInfoDialog == null) {
                    mPerfectInfoDialog = new PerfectInfoDialog(getActivity(), mHandler, SkipEDIT);
                }
                mPerfectInfoDialog.showEditDialog("请完善资料后领取奖励哦~", "完善资料");
            }
        } else {
            if (mPerfectInfoDialog == null) {
                mPerfectInfoDialog = new PerfectInfoDialog(getActivity(), mHandler, SkipEDIT);
            }
            mPerfectInfoDialog.showEditDialog("成为CHO后才能领取奖励哦~", "完善资料");
        }
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
        String jieqi = HomeFragment.jieqi_name;
        if (TextUtils.isEmpty(jieqi)) {
            jieqi = "";
        }
        tv_jieqiname.setText(jieqi + "小推手");
        GlideDownLoadImage.getInstance().loadCircleHeadImageCenter(mActivity, avatar, mycenterIvHead);
        GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mActivity, data.getJieqi_pic(), mycenter_iv_jieqi);

        int dit = DensityUtil.screenWith(mContext);
        int jian = (int) (dit / 5.137);
        iv_banner.setLayoutParams(new LinearLayout.LayoutParams(dit, jian));
        if (data.getIsShowNewyearMybook() == 1) {
            iv_banner.setVisibility(View.VISIBLE);
            GlideDownLoadImage.getInstance().loadCircleImageRoleREf2(data.getNewyear_mybook_pic(), iv_banner, 0);
        } else {
            iv_banner.setVisibility(View.GONE);
        }
        showFunctionGVData();
    }

    private void saveNewInfo(GetHomeInfoBean mGetHomeInfoBean) {
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
            cont = ability + "</font>次祝福啦！";
        } else {
            cont = ability + "</font>个生命能量啦！";
        }
        String jieqi = mGetHomeInfoBean.getJieqi_branch_name();
        mycenter_tv_jieeqi.setText(jieqi);
        if (TextUtils.isEmpty(jieqi)) {
            mycenter_tv_jieeqi.setVisibility(View.GONE);
        } else {
            mycenter_tv_jieeqi.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            String showT = "亲，升到<font color=\"#ff443b\">" + nextStartLevel + "</font>星只需送出<font color=\"#ff443b\">" + cont;
            mycenter_tv_starsTiShi.setText(Html.fromHtml(showT));
            if (ability.equals("-1") && CurrentStartLevel == 9) {
                mycenter_tv_starsTiShi.setText("您就是最强王者！");
            }
//            mycenterTvStars.setText(getString(R.string.xingJi) + CurrentStartLevel);
            mycenterRelatStars.setVisibility(View.GONE);
            mycenter_relat_shenfen.setVisibility(View.VISIBLE);
            mycenter_relat_shenfen.removeAllViews();
            ArrayList<GetHomeInfoBean> user_identity_imgs = mGetHomeInfoBean.getUser_identity_imgs();
            if (user_identity_imgs != null && user_identity_imgs.size() > 0) {
                for (GetHomeInfoBean info : user_identity_imgs) {
                    LinearLayout linearLayout = new LinearLayout(mActivity);
                    ImageView imageView = new ImageView(mActivity);
                    int dit = DensityUtil.dip2px(mActivity, 18);
                    int jian = DensityUtil.dip2px(mActivity, 3);
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
        if (mRQZDialog != null && mRQZDialog.isShowing()) {
            mRQZDialog.dismiss();
        }
        if (mZFSDialog != null && mZFSDialog.isShowing()) {
            mZFSDialog.dismiss();
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
        if (HomeFragment.isShowZhufubaoPayLayer == 1) {
            showZFSDialog();
            return;
        } else {
            if (mZFSDialog != null && mZFSDialog.isShowing()) {
                mZFSDialog.dismiss();
            }
        }
        boolean isShowRQZ = MySharedPreferences.getInstance().getIsShowRQZ();
        if (isShowRQZ && data.getIsShowNewyearMybook() == 1) {
            MySharedPreferences.getInstance().saveIsShowRQZ(false);
            showRQZDialog();
            return;
        } else {
            if (mRQZDialog != null && mRQZDialog.isShowing()) {
                mRQZDialog.dismiss();
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

    ImageView iv_img;
    MyDialog mZFSDialog;

    /**
     * 显示祝福师付款弹层
     */
    private void showZFSDialog() {
        try {
            if (mZFSDialog == null) {
                mZFSDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_zfscash);// 创建Dialog并设置样式主题
                mZFSDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = mZFSDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                mZFSDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = mZFSDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 3 / 4;
                mZFSDialog.getWindow().setAttributes(p); //设置生效
                iv_img = (ImageView) mZFSDialog.findViewById(R.id.iv_img);
                LinearLayout layout_img = (LinearLayout) mZFSDialog.findViewById(R.id.layout_img);
                layout_img.getBackground().setAlpha(92);
                TextView tv_sure = (TextView) mZFSDialog.findViewById(R.id.tv_sure);
                LinearLayout layout_cancel = (LinearLayout) mZFSDialog.findViewById(R.id.layout_cancel);
                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mZFSDialog.dismiss();
                    }
                });
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mZFSDialog.dismiss();/**/
                        if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                            Intent intent = new Intent(mContext, TiXianRecordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }

                    }
                });
            } else {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    mZFSDialog.show();
                }
            }
            GlideDownLoadImage.getInstance().loadCircleImageRoleREf(mActivity, HomeFragment.current_jieqi_picture, iv_img, 0);

        } catch (Exception e) {

        }
    }

    MyDialog mRQZDialog;

    public void showRQZDialog() {
        if (mRQZDialog == null) {
            mRQZDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_center_rqz);// 创建Dialog并设置样式主题
            mRQZDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mRQZDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mRQZDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mRQZDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
            mRQZDialog.getWindow().setAttributes(p); //设置生效
            TextView tv_cancel = (TextView) mRQZDialog.findViewById(R.id.tv_cancel);
            ImageView iv_xingji = (ImageView) mRQZDialog.findViewById(R.id.iv_xingji);
            iv_xingji.setLayoutParams(new FrameLayout.LayoutParams(p.width, (int) (p.width * 1.116)));
            iv_xingji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRQZDialog.dismiss();
                    skipRQZUrl();
                }
            });
            tv_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRQZDialog.dismiss();
                }
            });
        } else {
            mRQZDialog.show();
        }
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
            ImageView iv_xingji = (ImageView) mJinTieDialog.findViewById(R.id.iv_xingji);
            iv_xingji.setLayoutParams(new FrameLayout.LayoutParams(p.width, (int) (p.width * 1.2327)));
            tv_asset = (TextView) mJinTieDialog.findViewById(R.id.tv_asset);
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
            tv_cont = (TextView) LevelDialog.findViewById(R.id.tv_cont);
            ImageView iv_cancel = (ImageView) LevelDialog.findViewById(R.id.iv_cancel);
            ImageView iv_xingji = (ImageView) LevelDialog.findViewById(R.id.iv_xingji);
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
                ImageView fram_bg = (ImageView) LifeBasicDialog.findViewById(R.id.fram_bg);
                fram_bg.setBackgroundResource(R.mipmap.my_basic_bj);
                fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.423)));
                LinearLayout layout_cancel = (LinearLayout) LifeBasicDialog.findViewById(R.id.layout_cancel);

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
                TextView btn_jihuo = (TextView) myKaDialog.findViewById(R.id.btn_jihuo);
                LinearLayout layout_cancel = (LinearLayout) myKaDialog.findViewById(R.id.layout_cancel);

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
                ImageView fram_bg = (ImageView) LifeBasicCashDialog.findViewById(R.id.fram_bg);
                fram_bg.setBackgroundResource(R.mipmap.my_lifebasiccash_bg);
                fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.316)));
                LinearLayout layout_cancel = (LinearLayout) LifeBasicCashDialog.findViewById(R.id.layout_cancel);

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
                ImageView fram_bg = (ImageView) CrediterCashDialog.findViewById(R.id.fram_bg);
                fram_bg.setBackgroundResource(R.mipmap.my_credite_bg);
                fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.316)));
                LinearLayout layout_cancel = (LinearLayout) CrediterCashDialog.findViewById(R.id.layout_cancel);

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
            ImageView fram_bg = (ImageView) actionDialog.findViewById(R.id.fram_bg);
            fram_bg.setBackgroundResource(R.mipmap.my_action_icon);
            fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.423)));
            LinearLayout layout_cancel = (LinearLayout) actionDialog.findViewById(R.id.layout_cancel);

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

                RelativeLayout relat_redbao = (RelativeLayout) redBaoDialog.findViewById(R.id.relat_redbao);
                LinearLayout layout_cancel = (LinearLayout) redBaoDialog.findViewById(R.id.layout_cancel);
                ImageView iv_cancel = (ImageView) redBaoDialog.findViewById(R.id.iv_cancel);
                ImageView iv_xingji = (ImageView) redBaoDialog.findViewById(R.id.iv_xingji);

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
                LinearLayout layout_cancel = (LinearLayout) notCHODialog.findViewById(R.id.layout_cancel);
                TextView btn_upgrade = (TextView) notCHODialog.findViewById(R.id.btn_upgrade);

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
                LinearLayout layout_cancel = (LinearLayout) toDoctorDialog.findViewById(R.id.layout_cancel);
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
            }
        } else if (status.equals("499")) {
            logoutInit();
        }
    }

    private void setLoadData(GetHomeInfoBean mGetHomeInfoBean) {
        data = mGetHomeInfoBean;
        Certification_url = data.getCertification_url();
        if (usercenterTvDoctor == null) {
            return;
        }

        int isDoctorIdentity = mGetHomeInfoBean.getIsDoctorIdentity();//是否是坐堂医 0不是；1 是
        int isVolunteerIdentity = mGetHomeInfoBean.getIsVolunteerIdentity();//是否是志愿者 0不是；1 是
        if (isDoctorIdentity == 0) {
            usercenterTvDoctor.setText("申请坐堂医");
        } else {
            usercenterTvDoctor.setText("我是坐堂医");
        }
        if (isVolunteerIdentity == 0) {
            usercenterTvVolunteer.setText("申请志愿者");
        } else {
            usercenterTvVolunteer.setText("我是志愿者");
        }
        int isBlessedTeacher = data.getIsBlessedTeacher();
        if (isBlessedTeacher == 0) {
            usercenter_tv_applybless.setText("申请祝福师");
        } else {
            usercenter_tv_applybless.setText("我是祝福师");
        }
        int is_alipay_pool_user = data.getIs_alipay_pool_user();
        if (is_alipay_pool_user != 0) {
            layout_pool.setVisibility(View.VISIBLE);
        } else {
            layout_pool.setVisibility(View.GONE);
        }
        int isCommissionerIdentity = mGetHomeInfoBean.getIsCommissionerIdentity();
        if (isCommissionerIdentity == 0) {
            layout_commissioner.setVisibility(View.GONE);
        } else {
            layout_commissioner.setVisibility(View.VISIBLE);
        }
        int hasDiagnosticRecord = mGetHomeInfoBean.getHasDiagnosticRecord();
        if (hasDiagnosticRecord == 0) {
            layoutJiuzhen.setVisibility(View.GONE);
        } else {
            layoutJiuzhen.setVisibility(View.VISIBLE);
        }

        int isPartymember = mGetHomeInfoBean.getIsPartymember();
        if (isPartymember == 0) {
            layout_volunteerlist.setVisibility(View.GONE);
        } else {
            layout_volunteerlist.setVisibility(View.VISIBLE);
        }

        int isPartyGroupLeader = data.getIsPartyGroupLeader();
        if (isPartyGroupLeader == 0) {
            layout_partygroup.setVisibility(View.GONE);
        } else {
            layout_partygroup.setVisibility(View.VISIBLE);
        }
        int isVolunteerCreditor = data.getIsVolunteerCreditor();
        if (isVolunteerCreditor == 0) {
            layout_creditor.setVisibility(View.GONE);
        } else {
            layout_creditor.setVisibility(View.VISIBLE);
        }
        int displayLifeAdReward = data.getDisplayLifeAdReward();
        if (displayLifeAdReward == 0) {
            layout_publicize.setVisibility(View.GONE);
        } else {
            layout_publicize.setVisibility(View.VISIBLE);
        }
        complaints_hotline = mGetHomeInfoBean.getComplaints_hotline();
        about_me_url = mGetHomeInfoBean.getAbout_me_url();
        isDirectorOrTeamLeader = mGetHomeInfoBean.getIsDirectorOrTeamLeader();
        isUnopenedRedPackage = mGetHomeInfoBean.getIsUnopenedRedPackage();
        star_level_illustrate_url = mGetHomeInfoBean.getStar_level_illustrate_url();
        CurrentStartLevel = mGetHomeInfoBean.getCurrentStartLevel();
        chatuserStarLevelId = mGetHomeInfoBean.getChatuserStarLevelId();
        star_level = mGetHomeInfoBean.getStar_level();
        is_cho = mGetHomeInfoBean.getIs_cho();
        saveNewInfo(mGetHomeInfoBean);
        showInfoView();
        int is_show_invitation = mGetHomeInfoBean.getIs_show_invitation();
        if (is_show_invitation == 1) {
            usercenter_layout_changeinviter.setVisibility(View.VISIBLE);
        } else {
            usercenter_layout_changeinviter.setVisibility(View.GONE);
        }
        //是否显示复活卡  0：不显示  1：显示
        int isResetCard = mGetHomeInfoBean.getIsResetCard();
        if (isResetCard == 0) {
            usercenter_layout_rebirth.setVisibility(View.GONE);
        } else {
            usercenter_layout_rebirth.setVisibility(View.VISIBLE);
        }
        int is_show_holiday_asset = mGetHomeInfoBean.getIs_show_holiday_asset();
        if (is_show_holiday_asset != 0) {
            layout_jintie.setVisibility(View.VISIBLE);
            mycenter_tv_jintie.setText("" + mGetHomeInfoBean.getHoliday_asset());
        } else {
            layout_jintie.setVisibility(View.GONE);
        }

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
        String asset = mGetHomeInfoBean.getAsset();
        mycenterTvMoney.setText(asset);
        String userTotalReward = mGetHomeInfoBean.getUserTotalReward();
        mycenterTvTotalallowance.setText(userTotalReward);
        String userMonthTotalReward = mGetHomeInfoBean.getUserMonthTotalReward();
        mycenterTvMonthallowance.setText(userMonthTotalReward);
        String userYesterdayReward = mGetHomeInfoBean.getUserYesterdayReward();
        mycenterTvYesterdayallowance.setText(userYesterdayReward);

        if (mGetHomeInfoBean.issQimingSponsorUser()) {
            qimingaction_goods_id = mGetHomeInfoBean.getQimingaction_goods_id();
            dividerStar.setVisibility(View.VISIBLE);
            usercenterRelayMyStar.setVisibility(View.VISIBLE);
        } else {
            dividerStar.setVisibility(View.GONE);
            usercenterRelayMyStar.setVisibility(View.GONE);
        }
        UserUtils.saveWXToken(mActivity, mGetHomeInfoBean.getWxToken());
        showAllDialog();
    }

    private void logoutInit() {
        isDirectorOrTeamLeader = 0;
        chatuserStarLevelId = 0;
        isUnopenedRedPackage = 0;
        star_level = 0;
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
