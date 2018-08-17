package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

import android.view.View;
import android.widget.TextView;

public class StadiumDetailMerItemPriceInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int datetamp;
	private int inventory_summaray;
	private Boolean is_select;// 是否选中
	private String mer_item_price_summary_id;
	private String mer_price_id;
	private String mer_item_id;
	private String day;
	private String min_price;
	private String max_price;
	private Boolean is_order;// 是否有报价，没有时显示不可预订

	/**
	 * week
	 */
	private View weekviews;
	private TextView textview;
	private TextView textviewline;

	public StadiumDetailMerItemPriceInfo() {
		super();
	}

	public StadiumDetailMerItemPriceInfo(int datetamp, int inventory_summaray, Boolean is_select,
			String mer_item_price_summary_id, String mer_price_id, String mer_item_id, String day, String min_price,
			String max_price, Boolean is_order) {
		super();
		this.datetamp = datetamp;
		this.inventory_summaray = inventory_summaray;
		this.is_select = is_select;
		this.mer_item_price_summary_id = mer_item_price_summary_id;
		this.mer_price_id = mer_price_id;
		this.mer_item_id = mer_item_id;
		this.day = day;
		this.min_price = min_price;
		this.max_price = max_price;
		this.is_order = is_order;
	}

	public View getWeekviews() {
		return weekviews;
	}

	public void setWeekviews(View weekviews) {
		this.weekviews = weekviews;
	}

	public TextView getTextview() {
		return textview;
	}

	public void setTextview(TextView textview) {
		this.textview = textview;
	}

	public TextView getTextviewline() {
		return textviewline;
	}

	public void setTextviewline(TextView textviewline) {
		this.textviewline = textviewline;
	}

	public Boolean getIs_order() {
		return is_order;
	}

	public void setIs_order(Boolean is_order) {
		this.is_order = is_order;
	}

	public String getMer_item_price_summary_id() {
		return mer_item_price_summary_id;
	}

	public void setMer_item_price_summary_id(String mer_item_price_summary_id) {
		this.mer_item_price_summary_id = mer_item_price_summary_id;
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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public Boolean getIs_select() {
		return is_select;
	}

	public void setIs_select(Boolean is_select) {
		this.is_select = is_select;
	}

	public int getInventory_summaray() {
		return inventory_summaray;
	}

	public void setInventory_summaray(int inventory_summaray) {
		this.inventory_summaray = inventory_summaray;
	}

	public int getDatetamp() {
		return datetamp;
	}

	public void setDatetamp(int datetamp) {
		this.datetamp = datetamp;
	}

}
