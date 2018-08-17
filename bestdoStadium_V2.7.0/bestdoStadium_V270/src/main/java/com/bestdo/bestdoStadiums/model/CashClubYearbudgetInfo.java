/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-29 下午3:24:12
 * @Description 类描述：
 */
public class CashClubYearbudgetInfo {
	String id;
	String club_name;
	String budget;
	String year;

	public CashClubYearbudgetInfo(String id, String club_name, String budget, String year) {
		super();
		this.id = id;
		this.club_name = club_name;
		this.budget = budget;
		this.year = year;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public String getBudget() {
		return budget;
	}

	public void setBudget(String budget) {
		this.budget = budget;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
