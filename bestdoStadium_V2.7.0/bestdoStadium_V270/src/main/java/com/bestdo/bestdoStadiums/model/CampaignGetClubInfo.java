/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:03
 * @Description 类描述：
 */
public class CampaignGetClubInfo {

	String club_id;
	String club_name;
	String club_description;

	public CampaignGetClubInfo(String club_id, String club_name) {
		super();
		this.club_id = club_id;
		this.club_name = club_name;
	}

	public CampaignGetClubInfo(String club_id, String club_name, String club_description) {
		super();
		this.club_id = club_id;
		this.club_name = club_name;
		this.club_description = club_description;
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

	public String getClub_description() {
		return club_description;
	}

	public void setClub_description(String club_description) {
		this.club_description = club_description;
	}

}
