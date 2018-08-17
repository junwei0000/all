package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class CreatOrderGetMerItemPriceInfo implements Parcelable {

	/**
	 * 
	 */
	String mer_item_price_id;
	String mer_item_price_time_id;
	String mer_price_id;
	String mer_item_id;
	String start_time;
	String end_time;
	String week;
	String start_hour;
	String end_hour;
	String prepay_price;
	String type;
	String accountType;
	String balance_price;// 有余额价
	String balance_mer_price_id;// 有余额价id
	String nobalance_price;// 无余额价
	String nobalance_mer_price_id;// 无余额价id
	String reducedAfterprice;
	String main_deduct_time;
	String isReduced;
	String stadium_id;

	public CreatOrderGetMerItemPriceInfo(String mer_item_price_id, String mer_item_price_time_id, String mer_price_id,
			String mer_item_id, String start_time, String end_time, String week, String start_hour, String end_hour,
			String prepay_price, String type, String accountType, String balance_price, String balance_mer_price_id,
			String nobalance_price, String nobalance_mer_price_id, String reducedAfterprice, String main_deduct_time,
			String isReduced, String stadium_id) {
		super();
		this.mer_item_price_id = mer_item_price_id;
		this.mer_item_price_time_id = mer_item_price_time_id;
		this.mer_price_id = mer_price_id;
		this.mer_item_id = mer_item_id;
		this.start_time = start_time;
		this.end_time = end_time;
		this.week = week;
		this.start_hour = start_hour;
		this.end_hour = end_hour;
		this.prepay_price = prepay_price;
		this.type = type;
		this.accountType = accountType;
		this.balance_price = balance_price;// 有余额价
		this.balance_mer_price_id = balance_mer_price_id;// 有余额价id
		this.nobalance_price = nobalance_price;// 无余额价
		this.nobalance_mer_price_id = nobalance_mer_price_id;// 无余额价id
		this.reducedAfterprice = reducedAfterprice;
		this.main_deduct_time = main_deduct_time;
		this.isReduced = isReduced;
		this.stadium_id = stadium_id;
	}

	public String getStadium_id() {
		return stadium_id;
	}

	public void setStadium_id(String stadium_id) {
		this.stadium_id = stadium_id;
	}

	public String getIsReduced() {
		return isReduced;
	}

	public void setIsReduced(String isReduced) {
		this.isReduced = isReduced;
	}

	public String getMain_deduct_time() {
		return main_deduct_time;
	}

	public void setMain_deduct_time(String main_deduct_time) {
		this.main_deduct_time = main_deduct_time;
	}

	public String getReducedAfterprice() {
		return reducedAfterprice;
	}

	public void setReducedAfterprice(String reducedAfterprice) {
		this.reducedAfterprice = reducedAfterprice;
	}

	public String getMer_item_price_id() {
		return mer_item_price_id;
	}

	public void setMer_item_price_id(String mer_item_price_id) {
		this.mer_item_price_id = mer_item_price_id;
	}

	public String getMer_item_price_time_id() {
		return mer_item_price_time_id;
	}

	public void setMer_item_price_time_id(String mer_item_price_time_id) {
		this.mer_item_price_time_id = mer_item_price_time_id;
	}

	public String getMer_price_id() {
		return mer_price_id;
	}

	public void setMer_price_id(String mer_price_id) {
		this.mer_price_id = mer_price_id;
	}

	public String getMer_item_id() {
		return mer_item_id;
	}

	public void setMer_item_id(String mer_item_id) {
		this.mer_item_id = mer_item_id;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}

	public String getEnd_hour() {
		return end_hour;
	}

	public void setEnd_hour(String end_hour) {
		this.end_hour = end_hour;
	}

	public String getPrepay_price() {
		return prepay_price;
	}

	public void setPrepay_price(String prepay_price) {
		this.prepay_price = prepay_price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBalance_price() {
		return balance_price;
	}

	public void setBalance_price(String balance_price) {
		this.balance_price = balance_price;
	}

	public String getBalance_mer_price_id() {
		return balance_mer_price_id;
	}

	public void setBalance_mer_price_id(String balance_mer_price_id) {
		this.balance_mer_price_id = balance_mer_price_id;
	}

	public String getNobalance_price() {
		return nobalance_price;
	}

	public void setNobalance_price(String nobalance_price) {
		this.nobalance_price = nobalance_price;
	}

	public String getNobalance_mer_price_id() {
		return nobalance_mer_price_id;
	}

	public void setNobalance_mer_price_id(String nobalance_mer_price_id) {
		this.nobalance_mer_price_id = nobalance_mer_price_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

}
