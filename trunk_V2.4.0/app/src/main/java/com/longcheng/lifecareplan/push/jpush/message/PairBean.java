package com.longcheng.lifecareplan.push.jpush.message;

import java.util.ArrayList;

public class PairBean {
	private String blessed_teacher_pairs_id;
	private String user_id;
	private String blessed_user_id;
	private String start_time;
	private String jieqi_name;
	private String sponsor_user_name;
	private String sponsor_user_phone;
	private String sponsor_user_avatar;
	private int expireTime;

	private ArrayList<PairBean> img_all;
	private String image;

	public String getBlessed_teacher_pairs_id() {
		return blessed_teacher_pairs_id;
	}

	public void setBlessed_teacher_pairs_id(String blessed_teacher_pairs_id) {
		this.blessed_teacher_pairs_id = blessed_teacher_pairs_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getBlessed_user_id() {
		return blessed_user_id;
	}

	public void setBlessed_user_id(String blessed_user_id) {
		this.blessed_user_id = blessed_user_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getJieqi_name() {
		return jieqi_name;
	}

	public void setJieqi_name(String jieqi_name) {
		this.jieqi_name = jieqi_name;
	}

	public String getSponsor_user_name() {
		return sponsor_user_name;
	}

	public void setSponsor_user_name(String sponsor_user_name) {
		this.sponsor_user_name = sponsor_user_name;
	}

	public String getSponsor_user_phone() {
		return sponsor_user_phone;
	}

	public void setSponsor_user_phone(String sponsor_user_phone) {
		this.sponsor_user_phone = sponsor_user_phone;
	}

	public String getSponsor_user_avatar() {
		return sponsor_user_avatar;
	}

	public void setSponsor_user_avatar(String sponsor_user_avatar) {
		this.sponsor_user_avatar = sponsor_user_avatar;
	}

	public int getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

	public ArrayList<PairBean> getImg_all() {
		return img_all;
	}

	public void setImg_all(ArrayList<PairBean> img_all) {
		this.img_all = img_all;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
