package com.bestdo.bestdoStadiums.model;

/**
 * @author qyy pylw一天的价格info
 */
public class StadiumDetailOneDayMerItemPriceInfo {

	private String mer_item_id;
	private String prepay_price;
	private String mer_price_id;
	private String week;
	private String mer_item_price_time_id;

	public StadiumDetailOneDayMerItemPriceInfo(String mer_item_id, String prepay_price, String mer_price_id,
			String week, String mer_item_price_time_id) {
		super();
		this.mer_item_id = mer_item_id;
		this.prepay_price = prepay_price;
		this.mer_price_id = mer_price_id;
		this.week = week;
		this.mer_item_price_time_id = mer_item_price_time_id;
	}

	public String getMer_item_id() {
		return mer_item_id;
	}

	public void setMer_item_id(String mer_item_id) {
		this.mer_item_id = mer_item_id;
	}

	public String getPrepay_price() {
		return prepay_price;
	}

	public void setPrepay_price(String prepay_price) {
		this.prepay_price = prepay_price;
	}

	public String getMer_price_id() {
		return mer_price_id;
	}

	public void setMer_price_id(String mer_price_id) {
		this.mer_price_id = mer_price_id;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getMer_item_price_time_id() {
		return mer_item_price_time_id;
	}

	public void setMer_item_price_time_id(String mer_item_price_time_id) {
		this.mer_item_price_time_id = mer_item_price_time_id;
	}

}
