package com.KiwiSports.utils;

import com.amap.api.maps.AMap;

import java.util.HashMap;


/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:05:32
 * @Description 类描述：属性工具类
 */
public class Constants {

	/**
	 * ******************************************************************
	 * 接口定义*************************************************************
	 * ******************************************************************
	 */
//	public static final String WEB_ROOT = "http://track.zongyutech.com/api";
	public static final String WEB_ROOT = "http://www.kiwiloc.com/api";

	// ********** ***********
	public static final String GETVERSON = WEB_ROOT + "/version/upgrade";// 版本更新
	public static final String LOGIN = WEB_ROOT + "/user/login";// 登录
	public static final String LOGOUT = WEB_ROOT + "/user/logout";// 注销登录

	public static final String USERUPDATEINFO = WEB_ROOT + "/user/edit";// 修改个人资料
	public static final String USERUPDATEABLUM = WEB_ROOT + "/user/uploadThumb";// 上传头像
	public static final String USERUPDATEABLUMSAVE = WEB_ROOT
			+ "/user/saveThumb";// 保存头像
	public static final String UPDATELOCATION = WEB_ROOT + "/gps/upStream";// 上传(更新)用户轨迹。

	public static final String VENUESINFOBYLOCATION = WEB_ROOT
			+ "/gps/locationPosition";// 根据当前经纬度获取所在场地。
	public static final String VENUESLIST = WEB_ROOT + "/position/lists";// 获取场地列表
	public static final String VENUESITEMUSERLIST = WEB_ROOT + "/position/users";// 批量获得场地上用户信息
	public static final String VENUESHOBBY = WEB_ROOT + "/user/getHobby";// 运动偏好
	public static final String VENUESTYPE = WEB_ROOT + "/position/sportsTypes";// 获取场地类型。
	public static final String VENUESADD = WEB_ROOT + "/position/add";// 添加场地
	public static final String VENUESUSERS = WEB_ROOT
			+ "/gps/rangePositionUsers";// 返回一场地围内用户数据。
	public static final String VENUESMYAREAUSERS = WEB_ROOT + "/gps/rangeUsers";// 返回一定范围内用户数据。
	public static final String VENUESRANKTODAY = WEB_ROOT
			+ "/position/distance/day";// 获取场地日排行。
	public static final String VENUESRANKALL = WEB_ROOT
			+ "/position/distance/total";// 获取场地总排行。
	public static final String VENUETREASURE = WEB_ROOT + "/reward/list";// 某一场地下红包
	public static final String AROUNDTREASURE = WEB_ROOT
			+ "/reward/rangeRewards";// 某一范围下的红包
	public static final String GRABTREASURE = WEB_ROOT + "/reward/receive ";// 领取红包

	public static final String RECORDLIST = WEB_ROOT + "/record/user";// 用户的轨迹列表。
	public static final String RECORDCALENDER = WEB_ROOT + "/record/userHistory";// 返回当月的运动数据- 日历
	public static final String RECORDDETAILYOU = WEB_ROOT + "/record/posStream";// 返回一个用户一个场地的轨迹数据
	public static final String RECORDDETAIL = WEB_ROOT + "/record/info";// 用户一个轨迹数据
	public static final String RECORDDEL = WEB_ROOT + "/record/del";// 删除用户一个轨迹数据
	public static final String RECORDLOAD = WEB_ROOT + "/record/upStream";// 用户的轨迹上传。
	public static final String initRecord = WEB_ROOT + "/record/initRecord";// 用户本地开始记录轨迹时,必须向服务器申请record_data_id,
	public static final String USERTREASURE = WEB_ROOT + "/reward/myReward";// 我的红包领取记录

	/**
	 * ******************************************************************
	 * 常量属性定义**********************************************************
	 * ******************************************************************
	 */
	private static class SingletonHolder {
		private static final Constants INSTANCE = new Constants();
	}

	private Constants() {
	}

	public static final Constants getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public static String WEIXIN_APP_ID = "wxfaa988c3d3177266";
	public static String WEIXIN_APP_SECRET = "6e938aefcbb0bb3d7e89fbbdb0e61e2a";
	/**
	 * welcomeSharedPreferences
	 */
	public String welcomeSharedPrefsKey = "firstLaunch";
	public String welcomeSharedPrefs_LaunchStatusKey = "launchStatus";
	public String bestDoInfoSharedPrefsKey = "mikisports_info";

	public String exit = "exit";
	public String cancel = "cancel";

	public String loginStatus = "loging";
	/**
	 * 男
	 */
	public String SEX_MALE = "1";
	/**
	 * 女
	 */
	public String SEX_FAMALE = "2";

	public boolean refreshOrLoadMoreLoading = false;
	public boolean mSensorState;

	public static int BaiduMapTYPE = AMap.MAP_TYPE_SATELLITE;//AMap.MAP_TYPE_SATELLITE
	public static int BaiduMapTYPE_NORMAL = AMap.MAP_TYPE_NORMAL;
	/**
	 * 后台运行的广播
	 */
	public static final String RECEIVER_ACTION = "location_in_background";

	public static final String RECEIVER_ACTION_STEP = "STEP";
	/**
	 * 刷新首页属性页面数据
	 */
	public static final String RECEIVER_ACTION_REFRESHPROPERTY = "RECEIVER_ACTION_REFRESHPROPERTY";
	/**
	 * 刷新首页状态
	 */
	public static final String RECEIVER_ACTION_REFRESHMAIN = "RECEIVER_ACTION_REFRESHMAIN";
	/**
	 * 是否获取达到经纬度
	 */
	public boolean haveGetMyLocationStauts = false;
	public double longitude_me = -1;
	public double latitude_me = -1;

	public boolean addTrackStatus = false;//是否新增一条运动记录
	public int showMonthnum = 6;//日历运动记录显示月数



	public static final int BGSportsTypeUnknown = -1;
	public static final int BGSportsTypeWalk = 0;       // @"走路"
	public static final int BGSportsTypeRun = 1;        // @"跑步"
	public static final int BGSportsTypeBike = 2;       // @"自行车"
	public static final int BGSportsTypeDrive = 3;      // @"开车"
	public static final int BGSportsTypeClimb = 4;      // @"爬山"
	public static final int BGSportsTypeCrossCountryRun = 5; // @"越野跑"
	public static final int BGSportsTypeMountainBike = 6;  // @"山地车越野"
	public static final int BGSportsTypeSkate = 7;       // 轮滑
	public static final int BGSportsTypeMotor = 8;          // @"摩托车"
	public static final int BGSportsTypeShip = 9;           // 轮船
	public static final int BGSportsTypeSkateboard = 10;         // 滑板
	public static final int BGSportsTypeLongboard = 11;     // @"长板"
	public static final int BGSportsTypeRideHorse = 12;     // @"骑马"
	public static final int BGSportsTypeSnowboard = 13;     // @"单板滑雪"
	public static final int BGSportsTypeSki = 14;           // @"双板滑雪"
	public static final int BGSportsTypeSkiTouring = 15;       // @"登山滑雪"
	public static final int BGSportsTypeWindSurfing = 16;       // @"帆板"
	public static final int BGSportsTypeKiteSurfing = 17;   // @"风筝冲浪"
	public static final int BGSportsTypeHighSpeedTrain = 18;         //  @"高铁"
    public static final int BGSportsTypePlane = 19;         //  @"飞机"
}
