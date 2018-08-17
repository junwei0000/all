package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class CreatOrderGetDefautCardInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String accountNo;
	String accountName;
	String usableBalance;
	String balancePriceId;
	String noBalancePriceId;
	String amountType;
	String amount;
	String balance;
	String isReduced;
	String accountRuleId;
	String accountBuyNumber;
	String cardId;
	String cardNo;
	String useStartTime;
	String useEndTime;
	String default_price_id;
	String accountType;
	ArrayList<CreatOrderGetDefautCardInfo> mList;

	public ArrayList<CreatOrderGetDefautCardInfo> getmList() {
		return mList;
	}

	public void setmList(ArrayList<CreatOrderGetDefautCardInfo> mList) {
		this.mList = mList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CreatOrderGetDefautCardInfo() {
		super();
	}

	public CreatOrderGetDefautCardInfo(String accountNo, String accountName, String usableBalance,
			String balancePriceId, String noBalancePriceId, String amountType, String amount, String balance,
			String isReduced, String accountRuleId, String accountBuyNumber, String cardId, String cardNo,
			String useStartTime, String useEndTime, String default_price_id, String accountType) {
		super();
		this.accountNo = accountNo;
		this.accountName = accountName;
		this.usableBalance = usableBalance;
		this.balancePriceId = balancePriceId;
		this.noBalancePriceId = noBalancePriceId;
		this.amountType = amountType;
		this.amount = amount;
		this.balance = balance;
		this.isReduced = isReduced;
		this.accountRuleId = accountRuleId;
		this.accountBuyNumber = accountBuyNumber;
		this.cardId = cardId;
		this.cardNo = cardNo;
		this.useStartTime = useStartTime;
		this.useEndTime = useEndTime;
		this.default_price_id = default_price_id;
		this.accountType = accountType;

	}

	public String getDefault_price_id() {
		return default_price_id;
	}

	public void setDefault_price_id(String default_price_id) {
		this.default_price_id = default_price_id;
	}

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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getUsableBalance() {
		return usableBalance;
	}

	public void setUsableBalance(String usableBalance) {
		this.usableBalance = usableBalance;
	}

	public String getBalancePriceId() {
		return balancePriceId;
	}

	public void setBalancePriceId(String balancePriceId) {
		this.balancePriceId = balancePriceId;
	}

	public String getNoBalancePriceId() {
		return noBalancePriceId;
	}

	public void setNoBalancePriceId(String noBalancePriceId) {
		this.noBalancePriceId = noBalancePriceId;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getIsReduced() {
		return isReduced;
	}

	public void setIsReduced(String isReduced) {
		this.isReduced = isReduced;
	}

	public String getAccountRuleId() {
		return accountRuleId;
	}

	public void setAccountRuleId(String accountRuleId) {
		this.accountRuleId = accountRuleId;
	}

	public String getAccountBuyNumber() {
		return accountBuyNumber;
	}

	public void setAccountBuyNumber(String accountBuyNumber) {
		this.accountBuyNumber = accountBuyNumber;
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

}
