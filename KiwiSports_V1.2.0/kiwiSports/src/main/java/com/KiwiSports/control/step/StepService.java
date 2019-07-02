package com.KiwiSports.control.step;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.PowerManager;

import com.KiwiSports.control.locationService.IWifiAutoCloseDelegate;
import com.KiwiSports.control.locationService.NetUtil;
import com.KiwiSports.control.locationService.NotiService;
import com.KiwiSports.control.locationService.PowerManagerUtil;
import com.KiwiSports.control.locationService.WifiAutoCloseDelegate;
import com.KiwiSports.control.view.StepPhoneDetector;
import com.KiwiSports.utils.App;
import com.KiwiSports.utils.Constants;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * <p>
 * 项目名称：LocationServiceDemo 类说明：后台服务定位
 * <p>
 * update: 1. 只有在由息屏造成的网络断开造成的定位失败时才点亮屏幕 2. 利用notification机制增加进程优先级
 * </p>
 */
public class StepService extends StepNotiService {

	private StepSensorBase stepSensor; // 计步传感器

	/**
	 * 处理息屏关掉wifi的delegate类
	 */
	private IWifiAutoCloseDelegate mWifiAutoCloseDelegate = new WifiAutoCloseDelegate();

	/**
	 * 记录是否需要对息屏关掉wifi的情况进行处理
	 */
	private boolean mIsWifiCloseable = false;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		applyNotiKeepMech(); // 开启利用notification提高进程优先级的机制

		if (mWifiAutoCloseDelegate.isUseful(getApplicationContext())) {
			mIsWifiCloseable = true;
			mWifiAutoCloseDelegate
					.initOnServiceStarted(getApplicationContext());
		}

		// 开启计步监听
		stepSensor = new StepSensorPedometer(getApplicationContext());
		stepSensor.registerStep();

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		unApplyNotiKeepMech();
		// 注销传感器监听
		stepSensor.unregisterStep();
		super.onDestroy();
	}

}
