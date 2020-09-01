package com.longcheng.lifecareplan.modular.bottommenu.float_view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.longcheng.lifecareplan.push.jpush.message.EasyMessage;
import com.longcheng.lifecareplan.push.jpush.message.OnMessageListener;


/**
 * FloatWindowManager:管理悬浮窗视频播放
 * android.view.WindowManager$BadTokenException:
 * Unable to add window android.view.ViewRootImpl$W@123e0ab --
 * permission denied for this window 2003,type
 *
 * @author Nonolive-杜乾 Created on 2017/12/12-17:35.
 * E-mail:dusan.du@nonolive.com
 */

public class FloatWindowManager {
    public static final int FW_TYPE_ROOT_VIEW = 10;
    public static final int FW_TYPE_APP_DIALOG = 11;
    public static final int FW_TYPE_ALERT_WINDOW = 12;
    private int float_window_type = 0;
    private IFloatView floatView;
    private boolean isFloatWindowShowing = false;
    private FrameLayout contentView;
    private FloatViewParams floatViewParams;
    private WindowManager windowManager;
    //    private LastWindowInfo lastWindowInfo;
    private Activity activity;
    private OnShowMessageListener mOnShowMessageListener;

    public void setmOnShowMessageListener(OnShowMessageListener mOnShowMessageListener) {
        this.mOnShowMessageListener = mOnShowMessageListener;
    }

    public FloatWindowManager() {
//        lastWindowInfo = LastWindowInfo.getInstance();
        //接收
        EasyMessage.registerMessageListener("flag", mListener);
    }

    //处理消息
    public OnMessageListener mListener = new OnMessageListener() {
        public void onMessage(Object msg) {
//            long newTime = System.currentTimeMillis();
//            //打印Demo
//            Log.e("OnMessageListener", "OnMessageListener=" + msg);
//            updateShowView("", "" + msg);
            mOnShowMessageListener.onShowMessage();
        }
    };

    /**
     * 显示悬浮窗口
     */
    public synchronized void showFloatWindow(Activity baseActivity, int floatWindowType) {
        if (baseActivity == null) {
            return;
        }
        activity = baseActivity;
        Context mContext = baseActivity.getApplicationContext();
        showFloatWindow(mContext, floatWindowType);
    }

    private synchronized void showFloatWindow(Context context, int floatWindowType) {
        if (context == null) {
            return;
        }
        float_window_type = floatWindowType;
        try {
            isFloatWindowShowing = true;
            floatViewParams = initFloatViewParams(context);
            if (float_window_type == FW_TYPE_ROOT_VIEW) {
                initCommonFloatView(context);
            } else {
//                initSystemWindow(context);
            }
            isFloatWindowShowing = true;
        } catch (Exception e) {
            e.printStackTrace();
            isFloatWindowShowing = false;
        }
    }

    /**
     * 直接在activity根布局添加悬浮窗
     *
     * @param mContext
     */
    private void initCommonFloatView(Context mContext) {
        if (activity == null || mContext == null) {
            return;
        }
        try {
            floatView = new FloatView(mContext, floatViewParams);
            View rootView = activity.getWindow().getDecorView().getRootView();
            contentView = rootView.findViewById(android.R.id.content);
            contentView.addView((View) floatView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化窗口参数
     *
     * @param mContext
     * @return
     */
    private FloatViewParams initFloatViewParams(Context mContext) {
        FloatViewParams params = new FloatViewParams();
        int screenWidth = SystemUtils.getScreenWidth(mContext);
        int screenHeight = SystemUtils.getScreenHeight(mContext, false);
        int statusBarHeight = SystemUtils.getStatusBarHeight(mContext);
        Log.d("dq", "screenWidth=" + screenWidth + ",screenHeight=" + screenHeight + ",statusBarHeight=" + statusBarHeight);
        params.screenWidth = screenWidth;
        params.screenHeight = screenHeight;
        params.statusBarHeight = statusBarHeight;
        return params;
    }


    public IFloatView getFloatView() {
        return floatView;
    }

    /**
     * 隐藏悬浮视频窗口
     */
    public synchronized void dismissFloatWindow() {
        if (!isFloatWindowShowing) {
            Log.d("dq", "dismissFloatWindow false");
            return;
        }
        try {
            isFloatWindowShowing = false;
            removeWindow();

            if (contentView != null && floatView != null) {
                contentView.removeView((View) floatView);
            }
            floatView = null;
            windowManager = null;
            contentView = null;
            activity = null;//防止activity泄漏
        } catch (Exception e) {
        }
    }

    private void removeWindow() {
        if (windowManager != null && floatView != null) {
            windowManager.removeViewImmediate((View) floatView);
        }
    }
}