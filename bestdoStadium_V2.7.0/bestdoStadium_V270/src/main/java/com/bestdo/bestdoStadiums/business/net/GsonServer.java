package com.bestdo.bestdoStadiums.business.net;

import java.util.HashMap;
import java.util.Map;

import com.bestdo.bestdoStadiums.business.UserWalkingLoadTotalStepBusiness;
import com.bestdo.bestdoStadiums.business.UserWalkingLoadTotalStepBusiness.GetUserWalkingLoadTotalStepCallback;
import com.bestdo.bestdoStadiums.utils.App;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Config;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;

/**
 * Created by YuHua on 2017/5/24 19:38 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class GsonServer {

	/**
	 * 获取企业活动列表
	 * 
	 * @param business
	 */
	public static void getActsInfo(IBusiness business) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", Config.config().getUid());
		map.put("bid", Config.config().getBid());
		map.put("depid", Config.config().getDepid());
		new BaseBusiness(map, Functions.GET_ACTS_INFO, business);
	}

	/**
	 * 获取个人最近一周walk数据
	 * 
	 * @param business
	 */
	public static void getMyWeekWalk(IBusiness business) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", Config.config().getUid());
		map.put("bid", Config.config().getBid());
		map.put("depid", Config.config().getDepid());
		new BaseBusiness(map, Functions.GET_MY_WEEK_WALK, business);
	}

	/**
	 * 获取个人今天walk km
	 * 
	 * @param business
	 */
	public static void getMyWalkKm(IBusiness business) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", Config.config().getUid());
		map.put("bid", Config.config().getBid());
		new BaseBusiness(map, Functions.GET_MY_WALK_KM, business);
	}

	/**
	 * 企业个人排名页
	 * 
	 * @param page
	 *            第一页从0开始
	 * @param business
	 */
	public static void getRankPerson(int page, IBusiness business) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", Config.config().getUid());
		map.put("bid", Config.config().getBid());
		map.put("deptid", Config.config().getDepid());
		map.put("page", "" + (page + 1));
		map.put("pagesize", "" + 10);
		new BaseBusiness(map, Functions.GET_WALK_COMP_RANK, business);
	}

	/**
	 * 企业我的健步走详情
	 * 
	 * @param page
	 *            第一页从0开始
	 * @param business
	 */
	public static void getRankMyOwn(IBusiness business) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", Config.config().getUid());
		map.put("bid", Config.config().getBid());
		map.put("deptid", Config.config().getDepid());
		map.put("page", "" + "1");
		map.put("pagesize", "1");
		new BaseBusiness(map, Functions.GET_WALK_COMP_RANK, business);
	}

	/**
	 * 企业部门排名页
	 * 
	 * @param page
	 *            第一页从0开始
	 * @param business
	 */
	public static void getRankDep(IBusiness business) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("uid", Config.config().getUid());
		map.put("bid", Config.config().getBid());
		map.put("depid", Config.config().getDepid());
		new BaseBusiness(map, Functions.GET_WALK_DEP_RANK, business);
	}

	public static void uploadTodayStep(int step) {
		if (step <= 0)
			return;
		final Map<String, String> map = new HashMap<String, String>();
		map.put("uid", Config.config().getUid());
		map.put("bid", Config.config().getBid());
		map.put("deptid", Config.config().getDepid());
		map.put("step_num", "" + step);
		map.put("data_time", DatesUtils.getInstance().getNowTime("yyyy-MM-dd") + "");
		map.put("source", Constans.getInstance().source);
		map.put("ip", "");
		map.put("app_device",
				CommonUtils.getInstance().getBestDoInfoSharedPrefs(App.getContext()).getString("device_token", ""));
		map.put("m_type", ConfigUtils.getInstance().getXngHao());
		new BaseBusiness(map, Functions.UP_MY_WALK_STEPS, null);
	}
}
