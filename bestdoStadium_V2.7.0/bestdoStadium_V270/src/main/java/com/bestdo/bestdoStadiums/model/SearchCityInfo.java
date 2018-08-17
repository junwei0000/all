package com.bestdo.bestdoStadiums.model;

/**
 * 城市
 * 
 * @author jun
 * 
 */
public class SearchCityInfo {

	private String cityid;
	private String cityname;
	private String citynameShow;
	private double longitude;
	private double latitude;
	private String sortLetters;
	private String sortLettersShow;

	public SearchCityInfo() {
		super();
	}

	public SearchCityInfo(String cityid, String cityname, String citynameShow, double longitude, double latitude,
			String sortLetters, String sortLettersShow) {
		super();
		this.cityid = cityid;
		this.cityname = cityname;
		this.citynameShow = citynameShow;
		this.longitude = longitude;
		this.latitude = latitude;
		this.sortLetters = sortLetters;
		this.sortLettersShow = sortLettersShow;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public String getCitynameShow() {
		return citynameShow;
	}

	public void setCitynameShow(String citynameShow) {
		this.citynameShow = citynameShow;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getSortLettersShow() {
		return sortLettersShow;
	}

	public void setSortLettersShow(String sortLettersShow) {
		this.sortLettersShow = sortLettersShow;
	}

}
