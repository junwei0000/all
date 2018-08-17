package com.KiwiSports.model;


import java.io.Serializable;
import java.util.ArrayList;

public class DistanceCountInfo implements Serializable{

	int sportsType;
	String sportname;
	String distance;

	String totalDistances = "";
	ArrayList<DistanceCountInfo> mtotalDislist;
	public DistanceCountInfo() {
		super();
	}
	public DistanceCountInfo(int sportsType,String sportname, String distance) {
		super();
		this.sportsType = sportsType;
		this.sportname = sportname;
		this.distance = distance;
	}

	public String getSportname() {
		return sportname;
	}

	public void setSportname(String sportname) {
		this.sportname = sportname;
	}

	public String getTotalDistances() {
		return totalDistances;
	}

	public void setTotalDistances(String totalDistances) {
		this.totalDistances = totalDistances;
	}

	public ArrayList<DistanceCountInfo> getMtotalDislist() {
		return mtotalDislist;
	}

	public void setMtotalDislist(ArrayList<DistanceCountInfo> mtotalDislist) {
		this.mtotalDislist = mtotalDislist;
	}

	public int getSportsType() {
		return sportsType;
	}

	public void setSportsType(int sportsType) {
		this.sportsType = sportsType;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
}