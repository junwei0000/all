package com.bestdo.bestdoStadiums.utils;

import java.security.MessageDigest;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.baidu.mapapi.model.LatLng;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;

/**
 * 工具类
 * 
 * @author jun
 * 
 */
public class ConfigUtils {
	private static ConfigUtils mUtils;

	public synchronized static ConfigUtils getInstance() {
		if (mUtils == null) {
			mUtils = new ConfigUtils();
		}
		return mUtils;
	}

	/**
	 * 得到手机屏幕宽高
	 * 
	 * @param context
	 * @return
	 */
	public DisplayMetrics getPhoneWidHeigth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm;
	}

	/**
	 * 获取网络状态 TRUE 有网
	 * 
	 * @param context
	 * @return
	 */
	public boolean getNetWorkStatus(Context context) {
		boolean status = false;
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = networkInfo.isConnected();
		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = networkInfo.isConnected();
		if (isMobileConn || isWifiConn) {
			status = true;
		}
		return status;
	}

	/**
	 * 状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public int statusBarHeigth(Activity context) {
		Rect frame = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int contentTop = context.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
		// statusBarHeight是上面所求的状态栏的高度
		int titleBarHeight = contentTop - statusBarHeight;
		return titleBarHeight;
	}

	/**
	 * 判断是否联网
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean isNetWorkAvaiable(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				return info.isAvailable();
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取设备号-移动终端的软件版本
	 * 
	 * @param context
	 * @return
	 */
	// public String getDeviceId(Context context) {
	// TelephonyManager tm = (TelephonyManager) context
	// .getSystemService(Context.TELEPHONY_SERVICE);
	// String id = tm.getDeviceId() + "-" + tm.getDeviceSoftwareVersion();
	// id = "android_" + MD5(id);
	// Log.e("getDeviceId", id);
	// return id;
	// }

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public String getXngHao() {
		return android.os.Build.MODEL + android.os.Build.VERSION.RELEASE + "";

	}

	/**
	 * 判断Sdcard是否存在
	 * 
	 */
	public boolean isExsitSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 */
	public boolean isNumeric(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");

	}
	/**
	 * 第一个不能为0
	 * 
	 */
	public boolean noyStart0Numeric(String str) {
		return str.matches("^[^0].*");

	}
	
	/**
	 * 是否是手机号 1[0-9]{10}
	 * 
	 * @param mobiles
	 * @return
	 */
	public boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		// Pattern p = Pattern.compile("1[0-9]{10}");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 是否为邮箱格式
	 * 
	 * @param email
	 * @return
	 */
	public boolean isEmail(String email) {
		Pattern p = Pattern.compile(
				"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 第一位是中文或字母，后面是 中文 数字 字母 下划线
	 * ^[\u4e00-\u9fa5A-Za-z][a-zA-Z0-9_\u4e00-\u9fa5]{0,}
	 * 
	 * @param email
	 * @return
	 */

	public boolean isLoginName(String loginname) {
		Pattern p = Pattern.compile("^[a-zA-Z0-9]{0,}");
		Matcher m = p.matcher(loginname);
		return m.matches();
	}

	/**
	 * String regEx = "[^a-zA-Z0-9_\u4e00-\u9fa5]";
	 * 
	 * @param str
	 * @return
	 */
	public String stringFilter(String str) {

		String regEx = "[^a-zA-Z0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 是否满足数字、字母、下划线 Pattern p = Pattern.compile("^\\w+$");
	 * 
	 * @param password
	 * @return
	 */
	public boolean isPasswordNO(String password) {
		Pattern p = Pattern.compile("^[A-Za-z0-9_]+$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * 是否满足数字、字母
	 * 
	 * @param password
	 * @return
	 */
	public boolean isCardNum(String password) {
		Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	/**
	 * 跟php对接的md5加密
	 * 
	 * @param s
	 * @return
	 */
	public String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	 * 获取手机mac地址
	 * 
	 * @param context
	 * @return
	 */
	public String getMAC(Context context) {
		// 在wifi未开启状态下，仍然可以获取MAC地址，但是IP地址必须在已连接状态下否则为0
		String macAddress = null;
		WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			macAddress = info.getMacAddress();
		}
		System.out.println("mac:" + macAddress);
		return macAddress;
	}

	/**
	 * <uses-permissionandroid:name="android.permission.READ_PHONE_STATE" />
	 * 获取手机号
	 * 
	 * @param context
	 * @return
	 */
	public String getPhoneNumber(Context context) {
		// 创建电话管理
		TelephonyManager tm = (TelephonyManager)
		// 与手机建立连接
		context.getSystemService(Context.TELEPHONY_SERVICE);
		// 获取手机号码
		String phoneId = tm.getLine1Number();
		// 记得在manifest file中添加
		return phoneId;
	}

	/**
	 * 功能说明：获取版本号
	 */
	public String getVerCode(Context context) {
		String verName = null;
		try {
			verName = context.getPackageManager().getPackageInfo("com.bestdo.bestdoStadiums", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * 功能说明：获取版本名称
	 */
	public String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo("com.bestdo.bestdoStadiums", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * 生成32位的随机数作为id
	 * 
	 * @param digCount
	 * @return
	 */
	public String getRandomNumber(int digCount) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(digCount);
		for (int i = 0; i < digCount; i++)
			sb.append((char) ('0' + rnd.nextInt(10)));
		return sb.toString();
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	private static final double EARTH_RADIUS = 6378.137;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 根据两点间经纬度坐标（double值），计算两点间距离，
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return 距离：单位为公里
	 */
	public static double DistanceOfTwoPoints(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Double.valueOf(PriceUtils.getInstance().getPriceTwoDecimal(s, 3));
		return s;
	}

	/**
	 * 根据原始数据计算中心坐标和缩放级别，并为地图设置中心坐标和缩放级别。
	 * 
	 * @param pointList
	 */
	public float setZoom(List<LatLng> pointList) {
		float zoom = 19;
		if (pointList != null && pointList.size() > 0) {
			Double maxLng = pointList.get(0).longitude;
			Double minLng = pointList.get(0).longitude;
			Double maxLat = pointList.get(0).latitude;
			Double minLat = pointList.get(0).latitude;
			LatLng res;
			for (int i = 0; i <= pointList.size() - 1; i++) {
				res = pointList.get(i);
				if (res.longitude > maxLng)
					maxLng = res.longitude;
				if (res.longitude < minLng)
					minLng = res.longitude;
				if (res.latitude > maxLat)
					maxLat = res.latitude;
				if (res.latitude < minLat)
					minLat = res.latitude;
			}
			zoom = getZoom(maxLng, minLng, maxLat, minLat);
		}
		return zoom;
	}

	public LatLng getCenterpoint(List<LatLng> pointList) {
		LatLng cenpoint = null;
		if (pointList != null && pointList.size() > 0) {
			Double maxLng = pointList.get(0).longitude;
			Double minLng = pointList.get(0).longitude;
			Double maxLat = pointList.get(0).latitude;
			Double minLat = pointList.get(0).latitude;
			LatLng res;
			for (int i = 0; i <= pointList.size() - 1; i++) {
				res = pointList.get(i);
				if (res.longitude > maxLng)
					maxLng = res.longitude;
				if (res.longitude < minLng)
					minLng = res.longitude;
				if (res.latitude > maxLat)
					maxLat = res.latitude;
				if (res.latitude < minLat)
					minLat = res.latitude;
			}
			Double cenLng = (maxLng + minLng) / 2;
			Double cenLat = (((maxLat + minLat) / 2 + minLat) / 2 + minLat) / 2;

			cenpoint = new LatLng(cenLat, cenLng);

		}
		return cenpoint;
	}

	/**
	 * 根据经纬极值计算绽放级别。
	 * 
	 * @param maxLng
	 * @param minLng
	 * @param maxLat
	 * @param minLat
	 * @return
	 */
	private float getZoom(Double maxLng, Double minLng, Double maxLat, Double minLat) {
		float zoomss = 19;
		double[] zoom = { 50, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 25000, 50000, 100000, 200000, 500000,
				1000000, 2000000 };// 级别18到3。
		double distance = ConfigUtils.DistanceOfTwoPoints(minLat, minLng, maxLat, maxLng); // 获取两点距离,保留小数点后两位
		for (int i = 0, zoomLen = zoom.length; i < zoomLen; i++) {
			if (zoom[i] - distance * 1000 > 0) {
				zoomss = 18 - i + 2;// 之所以会多3，是因为地图范围常常是比例尺距离的10倍以上。所以级别会增加3。
				break;
			}
		}
		return zoomss;
	}
}
