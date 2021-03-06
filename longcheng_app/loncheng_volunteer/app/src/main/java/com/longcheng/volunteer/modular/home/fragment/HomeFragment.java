package com.longcheng.volunteer.modular.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseFragmentMVP;
import com.longcheng.volunteer.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.volunteer.modular.helpwith.applyhelp.activity.ActionDetailActivity;
import com.longcheng.volunteer.modular.helpwith.connonEngineering.activity.ConnonH5Activity;
import com.longcheng.volunteer.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.volunteer.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.volunteer.modular.home.adapter.ActionAdapter;
import com.longcheng.volunteer.modular.home.adapter.DedicationAdapter;
import com.longcheng.volunteer.modular.home.adapter.HealthAdapter;
import com.longcheng.volunteer.modular.home.adapter.HomeHotPushAdapter;
import com.longcheng.volunteer.modular.home.adapter.IconAdapter;
import com.longcheng.volunteer.modular.home.adapter.TopAdapter;
import com.longcheng.volunteer.modular.home.bean.HomeAfterBean;
import com.longcheng.volunteer.modular.home.bean.HomeDataBean;
import com.longcheng.volunteer.modular.home.bean.HomeItemBean;
import com.longcheng.volunteer.modular.home.bean.PoActionListDataBean;
import com.longcheng.volunteer.modular.home.commune.activity.CommuneJoinListActivity;
import com.longcheng.volunteer.modular.home.commune.activity.CommuneMineActivity;
import com.longcheng.volunteer.modular.home.healthydelivery.list.activity.HealthyDeliveryAct;
import com.longcheng.volunteer.modular.home.invitefriends.activity.InviteFriendsActivity;
import com.longcheng.volunteer.modular.index.login.activity.LoginThirdSetPwActivity;
import com.longcheng.volunteer.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.volunteer.modular.mine.fragment.MineFragment;
import com.longcheng.volunteer.modular.mine.message.activity.MessageActivity;
import com.longcheng.volunteer.modular.mine.set.version.AppUpdate;
import com.longcheng.volunteer.utils.ConfigUtils;
import com.longcheng.volunteer.utils.ConstantManager;
import com.longcheng.volunteer.utils.DensityUtil;
import com.longcheng.volunteer.utils.ListUtils;
import com.longcheng.volunteer.utils.ScrowUtil;
import com.longcheng.volunteer.utils.ToastUtils;
import com.longcheng.volunteer.utils.myview.MyDialog;
import com.longcheng.volunteer.utils.myview.MyGridView;
import com.longcheng.volunteer.utils.myview.MyListView;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesUtil;
import com.longcheng.volunteer.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.volunteer.zxing.activity.MipcaCaptureActivity;

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
    @BindView(R.id.pageTop_tv_name)
    TextView pageTopTvName;
    @BindView(R.id.pagetop_iv_rigth)
    ImageView pagetopIvRigth;
    @BindView(R.id.pagetop_layout_rigth)
    LinearLayout pagetopLayoutRigth;
    @BindView(R.id.main_sv)
    PullToRefreshScrollView main_sv;


    @BindView(R.id.maintop_tv_startday)
    TextView maintopTvStartday;
    @BindView(R.id.maintop_tv_helpnum)
    TextView maintopTvHelpnum;
    @BindView(R.id.maintop_tv_lifeenergy)
    TextView maintopTvLifeenergy;
    @BindView(R.id.vp)
    ViewFlipper vp;

    @BindView(R.id.gv_icon)
    MyGridView gv_icon;

    @BindView(R.id.mainhealth_layout_more)
    LinearLayout mainhealthLayoutMore;

    @BindView(R.id.homededi_vp_dedication)
    ViewPager homedediVpDedication;
    @BindView(R.id.homededi_layout_dedicationdot)
    LinearLayout homedediLayoutDedicationdot;

    @BindView(R.id.mainaction_layout_more)
    LinearLayout mainactionLayoutMore;
    @BindView(R.id.gv_Action)
    MyGridView gv_Action;

    @BindView(R.id.mainhotpush_iv_arrow)
    ImageView mainhotpushIvArrow;
    @BindView(R.id.mainhotpush_layout_more)
    LinearLayout mainhotpushLayoutMore;
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
    @BindView(R.id.homededi_layout_healthdot)
    LinearLayout homedediLayoutHealthdot;

    List<HomeItemBean> msg;
    List<HomeItemBean> actions, icons;
    @BindView(R.id.homededi_vp_top)
    ViewPager homedediVpTop;
    @BindView(R.id.homededi_layout_topdot)
    LinearLayout homedediLayoutTopdot;

    public static String jieqi_name = "";
    public static String kn_url = "";

    @Override
    public int bindLayout() {
        return R.layout.fragment_home;
    }


    /**
     * 是否有未讀消息
     */
    public void haveNotReadMsg() {
        pagetopIvLeft.setBackgroundResource(R.mipmap.usercenter_notinfo_icon);
        String loginStatus = (String) SharedPreferencesHelper.get(mContext, "loginStatus", "");
        if (loginStatus.equals(ConstantManager.loginStatus)) {
            boolean haveNotReadMsg = (boolean) SharedPreferencesHelper.get(mContext, "haveNotReadMsgStatus", false);
            if (haveNotReadMsg) {
                pagetopIvLeft.setBackgroundResource(R.mipmap.usercenter_info_icon);
            }
        }
    }

    @Override
    public void initView(View view) {
        AppUpdate appUpdate = new AppUpdate(getActivity());
        appUpdate.startUpdateAsy("Home");
        pagetopIvRigth.setBackgroundResource(R.mipmap.homepage_scan_icon);
        pagetopIvRigth.setVisibility(View.VISIBLE);
        haveNotReadMsg();
        pageTopTvName.setText(getString(R.string.main_title));
        ScrowUtil.ScrollViewDownConfig(main_sv);
        homedediVpTop.addOnPageChangeListener(mOnPageChangeTopListener);
        homedediVpHealth.addOnPageChangeListener(mOnPageChangeHealthListener);
        homedediVpDedication.addOnPageChangeListener(mOnPageChangeListener);
        main_sv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                mPresent.setListViewData();
            }
        });
        mainhotpushLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (msg != null && msg.size() > 0) {
                    SharedPreferencesHelper.put(mContext, "msg_id", msg.get(position).getId());
                    if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToEnergyDetail)) {
                        Intent intent = new Intent(mContext, DetailActivity.class);
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
                    if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToHome)) {
                        Intent intent = new Intent(mContext, ActionDetailActivity.class);
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
                        if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToHome)) {
                            //绑定手机号才能邀请亲友
                            String phone = UserUtils.getUserPhone(mContext);
                            if (TextUtils.isEmpty(phone)) {
                                intent = new Intent(mContext, LoginThirdSetPwActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_PHONE);
                                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                            } else {
                                intent = new Intent(mContext, InviteFriendsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                            }
                        }
                    } else if (sort == 2) {
                        if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToSINGIN)) {
                            intent = new Intent(mContext, ConnonH5Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("kn_url", "" + kn_url);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    } else if (sort == 3) {
                        if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToCommuneJoinList)) {
                            boolean haveCommune = (boolean) SharedPreferencesHelper.get(mContext, "haveCommune", false);
                            if (haveCommune) {
                                intent = new Intent(mContext, CommuneMineActivity.class);
                            } else {
                                intent = new Intent(mContext, CommuneJoinListActivity.class);
                            }
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    } else if (sort == 4) {
                        SharedPreferencesHelper.put(mContext, "skiptype", "HomeFragment");
                        if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToHelpWithEnergy)) {
                            intent = new Intent(mContext, HelpWithEnergyActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("skiptype", "HomeFragment");
                            startActivity(intent);
                            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                        }
                    } else if (sort == 5) {
                        if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToSINGIN)) {
                            ((MineFragment) BottomMenuActivity.fragmentList.get(BottomMenuActivity.tab_position_mine)).signInGetInfo("signIn");
                        }
                    }
                }
            }
        });
    }


    @Override
    public void doBusiness(Context mContext) {
        pagetopLayoutLeft.setOnClickListener(this);
        pagetopLayoutRigth.setOnClickListener(this);
        mainhealthLayoutMore.setOnClickListener(this);
        mainhotpushLayoutMore.setOnClickListener(this);
        mainactionLayoutMore.setOnClickListener(this);
        int width = DensityUtil.screenWith(mContext);
        int height = (int) (width * 0.454);
        homedediVpTop.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        pagetopLayoutRigth.setFocusable(true);
        pagetopLayoutRigth.setFocusableInTouchMode(true);
        pagetopLayoutRigth.requestFocus();
        showChache(false);
        mHandler.sendEmptyMessage(CLCIKLIKE);
    }

    protected static final int CLCIKLIKE = 1;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLCIKLIKE:
                    mPresent.setListViewData();
                    break;
                case 2:

                    break;
            }
        }
    };

    @Override
    public void widgetClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.pagetop_layout_left:
                if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToMessage)) {
                    intent = new Intent(mContext, MessageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.pagetop_layout_rigth:
                if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToHome)) {
                    intent = new Intent(mContext, MipcaCaptureActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.mainaction_layout_more://热门行动
                if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToHome)) {
                    intent = new Intent(mContext, PopularActionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.mainhotpush_layout_more://热推互祝
                SharedPreferencesHelper.put(mContext, "skiptype", "HomeFragment");
                if (UserLoginSkipUtils.checkLoginStatus(mContext, ConstantManager.loginSkipToHelpWithEnergy)) {
                    intent = new Intent(mContext, HelpWithEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("skiptype", "HomeFragment");
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
                break;
            case R.id.mainhealth_layout_more:// 健康速递 查看更多
                intent = new Intent(mContext, HealthyDeliveryAct.class);
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
        return new HomePresenterImp<>(mContext, this);
    }


    @Override
    public void showDialog() {
    }

    @Override
    public void dismissDialog() {
    }


    @Override
    public void ListSuccess(HomeDataBean responseBean) {
        String status_ = responseBean.getStatus();
        if (status_.equals("400")) {
            ToastUtils.showToast(responseBean.getMsg());
        } else if (status_.equals("200")) {
            HomeAfterBean mHomeAfterBean = responseBean.getData();
            setLoadData(mHomeAfterBean);
        }
        RefreshComplete();
    }

    MyDialog CononDialog;

    public void dismissCononDialog() {
        if (CononDialog != null && CononDialog.isShowing()) {
            CononDialog.dismiss();
        }
    }

    /**
     * 是否显示康农弹层
     */
    public void showCononDialog() {
        String loginStatus = (String) SharedPreferencesHelper.get(mContext, "loginStatus", "");
        if (Is_show_knp == 0 || BottomMenuActivity.position != BottomMenuActivity.tab_position_home || !loginStatus.equals(ConstantManager.loginStatus)) {
            return;
        }
        if (CononDialog == null) {
            CononDialog = new MyDialog(mContext, R.style.dialog, R.layout.dialog_hone_connon);// 创建Dialog并设置样式主题
            CononDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
            Window window = CononDialog.getWindow();
            window.setGravity(Gravity.CENTER);
            CononDialog.show();
            WindowManager m = getActivity().getWindowManager();
            Display d = m.getDefaultDisplay(); //为获取屏幕宽、高
            WindowManager.LayoutParams p = CononDialog.getWindow().getAttributes(); //获取对话框当前的参数值
            p.width = d.getWidth() * 3 / 4;
            p.height = (int) (p.width * 1.182);
            CononDialog.getWindow().setAttributes(p); //设置生效
            FrameLayout fram_bg = (FrameLayout) CononDialog.findViewById(R.id.fram_bg);
            fram_bg.setBackgroundResource(R.mipmap.home_img_connon);
            LinearLayout layout_cancel = (LinearLayout) CononDialog.findViewById(R.id.layout_cancel);
            TextView btn_upgrade = (TextView) CononDialog.findViewById(R.id.btn_upgrade);

            layout_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CononDialog.dismiss();
                }
            });
            btn_upgrade.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CononDialog.dismiss();/**/
                    Intent intent = new Intent(mContext, ConnonH5Activity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("kn_url", "" + kn_url);
                    startActivity(intent);
                    ConfigUtils.getINSTANCE().setPageIntentAnim(intent, getActivity());
                }
            });
        } else {
            CononDialog.show();
        }

    }

    HashMap<String, HomeAfterBean> HomeAfterDataMap = new HashMap<>();
    /**
     * 0 不显示康农弹层;1 显示
     */
    int Is_show_knp;

    private void setLoadData(HomeAfterBean mHomeAfterBean) {
        if (mHomeAfterBean != null) {
            Is_show_knp = mHomeAfterBean.getIs_show_knp();
            showCononDialog();
            kn_url = mHomeAfterBean.getKn_url();
            String sign_url = mHomeAfterBean.getSign_url();
            SharedPreferencesHelper.put(mContext, "sign_url", "" + sign_url);
            String invite_user_url = mHomeAfterBean.getInvite_user_url();
            SharedPreferencesHelper.put(mContext, "invite_user_url", "" + invite_user_url);
            HomeItemBean UserInfo = mHomeAfterBean.getUserInfo();
            if (UserInfo != null) {
                int group_id = UserInfo.getGroup_id();
                int team_id = UserInfo.getTeam_id();
                SharedPreferencesHelper.put(mContext, "group_id", group_id);
                SharedPreferencesHelper.put(mContext, "team_id", team_id);
                if (group_id == 0 || team_id == 0) {
                    SharedPreferencesHelper.put(mContext, "haveCommune", false);
                } else {
                    SharedPreferencesHelper.put(mContext, "haveCommune", true);
                }
            }
            chacheMap(mHomeAfterBean);
            showNoDataView(false);
            maintopTvStartday.setText(mHomeAfterBean.getRunDay());
            maintopTvHelpnum.setText(mHomeAfterBean.getHz_people());
            maintopTvLifeenergy.setText(mHomeAfterBean.getHz_ability_sum());
            List<HomeItemBean> Banners = mHomeAfterBean.getBanners();
            if (Banners != null) {
                shoeZZJieQi(Banners);
            }
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


            List<HomeItemBean> rankingData = mHomeAfterBean.getRankingData();
            if (rankingData != null && rankingData.size() > 0) {
                showDedicationAdapter(rankingData);
                initLineLayoutDao(rankingData);
            }

            msg = mHomeAfterBean.getMsg();
            if (msg != null && msg.size() > 0) {
                mainhotpushLv.setFocusable(false);
                setHotPushHelp(msg);
            }
            setFocuse();
        }
    }

    @Override
    public void ActionListSuccess(PoActionListDataBean mHomeDataBean) {

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
        showChache(true);
        RefreshComplete();
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
        if (frame_bottom != null) {
            //防止数据加载有跳动
            frame_bottom.setVisibility(View.VISIBLE);
        }
        isFirstComIn = false;
        ListUtils.getInstance().RefreshCompleteS(main_sv);
    }


    /**
     * 节气
     *
     * @param BannersList
     */
    private void shoeZZJieQi(List<HomeItemBean> BannersList) {

        TopAdapter adapter = new TopAdapter(mContext, BannersList);
        homedediVpTop.setAdapter(adapter);
        List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < BannersList.size(); i++) {
            ImageView img = new ImageView(mContext); // 现在空
            img.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
            if (i == selectTopPosition) {
                img.setImageResource(R.drawable.corners_oval_red);
            } else {
                img.setImageResource(R.drawable.corners_oval_redfen);
            }
            imgList.add(img);
        }
        dotTopList.clear();
        homedediLayoutTopdot.removeAllViews();
        for (int i = 0; i < BannersList.size(); i++) {
            homedediLayoutTopdot.addView(imgList.get(i), getDotViewParams());
            dotTopList.add(imgList.get(i));
        }
        if (BannersList.size() > 2) {
            selectTopPosition = 1;
            homedediVpTop.setCurrentItem(selectTopPosition);
        }
    }

    private void fillView(List<String> topmsg) {
        vp.removeAllViews();
        for (int i = 0; i < topmsg.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.text_item2, null);
            TextView textView = view.findViewById(R.id.tv_cont);
            textView.setTextColor(mContext.getResources().getColor(R.color.text_contents_color));
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
        vp.setOutAnimation(mContext, R.anim.push_bottom_outvp);
        vp.setInAnimation(mContext, R.anim.push_bottom_in);
    }

    private void showIcon(List<HomeItemBean> iconList) {
        if (iconList != null && iconList.size() > 0) {
            IconAdapter mIconAdapter = new IconAdapter(mContext, iconList);
            gv_icon.setAdapter(mIconAdapter);
        }
    }


    /**
     * 健康速递
     */
    private void showNewPu(List<HomeItemBean> newpuList) {
        selectHealthPosition = 0;
        HealthAdapter adapter = new HealthAdapter(getActivity(), newpuList);
        homedediVpHealth.setAdapter(adapter);
        List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < newpuList.size(); i++) {
            ImageView img = new ImageView(mContext); // 现在空
            img.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
            if (i == selectHealthPosition) {
                img.setImageResource(R.drawable.corners_oval_red);
            } else {
                img.setImageResource(R.drawable.corners_oval_redfen);
            }
            imgList.add(img);
        }
        dotHealthList.clear();
        homedediLayoutHealthdot.removeAllViews();
        for (int i = 0; i < newpuList.size(); i++) {
            homedediLayoutHealthdot.addView(imgList.get(i), getDotViewParams());
            dotHealthList.add(imgList.get(i));
        }
    }


    //**********************奉献榜 start*****************************************
    int selectDedicationPosition;
    int selectHealthPosition;
    int selectTopPosition;
    private List<ImageView> dotList = new ArrayList<>();
    private List<ImageView> dotHealthList = new ArrayList<>();
    private List<ImageView> dotTopList = new ArrayList<>();

    /**
     * 奉献榜
     *
     * @param list
     */
    private void showDedicationAdapter(List<HomeItemBean> list) {
        selectDedicationPosition = 0;
        DedicationAdapter adapter = new DedicationAdapter(mContext, list);
        homedediVpDedication.setAdapter(adapter);


    }

    /**
     * 奉献榜 初始化点
     *
     * @param list
     */
    public void initLineLayoutDao(List<HomeItemBean> list) {
        List<ImageView> imgList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ImageView img = new ImageView(mContext); // 现在空
            img.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
            if (i == selectDedicationPosition) {
                img.setImageResource(R.drawable.corners_oval_red);
            } else {
                img.setImageResource(R.drawable.corners_oval_redfen);
            }
            imgList.add(img);
        }
        dotList.clear();
        homedediLayoutDedicationdot.removeAllViews();
        for (int i = 0; i < imgList.size(); i++) {
            homedediLayoutDedicationdot.addView(imgList.get(i), getDotViewParams());
            dotList.add(imgList.get(i));
        }
    }

    public LinearLayout.LayoutParams getDotViewParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
        params.setMargins(5, 0, 5, 5);
        return params;
    }


    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectDedicationPosition = position % dotList.size();
            for (int i = 0; i < dotList.size(); i++) {
                ImageView img = dotList.get(i);
                if (i == selectDedicationPosition) {
                    img.setImageResource(R.drawable.corners_oval_red);
                } else {
                    img.setImageResource(R.drawable.corners_oval_redfen);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    ViewPager.OnPageChangeListener mOnPageChangeTopListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectTopPosition = position % dotTopList.size();
            for (int i = 0; i < dotTopList.size(); i++) {
                ImageView img = dotTopList.get(i);
                if (i == selectTopPosition) {
                    img.setImageResource(R.drawable.corners_oval_red);
                } else {
                    img.setImageResource(R.drawable.corners_oval_redfen);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    ViewPager.OnPageChangeListener mOnPageChangeHealthListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectHealthPosition = position % dotHealthList.size();
            for (int i = 0; i < dotHealthList.size(); i++) {
                ImageView img = dotHealthList.get(i);
                if (i == selectHealthPosition) {
                    img.setImageResource(R.drawable.corners_oval_red);
                } else {
                    img.setImageResource(R.drawable.corners_oval_redfen);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    //**********************奉献榜 end*****************************************
    ActionAdapter mActionAdapter;

    /**
     * 热门行动
     */
    private void setHotAtion(List<HomeItemBean> actions) {
        if (mActionAdapter == null) {
            mActionAdapter = new ActionAdapter(mContext, actions);
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
            mHomeHotPushAdapter = new HomeHotPushAdapter(mContext, msg);
            mainhotpushLv.setAdapter(mHomeHotPushAdapter);
        } else {
            mHomeHotPushAdapter.refreshListView(msg);
        }
    }

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
                haveNotReadMsg();
                mPresent.setListViewData();
            }
        });
    }

    boolean FirstComIn = true;

    @Override
    public void onResume() {
        super.onResume();
        if (vp != null && !vp.isFlipping()) {
            vp.startFlipping();
        }
        Log.e("super.onResume", "onResume");
        if (!isFirstComIn) {
            haveNotReadMsg();
            mPresent.setListViewData();
        }
    }

    boolean isFirstComIn = true;

    @Override
    public void onPause() {
        super.onPause();
        if (vp != null && vp.isFlipping()) {
            vp.stopFlipping();
        }
        Log.e("super.onResume", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("super.onResume", "onStop");
    }

}
