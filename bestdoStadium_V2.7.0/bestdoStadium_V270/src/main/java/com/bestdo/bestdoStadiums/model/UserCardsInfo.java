package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * 用户卡列表
 * 
 * @author jun
 * 
 */
public class UserCardsInfo implements Serializable {

	private String useStartTime;
	private String useEndTime;
	private String cardTypeName;
	private String projectId;
	private String cardTypeId;
	private String balance;
	private String cardNo;
	private String cardId;
	private int isExpire;
	private String accountType;
	private String accountName;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getIsExpire() {
		return isExpire;
	}

	public void setIsExpire(int isExpire) {
		this.isExpire = isExpire;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(String cardTypeId) {
		this.cardTypeId = cardTypeId;
	}

	public String getUseStartTime() {
		return useStartTime;
	}

	public void setUseStartTime(String useStartTime) {
		this.useStartTime = useStartTime;
	}

	public String getUseEndTime() {
		return useEndTime;
	}

	public void setUseEndTime(String useEndTime) {
		this.useEndTime = useEndTime;
	}

	public UserCardsInfo(String useStartTime2, String useEndTime2, String cardTypeName, String projectId,
			String cardTypeId, String balance, String cardNo, String cardId, int isExpire, String accountType,
			String accountName) {
		super();
		this.useStartTime = useStartTime2;
		this.useEndTime = useEndTime2;
		this.cardTypeName = cardTypeName;
		this.projectId = projectId;
		this.cardTypeId = cardTypeId;
		this.balance = balance;
		this.cardNo = cardNo;
		this.cardId = cardId;
		this.isExpire = isExpire;
		this.accountType = accountType;
		this.accountName = accountName;

	}

}
