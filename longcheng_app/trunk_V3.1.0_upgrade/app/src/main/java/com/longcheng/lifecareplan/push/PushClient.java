package com.longcheng.lifecareplan.push;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.longcheng.lifecareplan.push.hwpush.HWPushClient;
import com.longcheng.lifecareplan.push.jpush.client.JpushClient;
import com.longcheng.lifecareplan.push.listener.IBasePushReceiverListener;
import com.longcheng.lifecareplan.push.xmpush.XMPushClient;
import com.longcheng.lifecareplan.utils.PlatformUtil;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by markshuai on 2017/11/29.
 */

public class PushClient {

    private Context applicationContext;
    private static volatile PushClient INSTANCE;
    private IBasePushReceiverListener mPushReceiverListener;
    private boolean isOpenJPush = true;
    private boolean isOpenXMPush = true;
    /**
     * 是否都使用jpush
     */
    private boolean isAllUserJPush = false;
    private boolean isOpenHWPush = true;
    public static int sequence = 1;

    private PushClient(Context context) {
        applicationContext = context.getApplicationContext();
    }

    public static PushClient getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (PushClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PushClient(context);
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context, String XM_APP_ID, String XM_APP_KEY) {

        Log.e("Push", "PlatformUtil.isMIUI()-------" + PlatformUtil.isMIUI());
        if (!isAllUserJPush && PlatformUtil.isMIUI()) {
            // XIAOMI
            if (isOpenXMPush) {
                if (TextUtils.isEmpty(XM_APP_ID) || TextUtils.isEmpty(XM_APP_KEY)) {
                    throw new IllegalArgumentException("XM_APP_ID or XM_APP_KEY can not be empty");
                } else {
                    XMPushClient.getINSTANCE(applicationContext).init(XM_APP_ID, XM_APP_KEY);
                }
            }
        } else if (!isAllUserJPush && PlatformUtil.isEMUI()) {
            // HUAWEI
            if (isOpenHWPush) {
                HWPushClient.getINSTANCE(context).init();
            }
        } else {
            // Other
            if (isOpenJPush) {
                JpushClient.getINSTANCE(applicationContext).init();
            }
        }

    }

    public void setAllUserJPush(boolean allUserJPush) {
        isAllUserJPush = allUserJPush;
    }

    public void setOpenCategory(boolean isOpenJPush, boolean isOpenXMPush, boolean isOpenHWPush) {
        this.isOpenJPush = isOpenJPush;
        this.isOpenXMPush = isOpenXMPush;
        this.isOpenHWPush = isOpenHWPush;

    }

    public void setListener(IBasePushReceiverListener listener) {
        if (listener == null)
            throw new IllegalArgumentException("IBasePushReceiverListener can not be null");

        mPushReceiverListener = listener;
    }

    public IBasePushReceiverListener getPushReceiverListener() {
        return mPushReceiverListener;
    }

    /**
     * @param
     * @name 设置别名
     * @time 2017/11/30 16:19
     * @author MarkShuai
     */
    public void setAlias(String alias, TagAliasCallback setJPushAliasListener) {
        if (!isAllUserJPush && PlatformUtil.isMIUI()) {
            // XIAOMI
            XMPushClient.getINSTANCE(applicationContext).setAlias(alias);
        } else if (!isAllUserJPush && PlatformUtil.isEMUI()) {
            // HUAWEI not support
        } else {
            JpushClient.getINSTANCE(applicationContext).setJPushAlias(applicationContext, sequence, alias);
            sequence++;
        }
    }

    /**
     * @param
     * @name 链接状态
     * @time 2017/11/30 16:18
     * @author MarkShuai
     */
    public void getConnectionState() {
        if (PlatformUtil.isMIUI()) {
            // XIAOMI not support
        } else if (PlatformUtil.isEMUI()) {
            // HUAWEI
            HWPushClient.getINSTANCE(applicationContext).getPushStatus();
        } else {
            JpushClient.getINSTANCE(applicationContext).getConnectionState();
        }
    }

    /**
     * @param
     * @name 获取TOKEN
     * @time 2017/11/30 16:18
     * @author MarkShuai
     */
    public String getHWTokenOrRegId() {
        String regId = "";
        if (PlatformUtil.isMIUI()) {
            // XIAOMI
            regId = XMPushClient.getINSTANCE(applicationContext).getRegId();
        } else if (PlatformUtil.isEMUI()) {
            // HUAWEI
            HWPushClient.getINSTANCE(applicationContext).getTokenAsyn();
        } else {
            // JPush
            regId = JPushInterface.getRegistrationID(applicationContext);
        }
        return regId;
    }

}
