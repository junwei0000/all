/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午6:33:59
 * @Description 类描述：可购买的会员列表item info
 */
public class UserBuyMeberListInfo implements Serializable {
	String meber_title;
	String meber_detle;
	String price;
	String icon;
	Boolean select;
	String amazing;
	String id;
	String name;// 会员名称
	String identityId;// 会员身份id
	String priceBase;// 基础价格
	String validDays;// 会员有效期
	String priceMonthly;// 会员月平均价格
	String repertory;// 初始库存
	String balance;// 库存余额
	String buy_msg;// 普通列表优惠信息

	public String getAmazing() {
		return amazing;
	}

	public void setAmazing(String amazing) {
		this.amazing = amazing;
	}

	public String getMeber_title() {
		return meber_title;
	}

	public void setMeber_title(String meber_title) {
		this.meber_title = meber_title;
	}

	public String getMeber_detle() {
		return meber_detle;
	}

	public void setMeber_detle(String meber_detle) {
		this.meber_detle = meber_detle;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getSelect() {
		return select;
	}

	public void setSelect(Boolean select) {
		this.select = select;
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

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getPriceBase() {
		return priceBase;
	}

	public void setPriceBase(String priceBase) {
		this.priceBase = priceBase;
	}

	public String getValidDays() {
		return validDays;
	}

	public void setValidDays(String validDays) {
		this.validDays = validDays;
	}

	public String getPriceMonthly() {
		return priceMonthly;
	}

	public void setPriceMonthly(String priceMonthly) {
		this.priceMonthly = priceMonthly;
	}

	public String getRepertory() {
		return repertory;
	}

	public void setRepertory(String repertory) {
		this.repertory = repertory;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getBuy_msg() {
		return buy_msg;
	}

	public void setBuy_msg(String buy_msg) {
		this.buy_msg = buy_msg;
	}

}
