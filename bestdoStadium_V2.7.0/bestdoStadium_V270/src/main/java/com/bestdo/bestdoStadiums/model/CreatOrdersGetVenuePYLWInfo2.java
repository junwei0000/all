package com.bestdo.bestdoStadiums.model;

import java.util.ArrayList;

import android.view.View;
import android.widget.TextView;

public class CreatOrdersGetVenuePYLWInfo2 {

	private String status;
	private String name;
	private String piece_id;
	private String venue_id;

	private String start_hour;
	private String mer_item_id;
	private String end_time;
	private String piecebookstatus;
	private String prepay_price;
	private String end_hour;
	private String start_time;
	private String mer_price_id;
	private String mer_item_price_id;
	private String hour;
	private String type;
	private String week;

	private String mer_item_price_time_id;
	private Boolean is_select;
	private TextView tvpieces;
	private View mpiecesView;

	private ArrayList<CreatOrdersGetVenuePYLWInfo2> venuesList = new ArrayList<CreatOrdersGetVenuePYLWInfo2>();

	public View getMpiecesView() {
		return mpiecesView;
	}

	public void setMpiecesView(View mpiecesView) {
		this.mpiecesView = mpiecesView;
	}

	public TextView getTvpieces() {
		return tvpieces;
	}

	public void setTvpieces(TextView tvpieces) {
		this.tvpieces = tvpieces;
	}

	public ArrayList<CreatOrdersGetVenuePYLWInfo2> getVenuesList() {
		return venuesList;
	}

	public void setVenuesList(ArrayList<CreatOrdersGetVenuePYLWInfo2> venuesList) {
		this.venuesList = venuesList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPiece_id() {
		return piece_id;
	}

	public void setPiece_id(String piece_id) {
		this.piece_id = piece_id;
	}

	public String getVenue_id() {
		return venue_id;
	}

	public void setVenue_id(String venue_id) {
		this.venue_id = venue_id;
	}

	public String getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}

	public String getMer_item_id() {
		return mer_item_id;
	}

	public void setMer_item_id(String mer_item_id) {
		this.mer_item_id = mer_item_id;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getPiecebookstatus() {
		return piecebookstatus;
	}

	public void setPiecebookstatus(String piecebookstatus) {
		this.piecebookstatus = piecebookstatus;
	}

	public String getPrepay_price() {
		return prepay_price;
	}

	public void setPrepay_price(String prepay_price) {
		this.prepay_price = prepay_price;
	}

	public String getEnd_hour() {
		return end_hour;
	}

	public void setEnd_hour(String end_hour) {
		this.end_hour = end_hour;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getMer_price_id() {
		return mer_price_id;
	}

	public void setMer_price_id(String mer_price_id) {
		this.mer_price_id = mer_price_id;
	}

	public String getMer_item_price_id() {
		return mer_item_price_id;
	}

	public void setMer_item_price_id(String mer_item_price_id) {
		this.mer_item_price_id = mer_item_price_id;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Boolean getIs_select() {
		return is_select;
	}

	public void setIs_select(Boolean is_select) {
		this.is_select = is_select;
	}

}
