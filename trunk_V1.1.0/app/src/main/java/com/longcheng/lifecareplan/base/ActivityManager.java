package com.longcheng.lifecareplan.base;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.utils.ConstantManager;

import java.util.Iterator;
import java.util.Stack;

/**
 * 作者：MarkMingShuai
 * 时间 2017-8-9 13:53
 * 邮箱：mark_mingshuai@163.com
 * 类的意图：Activity的管理工具
 */

public class ActivityManager {

    private static Stack<Activity> activityStack;

    private static ActivityManager instance;


    private ActivityManager() {
    }

    public static ActivityManager getScreenManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public Activity getCurrentActivity() {
        if (activityStack.size() == 0) {
            return null;
        }
        for (int i = 0; i < activityStack.size(); i++) {
            Log.e("ResponseBody", "activityStack=" + activityStack.elementAt(i).toString());
        }
        Activity activity = activityStack.lastElement();
        Log.e("ResponseBody", "activityStack.size()==" + activityStack.size() + "   " + activity.toString());
        return activity;
    }

    //将Activity压入栈
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    //将Activity全部弹出栈
    public void popActivity(Activity activity) {
        if (activity != null) {
            activity.finish();
//            activity.overridePendingTransition(R.anim.fade, R.anim.hold);//注掉为了防止退出时闪现一下黑屏
            activityStack.remove(activity);
            activity = null;
        }
    }

    //将Activity全部弹出栈
    public void popAllActivity() {
        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null) {
                break;
            }
            popActivity(activity);
        }
    }

    //将Activity全部弹出栈,只留首页
    public void popAllActivityOnlyMain() {
        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null || activityStack.size() == 1) {
                break;
            }
            popActivity(activity);
        }
    }

    //将Activity全部弹出栈
    public void popAllActivityExceptOne(Class cls) {
        while (true) {
            Activity activity = getCurrentActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                break;
            }
            popActivity(activity);
        }
    }

    //将Activity弹出栈
    public void popActivity(Class<?> cls) {
        Iterator var3 = this.activityStack.iterator();
        while (var3.hasNext()) {
            Activity activity = (Activity) var3.next();
            if (cls.equals(activity.getClass())) {
                activity.finish();
            }
        }
    }

    /**
     * 返回键返回桌面不退出程序
     *
     * @param mActivity
     */

    public void backHome(Activity mActivity) {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(setIntent);
    }

}
