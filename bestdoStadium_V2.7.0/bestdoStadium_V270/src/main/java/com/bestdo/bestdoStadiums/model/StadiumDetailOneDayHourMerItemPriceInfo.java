package com.bestdo.bestdoStadiums.model;

/**
 * @author qyy 高尔夫、练习场、游泳健身一天的价格info
 */
public class StadiumDetailOneDayHourMerItemPriceInfo {

	String visitor_price;
	String mer_item_id;
	String mer_price_id;
	String vip_price;
	String end_hour;
	String start_hour;
	String end_time;
	String prepay_price;
	String create_time;
	String start_time;
	String week;
	String mer_item_price_time_id;
	int dayindex;

	public StadiumDetailOneDayHourMerItemPriceInfo() {
		super();
	}

	public StadiumDetailOneDayHourMerItemPriceInfo(String visitor_price, String mer_item_id, String mer_price_id,
			String vip_price, String end_hour, String start_hour, String end_time, String prepay_price,
			String create_time, String start_time, String week, String mer_item_price_time_id) {
		super();
		this.visitor_price = visitor_price;
		this.mer_item_id = mer_item_id;
		this.mer_price_id = mer_price_id;
		this.vip_price = vip_price;
		this.end_hour = end_hour;
		this.start_hour = start_hour;
		this.end_time = end_time;
		this.prepay_price = prepay_price;
		this.create_time = create_time;
		this.start_time = start_time;
		this.week = week;
		this.mer_item_price_time_id = mer_item_price_time_id;
	}

	/**
	 * @return the dayindex
	 */
	public int getDayindex() {
		return dayindex;
	}

	/**
	 * @param dayindex
	 *            the dayindex to set
	 */
	public void setDayindex(int dayindex) {
		this.dayindex = dayindex;
	}

	public String getMer_price_id() {
		return mer_price_id;
	}

	public void setMer_price_id(String mer_price_id) {
		this.mer_price_id = mer_price_id;
	}

	public String getVisitor_price() {
		return visitor_price;
	}

	public void setVisitor_price(String visitor_price) {
		this.visitor_price = visitor_price;
	}

	public String getMer_item_id() {
		return mer_item_id;
	}

	public void setMer_item_id(String mer_item_id) {
		this.mer_item_id = mer_item_id;
	}

	public String getVip_price() {
		return vip_price;
	}

	public void setVip_price(String vip_price) {
		this.vip_price = vip_price;
	}

	public String getEnd_hour() {
		return end_hour;
	}

	public void setEnd_hour(String end_hour) {
		this.end_hour = end_hour;
	}

	public String getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getPrepay_price() {
		return prepay_price;
	}

	public void setPrepay_price(String prepay_price) {
		this.prepay_price = prepay_price;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
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
