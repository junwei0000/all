package com.KiwiSports.model;

import android.graphics.Bitmap;

public class VenuesUsersInfo {
	String uid;
	String real_name;
	String nick_name;
	String album_url;
	String is_anonymous;
	String current_sports_type;
	double longitude;
	double latitude;
	Bitmap Roundbitmap;

	public VenuesUsersInfo() {
		super();
	}

	public VenuesUsersInfo(String uid, String real_name, String nick_name,
			String album_url, String is_anonymous, double longitude,
			double latitude,String current_sports_type ) {
		super();
		this.uid = uid;
		this.real_name = real_name;
		this.nick_name = nick_name;
		this.album_url = album_url;
		this.is_anonymous = is_anonymous;
		this.longitude = longitude;
		this.latitude = latitude;
		this.current_sports_type = current_sports_type;
	}

	public String getCurrent_sports_type() {
		return current_sports_type;
	}

	public void setCurrent_sports_type(String current_sports_type) {
		this.current_sports_type = current_sports_type;
	}

	public Bitmap getRoundbitmap() {
		return Roundbitmap;
	}

	public void setRoundbitmap(Bitmap roundbitmap) {
		Roundbitmap = roundbitmap;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getAlbum_url() {
		return album_url;
	}

	public void setAlbum_url(String album_url) {
		this.album_url = album_url;
	}

	public String getIs_anonymous() {
		return is_anonymous;
	}

	public void setIs_anonymous(String is_anonymous) {
		this.is_anonymous = is_anonymous;
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

}
