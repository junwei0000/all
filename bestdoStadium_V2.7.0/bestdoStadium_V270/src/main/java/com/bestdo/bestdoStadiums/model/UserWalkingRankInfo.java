package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-24 下午5:16:53
 * @Description 类描述：
 */
public class UserWalkingRankInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
	private String step_num;
	private String nick_name;
	private String ablum_url;
	private int num;
	String dept_name;

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStep_num() {
		return step_num;
	}

	public void setStep_num(String step_num) {
		this.step_num = step_num;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getAblum_url() {
		return ablum_url;
	}

	public void setAblum_url(String ablum_url) {
		this.ablum_url = ablum_url;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserWalkingRankInfo(String uid, String step_num, String nick_name, String ablum_url, int num) {
		super();
		this.uid = uid;
		this.step_num = step_num;
		this.nick_name = nick_name;
		this.ablum_url = ablum_url;
		this.num = num;
	}

	public UserWalkingRankInfo() {
		super();
	}

}
