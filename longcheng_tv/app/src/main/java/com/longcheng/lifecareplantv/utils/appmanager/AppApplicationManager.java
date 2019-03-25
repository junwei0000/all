package com.longcheng.lifecareplantv.utils.appmanager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 17:35
 * 邮箱：MarkShuai@163.com
 * 意图：获取App应用版本信息
 */

public class AppApplicationManager {
    /**
     * 获取本地Apk版本号
     *
     * @param context 上下文
     * @return int
     * @Author :MarkShuai
     * @DATE :2017/11/22 17:35
     */

    public static String getVersionCode(Context context) {
        String verCode = "";
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verCode;
    }

    /**
     * 获取应用第一次安装日期
     *
     * @param context
     * @param packageName
     * @return
     * @Author :MarkShuai
     * @DATE :2017/11/22 17:35
     */

    public static long getAppFirstInstallTime(Context context, String packageName) {
        long lastUpdateTime = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            lastUpdateTime = packageInfo.firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return lastUpdateTime;
    }

    /**
     * 获取应用更新日期
     *
     * @param context
     * @param packageName
     * @return
     * @Author :MarkShuai
     * @DATE :2017/11/22 17:35
     */
    public static long getAppLastUpdateTime(Context context, String packageName) {
        long lastUpdateTime = 0;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            lastUpdateTime = packageInfo.lastUpdateTime;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return lastUpdateTime;
    }

    /**
     * 是否有权限
     *
     * @param context
     * @param permission
     * @return
     * @Author :MarkShuai
     * @DATE :2017/11/22 17:35
     */

    public static boolean hasPermission(Context context, String permission) {
        if (context != null && !TextUtils.isEmpty(permission)) {
            try {
                PackageManager packageManager = context.getPackageManager();
                if (packageManager != null) {
                    if (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permission, context
                            .getPackageName())) {
                        return true;
                    }
                    Log.d("AppUtils", "Have you  declared permission " + permission + " in AndroidManifest.xml ?");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 安装更新应用
     *
     * @param context
     * @param filePath
     * @return
     * @Author :MarkShuai
     * @DATE :2017/11/22 17:35
     */
    public static boolean installApk(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile() || file.length() <= 0) {
            return false;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        return true;
    }

    /**
     * 结束进程
     *
     * @param context
     * @param pid
     * @param processName
     * @Author :MarkShuai
     * @DATE :2017/11/22 17:35
     */
    public static void killProcesses(Context context, int pid, String processName) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName;
        try {
            if (!processName.contains(":")) {
                packageName = processName;
            } else {
                packageName = processName.split(":")[0];
            }
            activityManager.killBackgroundProcesses(packageName);
            Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(activityManager, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
