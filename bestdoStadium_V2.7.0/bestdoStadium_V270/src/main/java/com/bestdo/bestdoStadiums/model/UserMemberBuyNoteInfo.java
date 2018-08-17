/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-11-28 下午6:33:59
 * @Description 类描述：会员特权说明info
 */
public class UserMemberBuyNoteInfo implements Serializable {
	String name;// 会员类型
	String img;// 会员特权图片
	String note;// 会员特权应用场景
	String discount; // 会员折扣介绍.

	public UserMemberBuyNoteInfo(String name, String img, String note, String discount) {
		super();
		this.name = name;
		this.img = img;
		this.note = note;
		this.discount = discount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

}
