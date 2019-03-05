package com.longcheng.lifecareplan.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.lifestyle.activity.LifeStyleActivity;

import java.util.Arrays;

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
//            ShortcutInfo shortcutInfo = new ShortcutInfo.Builder(mActivity, "id1")
//                    .setShortLabel("生命能量").setLongLabel("生命能量互祝")
//                    .setIcon(Icon.createWithResource(mActivity, R.mipmap.app_icon))
//                    .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"))).build();
            ShortcutInfo scInfoOne = new ShortcutInfo.Builder(mActivity, "dynamic_one")
                    .setShortLabel("生命能量")
                    .setLongLabel("生命能量互祝")
                    .setIcon(Icon.createWithResource(mActivity, R.mipmap.app_icon))
                    .setIntents(new Intent[]{
                            new Intent(Intent.ACTION_MAIN, Uri.EMPTY, mActivity, HelpWithEnergyActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),//加该FLAG的目的是让MainActivity作为根activity，清空已有的任务
//                            new Intent(DynamicASOneActivity.ACTION)
                    })
                    .build();
            ShortcutInfo scInfoTwo = new ShortcutInfo.Builder(mActivity, "dynamic_two")
                    .setShortLabel("生活方式")
                    .setLongLabel("生活方式互祝")
                    .setIcon(Icon.createWithResource(mActivity, R.mipmap.app_icon))
                    .setIntents(new Intent[]{
                            new Intent(Intent.ACTION_MAIN, Uri.EMPTY, mActivity, LifeStyleActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK),//加该FLAG的目的是让MainActivity作为根activity，清空已有的任务
//                            new Intent(DynamicASOneActivity.ACTION)
                    })
                    .build();
            //③、为ShortcutManager设置动态快捷方式集合
            shortcutManager.setDynamicShortcuts(Arrays.asList(scInfoOne, scInfoTwo));
        }
    }
}
