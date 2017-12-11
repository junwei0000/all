package com.KiwiSports.utils;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:27:13
 * @Description 类描述：工具类
 */
public class ConfigUtils {
	private static class SingletonHolder {
		private static final ConfigUtils INSTANCE = new ConfigUtils();
	}

	private ConfigUtils() {
	}

	public static final ConfigUtils getInstance() {
		return SingletonHolder.INSTANCE;
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
	 * deviceID的组成为：渠道标志+识别符来源标志+hash后的终端识别符
	 * 
	 * 渠道标志为： 1，andriod（a）
	 * 
	 * 识别符来源标志： 1， wifi mac地址（wifi）； 2， IMEI（imei）； 3， 序列号（sn）； 4，
	 * id：随机码。若前面的都取不到时，则随机生成一个随机码，需要缓存。
	 * 
	 * @param context
	 * @return
	 */
	public String getDeviceId(Context context) {
		StringBuilder deviceId = new StringBuilder();
		// 渠道标志
		deviceId.append("a");
		try {
			// IMEI（imei）
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String imei = tm.getDeviceId();
			if (!TextUtils.isEmpty(imei)) {
				deviceId.append("imei");
				deviceId.append(imei);
				Log.e("getDeviceId : ", deviceId.toString());
				return deviceId.toString();
			}
			// 序列号（sn）
			String sn = tm.getSimSerialNumber();
			if (!TextUtils.isEmpty(sn)) {
				deviceId.append("sn");
				deviceId.append(sn);
				Log.e("getDeviceId : ", deviceId.toString());
				return deviceId.toString();
			}
			// 如果上面都没有， 则生成一个id：随机码
			String uuid = getUUID(context);
			if (!TextUtils.isEmpty(uuid)) {
				deviceId.append("id");
				deviceId.append(uuid);
				Log.e("getDeviceId : ", deviceId.toString());
				return deviceId.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			deviceId.append("id").append(getUUID(context));
		}
		Log.e("getDeviceId : ", deviceId.toString());
		return deviceId.toString();
	}

	/**
	 * 得到全局唯一UUID
	 */
	public static String getUUID(Context context) {
		String welcomeSPFKey = Constans.getInstance().welcomeSharedPrefsKey;
		SharedPreferences mShare = context.getSharedPreferences(welcomeSPFKey, 0);
		String uuid = "";
		if (mShare != null) {
			uuid = mShare.getString("uuid", "");
		}
		if (TextUtils.isEmpty(uuid)) {
			uuid = UUID.randomUUID().toString();
			Editor welcomeEditor = mShare.edit();
			welcomeEditor.putString("uuid", uuid);
			welcomeEditor.commit();
		}
		return uuid;
	}

	/**
	 * 关闭键盘
	 * 
	 * @param context
	 */
	public void closeSoftInput(Activity context) {
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
				context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}

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
			verName = context.getPackageManager().getPackageInfo("com.KiwiSports", 0).versionName;
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
			verName = context.getPackageManager().getPackageInfo("com.KiwiSports", 0).versionName;
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
		s = Double.valueOf(PriceUtils.getInstance().getPriceTwoDecimal(s, 1));
		return s;
	}
}
