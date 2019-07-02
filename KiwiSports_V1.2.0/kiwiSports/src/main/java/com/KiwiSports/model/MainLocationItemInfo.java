package com.KiwiSports.model;

import com.amap.api.maps.model.LatLng;

public class MainLocationItemInfo {

	double latitude;
	double longitude;
	double speed;// 速度
	int altitude;// 海拔
	int accuracy;// 精度
	int nStatus;// 运动状态
	int nLapPoint;// 没圈线路中间点
	String nLapTime;// 单圈用时
	String latLngDashedStatus;
	long duration;// 用时
	double distance;// 距离
	String latitudeOffset;// 精度偏移
	String longitudeOffset;// 维度偏移
	LatLng mLatLng;
	public MainLocationItemInfo(){
		super();
	}
	public MainLocationItemInfo(double latitude, double longitude,
			double speed, int altitude, int accuracy, int nStatus,
			int nLapPoint, String nLapTime,String latLngDashedStatus, long duration, double distance,
			String latitudeOffset, String longitudeOffset,LatLng mLatLng) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.speed = speed;
		this.altitude = altitude;
		this.accuracy = accuracy;
		this.nStatus = nStatus;
		this.nLapPoint = nLapPoint;
		this.latLngDashedStatus = latLngDashedStatus;
		this.nLapTime = nLapTime;
		this.duration = duration;
		this.distance = distance;
		this.latitudeOffset = latitudeOffset;
		this.longitudeOffset = longitudeOffset;
		this.mLatLng = mLatLng;
	}

	public String getLatLngDashedStatus() {
		return latLngDashedStatus;
	}

	public void setLatLngDashedStatus(String latLngDashedStatus) {
		this.latLngDashedStatus = latLngDashedStatus;
	}

	public LatLng getmLatLng() {
		return mLatLng;
	}

	public void setmLatLng(LatLng mLatLng) {
		this.mLatLng = mLatLng;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public int getnStatus() {
		return nStatus;
	}

	public void setnStatus(int nStatus) {
		this.nStatus = nStatus;
	}

	public int getnLapPoint() {
		return nLapPoint;
	}

	public void setnLapPoint(int nLapPoint) {
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
