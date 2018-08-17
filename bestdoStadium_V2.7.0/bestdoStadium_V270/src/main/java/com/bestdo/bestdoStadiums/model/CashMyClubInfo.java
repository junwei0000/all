/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-12-29 下午3:24:12
 * @Description 类描述：
 */
public class CashMyClubInfo {
	String club_id;
	String club_name;
	String club_description;
	String icon;

	public CashMyClubInfo(String club_id, String club_name, String club_description, String icon) {
		super();
		this.club_id = club_id;
		this.club_name = club_name;
		this.club_description = club_description;
		this.icon = icon;
	}

	public String getClub_id() {
		return club_id;
	}

	public void setClub_id(String club_id) {
		this.club_id = club_id;
	}

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getClub_description() {
		return club_description;
	}

	public void setClub_description(String club_description) {
		this.club_description = club_description;
	}

}
