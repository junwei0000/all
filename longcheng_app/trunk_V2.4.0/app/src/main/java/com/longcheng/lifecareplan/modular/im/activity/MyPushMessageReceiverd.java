package com.longcheng.lifecareplan.modular.im.activity;

import android.content.Context;

import io.rong.push.PushType;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * 作者：jun on
 * 时间：2020/5/28 15:25
 * 意图：
 */
public class MyPushMessageReceiverd extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        return false;
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushType pushType, PushNotificationMessage pushNotificationMessage) {
        return false;
    }
}
