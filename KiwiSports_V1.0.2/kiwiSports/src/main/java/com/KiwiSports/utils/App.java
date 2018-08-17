package com.KiwiSports.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.KiwiSports.control.activity.MainStartActivity;
import com.KiwiSports.control.locationService.MyLocationNotification;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

public class App {
	private Application sApp;
	private Handler mainHandler;
	private Thread mainThread;
	public boolean DEBUG = false;
	private volatile static App instance;

	public static App getInstance() {
		if (instance == null) {
			synchronized (App.class) {
				if (instance == null) {
					instance = new App();
				}
			}
		}
		return instance;
	}

	public Handler getMainHandler() {
		return mainHandler;
	}

	public void init(Application app) {
		sApp = app;
		mainHandler = new Handler(app.getMainLooper());
		mainThread = mainHandler.getLooper().getThread();

	}

	public Context getContext() {
		if (sApp == null) {
			throw new IllegalThreadStateException(
					"必须在onCreat方法之前调用init方法，且在init方法之后调用发送请求");
		}
		return sApp;
	}

	public void runInMainThread(Runnable run) {
		if (Thread.currentThread() == mainThread) {
			run.run();
		} else {
			mainHandler.post(run);
		}
	}

	public void runInMainThread(Runnable run, int delayMillis) {
		mainHandler.postDelayed(run, delayMillis);
	}

	public void t(final int resId) {
		t(getContext().getString(resId));
	}

	public void t(final String msg) {
		runInMainThread(new Runnable() {

			@Override
			public void run() {
				Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
			}
		});

	}

	public Notification setNotification() {
		String time = MainStartActivity.duration;
		if (TextUtils.isEmpty(time)) {
			time = "00:00:00";
		}
		String content = "运动时间：" + time + "，运动距离：" + MainStartActivity.Speed
				+ "km";
		Notification notification = MyLocationNotification.addNotificaction(getContext(), content);
		return notification;
	}

	public void cancelNotificaction() {
		MyLocationNotification.cancelNotificaction(getContext());
	}
	public void savaInfoToSD(String sbd) {
		App.getInstance().savaInfoToSD(sbd, sbd, false);
	}
	/**
	 * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
	 * 
	 * @param context
	 * @param ex
	 * @return
	 */
	public String savaInfoToSD(String name, String content, boolean namestyleStu) {
		String fileName = "";
		// 判断SD卡是否存在
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File dir = new File(Environment.getExternalStorageDirectory()
					+ File.separator + "kiwi_gps" + File.separator);
			if (!dir.exists()) {
				dir.mkdir();
			}

			try {
				if (namestyleStu) {
					fileName = dir.toString() + File.separator
							+ paserTime(System.currentTimeMillis()) + name
							+ ".txt";
				} else {
					fileName = dir.toString() + File.separator + name + ".txt";
				}
				FileOutputStream fos = new FileOutputStream(fileName);
				fos.write(content.getBytes());
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// 写入手机内存
		else {
			FileOutputStream phone_outStream;
			try {
				fileName = paserTime(System.currentTimeMillis())
						+ "kiwi_log.txt";
				// openFileOutput()方法的第一参数用于指定文件名称，不能包含路径分隔符“/” ，
				// 如果文件不存在，Android 会自动创建它。
				// 创建的文件保存在/data/data/<package name>/files目录，
				phone_outStream = getContext().openFileOutput(fileName,
						Context.MODE_PRIVATE);
				phone_outStream.write(content.toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fileName;

	}

	/**
	 * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式
	 * 
	 * @param milliseconds
	 * @return
	 */
	@SuppressLint("SimpleDateFormat") private String paserTime(long milliseconds) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(tz);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String times = format.format(new Date(milliseconds));

		return times;
	}
}
