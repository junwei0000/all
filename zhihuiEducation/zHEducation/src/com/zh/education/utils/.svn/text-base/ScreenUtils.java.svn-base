package com.zh.education.utils;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2015-12-30 下午2:43:57
 * @Description 类描述：获得屏幕相关的辅助类
 */
public class ScreenUtils {
	private ScreenUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 获得屏幕宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 获得状态栏的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取手机Android版本
	 * 
	 * @return true>=4.4
	 */
	@SuppressWarnings("deprecation")
	public static boolean getAndroidOSVersionStatus() {
		String osVersion = "15";
		try {
			osVersion = android.os.Build.VERSION.SDK;
		} catch (NumberFormatException e) {
			osVersion = "15";
		}
		if (compareToPrice("19", osVersion)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 计算两个价格大小,计算两个字符数的大小 result大于0，则firstPrice>sencondPrice-->false;
	 * result等于0，则firstPrice=sencondPrice; result小于0，则firstPrice<sencondPrice;
	 * 
	 * @param firstPrice
	 * @param sencondPrice
	 * @return
	 */
	public static boolean compareToPrice(String morenVersion, String osVersion) {
		BigDecimal mprice = new BigDecimal(morenVersion);
		BigDecimal mstartsum = new BigDecimal(osVersion);
		int result = mprice.compareTo(mstartsum);
		if (result <= 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获取当前屏幕截图，包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**
	 * 获取当前屏幕截图，不包含状态栏
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}
}
