/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * @author 作者：zoc
 * @date 创建时间：2017-1-3 下午2:42:30
 * @Description 类描述：
 */
public class CashbookListInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;
	String money;
	String use_time;
	/**
	 * 1是支出，2是收入
	 */
	String type;
	String class_name;
	String description;
	String img;
	String select_img;

	public CashbookListInfo(String id, String money, String use_time, String type, String class_name,
			String description, String img, String select_img) {
		super();
		this.id = id;
		this.money = money;
		this.use_time = use_time;
		this.type = type;
		this.class_name = class_name;
		this.description = description;
		this.img = img;
		this.select_img = select_img;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getUse_time() {
		return use_time;
	}

	public void setUse_time(String use_time) {
		this.use_time = use_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSelect_img() {
		return select_img;
	}

	public void setSelect_img(String select_img) {
		this.select_img = select_img;
	}

}
