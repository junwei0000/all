package com.longcheng.lifecareplan.base;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.mine.emergencys.GenerateSign;
import com.longcheng.lifecareplan.push.PushClient;
import com.longcheng.lifecareplan.push.listener.IBasePushReceiverListener;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.CustomCrashHandler;
import com.longcheng.lifecareplan.utils.UnCeHandler;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.ugc.TXUGCBase;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;

import java.net.URISyntaxException;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.engineio.client.transports.WebSocket;


/**
 * 作者：jun on
 * 时间：2018/3/20 16:55
 * 意图：Application全局的
 */

public class ExampleApplication extends MultiDexApplication {

    public static ExampleApplication exampleApplication;
    private static Context context;
    public static UMShareAPI mUMShareAPI;
    //faceID key
    public static final String API_KEY = "8FJcre6VyE9pH8QY6mCdlgf5v7rZldzr";
    public static final String SECRET = "RXXpoDHZStOYT8YRas8PyNqM3tXi91l-";
    public static final String VERIFY_URL = "https://api.megvii.com/faceid/v3/sdk/verify";
    public static final String SIGN_VERSION = "hmac_sha1";
    public static final String GET_BIZTOKEN_URL = "https://api.megvii.com/faceid/v3/sdk/get_biz_token";
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    public static final int EXTERNAL_STORAGE_REQ_WRITE_EXTERNAL_STORAGE_CODE = 101;

    //    友盟配置
    {
        PlatformConfig.setWeixin(ConstantManager.WECHATAPPID, ConstantManager.WECHATSECRET);//真的
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");//假的
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");//假的
    }

    @SuppressLint("NewApi")
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
        initTencentLive();
        initOKHttp();
        initMeiQia();
        initSocketIO();
        initTalkingData();
    }

    private void initTalkingData() {
        TCAgent.LOG_ON = true;
        // App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
        // 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
        TCAgent.init(this, "0EC2B3840B7D418C84910AC81E19DCB8", "android");
        // 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
        TCAgent.setReportUncaughtExceptions(true);
    }

    private Socket mSocket;
    IO.Options opts;

    public Socket getSocket() {
        return mSocket;
    }

    /**
     * 设置当前用户连接
     *
     * @param user_id
     */
    public void setOptsQuery(String user_id) {
        opts.query = "unique_id=" + user_id;
    }

    private void initSocketIO() {
        try {
            opts = new IO.Options();
            opts.transports = new String[]{
                    WebSocket.NAME
            };
            mSocket = IO.socket(Config.CHAT_SERVER_URL, opts);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void initOKHttp() {
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
        OkHttpFinal.getInstance().init(builder.build());
    }

    /**
     * 初始化腾讯云直播，短视频
     */
    private void initTencentLive() {
        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/def2b691939d9eb3be268179c8930e67/TXLiveSDK.licence"; // 获取到的 licence url
        String licenceKey = "f7902ca622e5fd7f4b6559a455fdcc2d"; // 获取到的 licence key
        String TXUgclicenceURL = "http://license.vod2.myqcloud.com/license/v1/def2b691939d9eb3be268179c8930e67/TXUgcSDK.licence";
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);
        TXUGCBase.getInstance().setLicence(this, TXUgclicenceURL, licenceKey);
        String sdkVer = TXLiveBase.getSDKVersionStr();
        Log.d("liteavsdk", "liteav sdk version is : " + sdkVer);
        String mLicenceInfo = TXUGCBase.getInstance().getLicenceInfo(this);
        Log.d("liteavsdk", "mLicenceInfo : " + mLicenceInfo);
    }

    public static String token;

    private void initToken() {
        token = ConfigUtils.getINSTANCE().getDeviceId(this);
    }

    public static String sign() {

        long currtTime = System.currentTimeMillis() / 1000;
        long expireTime = (System.currentTimeMillis() + 60 * 60 * 100) / 1000;
        return GenerateSign.appSign(ExampleApplication.API_KEY, ExampleApplication.SECRET, currtTime, expireTime);

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
        MQConfig.isShowClientAvatar = true;//是否显示用户头像
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
        builder.statusBarDrawable = R.mipmap.app_icon;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    /**
     * JPush  设置通知栏样式 - 定义通知栏Layout
     */
    private void setStyleCustom() {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(this,
                R.layout.jpush_customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.mipmap.app_icon;
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
