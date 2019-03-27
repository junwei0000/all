package com.longcheng.volunteer.push.xmpush;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import com.longcheng.volunteer.utils.LogUtils;
import com.xiaomi.channel.commonutils.logger.LoggerInterface;
import com.xiaomi.mipush.sdk.Logger;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.List;


/**
 * Created by chengkai on 2017/9/4.
 */

public final class XMPushClient {

    private String TAG = "XMPushClient";
    private String APP_ID;
    private String APP_KEY;

    private static volatile XMPushClient INSTANCE;
    private Context applicationContext;

    private XMPushClient(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public static XMPushClient getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (XMPushClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new XMPushClient(context);
                }
            }
        }
        return INSTANCE;
    }

    public void init(String APP_ID, String APP_KEY) {
        if (TextUtils.isEmpty(APP_ID) && TextUtils.isEmpty(APP_KEY)) {
            throw new IllegalArgumentException("APP_ID or APP_KEY can not be empty/null");
        }
        if (TextUtils.isEmpty(this.APP_ID) && TextUtils.isEmpty(this.APP_KEY)) {
            this.APP_ID = APP_ID;
            this.APP_KEY = APP_KEY;
            initXMPush(APP_ID, APP_KEY);
        }
        initXMPushLog();
    }

    /**
     * 因为推送服务XMPushService在AndroidManifest.xml中设置为运行在另外一个进程，
     * 这导致本Application会被实例化两次，所以我们需要让应用的主进程初始化。
     */
    private void initXMPush(String APP_ID, String APP_KEY) {
        //初始化push推送服务
        if (shouldInit()) {
            LogUtils.e(TAG, "initXMPush");
            MiPushClient.registerPush(applicationContext, APP_ID, APP_KEY);
            initXMPushLog();
        }
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = applicationContext.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化小米推送log
     * <p>
     * 默认情况下,日志内容写入SDCard/Android/data/app pkgname/files/MiPushLog目录下的文件
     * 关闭写日志文件功能
     * Logger.disablePushFileLog(this);
     */
    private void initXMPushLog() {
        LoggerInterface newLogger = new LoggerInterface() {
            @Override
            public void setTag(String tag) {
                // ignore
                LogUtils.d(TAG, tag);
            }

            @Override
            public void log(String content, Throwable t) {
                LogUtils.d(TAG, content);
            }

            @Override
            public void log(String content) {
                LogUtils.d(TAG, content);
            }
        };
        Logger.setLogger(applicationContext, newLogger);

    }

    /**
     * 关闭MiPush推送服务,当用户希望不再使用MiPush推送服务的时候调用,
     * 调用成功之后,app将不会接收到任何MiPush服务推送的数据,直到下一次调用registerPush().
     * 注: 调用unregisterPush()之后，服务器不会向app发送任何消息.
     */
    public void unRegisterPush() {
        MiPushClient.unregisterPush(applicationContext);
    }

    /**
     * 开发者可以为指定用户设置别名,然后给这个别名推送消息,效果等同于给RegId推送消息.
     * 注: 一个RegId可以被设置多个别名,如果设置的别名已经存在,会覆盖掉之前的别名.
     *
     * @param alias 为指定用户设置别名
     */
    public void setAlias(String alias) {
        MiPushClient.setAlias(applicationContext, alias, null);
    }

    /**
     * 开发者可以取消指定用户的某个别名,服务器就不会给这个别名推送消息了.
     *
     * @param alias    为指定用户取消别名
     * @param category 扩展参数，暂时没有用途，直接填null
     */
    public void unSetAlias(String alias, String category) {
        MiPushClient.unsetAlias(applicationContext, alias, category);
    }


    /**
     * 开发者可以为指定用户设置userAccount
     *
     * @param userAccount 为指定用户设置userAccount
     * @param category    扩展参数，暂时没有用途，直接填null
     */
    public void setUserAccount(String userAccount, String category) {
        MiPushClient.setUserAccount(applicationContext, userAccount, category);
    }

    /**
     * 开发者可以取消指定用户的某个userAccount,服务器就不会给这个userAccount推送消息了.
     *
     * @param userAccount 为指定用户设置userAccount
     * @param category    扩展参数,暂时没有用途，直接填null
     */
    public void unSetUserAccount(String userAccount, String category) {
        MiPushClient.unsetUserAccount(applicationContext, userAccount, category);
    }

    /**
     * 为某个用户设置订阅主题；根据用户订阅的不同主题，开发者可以根据订阅的主题实现分组群发。
     *
     * @param topic    某个用户设置订阅的主题
     * @param category 扩展参数，暂时没有用途，直接填null
     */
    public void subscribe(String topic, String category) {
        MiPushClient.subscribe(applicationContext, topic, category);
    }

    /**
     * 为某个用户取消某个订阅主题
     *
     * @param topic    某个用户设置订阅的主题
     * @param category 扩展参数，暂时没有用途，直接填null
     */
    public void unsubscribe(String topic, String category) {
        MiPushClient.unsubscribe(applicationContext, topic, category);
    }

    /**
     * 设置接收MiPush服务推送的时段，不在该时段的推送消息会被缓存起来，到了合适的时段再向app推送原先被缓存的消息。
     * 这里采用24小时制，如果开始时间早于结束时间，则这个时段落在一天内；否则，这个时间将会跨越凌晨0点。
     * 注: 这里使用与regId相关联的alias和topic推送消息，也会受到限制。
     * <p>
     * 如果时间设置为0:00-0:00，就是暂停push推送服务，也可以直接调用pausePush()方法，
     * 其本质相同 如果时间设置为0:00-23:59，就是恢复push推送服务，即全天接收push推送消息，
     * 也可以直接调用resumePush()方法，其本质相同
     *
     * @param startHour 接收时段开始时间的小时
     * @param startMin  接收时段开始时间的分钟
     * @param endHour   接收时段结束时间的小时
     * @param endMin    接收时段结束时间的分钟
     * @param category  扩展参数，暂时没有用途，直接填null
     */
    public void setAcceptTime
    (int startHour, int startMin, int endHour, int endMin, String category) {
        MiPushClient.setAcceptTime(applicationContext, startHour, startMin, endHour, endMin, category);
    }

    /**
     * 暂停接收MiPush服务推送的消息，app在恢复MiPush推送服务之前，不接收任何推送消息
     * <p>
     * 注: 这里使用与RegId相关联的alias和topic推送消息，也是被暂停的
     *
     * @param category 扩展参数，暂时没有用途，直接填null
     */
    public void pausePush(String category) {
        MiPushClient.pausePush(applicationContext, category);
    }

    /**
     * 恢复接收MiPush服务推送的消息
     * 注: 这里使用与RegId相关联的alias和topic推送消息，也是被恢复的；
     * 这时服务器会把暂停时期的推送消息重新推送过来
     *
     * @param category 扩展参数，暂时没有用途，直接填null
     */
    public void resumePush(String category) {
        MiPushClient.resumePush(applicationContext, category);
    }

    /**
     * 获取客户端所有设置的别名
     *
     * @return
     */
    public List<String> getAllAlias() {
        return MiPushClient.getAllAlias(applicationContext);
    }

    /**
     * 获取客户端所有订阅的主题
     *
     * @return
     */
    public List<String> getAllTopic() {
        return MiPushClient.getAllTopic(applicationContext);
    }

    /**
     * 获取客户端所有设置的帐号
     *
     * @return
     */
    public List<String> getAllUserAccount() {
        return MiPushClient.getAllUserAccount(applicationContext);
    }


    /**
     * 上报点击的消息
     *
     * @param msgid 调用server api推送消息后返回的消息ID。
     */
    public void reportMessageClicked(String msgid) {
        MiPushClient.reportMessageClicked(applicationContext, msgid);
    }

    /**
     * 清除小米推送弹出的所有通知
     */
    public void clearNotification() {
        MiPushClient.clearNotification(applicationContext);
    }

    /**
     * 清除小米推送弹出的某一个notifyId通知
     *
     * @param notifyId 调用server api设置通知消息的notifyId。
     */
    public void clearNotification(int notifyId) {
        MiPushClient.clearNotification(applicationContext, notifyId);
    }

    /**
     * 客户端设置通知消息的提醒类型。 注：当服务端指定了消息的提醒类型，会优选考虑客户端设置的。
     *
     * @param notifyType 通知栏消息的提醒类型。
     */
    public void setLocalNotificationType(int notifyType) {
        MiPushClient.setLocalNotificationType(applicationContext, notifyType);
    }

    /**
     * 清除客户端设置的通知消息提醒类型。
     */
    public void clearLocalNotificationType() {
        MiPushClient.clearLocalNotificationType(applicationContext);
    }

    /**
     * 获取客户端的RegId。
     *
     * @return
     */
    public String getRegId() {
        return MiPushClient.getRegId(applicationContext);
    }

}
