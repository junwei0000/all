package com.longcheng.lifecareplan.push.listener;


import android.content.Context;

import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;

/**
 * Created by chengkai on 2017/8/31.
 */

public interface IXMReceiverListener {

    /**
     * 接收服务器推送的通知消息,消息到达客户端时触发
     *
     * @param context receiver中的上下文
     * @param message receiver返回消息数据
     */
    void xm_onNotificationMessageArrived(Context context, MiPushMessage message);

    /**
     * 当注册小米
     *
     * @param context receiver中的上下文
     * @param message receiver返回消息数据
     */
    void xm_onReceiveRegisterResult(Context context, MiPushCommandMessage message);

    /**
     * 获取给服务器发送命令的结果,结果封装在MiPushCommandMessage类中.
     *
     * @param context receiver中的上下文
     * @param message receiver返回消息数据
     */
    void xm_onCommandResult(Context context, MiPushCommandMessage message);

}
