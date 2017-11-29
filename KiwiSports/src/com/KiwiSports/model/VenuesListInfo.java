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

	
	public VenuesListInfo() {
		super();
	}

	public VenuesListInfo(String posid, String uid, String field_name, String sportsType, String thumb,
			String venuestatus, String audit_status) {
		super();
		this.posid = posid;
		this.uid = uid;
		this.field_name = field_name;
		this.sportsType = sportsType;
		this.thumb = thumb;
		this.venuestatus = venuestatus;
		this.audit_status = audit_status;
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
