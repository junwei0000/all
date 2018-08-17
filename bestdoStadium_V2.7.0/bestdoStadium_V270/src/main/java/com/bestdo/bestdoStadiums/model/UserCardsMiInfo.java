package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;

/**
 * 用户卡列表
 * 
 * @author jun
 * 
 */
public class UserCardsMiInfo implements Serializable {

	private String card_name;
	private String card_no;
	private String card_pass;
	private String card_status;
	private String amount;
	private String card_img, share_url;
	private String validNote, backGroundColor, camiNote;

	public String getValidNote() {
		return validNote;
	}

	public void setValidNote(String validNote) {
		this.validNote = validNote;
	}

	public String getBackGroundColor() {
		return backGroundColor;
	}

	public void setBackGroundColor(String backGroundColor) {
		this.backGroundColor = backGroundColor;
	}

	public String getCamiNote() {
		return camiNote;
	}

	public void setCamiNote(String camiNote) {
		this.camiNote = camiNote;
	}

	public String getCard_img() {
		return card_img;
	}

	public void setCard_img(String card_img) {
		this.card_img = card_img;
	}

	public String getShare_url() {
		return share_url;
	}

	public void setShare_url(String share_url) {
		this.share_url = share_url;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getCard_pass() {
		return card_pass;
	}

	public void setCard_pass(String card_pass) {
		this.card_pass = card_pass;
	}

	public String getCard_status() {
		return card_status;
	}

	public void setCard_status(String card_status) {
		this.card_status = card_status;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public UserCardsMiInfo(String card_name, String card_no, String card_pass, String card_status, String amount,
			String card_img, String share_url) {
		super();
		this.card_name = card_name;
		this.card_no = card_no;
		this.card_pass = card_pass;
		this.card_status = card_status;
		this.amount = amount;
		this.card_img = card_img;
		this.share_url = share_url;
	}

}
