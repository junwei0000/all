package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 场馆列表
 * 
 * @author qyy
 * 
 */
public class StadiumInfo implements Serializable {

	private String mer_item_id;
	private String mer_price_id;
	private String merid;
	private String cid;
	private String name;
	private String position;
	private String price_info;
	private String thumb;
	private String province;
	private String city;
	private String district;
	private String region;
	private String latitude;
	private String longitude;
	private String lat;
	private String lng;
	private String price;
	private String price_vip;
	private String geodist;
	private String vip_price_id;
	private String address;
	private ArrayList<StadiumInfo> mMapList;

	public StadiumInfo() {
		super();
	}

	public StadiumInfo(String mer_item_id, String mer_price_id, String merid, String cid, String name, String position,
			String price_info, String thumb, String province, String city, String district, String region,
			String latitude, String longitude, String lat, String lng, String price, String price_vip, String geodist,
			String vip_price_id, String address) {
		super();
		this.mer_item_id = mer_item_id;
		this.mer_price_id = mer_price_id;
		this.merid = merid;
		this.cid = cid;
		this.name = name;
		this.position = position;
		this.price_info = price_info;
		this.thumb = thumb;
		this.province = province;
		this.city = city;
		this.district = district;
		this.region = region;
		this.latitude = latitude;
		this.longitude = longitude;
		this.lat = lat;
		this.lng = lng;
		this.price = price;
		this.price_vip = price_vip;
		this.geodist = geodist;
		this.vip_price_id = vip_price_id;
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<StadiumInfo> getmMapList() {
		return mMapList;
	}

	public void setmMapList(ArrayList<StadiumInfo> mMapList) {
		this.mMapList = mMapList;
	}

	public String getVip_price_id() {
		return vip_price_id;
	}

	public void setVip_price_id(String vip_price_id) {
		this.vip_price_id = vip_price_id;
	}

	public String getGeodist() {
		return geodist;
	}

	public void setGeodist(String geodist) {
		this.geodist = geodist;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice_vip() {
		return price_vip;
	}

	public void setPrice_vip(String price_vip) {
		this.price_vip = price_vip;
	}

	public String getMer_item_id() {
		return mer_item_id;
	}

	public void setMer_item_id(String mer_item_id) {
		this.mer_item_id = mer_item_id;
	}

	public String getMer_price_id() {
		return mer_price_id;
	}

	public void setMer_price_id(String mer_price_id) {
		this.mer_price_id = mer_price_id;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPrice_info() {
		return price_info;
	}

	public void setPrice_info(String price_info) {
		this.price_info = price_info;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

}
