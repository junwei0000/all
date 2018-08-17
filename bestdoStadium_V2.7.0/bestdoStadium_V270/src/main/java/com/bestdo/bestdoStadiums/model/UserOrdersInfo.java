package com.bestdo.bestdoStadiums.model;

import java.util.ArrayList;

import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午5:26:45
 * @Description 类描述：订单列表info
 */
public class UserOrdersInfo {
	private String account;
	private String pwd;
	String life_type;
	String account_text;
	private String oid;
	private String uid;
	private String card_type_id;
	private String card_batch_id;
	private String card_id;
	private String account_rule_id;
	private String account_no;
	private String cid;
	private String stadium_id;
	private String stadium_name;
	private String venue_id;
	private String merid;
	private String mer_item_id;
	private String mer_item_name;
	private String book_day;
	private String order_money;
	private String reduce_money;
	private String pay_money;
	private String status;
	private String book_phone;
	private String note;
	private String book_number;
	private String stadium_thumb;
	private int current_time;
	private int create_time;
	/*
	 * item
	 */
	private String order_item_id;
	private String mer_price_id;
	private String price_type;
	private String play_time;
	private String play_person_name;
	private String piece_id;
	private String start_hour;
	private String end_hour;
	private String price_info;
	private String price_info_type;
	private String code;
	/**
	 * 验证码状态：0 未使用，1 已使用，2 已退订
	 */
	private String code_status;
	private ArrayList<UserOrdersInfo> itemList;

	private int unpay_num;
	private int confirm_num;
	private int off_num;
	private String order_list;
	private String address_list;

	public UserOrdersInfo() {
		super();
	}

	public UserOrdersInfo(int unpay_num, int confirm_num, int off_num, String order_list2, String address_list2) {
		super();
		this.unpay_num = unpay_num;
		this.confirm_num = confirm_num;
		this.off_num = off_num;
		this.order_list = order_list2;
		this.address_list = address_list2;
	}

	public UserOrdersInfo(int unpay_num, int confirm_num, int off_num) {
		super();
		this.unpay_num = unpay_num;
		this.confirm_num = confirm_num;
		this.off_num = off_num;
	}

	public UserOrdersInfo(String order_item_id, String mer_price_id, String price_type, String play_time,
			String play_person_name, String piece_id, String start_hour, String end_hour, String price_info,
			String price_info_type, String code, String code_status) {
		super();
		this.order_item_id = order_item_id;
		this.mer_price_id = mer_price_id;
		this.price_type = price_type;
		this.play_time = play_time;
		this.play_person_name = play_person_name;
		this.piece_id = piece_id;
		this.start_hour = start_hour;
		this.end_hour = end_hour;
		this.price_info = price_info;
		this.price_info_type = price_info_type;
		this.code = code;
		this.code_status = code_status;
	}

	public UserOrdersInfo(String oid, String uid, String card_type_id, String card_batch_id, String card_id,
			String account_rule_id, String account_no, String cid, String stadium_id, String stadium_name,
			String venue_id, String merid, String mer_item_id, String mer_item_name, String book_day,
			String order_money, String reduce_money, String pay_money, String status, String book_phone, String note,
			int create_time, String book_number, int current_time) {
		super();
		this.oid = oid;
		this.uid = uid;
		this.card_type_id = card_type_id;
		this.card_batch_id = card_batch_id;
		this.card_id = card_id;
		this.account_rule_id = account_rule_id;
		this.account_no = account_no;
		this.cid = cid;
		this.stadium_id = stadium_id;
		this.stadium_name = stadium_name;
		this.venue_id = venue_id;
		this.merid = merid;
		this.mer_item_id = mer_item_id;
		this.mer_item_name = mer_item_name;
		this.book_day = book_day;
		this.order_money = order_money;
		this.reduce_money = reduce_money;
		this.pay_money = pay_money;
		this.status = status;
		this.book_phone = book_phone;
		this.note = note;
		this.create_time = create_time;
		this.book_number = book_number;
		this.current_time = current_time;
	}

	public String getAccount_text() {
		return account_text;
	}

	public void setAccount_text(String account_text) {
		this.account_text = account_text;
	}

	public String getLife_type() {
		return life_type;
	}

