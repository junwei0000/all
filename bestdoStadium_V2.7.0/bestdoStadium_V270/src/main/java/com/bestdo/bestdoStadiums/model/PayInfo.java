package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * 支付model
 */
public class PayInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String appid;
	private String nonce_str;
	private String mch_id;
	private String prepay_id;
	private String sign;
	private String timestamp;
	private String packages;

	public PayInfo() {
		super();
	}

	public PayInfo(String appid, String nonce_str, String mch_id, String prepay_id, String sign, String timestamp,
			String packages) {
		super();
		this.appid = appid;
		this.nonce_str = nonce_str;
		this.mch_id = mch_id;
		this.prepay_id = prepay_id;
		this.sign = sign;
		this.timestamp = timestamp;
		this.packages = packages;
	}

	public String getPackages() {
		return packages;
	}

	public void setPackages(String packages) {
		this.packages = packages;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
