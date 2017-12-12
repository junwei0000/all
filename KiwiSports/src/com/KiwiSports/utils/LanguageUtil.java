package com.KiwiSports.utils;

import java.util.Locale;

import com.KiwiSports.control.activity.MainActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

/**
 * 
 * @author Administrator 中英文切换
 */
public class LanguageUtil {
	
	public static boolean idChLanguage(Context mContext){
		String able = mContext.getResources().getConfiguration().locale.getCountry();
		if (able.equals("CN")) {
			return true;
		}
		return false;
	}
	/**
	 * @param isEnglish
	 */
	public static void set(Context mContext) {
		String able = mContext.getResources().getConfiguration().locale.getCountry();
		Configuration configuration = mContext.getResources().getConfiguration();
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
		if (able.equals("CN")) {
			configuration.locale = Locale.SIMPLIFIED_CHINESE;
		} else if (able.equals("AS")) {
			configuration.locale = Locale.ENGLISH;
		} else {
			configuration.locale = Locale.ENGLISH;
		}
		// 更新配置
		mContext.getResources().updateConfiguration(configuration, displayMetrics);
	}
}
