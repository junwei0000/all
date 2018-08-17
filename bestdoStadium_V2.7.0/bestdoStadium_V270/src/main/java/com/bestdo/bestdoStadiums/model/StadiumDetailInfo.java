package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 场馆详情
 * 
 * @author qyy
 * 
 */
public class StadiumDetailInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 1表示已收藏 0表示未收藏
	 */
	private boolean collectStatus;
	private String merid;
	private String city;
	private String min_price;
	private String stadium_id;
	private String stadium_status;
	private String venue_id;
	private String name;
	private String stadium_name;
	private boolean isbook;
	private String mer_item_id;
	private String price_info;
	private String process_type;// 是否人工
	String member_discount_note;
	private String book_info;

	private String longitude;
	private String latitude;
	private String thumb;
	private String abbreviation;
	private String description;
	private String address;
	private String bd_phone;

	private int teetimejiange;

	private String services_name;
	private String services_uri;
	private String stadium_service_id;

	private String displayStadiumProperties_name;
	private String displayStadiumProperties_value;
	private String inventory_type;
	private String cid;

	private ArrayList<BannerInfo> mAlbumsList;

	/**
	 * @return the member_discount_note
	 */
	public String getMember_discount_note() {
		return member_discount_note;
	}

	/**
	 * @param member_discount_note
	 *            the member_discount_note to set
	 */
	public void setMember_discount_note(String member_discount_note) {
		this.member_discount_note = member_discount_note;
	}

	public String getBook_info() {
		return book_info;
	}

	public void setBook_info(String book_info) {
		this.book_info = book_info;
	}

	public int getTeetimejiange() {
		return teetimejiange;
	}

	public void setTeetimejiange(int teetimejiange) {
		this.teetimejiange = teetimejiange;
	}

	public ArrayList<BannerInfo> getmAlbumsList() {
		return mAlbumsList;
	}

	public void setmAlbumsList(ArrayList<BannerInfo> mAlbumsList) {
		this.mAlbumsList = mAlbumsList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getProcess_type() {
		return process_type;
	}

	public void setProcess_type(String process_type) {
		this.process_type = process_type;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStadium_name() {
		return stadium_name;
	}

	public void setStadium_name(String stadium_name) {
		this.stadium_name = stadium_name;
	}

	public boolean isCollectStatus() {
		return collectStatus;
	}

	public void setCollectStatus(boolean collectStatus) {
		this.collectStatus = collectStatus;
	}

	public String getInventory_type() {
		return inventory_type;
	}

	public void setInventory_type(String inventory_type) {
		this.inventory_type = inventory_type;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMin_price() {
		return min_price;
	}

	public void setMin_price(String min_price) {
		this.min_price = min_price;
	}

	public String getStadium_id() {
		return stadium_id;
	}

	public void setStadium_id(String stadium_id) {
		this.stadium_id = stadium_id;
	}

	public String getStadium_status() {
		return stadium_status;
	}

	public void setStadium_status(String stadium_status) {
		this.stadium_status = stadium_status;
	}

	public String getVenue_id() {
		return venue_id;
	}

	public void setVenue_id(String venue_id) {
		this.venue_id = venue_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isIsbook() {
		return isbook;
	}

	public void setIsbook(boolean isbook) {
		this.isbook = isbook;
	}

	public String getMer_item_id() {
		return mer_item_id;
	}

	public void setMer_item_id(String mer_item_id) {
		this.mer_item_id = mer_item_id;
	}

	public String getPrice_info() {
		return price_info;
	}

	public void setPrice_info(String price_info) {
		this.price_info = price_info;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBd_phone() {
		return bd_phone;
	}

	public void setBd_phone(String bd_phone) {
		this.bd_phone = bd_phone;
	}

	public String getServices_name() {
		return services_name;
	}

	public void setServices_name(String services_name) {
		this.services_name = services_name;
	}

	public String getServices_uri() {
		return services_uri;
	}

	public void setServices_uri(String services_uri) {
		this.services_uri = services_uri;
	}

	public String getStadium_service_id() {
		return stadium_service_id;
	}

	public void setStadium_service_id(String stadium_service_id) {
		this.stadium_service_id = stadium_service_id;
	}

	public String getDisplayStadiumProperties_name() {
		return displayStadiumProperties_name;
	}

	public void setDisplayStadiumProperties_name(String displayStadiumProperties_name) {
		this.displayStadiumProperties_name = displayStadiumProperties_name;
	}

	public String getDisplayStadiumProperties_value() {
		return displayStadiumProperties_value;
	}

	public void setDisplayStadiumProperties_value(String displayStadiumProperties_value) {
		this.displayStadiumProperties_value = displayStadiumProperties_value;
	}

}
