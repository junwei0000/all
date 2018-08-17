/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

import android.widget.ImageView;

/**
 * @author 作者：
 * @date 创建时间：
 * @Description 类描述：加入的俱乐部
 */
public class MyJoinClubmenuInfo implements Serializable {

	String club_id;// 俱乐部ID
	String icon;// 俱乐部图标
	String id;
	String is_has_regular_activity;// 俱乐部是否有固定活动，1有固定活动，2无固定活动
	String club_name;// 俱乐部名称
	String member_count;// 俱乐部成员数量
	String activity_count;// 活动的次数
	String club_text;// 每周一 00:00 ~ 11:20 固定活动"//俱乐部文字
	String member_text;
	String activity_text;
	String club_description;
	String club_banner;

	ImageView choosedbt;
	boolean check;

	public String getClub_description() {
		return club_description;
	}

	public ImageView getChoosedbt() {
		return choosedbt;
	}

	public void setChoosedbt(ImageView choosedbt) {
		this.choosedbt = choosedbt;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setClub_description(String club_description) {
		this.club_description = club_description;
	}

	public String getClub_banner() {
		return club_banner;
	}

	public void setClub_banner(String club_banner) {
		this.club_banner = club_banner;
	}

	public String getMember_text() {
		return member_text;
	}

	public void setMember_text(String member_text) {
		this.member_text = member_text;
	}

	public String getActivity_text() {
		return activity_text;
	}

	public void setActivity_text(String activity_text) {
		this.activity_text = activity_text;
	}

	public String getClub_id() {
		return club_id;
	}

	public void setClub_id(String club_id) {
		this.club_id = club_id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIs_has_regular_activity() {
		return is_has_regular_activity;
	}

	public void setIs_has_regular_activity(String is_has_regular_activity) {
		this.is_has_regular_activity = is_has_regular_activity;
	}

	public String getClub_name() {
		return club_name;
	}

	public void setClub_name(String club_name) {
		this.club_name = club_name;
	}

	public String getMember_count() {
		return member_count;
	}

	public void setMember_count(String member_count) {
		this.member_count = member_count;
	}

	public String getActivity_count() {
		return activity_count;
	}

	public void setActivity_count(String activity_count) {
		this.activity_count = activity_count;
	}

	public String getClub_text() {
		return club_text;
	}

	public void setClub_text(String club_text) {
		this.club_text = club_text;
	}

	public MyJoinClubmenuInfo() {
		super();
	}

	public MyJoinClubmenuInfo(String club_id, String icon, String id, String is_has_regular_activity, String club_name,
			String member_count, String activity_count, String club_text, String member_text, String activity_text,
			String club_description, String club_banner) {
		super();
		this.club_id = club_id;
		this.icon = icon;
		this.id = id;
		this.is_has_regular_activity = is_has_regular_activity;
		this.club_name = club_name;
		this.member_count = member_count;
		this.activity_count = activity_count;
		this.club_text = club_text;
		this.member_text = member_text;
		this.activity_text = activity_text;
		this.club_description = club_description;
		this.club_banner = club_banner;
	}

}
