package com.longcheng.lifecareplan.base;

import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.alivc.player.AliVcMediaPlayer;
import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.push.PushClient;
import com.longcheng.lifecareplan.push.listener.IBasePushReceiverListener;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.CustomCrashHandler;
import com.longcheng.lifecareplan.utils.UnCeHandler;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


/**
 * 作者：jun on
 * 时间：2018/3/20 16:55
 * 意图：Application全局的
 */

public class ExampleApplication extends MultiDexApplication {

    public static ExampleApplication exampleApplication;
    private static Context context;
    public static UMShareAPI mUMShareAPI;

    //    友盟配置
    {
        PlatformConfig.setWeixin(ConstantManager.WECHATAPPID, ConstantManager.WECHATSECRET);//真的
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");//假的
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");//假的
    }

    @Override
    public void onCreate() {
        super.onCreate();
        exampleApplication = this;
        context = getApplicationContext();
        initUnCeHandler(false);
        setCrash();
        initToken();
        initUmeng();

        setStyleBasic();
        setStyleCustom();
        initPush();
        pushListener();

        //初始化播放器（只需调用一次即可，建议在application中初始化）
        AliVcMediaPlayer.init(getApplicationContext());
//        initHttp();
        initMeiQia();
    }
//    /**
//     * 短视频需要的http依赖
//     */
//    private void initHttp() {
//        com.aliyun.vod.common.httpfinal.QupaiHttpFinal.getInstance().initOkHttpFinal();
//    }

