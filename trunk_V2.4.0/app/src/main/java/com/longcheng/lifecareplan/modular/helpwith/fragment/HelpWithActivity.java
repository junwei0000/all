package com.longcheng.lifecareplan.modular.helpwith.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivityMVP;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.helpwith.adapter.HelpWithBottomAdapter;
import com.longcheng.lifecareplan.modular.helpwith.adapter.HelpWithTopAdapter;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexDataBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpIndexItemBean;
import com.longcheng.lifecareplan.modular.helpwith.bean.HelpWithInfo;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.activity.MyDeH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.activity.MyGraH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.myfamily.activity.MyFamilyActivity;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.lifecareplan.modular.mine.fragment.MineFragment;
import com.longcheng.lifecareplan.modular.mine.relationship.activity.RelationshipAccountAct;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyScrollView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 快捷中心
 */
public class HelpWithActivity extends BaseActivityMVP<HelpWithContract.View, HelpWithPresenterImp<HelpWithContract.View>> implements HelpWithContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetop_layout_left;
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.helpWith_gvtop)
    MyGridView helpWithGvtop;
    @BindView(R.id.helpWith_gvbottom)
    MyGridView helpWithGvbottom;
    @BindView(R.id.helpWith_sv)
    MyScrollView helpWithSv;

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
    private String lifeUrl, lifeBasicApplyUrl, lifeUrlWorld, become_volunteer_url;

    private int isVolunteer;

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                doFinish();
                break;
            case R.id.layout_golf:
                intent = new Intent(mActivity, AWordOfGoldAct.class);
                intent.putExtra("otherId", UserUtils.getUserId(mActivity));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                break;
            case R.id.tv_tohelp:
                if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1") && zangfus != null && zangfus.size() > 0) {
                    Intent intents = new Intent();
                    intents.setAction(ConstantManager.MAINMENU_ACTION);
                    intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                    LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                    ActivityManager.getScreenManager().popAllActivityOnlyMain();
                } else {
                    intent = new Intent(mActivity, UserInfoActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                }
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
        return R.layout.fragment_helpwithnew;
    }

    @Override
    public void initView(View view) {
        setOrChangeTranslucentColor(toolbar, null);
    }

    @Override
    public void setListener() {
        pagetop_layout_left.setOnClickListener(this);
        tvTohelp.setOnClickListener(this);
        layoutGolf.setOnClickListener(this);
        pageTopTvName.setText(getString(R.string.bottom_helpwith));
        helpWithGvbottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                if (position == 0) {
                    //我的恩人
                    intent = new Intent(mActivity, MyGraH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + myGratitudeUrl);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                } else if (position == 1) {
                    //我的奉献
                    intent = new Intent(mActivity, MyDeH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", "" + myDedicationUrl);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                } else if (position == 2) {
                    //激活能量
                    intent = new Intent(mActivity, ActivatEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                } else if (position == 3) {
                    //我的家人
                    intent = new Intent(mActivity, MyFamilyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                } else if (position == 4) {
                    //1:是  ；0：不是
                    String is_cho = (String) SharedPreferencesHelper.get(mActivity, "is_cho", "");
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        //人情账
                        intent = new Intent(mActivity, RelationshipAccountAct.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    } else {
                        //成为CHO
                        intent = new Intent(mActivity, UserInfoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    }

                }
            }
        });
    }

    @Override
    public void initDataAfter() {
        initInfo();
    }

    List<String> solarTermsEnsImg;

    public void initInfo() {
        showCont();
        mPresent.getHelpInfo(UserUtils.getUserId(mActivity));
    }

    private void showCont() {
        if (helpWithGvtop == null) {
            return;
        }
        List<HelpWithInfo> mList = new ArrayList();
        mList.add(new HelpWithInfo("生命能量互祝", "申请", R.color.white, R.mipmap.wisheach_icon_energy, ""));
        mList.add(new HelpWithInfo("生活方式互祝", "申请", R.color.white, R.mipmap.wisheach_icon_life, ""));
        mList.add(new HelpWithInfo("24节气福券", "", R.color.white, R.mipmap.wisheach_icon_baoz, automationHelpUrl));
        mList.add(new HelpWithInfo("天下无癌", "", R.color.white, R.mipmap.wisheach_icon_kangno, HomeFragment.kn_url));
        mList.add(new HelpWithInfo("生活保障互祝", "申请", R.color.bluebg, R.mipmap.wisheach_icon_toapplyfor, lifeUrl, lifeBasicApplyUrl));
        mList.add(new HelpWithInfo("天下无债", "", R.color.bluebg, R.mipmap.wisheach_icon_toapplyfor, lifeUrlWorld));
        HelpWithTopAdapter mHelpWithTopAdapter = new HelpWithTopAdapter(mActivity, mList, solarTermsEnsImg);
        mHelpWithTopAdapter.initHandle(mHandler, lifeBasicApply);
        helpWithGvtop.setAdapter(mHelpWithTopAdapter);

        List<HelpWithInfo> mBottomList = new ArrayList();
        mBottomList.add(new HelpWithInfo("我的恩人", R.mipmap.wisheach_icon_benefactor));
        mBottomList.add(new HelpWithInfo("我的奉献", R.mipmap.wisheach_icon_dedication));
        mBottomList.add(new HelpWithInfo("激活能量", R.mipmap.wisheach_icon_theactivation));
        mBottomList.add(new HelpWithInfo("我的家人", R.mipmap.wisheach_icon_family));
        //1:是  ；0：不是
        String is_cho = (String) SharedPreferencesHelper.get(mActivity, "is_cho", "");
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            layoutGolf.setVisibility(View.VISIBLE);
            mBottomList.add(new HelpWithInfo("人情账", R.mipmap.wisheach_icon_love));
        } else {
            layoutGolf.setVisibility(View.INVISIBLE);
            mBottomList.add(new HelpWithInfo("成为CHO", R.mipmap.wisheach_icon_cho));
        }
        HelpWithBottomAdapter mHelpWithBottomAdapter = new HelpWithBottomAdapter(mActivity, mBottomList, myBlessHelpCount, blessMeHelpCount);
        helpWithGvbottom.setAdapter(mHelpWithBottomAdapter);
        helpWithGvbottom.setNumColumns(5);
    }

    private final int lifeBasicApply = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case lifeBasicApply:
                    if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
                        if (isVolunteer == 0) {
                            showVolunteerDialog();
                        } else {
                            Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("html_url", "" + lifeBasicApplyUrl);
                            startActivity(intent);
                        }
                    } else {
                        ((MineFragment) BottomMenuActivity.fragmentList.get(BottomMenuActivity.tab_position_mine)).showNotCHODialog();
                    }
                    break;
            }
        }
    };

    MyDialog VolunteerDialog;

    /**
     * 康农成为志愿者
     */
    public void showVolunteerDialog() {
        if (VolunteerDialog == null) {
            VolunteerDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_malldetail_connon);// 创建Dialog并设置样式主题
            VolunteerDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = VolunteerDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            VolunteerDialog.show();
            WindowManager m = mActivity.getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = VolunteerDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4; //宽度设置为屏幕
            VolunteerDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout layout_cancel = VolunteerDialog.findViewById(R.id.layout_cancel);
            TextView btn_gohelp = VolunteerDialog.findViewById(R.id.btn_gohelp);
            TextView btn_jihuo = VolunteerDialog.findViewById(R.id.btn_jihuo);
            TextView tv_title = VolunteerDialog.findViewById(R.id.tv_title);
            tv_title.setText("此行动只限志愿者申请");
            tv_title.setTextColor(getResources().getColor(R.color.red));
            btn_gohelp.setVisibility(View.GONE);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VolunteerDialog.dismiss();
                }
            });
            btn_gohelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VolunteerDialog.dismiss();
                }
            });
            btn_jihuo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("html_url", become_volunteer_url);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
                    VolunteerDialog.dismiss();
                }
            });
        } else {
            VolunteerDialog.show();
        }
    }

    @Override
    protected HelpWithPresenterImp<HelpWithContract.View> createPresent() {
        return new HelpWithPresenterImp<>(this);
    }

    @Override
    public void showDialog() {
        LoadingDialogAnim.show(mContext);
    }

    @Override
    public void dismissDialog() {
        LoadingDialogAnim.dismiss(mContext);
    }


    @Override
    public void getHelpIndexSuccess(HelpIndexDataBean mHomeDataBean) {
        String status = mHomeDataBean.getStatus();
        if (status.equals("200")) {
            HelpIndexAfterBean mHelpIndexAfterBean = mHomeDataBean.getData();

            automationHelpUrl = mHelpIndexAfterBean.getAutomationHelpUrl();
            myDedicationUrl = mHelpIndexAfterBean.getMyDedicationUrl();
            myGratitudeUrl = mHelpIndexAfterBean.getMyGratitudeUrl();
            lifeUrlWorld = mHelpIndexAfterBean.getLifeUrlWorld();
            lifeUrl = mHelpIndexAfterBean.getLifeUrl();
            isVolunteer = mHelpIndexAfterBean.getIsVolunteer();
            become_volunteer_url = mHelpIndexAfterBean.getBecome_volunteer_url();
            lifeBasicApplyUrl = mHelpIndexAfterBean.getLifeBasicApplyUrl();
            solarTermsEnsImg = mHelpIndexAfterBean.getSolarTermsEnsImg();

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

            if (solarTermsEnsImg != null && solarTermsEnsImg.size() >= 7) {
                int width = (int) (DensityUtil.screenWith(mActivity) * 2.7 / 7);
                int imghei = (int) (width * 0.6);
                iv_tohelpimg.setLayoutParams(new LinearLayout.LayoutParams(width, imghei));
                GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(mActivity, solarTermsEnsImg.get(6), iv_tohelpimg);
            }
        }
    }

    @Override
    public void ListError() {
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
