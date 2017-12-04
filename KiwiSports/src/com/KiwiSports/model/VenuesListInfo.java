package com.KiwiSports.model;

public class VenuesListInfo {
	String posid;
	String uid;
	String field_name;
	String sportsType;
	String thumb;
	/**
	 * 场地状态 0：停用； 1：正常。
	 */
	String venuestatus;
	/**
	 * 审核状态 0：未审核； 1：审核成功； -1：审核失败。
	 */
	String audit_status;
	double top_left_x;
	double top_left_y;
	double bottom_right_x;
	double bottom_right_y;

	public VenuesListInfo() {
		super();
	}

	public VenuesListInfo(String posid, String uid, String field_name, String sportsType, String thumb,
			String venuestatus, String audit_status, double top_left_x, double top_left_y, double bottom_right_x,
			double bottom_right_y) {
		super();
		this.posid = posid;
		this.uid = uid;
		this.field_name = field_name;
		this.sportsType = sportsType;
		this.thumb = thumb;
		this.venuestatus = venuestatus;
		this.audit_status = audit_status;
		this.top_left_x = top_left_x;
		this.top_left_y = top_left_y;
		this.bottom_right_x = bottom_right_x;
		this.bottom_right_y = bottom_right_y;
	}

	public double getTop_left_x() {
		return top_left_x;
	}

	public void setTop_left_x(double top_left_x) {
		this.top_left_x = top_left_x;
	}

	public double getTop_left_y() {
		return top_left_y;
	}

	public void setTop_left_y(double top_left_y) {
		this.top_left_y = top_left_y;
	}

	public double getBottom_right_x() {
		return bottom_right_x;
	}

	public void setBottom_right_x(double bottom_right_x) {
		this.bottom_right_x = bottom_right_x;
	}

	public double getBottom_right_y() {
		return bottom_right_y;
	}

	public void setBottom_right_y(double bottom_right_y) {
		this.bottom_right_y = bottom_right_y;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getSportsType() {
		return sportsType;
	}

	public void setSportsType(String sportsType) {
		this.sportsType = sportsType;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getVenuestatus() {
		return venuestatus;
	}

	public void setVenuestatus(String venuestatus) {
		this.venuestatus = venuestatus;
	}

	public String getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(String audit_status) {
		this.audit_status = audit_status;
	}

}
