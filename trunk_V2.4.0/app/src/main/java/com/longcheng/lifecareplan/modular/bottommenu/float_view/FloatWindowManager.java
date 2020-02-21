package com.longcheng.lifecareplan.modular.bottommenu.float_view;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;


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

    public FloatWindowManager() {
//        lastWindowInfo = LastWindowInfo.getInstance();
    }

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
                initSystemWindow(context);
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

    public synchronized void updateShowView(String thumburl, String cont) {
        ((FloatView) floatView).updateShowView(thumburl, cont);
    }

    /**
     * 利用系统弹窗实现悬浮窗
     *
     * @param mContext
     */
    private void initSystemWindow(Context mContext) {
        windowManager = SystemUtils.getWindowManager(mContext);
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.packageName = mContext.getPackageName();
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SCALED
                | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        if (float_window_type == FW_TYPE_APP_DIALOG) {
            //这个一定要activity running
            //wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;//TYPE_TOAST
            //TYPE_TOAST targetSDK必须小于26
            wmParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        } else if (float_window_type == FW_TYPE_ALERT_WINDOW) {
            //需要权限
            if (Build.VERSION.SDK_INT >= 26) {
                wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.gravity = Gravity.START | Gravity.TOP;

        wmParams.width = floatViewParams.width;
        wmParams.height = floatViewParams.height;
        wmParams.x = floatViewParams.x;
        wmParams.y = floatViewParams.y;

        if (floatView instanceof View) {
            windowManager.removeView((View) this.floatView);
        }

        floatView = new FloatWindowView(mContext, floatViewParams, wmParams);
        //监听关闭悬浮窗
        floatView.setFloatViewListener(new FloatViewListener() {
            @Override
            public void onClose() {
                dismissFloatWindow();
            }

            @Override
            public void onClick() {

            }

            @Override
            public void onMoved() {

            }

            @Override
            public void onDragged() {

            }
        });
        try {
            final View floatView = (View) this.floatView;
            windowManager.addView(floatView, wmParams);
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