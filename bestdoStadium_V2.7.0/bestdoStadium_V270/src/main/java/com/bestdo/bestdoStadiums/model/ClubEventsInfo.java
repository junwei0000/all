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
public class ClubEventsInfo implements Serializable {
	String start_time;
	String name;
	String act_status;
	String week;
	String day;
	String status_text;
	String activity_id;

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAct_status() {
		return act_status;
	}

	public void setAct_status(String act_status) {
		this.act_status = act_status;
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

	public String getStatus_text() {
		return status_text;
	}

	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activity_id) {
		this.activity_id = activity_id;
	}

	public ClubEventsInfo(String start_time, String name, String act_status, String week, String day,
			String status_text, String activity_id) {
		super();
		this.start_time = start_time;
		this.name = name;
		this.act_status = act_status;
		this.week = week;
		this.day = day;
		this.status_text = status_text;
		this.activity_id = activity_id;
	}

}
