package com.longcheng.lifecareplan.modular.helpwith.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.helpwith.adapter.HelpWithBottomAdapter;
import com.longcheng.lifecareplan.modular.helpwith.adapter.HelpWithTopAdapter;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.autohelp.activity.AutoHelpH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexDataBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexItemBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpWithInfo;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.activity.MyDeH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.activity.MyDedicationActivity;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.activity.MyGraH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.activity.MyGratitudeActivity;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.activity.MyFamilyActivity;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.lifecareplan.modular.mine.relationship.activity.RelationshipAccountAct;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：互助页面
 */

public class HelpWithFragmentNew extends BaseFragmentMVP<HelpWithContract.View, HelpWithPresenterImp<HelpWithContract.View>> implements HelpWithContract.View {

    @BindView(R.id.pagetop_iv_left)
    ImageView pagetop_iv_left;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.helpWith_gvtop)
    MyGridView helpWithGvtop;
    @BindView(R.id.helpWith_gvbottom)
    MyGridView helpWithGvbottom;
    @BindView(R.id.helpWith_sv)
    MyScrollView helpWithSv;


    @BindView(R.id.layout_autohelp)
    RelativeLayout layout_autohelp;
    @BindView(R.id.iv_autohelpimg)
    ImageView iv_autohelpimg;
    @BindView(R.id.tv_autohelpNum)
    TextView tv_autohelpNum;


    @BindView(R.id.layout_golf)
    LinearLayout layoutGolf;
    @BindView(R.id.tv_showname)
    TextView tvShowname;
    @BindView(R.id.tv_showcont)
    TextView tvShowcont;
    @BindView(R.id.tv_tohelp)
    TextView tvTohelp;
    @BindView(R.id.iv_tohelpimg)
    ImageView iv_tohelpimg;
    private String is_cho;
    private String myBlessHelpCount = "0", blessMeHelpCount = "0";
    public static String automationHelpUrl, myDedicationUrl, myGratitudeUrl;
    private List<String> zangfus;
    private String lifeUrl;

    @Override
    public int bindLayout() {
        return R.layout.fragment_helpwithnew;
    }

    @Override
    public void initView(View view) {
        tvTohelp.setOnClickListener(this);
        layoutGolf.setOnClickListener(this);
        layout_autohelp.setOnClickListener(this);
        pagetop_iv_left.setVisibility(View.GONE);
        pageTopTvName.setText(getString(R.string.bottom_helpwith));
        helpWithGvbottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (position == 0) {
                    //我的恩人
                    intent = new Intent(mContext, MyGraH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + myGratitudeUrl);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 1) {
                    //我的奉献
                    intent = new Intent(mContext, MyDeH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + myDedicationUrl);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 2) {
                    //激活能量
                    intent = new Intent(mContext, ActivatEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 3) {
                    //我的家人
                    intent = new Intent(mContext, MyFamilyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                } else if (position == 4) {
                    //1:是  ；0：不是
                    String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        //人情账
                        intent = new Intent(mContext, RelationshipAccountAct.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    } else {
                        //成为CHO
                        intent = new Intent(mContext, UserInfoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }

                }
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        initInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume", "onResume");
        initInfo();
    }

    List<String> solarTermsEnsImg;

    public void initInfo() {
        showCont();
        mPresent.getHelpInfo(UserUtils.getUserId(mContext));
    }

    private void showCont() {
        List<HelpWithInfo> mList = new ArrayList();
        mList.add(new HelpWithInfo("生命能量互祝", "申请", R.mipmap.wisheach_icon_energy, ""));
        mList.add(new HelpWithInfo("生活方式互祝", "申请", R.mipmap.wisheach_icon_life, ""));
        mList.add(new HelpWithInfo("生活保障互祝", "", R.mipmap.wisheach_icon_baoz, lifeUrl));
        mList.add(new HelpWithInfo("康农工程互祝", "", R.mipmap.wisheach_icon_toapplyfor, HomeFragment.kn_url));
        HelpWithTopAdapter mHelpWithTopAdapter = new HelpWithTopAdapter(mContext, mList, solarTermsEnsImg);
        helpWithGvtop.setAdapter(mHelpWithTopAdapter);

        List<HelpWithInfo> mBottomList = new ArrayList();
        mBottomList.add(new HelpWithInfo("我的恩人", R.mipmap.wisheach_icon_benefactor));
        mBottomList.add(new HelpWithInfo("我的奉献", R.mipmap.wisheach_icon_dedication));
        mBottomList.add(new HelpWithInfo("激活能量", R.mipmap.wisheach_icon_theactivation));
        mBottomList.add(new HelpWithInfo("我的家人", R.mipmap.wisheach_icon_family));
        //1:是  ；0：不是
        String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            layoutGolf.setVisibility(View.VISIBLE);
            mBottomList.add(new HelpWithInfo("人情账", R.mipmap.wisheach_icon_love));
        } else {
            layoutGolf.setVisibility(View.INVISIBLE);
            mBottomList.add(new HelpWithInfo("成为CHO", R.mipmap.wisheach_icon_cho));
        }
        HelpWithBottomAdapter mHelpWithBottomAdapter = new HelpWithBottomAdapter(mContext, mBottomList, myBlessHelpCount, blessMeHelpCount);
        helpWithGvbottom.setAdapter(mHelpWithBottomAdapter);
        helpWithGvbottom.setNumColumns(5);
    }


    @Override
    public void widgetClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.layout_autohelp:
                //智能互祝
                intent = new Intent(mContext, AutoHelpH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + automationHelpUrl);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.layout_golf:
                intent = new Intent(mContext, AWordOfGoldAct.class);
                intent.putExtra("otherId", UserUtils.getUserId(mContext));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            case R.id.tv_tohelp:
                if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1") && zangfus != null && zangfus.size() > 0) {
                    Intent intents = new Intent();
                    intents.setAction(ConstantManager.MAINMENU_ACTION);
                    intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                    LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                    ActivityManager.getScreenManager().popAllActivityOnlyMain();
                } else {
                    intent = new Intent(mContext, UserInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected HelpWithPresenterImp<HelpWithContract.View> createPresent() {
        return new HelpWithPresenterImp<>(this);
    }

    @Override
    public void getHelpIndexSuccess(HelpIndexDataBean mHomeDataBean) {
        String status = mHomeDataBean.getStatus();
        if (status.equals("200")) {
            HelpIndexAfterBean mHelpIndexAfterBean = mHomeDataBean.getData();

            automationHelpUrl = mHelpIndexAfterBean.getAutomationHelpUrl();
            myDedicationUrl = mHelpIndexAfterBean.getMyDedicationUrl();
            myGratitudeUrl = mHelpIndexAfterBean.getMyGratitudeUrl();
            lifeUrl = mHelpIndexAfterBean.getLifeUrl();
            solarTermsEnsImg = mHelpIndexAfterBean.getSolarTermsEnsImg();
            int isStartAutoHelp = mHelpIndexAfterBean.getIsStartAutoHelp();
            String open;
            if (isStartAutoHelp == 0) {//未开启智能互祝
                open = "未开启";
            } else {
                String autoHelpNumberTotal = mHelpIndexAfterBean.getAutoHelpNumberTotal();
                open = "今日帮您送出" + autoHelpNumberTotal + "次祝福";
            }
            tv_autohelpNum.setText(open);
            if (solarTermsEnsImg != null && solarTermsEnsImg.size() >= 5) {
                int imgwidth = (int) ((layout_autohelp.getMeasuredHeight() - 10) * 1.68);
                iv_autohelpimg.setLayoutParams(new LinearLayout.LayoutParams(imgwidth, layout_autohelp.getMeasuredHeight() - 10));
                GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mContext, solarTermsEnsImg.get(4), iv_autohelpimg);
            }

            myBlessHelpCount = mHelpIndexAfterBean.getMyBlessHelpCount();
            blessMeHelpCount = mHelpIndexAfterBean.getBlessMeHelpCount();
            showCont();
            zangfus = mHelpIndexAfterBean.getZangfus();
            HelpIndexItemBean mUserInfo = mHelpIndexAfterBean.getUser();
            is_cho = mUserInfo.getIs_cho();
            String shoevon = "";
            if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1") && zangfus != null && zangfus.size() > 0) {
                for (String str : zangfus) {
                    shoevon = shoevon + str + "\n";
                }
                layoutGolf.setVisibility(View.VISIBLE);
                tvTohelp.setText("立即申请");
            } else {
                layoutGolf.setVisibility(View.INVISIBLE);
                shoevon = "请完善个人资料，体验24节气定制养生指导";
                tvTohelp.setText("完善资料");
            }
            tvShowname.setText("“24节气养生指导”温馨提示\n" + mUserInfo.getUser_name() + "：");
            tvShowcont.setText(shoevon);

            if (solarTermsEnsImg != null && solarTermsEnsImg.size() >= 6) {
                int width = (int) (DensityUtil.screenWith(mContext) * 2.7 / 7);
                int imghei = (int) (width * 0.6);
                iv_tohelpimg.setLayoutParams(new LinearLayout.LayoutParams(width, imghei));
                GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mContext, solarTermsEnsImg.get(5), iv_tohelpimg);
            }
        }
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


}
