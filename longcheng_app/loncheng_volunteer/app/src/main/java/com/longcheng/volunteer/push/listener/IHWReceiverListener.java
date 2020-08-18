package com.longcheng.volunteer.push.listener;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by chengkai on 2017/8/31.
 */

public interface IHWReceiverListener {

    /**
     * 获取token的结果
     *
     * @param context receiver上下文
     * @param token   token
     * @param bundle  receiver中的bundle
     */
    void hw_onToken(Context context, String token, Bundle bundle);

    /**
     * 查询client链接状态
     *
     * @param context   receiver上下文
     * @param pushState 推送连接状态
     */
    void hw_onPushState(Context context, boolean pushState);

}
