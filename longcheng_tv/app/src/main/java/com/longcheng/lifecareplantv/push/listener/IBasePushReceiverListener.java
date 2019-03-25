package com.longcheng.lifecareplantv.push.listener;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by chengkai on 2017/8/30.
 */

public abstract class IBasePushReceiverListener implements IHWReceiverListener, IXMReceiverListener, IJPushReceiverListener {

    /**
     * 点击收到的通知
     *
     * @param message 消息内容
     * @param context receiver上下文
     * @param extras  receiver中的bundle
     */
    public abstract void onPushNotificationClicked(Context context, String message, Bundle extras);

    /**
     * 收到透传消息
     *
     * @param message 推送消息内容内容
     */
    public abstract void onPushPassThroughMessage(Context context, String message);

}
