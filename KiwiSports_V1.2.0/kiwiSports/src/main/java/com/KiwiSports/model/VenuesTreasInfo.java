package com.KiwiSports.model;

import android.graphics.Bitmap;

public class VenuesTreasInfo {
	String reward_item_id;
	String reward_id;
	String uid;
	String nick_name;
	String album_url;
	double longitude;
	double latitude;
	String name;
	String money;
	String is_receive;// 1.已领取；0.未领取
	String receive_time;
	String thumb;
	String type;// 1.等额红包；2.随机红包；3.实物
	Bitmap Roundbitmap;
	String is_exchange;// 0 未兑换 1 已兑换
	String sequence;
	public VenuesTreasInfo() {
		super();
	}

	public VenuesTreasInfo(String reward_item_id, String reward_id, String uid,
			String nick_name, String album_url, double longitude,
			double latitude, String name, String money, String is_receive,
			String receive_time, String thumb, String type,String sequence) {
		super();
		this.reward_item_id = reward_item_id;
		this.reward_id = reward_id;
		this.uid = uid;
		this.nick_name = nick_name;
		this.album_url = album_url;
		this.longitude = longitude;
		this.latitude = latitude;
		this.name = name;
		this.money = money;
		this.is_receive = is_receive;
		this.type = type;
		this.receive_time = receive_time;
		this.thumb = thumb;
		this.sequence = sequence;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getIs_exchange() {
		return is_exchange;
	}

	public void setIs_exchange(String is_exchange) {
		this.is_exchange = is_exchange;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public Bitmap getRoundbitmap() {
		return Roundbitmap;
	}

	public void setRoundbitmap(Bitmap roundbitmap) {
		Roundbitmap = roundbitmap;
	}

	public String getReward_item_id() {
		return reward_item_id;
	}

	public void setReward_item_id(String reward_item_id) {
		this.reward_item_id = reward_item_id;
	}

	public String getReward_id() {
		return reward_id;
	}

	public void setReward_id(String reward_id) {
		this.reward_id = reward_id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getIs_receive() {
		return is_receive;
	}

	public void setIs_receive(String is_receive) {
		this.is_receive = is_receive;
	}

	public String getReceive_time() {
		return receive_time;
	}

	public void setReceive_time(String receive_time) {
		this.receive_time = receive_time;
	}

}
