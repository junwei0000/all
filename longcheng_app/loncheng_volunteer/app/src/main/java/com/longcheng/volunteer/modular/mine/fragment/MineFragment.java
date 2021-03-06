package com.longcheng.volunteer.modular.mine.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
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

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseFragmentMVP;
import com.longcheng.volunteer.bean.ResponseBean;
import com.longcheng.volunteer.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.volunteer.modular.helpwith.fragment.HelpWithFragmentNew;
import com.longcheng.volunteer.modular.helpwith.myGratitude.activity.MyGraH5Activity;
import com.longcheng.volunteer.modular.helpwith.myfamily.activity.PerfectInfoDialog;
import com.longcheng.volunteer.modular.home.fragment.HomeFragment;
import com.longcheng.volunteer.modular.index.login.activity.UpdatePwActivity;
import com.longcheng.volunteer.modular.mine.absolutelyclear.activity.AbsolutelyclearAct;
import com.longcheng.volunteer.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.volunteer.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.volunteer.modular.mine.bill.activity.BillActivity;
import com.longcheng.volunteer.modular.mine.bill.activity.EngryRecordActivity;
import com.longcheng.volunteer.modular.mine.bill.activity.SleepEngryActivity;
import com.longcheng.volunteer.modular.mine.bill.activity.SleepSkbActivity;
import com.longcheng.volunteer.modular.mine.bill.activity.WakeSkbActivity;
import com.longcheng.volunteer.modular.mine.changeinviter.activity.ChangeInviterActivity;
import com.longcheng.volunteer.modular.mine.fragment.genius.ActionH5Activity;
import com.longcheng.volunteer.modular.mine.fragment.genius.FunctionAdapter;
import com.longcheng.volunteer.modular.mine.fragment.genius.FunctionGVItemBean;
import com.longcheng.volunteer.modular.mine.goodluck.activity.GoodLuckActivity;
import com.longcheng.volunteer.modular.mine.invitation.activity.InvitationAct;
import com.longcheng.volunteer.modular.mine.myaddress.activity.AddressListActivity;
import com.longcheng.volunteer.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.volunteer.modular.mine.phosphor.PhosphorAct;
import com.longcheng.volunteer.modular.mine.rebirth.activity.RebirthActivity;
import com.longcheng.volunteer.modular.mine.recovercash.activity.RecoverCashActivity;
import com.longcheng.volunteer.modular.mine.relationship.activity.RelationshipAccountAct;
import com.longcheng.volunteer.modular.mine.rewardcenters.activity.RewardCentersActivity;
import com.longcheng.volunteer.modular.mine.set.activity.ReceiveH5Activity;
import com.longcheng.volunteer.modular.mine.set.activity.SetActivity;
import com.longcheng.volunteer.modular.mine.set.activity.VolunteerH5Activity;
import com.longcheng.volunteer.modular.mine.signIn.activity.SignInH5Activity;
import com.longcheng.volunteer.modular.mine.starinstruction.StarInstructionAct;
import com.longcheng.volunteer.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.volunteer.modular.mine.userinfo.bean.EditDataBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.GetHomeInfoBean;
import com.longcheng.volunteer.modular.mine.userinfo.bean.GetHomeInfoDataBean;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.PriceUtils;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.glide.GlideDownLoadImage;
import com.longcheng.volunteer.utils.myview.CircleImageView;
import com.longcheng.volunteer.utils.myview.MyDialog;
import com.longcheng.volunteer.utils.myview.MyGridView;
import com.longcheng.volunteer.utils.myview.MyScrollView;
import com.longcheng.volunteer.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesUtil;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;

/**
 * @author MarkShuai
 * @name 我的页面
 * @time 2017/11/23 17:24
 */
public class MineFragment extends BaseFragmentMVP<MineContract.View, MinePresenterImp<MineContract.View>> implements MineContract.View {

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
    TextView mycenterLayoutActivatenergy;
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
    @BindView(R.id.usercenter_relay_myorder)
    LinearLayout usercenterRelayMyorder;
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

    @BindView(R.id.usercenter_relay_receiving)
    LinearLayout usercenter_relay_receiving;

    @BindView(R.id.layout_volunteerlist)
    LinearLayout layout_volunteerlist;


