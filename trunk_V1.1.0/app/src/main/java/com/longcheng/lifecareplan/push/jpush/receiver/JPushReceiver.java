package com.longcheng.lifecareplan.push.jpush.receiver;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.mine.message.activity.MessageActivity;
import com.longcheng.lifecareplan.push.PushClient;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogUtils.d("JPushReceiver", "[JpushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                // 接收Registration Id
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                PushClient.getINSTANCE(context).getPushReceiverListener().jpush_actionRegistrationId(context, regId, bundle);
                LogUtils.d("JPushReceiver", "[JpushReceiver] 接收Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                // 收到自定义消息(透传消息)
                JSONObject json = new JSONObject();
                json.put(JPushInterface.EXTRA_MESSAGE, bundle.getString(JPushInterface.EXTRA_MESSAGE));
                json.put(JPushInterface.EXTRA_EXTRA, bundle.getString(JPushInterface.EXTRA_EXTRA));
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .onPushPassThroughMessage(context, json.toString());
                LogUtils.d("JPushReceiver", "[JpushReceiver] 接收到推送下来的自定义消息: " + json.toString());
                receivingNotification(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                // 收到通知
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .jpush_onNotificationMessageArrived(context, bundle);
                LogUtils.d(getClass() + "", "[JpushReceiver] 接收到推送下来的通知的ID: " + bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
                SharedPreferencesHelper.put(context, "haveNotReadMsgStatus", true);
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_JPUSHMESSAGE);
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);


            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                // 点击了收到的通知
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .onPushNotificationClicked(context, "", bundle);
                LogUtils.d("JPushReceiver", "[JpushReceiver] 用户点击打开了通知");
                if (UserLoginSkipUtils.checkLoginStatus(context, ConstantManager.loginSkipToMessage)) {
                    //兼容个别手机不能跳转问题
                    Activity mActivity = ActivityManager.getScreenManager().getCurrentActivity();
                    Intent intents = new Intent(mActivity, MessageActivity.class);
                    intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    mActivity.startActivity(intents);
                }
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .jpush_actionRichpushCallback(context, bundle.getString(JPushInterface.EXTRA_EXTRA), bundle);
                LogUtils.d("JPushReceiver", "[JpushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .jpush_actionConnectionChange(context, connected);
                LogUtils.w("JPushReceiver", "[JpushReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                PushClient.getINSTANCE(context).getPushReceiverListener().jpush_onNotificationOtherHandle(context, bundle);
                LogUtils.d("JPushReceiver", "[JpushReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    private void receivingNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        LogUtils.d("JPushReceiver", "message=" + message);
        JSONObject json = null;
        try {
            json = new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String type = json.optString("type", "2");
        String content = json.optString("content", "");
        String title = json.optString("title", "");
        LogUtils.d("JPushReceiver", "content=" + content);

        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 使用notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        builder.setContentText(content).setSmallIcon(R.mipmap.app_icon).setContentTitle(title);
        builder.setDefaults(Notification.DEFAULT_SOUND);
        manager.notify(1, builder.build());
        Notification dd = builder.build();
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtils.i(JPushReceiver.class + "", "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtils.e(JPushReceiver.class + "", "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
