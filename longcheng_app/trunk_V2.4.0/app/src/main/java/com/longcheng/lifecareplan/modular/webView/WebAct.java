package com.longcheng.lifecareplan.modular.webView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.ReportMenuActivity;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.activity.HealthyDeliveryAct;
import com.longcheng.lifecareplan.modular.home.invitefriends.activity.InviteFriendsActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.PlayerUtils;
import com.longcheng.lifecareplan.modular.home.liveplay.VideoMenuActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.mine.activity.MineActivity;
import com.longcheng.lifecareplan.modular.home.liveplay.shortvideo.SelectVideoActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.LoginThirdSetPwActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity.AiYouSelectActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity.DocRewardActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity.LifeEnergyCalculatorActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity.VoloutListActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.activity.VoloutSelectActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.activity.BasicListActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.volunteer.activity.AiYouRecordListActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.volunteer.activity.VolRewardActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.BuyBlessActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.DaiFuActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.TiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.ZFBRewardActivity;
import com.longcheng.lifecareplan.modular.mine.myorder.activity.OrderListActivity;
import com.longcheng.lifecareplan.modular.mine.phosphor.RedirectDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.AblumWebUtils;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.network.LocationUtils;
import com.longcheng.lifecareplan.utils.SaveImageUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebViewClient;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Map;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/9/11.
 */

public abstract class WebAct extends BaseActivity {

    @BindView(R.id.pageTop_tv_name)
    public TextView pageTopTvName;
    @BindView(R.id.webView)
    public BridgeWebView mBridgeWebView;

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


    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initDataAfter() {

    }

    @Override
    public void setListener() {
    }

    @Override
    public void onClick(View view) {

    }

    String baseurl;

    public void loadUrl(String url) {
        this.baseurl = url;
        if (!TextUtils.isEmpty(baseurl)) {
            mBridgeWebView.addUrlPageBackListItem(baseurl);
            sewtCookie(baseurl);
        }
        // 打开页面，也可以支持网络url
        mBridgeWebView.loadUrl(baseurl);
    }

