package com.bestdo.bestdoStadiums.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 订单详情
 * 
 * @author jun
 * 
 */
public class UserOrderDetailsInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uid;
	private String Stats;// 订单状态
	private String contain;
	private String vip_price_id;
	/**
	 * 订单信息
	 */
	private String cid;// 运动类型
	private String oid;
	private String mer_price_id;
	private String merid;
	private String mer_item_id;
	private String mer_item_name;
	private ArrayList<UserOrderDetailsInfo> itemOrderList;

	private String code;
	private String code_status;
	private String play_time;
	private String day;
	private int create_time;
	private int current_time;

	private String start_time;
	private String end_time;
	private String number;
	private String phone;

	private String is_set_supplier;
	private String process_type;
	/**
	 * 支付信息
	 */
	private String pay_money;
	private String order_money;
	private String reduce_money;// 抵扣
	private String member_reduce_money;
	private String balance;
	/**
	 * 联系人信息
	 */
	private String players;
	/**
	 * 场馆信息
	 */
	private String address;
	private String stadiumtel;

	public UserOrderDetailsInfo() {
		super();
	}

	public UserOrderDetailsInfo(String start_time, String end_time, String play_time, String code, String code_status) {
		super();
		this.start_time = start_time;
		this.end_time = end_time;
		this.play_time = play_time;
		this.code = code;
		this.code_status = code_status;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	/**
	 * @return the member_reduce_money
	 */
	public String getMember_reduce_money() {
		return member_reduce_money;
	}

	/**
	 * @param member_reduce_money
	 *            the member_reduce_money to set
	 */
	public void setMember_reduce_money(String member_reduce_money) {
		this.member_reduce_money = member_reduce_money;
	}

	public String getVip_price_id() {
		return vip_price_id;
	}

	public void setVip_price_id(String vip_price_id) {
		this.vip_price_id = vip_price_id;
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

	public String getIs_set_supplier() {
		return is_set_supplier;
	}

	public void setIs_set_supplier(String is_set_supplier) {
		this.is_set_supplier = is_set_supplier;
	}

	public String getProcess_type() {
		return process_type;
	}

	public void setProcess_type(String process_type) {
		this.process_type = process_type;
	}

	public String getStadiumtel() {
		return stadiumtel;
	}

	public void setStadiumtel(String stadiumtel) {
		this.stadiumtel = stadiumtel;
	}

	public String getMer_price_id() {
		return mer_price_id;
	}

	public void setMer_price_id(String mer_price_id) {
		this.mer_price_id = mer_price_id;
	}

	public String getReduce_money() {
		return reduce_money;
	}

	public void setReduce_money(String reduce_money) {
		this.reduce_money = reduce_money;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getPlay_time() {
		return play_time;
	}

	public void setPlay_time(String play_time) {
		this.play_time = play_time;
	}

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStats() {
		return Stats;
	}

	public void setStats(String stats) {
		Stats = stats;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public ArrayList<UserOrderDetailsInfo> getItemOrderList() {
		return itemOrderList;
	}

	public void setItemOrderList(ArrayList<UserOrderDetailsInfo> itemOrderList) {
		this.itemOrderList = itemOrderList;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public int getCurrent_time() {
		return current_time;
	}

	public void setCurrent_time(int current_time) {
		this.current_time = current_time;
	}

	public String getPay_money() {
		return pay_money;
	}

	public void setPay_money(String pay_money) {
		this.pay_money = pay_money;
	}

	public String getOrder_money() {
		return order_money;
	}

	public void setOrder_money(String order_money) {
		this.order_money = order_money;
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getCreate_time() {
		return create_time;
	}

	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}

}
