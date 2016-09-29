package com.zongyu.elderlycommunity.control.view;

import com.zongyu.elderlycommunity.utils.volley.RequestUtils;

import android.app.Application;

public class MyApplication extends Application {
	private static MyApplication mInstance = null;
	public boolean m_bKeyRight = true;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		// 异常捕捉
		CustomCrashHandler mCustomCrashHandler = CustomCrashHandler
				.getInstance();
		mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());

		RequestUtils.init(this);// 请求volley

	}

	public static MyApplication getInstance() {
		if (null == mInstance) {
			mInstance = new MyApplication();
		}
		return mInstance;
	}


}
