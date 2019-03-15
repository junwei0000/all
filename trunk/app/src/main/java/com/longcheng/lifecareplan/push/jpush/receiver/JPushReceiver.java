package com.longcheng.lifecareplan.push.jpush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.longcheng.lifecareplan.push.PushClient;
import com.longcheng.lifecareplan.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

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
            LogUtils.d(getClass()+"", "[JpushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                // 接收Registration Id
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                PushClient.getINSTANCE(context).getPushReceiverListener().jpush_actionRegistrationId(context, regId, bundle);
                LogUtils.d(getClass()+"", "[JpushReceiver] 接收Registration Id : " + regId);

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                // 收到自定义消息(透传消息)
                JSONObject json = new JSONObject();
                json.put(JPushInterface.EXTRA_MESSAGE, bundle.getString(JPushInterface.EXTRA_MESSAGE));
                json.put(JPushInterface.EXTRA_EXTRA, bundle.getString(JPushInterface.EXTRA_EXTRA));
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .onPushPassThroughMessage(context, json.toString());
                LogUtils.d(getClass()+"", "[JpushReceiver] 接收到推送下来的自定义消息: " + json.toString());

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                // 收到通知
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .jpush_onNotificationMessageArrived(context, bundle);
                LogUtils.d(getClass()+"", "[JpushReceiver] 接收到推送下来的通知的ID: " + bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                // 点击了收到的通知
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .onPushNotificationClicked(context, "", bundle);
                LogUtils.d(getClass()+"", "[JpushReceiver] 用户点击打开了通知");

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .jpush_actionRichpushCallback(context, bundle.getString(JPushInterface.EXTRA_EXTRA), bundle);
                LogUtils.d(getClass()+"", "[JpushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .jpush_actionConnectionChange(context, connected);
                LogUtils.w(getClass()+"", "[JpushReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                PushClient.getINSTANCE(context).getPushReceiverListener().jpush_onNotificationOtherHandle(context, bundle);
                LogUtils.d(getClass()+"", "[JpushReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

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
                    LogUtils.i(JPushReceiver.class+"", "This message has no Extra data");
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
                    LogUtils.e(JPushReceiver.class+"", "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
