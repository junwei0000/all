/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午6:33:59
 * @Description 类描述：当前用户会员info
 */
public class UserMemberInfo implements Serializable {
	String uid;// 用户uid
	String nick_name;// 用户昵称
	String note;// 头像右侧话术
	String ablumUrl;// 会员头像
	String member_note;// 会员有效期
	String member_icon;// 会员icon
	String member_level;// 会员等级id 不是会员时是0
	String member_name;//// 会员名称

	public UserMemberInfo(String uid, String nick_name, String note, String ablumUrl, String member_note,
			String member_icon, String member_level, String member_name) {
		super();
		this.uid = uid;
		this.nick_name = nick_name;
		this.note = note;
		this.ablumUrl = ablumUrl;
		this.member_note = member_note;
		this.member_icon = member_icon;
		this.member_level = member_level;
		this.member_name = member_name;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAblumUrl() {
		return ablumUrl;
	}

	public void setAblumUrl(String ablumUrl) {
		this.ablumUrl = ablumUrl;
	}

	public String getMember_note() {
		return member_note;
	}

	public void setMember_note(String member_note) {
		this.member_note = member_note;
	}

	public String getMember_icon() {
		return member_icon;
	}

	public void setMember_icon(String member_icon) {
		this.member_icon = member_icon;
	}

	public String getMember_level() {
		return member_level;
	}

	public void setMember_level(String member_level) {
		this.member_level = member_level;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

}
