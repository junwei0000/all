/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午6:33:59
 * @Description 类描述：
 */
public class CampaignListInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String activity_id;
	String name;
	int start_time;
	int end_time;
	String gps;
	String max_peo;
	String situs;
	String rule;
	String logo;
	String club_id;
	String club_name;
	double longitude;
	double latitude;
	/**
	 * 1是已签到，2未签到
	 */
	String is_sign;
	/**
	 * 0不可以编辑-进行中，1可编辑-报名中
	 */
	String is_edit;
	/**
	 * 0进行中1已结束2取消
	 */
	String act_status;
	String status_txt;
	String week;
	String day;

	public CampaignListInfo() {
		super();
	}

	public CampaignListInfo(String activity_id, String name, int start_time, int end_time, String gps, String max_peo,
			String situs, String rule, String logo, String act_status, String status_txt, String week, String day) {
		super();
		this.activity_id = activity_id;
		this.name = name;
		this.start_time = start_time;
		this.end_time = end_time;
		this.gps = gps;
		this.max_peo = max_peo;
		this.situs = situs;
		this.rule = rule;
		this.logo = logo;
		this.act_status = act_status;
		this.status_txt = status_txt;
		this.week = week;
		this.day = day;
	}

	public String getIs_sign() {
		return is_sign;
	}

	public void setIs_sign(String is_sign) {
		this.is_sign = is_sign;
	}

	public String getIs_edit() {
		return is_edit;
	}

	public void setIs_edit(String is_edit) {
		this.is_edit = is_edit;
	}

	public String getClub_id() {
		return club_id;
	}

	public void setClub_id(String club_id) {
		this.club_id = club_id;
	}

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStart_time() {
		return start_time;
	}

	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}

	public int getEnd_time() {
		return end_time;
	}

	public void setEnd_time(int end_time) {
		this.end_time = end_time;
	}

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getMax_peo() {
		return max_peo;
	}

	public void setMax_peo(String max_peo) {
		this.max_peo = max_peo;
	}

	public String getSitus() {
		return situs;
	}

	public void setSitus(String situs) {
		this.situs = situs;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getAct_status() {
		return act_status;
	}

	public void setAct_status(String act_status) {
		this.act_status = act_status;
	}

	public String getStatus_txt() {
		return status_txt;
	}

	public void setStatus_txt(String status_txt) {
		this.status_txt = status_txt;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

}
