package com.longcheng.volunteer.push.listener;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by chengkai on 2017/8/31.
 */

public interface IJPushReceiverListener {

    /**
     * 接收服务器推送的通知消息,消息到达客户端时触发
     *
     * @param context receiver上下文
     * @param message 消息内容
     */
    void jpush_onNotificationMessageArrived(Context context, Bundle message);

    /**
     * 其他jpush事件,需要的话自行实现
     *
     * @param context receiver上下文
     * @param bundle  receiver中的bundle
     */
    void jpush_onNotificationOtherHandle(Context context, Bundle bundle);

    /**
     * 用户收到到RICH PUSH CALLBACK
     *
     * @param context receiver上下文
     * @param bundle  receiver中的bundle
     */
    void jpush_actionRichpushCallback(Context context, String message, Bundle bundle);

    /**
     * jpush链接状态变化
     *
     * @param context   receiver上下文
     * @param connected 连接状态变化后的状态
     */
    void jpush_actionConnectionChange(Context context, boolean connected);

    /**
     * 获取REGISTRATION_ID
     *
     * @param context receiver上下文
     * @param regId   registerId
     * @param bundle  receiver中的bundle
     */
    void jpush_actionRegistrationId(Context context, String regId, Bundle bundle);

}
