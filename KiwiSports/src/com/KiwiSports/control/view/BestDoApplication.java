package com.KiwiSports.control.view;


import com.KiwiSports.utils.LanguageUtil;

import android.app.Application;

public class BestDoApplication extends Application {
	private static BestDoApplication mInstance = null;
	public boolean m_bKeyRight = true;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		LanguageUtil.set(true, mInstance);
		setCrash();
//		initMap();
//		initWXAPI();
	}


	public static BestDoApplication getInstance() {
		if (null == mInstance) {
			mInstance = new BestDoApplication();
		}
		return mInstance;
	}

//	public IWXAPI msgApi;
//
//	public void initWXAPI() {
//		msgApi = WXAPIFactory.createWXAPI(this, null);
//		msgApi.registerApp("wx1d30dc3cd2adc80c");
//	}

	private void setCrash() {
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		// 异常捕捉
		try {
			CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
			mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

//	private void initMap() {
//		SDKInitializer.initialize(this);
//	}

}