    /**
     * 初始化美洽
     */
    private void initMeiQia() {
        MQConfig.init(this, "3871d57fb830593af57ef75af3538082", new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
//                ToastUtils.showToast("init success");
            }

            @Override
            public void onFailure(int code, String message) {
//                ToastUtils.showToast("int failure");
            }
        });
        // 可选
        customMeiqiaSDK();
    }

    private void customMeiqiaSDK() {
        // 配置自定义信息
        MQConfig.ui.titleGravity = MQConfig.ui.MQTitleGravity.LEFT;
        MQConfig.ui.backArrowIconResId = R.mipmap.back;
        MQConfig.isShowClientAvatar=true;//是否显示用户头像
//        MQConfig.ui.titleBackgroundResId = R.color.test_red;
//        MQConfig.ui.titleTextColorResId = R.color.test_blue;
//        MQConfig.ui.leftChatBubbleColorResId = R.color.test_green;
//        MQConfig.ui.leftChatTextColorResId = R.color.test_red;
//        MQConfig.ui.rightChatBubbleColorResId = R.color.test_red;
//        MQConfig.ui.rightChatTextColorResId = R.color.test_green;
//        MQConfig.ui.robotEvaluateTextColorResId = R.color.test_red;
//        MQConfig.ui.robotMenuItemTextColorResId = R.color.test_blue;
//        MQConfig.ui.robotMenuTipTextColorResId = R.color.test_blue;
    }

    public static String token;

    private void initToken() {
        token = ConfigUtils.getINSTANCE().getDeviceId(this);
//        setStrictModePermission();
    }

    /**
     * Android7.0 provider FileUriExposedException 兼容问题
     */
    private void setStrictModePermission() {
        //取消严格模式 FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }


    /**
     * 友盟初始化
     */
    private void initUmeng() {
        mUMShareAPI = UMShareAPI.get(this);
        /**
         * 第一个参数是application
         * 第二个是Appkey
         * 第三个是channel（只用share可以不写）
         * 第四个参数是设备类型
         * 第五个参数为push的secret
         */
        UMConfigure.init(this, ConstantManager.UMENGAPPID, "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
    }

    /**
     * 极光推送初始化
     */
    private void initPush() {
        PushClient.getINSTANCE(this).setAllUserJPush(true);//设置只使用极光推送
        PushClient.getINSTANCE(this).init(this, ConstantManager.XM_APP_ID, ConstantManager.XM_APP_KEY);
    }

    /**
     * 推送的回调方法
     */
    private void pushListener() {
        PushClient.getINSTANCE(this).setListener(new IBasePushReceiverListener() {
            @Override
            public void onPushNotificationClicked(Context context, String message, Bundle extras) {
                //推送来的事件被点击了
                Log.d("pushListener", "onPushNotificationClicked -> " + message);
            }

            @Override
            public void onPushPassThroughMessage(Context context, String message) {
                //接收到推送下来的自定义消息
                Log.d("pushListener", "onPushPassThroughMessage -> " + message);
            }

            //华为
            @Override
            public void hw_onToken(Context context, String token, Bundle bundle) {
                //华为的token
                Log.d("pushListener", "hw_onToken -> " + token);
            }

            @Override
            public void hw_onPushState(Context context, boolean pushState) {
                //Push连接状态为
                Log.d("pushListener", "hw_onPushState -> " + pushState);
            }

            //Jpush
            @Override
            public void jpush_onNotificationMessageArrived(Context context, Bundle message) {
                //JPush接收到推送下来的通知的ID
                Log.d("pushListener", "jpush_onNotificationMessageArrived -> " + message);
            }

            @Override
            public void jpush_onNotificationOtherHandle(Context context, Bundle message) {
                Log.d("pushListener", "jpush_onNotificationOtherHandle -> " + message);
            }

            @Override
            public void jpush_actionRichpushCallback(Context context, String message, Bundle bundle) {
                //用户收到到RICH PUSH CALLBACK
                Log.d("pushListener", "jpush_actionRichpushCallback -> " + message);
            }

            @Override
            public void jpush_actionConnectionChange(Context context, boolean connected) {
                //Jpush 连接状态发生改变
                Log.d("pushListener", "jpush_actionConnectionChange -> " + connected);
            }

            @Override
            public void jpush_actionRegistrationId(Context context, String regId, Bundle bundle) {
                //接收Registration Id
                Log.d("pushListener", "jpush_actionRegistrationId -> " + regId);
            }

            //小米
            @Override
            public void xm_onNotificationMessageArrived(Context context, MiPushMessage message) {
                //接收服务器推送的通知消息,消息到达客户端时触发,
                Log.d("pushListener", "xm_onNotificationMessageArrived -> " + message);
            }

            @Override
            public void xm_onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
                //获取给服务器发送注册命令的结果,结果封装在MiPushCommandMessage类中.
                Log.d("pushListener", "xm_onReceiveRegisterResult -> " + message);
            }

            @Override
            public void xm_onCommandResult(Context context, MiPushCommandMessage message) {
                /*
                 *获取给服务器发送命令的结果,结果封装在MiPushCommandMessage类中.
                 * <p>
                 * 当客户端向服务器发送注册push、设置alias、取消注册alias、订阅topic、取消订阅topic等等命令后，从服务器返回结果。
                 * */
                Log.d("pushListener", "xm_onCommandResult -> " + message);
            }
        });
    }

    /**
     * JPush设置通知提示方式 - 基础属性
     */
    private void setStyleBasic() {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.drawable.home_tab_main_selector;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    /**
     * JPush  设置通知栏样式 - 定义通知栏Layout
     */
    private void setStyleCustom() {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(this,
                R.layout.jpush_customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.drawable.home_tab_main_selector;
        builder.developerArg0 = "developerArg2";
        JPushInterface.setPushNotificationBuilder(2, builder);
    }

    public static Context getContext() {
        return context;
    }

    public static ExampleApplication getInstance() {
        return exampleApplication;
    }

    /**
     * 是否开启全局异常捕获器
     *
     * @param isOpen
     */
    public void initUnCeHandler(boolean isOpen) {
        //设置该CrashHandler为程序的默认处理器 开启异常捕捉
        if (isOpen) {
            UnCeHandler.getInstance().init(this);
        }
    }

    private void setCrash() {
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        // 异常捕捉
        try {
            CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
            mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}