    /**
     * 缓存  Cookie
     */
    private void sewtCookie(String url) {
        //设置网络请求cookie
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeAllCookie();//移除
        String name = UserUtils.getUserName(mContext);
        String phone = UserUtils.getUserPhone(mContext);
        String userId = UserUtils.getUserId(mContext);
        String avatar = UserUtils.getUserAvatar(mContext);
        String token = UserUtils.getWXToken(mContext);
        int vercode = ConfigUtils.getINSTANCE().getVersionCode(mContext);
        //Config.WEB_DOMAIN域名
        cookieManager.setCookie(url, "phone_user_name=" + name + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_phone=" + phone + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_id=" + userId + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_avatar=" + avatar + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_token=" + token + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "isApp_Storage=1" + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "APP_type_pay_source=2" + Config.WEB_DOMAIN);//安卓source=2
        cookieManager.setCookie(url, "versionCode=" + vercode + Config.WEB_DOMAIN);
        //Config.WEB_DOMAIN_PAY域名
        cookieManager.setCookie(url, "phone_user_name=" + name + Config.WEB_DOMAIN_PAY);
        cookieManager.setCookie(url, "phone_user_phone=" + phone + Config.WEB_DOMAIN_PAY);
        cookieManager.setCookie(url, "phone_user_id=" + userId + Config.WEB_DOMAIN_PAY);
        cookieManager.setCookie(url, "phone_user_avatar=" + avatar + Config.WEB_DOMAIN_PAY);
        cookieManager.setCookie(url, "phone_user_token=" + token + Config.WEB_DOMAIN_PAY);
        cookieManager.setCookie(url, "isApp_Storage=1" + Config.WEB_DOMAIN_PAY);
        cookieManager.setCookie(url, "APP_type_pay_source=2" + Config.WEB_DOMAIN_PAY);//安卓source=2
        cookieManager.setCookie(url, "versionCode=" + vercode + Config.WEB_DOMAIN_PAY);
        Log.e("aaa", "getCookie : " + cookieManager.getCookie(url));
        cookieManager.getCookie(url);
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync();
        } else {
            CookieManager.getInstance().flush();
        }
    }

    @Override
    public void initView(View view) {
        initWebView();
    }

    private void initWebView() {
        updateWebPic();
        //刷新头部标题
        mBridgeWebView.registerHandler("public_TitleChange", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                if (pageTopTvName != null)
                    pageTopTvName.setText("" + data);
            }
        });
        //查看用户视频个人中心
        mBridgeWebView.registerHandler("loveVideoShow", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mActivity, MineActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("video_user_id", data);
                startActivity(intent);
            }
        });
        // 24节气福券---福券报表
        mBridgeWebView.registerHandler("bless_report_form", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mActivity, ReportMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        // 能量中心代付列表
        mBridgeWebView.registerHandler("energyCenter_userCash", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mActivity, DaiFuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        // 能量中心--祝福宝奖励
        mBridgeWebView.registerHandler("rewardList", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mActivity, ZFBRewardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        // 能量中心--购买祝福宝
        mBridgeWebView.registerHandler("userZhufubao_list", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mActivity, BuyBlessActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //  提现记录
        mBridgeWebView.registerHandler("tiXian_record", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mActivity, TiXianRecordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        // 跳转视频首页
        mBridgeWebView.registerHandler("about_live", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mActivity, VideoMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("skipType", "click");
                startActivity(intent);
            }
        });
        // 跳转视频首页
        mBridgeWebView.registerHandler("about_home", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_HOME);
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                ActivityManager.getScreenManager().popAllActivityOnlyMain();
            }
        });
        mBridgeWebView.registerHandler("main_qiming_skipdetail", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                qiMingSkipDetail(data);
            }
        });
        mBridgeWebView.registerHandler("qiming_skipcenter", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                skipCenter();
            }
        });
        //启明星页面-返回当前启明星姓名
        mBridgeWebView.registerHandler("qiming_data", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                qiming_name = data;
            }
        });
        //启明星页面-点击左边底部按钮
        mBridgeWebView.registerHandler("qiming_clickLeftBtn", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                getRedirectMsgId(data);
            }
        });
        //启明星页面-点击右边底部按钮
        mBridgeWebView.registerHandler("qiming_clickRigthBtn", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                qiMingSkipApplyHelp(data);
            }
        });
        //每日签到-跳转康农详情
        mBridgeWebView.registerHandler("knp_detailsBack", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                if (!data.contains("http")) {
                    data = Config.BASE_HEAD_URL + data;
                }
                Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data);
                startActivity(intent);
            }
        });
        //每日签到-跳转康农列表
        mBridgeWebView.registerHandler("knp_indexBack", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + HomeFragment.kn_url);
                startActivity(intent);
            }
        });
        //坐堂医-去互祝跳转康农列表带参数
        mBridgeWebView.registerHandler("DoctorGo_KNP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (!data.contains("http")) {
                    data = Config.BASE_HEAD_URL + data;
                }
                Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data);
                startActivity(intent);
            }
        });
        //每日签到-跳转兑换首页
        mBridgeWebView.registerHandler("signIn_skiptoexchange", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data.equals("1")) {
                    Intent intents = new Intent();
                    intents.setAction(ConstantManager.MAINMENU_ACTION);
                    intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_EXCHANGE);
                    LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                    ActivityManager.getScreenManager().popAllActivityOnlyMain();
                } else if (data.equals("2")) {
                    Intent intent = new Intent(mContext, HelpWithEnergyActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("skiptype", "HomeFragment");
                    startActivity(intent);
                }
            }
        });
        //每日签到-跳转兑换商品详情
        mBridgeWebView.registerHandler("signIn_skiptoGoodsDetail", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, MallDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("shop_goods_id", data);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            }
        });
        //每日签到-跳转一字千金
        mBridgeWebView.registerHandler("signIn_skiptoGold", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, AWordOfGoldAct.class);
                intent.putExtra("otherId", UserUtils.getUserId(mContext));
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            }
        });
        //每日签到-跳转生命能量申请互祝
        mBridgeWebView.registerHandler("signIn_applyHelp", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, ApplyHelpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //健康速递详情-返回关闭页面
        mBridgeWebView.registerHandler("Newspu_ReturnJudgement", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                doFinish();
            }
        });
        //健康速递详情-返回用户首页
        mBridgeWebView.registerHandler("Newspu_ReturnIndex", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_HOME);
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                ActivityManager.getScreenManager().popAllActivityOnlyMain();
            }
        });
        //健康速递详情-跳转健康速递列表
        mBridgeWebView.registerHandler("Newspu_ReturnList", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "Newspu_ReturnList=" + data);
                Intent intent = new Intent(mContext, HealthyDeliveryAct.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                if (TextUtils.isEmpty(data)) {
                    data = "0";
                }
                intent.putExtra("type", Integer.valueOf(data));
                intent.putExtra("skiptype", "about");
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            }
        });
        //成为志愿者--返回上一步
        mBridgeWebView.registerHandler("toVolunteer_PreviousPage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                doFinish();
            }
        });
        //成为志愿者--返回我家
        mBridgeWebView.registerHandler("toVolunteer_user", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_CENTER);
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
                ActivityManager.getScreenManager().popAllActivityOnlyMain();
            }
        });
        //我是志愿者--跳转到兑换详情
        mBridgeWebView.registerHandler("toVolunteer_goodsinfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, MallDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("shop_goods_id", data);
                startActivity(intent);
            }
        });
        //成为坐堂医--邀请亲友
        mBridgeWebView.registerHandler("toVolunDoctor_InviteFriends", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                //绑定手机号才能邀请亲友
                String phone = UserUtils.getUserPhone(mContext);
                Intent intent;
                if (TextUtils.isEmpty(phone)) {
                    intent = new Intent(mContext, LoginThirdSetPwActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, ConstantManager.USERINFO_FORRESULT_PHONE);
                } else {
                    intent = new Intent(mContext, InviteFriendsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }
        });

        //成为坐堂医--为患者送出一次祝福（康农列表）
        mBridgeWebView.registerHandler("toDoctor_connonList", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + HomeFragment.kn_url);
                startActivity(intent);
            }
        });
        //坐堂医--发起申请互祝
        mBridgeWebView.registerHandler("Doctor_applyHelp", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mContext, ApplyHelpActivity.class);
                intent.putExtra("other_user_id", "" + data);
                intent.putExtra("skiptype", "Doctor_applyHelp");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //志愿者申请列表、党小组审批--跳转生活保障
        mBridgeWebView.registerHandler("volunteerList_LifeIndemnify", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (!data.contains("http")) {
                    data = Config.BASE_HEAD_URL + data;
                }
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mContext, BaoZhangActitvty.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("html_url", "" + data);
                startActivity(intent);
            }
        });
        //康农工程、申请生活保障列表-跳转信息完善页
        mBridgeWebView.registerHandler("knp_skiptoperfectuserinfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, UserInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //康农工程-返回项目首页
        mBridgeWebView.registerHandler("knp_skiptoindex", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                doFinish();
            }
        });
        //地图定位获取坐标----康农  坐堂医
        mBridgeWebView.registerHandler("user_getLongitudeLatitude", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                LocationUtils mLocationUtils = new LocationUtils();
                double[] mLngAndLat = mLocationUtils.getLngAndLatWithNetwork(mContext);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("phone_user_latitude", "" + mLngAndLat[0]);
                    jsonObject.put("phone_user_longitude", "" + mLngAndLat[1]);
                    String address = mLocationUtils.getAddress(mContext, mLngAndLat[0], mLngAndLat[1]);
                    jsonObject.put("phone_user_address", "" + address);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("registerHandler", "" + jsonObject);
                function.onCallBack(jsonObject.toString());
            }
        });
        //返回按钮
        mBridgeWebView.registerHandler("about_back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                doFinish();
            }
        });
        //智能互祝-生命能量不足跳转激活能量
        mBridgeWebView.registerHandler("autohelp_Activat", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mContext, ActivatEnergyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            }
        });
        //智能互祝-跳转生命能量互祝列表
        mBridgeWebView.registerHandler("helpList_back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mContext, HelpWithEnergyActivity.class);
                intent.putExtra("skiptype", "myDeGra");
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            }
        });
        //我的恩人我的奉献-跳转生命能量互祝列表
        mBridgeWebView.registerHandler("myDeGra_helpEnergy", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mContext, HelpWithEnergyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("skiptype", "myDeGra");
                intent.putExtra("h_user_id", "" + data);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            }
        });
        //我的恩人我的奉献-跳转生活方式互祝列表
        mBridgeWebView.registerHandler("myDeGra_helpLifeStyle", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                Intent intent = new Intent(mContext, LifeStyleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("skiptype", "myDeGra");
                intent.putExtra("h_user_id", "" + data);
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
            }
        });
        //APP跳转到登录
        mBridgeWebView.registerHandler("APP_ReLogin", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                UserLoginBack403Utils.getInstance()
                        .zhuXiao();
            }
        });
        //我的手机卷-完善收货地址跳转订单列表
        mBridgeWebView.registerHandler("cardOrderList_come", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, OrderListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //收付款-选择套餐-申请跳转商品详情
        mBridgeWebView.registerHandler("CodePay_ToShopInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, MallDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("shop_goods_id", "" + data);
                startActivity(intent);
            }
        });
        //收款码-保存图片
        mBridgeWebView.registerHandler("receiptCodeImageUrl", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.e("registerHandler", "data=" + data);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int width = DensityUtil.getPhoneWidHeigth(mActivity).widthPixels;
                            int height = DensityUtil.getPhoneWidHeigth(mActivity).heightPixels;
                            final Bitmap myBitmap = Glide.with(mActivity)//上下文
                                    .load(data)//url
                                    .asBitmap() //必须
                                    .into(width, height)
                                    .get();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    SaveImageUtils.saveImageToGallerys(mActivity, myBitmap);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        //债权人提现、志愿者活雷锋提现、我家提现--获取openid
        mBridgeWebView.registerHandler("APPTuneUp_WX", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (ExampleApplication.mUMShareAPI.isInstall(mActivity, SHARE_MEDIA.WEIXIN)) {
                    creditorAuthorization(SHARE_MEDIA.WEIXIN);
                } else {
                    creditorBack("-1", "");
                }
            }
        });

        //我是志愿者---志愿者奖励
        mBridgeWebView.registerHandler("volunteer_award", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, VolRewardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是志愿者---爱友记录
        mBridgeWebView.registerHandler("msg_patient", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, AiYouRecordListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是坐堂医---生命能量计算器
        mBridgeWebView.registerHandler("life_energy_calc", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, LifeEnergyCalculatorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是坐堂医---坐堂医奖励
        mBridgeWebView.registerHandler("docker_award", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, DocRewardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是坐堂医---爱友资料
        mBridgeWebView.registerHandler("knp_patient_info", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, BasicListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是坐堂医---志愿者
        mBridgeWebView.registerHandler("volunteer_add", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, VoloutListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是坐堂医---添加爱友-志愿者选择
        mBridgeWebView.registerHandler("volunteer_select", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                ConfigUtils.getINSTANCE().function = function;
                Intent intent = new Intent(mContext, VoloutSelectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是坐堂医---申请行动-爱友选择
        mBridgeWebView.registerHandler("applyaction_aiyou_select", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                ConfigUtils.getINSTANCE().function = function;
                Intent intent = new Intent(mContext, AiYouSelectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是坐堂医---视频上传
        mBridgeWebView.registerHandler("question_video_add", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                ConfigUtils.getINSTANCE().function = function;
                Intent intent = new Intent(mContext, SelectVideoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        //我是坐堂医---视频播放
        mBridgeWebView.registerHandler("play_video_url", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                PlayerUtils.playNetworkVideo(mActivity,data);
            }
        });
    }
    /**
     * ________________债权人提现、志愿者活雷锋提现、我家提现--获取openid____________________________
     */
    /**
     * 债权人- 返回
     *
     * @param type 0:无  ； 1：有 ； -1：未安装微信
     */
    private void creditorBack(String type, String relation_str) {
        if (mBridgeWebView == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("relation_str", relation_str);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("creditorBack", "" + jsonObject.toString());
        mBridgeWebView.callHandler("ObtainAPP_relation_str", jsonObject.toString(), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });
    }

    private void creditorAuthorization(SHARE_MEDIA share_media) {
        ExampleApplication.mUMShareAPI.getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Log.d(TAG, "onComplete " + "授权完成");
                String openid = map.get("openid");
                creditorBack("1", openid);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
                creditorBack("0", "");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
                creditorBack("0", "");
            }
        });
    }

    /**
     * _______________________________end_____________________________
     */
    boolean showErr = false;

    private void showNoDataView(boolean flag) {
        if (mBridgeWebView == null) {
            return;
        }
        Log.e("showChache", "showNoDataView=" + flag);
        int showNodata = flag ? View.VISIBLE : View.GONE;
        int showData = flag ? View.GONE : View.VISIBLE;
        mBridgeWebView.setVisibility(showData);
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
                showErr = false;
                mBridgeWebView.reload();
            }
        });
    }

    /**
     * ***********************上传图片*************************
     */
    private void updateWebPic() {
        //开启长按复制粘贴
        mBridgeWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //如果想要屏蔽只需要返回ture 即可
                return false;
            }
        });
        mBridgeWebView.setWebChromeClient(new WebChromeClient() {
            // 配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }


            // For Android 5.0+
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadCallbackAboveL = filePathCallback;
                choseSinglePic();
                return true;
            }
            // For Android 3.0+

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                choseSinglePic();
            }
            //3.0--版本

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                choseSinglePic();
            }
            // For Android 4.1

            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                choseSinglePic();
            }
        });
        mBridgeWebView.setWebViewClient(new BridgeWebViewClient(mBridgeWebView) {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mHandler.sendEmptyMessage(SHOWDIALOG);
                Log.e("URLDecoder", "onPageStarted  =" + url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                setLoadPicEnd(view);
                super.onPageFinished(view, url);
                Log.e("URLDecoder", "onPageFinished url=" + url);
                mHandler.sendEmptyMessage(DISMISSDIALOG);
            }

            //Android6.0以上404或者500处理
            @TargetApi(android.os.Build.VERSION_CODES.M)
            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                super.onReceivedHttpError(view, request, errorResponse);
                int statusCode = errorResponse.getStatusCode();
                Log.e("URLDecoder", "onReceivedHttpError statusCode=" + statusCode);
                //404 图片问题
                if (500 == statusCode) {
                    showErr = true;
                    showNoDataView(showErr);
                }
            }

            //Android6.0以上断网和链接超时处理
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("URLDecoder", "onReceivedError errorCode=" + errorCode);
                showErr = true;
                showNoDataView(showErr);
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
    }

    /**
     * 设置webview图片缓存本地----加载结束设置
     *
     * @param view
     */

    public void setLoadPicEnd(WebView view) {
//        view.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        view.getSettings().setBlockNetworkImage(false);
    }

    AblumWebUtils mAblumPicUtils;

    private void choseSinglePic() {
        if (mAblumPicUtils == null) {
            mAblumPicUtils = new AblumWebUtils(mActivity, mUploadMessage, mUploadCallbackAboveL);
        }
        mAblumPicUtils.setmUploadMessage(mUploadMessage);
        mAblumPicUtils.setmUploadCallbackAboveL(mUploadCallbackAboveL);
        mAblumPicUtils.onAddAblumClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAblumPicUtils != null && mAblumPicUtils.selectDialog != null && mAblumPicUtils.selectDialog.isShowing()) {
            mAblumPicUtils.selectDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == mAblumPicUtils.RESULTCAMERA
                    || requestCode == mAblumPicUtils.RESULTGALLERY) {
                mAblumPicUtils.setResult(requestCode, resultCode, data);
            }
        } catch (Exception e) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * ******************end****************
     */
    public String qiming_name;
    private String qiming_user_id;

    protected void updateQiMingUserId(String qiming_user_id) {
        this.qiming_user_id = qiming_user_id;
    }

    /**
     * 启明星页面-右边按钮 跳转申请互祝页
     */
    private void qiMingSkipApplyHelp(String action_goods_id) {
        Intent intent = new Intent(mContext, ApplyHelpActivity.class);
        intent.putExtra("action_goods_id", "" + action_goods_id);
        intent.putExtra("qiming_user_id", "" + qiming_user_id);
        intent.putExtra("skiptype", ConstantManager.skipType_OPENRED);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void getRedirectMsgId(String action_goods_id) {
        Observable<RedirectDataBean> observable = Api.getInstance().service.getRedirectMsgId(UserUtils.getUserId(mContext), action_goods_id,
                ExampleApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<RedirectDataBean>() {
                    @Override
                    public void accept(RedirectDataBean responseBean) throws Exception {
                        String redirectMsgId = responseBean.getData().getRedirectMsgId();
                        qiMingSkipHelp(redirectMsgId);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        qiMingSkipHelp("0");
                    }
                });

    }

    private void qiMingSkipHelp(String redirectMsgId) {
        Intent intent;
        if (!TextUtils.isEmpty(redirectMsgId) && !redirectMsgId.equals("0")) {
            intent = new Intent(mContext, DetailActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("msg_id", redirectMsgId);
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        } else {
            intent = new Intent(mContext, HelpWithEnergyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("skiptype", "qiming");
            startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
        }
    }

    /**
     * 首页 启明课程 点击item 跳转 详情
     */
    private void qiMingSkipDetail(String msg_id) {
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("msg_id", msg_id);
        startActivity(intent);
        ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
    }

    private void skipCenter() {
        Intent intents = new Intent();
        intents.setAction(ConstantManager.MAINMENU_ACTION);
        intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_CENTER);
        LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);
        ActivityManager.getScreenManager().popAllActivityOnlyMain();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBridgeWebView != null) {
            mBridgeWebView.urlPageBackList.clear();
            mBridgeWebView.destroy();
        }
    }

    /**
     * 点击返回的时间间隔
     */
    private long clickBackTime = 0;

    /**
     * 防止连续点击返回键
     */
    public void back() {
        if (mBridgeWebView != null && mBridgeWebView.canGoBack() && mBridgeWebView.urlPageBackList.size() > 1) {
            if ((System.currentTimeMillis() - clickBackTime) > 500) {
                clickBackTime = System.currentTimeMillis();
                showDialog();
                //防止同时操作主线程阻塞
                mHandler.sendEmptyMessage(GOBACKPAGE);
            }
        } else {
            doFinish();
        }
    }

    private static final int GOBACKPAGE = -1;
    private static final int SHOWDIALOG = -2;
    private static final int DISMISSDIALOG = -3;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GOBACKPAGE:
                    //---------------------webview关于返回错乱----修改.1-----------------------------------------------
                    String url = mBridgeWebView.getUrl();
                    int index = -1;
                    if (mBridgeWebView.urlPageBackList.contains(url)) {
                        index = mBridgeWebView.urlPageBackList.indexOf(url);
                    }
                    Log.e("goBack", "urlPageBackList=\n" + mBridgeWebView.urlPageBackList.toString());
                    Log.e("goBack", "goBack------index==" + index + "  beforepageIndex==" + mBridgeWebView.beforepageIndex + " mBridgeWebView.getUrl= " + mBridgeWebView.getUrl());
                    mBridgeWebView.clickPageBacking = true;
                    if (index > 0) {
                        if (index == mBridgeWebView.beforepageIndex) {//相同的页面无法返回时，回到首页
                            mBridgeWebView.loadUrl(baseurl);
                        } else {
                            mBridgeWebView.goBack();
                        }
                    } else {//等于0或-1堆栈错乱 关闭页面
                        doFinish();
                    }
                    mBridgeWebView.beforepageIndex = index;
                    //------------------------------------------------------------------------
                    break;
                case SHOWDIALOG:
                    showDialog();
                    break;
                case DISMISSDIALOG:
                    dismissDialog();
                    if (!showErr) {
                        showNoDataView(false);
                    }
                    break;
            }
        }
    };

    public void showDialog() {
        RequestDataStatus = true;
        LoadingDialogAnim.show(mContext);
    }

    public void dismissDialog() {
        RequestDataStatus = false;
        LoadingDialogAnim.dismiss(mContext);
    }
}
