package com.bestdo.bestdoStadiums.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.baidu.navisdk.util.common.StringUtils;
import com.bestdo.bestdoStadiums.business.net.RankMyKmResponse;
import com.bestdo.bestdoStadiums.business.net.RankPersonResponse;
import com.bestdo.bestdoStadiums.business.net.RankPersonResponse.UserInfoEntity;
import com.bestdo.bestdoStadiums.model.ActInfoMapper.ActInfo;
import com.bestdo.bestdoStadiums.model.MyWalkInfoMapper.MyWalkInfo;
import com.bestdo.bestdoStadiums.model.MyWalkInfoMapper.MyWalkInfoBack;

import android.content.SharedPreferences;

/**
 * Created by YuHua on 2017/5/24 14:57 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：缓存工具类
 */

public class Config {
	public static boolean DEBUG = true;
	private static final Config config = new Config();
	private MyWalkInfo myWalkInfo;// walk_个人信息
	private RankMyKmResponse myKm;// 个人跑步公里数和消息
	private List<ActInfo> actInfos;// 跑步_活动列表
	private boolean mSensorState;

	private Config() {
		if (DEBUG) {
			// myWalkInfo = new MyWalkInfo();
			// uid = "530160712115925xKE";
			// bid = "64";
			// depid = "239";
		}
	}

	public static Config config() {
		return config;
	}

	public boolean isSensorAvalible() {
		return mSensorState;
	}

	public void setSensorState(boolean avalible) {
		mSensorState = avalible;
	}

	public String getUid() {
		return getSh("uid", "");
	}

	public String getBid() {
		String bid = getSh("bid", "");
		if (StringUtils.isEmpty(bid) || bid.equals("nobid"))
			bid = "0";
		return bid;
	}

	public String getDepid() {
		return getSh("deptId", "");
	}

	public RankMyKmResponse getMyKm() {
		if (myKm == null)
			myKm = new RankMyKmResponse();
		return myKm;
	}

	public void setMyKm(RankMyKmResponse myKm) {
		this.myKm = myKm;
	}

	public MyWalkInfo getMyWalkInfo() {
		if (myWalkInfo == null)
			myWalkInfo = new MyWalkInfo();
		return myWalkInfo;
	}

	public List<ActInfo> getActInfos() {
		if (actInfos == null)
			actInfos = new ArrayList<ActInfo>();
		return actInfos;
	}

	public void setActInfos(List<ActInfo> actInfos) {
		this.actInfos = actInfos;
	}

	public static void d(String url, Map<String, String> map) {
		try {
			if (DEBUG) {
				StringBuilder sb = new StringBuilder();
				for (Entry<String, String> e : map.entrySet()) {
					sb.append(e.getKey() + "=" + e.getValue() + "&");
				}
				url += "?" + sb.toString().substring(0, sb.length() - 1);
			}
			System.out.println("----" + url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void d(String msg) {
		try {
			if (DEBUG)
				System.out.println("----" + msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateMyWalkInfo(List<MyWalkInfoBack> userInfo) {
		MyWalkInfo info = getMyWalkInfo();
		info.myWalkInfoBacks = userInfo;
	}

	public void updateMyWalkInfo(RankPersonResponse re, double time) {
		int step = 0;
		try {
			step = Integer.valueOf(getMyWalkInfo().myWalkInfoBacks.get(6).step_num);
		} catch (Exception e) {
		}
		updateMyWalkInfo(re.getUser_info());
		double timeD = time;
		updateMyCompanyName(re.getCompany_name(), TimeUtils.formatTime(timeD, TimeUtils.FORMAT_YYYYMMDD), step);
	}

	private void updateMyCompanyName(String company, String curTime, int step) {
		MyWalkInfo info = getMyWalkInfo();
		info.company = company;
		info.curTime = curTime;
		updateMyKart(step);
	}

	public void updateCurTime(String curTime) {
		MyWalkInfo info = getMyWalkInfo();
		info.curTime = curTime;
	}

	public void updateMyKart(int step) {
		float kart = step * 0.028f;
		float kilo = step * 0.7f;
		MyWalkInfo info = getMyWalkInfo();
		info.kart = kart;
		info.kilometers = kilo / 1000;
	}

	public void updateMyWalkInfo(UserInfoEntity entity) {
		MyWalkInfo info = getMyWalkInfo();
		info.myName = entity.getNick_name();
		info.curSteps = entity.getStep_num();
		info.no = entity.getNum();
		info.myIcon = entity.getAblum_url();
	}

	/**
	 * 判断用户是B端用户还是C端用户
	 * 
	 * @return
	 */
	public boolean isBuser() {
		String bid = getSh("bid", "nobid");
		return !bid.equals("nobid");
	}

	private String getSh(String key, String defaultV) {
		SharedPreferences sh = CommonUtils.getInstance().getBestDoInfoSharedPrefs(App.getContext());
		return sh.getString(key, defaultV);
	}

	public void clear() {
		myKm = null;
		myWalkInfo = null;
		actInfos = null;
	}
}
