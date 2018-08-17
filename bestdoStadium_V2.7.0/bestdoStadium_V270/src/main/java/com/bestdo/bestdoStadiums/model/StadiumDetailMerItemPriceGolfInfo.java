package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.view.View;
import android.widget.TextView;

public class StadiumDetailMerItemPriceGolfInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int datetamp;
	private String inventory_summaray;

	private String[] daysshow;
	private String[] days;
	private String[][] hoursgolf;
	private String[][] hoursgolfange;
	ArrayList<Boolean> isorderList;

	public StadiumDetailMerItemPriceGolfInfo() {
		super();
	}

	public StadiumDetailMerItemPriceGolfInfo(String[] daysshow, String[] days, String[][] hoursgolf,
			String[][] hoursgolfange, ArrayList<Boolean> isorderList) {
		super();
		this.daysshow = daysshow;
		this.days = days;
		this.hoursgolf = hoursgolf;
		this.hoursgolfange = hoursgolfange;
		this.isorderList = isorderList;
	}

	public ArrayList<Boolean> getIsorderList() {
		return isorderList;
	}

	public void setIsorderList(ArrayList<Boolean> isorderList) {
		this.isorderList = isorderList;
	}

	public String[] getDaysshow() {
		return daysshow;
	}

	public void setDaysshow(String[] daysshow) {
		this.daysshow = daysshow;
	}

	public int getDatetamp() {
		return datetamp;
	}

	public void setDatetamp(int datetamp) {
		this.datetamp = datetamp;
	}

	public String getInventory_summaray() {
		return inventory_summaray;
	}

	public void setInventory_summaray(String inventory_summaray) {
		this.inventory_summaray = inventory_summaray;
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String[] days) {
		this.days = days;
	}

	public String[][] getHoursgolf() {
		return hoursgolf;
	}

	public void setHoursgolf(String[][] hoursgolf) {
		this.hoursgolf = hoursgolf;
	}

	public String[][] getHoursgolfange() {
		return hoursgolfange;
	}

	public void setHoursgolfange(String[][] hoursgolfange) {
		this.hoursgolfange = hoursgolfange;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
