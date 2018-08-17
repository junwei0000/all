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
public class CampaignRegularListInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String name;
	int start_time;
	int end_time;
	String max_peo;
	String address;
	String rule;
	String advance_time;
	/**
	 * 1启用状态 2停用状态 3删除状态
	 */
	String status;
	String auto_txt;
	String act_time;
	double longitude;
	double latitude;

	public CampaignRegularListInfo(String id, String name, int start_time, int end_time, String max_peo, String address,
			String rule, String advance_time, String status, String auto_txt, String act_time, double longitude,
			double latitude) {
		super();
		this.id = id;
		this.name = name;
		this.start_time = start_time;
		this.end_time = end_time;
		this.max_peo = max_peo;
		this.address = address;
		this.rule = rule;
		this.advance_time = advance_time;
		this.status = status;
		this.auto_txt = auto_txt;
		this.act_time = act_time;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getMax_peo() {
		return max_peo;
	}

	public void setMax_peo(String max_peo) {
		this.max_peo = max_peo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getAdvance_time() {
		return advance_time;
	}

	public void setAdvance_time(String advance_time) {
		this.advance_time = advance_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAuto_txt() {
		return auto_txt;
	}

	public void setAuto_txt(String auto_txt) {
		this.auto_txt = auto_txt;
	}

	public String getAct_time() {
		return act_time;
	}

	public void setAct_time(String act_time) {
		this.act_time = act_time;
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

}
