package com.bestdo.bestdoStadiums.control.map;

import java.io.File;

import com.baidu.mapapi.SDKInitializer;
import com.bestdo.bestdoStadiums.control.service.JobHandleService;
import com.bestdo.bestdoStadiums.control.service.LocalService;
import com.bestdo.bestdoStadiums.control.service.RemoteService;
import com.bestdo.bestdoStadiums.control.walking.StepAllCounterService;
import com.bestdo.bestdoStadiums.utils.App;
import com.bestdo.bestdoStadiums.utils.AppUpdate;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Environment;

public class BestDoApplication extends Application {
	private static BestDoApplication mInstance = null;
	public boolean m_bKeyRight = true;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor bestDoInfoEditor;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(this);
		bestDoInfoEditor = bestDoInfoSharedPrefs.edit();
		App.init(this);
		initHotfix();
		setCrash();
		initMap();
		initWXAPI();
		initService();
		initPush();
		initImageLoad();

	}

	private void initHotfix() {
		SophixManager.getInstance().setContext(this).setAppVersion(AppUpdate.getVersion()).setAesKey(null)
				.setEnableDebug(false).setEnableFixWhenJit().setPatchLoadStatusStub(new PatchLoadStatusListener() {
					@Override
					public void onLoad(final int mode, final int code, final String info,
							final int handlePatchVersion) {
						if (code == PatchStatus.CODE_LOAD_SUCCESS) {
						} else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
						} else if (code == PatchStatus.CODE_LOAD_FAIL) {
							SophixManager.getInstance().cleanPatches();
						} else {
						}
					}
				}).initialize();
		SophixManager.getInstance().queryAndLoadNewPatch();

	}

	public static BestDoApplication getInstance() {
		if (null == mInstance) {
			mInstance = new BestDoApplication();
		}
		return mInstance;
	}

	public IWXAPI msgApi;

	public void initWXAPI() {
		msgApi = WXAPIFactory.createWXAPI(this, null);
		msgApi.registerApp("wx1d30dc3cd2adc80c");
	}

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

	private void initMap() {
		SDKInitializer.initialize(this);
	}

	/**
	 * ***（程序启动问题）****** **1.每次开启程序先启动服务，防止JobHandleService报错不运行计步服务问题* **2.
	 * ******
	 */
	private void initService() {
		try {
			if (Build.VERSION.SDK_INT >= 21) {
				JobHandleService.startServices(this);
				startService(new Intent(this, JobHandleService.class));
			} else {
				this.startService(new Intent(this, LocalService.class));
				this.startService(new Intent(this, RemoteService.class));
				this.startService(new Intent(this, StepAllCounterService.class));
			}
		} catch (Exception e) {
		}
	}

	private void initPush() {
		// JPushInterface.setDebugMode(false); // 设置开启日志,发布时请关闭日志
		// JPushInterface.init(this); // 初始化 JPush
		// MobclickAgent.updateOnlineConfig(this);// 友盟发送策略定义
		// MobclickAgent.openActivityDurationTrack(false);
		RequestUtils.init(this);// 请求volley
		// final PushAgent mPushAgent = PushAgent.getInstance(this);
		// mPushAgent
		// .setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER); //
		// 声音
		// mPushAgent
		// .setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SERVER);//
		// 呼吸灯
		// mPushAgent
		// .setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SERVER);//
		// 振动
		// mPushAgent.setPushCheck(true);
		//
		// // 开启推送并设置注册的回调处理
		// mPushAgent.enable(new IUmengRegisterCallback() {
		//
		// @Override
		// public void onRegistered(final String registrationId) {
		// new Handler().post(new Runnable() {
		// @Override
		// public void run() {
		// // onRegistered方法的参数registrationId即是device_token
		// Log.e("device_token", registrationId);
		// //
		// mPushAgent.setAlias(ConfigUtils.getInstance().getDeviceId(getApplicationContext()),"main_id"
		// // );
		// }
		// });
		// }
		// });
		//
		// String device_token = UmengRegistrar.getRegistrationId(this);
		// Log.e("device_token1", "" + device_token);
		// bestDoInfoEditor.putString("device_token", "" + device_token);
		// bestDoInfoEditor.commit();
		// try {
		// mPushAgent.addAlias(ConfigUtils.getInstance().getDeviceId(getApplicationContext()),"main_id"
		// );
		// } catch (e e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (JSONException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// mPushAgent.setExclusiveAlias(ConfigUtils.getInstance().getDeviceId(getApplicationContext()),"main_id"
		// );

	}

	private void initImageLoad() {
		// 配置ImageLoad
		File cacheDir = new File(Environment.getExternalStorageDirectory() + "/friendcircle/" + "image/");
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.memoryCacheExtraOptions(480, 800).threadPoolSize(2).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().memoryCache(new WeakMemoryCache())
				.memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(100)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 30 * 1000)).writeDebugLogs()
				.defaultDisplayImageOptions(options).build();
		ImageLoader.getInstance().init(config);
	}

}
