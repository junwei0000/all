package com.longcheng.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * 作者：MarkShuai
 * 时间：2017/12/20 11:08
 * 邮箱：MarkShuai@163.com
 * 意图：沉浸式效果
 */

public class Immersive {
    /**
     * [沉浸状态栏]
     */
    public static void steepStatusBar(Activity activity) {
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置虚拟导航栏为透明
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar                 顶部导航
     * @param bottomNavigationBar     底部导航
     * @param translucentPrimaryColor 沉浸式效果的主题颜色
     */
    @SuppressLint("NewApi")
    public static void setOrChangeTranslucentColor(Toolbar toolbar, View bottomNavigationBar, int translucentPrimaryColor, Activity activity) {
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (toolbar != null) {
                //先设置toolbar的高度
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                int statusBarHeight = getStatusBarHeight(activity);
                params.height += statusBarHeight;
                toolbar.setLayoutParams(params);
                toolbar.setPadding(
                        toolbar.getPaddingLeft(),
                        toolbar.getPaddingTop() + getStatusBarHeight(activity),
                        toolbar.getPaddingRight(),
                        toolbar.getPaddingBottom());
                toolbar.setBackgroundColor(translucentPrimaryColor);
            }
            if (bottomNavigationBar != null) {
                //解决低版本4.4+的虚拟导航栏的
                if (hasNavigationBarShow(activity.getWindowManager())) {
                    ViewGroup.LayoutParams p = bottomNavigationBar.getLayoutParams();
                    p.height += getNavigationBarHeight(activity);
                    bottomNavigationBar.setLayoutParams(p);
                    //设置底部导航栏的颜色
                    bottomNavigationBar.setBackgroundColor(translucentPrimaryColor);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setNavigationBarColor(translucentPrimaryColor);
            window.setStatusBarColor(translucentPrimaryColor);//状态栏背景
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
        } else {
            //<4.4的，不做处理
        }
    }

    /**
     * 调用此方法设置沉浸式效果，向下兼容4.4
     *
     * @param toolbar                 顶部导航
     * @param bottomNavigationBar     底部导航
     * @param translucentPrimaryColor 沉浸式效果的主题颜色
     */
    @SuppressLint("NewApi")
    public static void setOrChangeTranslucentColorWrite(Toolbar toolbar, View bottomNavigationBar, int translucentPrimaryColor, Activity activity) {
        //判断版本,如果[4.4,5.0)就设置状态栏和导航栏为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (toolbar != null) {
                //先设置toolbar的高度
                ViewGroup.LayoutParams params = toolbar.getLayoutParams();
                int statusBarHeight = getStatusBarHeight(activity);
                params.height += statusBarHeight;
                toolbar.setLayoutParams(params);
                toolbar.setPadding(
                        toolbar.getPaddingLeft(),
                        toolbar.getPaddingTop() + getStatusBarHeight(activity),
                        toolbar.getPaddingRight(),
                        toolbar.getPaddingBottom());
                toolbar.setBackgroundColor(translucentPrimaryColor);
            }
            if (bottomNavigationBar != null) {
                //解决低版本4.4+的虚拟导航栏的
                if (hasNavigationBarShow(activity.getWindowManager())) {
                    ViewGroup.LayoutParams p = bottomNavigationBar.getLayoutParams();
                    p.height += getNavigationBarHeight(activity);
                    bottomNavigationBar.setLayoutParams(p);
                    //设置底部导航栏的颜色
                    bottomNavigationBar.setBackgroundColor(translucentPrimaryColor);
                }
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setNavigationBarColor(translucentPrimaryColor);
            window.setStatusBarColor(translucentPrimaryColor);//状态栏背景
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏黑色字体
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);//恢复状态栏白色字体
        } else {
            //<4.4的，不做处理
        }
    }

    //获取虚拟导航栏高度
    private static int getNavigationBarHeight(Context context) {
        return getSystemComponentDimen(context, "navigation_bar_height");
    }

    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        return getSystemComponentDimen(context, "status_bar_height");
    }

    //获取状态栏的高度
    private static int getSystemComponentDimen(Context context, String dimenName) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            String heightStr = clazz.getField(dimenName).get(object).toString();
            int height = Integer.parseInt(heightStr);
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    //获取底部虚拟导航栏的高度
    @SuppressLint("NewApi")
    private static boolean hasNavigationBarShow(WindowManager wm) {
        Display display = wm.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getRealMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        int widthPixels = outMetrics.widthPixels;
        outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int heightPixels2 = outMetrics.heightPixels;
        int widthPixels2 = outMetrics.widthPixels;
        int w = widthPixels - widthPixels2;
        int h = heightPixels - heightPixels2;
        return w > 0 || h > 0;//竖屏和横屏两种情况。
    }

/**
 * ***************************************************************************************
 */
    /**
     * 背景设置透明
     *
     * @param activity
     */
    public static void setOrChangeTranslucentColorTransparent(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置虚拟导航栏为透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.getWindow().setStatusBarColor(color);
                // 底部导航栏颜色也可以由系统设置
                //activity.getWindow().setNavigationBarColor(color);
            } else {
                setKitKatStatusBarColor(activity, color);
            }
            removeMarginTop(activity);
        }
    }

    // 对于Android4.4，系统没有提供设置状态栏颜色的方法，只能手工搞个假冒的状态栏来占坑
    private static void setKitKatStatusBarColor(Activity activity, int statusBarColor) {
        Window window = activity.getWindow();
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        // 先移除已有的冒牌状态栏
        View fakeView = decorView.findViewWithTag(TAG_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            decorView.removeView(fakeView);
        }
        // 再添加新来的冒牌状态栏
        View statusBarView = new View(activity);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(statusBarColor);
        statusBarView.setTag(TAG_FAKE_STATUS_BAR_VIEW);
        decorView.addView(statusBarView);
    }

    private static final String TAG_FAKE_STATUS_BAR_VIEW = "statusBarView";
    private static final String TAG_MARGIN_ADDED = "marginAdded";

    // 移除顶部间隔，霸占状态栏的位置
    private static void removeMarginTop(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup contentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View child = contentView.getChildAt(0);
        if (TAG_MARGIN_ADDED.equals(child.getTag())) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) child.getLayoutParams();
            // 移除的间隔大小就是状态栏的高度
            params.topMargin -= getStatusBarHeight(activity);
            child.setLayoutParams(params);
            child.setTag(null);
        }
    }


}
