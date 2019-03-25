package com.longcheng.lifecareplantv.utils;

import android.app.Activity;
import android.content.pm.ShortcutManager;
import android.os.Build;

/**
 * 作者：jun on
 * 时间：2019/3/4 16:50
 * 意图：App Shortcuts实现长按图标显示快捷入口
 * https://blog.csdn.net/zhe_ge_sha_shou/article/details/83626934
 */

public class ShortcutUtils {

    public static void setDynamicShort(Activity mActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            // android 7.1
            ShortcutManager shortcutManager = mActivity.getSystemService(ShortcutManager.class);
//            ShortcutInfo scInfoOne = new ShortcutInfo.Builder(mActivity, "dynamic_one")
//                    .setShortLabel("生命能量")
//                    .setLongLabel("生命能量互祝")
//                    .setIcon(Icon.createWithResource(mActivity, R.mipmap.app_icon))
//                    .setIntents(new Intent[]{
//                            new Intent(Intent.ACTION_MAIN, Uri.EMPTY, mActivity, HelpWithEnergyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),//加该FLAG的目的是让MainActivity作为根activity，清空已有的任务
////                            new Intent(DynamicASOneActivity.ACTION)
//                    })
//                    .build();
//            ShortcutInfo scInfoTwo = new ShortcutInfo.Builder(mActivity, "dynamic_two")
//                    .setShortLabel("生活方式")
//                    .setLongLabel("生活方式互祝")
//                    .setIcon(Icon.createWithResource(mActivity, R.mipmap.app_icon))
//                    .setIntents(new Intent[]{
//                            new Intent(Intent.ACTION_MAIN, Uri.EMPTY, mActivity, LifeStyleActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),//加该FLAG的目的是让MainActivity作为根activity，清空已有的任务
////                            new Intent(DynamicASOneActivity.ACTION)
//                    })
//                    .build();
            //③、为ShortcutManager设置动态快捷方式集合
//            shortcutManager.setDynamicShortcuts(Arrays.asList(scInfoOne, scInfoTwo));
        }
    }
}
