package com.KiwiSports.model;

public class VenuesRankTodayInfo {
	double distanceTraveled;
	String uid;
	String posid;
	String date_time;
	String num;
	String nick_name;
	String album_url;
	int is_anonymous;
	public VenuesRankTodayInfo(int is_anonymous,double distanceTraveled, String uid, String posid, String date_time, String num,
			String nick_name, String album_url) {
		super();
		this.is_anonymous = is_anonymous;
		this.distanceTraveled = distanceTraveled;
		this.uid = uid;
		this.posid = posid;
		this.album_url = album_url;
		this.date_time = date_time;
		this.num = num;
		this.nick_name = nick_name;
	}

	public int getIs_anonymous() {
		return is_anonymous;
	}

	public void setIs_anonymous(int is_anonymous) {
		this.is_anonymous = is_anonymous;
	}

	public String getAlbum_url() {
		return album_url;
	}

	public void setAlbum_url(String album_url) {
		this.album_url = album_url;
	}

	public double getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(double distanceTraveled) {
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
