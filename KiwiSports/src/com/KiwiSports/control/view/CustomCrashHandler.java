package com.KiwiSports.control.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.KiwiSports.utils.CommonUtils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 自定义系统的Crash捕捉类，用Toast替换系统的对话框 将软件版本信息，设备信息，出错信息保存在sd卡中，你可以上传到服务器中
 * 
 * @author jun
 * 
 */
public class CustomCrashHandler implements UncaughtExceptionHandler {
	private String TAG = "Activity";
	private Context mContext;
	private String SDCARD_ROOT = Environment.getExternalStorageDirectory().toString();

	private CustomCrashHandler() {
	}

	/**
	 * 单例模式，保证只有一个CustomCrashHandler实例存在
	 * 
	 * @return
	 */
	private static CustomCrashHandler mInstance;

	public static CustomCrashHandler getInstance() {
		if (mInstance == null) {
			mInstance = new CustomCrashHandler();
		}
		return mInstance;
	}

	/**
	 * 异常发生时，系统回调的函数，我们在这里处理一些操作
	 */
	@SuppressWarnings("static-access")
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// 将一些信息保存到SDcard中
		try {
			savaInfoToSD(mContext, ex);
			Log.e("-----crashhandle-----", ex.toString());
			// MobclickAgent.reportError(mContext, ex);
			// 提示用户程序即将退出
			showToast(mContext, "很抱歉，遭遇异常，即将退出！");
			thread.sleep(2000);
			CommonUtils.getInstance().exit();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 为我们的应用程序设置自定义Crash处理
	 */
	public void setCustomCrashHanler(Context context) {
		mContext = context;
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 显示提示信息，需要在线程中显示Toast
	 * 
	 * @param context
	 * @param msg
	 */
	private void showToast(final Context context, final String msg) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
				Looper.loop();
			}
		}).start();
	}

	/**
	 * 获取一些简单的信息,软件版本，手机版本，型号等信息存放在HashMap中
	 * 
	 * @param context
	 * @return
	 */
	private HashMap<String, String> obtainSimpleInfo(Context context) {
		HashMap<String, String> map = new HashMap<String, String>();
		PackageManager mPackageManager = context.getPackageManager();
		PackageInfo mPackageInfo = null;
		try {
			mPackageInfo = mPackageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		map.put("versionName", mPackageInfo.versionName);
		map.put("versionCode", "" + mPackageInfo.versionCode);

		map.put("MODEL", "" + Build.MODEL);
		map.put("SDK_INT", "" + Build.VERSION.SDK_INT);
		map.put("PRODUCT", "" + Build.PRODUCT);

		return map;
	}

	/**
	 * 获取系统未捕捉的错误信息
	 * 
	 * @param throwable
	 * @return
	 */
	private String obtainExceptionInfo(Throwable throwable) {
		StringWriter mStringWriter = new StringWriter();
		PrintWriter mPrintWriter = new PrintWriter(mStringWriter);
		throwable.printStackTrace(mPrintWriter);
		mPrintWriter.close();

		Log.e(TAG, mStringWriter.toString());
		return mStringWriter.toString();
	}

	/**
	 * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
	 * 
	 * @param context
	 * @param ex
	 * @return
	 */
	private String savaInfoToSD(Context context, Throwable ex) {
		String fileName = null;
		StringBuffer sb = new StringBuffer();

		for (Map.Entry<String, String> entry : obtainSimpleInfo(context).entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append(" = ").append(value).append("\n");
		}

		sb.append(obtainExceptionInfo(ex));
		// 判断SD卡是否存在
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File dir = new File(SDCARD_ROOT + File.separator + "bestdo_log" + File.separator);
			if (!dir.exists()) {
				dir.mkdir();
			}

			try {
				fileName = dir.toString() + File.separator + paserTime(System.currentTimeMillis()) + ".txt";
				FileOutputStream fos = new FileOutputStream(fileName);
				fos.write(sb.toString().getBytes());
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
				fileName = paserTime(System.currentTimeMillis()) + "bestdo_log.txt";
				// openFileOutput()方法的第一参数用于指定文件名称，不能包含路径分隔符“/” ，
				// 如果文件不存在，Android 会自动创建它。
				// 创建的文件保存在/data/data/<package name>/files目录，
				phone_outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
				phone_outStream.write(sb.toString().getBytes());
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
	private String paserTime(long milliseconds) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(tz);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String times = format.format(new Date(milliseconds));

		return times;
	}
}
