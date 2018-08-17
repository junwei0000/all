/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：
 * @date 创建时间：
 * @Description 类描述：运动圈用户菜单
 */
public class ClubMenuInfo {
	int page;
	String title;
	String thumb;
	String club_name;

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public ClubMenuInfo(String title, String thumb, String club_name) {
		super();
		this.title = title;
		this.club_name = club_name;
		this.thumb = thumb;
	}

}
