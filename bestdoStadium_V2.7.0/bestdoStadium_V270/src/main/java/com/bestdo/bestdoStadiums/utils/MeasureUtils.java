package com.bestdo.bestdoStadiums.utils;

import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 屏幕操作工具类
 * 
 * @author zhouyou
 */
public class MeasureUtils {

	/**
	 * 获取屏幕宽
	 */
	public static int getWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * 获取屏幕高
	 */
	public static int getHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/** 判断软键盘是否弹起 */
	public static boolean isKeyboardShown(View rootView) {
		final int softKeyboardHeight = 100;
		Rect r = new Rect();
		rootView.getWindowVisibleDisplayFrame(r);
		DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
		int heightDiff = rootView.getBottom() - r.bottom;
		return heightDiff > softKeyboardHeight * dm.density;
	}

	/** 隐藏软件盘 */
	public static void hideKeyboard(Context context, IBinder token) {
		if (token != null) {
			InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}