	public void setLife_type(String life_type) {
		this.life_type = life_type;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getOrder_list() {
		return order_list;
	}

	public void setOrder_list(String order_list) {
		this.order_list = order_list;
	}

	public String getAddress_list() {
		return address_list;
	}

	public void setAddress_list(String address_list) {
		this.address_list = address_list;
	}

	public int getUnpay_num() {
		return unpay_num;
	}

	public void setUnpay_num(int unpay_num) {
		this.unpay_num = unpay_num;
	}

	public int getConfirm_num() {
		return confirm_num;
	}

	public void setConfirm_num(int confirm_num) {
		this.confirm_num = confirm_num;
	}

	public int getOff_num() {
		return off_num;
	}

	public void setOff_num(int off_num) {
		this.off_num = off_num;
	}

	public int getCurrent_time() {
		return current_time;
	}

	public void setCurrent_time(int current_time) {
		this.current_time = current_time;
	}

	public String getStadium_thumb() {
		return stadium_thumb;
	}

	public void setStadium_thumb(String stadium_thumb) {
		this.stadium_thumb = stadium_thumb;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCard_type_id() {
		return card_type_id;
	}

	public void setCard_type_id(String card_type_id) {
		this.card_type_id = card_type_id;
	}

	public String getCard_batch_id() {
		return card_batch_id;
	}

	public void setCard_batch_id(String card_batch_id) {
		this.card_batch_id = card_batch_id;
	}

	public String getCard_id() {
		return card_id;
	}

	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}

	public String getAccount_rule_id() {
		return account_rule_id;
	}

	public void setAccount_rule_id(String account_rule_id) {
		this.account_rule_id = account_rule_id;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getStadium_id() {
		return stadium_id;
	}

	public void setStadium_id(String stadium_id) {
		this.stadium_id = stadium_id;
	}

	public String getStadium_name() {
		return stadium_name;
	}

	public void setStadium_name(String stadium_name) {
		this.stadium_name = stadium_name;
	}

	public String getVenue_id() {
		return venue_id;
	}

	public void setVenue_id(String venue_id) {
		this.venue_id = venue_id;
	}

	public String getMerid() {
		return merid;
	}

	public void setMerid(String merid) {
		this.merid = merid;
	}

	public String getMer_item_id() {
		return mer_item_id;
	}

	public void setMer_item_id(String mer_item_id) {
		this.mer_item_id = mer_item_id;
	}

	public String getMer_item_name() {
		return mer_item_name;
	}

	public void setMer_item_name(String mer_item_name) {
		this.mer_item_name = mer_item_name;
	}

	public String getBook_day() {
		return book_day;
	}

	public void setBook_day(String book_day) {
		this.book_day = book_day;
	}

	public String getOrder_money() {
		return order_money;
	}

	public void setOrder_money(String order_money) {
		this.order_money = order_money;
	}

	public String getReduce_money() {
		return reduce_money;
	}

	public void setReduce_money(String reduce_money) {
		this.reduce_money = reduce_money;
	}

	public String getPay_money() {
		return pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBook_phone() {
		return book_phone;
	}

	public void setBook_phone(String book_phone) {
		this.book_phone = book_phone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getCreate_time() {
		return create_time;
	}

	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}

	public String getBook_number() {
		return book_number;
	}

	public void setBook_number(String book_number) {
		this.book_number = book_number;
	}

	public String getOrder_item_id() {
		return order_item_id;
	}

	public void setOrder_item_id(String order_item_id) {
		this.order_item_id = order_item_id;
	}

	public String getMer_price_id() {
		return mer_price_id;
	}

	public void setMer_price_id(String mer_price_id) {
		this.mer_price_id = mer_price_id;
	}

	public String getPrice_type() {
		return price_type;
	}

	public void setPrice_type(String price_type) {
		this.price_type = price_type;
	}

	public String getPlay_time() {
		return play_time;
	}

	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}

	public String getPlay_person_name() {
		return play_person_name;
	}

	public void setPlay_person_name(String play_person_name) {
		this.play_person_name = play_person_name;
	}

	public String getPiece_id() {
		return piece_id;
	}

	public void setPiece_id(String piece_id) {
		this.piece_id = piece_id;
	}

	public String getStart_hour() {
		return start_hour;
	}

	public void setStart_hour(String start_hour) {
		this.start_hour = start_hour;
	}

	public String getEnd_hour() {
		return end_hour;
	}

	public void setEnd_hour(String end_hour) {
		this.end_hour = end_hour;
	}

	public String getPrice_info() {
		return price_info;
	}

	public void setPrice_info(String price_info) {
		this.price_info = price_info;
	}

	public String getPrice_info_type() {
		return price_info_type;
	}

	public void setPrice_info_type(String price_info_type) {
		this.price_info_type = price_info_type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode_status() {
		return code_status;
	}

	public void setCode_status(String code_status) {
		this.code_status = code_status;
	}

	public ArrayList<UserOrdersInfo> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<UserOrdersInfo> itemList) {
		this.itemList = itemList;
	}

}
