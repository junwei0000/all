/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午6:33:59
 * @Description 类描述：可购买的会员列表info
 */
public class UserBuyMeberInfo implements Parcelable {

	String id;
	String name;
	String is_show_more;
	String show_more_url;
	String useNotice;
	ArrayList<UserMemberBuyNoteInfo> userMemberBuyNoteInfoList;// 会员特权说明info列表
	ArrayList<UserBuyMeberListInfo> userBuyMeberListInfoList = new ArrayList<UserBuyMeberListInfo>();

	public UserBuyMeberInfo() {
		super();
	}

	public ArrayList<UserBuyMeberListInfo> getUserBuyMeberListInfoList() {
		return userBuyMeberListInfoList;
	}

	public void setUserBuyMeberListInfoList(ArrayList<UserBuyMeberListInfo> userBuyMeberListInfoList) {
		this.userBuyMeberListInfoList = userBuyMeberListInfoList;
	}

	public ArrayList<UserMemberBuyNoteInfo> getUserMemberBuyNoteInfoList() {
		return userMemberBuyNoteInfoList;
	}

	public void setUserMemberBuyNoteInfoList(ArrayList<UserMemberBuyNoteInfo> userMemberBuyNoteInfoList) {
		this.userMemberBuyNoteInfoList = userMemberBuyNoteInfoList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIs_show_more() {
		return is_show_more;
	}

	public void setIs_show_more(String is_show_more) {
		this.is_show_more = is_show_more;
	}

	public String getShow_more_url() {
		return show_more_url;
	}

	public void setShow_more_url(String show_more_url) {
		this.show_more_url = show_more_url;
	}

	public String getUseNotice() {
		return useNotice;
	}

	public void setUseNotice(String useNotice) {
		this.useNotice = useNotice;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
