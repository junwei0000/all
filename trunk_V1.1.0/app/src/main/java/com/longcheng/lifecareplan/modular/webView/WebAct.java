package com.longcheng.lifecareplan.modular.webView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.api.Api;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.BaseActivity;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.exchange.malldetail.activity.MallDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.activity.ApplyHelpActivity;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.energydetail.activity.DetailActivity;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.activity.HealthyDeliveryAct;
import com.longcheng.lifecareplan.modular.mine.awordofgold.activity.AWordOfGoldAct;
import com.longcheng.lifecareplan.modular.mine.phosphor.RedirectDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.activity.UserInfoActivity;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.AblumWebUtils;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.LocationUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeHandler;
import com.longcheng.lifecareplan.widget.jswebview.browse.BridgeWebView;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;

import org.json.JSONException;
import org.json.JSONObject;


import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Burning on 2018/9/11.
 */

public abstract class WebAct extends BaseActivity {


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
        this.url=url;
        sewtCookie(url);
        // 打开页面，也可以支持网络url
        mBridgeWebView.loadUrl(url);
    }

    private void sewtCookie(String url){
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

        double[] mLngAndLat = LocationUtils.getInstance().getLngAndLat(mContext);
        cookieManager.setCookie(url, "phone_user_latitude=" + mLngAndLat[0] + Config.WEB_DOMAIN);
        cookieManager.setCookie(url, "phone_user_longitude=" + mLngAndLat[1] + Config.WEB_DOMAIN);
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
//        mBridgeWebView.setDefaultHandler(new DefaultHandler());
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
                startActivity(intent);
                ConfigUtils.getINSTANCE().setPageIntentAnim(intent, mActivity);
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
                sewtCookie(url);
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
        Intent intent;
        String is_cho = (String) SharedPreferencesHelper.get(mContext, "is_cho", "");
        if (!TextUtils.isEmpty(is_cho) && is_cho.equals("1")) {
            intent = new Intent(mContext, ApplyHelpActivity.class);
            intent.putExtra("action_goods_id", "" + action_goods_id);
            intent.putExtra("qiming_user_id", "" + qiming_user_id);
            intent.putExtra("skipType", ConstantManager.skipType_OPENRED);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else {
            intent = new Intent(mContext, UserInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
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
