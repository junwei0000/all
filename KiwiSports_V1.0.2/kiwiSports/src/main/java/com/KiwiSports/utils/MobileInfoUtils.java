package com.KiwiSports.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * 手机权限设置
 */
public class MobileInfoUtils {
    private static MobileInfoUtils instance;

    private MobileInfoUtils() {
    }

    public static synchronized MobileInfoUtils getInstance() {
        if (instance == null) {
            instance = new MobileInfoUtils();
        }
        return instance;
    }

    /**
     * 获取手机类型
     */
    private String getMobileType() {
        return Build.MANUFACTURER;
    }
    /**
     * 获取手机型号
     */
    private String getMobileBRAND() {
        return android.os.Build.BRAND;
    }

    /**
     * 跳转至授权页面
     *
     * @param context
     */
    public void jumpStartInterface(Context context) {
        Intent intent = new Intent();
        try {
            //HUAWEI PLK-TL01H unknown HWPLK PLK-TL01HC01B389 HONORPLK-TL01H PLK-TL01H user user HONOR
            //HUAWEI EML unknown HWEML EML-AL00 8.1.0.112(C00) HUAWEIEML-AL00 EML-AL00 user user HUAWEI
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.e("HLQ_Struggle", "******************当前手机类型为："
                    + getMobileType()+"手机型号:"+getMobileBRAND());
            Log.e("HLQ_Struggle", " "+ getMobileType()
                    +" "+android.os.Build.BOARD
                    +" "+android.os.Build.BOOTLOADER
                    +" "+android.os.Build.DEVICE
                    +" "+android.os.Build.DISPLAY
                    +" "+android.os.Build.ID
                    +" "+android.os.Build.MODEL
                    +" "+android.os.Build.TYPE
                    +" "+android.os.Build.TYPE
                    +" "+getMobileBRAND());
            ComponentName componentName = null;
            if (getMobileType().equals("Xiaomi")) { // 红米Note4测试通过
                componentName = new ComponentName("com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity");

            } else if (getMobileType().equals("Letv")) { // 乐视2测试通过
                intent.setAction("com.letv.android.permissionautoboot");
            } else if (getMobileType().equals("samsung")) { // 三星Note5测试通过
                // componentName = new
                // ComponentName("com.samsung.android.sm_cn",
                // "com.samsung.android.sm.ui.ram.AutoRunActivity");
                // componentName =
                // ComponentName.unflattenFromString("com.samsung.android.sm/.ui.ram.RamActivity");//
                // Permission Denial not exported from uid 1000，不允许被其他程序调用
                componentName = ComponentName
                        .unflattenFromString("com.samsung.android.sm/.app.dashboard.SmartManagerDashBoardActivity");
            } else if (getMobileType().equals("HUAWEI")) { // 华为测试通过
                componentName = ComponentName
                        .unflattenFromString("com.huawei.systemmanager/.startupmgr.ui.StartupNormalAppListActivity");// 跳自启动管理
                if(getMobileBRAND().equals("HONOR")){
                    componentName = new ComponentName("com.huawei.systemmanager",
                            "com.huawei.systemmanager.optimize.process.ProtectActivity");//锁屏清理
                }
                
                // SettingOverlayView.show(context);
            } else if (getMobileType().equals("vivo")) { // VIVO测试通过
                componentName = ComponentName
                        .unflattenFromString("com.iqoo.secure/.safeguard.PurviewTabActivity");
            } else if (getMobileType().equals("Meizu")) { // 万恶的魅族
                // componentName =
                // ComponentName.unflattenFromString("com.meizu.safe/.permission.PermissionMainActivity");//跳转到手机管家
                componentName = ComponentName
                        .unflattenFromString("com.meizu.safe/.permission.SmartBGActivity");// 跳转到后台管理页面
            } else if (getMobileType().equals("OPPO")) { // OPPO R8205测试通过
                componentName = ComponentName
                        .unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity");
            } else if (getMobileType().equals("ulong")) { // 360手机 未测试
                componentName = new ComponentName(
                        "com.yulong.android.coolsafe",
                        ".ui.activity.autorun.AutoRunListActivity");
            } else {
                // 将用户引导到系统设置页面
                if (Build.VERSION.SDK_INT >= 9) {
                    Log.e("HLQ_Struggle", "APPLICATION_DETAILS_SETTINGS");
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package",
                            context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setClassName("com.android.settings",
                            "com.android.settings.InstalledAppDetails");
                    intent.putExtra("com.android.settings.ApplicationPkgName",
                            context.getPackageName());
                }
            }
            intent.setComponent(componentName);
            context.startActivity(intent);

        } catch (Exception e) {// 抛出异常就直接打开设置页面
            Log.e("HLQ_Struggle", e.getLocalizedMessage());
            intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }

    /**
     * 跳转锁屏清理
     *
     * @param context
     */
    public void skipAppLockScreenProtection(Context context) {
        try {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
            intent.setComponent(componentName);
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    /**
     * 跳转应该系统设置页面
     *
     * @param context
     * @return
     */
    public Intent skipAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
        return localIntent;
    }

}
