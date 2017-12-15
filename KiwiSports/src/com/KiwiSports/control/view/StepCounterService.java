 
package com.KiwiSports.control.view;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

//service负责后台的需要长期运行的任务
// 计步器服务
// 运行在后台的服务程序，完成了界面部分的开发后
// 就可以开发后台的服务类StepService
// 注册或注销传感器监听器，在手机屏幕状态栏显示通知，与StepActivity进行通信，走过的步数记到哪里了？？？
public class StepCounterService extends Service {

	public static Boolean FLAG = false;// 服务运行标志־

	private SensorManager mSensorManager;// 传感器服务
	private StepDetector detector;// 传感器监听对象

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		FLAG = true;// 标记为服务正在运行

		// 创建监听器类，实例化监听对象
		detector = new StepDetector(this);

		// 获取传感器的服务，初始化传感器
		mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		// 注册传感器，注册监听器
		mSensorManager.registerListener(detector, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);
	}

	public void setClear() {
		FLAG = false;// ����ֹͣ
		if (detector != null) {
			mSensorManager.unregisterListener(detector);
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		setClear();
	}

}
