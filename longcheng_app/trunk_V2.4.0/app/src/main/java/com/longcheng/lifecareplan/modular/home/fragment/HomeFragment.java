package com.longcheng.lifecareplan.modular.home.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseFragmentMVP;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ActionDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.home.adapter.ActionAdapter;
import com.longcheng.lifecareplan.modular.home.adapter.DedicationAdapter;
import com.longcheng.lifecareplan.modular.home.adapter.HealthAdapter;
import com.longcheng.lifecareplan.modular.home.adapter.HomeHotPushAdapter;
import com.longcheng.lifecareplan.modular.home.adapter.IconAdapter;
import com.longcheng.lifecareplan.modular.home.adapter.TopAdapter;
import com.longcheng.lifecareplan.modular.home.bean.HomeAfterBean;
import com.longcheng.lifecareplan.modular.home.bean.HomeDataBean;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.home.bean.PoActionListDataBean;
import com.longcheng.lifecareplan.modular.home.bean.QuickTeamDataBean;
import com.longcheng.lifecareplan.modular.home.commune.activity.CommuneJoinListActivity;
import com.longcheng.lifecareplan.modular.home.commune.activity.CommuneMineActivity;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.activity.HealthyDeliveryAct;
import com.longcheng.lifecareplan.modular.home.invitefriends.activity.InviteFriendsActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.VideoMenuActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.LoginThirdSetPwActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.DaiFuActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.TiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.message.activity.MessageActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.cz.PionCZRecordActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.recovercash.daifu.activity.tx.PionTiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.set.version.AppUpdate;
import com.longcheng.lifecareplan.push.jpush.message.PairingUtils;
import com.longcheng.lifecareplan.utils.CleanMessageUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ListUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.ScrowUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.myview.MyDialog;
import com.longcheng.lifecareplan.utils.myview.MyGridView;
import com.longcheng.lifecareplan.utils.myview.MyListView;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.MySharedPreferences;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesUtil;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;
import com.longcheng.lifecareplan.zxing.activity.MipcaCaptureActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：MarkShuai
 * 时间：2017/11/23 15:19
 * 邮箱：MarkShuai@163.com
 * 意图：首页
 */

