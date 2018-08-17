package com.KiwiSports.control.locationService;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.KiwiSports.R;
import com.KiwiSports.control.activity.MainActivity;

/**
 * Android 8.0版本以上定位问题,使用通知栏显示在前台非锁屏状态下可运行定位正常
 * 
 * @author Dell
 * 
 */
public class MyLocationNotification {

	private static NotificationManager manager;

	/**
	 * 添加一个notification
	 */
	@SuppressLint("NewApi")
	public static Notification addNotificaction(Context mContext, String content) {
		Intent intent = new Intent(mContext, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
				intent, PendingIntent.FLAG_ONE_SHOT);
		Notification.Builder builder = new Notification.Builder(mContext);
		builder.setContentIntent(pendingIntent)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("kiwiSports轨迹记录服务").setContentText(content);
		Notification notification = builder.build();
		notification.flags = Notification.FLAG_NO_CLEAR;// 通知无法清除
		notification.visibility = Notification.VISIBILITY_PUBLIC;
		// manager = (NotificationManager) mContext
		// .getSystemService(Context.NOTIFICATION_SERVICE);
		// manager.notify(0, notification);// 发送通知请求
		return notification;
	}

	public static void cancelNotificaction(Context mContext) {
		if (manager != null)
			manager.cancelAll();
	}
}
