package com.zongyu.elderlycommunity.utils;

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
	public static final String WEB_ROOT = "http://new.bd.app.bestdo.com/2.3.2";
	// public static final String WEB_ROOT =
	// "http://test.bd.app.bestdo.com/2.3.0";

	// **********登录、注册***********
	public static final String GETVERSON = WEB_ROOT + "/version/androidVersion";// 版本更新
	public static final String LOGIN = WEB_ROOT + "/user/login";// 登录
	public static final String FINDPASSWORDGETCODE = WEB_ROOT
			+ "/user/mobileFindPasswordSendCode";// 找回密码获取验证码
	public static final String FINDPWBYPHONE = WEB_ROOT
			+ "/user/findPasswordByMobile";// 找回密码
	public static final String REGISTGETCODE = WEB_ROOT
			+ "/user/mobileRegisterSendCode";// 注册获取验证码
	public static final String REGISTCHECKCODE = WEB_ROOT
			+ "/user/mobileRegisterCheckCode";// 注册时检查验证码
	public static final String REGISTSETPASSWORD = WEB_ROOT + "/user/register";// 注册设置密码
	public static final String FINDPASSWORDGETCODECHECKCODE = WEB_ROOT
			+ "/user/mobileFindPasswordCheckCode";// 找回密码检查验证码
	public static final String UPDATEPW = WEB_ROOT + "/user/editPassword";// 修改密码

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

	/**
	 * welcomeSharedPreferences
	 */
	public String welcomeSharedPrefsKey = "firstLaunch";
	public String welcomeSharedPrefs_LaunchStatusKey = "launchStatus";
	public String bestDoInfoSharedPrefsKey = "bestdo_info";
	// invokingType
	public String invokingTypeByFastLogin = "FastLogin";
	public String invokingTypeByRegist = "regist";
	public String invokingTypeByFindphone = "findpmbyphone";
	public String invokingTypeByBindMobile = "bindMobile";
	/**
	 * 登录后跳转方向
	 */
	public String loginskiptofavoritelist = "favoritelist";
	public String loginskiptoaddfavorite = "addfavorite";//
	public String loginskiptocreateorder = "createorder";
	public String loginskiptoPYLWyuding = "PYLWyuding";
	public String loginskiptologin403 = "login403";

	/**
	 * 登录状态
	 */
	public String loginStatus = "loging";

	/**
	 * 调用版本更新方向
	 */
	public Activity mHomeActivity;
	/**
	 * 当前activity
	 */
	public Activity mCurrentActivity;
	public String exit = "exit";
	public String cancel = "cancel";
}
