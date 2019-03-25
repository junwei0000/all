package com.longcheng.lifecareplantv.push.hwpush;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import com.huawei.hms.support.api.push.PushReceiver;
import com.longcheng.lifecareplantv.push.PushClient;
import com.longcheng.lifecareplantv.utils.LogUtils;


/**
 * 应用需要创建一个子类继承com.huawei.hms.support.api.push.PushReceiver，
 * 实现onToken，onPushState ，onPushMsg，onEvent，这几个抽象方法，用来接收token返回，push连接状态，透传消息和通知栏点击事件处理。
 * onToken 调用getToken方法后，获取服务端返回的token结果，返回token以及belongId
 * onPushState 调用getPushState方法后，获取push连接状态的查询结果
 * onPushMsg 推送消息下来时会自动回调onPushMsg方法实现应用透传消息处理。本接口必须被实现。 在开发者网站上发送push消息分为通知和透传消息
 * 通知为直接在通知栏收到通知，通过点击可以打开网页，应用 或者富媒体，不会收到onPushMsg消息
 * 透传消息不会展示在通知栏，应用会收到onPushMsg
 * onEvent 该方法会在设置标签、点击打开通知栏消息、点击通知栏上的按钮之后被调用。由业务决定是否调用该函数。
 * <p>
 * 受限说明：
 * 由于是通过广播触发，所以当应用的进程不存在时可能由于系统原因无法通过广播方式拉起应用处理通知栏点击事件等。
 * 需要在手机上面为应用设置“允许自启动”才能在进程不存在时正常处理PushReceiver的广播
 */
public class HWPushReceiver extends PushReceiver {
    private String TAG = "HWPushReceiver";

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        PushClient.getINSTANCE(context).getPushReceiverListener().hw_onToken(context, token, extras);
        LogUtils.d(TAG, "onToken() -> token = " + token + ",belongId = " + extras.getString("belongId"));
    }


    @Override
    public boolean onPushMsg(Context context, byte[] msg, Bundle bundle) {
        try {
            PushClient.getINSTANCE(context).getPushReceiverListener()
                    .onPushPassThroughMessage(context, new String(msg, "UTF-8"));
            LogUtils.d(TAG, "onPushMsg() -> 收到一条Push消息： " + new String(msg, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            String content = "onPushMsg() -> 收到一条Push消息： " + e.getMessage();
            LogUtils.d(TAG, content);
        }
        return false;
    }

    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        if (Event.NOTIFICATION_OPENED.equals(event) //通知栏中的通知被点击打开
                || Event.NOTIFICATION_CLICK_BTN.equals(event)) { //通知栏中通知上的按钮被点击
            PushClient.getINSTANCE(context).getPushReceiverListener()
                    .onPushNotificationClicked(context, extras.getString(BOUND_KEY.pushMsgKey), extras);
            int notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.cancel(notifyId);
            }
        }
        String content = "onEvent() -> " + extras.getString(BOUND_KEY.pushMsgKey);
        LogUtils.d(TAG, content);
        super.onEvent(context, event, extras);
    }

    @Override
    public void onPushState(Context context, boolean pushState) {
        super.onPushState(context, pushState);
        PushClient.getINSTANCE(context).getPushReceiverListener().hw_onPushState(context, pushState);
        LogUtils.i(TAG, "Push连接状态为:" + pushState);
    }
}
