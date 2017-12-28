package com.KiwiSports.model;

public class TrackSaveInfo {

	private String uid;
	private String token;
	private String access_token;
	private String recordDatas;

	public TrackSaveInfo(String uid, String token, String access_token, String recordDatas) {
		super();
		this.uid = uid;
		this.token = token;
		this.access_token = access_token;
		this.recordDatas = recordDatas;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getRecordDatas() {
		return recordDatas;
	}

	public void setRecordDatas(String recordDatas) {
		this.recordDatas = recordDatas;
	}

}
