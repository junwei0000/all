package com.KiwiSports.model;

public class VenuesRankTodayInfo {
	String distanceTraveled;
	String uid;
	String posid;
	String date_time;
	String num;
	String nick_name;

	public VenuesRankTodayInfo(String distanceTraveled, String uid, String posid, String date_time, String num,
			String nick_name) {
		super();
		this.distanceTraveled = distanceTraveled;
		this.uid = uid;
		this.posid = posid;
		this.date_time = date_time;
		this.num = num;
		this.nick_name = nick_name;
	}

	public String getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(String distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

}
