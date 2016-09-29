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
	public static final String WEB_ROOT = "http://ixb.api.zongyutech.com";
	// public static final String WEB_ROOT =
	// "http://test.bd.app.bestdo.com/2.3.0";

	// **********登录、注册***********
	public static final String GETVERSON = WEB_ROOT + "/version/androidVersion";// 版本更新
	public static final String LOGIN = WEB_ROOT + "/user/login";// 登录
	public static final String REGISTCHECKPHONE = WEB_ROOT
			+ "/user/checkAccount";// 注册验证手机号
	public static final String REGISTCHECKCODE = WEB_ROOT + "/user/checkCode";// 注册时检查验证码
	public static final String REGISTGETCODE = WEB_ROOT + "/user/sendCode";// 注册获取验证码
	public static final String REGISTSET = WEB_ROOT + "/user/register";// 注册
	public static final String REGISTSETEDIT = WEB_ROOT + "/user/edit";// 编辑用户信息

	public static final String FINDPASSWORDGETCODE = WEB_ROOT
			+ "/user/mobileFindPasswordSendCode";// 找回密码获取验证码
	public static final String FINDPWBYPHONE = WEB_ROOT
			+ "/user/findPasswordByMobile";// 找回密码

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
	public String loginskiptoTiXing = "tixing";
	// public String loginskiptoaddfavorite = "addfavorite";//
	// public String loginskiptocreateorder = "createorder";
	// public String loginskiptoPYLWyuding = "PYLWyuding";
	// public String loginskiptologin403 = "login403";

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
	/**
	 * 性别
	 */
	public String SEX_UNKNOW = "0";
	public String SEX_MALE = "1";
	public String SEX_FAMALE = "2";
}