    @BindView(R.id.layout_functionstatus)
    LinearLayout layout_functionstatus;
    @BindView(R.id.tv_functionstatus)
    TextView tv_functionstatus;
    @BindView(R.id.layout_gongnengnlist)
    LinearLayout layout_gongnengnlist;

    @BindView(R.id.layout_gongnengngv)
    LinearLayout layout_gongnengngv;
    @BindView(R.id.gongnengn_gv1)
    MyGridView gongnengn_gv1;
    @BindView(R.id.gongnengn_gv2)
    MyGridView gongnengn_gv2;
    @BindView(R.id.mycenter_iv_jieqi)
    ImageView mycenter_iv_jieqi;

    @BindView(R.id.layout_commissioner)
    LinearLayout layout_commissioner;


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
    private String about_me_url;
    private String qimingaction_goods_id;
    boolean mAllowanceLayoutShow = false;
    PerfectInfoDialog mPerfectInfoDialog;

    @Override
    public int bindLayout() {
        return R.layout.fragment_mycenter;
    }

    @Override
    public void initView(View view) {
        pageTopTvName.setText("我家");
        pagetopIvLeft.setVisibility(View.INVISIBLE);
        pagetopIvLeft.setBackgroundResource(R.mipmap.usercenter_info_icon);
        pagetopIvRigth.setBackgroundResource(R.mipmap.usercenter_set_icon);
        usercenterRelayVolunteer.setOnClickListener(this);
        usercenterRelayDoctor.setOnClickListener(this);
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        mycenterIvHead.setOnClickListener(this);
        mycenterTvWithdraw.setOnClickListener(this);
        usercenterRelayAddress.setOnClickListener(this);
        mycenterLayoutActivatenergy.setOnClickListener(this);
        mycenterLayoutHumanity.setOnClickListener(this);
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
        usercenterRelayMyorder.setOnClickListener(this);
        ll_mycenter_wakeskb.setOnClickListener(this);
        ll_mycenter_sleepskb.setOnClickListener(this);
        llMycenterSleepennergy.setOnClickListener(this);
        llEnergy.setOnClickListener(this);
        usercenterRelaygoodluck.setOnClickListener(this);
        mycenterLayoutJieqisignin.setOnClickListener(this);
        mycenterRelatStars.setOnClickListener(this);
        mycenterLayoutsmallpusher.setOnClickListener(this);
        usercenter_layout_rebirth.setOnClickListener(this);
        usercenter_relay_changeinviter.setOnClickListener(this);
        layoutJiuzhen.setOnClickListener(this);
        usercenter_relay_receiving.setOnClickListener(this);
        layout_volunteerlist.setOnClickListener(this);
        layout_functionstatus.setOnClickListener(this);
        layout_commissioner.setOnClickListener(this);

        mycenterTvName.setFocusable(true);
        mycenterTvName.setFocusableInTouchMode(true);
        mycenterTvName.requestFocus();
        setFunctionType();
        showFunctionGVData();
        initUserInfo();
    }

    @Override
    public void doBusiness(Context mContext) {
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
    }

