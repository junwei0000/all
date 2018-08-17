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
public class UserMemberSuccessInfo implements Serializable {

	private String uid;// 用户uid
	private String nickName;// 用户昵称
	private String note;// 鼓励语
	private String memberNo;// 会员编号
	private String identityId;// 会员身份id
	private String identityName;// 会员身份名称
	private String buyTime;// 会员购买日期
	private String useStartTime;// 会员有效期起始时间
	private String useEndTime;// 会员有效期结束时间
	private String iconUrl;// 会员icon
	private String successMsg;// 提示语
	private String validDays;// 会员有效期
	private String endTimeMsg;// 会员到期时间
	private String shopUrl;

	public UserMemberSuccessInfo(String uid, String nickName, String note, String memberNo, String identityId,
			String identityName, String buyTime, String useStartTime, String useEndTime, String iconUrl,
			String successMsg, String validDays, String endTimeMsg, String shopUrl) {
		super();
		this.uid = uid;
		this.nickName = nickName;
		this.note = note;
		this.memberNo = memberNo;
		this.identityId = identityId;
		this.identityName = identityName;
		this.buyTime = buyTime;
		this.useStartTime = useStartTime;
		this.useEndTime = useEndTime;
		this.iconUrl = iconUrl;
		this.successMsg = successMsg;
		this.validDays = validDays;
		this.endTimeMsg = endTimeMsg;
		this.shopUrl = shopUrl;
	}

	public String getShopUrl() {
		return shopUrl;
	}

	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getIdentityName() {
		return identityName;
	}

	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	public String getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(String buyTime) {
		this.buyTime = buyTime;
	}

	public String getUseStartTime() {
		return useStartTime;
	}

	public void setUseStartTime(String useStartTime) {
		this.useStartTime = useStartTime;
	}

	public String getUseEndTime() {
		return useEndTime;
	}

	public void setUseEndTime(String useEndTime) {
		this.useEndTime = useEndTime;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public String getValidDays() {
		return validDays;
	}

	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}

	public String getEndTimeMsg() {
		return endTimeMsg;
	}

	public void setEndTimeMsg(String endTimeMsg) {
		this.endTimeMsg = endTimeMsg;
	}

}
