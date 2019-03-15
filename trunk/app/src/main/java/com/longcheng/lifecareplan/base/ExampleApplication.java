package com.longcheng.lifecareplan.base;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.Window;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.push.PushClient;
import com.longcheng.lifecareplan.push.listener.IBasePushReceiverListener;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.UnCeHandler;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;


/**
 * 作者：MarkShuai on
 * 时间：2017/11/20 16:55
 * 邮箱：mark_mingshuai@163.com
 * 意图：Application全局的
 */

public class ExampleApplication extends MultiDexApplication {

    public static ExampleApplication exampleApplication;
    private static Context context;

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
        //初始化友盟配置
        initUmeng();

//        setStyleBasic();
//        setStyleCustom();
        PushClient.getINSTANCE(this).init(this, ConstantManager.XM_APP_ID, ConstantManager.XM_APP_KEY);
        pushListener();
    }

    private void initUmeng() {
        UMShareAPI.get(this);
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
     * @param
     * @name 推送的回调方法
     * @time 2017/11/29 19:53
     * @author MarkShuai
     */
    private void pushListener() {
        PushClient.getINSTANCE(this).setListener(new IBasePushReceiverListener() {
            @Override
            public void onPushNotificationClicked(Context context, String message, Bundle extras) {
                //推送来的事件被点击了
//                ToastUtils.showLongToast("onPushNotificationClicked -> " + message);
            }

            @Override
            public void onPushPassThroughMessage(Context context, String message) {
                //接收到推送下来的自定义消息

//                ToastUtils.showLongToast("onPushPassThroughMessage -> " + message);
            }

            //华为
            @Override
            public void hw_onToken(Context context, String token, Bundle bundle) {
                //华为的token
//                ToastUtils.showLongToast("hw_onToken -> " + token);
            }

            @Override
            public void hw_onPushState(Context context, boolean pushState) {
                //Push连接状态为
//                ToastUtils.showLongToast("hw_onPushState -> " + pushState);
            }

            //Jpush
            @Override
            public void jpush_onNotificationMessageArrived(Context context, Bundle message) {
                //JPush接收到推送下来的通知的ID
//                ToastUtils.showLongToast("jpush_onNotificationMessageArrived -> " + message);
            }

            @Override
            public void jpush_onNotificationOtherHandle(Context context, Bundle message) {
//                ToastUtils.showLongToast("jpush_onNotificationOtherHandle -> " + message);
            }

            @Override
            public void jpush_actionRichpushCallback(Context context, String message, Bundle bundle) {
                //用户收到到RICH PUSH CALLBACK
//                ToastUtils.showLongToast("jpush_actionRichpushCallback -> " + message);
            }

            @Override
            public void jpush_actionConnectionChange(Context context, boolean connected) {
                //Jpush 连接状态发生改变
//                ToastUtils.showLongToast("jpush_actionConnectionChange -> " + connected);
            }

            @Override
            public void jpush_actionRegistrationId(Context context, String regId, Bundle bundle) {
                //接收Registration Id
//                ToastUtils.showLongToast("jpush_actionRegistrationId -> " + regId);
            }

            //小米
            @Override
            public void xm_onNotificationMessageArrived(Context context, MiPushMessage message) {
                //接收服务器推送的通知消息,消息到达客户端时触发,
//                ToastUtils.showLongToast("xm_onNotificationMessageArrived -> " + message);
            }

            @Override
            public void xm_onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
                //获取给服务器发送注册命令的结果,结果封装在MiPushCommandMessage类中.
//                ToastUtils.showLongToast("xm_onReceiveRegisterResult -> " + message);
            }

            @Override
            public void xm_onCommandResult(Context context, MiPushCommandMessage message) {
                /*
                *获取给服务器发送命令的结果,结果封装在MiPushCommandMessage类中.
                * <p>
                * 当客户端向服务器发送注册push、设置alias、取消注册alias、订阅topic、取消订阅topic等等命令后，从服务器返回结果。
                * */
//                ToastUtils.showLongToast("xm_onCommandResult -> " + message);
            }
        });
    }

    /**
     * @param
     * @name JPush设置通知提示方式 - 基础属性
     * @time 2017/11/30 15:18
     * @author MarkShuai
     */
    private void setStyleBasic() {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(this);
        builder.statusBarDrawable = R.drawable.tuijianbai;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;  //设置为点击后自动消失
        builder.notificationDefaults = Notification.DEFAULT_SOUND;  //设置为铃声（ Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

    /**
     * @param
     * @name JPush  设置通知栏样式 - 定义通知栏Layout
     * @time 2017/11/30 15:18
     * @author MarkShuai
     */
    private void setStyleCustom() {
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(this,
                R.layout.jpush_customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);
        builder.layoutIconDrawable = R.drawable.tuijianbai;
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
     * @params isOpen是否开启
     * @name 是否开启全局异常捕获器
     * @time 2017/11/21 15:16
     * @author MarkShuai
     */
    public void initUnCeHandler(boolean isOpen) {
        //设置该CrashHandler为程序的默认处理器 开启异常捕捉
        if (isOpen) {
            UnCeHandler.getInstance().init(this);
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
