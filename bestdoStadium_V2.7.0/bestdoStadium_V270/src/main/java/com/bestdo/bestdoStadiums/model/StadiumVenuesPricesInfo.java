package com.bestdo.bestdoStadiums.model;

/**
 * 场地片数
 * 
 * @author jun
 * 
 */
public class StadiumVenuesPricesInfo {

	private String day;
	private String week;
	private String min_price;
	private String max_price;
	private String stock_total;
	private String stock_surplus;
	private String stadium_vence_id;
	private String normal_price;

	public String getNormal_price() {
		return normal_price;
	}

	public void setNormal_price(String normal_price) {
		this.normal_price = normal_price;
	}

	public String getStadium_vence_id() {
		return stadium_vence_id;
	}

	public void setStadium_vence_id(String stadium_vence_id) {
		this.stadium_vence_id = stadium_vence_id;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getMin_price() {
		return min_price;
	}

	public void setMin_price(String min_price) {
		this.min_price = min_price;
	}

	public String getMax_price() {
		return max_price;
	}

	public void setMax_price(String max_price) {
		this.max_price = max_price;
	}

	public String getStock_total() {
		return stock_total;
	}

	public void setStock_total(String stock_total) {
		this.stock_total = stock_total;
	}

	public String getStock_surplus() {
		return stock_surplus;
	}

	public void setStock_surplus(String stock_surplus) {
		this.stock_surplus = stock_surplus;
	}

}
