package com.bestdo.bestdoStadiums.model;

public class UserBalanceDetailInfo {

	String id;
	String serviceId;
	String relationNo;
	int rechargeConsumeTime;
	String formatterTime;
	String balance;
	String orderNo;
	String orderDesc;
	/**
	 * type 三种类型：
	 * 
	 * "recharge_UNSUBSCRIBE" -> 余额退款
	 * 
	 * "recharge_PAY" -> 余额充值
	 * 
	 * "consume_PAY" -> 余额支付
	 */
	String type;

	public UserBalanceDetailInfo() {
		super();
	}

	public UserBalanceDetailInfo(String id, String serviceId, String relationNo, int rechargeConsumeTime,
			String balance, String orderNo, String orderDesc, String type,String formatterTime) {
		super();
		this.id = id;
		this.serviceId = serviceId;
		this.relationNo = relationNo;
		this.rechargeConsumeTime = rechargeConsumeTime;
		this.balance = balance;
		this.orderNo = orderNo;
		this.orderDesc = orderDesc;
		this.type = type;
		this.formatterTime = formatterTime;
	}

	public String getFormatterTime() {
		return formatterTime;
	}

	public void setFormatterTime(String formatterTime) {
		this.formatterTime = formatterTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getRelationNo() {
		return relationNo;
	}

	public void setRelationNo(String relationNo) {
		this.relationNo = relationNo;
	}

	public int getRechargeConsumeTime() {
		return rechargeConsumeTime;
	}

	public void setRechargeConsumeTime(int rechargeConsumeTime) {
		this.rechargeConsumeTime = rechargeConsumeTime;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
