package com.bestdo.bestdoStadiums.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by YuHua on 2017/5/23 11:19 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class DpUtil {

	/**
	 * dp 转换成px
	 *
	 * @param dp
	 * @return
	 */
	public static float dpToPx(Context context, float dp) {
		float density = context.getResources().getDisplayMetrics().density;
		return (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
	}

	/**
	 * 得到屏幕宽度
	 *
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics.widthPixels;
	}

	/**
	 * 得到屏幕宽度
	 *
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displayMetrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics.heightPixels;
	}
}
