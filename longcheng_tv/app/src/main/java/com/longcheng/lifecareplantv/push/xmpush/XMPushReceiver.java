package com.longcheng.lifecareplantv.push.xmpush;


import android.content.Context;

import com.longcheng.lifecareplantv.push.PushClient;
import com.longcheng.lifecareplantv.utils.LogUtils;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;


/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 * <p>
 * Created by markShuai on 2017/11/30.
 * <p>
 * 1.获取服务器推送的消息
 * 2.获取调用MiPushClient方法的返回结果
 */
public class XMPushReceiver extends PushMessageReceiver {

    private String TAG = "XMPushReceiver";

    /**
     * 接收服务器推送的透传消息,消息封装在 MiPushMessage类中
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        PushClient.getINSTANCE(context).getPushReceiverListener()
                .onPushPassThroughMessage(context, message.getContent());
        LogUtils.e(TAG, "onReceivePassThroughMessage() -> message : " + message);
    }

    /**
     * 接收服务器推送的通知消息,用户点击后触发,消息封装在 MiPushMessage类中
     * 注：用户点击了预定义通知消息，消息不会通过onNotificationMessageClicked方法传到客户端。
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        PushClient.getINSTANCE(context).getPushReceiverListener()
                .onPushNotificationClicked(context, message.getContent(), null);
        LogUtils.e(TAG, "onNotificationMessageClicked() -> message : " + message);
    }

    /**
     * 接收服务器推送的通知消息,消息到达客户端时触发,
     * 还可以接受应用在前台时不弹出通知的通知消息,消息封装在 MiPushMessage类中.
     * 在MIUI上,只有应用处于启动状态,或者自启动白名单中,才可以通过此方法接受到该消息.
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        PushClient.getINSTANCE(context).getPushReceiverListener().xm_onNotificationMessageArrived(context, message);
        LogUtils.e(TAG, "onNotificationMessageArrived() -> message : " + message);
    }

    /**
     * 获取给服务器发送命令的结果,结果封装在MiPushCommandMessage类中.
     * <p>
     * 当客户端向服务器发送注册push、设置alias、取消注册alias、订阅topic、取消订阅topic等等命令后，从服务器返回结果。
     *
     * @param context
     * @param message
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        PushClient.getINSTANCE(context).getPushReceiverListener().xm_onCommandResult(context, message);
        LogUtils.e(TAG, "onCommandResult() -> message : " + message);
    }

    /**
     * 获取给服务器发送注册命令的结果,结果封装在MiPushCommandMessage类中.
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        PushClient.getINSTANCE(context).getPushReceiverListener().xm_onReceiveRegisterResult(context, message);
        LogUtils.e(TAG, "onReceiveRegisterResult() -> message : " + message);
    }

    /*
        服务器推送的消息封装在 MiPushMessage的对象中，可以从该对象中获取messageType、messageId、 content、
            alias、 topic、passThrough、isNotified、 notifyType、 description、 title、extra等信息。
        1.messageType表示消息的类型，分为三种:MESSAGE_TYPE_REG、MESSAGE_TYPE_ALIAS、MESSAGE_TYPE_TOPIC，
            这三个是MiPushMessage的静态变量。
        2.如果服务器是给alias推送的消息，则alias不为null。
        3.如果服务器是给topic推送的消息，则topic内容不为null。
        4.passThrough指示服务器端推送的消息类型。 如果passThrough值为1,则是透传消息；如果passThrough值为0,
            则是通知栏消息。
        5.isNotified表示消息是否通过通知栏传给app的。如果为true，表示消息在通知栏出过通知；如果为false，
            表示消息是直接传给app的，没有弹出过通知。
        6.messageId是消息的id。
        7.content是消息的内容。
        8.notifyType是消息的提醒方式，如震动、响铃和闪光灯。
        9.description是消息描述。
        10.title是消息的标题。
        11.extra是一个map类型，包含一些附加信息，如自定义通知栏铃声的URI、通知栏的点击行为等等。
     */


    /*
        服务器返回的命令封装在 MiPushCommandMessage的对象中，
            可以从该对象中获取command、commandArguments、 resultCode、 reason等信息。
        一、command表示命令的类型。
            1.调用MiPushClient.registerPush(),返回MiPushClient.COMMAND_REGISTER
            2、调用MiPushClient.setAlias()，返回MiPushClient.COMMAND_SET_ALIAS
            3.调用MiPushClient.unsetAlias()，返回MiPushClient.COMMAND_UNSET_ALIAS
            4.调用MiPushClient.subscribe()，返回MiPushClient.COMMAND_SUBSCRIBE_TOPIC
            5.调用MiPushClient.unsubscribe()，返回MiPushClient.COMMAND_UNSUBSCIRBE_TOPIC
            6.调用MiPushClient.setAcceptTime()，返回MiPushClient.COMMAND_SET_ACCEPT_TIME
            7.调用MiPushClient.pausePush()，返回MiPushClient.COMMAND_SET_TARGETPT_TIME
            8.调用MiPushClient.resumePush()，返回MiPushClient.COMMAND_SET_ACCEPT_TIME
        二、commandArguments 表示命令的参数。例如: 注册app就会返回app本次初始化所对应MiPush推送服务的唯一标识regId，
            alias就会返回alias的内容，订阅和取消订阅主题就会返回topic，setAcceptTime就会返回时间段.
        三、resultCode 表示调用命令的结果。如果成功，返回ErrorCode.Sussess即0；否则返回错误类型值。
        四、reason表示调用命令失败的原因。如果失败，则返回失败原因，否则返回为null。
     */

}
