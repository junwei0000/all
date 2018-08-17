package com.bestdo.bestdoStadiums.model;

/**
 * 距离选择
 * 
 * @author qyy
 * 
 */
public class StadiumSelectJuliInfo {
	private String name;
	private Boolean check;
	private String radius;

	private String sort;
	private String pricesort;

	private String region_id;
	private String level;

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getPricesort() {
		return pricesort;
	}

	public void setPricesort(String pricesort) {
		this.pricesort = pricesort;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public StadiumSelectJuliInfo(String name, Boolean check, String radius) {
		super();
		this.name = name;
		this.check = check;
		this.radius = radius;
	}

	public StadiumSelectJuliInfo(String name, String region_id, String level, Boolean check) {
		super();
		this.name = name;
		this.region_id = region_id;
		this.level = level;
		this.check = check;
	}

	public StadiumSelectJuliInfo(String name, Boolean check, String sort, String pricesort) {
		super();
		this.name = name;
		this.check = check;
		this.sort = sort;
		this.pricesort = pricesort;
	}

}
