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
public class UserCenterMemberInfo implements Serializable {
	private static final long serialVersionUID = 2961045861059021465L;
	/**
	 * 会员信息
	 */
	private String uid;
	private String memberNo; 
	/**
	 * 会员身份id  0:不是会员
	 */
	private String identityId; 
	private String identityName;// 会员身份名称
	private String buyTime;// 会员购买日期
	private String useStartTime;// 会员有效期起始时间
	private String useEndTime;// 会员有效期结束时间
	private String iconUrl;// 会员icon
	private String discountBook;// 会员订场折扣
	private String discountShop;// 会员上传折扣
	private String defaultCount;
	/**
	 * 1：已过期； 0没过期
	 */
	private String isExpired;
	private String levelName;
	private String levelUrl;
	private String tip;
	/**
	 * 余额信息
	 */
	private String balance;
	private String cardNumber;
	private String kmNumber;

	public UserCenterMemberInfo(String balance, String cardNumber, String kmNumber,String tip) {
		super();
		this.balance = balance;
		this.cardNumber = cardNumber;
		this.kmNumber = kmNumber;
		this.tip = tip;
	}

	public UserCenterMemberInfo(String uid, String memberNo, String identityId, String identityName, String buyTime,
			String useStartTime, String useEndTime, String iconUrl, String discountBook, String discountShop,
			String defaultCount, String isExpired, String levelName,String levelUrl) {
		super();
		this.uid = uid;
		this.memberNo = memberNo;
		this.identityId = identityId;
		this.identityName = identityName;
		this.buyTime = buyTime;
		this.useStartTime = useStartTime;
		this.useEndTime = useEndTime;
		this.iconUrl = iconUrl;
		this.discountBook = discountBook;
		this.discountShop = discountShop;
		this.defaultCount = defaultCount;
		this.isExpired = isExpired;
		this.levelName = levelName;
		this.levelUrl = levelUrl;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getLevelUrl() {
		return levelUrl;
	}

	public void setLevelUrl(String levelUrl) {
		this.levelUrl = levelUrl;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getKmNumber() {
		return kmNumber;
	}

	public void setKmNumber(String kmNumber) {
		this.kmNumber = kmNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * @return the defaultCount
	 */
	public String getDefaultCount() {
		return defaultCount;
	}

	/**
	 * @param defaultCount
	 *            the defaultCount to set
	 */
	public void setDefaultCount(String defaultCount) {
		this.defaultCount = defaultCount;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
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

	public String getDiscountBook() {
		return discountBook;
	}

	public void setDiscountBook(String discountBook) {
		this.discountBook = discountBook;
	}

	public String getDiscountShop() {
		return discountShop;
	}

	public void setDiscountShop(String discountShop) {
		this.discountShop = discountShop;
	}

}
