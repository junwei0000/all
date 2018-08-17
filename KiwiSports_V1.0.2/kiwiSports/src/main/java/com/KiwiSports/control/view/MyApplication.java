package com.KiwiSports.control.view;

import java.io.File;

import com.KiwiSports.utils.App;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.LanguageUtil;
import com.KiwiSports.utils.volley.RequestUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import android.app.Application;
import android.os.Environment;

public class MyApplication extends Application {
	private static MyApplication mInstance = null;
	public boolean m_bKeyRight = true;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		App.getInstance().init(mInstance);
		setLanguage();
		initWXAPI();
		setVolley();
		setCrash();
		initMap();
		initImageLoad();
	}

	public static MyApplication getInstance() {
		if (null == mInstance) {
			mInstance = new MyApplication();
		}
		return mInstance;
	}
	public IWXAPI msgApi;

	public void initWXAPI() {
		msgApi = WXAPIFactory.createWXAPI(this, null);
		msgApi.registerApp(Constants.WEIXIN_APP_ID);
	}
	private void setLanguage() {
		LanguageUtil.set(mInstance);
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
			CustomCrashHandler mCustomCrashHandler = CustomCrashHandler
					.getInstance();
			mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
		} catch (Exception e) {
		}
	}

	private void initMap() {
	}

	private void initImageLoad() {
		// 配置ImageLoad
		File cacheDir = new File(Environment.getExternalStorageDirectory()
				+ "/kiwi/" + "image/");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCacheExtraOptions(480, 800)
				.threadPoolSize(2)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new WeakMemoryCache())
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(getApplicationContext(),
								5 * 1000, 30 * 1000)).writeDebugLogs()
				.defaultDisplayImageOptions(options).build();
		ImageLoader.getInstance().init(config);
	}
}
