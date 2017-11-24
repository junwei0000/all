package com.KiwiSports.utils;

import java.util.Locale;

import com.KiwiSports.control.activity.MainActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
/**
 * 
 * @author Administrator
 * 中英文切换
 */
public class LanguageUtil {
	 /**
     * @param isEnglish true  ：点击英文，把中文设置未选中
     *                  false ：点击中文，把英文设置未选中
     */
    public static void set(boolean isEnglish,Context mContext) {

        Configuration configuration =mContext.getResources().getConfiguration();
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文
            configuration.locale = Locale.ENGLISH;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        mContext.getResources().updateConfiguration(configuration, displayMetrics);
    }
}
