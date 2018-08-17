package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-24 下午5:16:53
 * @Description 类描述：
 */
public class UserWalkingHistoryInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String step_num;
	private String km_num;
	private String v_num;
	private String step_time;
	private int timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStep_num() {
		return step_num;
	}

	public void setStep_num(String step_num) {
		this.step_num = step_num;
	}

	public String getKm_num() {
		return km_num;
	}

	public void setKm_num(String km_num) {
		this.km_num = km_num;
	}

	public String getV_num() {
		return v_num;
	}

	public void setV_num(String v_num) {
		this.v_num = v_num;
	}

	public String getStep_time() {
		return step_time;
	}

	public void setStep_time(String step_time) {
		this.step_time = step_time;
	}

	/**
	 * @return the timestamp
	 */
	public int getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public UserWalkingHistoryInfo(String id, String step_num, String km_num, String v_num, String step_time,
			int timestamp) {
		super();
		this.id = id;
		this.step_num = step_num;
		this.km_num = km_num;
		this.v_num = v_num;
		this.step_time = step_time;
		this.timestamp = timestamp;
	}

	public UserWalkingHistoryInfo() {
		super();
	}

}
