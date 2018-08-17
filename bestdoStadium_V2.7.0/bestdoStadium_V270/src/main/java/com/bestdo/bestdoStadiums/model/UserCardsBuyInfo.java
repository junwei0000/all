package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-5-5 下午5:10:24
 * @Description 类描述：百动卡购买
 */
public class UserCardsBuyInfo implements Serializable {

	private String productid;
	private String price;
	private String name;
	private String detail;
	private String card_batch_id;
	private String card_explain;

	public UserCardsBuyInfo(String productid, String price, String name, String detail, String card_batch_id) {
		super();
		this.productid = productid;
		this.price = price;
		this.name = name;
		this.detail = detail;
		this.card_batch_id = card_batch_id;
	}

	public String getCard_explain() {
		return card_explain;
	}

	public void setCard_explain(String card_explain) {
		this.card_explain = card_explain;
	}

	public String getCard_batch_id() {
		return card_batch_id;
	}

	public void setCard_batch_id(String card_batch_id) {
		this.card_batch_id = card_batch_id;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
