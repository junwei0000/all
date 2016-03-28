package com.zh.education.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.zh.education.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2015-12-31 下午5:05:20
 * @Description 类描述：调整字体大小
 */
public class TextSizeUtils {

	private static TextSizeUtils mUtils;

	public synchronized static TextSizeUtils getInstance() {
		if (mUtils == null) {
			mUtils = new TextSizeUtils();
		}
		return mUtils;
	}

	public int NOWTEXTSIZE = 1;
	public int minTEXTSIZE = 0;
	public int conTEXTSIZE = 1;
	public int maxTEXTSIZE = 2;

	/**
	 * 初始化赋值
	 * 
	 * @param context
	 */
	public void setInit(Context context) {
		NOWTEXTSIZE = (CommonUtils.getInstance()
				.getTextSizeSharedPrefs(context)).getInt("NOWTEXTSIZE",
				conTEXTSIZE);
	}

	/**
	 * 更新字体大小设置
	 * 
	 * @param context
	 * @param sizeIndex
	 */
	public void updateSize(Context context, int sizeIndex) {
		SharedPreferences mSharedPreferences = CommonUtils.getInstance()
				.getTextSizeSharedPrefs(context);
		NOWTEXTSIZE = sizeIndex;
		Editor mEditor = mSharedPreferences.edit();
		mEditor.putInt("NOWTEXTSIZE", sizeIndex);
		mEditor.commit();

	}

	public void setChangeTextSize(TextView text) {
		if (NOWTEXTSIZE == minTEXTSIZE) {
			text.setTextSize(12);
		} else if (NOWTEXTSIZE == maxTEXTSIZE) {
			text.setTextSize(20);
		} else {
			text.setTextSize(16);
		}
	}
}