    /**
     * 设置常用功能显示类型
     */
    private void setFunctionType() {
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

        int isCommissionerIdentity = data.getIsCommissionerIdentity();
        if (isCommissionerIdentity == 0) {
        } else {
            FunctionGVlist1.add(new FunctionGVItemBean("我是特派员", R.id.layout_commissioner, R.mipmap.my_commissioner_icon));
        }
        int hasDiagnosticRecord = data.getHasDiagnosticRecord();
        if (hasDiagnosticRecord == 0) {
        } else {
            FunctionGVlist1.add(new FunctionGVItemBean("就诊记录", R.id.layout_jiuzhen, R.mipmap.my_treatment_icon));
        }
        FunctionGVlist1.add(new FunctionGVItemBean("我的恩人", R.id.usercenter_relay_myenren, R.mipmap.my_benefactor_icon));
        FunctionGVlist1.add(new FunctionGVItemBean("好运来", R.id.usercenter_relay_goodluck, R.mipmap.usercenter_goodluck_icon));
        FunctionGVlist1.add(new FunctionGVItemBean(HomeFragment.jieqi_name + "小推手", R.id.mycenter_layout_smallpusher, R.mipmap.my_smallpusher_icon));
        FunctionGVlist1.add(new FunctionGVItemBean("每日签到", R.id.mycenter_layout_jieqisignin, R.mipmap.my_signin_icon));
        if (data.issQimingSponsorUser()) {
            FunctionGVlist1.add(new FunctionGVItemBean("启明星", R.id.usercenter_relay_myStar, R.mipmap.my_star_icon));
        }
        int isPartymember = data.getIsPartymember();
        if (isPartymember == 0) {
        } else {
            FunctionGVlist1.add(new FunctionGVItemBean("志愿者列表", R.id.layout_volunteerlist, R.mipmap.my_volunteers_icon));
        }
        FunctionAdapter mFunctionAdapter1 = new FunctionAdapter(mContext, FunctionGVlist1);
        gongnengn_gv1.setAdapter(mFunctionAdapter1);


        FunctionGVlist2.add(new FunctionGVItemBean("一目了然", R.id.usercenter_relay_appexplanation, R.mipmap.usercenter_appexplanation_icon));
        FunctionGVlist2.add(new FunctionGVItemBean("地址管理", R.id.usercenter_relay_address, R.mipmap.usercenter_address_icon));
        FunctionGVlist2.add(new FunctionGVItemBean("修改密码", R.id.usercenter_relay_updatepw, R.mipmap.usercenter_updatepw_icon));
//        FunctionGVlist2.add(new FunctionGVItemBean("变更邀请人", R.id.usercenter_relay_changeinviter, R.mipmap.my_change_icon));
        //是否显示复活卡  0：不显示  1：显示
        int isResetCard = data.getIsResetCard();
        if (isResetCard == 0) {
        } else {
            FunctionGVlist2.add(new FunctionGVItemBean("重生卡", R.id.usercenter_layout_rebirth, R.mipmap.my_rebirth_icon));
        }
        FunctionAdapter mFunctionAdapter2 = new FunctionAdapter(mContext, FunctionGVlist2);
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

    private void viewClick(int viewId) {
        Intent intent;
        switch (viewId) {
            case R.id.pagetop_layout_rigth://设置
                intent = new Intent(mContext, SetActivity.class);
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

            case R.id.usercenter_relay_receiving://收付款
                intent = new Intent(mContext, ReceiveH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getReceiptCodeUrl());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_volunteerlist://志愿者申请列表
                intent = new Intent(mContext, VolunteerH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getPartymember_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_volunteer://志愿者
                int isVolunteerIdentity = data.getIsVolunteerIdentity();//是否是志愿者 0不是；1 是
                if (isVolunteerIdentity == 0) {
                    String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        intent = new Intent(mContext, VolunteerH5Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getBecome_volunteer_url());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    } else {
                        showNotCHODialog();
                    }
                } else {
                    intent = new Intent(mContext, VolunteerH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + data.getAlready_volunteer_url());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.usercenter_relay_doctor://坐堂医
                int isDoctorIdentity = data.getIsDoctorIdentity();//是否是坐堂医 0不是；1 是
                if (isDoctorIdentity == 0) {
                    String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        intent = new Intent(mContext, VolunteerH5Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("html_url", "" + data.getBecome_doctor_url());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    } else {
                        showNotCHODialog();
                    }
                } else {
                    intent = new Intent(mContext, VolunteerH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + data.getAlready_doctor_url());
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.layout_jiuzhen://就诊记录
                intent = new Intent(mContext, VolunteerH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getPatient_record_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_commissioner://我是特派员
                intent = new Intent(mContext, VolunteerH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data.getCommissioner_url());
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;

            case R.id.mycenter_layout_smallpusher://小推手
                ToastUtils.showToast("程序猿正在攻坚中…");
                break;
            case R.id.mycenter_iv_head://用户信息
                intent = new Intent(mContext, UserInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_allowancearrow://津贴
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
                intent = new Intent(mContext, RelationshipAccountAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_jieqisignin://签到
                signInGetInfo("signIn");
                break;
            case R.id.mycenter_tv_withdraw://提现
                if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1") && star_level > 0) {
                    if (isDirectorOrTeamLeader == 0 || (isDirectorOrTeamLeader == 1 && CurrentStartLevel >= 6)) {
                        intent = new Intent(mContext, RecoverCashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    } else {
                        ToastUtils.showToast("大队长及主任达到六星级才可提现");
                    }
                } else {
                    ToastUtils.showToast("成为CHO并连续30天达到一星级以上才可提现");
                }
                break;
            case R.id.usercenter_relay_address://地址管理
                intent = new Intent(mContext, AddressListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("receive_user_id", user_id);
                intent.putExtra("skipType", "MineFragment");
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_activatenergy://激活能量
                intent = new Intent(mContext, ActivatEnergyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;

            case R.id.usercenter_relay_myorder://订单
                intent = new Intent(mContext, OrderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_appexplanation://一目了然
                intent = new Intent(mContext, AbsolutelyclearAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_tv_bill://账单
                intent = new Intent(mContext, BillActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_updatepw://修改密码
                intent = new Intent(mContext, UpdatePwActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_zodiacdescripte://一字千金
                signInGetInfo("AWordOfGold");
                break;
            case R.id.usercenter_relay_myenren://恩人
                intent = new Intent(mContext, MyGraH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + HelpWithFragmentNew.myGratitudeUrl);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_reward://奖励中心
                intent = new Intent(mContext, RewardCentersActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_layout_invitationrecord://邀请记录
                intent = new Intent(mContext, InvitationAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_myStar://启明星
                if (data != null && data.issQimingSponsorUser() && !TextUtils.isEmpty(data.getQimingSponsorUserUrl())) {
                    intent = new Intent(mContext, PhosphorAct.class);
                    intent.putExtra("starturl", data.getQimingSponsorUserUrl());
                    intent.putExtra("action_goods_id", "" + qimingaction_goods_id);
                    intent.putExtra("qiming_user_id", "" + user_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.ll_mycenter_sleepskb:
                intent = new Intent(mContext, SleepSkbActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.ll_mycenter_wakeskb:
                intent = new Intent(mContext, WakeSkbActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.ll_mycenter_sleepennergy:
                intent = new Intent(mContext, SleepEngryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.ll_mycenter_ennergy://激活记录
                intent = new Intent(mContext, EngryRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_goodluck://好运来
                intent = new Intent(mContext, GoodLuckActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.mycenter_relat_stars://星级布局
                if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                    if (!TextUtils.isEmpty(star_level_illustrate_url)) {
                        intent = new Intent(mContext, StarInstructionAct.class);
                        intent.putExtra("starturl", "" + star_level_illustrate_url);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                } else {
                    intent = new Intent(mContext, UserInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.usercenter_layout_rebirth://重生卡
                intent = new Intent(mContext, RebirthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.usercenter_relay_changeinviter://变更邀请人
                intent = new Intent(mContext, ChangeInviterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            default:
                break;
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
        is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            if (oldUserPerfectInfoStatus_) {
                Intent intent = new Intent(mContext, SignInH5Activity.class);
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
                Intent intent = new Intent(mContext, AWordOfGoldAct.class);
                intent.putExtra("otherId", UserUtils.getUserId(mContext));
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
                    Intent intent = new Intent(mContext, UserInfoActivity.class);
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
        if (!TextUtils.isEmpty(user_id)) {
            mPresent.getUserHomeInfo(user_id);
        }
        Log.e("initUserInfo", "user_id=" + user_id + " \nis_cho= " + is_cho);
    }

    private void showInfoView() {
        String avatar = (String) SharedPreferencesHelper.get(mContext, "avatar", "");
        is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
        String phone = (String) SharedPreferencesHelper.get(mContext, "phone", "");
        user_id = (String) SharedPreferencesHelper.get(mContext, "user_id", "");
        String user_name = (String) SharedPreferencesHelper.get(mContext, "user_name", "");
        //1:是  ；0：不是
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
        } else {
            mycenterTvStars.setText(getString(R.string.becomeCHO));
            mycenter_tv_starsTiShi.setText(getString(R.string.becomeCHO_tishi));
            mycenter_iv_stars.setVisibility(View.GONE);
        }
        mycenterTvName.setText(user_name);
        String jieqi = HomeFragment.jieqi_name;
        if (TextUtils.isEmpty(jieqi)) {
            jieqi = "";
        }
        tv_jieqiname.setText(jieqi + "小推手");
        GlideDownLoadImage.getInstance().loadCircleHeadImageCenter(mContext, avatar, mycenterIvHead);
        GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mContext, data.getJieqi_pic(), mycenter_iv_jieqi);
        showFunctionGVData();
    }

    private void saveNewInfo(GetHomeInfoBean mGetHomeInfoBean) {
        SharedPreferencesHelper.put(mContext, "user_name", mGetHomeInfoBean.getUser_name());
        SharedPreferencesHelper.put(mContext, "avatar", mGetHomeInfoBean.getAvatar());
        SharedPreferencesHelper.put(mContext, "star_level", "" + CurrentStartLevel);
        SharedPreferencesHelper.put(mContext, "is_cho", is_cho);
        SharedPreferencesHelper.put(mContext, "birthday", mGetHomeInfoBean.getBirthday());
        SharedPreferencesHelper.put(mContext, "lunar_calendar_birthday", "" + mGetHomeInfoBean.getLunar_calendar_birthday());
        SharedPreferencesHelper.put(mContext, "political_status", "" + mGetHomeInfoBean.getPolitical_status());
        SharedPreferencesHelper.put(mContext, "is_military_service", "" + mGetHomeInfoBean.getIs_military_service());
        SharedPreferencesHelper.put(mContext, "pid", "" + mGetHomeInfoBean.getPid());
        SharedPreferencesHelper.put(mContext, "cid", "" + mGetHomeInfoBean.getCid());
        SharedPreferencesHelper.put(mContext, "aid", "" + mGetHomeInfoBean.getAid());
        SharedPreferencesHelper.put(mContext, "area", "" + mGetHomeInfoBean.getArea());

        String ability = mGetHomeInfoBean.getDisparity();
        String is_show = mGetHomeInfoBean.getIs_show();//ability生命能量 number次数
        String nextStartLevel = mGetHomeInfoBean.getNextStartLevel();
        String cont = "";
        if (!TextUtils.isEmpty(is_show) && is_show.endsWith("number")) {
            cont = ability + "</font>次祝福啦！";
        } else {
            cont = ability + "</font>个生命能量啦！";
        }
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            String showT = "亲，升到<font color=\"#ff443b\">" + nextStartLevel + "</font>星只需送出<font color=\"#ff443b\">" + cont;
            mycenter_tv_starsTiShi.setText(Html.fromHtml(showT));
            if (ability.equals("-1") && CurrentStartLevel == 9) {
                mycenter_tv_starsTiShi.setText("您就是最强王者！");
            }
            mycenterTvStars.setText(getString(R.string.xingJi) + CurrentStartLevel);
            mycenter_iv_stars.setVisibility(View.VISIBLE);
        }
    }

    MyDialog LevelDialog;
    int CurrentStartLevel;
    TextView tv_cont;

    public void dismissAllDialog() {
        if (toDoctorDialog != null && toDoctorDialog.isShowing()) {
            toDoctorDialog.dismiss();
        }
        if (LevelDialog != null && LevelDialog.isShowing()) {
            LevelDialog.dismiss();
        }
        if (actionDialog != null && actionDialog.isShowing()) {
            actionDialog.dismiss();
        }
        if (redBaoDialog != null && redBaoDialog.isShowing()) {
            redBaoDialog.dismiss();
        }
    }

    /**
     * 是否是坐堂医交押金成功回到我家提示弹层
     */
    public static boolean showDoctorDialogStatus = false;

    private void showAllDialog() {
        String loginStatus = (String) SharedPreferencesHelper.get(mContext, "loginStatus", "");
        if (!loginStatus.equals(ConstantManager.loginStatus)
                || BottomMenuActivity.position != BottomMenuActivity.tab_position_mine) {
            return;
        }
        //坐堂医支付成功弹层  1
        if (showDoctorDialogStatus || (toDoctorDialog != null && toDoctorDialog.isShowing())) {
            showDoctorDialogStatus = false;
            showDoctorDialog();
            return;
        }
        if (chatuserStarLevelId == 0) {
            //防止等级弹层提示后，黑屏后重新打开刷新没关闭问题
            if (toDoctorDialog != null && toDoctorDialog.isShowing()) {
                toDoctorDialog.dismiss();
            }
            if (LevelDialog != null && LevelDialog.isShowing()) {
                LevelDialog.dismiss();
            }

            //天才行动 3
            if (data.getIs_commonweal_activity() > 0) {
                showActionDialog();
                return;
            }
            //红包 4
            showRedBaoDialog();
            return;
        } else {
            //等级提示  2
            showLevelDialog();
        }
    }


    public void showLevelDialog() {
        //每次升星级都提示并传给后台已读
        mPresent.doStarLevelRemind(user_id);
        if (LevelDialog == null) {
            LevelDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_centerlevel);// 创建Dialog并设置样式主题
            LevelDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
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
                    Intent intent = new Intent(mContext, StarInstructionAct.class);
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


    MyDialog actionDialog;

    /**
     * 天才行动弹层
     */
    public void showActionDialog() {
        String loginStatus = (String) SharedPreferencesHelper.get(mContext, "loginStatus", "");
        if (BottomMenuActivity.position != BottomMenuActivity.tab_position_mine || !loginStatus.equals(ConstantManager.loginStatus)) {
            return;
        }
        if (actionDialog == null) {
            actionDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_hone_connon);// 创建Dialog并设置样式主题
            actionDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = actionDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            actionDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = actionDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4;
            p.height = (int) (p.width * 1.28);
            actionDialog.getWindow().setAttributes(p); //设置生效
            FrameLayout fram_bg = (FrameLayout) actionDialog.findViewById(R.id.fram_bg);
            fram_bg.setBackgroundResource(R.mipmap.my_action_icon);
            LinearLayout layout_cancel = (LinearLayout) actionDialog.findViewById(R.id.layout_cancel);
            TextView btn_upgrade = (TextView) actionDialog.findViewById(R.id.btn_upgrade);

            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionDialog.dismiss();
                }
            });
            btn_upgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionDialog.dismiss();
                    Intent intent = new Intent(mContext, ActionH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("kn_url", "" + data.getCommonweal_activity_url());
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
        if (isUnopenedRedPackage == 0) {
            if (redBaoDialog != null && redBaoDialog.isShowing()) {
                redBaoDialog.dismiss();
            }
            return;
        }
        //用户首页是否有等级弹窗 0不显示；1显示
        if (redBaoDialog == null) {
            redBaoDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_centeropenredbao);// 创建Dialog并设置样式主题
            redBaoDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
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
            relat_redbao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redBaoDialog.dismiss();
                }
            });
            iv_xingji.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redBaoDialog.dismiss();
                    Intent intent = new Intent(mContext, GoodLuckActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            });
        } else {
            redBaoDialog.show();
        }
    }

    MyDialog notCHODialog;

    /**
     * 申请志愿者 不是cho弹层
     */
    public void showNotCHODialog() {
        if (notCHODialog == null) {
            notCHODialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_mycenter_notcho);// 创建Dialog并设置样式主题
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

    MyDialog toDoctorDialog;

    /**
     * 成为坐堂医交押金成功弹层
     */
    public void showDoctorDialog() {
        if (toDoctorDialog == null) {
            toDoctorDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_mycenter_todoctor);// 创建Dialog并设置样式主题
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
        //是否显示复活卡  0：不显示  1：显示
        int isResetCard = mGetHomeInfoBean.getIsResetCard();
        if (isResetCard == 0) {
            usercenter_layout_rebirth.setVisibility(View.GONE);
        } else {
            usercenter_layout_rebirth.setVisibility(View.VISIBLE);
        }


        String shoukangyuan = mGetHomeInfoBean.getShoukangyuan();
        mycentertopTvAwakeskb.setText(shoukangyuan);
        String dongjie = mGetHomeInfoBean.getDongjie();
        mycentertopTvSleepskb.setText(dongjie);
        String sleepengry = mGetHomeInfoBean.getFrost_ability();
        sleepengry = PriceUtils.getInstance().seePrice(sleepengry);
        mycentertopTvSleeplifeenergy.setText(sleepengry);
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
        UserUtils.saveWXToken(mContext, mGetHomeInfoBean.getWxToken());
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
}
