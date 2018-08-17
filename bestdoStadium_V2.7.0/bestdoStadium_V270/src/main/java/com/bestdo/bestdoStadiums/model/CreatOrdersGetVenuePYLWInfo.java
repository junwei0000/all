package com.bestdo.bestdoStadiums.model;

import java.util.ArrayList;

import android.view.View;
import android.widget.TextView;

public class CreatOrdersGetVenuePYLWInfo {

	private String day;
	private String week;

	private String start_hour;
	private String end_hour;
	private String normal_price;
	private String price_tennis_detail_id;
	private String prepay_price;
	private String is_book;// 1已预订、0可预订
	private String show;// 1可预订、2不在可预订时间内
	private String payment_method;
	private String cash_price;
	private String venue_hao;
	private ArrayList<CreatOrdersGetVenuePYLWInfo> mBooksAllDayList = new ArrayList<CreatOrdersGetVenuePYLWInfo>();
	private ArrayList<CreatOrdersGetVenuePYLWInfo> mDayPricesList = new ArrayList<CreatOrdersGetVenuePYLWInfo>();

	/**
	 * 片数
	 */
	private String is_select = "";// 是否选中
	private String name;
	private TextView tvpieces;
	/**
	 * week
	 */
	private View weekviews;
	private TextView textview;
	private TextView textviewline;

	public CreatOrdersGetVenuePYLWInfo() {
		super();
	}

	public CreatOrdersGetVenuePYLWInfo(String day, String week,
			ArrayList<CreatOrdersGetVenuePYLWInfo> mBooksAllDayList) {
		super();
		this.day = day;
		this.week = week;
		this.mBooksAllDayList = mBooksAllDayList;
	}

	public CreatOrdersGetVenuePYLWInfo(ArrayList<CreatOrdersGetVenuePYLWInfo> mDayPricesList) {
		super();
		this.mDayPricesList = mDayPricesList;
	}

	public CreatOrdersGetVenuePYLWInfo(String day, String start_hour, String end_hour, String price_tennis_detail_id,
			String prepay_price, String is_book, String venue_hao, String payment_method, String cash_price,
			String is_select, String stadium_venue_piece_id, String name, String show) {
		super();
		this.day = day;
		this.start_hour = start_hour;
		this.end_hour = end_hour;
		this.price_tennis_detail_id = price_tennis_detail_id;
		this.prepay_price = prepay_price;
		this.is_book = is_book;
		this.venue_hao = venue_hao;
		this.payment_method = payment_method;
		this.cash_price = cash_price;
		this.is_select = is_select;
		this.name = name;
		this.show = show;
	}

	public String getNormal_price() {
		return normal_price;
	}

	public void setNormal_price(String normal_price) {
		this.normal_price = normal_price;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getCash_price() {
		return cash_price;
	}

	public void setCash_price(String cash_price) {
		this.cash_price = cash_price;
	}

	public TextView getTvpieces() {
		return tvpieces;
	}

	public void setTvpieces(TextView tvpieces) {
		this.tvpieces = tvpieces;
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

	public ArrayList<CreatOrdersGetVenuePYLWInfo> getmBooksAllDayList() {
		return mBooksAllDayList;
	}

	public void setmBooksAllDayList(ArrayList<CreatOrdersGetVenuePYLWInfo> mBooksAllDayList) {
		this.mBooksAllDayList = mBooksAllDayList;
	}

	public String getIs_select() {
		return is_select;
	}

	public void setIs_select(String is_select) {
		this.is_select = is_select;
	}

	public String getVenue_hao() {
		return venue_hao;
	}

	public void setVenue_hao(String venue_hao) {
		this.venue_hao = venue_hao;
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

	public String getPrice_tennis_detail_id() {
		return price_tennis_detail_id;
	}

	public void setPrice_tennis_detail_id(String price_tennis_detail_id) {
		this.price_tennis_detail_id = price_tennis_detail_id;
	}

	public String getPrepay_price() {
		return prepay_price;
	}

	public void setPrepay_price(String prepay_price) {
		this.prepay_price = prepay_price;
	}

	public String getIs_book() {
		return is_book;
	}

	public void setIs_book(String is_book) {
		this.is_book = is_book;
	}

	public ArrayList<CreatOrdersGetVenuePYLWInfo> getmDayPricesList() {
		return mDayPricesList;
	}

	public void setmDayPricesList(ArrayList<CreatOrdersGetVenuePYLWInfo> mDayPricesList) {
		this.mDayPricesList = mDayPricesList;
	}

}
