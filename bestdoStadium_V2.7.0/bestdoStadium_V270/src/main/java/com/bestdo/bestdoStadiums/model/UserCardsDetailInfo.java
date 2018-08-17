package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * 卡详情
 */
public class UserCardsDetailInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	String cardId;
	String accountNo;
	String accountType;
	String cardNo;
	String uid;
	String cardBatchId;
	String cardTypeName;
	String companyId;
	String useStartTime;
	String useEndTime;
	String status2;
	String amount;
	String buyableMerList;
	String balance;
	String activeTime;

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCardBatchId() {
		return cardBatchId;
	}

	public void setCardBatchId(String cardBatchId) {
		this.cardBatchId = cardBatchId;
	}

	public String getCardTypeName() {
		return cardTypeName;
	}

	public void setCardTypeName(String cardTypeName) {
		this.cardTypeName = cardTypeName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBuyableMerList() {
		return buyableMerList;
	}

	public void setBuyableMerList(String buyableMerList) {
		this.buyableMerList = buyableMerList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserCardsDetailInfo(String cardId, String cardNo, String uid, String cardBatchId, String cardTypeName,
			String companyId, String useStartTime, String useEndTime, String status2, String amount,
			String buyableMerList, String balance, String activeTime, String accountNo, String accountType) {
		super();
		this.cardId = cardId;
		this.cardNo = cardNo;
		this.uid = uid;
		this.cardBatchId = cardBatchId;
		this.cardTypeName = cardTypeName;
		this.companyId = companyId;
		this.useStartTime = useStartTime;
		this.useEndTime = useEndTime;
		this.status2 = status2;
		this.amount = amount;
		this.buyableMerList = buyableMerList;
		this.balance = balance;
		this.activeTime = activeTime;
		this.accountNo = accountNo;
		this.accountType = accountType;

	}

}
