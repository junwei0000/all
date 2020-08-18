package com.longcheng.lifecareplan.push.jpush.receiver;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.modular.bottommenu.activity.BottomMenuActivity;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.modular.mine.message.activity.MessageActivity;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.user.activatenergy.daichong.activity.dc.PionDaiCActivity;
import com.longcheng.lifecareplan.push.AppShortCutUtil;
import com.longcheng.lifecareplan.push.PushClient;
import com.longcheng.lifecareplan.push.jpush.broadcast.LocalBroadcastManager;
import com.longcheng.lifecareplan.push.jpush.message.EasyMessage;
import com.longcheng.lifecareplan.push.jpush.message.PairingUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.LogUtils;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.UserUtils;

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

                try {
                    JSONObject jsonmessage = new JSONObject(bundle.getString(JPushInterface.EXTRA_MESSAGE));
                    String receive_user_id = jsonmessage.optString("receive_user_id");
                    if (receive_user_id.equals(UserUtils.getUserId(context))) {
                        int type = jsonmessage.optInt("type", 0);
                        if (type == 9) {
                            /**
                             * 结对子弹层
                             */
                        } else {
                            /**
                             *  回馈感恩 发送
                             */
                            EasyMessage.sendMessage("flag", bundle.getString(JPushInterface.EXTRA_MESSAGE));
                            BottomMenuActivity.newMessageTime = System.currentTimeMillis();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                // 收到通知
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .jpush_onNotificationMessageArrived(context, bundle);
                LogUtils.d("JPushReceiver", "[JpushReceiver] 接收到推送下来的通知的ID: " + bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
                SharedPreferencesHelper.put(context, "haveNotReadMsgStatus", true);
                Intent intents = new Intent();
                intents.setAction(ConstantManager.MAINMENU_ACTION);
                intents.putExtra("type", ConstantManager.MAIN_ACTION_TYPE_JPUSHMESSAGE);
                LocalBroadcastManager.getInstance(ExampleApplication.getContext()).sendBroadcast(intents);

                int messagecount = (int) SharedPreferencesHelper.get(context, "messagecount", 0);
                messagecount = messagecount + 1;
                if (messagecount > 99) {
                    messagecount = 99;
                }
                SharedPreferencesHelper.put(context, "messagecount", messagecount);
                AppShortCutUtil.setCount(messagecount, context);


                String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
                LogUtils.d("JPushReceiver", "message=" + message);
                JSONObject json = null;
                try {
                    json = new JSONObject(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int type = json.optInt("music_type", 0);
                if (type == 1) {
                    //获取到的推送的通知类型
                    MediaPlayer mPlayer = MediaPlayer.create(context, R.raw.daichong2);
                    mPlayer.start();
                }
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                // 点击了收到的通知
                PushClient.getINSTANCE(context).getPushReceiverListener()
                        .onPushNotificationClicked(context, "", bundle);
                LogUtils.d("JPushReceiver", "[JpushReceiver] 用户点击打开了通知");

                if (UserLoginSkipUtils.checkLoginStatus(context, ConstantManager.loginSkipToMessage)) {
                    //兼容个别手机不能跳转问题
                    String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
                    LogUtils.d("JPushReceiver", "message=" + message);
                    JSONObject json = null;
                    try {
                        json = new JSONObject(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    int type = json.optInt("music_type", 0);
                    if (type == 1) {
                        Activity mActivity = ActivityManager.getScreenManager().getCurrentActivity();
                        Intent intents = new Intent(mActivity, PionDaiCActivity.class);
                        intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intents.putExtra("skiptype","push");
                        mActivity.startActivity(intents);
                    } else {
                        Activity mActivity = ActivityManager.getScreenManager().getCurrentActivity();
                        Intent intents = new Intent(mActivity, MessageActivity.class);
                        intents.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mActivity.startActivity(intents);
                    }

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
        String message = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogUtils.d("JPushReceiver", "message=" + message);
        JSONObject json = null;
        try {
            json = new JSONObject(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String type = json.optString("type", "1");
        String content = json.optString("content", "");
        String title = json.optString("title", "互祝");
        NotificationManager manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        // 使用notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                context);
        Intent mIntent = new Intent(context, MessageActivity.class);
        mIntent.putExtras(bundle);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText(content)
                .setContentTitle(title.equals("") ? "互祝" : title)
                .setSmallIcon(R.mipmap.app_icon);
        builder.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.daichong2));
        Notification notification = builder.build();
        notification.ledARGB = Color.GREEN;// 控制 LED 灯的颜色，一般有红绿蓝三种颜色可选
        notification.ledOnMS = 1000;// 指定 LED 灯亮起的时长，以毫秒为单位
        notification.ledOffMS = 1000;// 指定 LED 灯暗去的时长，也是以毫秒为单位
        notification.flags = Notification.FLAG_SHOW_LIGHTS;// 指定通知的一些行为，其中就包括显示
        /**
         * vibrate属性是一个长整型的数组，用于设置手机静止和振动的时长，以毫秒为单位。
         * 参数中下标为0的值表示手机静止的时长，下标为1的值表示手机振动的时长， 下标为2的值又表示手机静止的时长，以此类推。
         */
        long[] vibrates = {0, 500};
        notification.vibrate = vibrates;
        manager.notify(1, notification);
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
