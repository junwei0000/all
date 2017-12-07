package com.KiwiSports.model;

import java.util.ArrayList;

public class MainSportInfo {

	String cname;
	String ename;
	String value;
	String unit;
	ArrayList<MainSportInfo> mpropertytwnList = new ArrayList<MainSportInfo>();
	ArrayList<MainSportInfo> mpropertyList = new ArrayList<MainSportInfo>();

	public MainSportInfo(String cname, String ename, ArrayList<MainSportInfo> mpropertytwnList,
			ArrayList<MainSportInfo> mpropertyList) {
		super();
		this.cname = cname;
		this.ename = ename;
		this.mpropertyList = mpropertyList;
		this.mpropertytwnList = mpropertytwnList;
	}

	public MainSportInfo(String cname, String ename, String value, String unit) {
		super();
		this.cname = cname;
		this.ename = ename;
		this.value = value;
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public ArrayList<MainSportInfo> getMpropertytwnList() {
		return mpropertytwnList;
	}

	public void setMpropertytwnList(ArrayList<MainSportInfo> mpropertytwnList) {
		this.mpropertytwnList = mpropertytwnList;
	}

	public ArrayList<MainSportInfo> getMpropertyList() {
		return mpropertyList;
	}

	public void setMpropertyList(ArrayList<MainSportInfo> mpropertyList) {
		this.mpropertyList = mpropertyList;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

}