public class HomeFragment extends BaseFragmentMVP<HomeContract.View, HomePresenterImp<HomeContract.View>> implements HomeContract.View {
    @BindView(R.id.pagetop_iv_left)
    ImageView pagetopIvLeft;
    @BindView(R.id.pagetop_layout_left)
    LinearLayout pagetopLayoutLeft;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.main_sv)
    PullToRefreshScrollView main_sv;

    @BindView(R.id.vp)
    ViewFlipper vp;

    @BindView(R.id.gv_icon)
    MyGridView gv_icon;

    @BindView(R.id.mainhealth_tv_more)
    TextView mainhealth_tv_more;

    @BindView(R.id.homededi_vp_dedication)
    ViewPager homedediVpDedication;

    @BindView(R.id.mainaction_tv_more)
    TextView mainactionLayoutMore;
    @BindView(R.id.gv_Action)
    MyGridView gv_Action;

    @BindView(R.id.mainhotpush_tv_more)
    TextView mainhotpushLayoutMore;
    @BindView(R.id.mainhotpush_lv)
    MyListView mainhotpushLv;
    @BindView(R.id.frame_bottom)
    LinearLayout frame_bottom;
    @BindView(R.id.layout_notdate)
    LinearLayout llNodata;
    @BindView(R.id.not_date_img)
    ImageView ivNodata;
    @BindView(R.id.not_date_cont)
    TextView tvNoDataContent;
    @BindView(R.id.not_date_cont_title)
    TextView tvNoDataTitle;
    @BindView(R.id.not_date_btn)
    TextView btnNoData;


    @BindView(R.id.homededi_vp_health)
    ViewPager homedediVpHealth;

    List<HomeItemBean> msg;
    List<HomeItemBean> actions, icons;
    @BindView(R.id.homededi_vp_top)
    ViewPager homedediVpTop;

    public static String jieqi_name = "", current_jieqi = "";
    public static String kn_url = "", activity_url = "", my_gratitude_url = "", my_dedication_url = "";
    @BindView(R.id.tv_newtitle)
    TextView tvNewtitle;
    @BindView(R.id.tv_drawable1)
    TextView tvDrawable1;
    @BindView(R.id.tv_drawable2)
    TextView tvDrawable2;
    @BindView(R.id.tv_drawable3)
    TextView tvDrawable3;
    @BindView(R.id.tv_drawable4)
    TextView tvDrawable4;
    @BindView(R.id.top_tv_num)
    TextView top_tv_num;


    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }

    /**
     * 是否有未讀消息
     */
    public void haveNotReadMsg() {
        if (pagetopIvLeft != null) {
            pagetopIvLeft.setBackgroundResource(R.mipmap.usercenter_notinfo_icon);
            top_tv_num.setVisibility(View.INVISIBLE);
            String loginStatus = (String) SharedPreferencesHelper.get(mActivity, "loginStatus", "");
            if (loginStatus.equals(ConstantManager.loginStatus)) {
                boolean haveNotReadMsg = (boolean) SharedPreferencesHelper.get(mActivity, "haveNotReadMsgStatus", false);
                if (haveNotReadMsg) {
                    int messagecount = (int) SharedPreferencesHelper.get(mActivity, "messagecount", 0);
                    top_tv_num.setVisibility(View.VISIBLE);
                    top_tv_num.setText("" + messagecount);
//                    pagetopIvLeft.setBackgroundResource(R.mipmap.usercenter_info_icon);
                }
            }
        }
    }

    AppUpdate appUpdate;

    /**
     *
     */
    public void reLoadData() {
        ColorChangeByTime.getInstance().changeDrawable(mActivity, tvDrawable1);
        ColorChangeByTime.getInstance().changeDrawable(mActivity, tvDrawable2);
        ColorChangeByTime.getInstance().changeDrawable(mActivity, tvDrawable3);
        ColorChangeByTime.getInstance().changeDrawable(mActivity, tvDrawable4);
        if (appUpdate == null) {
            appUpdate = new AppUpdate();
        }
        appUpdate.startUpdateAsy(mActivity, "Home");
        if (mPresent != null) {
            haveNotReadMsg();
            mPresent.setListViewData();
        }
    }


    /**
     * 第一次跳转登录页面，返回设置
     */
    private void setNoLoginBack() {
        initContext();
        PriceUtils.getInstance().mbackgroundStatus = false;
        //        String loginStatus = (String) SharedPreferencesHelper.get(mActivity, "loginStatus", "");
//        if (isFirstComIn == 1) {//标记第一次未登录跳转快速组队页
//            if (!loginStatus.equals(ConstantManager.loginStatus)) {
//                isFirstComIn = 2;//未登录不再显示跳转
//            } else {//登录初始化0显示跳转
//                isFirstComIn = 0;
//            }
//        }
    }

    /**
     * 第一次缓存开启页面
     */
    boolean initshowChache = true;

    @Override
    public void initView(View view) {
        initContext();
        showChache(false);
        try {
            String d = CleanMessageUtil.getTotalCacheSize(mActivity);
            Log.e("CleanMessageUtil", d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pagetopLayoutLeft.getBackground().setAlpha(60);
        pagetopLayoutRigth.getBackground().setAlpha(60);
        pagetopLayoutRigth.setVisibility(View.GONE);
        pagetopLayoutLeft.setOnClickListener(this);
        mainhealth_tv_more.setOnClickListener(this);
        mainhotpushLayoutMore.setOnClickListener(this);
        mainactionLayoutMore.setOnClickListener(this);
        int width = DensityUtil.screenWith(mActivity);
        int height = (int) (width * 0.62);
        homedediVpTop.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        pagetopLayoutRigth.setFocusable(true);
        pagetopLayoutRigth.setFocusableInTouchMode(true);
        pagetopLayoutRigth.requestFocus();
        ScrowUtil.ScrollViewDownConfig(main_sv);
        main_sv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (main_sv != null) {
                    reLoadData();
                }
            }
        });
        mainhotpushLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (msg != null && msg.size() > 0) {
                    SharedPreferencesHelper.put(mActivity, "msg_id", msg.get(position).getId());
                    if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToEnergyDetail)) {
                        Intent intent = new Intent(mActivity, DetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("msg_id", msg.get(position).getId());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                }
            }
        });
        gv_Action.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (actions != null && actions.size() > 0) {
                    if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                        Intent intent = new Intent(mActivity, ActionDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("goods_id", actions.get(position).getGoods_id());
                        startActivity(intent);
                        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                    }
                }
            }
        });
        gv_icon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (icons != null && icons.size() > 0) {
                    int sort = icons.get(position).getSort();
                    Intent intent;
                    if (sort == 1) {
                        if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                            //绑定手机号才能邀请亲友
                            String phone = UserUtils.getUserPhone(mActivity);
                            if (TextUtils.isEmpty(phone)) {
                                intent = new Intent(mActivity, LoginThirdSetPwActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_PHONE);
                                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                            } else {
                                intent = new Intent(mActivity, InviteFriendsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                            }
                        }
                    } else if (sort == 2) {
                        if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                            intent = new Intent(mActivity, BaoZhangActitvty.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("html_url", "" + kn_url);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    } else if (sort == 3) {
                        if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToCommuneJoinList)) {
                            boolean haveCommune = (boolean) SharedPreferencesHelper.get(mActivity, "haveCommune", false);
                            if (haveCommune) {
                                intent = new Intent(mActivity, CommuneMineActivity.class);
                            } else {
                                intent = new Intent(mActivity, CommuneJoinListActivity.class);
                            }
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    } else if (sort == 4) {
                        if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHelpWithEnergy)) {
                            Intent intents = new Intent(mActivity, BaoZhangActitvty.class);
                            intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intents.putExtra("html_url", "" + HomeFragment.activity_url);
                            startActivity(intents);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intents, mActivity);
                        }
                    } else if (sort == 5) {
                        if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                            //直播
                            intent = new Intent(mActivity, VideoMenuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("skipType", "click");
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    }
                }
            }
        });
    }


    @Override
    public void doBusiness(Context mActivity) {

    }

    @Override
    public void widgetClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToMessage)) {
                    intent = new Intent(mActivity, MessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.pagetop_layout_rigth:
                if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                    intent = new Intent(mActivity, MipcaCaptureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.mainaction_tv_more://热门行动
                if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                    intent = new Intent(mActivity, PopularActionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.mainhotpush_tv_more://热推互祝
                SharedPreferencesHelper.put(mActivity, "skiptype", "HomeFragment");
                if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHelpWithEnergy)) {
                    intent = new Intent(mActivity, HelpWithEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("skiptype", "HomeFragment");
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.mainhealth_tv_more:// 健康速递 查看更多
                intent = new Intent(mActivity, HealthyDeliveryAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                break;
            default:
                break;
        }
    }


    @Override
    protected HomePresenterImp<HomeContract.View> createPresent() {
        return new HomePresenterImp<>(mActivity, this);
    }


    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void ListSuccess(HomeDataBean responseBean) {
        RefreshComplete();
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            HomeAfterBean mHomeAfterBean = responseBean.getData();
            setLoadData(mHomeAfterBean);
        }
    }


    public void dismissAllDialog() {
        if (CononDialog != null && CononDialog.isShowing()) {
            CononDialog.dismiss();
        }
        if (mUpdaDialog != null && mUpdaDialog.isShowing()) {
            mUpdaDialog.dismiss();
        }
        if (OpenNotificationDialog != null && OpenNotificationDialog.isShowing()) {
            OpenNotificationDialog.dismiss();
        }
        if (JSDialog != null && JSDialog.isShowing()) {
            JSDialog.dismiss();
        }
        if (QGDialog != null && QGDialog.isShowing()) {
            QGDialog.dismiss();
        }
        dismissTiXian();
    }

    /**
     * 首页 是否开启通知
     */
    MyDialog OpenNotificationDialog;

    private void showOpenNotificationWindow() {
        if (CononDialog != null && CononDialog.isShowing()) {
            CononDialog.dismiss();
        }
        if (OpenNotificationDialog != null && OpenNotificationDialog.isShowing()) {
            return;
        }
        OpenNotificationDialog = new MyDialog(mActivity, R.style.dialog, R.layout.dialog_openotification);// 创建Dialog并设置样式主题
        OpenNotificationDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
        Window window = OpenNotificationDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        OpenNotificationDialog.show();
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
        WindowManager.LayoutParams p = OpenNotificationDialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = d.getWidth() * 5 / 6; //宽度设置为屏幕
        OpenNotificationDialog.getWindow().setAttributes(p); //设置生效

        LinearLayout layout_cancel = OpenNotificationDialog.findViewById(R.id.layout_cancel);
        TextView btn_ok = OpenNotificationDialog.findViewById(R.id.btn_ok);
        layout_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenNotificationDialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenNotificationDialog.dismiss();
                String pkg = mActivity.getApplicationContext().getPackageName();
                // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", pkg, null);
                intent.setData(uri);
                startActivity(intent);
            }
        });

    }

    MyDialog mUpdaDialog;
    BridgeWebView tv_dialog_content;

    /**
     * 是否显示更新内容弹层
     */
    public void showUpdaDialog() {
        if (getActivity() == null) {
            return;
        }
        if (mUpdaDialog == null) {
            mUpdaDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_updat);// 创建Dialog并设置样式主题
            mUpdaDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mUpdaDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mUpdaDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mUpdaDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4;
            mUpdaDialog.getWindow().setAttributes(p); //设置生效
            LinearLayout fram_bg = mUpdaDialog.findViewById(R.id.fram_bg);
            fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.433)));
            LinearLayout layout_cancel = mUpdaDialog.findViewById(R.id.layout_cancel);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUpdaDialog.dismiss();
                }
            });
            tv_dialog_content = mUpdaDialog.findViewById(R.id.tv_dialog_content);
            ConfigUtils.getINSTANCE().setInitWebView(tv_dialog_content, mContext);
        } else {
            if (getActivity() != null && !getActivity().isFinishing()) {
                mUpdaDialog.show();
            }
        }
        Log.e("showUpdaDialog", "\n" + display_note);
        uphandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ConfigUtils.getINSTANCE().showBridgeWebViewColor(tv_dialog_content, display_note);
            }
        }, 0);
    }

    Handler uphandler = new Handler();
    MyDialog CononDialog;

    String display_note;
    MyDialog JSDialog;
    MyDialog QGDialog;
    RoundedImageView iv_solar, iv_solarQG;

    /**
     * 敬售福祺宝进行中弹层
     */
    private void showJSDialog() {
        try {
            if (JSDialog == null) {
                JSDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_coming);// 创建Dialog并设置样式主题
                JSDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = JSDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                JSDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = JSDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 4 / 5;
                JSDialog.getWindow().setAttributes(p); //设置生效
                iv_solar = JSDialog.findViewById(R.id.iv_solar);
                iv_solar.setVisibility(View.VISIBLE);
                iv_solar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, p.width));
                ImageView iv_imgJS = JSDialog.findViewById(R.id.iv_img);
                TextView tv_contJS = JSDialog.findViewById(R.id.tv_cont);
                TextView tv_sure = JSDialog.findViewById(R.id.tv_sure);
                iv_imgJS.setBackgroundResource(R.mipmap.my_fuqibao_tc);
                tv_contJS.setText("请联系创业者完成敬售福祺宝订单!");
                ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_sure, R.color.red);
                LinearLayout layout_cancel = JSDialog.findViewById(R.id.layout_cancel);
                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSDialog.dismiss();
                    }
                });
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JSDialog.dismiss();/**/
                        if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                            Intent intent = new Intent(mActivity, PionTiXianRecordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    }
                });
            } else {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    JSDialog.show();
                }
            }
            if (!TextUtils.isEmpty(current_jieqi)) {
                Bitmap bitmap = DensityUtil.getAssets(mActivity, current_jieqi);
                Log.e("PionDaiFuItemBean", "current_jieqi=" + current_jieqi + "  ;bitmap=" + bitmap);
                BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                iv_solar.setBackground(bd);
                iv_solar.getBackground().setAlpha(20);
            }
        } catch (Exception e) {

        }
    }

    /**
     * 请购祝佑宝进行中弹层
     */
    private void showQGDialog() {
        try {
            if (QGDialog == null) {
                QGDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_coming);// 创建Dialog并设置样式主题
                QGDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
                Window window = QGDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                QGDialog.show();
                WindowManager m = getActivity().getWindowManager();
                Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
                WindowManager.LayoutParams p = QGDialog.getWindow().getAttributes(); //获取对话框当前的参数值
                p.width = d.getWidth() * 4 / 5;
                QGDialog.getWindow().setAttributes(p); //设置生效
                iv_solarQG = QGDialog.findViewById(R.id.iv_solar);
                iv_solarQG.setVisibility(View.VISIBLE);
                iv_solarQG.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, p.width));
                ImageView iv_img = QGDialog.findViewById(R.id.iv_img);
                TextView tv_cont = QGDialog.findViewById(R.id.tv_cont);
                TextView tv_sure = QGDialog.findViewById(R.id.tv_sure);
                iv_img.setBackgroundResource(R.mipmap.my_zhuyoubao_tc);
                tv_cont.setText("请联系创业者完成请购祝佑宝订单!");
                ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, tv_sure, R.color.red);
                LinearLayout layout_cancel = QGDialog.findViewById(R.id.layout_cancel);
                layout_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QGDialog.dismiss();
                    }
                });
                tv_sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QGDialog.dismiss();/**/
                        if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                            Intent intent = new Intent(mActivity, PionCZRecordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    }
                });
            } else {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    QGDialog.show();
                }
            }
            if (!TextUtils.isEmpty(current_jieqi)) {
                Bitmap bitmap = DensityUtil.getAssets(mActivity, current_jieqi);
                BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                iv_solarQG.setBackground(bd);
                iv_solarQG.getBackground().setAlpha(20);
            }
        } catch (Exception e) {

        }
    }

    private void showChoAbilityDialog() {
        if (CononDialog == null) {
            CononDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_connon);// 创建Dialog并设置样式主题
            CononDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = CononDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            CononDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = CononDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4;
            CononDialog.getWindow().setAttributes(p); //设置生效
            ImageView fram_bg = CononDialog.findViewById(R.id.fram_bg);
            fram_bg.setLayoutParams(new LinearLayout.LayoutParams(p.width, (int) (p.width * 1.1)));
            fram_bg.setBackgroundResource(R.mipmap.my_xinren_nengliang);
            LinearLayout layout_cancel = CononDialog.findViewById(R.id.layout_cancel);

            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CononDialog.dismiss();
                }
            });
            fram_bg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CononDialog.dismiss();/**/
                }
            });
        } else {
            if (getActivity() != null && !getActivity().isFinishing()) {
                CononDialog.show();
            }
        }
        isShowChoAbilityLayer = 0;
        mPresent.updateChoAbilityLayer();
    }


    /**
     * 显示祝福师付款弹层
     */
    private void showZFSDialog(HomeItemBean itemBean) {
        try {
            MyDialog mZFSDialog = new MyDialog(getActivity(), R.style.dialog, R.layout.dialog_hone_zfscash);// 创建Dialog并设置样式主题
            mZFSDialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = mZFSDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            mZFSDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = mZFSDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4;
            mZFSDialog.getWindow().setAttributes(p); //设置生效
            ImageView iv_img = mZFSDialog.findViewById(R.id.iv_img);
            LinearLayout layout_img = mZFSDialog.findViewById(R.id.layout_img);
            layout_img.getBackground().setAlpha(92);
            LinearLayout layout_bg = mZFSDialog.findViewById(R.id.layout_bg);
            ColorChangeByTime.getInstance().changeDrawableToClolor(mActivity, layout_bg, R.color.red);
            TextView tv_sure = mZFSDialog.findViewById(R.id.tv_sure);
            TextView tv_msg = mZFSDialog.findViewById(R.id.tv_msg);
            LinearLayout layout_cancel = mZFSDialog.findViewById(R.id.layout_cancel);
            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mZFSDialog.dismiss();
                }
            });
            tv_sure.setTag(itemBean);
            tv_sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mZFSDialog.dismiss();/**/
                    if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
                        HomeItemBean itemBean = (HomeItemBean) v.getTag();
                        int skip_type = itemBean.getSkip_type();
                        if (skip_type == 1) {
                            Intent intent = new Intent(mContext, DaiFuActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } else if (skip_type == 0) {
                            Intent intent = new Intent(mContext, TiXianRecordActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        } else if (skip_type == 2) {//用户代充
                            Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("html_url", itemBean.getSkip_url());
                            startActivity(intent);
                        } else if (skip_type == 3) {//祝福师代充
                            Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("html_url", itemBean.getSkip_url());
                            startActivity(intent);
                        }
                    }

                }
            });
            tv_msg.setText(itemBean.getMsg());
            GlideDownLoadImage.getInstance().loadCircleImageRoleREf(mActivity, itemBean.getUrl(), iv_img, 0);
            isOrderZhufubaoLayerDialog.add(mZFSDialog);
        } catch (Exception e) {

        }
    }

