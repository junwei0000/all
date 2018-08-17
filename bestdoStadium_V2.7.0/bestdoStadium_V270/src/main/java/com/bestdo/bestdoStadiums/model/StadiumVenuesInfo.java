package com.bestdo.bestdoStadiums.model;

import java.util.ArrayList;
import android.widget.TextView;

/**
 * 场馆场地
 * 
 * @author jun
 * 
 */
public class StadiumVenuesInfo {

	private String stadium_venue_id;
	private String stadium_id;
	private String name;
	private String cid;
	private String contain;
	private String venuce_is_order;
	private String normal_price;
	/**
	 * 周期型场地的有效期字段
	 */
	private String expiration_time;
	/**
	 * 3.周期型 ；当为3的时候是团购订单
	 */
	private String set_stock_type;
	/**
	 * 乒羽篮网弹窗判断的字段说明：used_stadium_manage（使用场馆管理系统0 不使用, 1 使用 ）；当不使用的时候弹窗
	 */
	private String used_stadium_manage;

	private ArrayList<StadiumVenuesPricesInfo> venuePricesPYLWList;
	TextView textview;
	TextView textviewline;
	private String is_booknow;
	/**
	 * venuces
	 */
	private String stadium_venue_priceruleid;// 1、2、3
	private String stadium_venue_pricerulename;
	private ArrayList<StadiumVenuesInfo> mvenuespriceruleitemList;
	private ArrayList<StadiumVenuesInfo> mpricesList;
	private String stadium_venue_minprice;// 每条的最小价格
	/**
	 * price time
	 */
	private String price_id;
	private String start_hour;
	private String end_hour;
	private String prepay_price;
	private String cash_price;

	public String getExpiration_time() {
		return expiration_time;
	}

	public void setExpiration_time(String expiration_time) {
		this.expiration_time = expiration_time;
	}

	public String getSet_stock_type() {
		return set_stock_type;
	}

	public void setSet_stock_type(String set_stock_type) {
		this.set_stock_type = set_stock_type;
	}

	public String getUsed_stadium_manage() {
		return used_stadium_manage;
	}

	public void setUsed_stadium_manage(String used_stadium_manage) {
		this.used_stadium_manage = used_stadium_manage;
	}

	public String getNormal_price() {
		return normal_price;
	}

	public void setNormal_price(String normal_price) {
		this.normal_price = normal_price;
	}

	public String getIs_booknow() {
		return is_booknow;
	}

	public void setIs_booknow(String is_booknow) {
		this.is_booknow = is_booknow;
	}

	public String getVenuce_is_order() {
		return venuce_is_order;
	}

	public void setVenuce_is_order(String venuce_is_order) {
		this.venuce_is_order = venuce_is_order;
	}

	public ArrayList<StadiumVenuesInfo> getMvenuespriceruleitemList() {
		return mvenuespriceruleitemList;
	}

	public void setMvenuespriceruleitemList(ArrayList<StadiumVenuesInfo> mvenuespriceruleitemList) {
		this.mvenuespriceruleitemList = mvenuespriceruleitemList;
	}

	public String getStadium_venue_priceruleid() {
		return stadium_venue_priceruleid;
	}

	public void setStadium_venue_priceruleid(String stadium_venue_priceruleid) {
		this.stadium_venue_priceruleid = stadium_venue_priceruleid;
	}

	public String getStadium_venue_pricerulename() {
		return stadium_venue_pricerulename;
	}

	public void setStadium_venue_pricerulename(String stadium_venue_pricerulename) {
		this.stadium_venue_pricerulename = stadium_venue_pricerulename;
	}

	public ArrayList<StadiumVenuesInfo> getMpricesList() {
		return mpricesList;
	}

	public void setMpricesList(ArrayList<StadiumVenuesInfo> mpricesList) {
		this.mpricesList = mpricesList;
	}

	public String getStadium_venue_minprice() {
		return stadium_venue_minprice;
	}

	public void setStadium_venue_minprice(String stadium_venue_minprice) {
		this.stadium_venue_minprice = stadium_venue_minprice;
	}

	public String getPrice_id() {
		return price_id;
	}

	public void setPrice_id(String price_id) {
		this.price_id = price_id;
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

	public String getCash_price() {
		return cash_price;
	}

	public void setCash_price(String cash_price) {
		this.cash_price = cash_price;
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

	public String getStadium_venue_id() {
		return stadium_venue_id;
	}

	public void setStadium_venue_id(String stadium_venue_id) {
		this.stadium_venue_id = stadium_venue_id;
	}

	public String getStadium_id() {
		return stadium_id;
	}

	public void setStadium_id(String stadium_id) {
		this.stadium_id = stadium_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
	}

	public ArrayList<StadiumVenuesPricesInfo> getVenuePricesPYLWList() {
		return venuePricesPYLWList;
	}

	public void setVenuePricesPYLWList(ArrayList<StadiumVenuesPricesInfo> venuePricesPYLWList) {
		this.venuePricesPYLWList = venuePricesPYLWList;
	}

}
