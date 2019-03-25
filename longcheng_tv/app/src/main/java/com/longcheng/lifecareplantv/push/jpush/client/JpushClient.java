package com.longcheng.lifecareplantv.push.jpush.client;

import android.content.Context;
import android.text.TextUtils;

import com.longcheng.lifecareplantv.utils.Utils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by MarkShuai on 2017/11/29.
 */

public class JpushClient {

    private Context applicationContext;

    private static volatile JpushClient INSTANCE;

    private JpushClient(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public static JpushClient getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (JpushClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JpushClient(context);
                }
            }
        }
        return INSTANCE;
    }


    public void init() {
        JPushInterface.init(applicationContext);
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
    }


    // 2.设置 Alias
    public void setJPushAlias(Context context, int sequence, String alias) {
        if (TextUtils.isEmpty(alias)) {
            throw new IllegalArgumentException("alias can not null");
        }
        if (!Utils.isValidTagAndAlias(alias)) {
            throw new IllegalArgumentException("alias can not Chinese");
        }
        JPushInterface.setAlias(context, sequence, alias);
    }

    public boolean getConnectionState() {
        return JPushInterface.getConnectionState(applicationContext);
    }
}
