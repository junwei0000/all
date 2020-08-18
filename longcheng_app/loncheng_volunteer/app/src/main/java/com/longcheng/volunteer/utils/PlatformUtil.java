package com.longcheng.volunteer.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;

import java.lang.reflect.Method;


/**
 * Created by markShuai on 2017/11/28.
 * android手机平台判断
 */

public class PlatformUtil {

    static {
        LogUtils.e(PlatformUtil.class + "", Build.MANUFACTURER);
    }

    /**
     * 小米
     *
     * @return
     */
    public static boolean isMIUI() {
        return "Xiaomi".equalsIgnoreCase(Build.MANUFACTURER);
    }

    /**
     * 华为
     *
     * @return
     */
    public static boolean isEMUI() {
        return "HUAWEI".equalsIgnoreCase(Build.MANUFACTURER) && !TextUtils.isEmpty(getEmuiVersion());
    }

    /**
     * 三星
     *
     * @return
     */
    public static boolean isSAMSUNG() {
        return "samsung".equalsIgnoreCase(Build.MANUFACTURER);
    }

    /**
     * 获取华为移动服务版本
     *
     * @return 只要返回不是-1，则是华为移动服务版本
     */
    public static int getEmuiMobileVersion(Context context) {
        int result = -1;
        PackageInfo pi = null;
        PackageManager pm = context.getPackageManager();
        try {
            pi = pm.getPackageInfo("com.huawei.hwid", 0);
            if (pi != null) {
                result = pi.versionCode;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }

    /**
     * @return 只要返回不是""，则是EMUI版本
     */
    public static String getEmuiVersion() {
        String emuiVerion = "";
        Class<?>[] clsArray = new Class<?>[]{String.class};
        Object[] objArray = new Object[]{"ro.build.version.emui"};
        try {
            Class<?> SystemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method get = SystemPropertiesClass.getDeclaredMethod("get", clsArray);
            String version = (String) get.invoke(SystemPropertiesClass, objArray);
            LogUtils.d(PlatformUtil.class + "", "get EMUI version is:" + version);
            if (!TextUtils.isEmpty(version)) {
                return version;
            }
        } catch (ClassNotFoundException e) {
            LogUtils.d(PlatformUtil.class + "", " getEmuiVersion wrong, ClassNotFoundException");
        } catch (LinkageError e) {
            LogUtils.d(PlatformUtil.class + "", " getEmuiVersion wrong, LinkageError");
        } catch (NoSuchMethodException e) {
            LogUtils.d(PlatformUtil.class + "", " getEmuiVersion wrong, NoSuchMethodException");
        } catch (NullPointerException e) {
            LogUtils.d(PlatformUtil.class + "", " getEmuiVersion wrong, NullPointerException");
        } catch (Exception e) {
            LogUtils.d(PlatformUtil.class + "", " getEmuiVersion wrong");
        }
        return emuiVerion;
    }

}
