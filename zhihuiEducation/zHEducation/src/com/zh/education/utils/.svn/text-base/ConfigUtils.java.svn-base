package com.zh.education.utils;

import java.security.MessageDigest;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
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
	public static final String[] colorArray = new String[] { "#bf5853",
			"#2555e3", "#6abf19", "#db700a", "#00C78C", "#fccd8a", "#87CEEB",
			"#385E0F", "#BDFCC9", "#FFDEAD", "#9C661F", "#3D59AB", "#DA70D6",
			"#B03060", "#00C78C", "#FF00FF", "#A0522D", "#2E8B57", "#802A2A",
			"#3D9140", };

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
	 * 状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public int statusBarHeigth(Activity context) {
		Rect frame = new Rect();
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int contentTop = context.getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT).getTop();
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
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(context.CONNECTIVITY_SERVICE);
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
	public String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String id = tm.getDeviceId() + "-" + tm.getDeviceSoftwareVersion();
		id = "android_" + MD5(id);
		Log.e("getDeviceId", id);
		return id;
	}

	/**
	 * 判断Sdcard是否存在
	 * 
	 */
	public boolean isExsitSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {
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
		Pattern p = Pattern
				.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
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
		Pattern p = Pattern
				.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher m = p.matcher(email);
		return m.matches();
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
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
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
		WifiManager wifiMgr = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
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
	 * */
	public String getVerCode(Context context) {
		String verName = null;
		try {
			verName = context.getPackageManager().getPackageInfo(
					"com.bestdo.stadium.main", 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * 功能说明：获取版本名称
	 * */
	public String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					"com.bestdo.stadium", 0).versionName;
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
}
