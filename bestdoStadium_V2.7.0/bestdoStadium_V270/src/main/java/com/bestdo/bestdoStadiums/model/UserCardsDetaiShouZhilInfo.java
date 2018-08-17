package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * 卡收支详情
 */
public class UserCardsDetaiShouZhilInfo implements Serializable {
	private String relationNo;
	private String createTime;
	private String recordType;
	private String typeName;
	private String balance;

	public String getRelationNo() {
		return relationNo;
	}

	public void setRelationNo(String relationNo) {
		this.relationNo = relationNo;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public UserCardsDetaiShouZhilInfo(String relationNo, String createTime, String recordType, String typeName,
			String balance) {
		super();
		this.relationNo = relationNo;
		this.createTime = createTime;
		this.recordType = recordType;
		this.typeName = typeName;
		this.balance = balance;
	}

}
