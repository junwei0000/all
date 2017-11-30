package com.KiwiSports.control.view;

import com.KiwiSports.utils.LanguageUtil;
import com.KiwiSports.utils.volley.RequestUtils;
import com.baidu.mapapi.SDKInitializer;

import android.app.Application;

public class MyApplication extends Application {
	private static MyApplication mInstance = null;
	public boolean m_bKeyRight = true;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		setLanguage();
		setVolley();
		setCrash();
		initMap();
	}

	public static MyApplication getInstance() {
		if (null == mInstance) {
			mInstance = new MyApplication();
		}
		return mInstance;
	}

	private void setLanguage() {
		LanguageUtil.set(false, mInstance);
	}

	/**
	 * 请求volley
	 */
	private void setVolley() {
		RequestUtils.init(this);
	}

	/**
	 * 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext 异常捕捉
	 */
	private void setCrash() {
		try {
			CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
			mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
		} catch (Exception e) {
		}
	}

	private void initMap() {
		SDKInitializer.initialize(this);
	}

}
