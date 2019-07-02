/**
 *
 */
package com.KiwiSports.control.step;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * 辅助工具类
 * @类型名称: Utils
 */
public class StepUtils {
	/**
	 * 开始定位
	 */
	public final static int MSG_LOCATION_START = 0;
	/**
	 * 定位完成
	 */
	public final static int MSG_LOCATION_FINISH = 1;
	/**
	 * 停止定位
	 */
	public final static int MSG_LOCATION_STOP = 2;

	public final static String KEY_URL = "URL";
	public final static String URL_H5LOCATION = "file:///android_asset/location.html";

	/**
	 * 根据定位结果返回定位信息的字符串
	 *
	 * @param location
	 * @return
	 */
	public synchronized static String getLocationStr(AMapLocation location) {
		if (null == location) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		//errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
		if (location.getErrorCode() == 0) {
			sb.append("定位成功" + "\n");
			sb.append("定位类型: " + location.getLocationType() + "\n");
			sb.append("经    度    : " + location.getLongitude() + "\n");
			sb.append("纬    度    : " + location.getLatitude() + "\n");
			sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
			sb.append("提供者    : " + location.getProvider() + "\n");

			sb.append("海    拔    : " + location.getAltitude() + "米" + "\n");
			sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
			sb.append("角    度    : " + location.getBearing() + "\n");
			if (location.getProvider().equalsIgnoreCase(
					android.location.LocationManager.GPS_PROVIDER)) {
				// 以下信息只有提供者是GPS时才会有
				// 获取当前提供定位服务的卫星个数
				sb.append("星    数    : "
						+ location.getSatellites() + "\n");
			}

			//逆地理信息
			sb.append("国    家    : " + location.getCountry() + "\n");
			sb.append("省            : " + location.getProvince() + "\n");
			sb.append("市            : " + location.getCity() + "\n");
			sb.append("城市编码 : " + location.getCityCode() + "\n");
			sb.append("区            : " + location.getDistrict() + "\n");
			sb.append("区域 码   : " + location.getAdCode() + "\n");
			sb.append("地    址    : " + location.getAddress() + "\n");
			sb.append("兴趣点    : " + location.getPoiName() + "\n");
			//定位完成的时间
			sb.append("定位时间: " + formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");

		} else {
			//定位失败
			sb.append("定位失败" + "\n");
			sb.append("错误码:" + location.getErrorCode() + "\n");
			sb.append("错误信息:" + location.getErrorInfo() + "\n");
			sb.append("错误描述:" + location.getLocationDetail() + "\n");
		}
		//定位之后的回调时间
		sb.append("回调时间: " + formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");
		return sb.toString();
	}

	private static SimpleDateFormat sdf = null;

	public synchronized static String formatUTC(long l, String strPattern) {
		if (TextUtils.isEmpty(strPattern)) {
			strPattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (sdf == null) {
			try {
				sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
			} catch (Throwable e) {
			}
		} else {
			sdf.applyPattern(strPattern);
		}
		return sdf == null ? "NULL" : sdf.format(l);
	}

	public static Intent getExplicitIntent(Context context, Intent implicitIntent) {

		if (context.getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.LOLLIPOP) {
			return implicitIntent;
		}

		// Retrieve all services that can match the given intent
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
		// Make sure only one match was found
		if (resolveInfo == null || resolveInfo.size() != 1) {
			return null;
		}
		// Get component info and create ComponentName
		ResolveInfo serviceInfo = resolveInfo.get(0);
		String packageName = serviceInfo.serviceInfo.packageName;
		String className = serviceInfo.serviceInfo.name;
		ComponentName component = new ComponentName(packageName, className);
		// Create a new intent. Use the old one for extras and such reuse
		Intent explicitIntent = new Intent(implicitIntent);
		// Set the component to be explicit
		explicitIntent.setComponent(component);
		return explicitIntent;
	}


	/**
	 * 　　* 保存文件 　　* @param toSaveString 　　* @param filePath
	 */
	public static void saveFile(String toSaveString, String fileName, boolean append) {
		try {
			String sdCardRoot = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			File saveFile = new File(sdCardRoot + "/" + fileName);
			if (!saveFile.exists()) {
				File dir = new File(saveFile.getParent());
				dir.mkdirs();
				saveFile.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(saveFile, append);
			outStream.write(toSaveString.getBytes());
			outStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public static Notification buildNotification(Context context) {
		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentText("service");
		return builder.getNotification();
	}

	public static void startWifi(Context context) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		wm.setWifiEnabled(true);
		wm.reconnect();
	}

	public static boolean isWifiEnabled(Context context) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		return wm.isWifiEnabled();
	}

	public static String getManufacture(Context context) {
		return Build.MANUFACTURER;
	}

	private static String CLOSE_BRODECAST_INTENT_ACTION_NAME="com.KiwiSports.control.step.CloseService";


	public static Intent getCloseBrodecastIntent() {
		return new Intent(CLOSE_BRODECAST_INTENT_ACTION_NAME);
	}

	public static IntentFilter getCloseServiceFilter() {
		return new IntentFilter(CLOSE_BRODECAST_INTENT_ACTION_NAME);
	}

	public static class CloseServiceReceiver extends BroadcastReceiver {

		Service mService;

		public CloseServiceReceiver(Service service) {
			this.mService = service;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			if (mService == null) {
				return;
			}
			mService.onDestroy();
		}
	}
}
