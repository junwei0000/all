/*
 *  http://www.appcodes.cn APP精品源码下载站声明：
 * 1、本站源码为网上搜集或网友提供，如果涉及或侵害到您的版 权，请立即通知我们。
 * 2、 本站提供免费代码只可供研究学习使用，切勿用于商业用途 由此引起一切后果与本站无关。
 * 3、 商业源码请在源码授权范围内进行使用。
 * 4、更多APP精品源码下载请访问:http://www.appcodes.cn。
 * 5、如有疑问请发信息至appcodes@qq.com。
 */
package com.bestdo.bestdoStadiums.control.walking;

import java.util.ArrayList;
import java.util.Calendar;

import com.bestdo.bestdoStadiums.R.bool;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
public class StepAllCounterService extends Service {

	public static Boolean FLAG = false;// 服务运行标志־

	private SensorManager mSensorManager;// 传感器服务
	private StepAllDetector detector;// 传感器监听对象

	private PowerManager mPowerManager;// 电源管理服务
	private WakeLock mWakeLock;// 屏幕灯

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
		detector = new StepAllDetector(this);

		// 获取传感器的服务，初始化传感器
		mSensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		// 注册传感器，注册监听器
		mSensorManager.registerListener(detector, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);

		// // 电源管理服务
		// try {
		// mPowerManager = (PowerManager)
		// this.getSystemService(Context.POWER_SERVICE);
		// if (mPowerManager != null) {
		// mWakeLock = mPowerManager
		// .newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK |
		// PowerManager.ACQUIRE_CAUSES_WAKEUP, "S");
		// if (mWakeLock != null)
		// mWakeLock.acquire();
		// }
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		createAlarm();
	}

	/**
	 * 创建闹钟提醒
	 * 
	 */
	private void createAlarm() {
		AlarmManager alarmManager;
		if (getTimesnight() > System.currentTimeMillis()) {
			alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(this, AlarmReceiver.class); // 创建Intent对象
			intent.putExtra("time", getTimesnight());
			/**
			 * pendingIntent的第4个参数,问题防止数据错位
			 */
			PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT); // 创建PendingIntent
			// alarmManager.set(AlarmManager.RTC_WAKEUP,
			// System.currentTimeMillis(), pi); //设置闹钟
			alarmManager.set(AlarmManager.RTC_WAKEUP, getTimesnight(), pi); // 设置闹钟，当真实提醒时间就唤醒
		}
	}

	// 获得当天24点时间
	public long getTimesnight() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 0);
		return cal.getTimeInMillis();
	}

	public void setClear() {
		FLAG = false;//
		if (detector != null) {
			mSensorManager.unregisterListener(detector);
		}

		if (mWakeLock != null) {
			mWakeLock.release();
		}
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		setClear();
	}
}
