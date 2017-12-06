package com.KiwiSports.utils;

import android.app.Activity;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:05:32
 * @Description 类描述：属性工具类
 */
public class Constans {

	/**
	 * ******************************************************************
	 * 接口定义*************************************************************
	 * ******************************************************************
	 */
	public static final String WEB_ROOT = "http://track.zongyutech.com/api";

	// ********** ***********
	public static final String GETVERSON = WEB_ROOT + "/version/androidVersion";// 版本更新
	public static final String LOGIN = WEB_ROOT + "/user/login";// 登录
	public static final String LOGOUT = WEB_ROOT + "/user/logout";// 注销登录

	public static final String USERUPDATEINFO = WEB_ROOT + "/user/edit";// 修改个人资料
	public static final String USERUPDATEABLUM = WEB_ROOT + "/user/uploadThumb";// 上传头像
	public static final String USERUPDATEABLUMSAVE = WEB_ROOT + "/user/saveThumb";// 保存头像
	public static final String UPDATELOCATION = WEB_ROOT + "/gps/upStream";// 上传(更新)用户位置。

	public static final String VENUESLIST = WEB_ROOT + "/position/list";// 获取场地列表
	public static final String VENUESHOBBY = WEB_ROOT + "/user/getHobby";// 运动偏好
	public static final String VENUESTYPE = WEB_ROOT + "/position/sportsTypes";// 获取场地类型。
	public static final String VENUESADD = WEB_ROOT + "/position/add";// 添加场地
	public static final String VENUESUSERS = WEB_ROOT + "/gps/rangePositionUsers";// 返回一场地围内用户数据。
	public static final String VENUESMYAREAUSERS = WEB_ROOT + "/gps/rangeUsers";// 返回一定范围内用户数据。
	public static final String VENUESRANKTODAY = WEB_ROOT + "/position/distance/day";// 获取场地日排行。
	public static final String VENUESRANKALL = WEB_ROOT + "/position/distance/total";// 获取场地总排行。

	public static final String RECORDLIST = WEB_ROOT + "/record/user";// 用户的轨迹列表。
	/**
	 * ******************************************************************
	 * 常量属性定义**********************************************************
	 * ******************************************************************
	 */
	private static class SingletonHolder {
		private static final Constans INSTANCE = new Constans();
	}

	private Constans() {
	}

	public static final Constans getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public static String WEIXIN_APP_ID = "wxc4ab96e64155fafe";
	public static String WEIXIN_APP_SECRET = "1779b88f3e612c46d0f212fc6a718410";

	/**
	 * welcomeSharedPreferences
	 */
	public String welcomeSharedPrefsKey = "firstLaunch";
	public String welcomeSharedPrefs_LaunchStatusKey = "launchStatus";
	public String bestDoInfoSharedPrefsKey = "mikisports_info";

	/**
	 * 当前activity
	 */
	public Activity mCurrentActivity;
	public Activity mHomeActivity;
	public String exit = "exit";
	public String cancel = "cancel";

	public String loginStatus = "loging";
	/**
	 * 男
	 */
	public int SEX_MALE = 1;
	/**
	 * 女
	 */
	public int SEX_FAMALE = 2;

	public boolean refreshOrLoadMoreLoading = false;
	public boolean mSensorState;
}
