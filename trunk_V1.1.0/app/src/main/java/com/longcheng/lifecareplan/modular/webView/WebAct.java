package com.longcheng.lifecareplan.modular.webView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.BaoZhangActitvty;
import com.longcheng.lifecareplan.modular.helpwith.connonEngineering.activity.ConnonH5Activity;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;
import com.longcheng.lifecareplan.modular.home.fragment.HomeFragment;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.activity.HealthyDeliveryAct;
import com.longcheng.lifecareplan.modular.home.invitefriends.activity.InviteFriendsActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.LoginThirdSetPwActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginBack403Utils;
import com.longcheng.lifecareplan.modular.mine.activatenergy.activity.ActivatEnergyActivity;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.lifecareplan.modular.mine.phosphor.RedirectDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.AblumWebUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.LocationUtils;
import com.longcheng.lifecareplan.utils.SaveImageUtils;
import com.longcheng.lifecareplan.utils.glide.GlideCircleTransform;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.dialog.LoadingDialogAnim;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;
import com.longcheng.lifecareplan.widget.jswebview.browse.JsWeb.CustomWebViewClient;

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

    String url;

    public void loadUrl(String url) {
        this.url = url;
        sewtCookie(url);
        // 打开页面，也可以支持网络url
        mBridgeWebView.loadUrl(url);
    }

    private void sewtCookie(String url) {
        /**
         * 缓存  Cookie
         */
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        String name = UserUtils.getUserName(mContext);
        String phone = UserUtils.getUserPhone(mContext);
        String userId = UserUtils.getUserId(mContext);
        String avatar = UserUtils.getUserAvatar(mContext);
        String token = UserUtils.getWXToken(mContext);
        cookieManager.setCookie(url, "phone_user_name=" + name + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_phone=" + phone + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_id=" + userId + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_avatar=" + avatar + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_token=" + token + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "isApp_Storage=1" + Config.WEB_DOMAIN);
        Log.e("aaa", "name : " + name + " , " + phone + " , userId : " + userId + avatar + " , token : " + token);
        cookieManager.getCookie(url);
        CookieSyncManager.getInstance().sync();
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
                Intent intent = new Intent(mContext, ConnonH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("kn_url", "" + data);
                startActivity(intent);
            }
        });
        //每日签到-跳转康农列表
        mBridgeWebView.registerHandler("knp_indexBack", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Intent intent = new Intent(mContext, ConnonH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("kn_url", "" + HomeFragment.kn_url);
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
                Intent intent = new Intent(mContext, ConnonH5Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("kn_url", "" + HomeFragment.kn_url);
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
        //康农工程-跳转信息完善页
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
                        .showDialogPromptReLogin(mActivity);
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
    }

    /**
     * ***********************上传图片*************************
     */
    private void updateWebPic() {
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
        intent.putExtra("skipType", ConstantManager.skipType_OPENRED);
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
            mBridgeWebView.destroy();
        }
    }


}
