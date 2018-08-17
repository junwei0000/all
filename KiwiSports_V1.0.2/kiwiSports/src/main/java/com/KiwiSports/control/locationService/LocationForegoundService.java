package com.KiwiSports.control.locationService;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.KiwiSports.utils.App;

/**
 *   前台定位service
 */

public class LocationForegoundService extends Service {
	private Timer mTimer = null;
	private TimerTask mTimerTask = null;
	private boolean isStop = false;
	private Thread thread;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Android O上才显示通知栏
		// 触发定时器
		if (!isStop) {
			startTimer();
		}
//		Notification notification = App.getInstance().setNotification();
//		startForeground(110, notification);
//		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        int anHour = 5 * 1000;  
//        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
//        Intent i = new Intent(this, AlarmReceiver.class);
//        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
//        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * ***********************Android
	 * 8.0版本以上定位问题*********************************
	 * 3.充电时可以一直记录,有toast,读写交互等也会一直显示
	 */
	private void startTimer() {
		isStop = true;// 定时器启动后，修改标识，关闭定时器的开关
//		if (mTimer == null) {
//			mTimer = new Timer();
//		}
//		if (mTimerTask == null) {
//			mTimerTask = new TimerTask() {
//				@Override
//				public void run() {
//					do {
//						try {
//							mHandler.sendEmptyMessage(TONGZHI);
//							Thread.sleep(5000);
//						} catch (InterruptedException e) {
//							return;
//						}
//					} while (isStop);
//				}
//			};
//		}
//		if (mTimer != null && mTimerTask != null) {
//			mTimer.schedule(mTimerTask, 0);// 执行定时器中的任务
//		}
		if (thread == null) {
			thread = new Thread() {
				@Override
				public void run() {
					super.run();
					while (isStop) {
						try {
							Thread.sleep(5000);
							mHandler.sendEmptyMessage(TONGZHI);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			thread.start();
		}
	}

	private final int TONGZHI = 10;
	private final int SART = 11;
	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case TONGZHI:
				Notification notification = App.getInstance().setNotification();
				startForeground(110, notification);
				break;
			case SART:
				isStop = true;
				break;
			}
		}
	};

	/**
	 * 停止定时器，初始化定时器开关
	 */
	private void stopTimer() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}
		isStop = false;// 重新打开定时器开关
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 停止定时器
		if (isStop) {
			stopTimer();
		}
	}

	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		LocationForegoundService getService() {
			return LocationForegoundService.this;
		}
	}
}