//    /**
//     * 0初始化 ； 1 跳转登录返回（未登录返回不再显示）；2 跳转快速组队页
//     */
//    public int isFirstComIn = 0;
//    private void setDaTing() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (isAdded()) {
//                    if (UserLoginSkipUtils.checkLoginStatus(mActivity, ConstantManager.loginSkipToHome)) {
//                        isFirstComIn = 2;
//                        Intent intent = new Intent(mActivity, VideoMenuActivity.class);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        intent.putExtra("skipType", "auto");
//                        startActivity(intent);
//                    } else {
//                        isFirstComIn = 1;
//                    }
//                }
//            }
//        }, 0);
//    }

    public boolean showChoAbility() {
        if (isShowChoAbilityLayer == 1) {
            showChoAbilityDialog();
            return true;
        } else {
            if (CononDialog != null && CononDialog.isShowing()) {
                CononDialog.dismiss();
            }
        }
        return false;
    }

    public void setAllContDialog() {
        if (mActivity == null) {
            return;
        }
        //更新或后天隐藏弹层
        if (BottomMenuActivity.updatedialogstatus
                || PriceUtils.getInstance().mbackgroundStatus
                || PairingUtils.getINSTANCE().haveShowDialogStatus()) {
            dismissAllDialog();
            return;
        }
        //显示视频和康农页面
//        if (!BottomMenuActivity.updatedialogstatus && isFirstComIn == 0) {
//            dismissAllDialog();
//            setDaTing();
//            return;
//        }
        //非首页隐藏首页弹层
        if (BottomMenuActivity.position != BottomMenuActivity.tab_position_home) {
            dismissAllDialog();
            return;
        }
        boolean showDialogStatus = MySharedPreferences.getInstance().getshowDialogStatus();
        //开启通知弹层
        Log.e("getIsOpenNotification", "mActivity=" + mActivity);
        NotificationManagerCompat manager = NotificationManagerCompat.from(mActivity);
        boolean isOpened = manager.areNotificationsEnabled();
        Log.e("getIsOpenNotification", "isOpened=" + isOpened + "  showDialogStatus==" + showDialogStatus);
        if (!isOpened) {
            dismissAllDialog();
            showOpenNotificationWindow();
            return;
        }
        //只显示一次更新通知弹层
        if (showDialogStatus && !TextUtils.isEmpty(display_note)) {
            MySharedPreferences.getInstance().showDialogStatus(false);
            showUpdaDialog();
            return;
        }
        dismissTiXian();

        if (showChoAbility()) {
            return;
        }
        if (entreUserCashIngLayer == 1 || entreUserRechargeIngLayer == 1) {
            if (entreUserCashIngLayer == 1 && BottomMenuActivity.openAppShowJSOnc) {
                BottomMenuActivity.openAppShowJSOnc = false;
                showJSDialog();
                return;
            } else {
                if (JSDialog != null && JSDialog.isShowing()) {
                    JSDialog.dismiss();
                }
            }

            Log.e("PionDaiFuItemBean", "openAppShowQGOnc=" + BottomMenuActivity.openAppShowQGOnc + "   ;entreUserRechargeIngLayer=" + entreUserRechargeIngLayer);
            if (entreUserRechargeIngLayer == 1 && BottomMenuActivity.openAppShowQGOnc) {
                BottomMenuActivity.openAppShowQGOnc = false;
                showQGDialog();
                return;
            } else {
                if (QGDialog != null && QGDialog.isShowing()) {
                    QGDialog.dismiss();
                }
            }
        } else {
            if (JSDialog != null && JSDialog.isShowing()) {
                JSDialog.dismiss();
            }
            if (QGDialog != null && QGDialog.isShowing()) {
                QGDialog.dismiss();
            }
        }
//        if (isOrderZhufubaoLayer != null && isOrderZhufubaoLayer.size() > 0) {
//            for (HomeItemBean itemBean : isOrderZhufubaoLayer) {
//                showZFSDialog(itemBean);
//            }
//            return;
//        }
    }

    public void dismissTiXian() {
        if (isOrderZhufubaoLayerDialog != null && isOrderZhufubaoLayerDialog.size() > 0) {
            for (Dialog itemBean : isOrderZhufubaoLayerDialog) {
                if (itemBean != null && itemBean.isShowing()) {
                    itemBean.dismiss();
                }
            }
            isOrderZhufubaoLayerDialog.clear();
        }
    }


    HashMap<String, HomeAfterBean> HomeAfterDataMap = new HashMap<>();
    /**
     * 0 不显示康农弹层;1 显示
     */
    List<HomeItemBean> layer;
    List<HomeItemBean> isOrderZhufubaoLayer;
    List<Dialog> isOrderZhufubaoLayerDialog = new ArrayList<>();
    int entreUserCashIngLayer;//敬售进行中
    int entreUserRechargeIngLayer;//请购进行中
    int isShowChoAbilityLayer;//0 不展示cho 能量9000的弹层， 1 展示。

    private void setLoadData(HomeAfterBean mHomeAfterBean) {
        if (mHomeAfterBean != null) {
            isShowChoAbilityLayer = mHomeAfterBean.getIsShowChoAbilityLayer();
            isOrderZhufubaoLayer = mHomeAfterBean.getIsOrderZhufubaoLayer();
            layer = mHomeAfterBean.getLayer();
            display_note = mHomeAfterBean.getDisplay_note();
            my_gratitude_url = mHomeAfterBean.getMy_gratitude_url();
            my_dedication_url = mHomeAfterBean.getMy_dedication_url();
            kn_url = mHomeAfterBean.getKn_url();
            activity_url = mHomeAfterBean.getActivity_url();
            entreUserCashIngLayer = mHomeAfterBean.getEntreUserCashIngLayer();//敬售进行中
            entreUserRechargeIngLayer = mHomeAfterBean.getEntreUserRechargeIngLayer();//请购进行中
            String sign_url = mHomeAfterBean.getSign_url();
            SharedPreferencesHelper.put(mActivity, "sign_url", "" + sign_url);
            String invite_user_url = mHomeAfterBean.getInvite_user_url();
            SharedPreferencesHelper.put(mActivity, "invite_user_url", "" + invite_user_url);
            HomeItemBean UserInfo = mHomeAfterBean.getUserInfo();
            if (UserInfo != null) {
                int group_id = UserInfo.getGroup_id();
                int team_id = UserInfo.getTeam_id();
                SharedPreferencesHelper.put(mActivity, "group_id", group_id);
                SharedPreferencesHelper.put(mActivity, "team_id", team_id);
                if (group_id == 0 || team_id == 0) {
                    SharedPreferencesHelper.put(mActivity, "haveCommune", false);
                } else {
                    SharedPreferencesHelper.put(mActivity, "haveCommune", true);
                }
            }
            chacheMap(mHomeAfterBean);
            showNoDataView(false);
            List<HomeItemBean> Banners = mHomeAfterBean.getBanners();
            if (Banners != null) {
                shoeZZJieQi(Banners);
            }
            current_jieqi = mHomeAfterBean.getCurrent_jieqi().getEn();
            jieqi_name = mHomeAfterBean.getCurrent_jieqi().getCn();
            List<String> topmsg = mHomeAfterBean.getTop_msgs();
            if (topmsg != null && topmsg.size() > 0) {
                fillView(topmsg);
            }

            icons = mHomeAfterBean.getIcons();
            showIcon(icons);

            actions = mHomeAfterBean.getActions();
            if (actions != null && actions.size() > 0) {
                gv_Action.setFocusable(false);
                setHotAtion(actions);
            }


            List<HomeItemBean> newpu = mHomeAfterBean.getNewpu();
            if (newpu != null) {
                showNewPu(newpu);
            }


            ArrayList<HomeItemBean> rankingData = mHomeAfterBean.getRankingData();
            if (rankingData != null && rankingData.size() > 0) {
                showDedicationAdapter(rankingData);
            }

            msg = mHomeAfterBean.getMsg();
            if (msg != null && msg.size() > 0) {
                mainhotpushLv.setFocusable(false);
                setHotPushHelp(msg);
            }
            if (!initshowChache) {
                setAllContDialog();
            }
            setFocuse();
        }
    }

    @Override
    public void ActionListSuccess(PoActionListDataBean mHomeDataBean) {

    }

    @Override
    public void QuickTeamUrlSuccess(QuickTeamDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            String url = responseBean.getData();
            Intent intent = new Intent(mActivity, BaoZhangActitvty.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("html_url", "" + url);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
        }
    }

    /**
     * 缓存首页数据
     *
     * @param mHomeAfterBean
     */
    private void chacheMap(HomeAfterBean mHomeAfterBean) {
        HomeAfterDataMap.clear();
        HomeAfterDataMap.put("mHomeAfterBean", mHomeAfterBean);
        SharedPreferencesUtil.getInstance().putHashMapData("mHomeAfterDataNew", HomeAfterDataMap);
    }

    @Override
    public void ListError() {
        RefreshComplete();
        showChache(true);
    }

    private void showChache(boolean showAreaListError) {
        Log.e("showChache", "showAreaListError=" + showAreaListError);
        HomeAfterDataMap.clear();
        HomeAfterDataMap.putAll(SharedPreferencesUtil.getInstance().getHashMapData("mHomeAfterDataNew", HomeAfterBean.class));
        if (HomeAfterDataMap != null && HomeAfterDataMap.size() > 0) {
            Log.e("showChache", "HomeAfterDataMap=" + HomeAfterDataMap.size());
            HomeAfterBean mHomeAfterBean_ = HomeAfterDataMap.get("mHomeAfterBean");
            setLoadData(mHomeAfterBean_);
        } else {
            if (showAreaListError)
                showNoDataView(true);
        }
    }

    private void RefreshComplete() {
        initshowChache = false;
        if (frame_bottom != null) {
            //防止数据加载有跳动
            frame_bottom.setVisibility(View.VISIBLE);
        }
        ListUtils.getInstance().RefreshCompleteS(main_sv);
    }


    /**
     * 节气
     *
     * @param BannersList
     */
    private void shoeZZJieQi(List<HomeItemBean> BannersList) {
        if (homedediVpTop == null) {
            return;
        }
        TopAdapter adapter = new TopAdapter(mActivity, BannersList);
        homedediVpTop.setAdapter(adapter);
        if (BannersList != null && BannersList.size() > 1) {
            homedediVpTop.setCurrentItem(1, false);
        }
    }

    private void fillView(List<String> topmsg) {
        vp.removeAllViews();
        for (int i = 0; i < topmsg.size(); i++) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.text_item2, null);
            TextView textView = view.findViewById(R.id.tv_cont);
            textView.setTextColor(mActivity.getResources().getColor(R.color.text_contents_color));
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText(Html.fromHtml(topmsg.get(i)));
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

    IconAdapter mIconAdapter;

    private void showIcon(List<HomeItemBean> iconList) {
        if (iconList != null && iconList.size() > 0) {
            if (mIconAdapter == null) {
                mIconAdapter = new IconAdapter(mActivity, iconList);
                gv_icon.setAdapter(mIconAdapter);
            } else {
                mIconAdapter.reloadListView(iconList, true);
            }
        }
    }


    /**
     * 健康速递
     */
    private void showNewPu(List<HomeItemBean> newpuList) {
        HealthAdapter adapter = new HealthAdapter(getActivity(), newpuList);
        homedediVpHealth.setAdapter(adapter);
    }


    //**********************奉献榜 start*****************************************

    /**
     * 奉献榜
     *
     * @param list
     */
    private void showDedicationAdapter(ArrayList<HomeItemBean> list) {
        boolean ss = (list.size() % 2) == 0;
        if (!ss) {
            list.add(list.get(0));
        }
        DedicationAdapter adapter = new DedicationAdapter(mActivity, list);
        homedediVpDedication.setAdapter(adapter);
        int width = (DensityUtil.screenWith(mContext) - DensityUtil.dip2px(mContext, 30)) / 2;
        homedediVpDedication.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, width));
    }


    //**********************奉献榜 end*****************************************
    ActionAdapter mActionAdapter;

    /**
     * 热门行动
     */
    private void setHotAtion(List<HomeItemBean> actions) {
        if (mActionAdapter == null) {
            mActionAdapter = new ActionAdapter(mActivity, actions);
            gv_Action.setAdapter(mActionAdapter);
        } else {
            mActionAdapter.refreshListView(actions);
        }
    }

    HomeHotPushAdapter mHomeHotPushAdapter;

    /**
     * 热推互助
     */
    private void setHotPushHelp(List<HomeItemBean> msg) {
        if (mHomeHotPushAdapter == null) {
            mHomeHotPushAdapter = new HomeHotPushAdapter(mActivity, msg);
            mainhotpushLv.setAdapter(mHomeHotPushAdapter);
        } else {
            mHomeHotPushAdapter.refreshListView(msg);
        }
    }

    boolean FirstComIn = true;

    /**
     * scrollBy()直接设置滚动条到该位置 scrollTo()
     * 是直接指定滚动条的位置，会有一个滑动效果, 但是由于这个动作不是单纯关于 ScrollView 而已,
     * 还要根据 ScrollView 里面包含的View 的实际信息.
     * 所以这动作必须在页面加载完成以后才能执行.
     */
    private void setFocuse() {
        if (FirstComIn) {
            FirstComIn = false;
            main_sv.post(
                    new Runnable() {
                        public void run() {
                            /**
                             * 从本质上来讲，pulltorefreshscrollview 是 LinearLayout，那么要想让它能滚动到顶部，我们就需要将它转为 ScrollView
                             */
                            if (main_sv != null) {
                                ScrollView scrollview = main_sv.getRefreshableView();
                                scrollview.smoothScrollTo(0, 0);
                                frame_bottom.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        }
    }


    private void showNoDataView(boolean flag) {
        if (main_sv == null) {
            return;
        }
        Log.e("showChache", "showNoDataView=" + flag);
        int showNodata = flag ? View.VISIBLE : View.GONE;
        int showData = flag ? View.GONE : View.VISIBLE;
        main_sv.setVisibility(showData);
        llNodata.setVisibility(showNodata);
        tvNoDataContent.setVisibility(showNodata);
        tvNoDataTitle.setVisibility(showNodata);
        btnNoData.setVisibility(showNodata);
        ivNodata.setVisibility(showNodata);
        ivNodata.setBackgroundResource(R.mipmap.my_network_icon);
        tvNoDataContent.setText("请刷新或检查网络");
        tvNoDataTitle.setText("网络加载失败");
        btnNoData.setText("刷新");
        btnNoData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reLoadData();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (vp != null && !vp.isFlipping()) {
            vp.startFlipping();
        }
        setNoLoginBack();
        reLoadData();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (vp != null && vp.isFlipping()) {
            vp.stopFlipping();
        }
        dismissAllDialog();
        Log.e("super.onResume", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("super.onResume", "onStop");
    }

    @Override
    public void onDestroy() {
        dismissAllDialog();
        if (tv_dialog_content != null) {
            tv_dialog_content.destroy();
        }
        super.onDestroy();
    }
}
