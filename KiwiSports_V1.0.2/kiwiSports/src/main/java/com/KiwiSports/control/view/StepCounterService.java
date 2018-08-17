package com.KiwiSports.control.view;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

//service负责后台的需要长期运行的任务
// 计步器服务
// 运行在后台的服务程序，完成了界面部分的开发后
// 就可以开发后台的服务类StepService
// 注册或注销传感器监听器，在手机屏幕状态栏显示通知，与StepActivity进行通信，走过的步数记到哪里了？？？
public class StepCounterService extends Service {

	private SensorManager mSensorManager;// 传感器服务
	private StepDetector detector;// 传感器监听对象

	private WakeLock wakeLock;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// 创建监听器类，实例化监听对象
		detector = new StepDetector(this);
		// 获取传感器的服务，初始化传感器
		mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		// 注册传感器，注册监听器
		mSensorManager.registerListener(detector,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
		// acquireWakeLock();
	}

	/**
	 * 获取电源锁，保持该服务在屏幕熄灭时仍然获取CPU时，保持运行; **设置忽略电池优化
	 */
	public void acquireWakeLock() {
		if (null == wakeLock) {
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
					| PowerManager.ON_AFTER_RELEASE, getClass()
					.getCanonicalName());
			if (null != wakeLock) {
				Log.i("TESTLOG", "call acquireWakeLock");
				wakeLock.acquire();
			}
		}
	}

	/**
	 * 释放设备电源锁
	 */
	private void releaseWakeLock() {
		if (null != wakeLock && wakeLock.isHeld()) {
			Log.i("TESTLOG", "call releaseWakeLock");
			wakeLock.release();
			wakeLock = null;
		}
	}

	public void setClear() {
		if (detector != null) {
			mSensorManager.unregisterListener(detector);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		setClear();
		releaseWakeLock();
	}

}
