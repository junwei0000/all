package com.KiwiSports.model;


import com.baidu.mapapi.model.LatLng;

public class MainLocationItemInfo { 
	
	LatLng mLatLng;//经纬度
	double speed;//速度
	double altitude;//海拔
	double accuracy;//精度
	String nStatus;//运动状态
	String nLapPoint;//没圈线路中间点
	String nLapTime;//单圈用时
	long duration;//用时
	double distance;//距离
	String latitudeOffset;//精度偏移
	String longitudeOffset;//维度偏移
	
	
	public MainLocationItemInfo(LatLng mLatLng, double speed, double altitude, double accuracy, String nStatus,
			String nLapPoint, String nLapTime, long duration, double distance, String latitudeOffset,
			String longitudeOffset) {
		super();
		this.mLatLng = mLatLng;
		this.speed = speed;
		this.altitude = altitude;
		this.accuracy = accuracy;
		this.nStatus = nStatus;
		this.nLapPoint = nLapPoint;
		this.nLapTime = nLapTime;
		this.duration = duration;
		this.distance = distance;
		this.latitudeOffset = latitudeOffset;
		this.longitudeOffset = longitudeOffset;
	}
	public LatLng getmLatLng() {
		return mLatLng;
	}
	public void setmLatLng(LatLng mLatLng) {
		this.mLatLng = mLatLng;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getAltitude() {
		return altitude;
	}
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public String getnStatus() {
		return nStatus;
	}
	public void setnStatus(String nStatus) {
		this.nStatus = nStatus;
	}
	public String getnLapPoint() {
		return nLapPoint;
	}
	public void setnLapPoint(String nLapPoint) {
		this.nLapPoint = nLapPoint;
	}
	public String getnLapTime() {
		return nLapTime;
	}
	public void setnLapTime(String nLapTime) {
		this.nLapTime = nLapTime;
	}
	 
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getLatitudeOffset() {
		return latitudeOffset;
	}
	public void setLatitudeOffset(String latitudeOffset) {
		this.latitudeOffset = latitudeOffset;
	}
	public String getLongitudeOffset() {
		return longitudeOffset;
	}
	public void setLongitudeOffset(String longitudeOffset) {
		this.longitudeOffset = longitudeOffset;
	}
	
}
