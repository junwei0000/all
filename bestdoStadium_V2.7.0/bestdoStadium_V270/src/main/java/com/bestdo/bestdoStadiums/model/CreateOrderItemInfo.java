/**
 * 
 */
package com.bestdo.bestdoStadiums.model;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-3-1 下午2:58:40
 * @Description 类描述：创建订单item
 */
public class CreateOrderItemInfo {
	String mer_price_id;
	String play_time;
	String play_person_name;
	String start_hour;
	String end_hour;
	String order_money;
	String reduce_money;
	String pay_money;
	String is_rights;
	String piece_id;
	String member_reduce_money;

	public CreateOrderItemInfo() {
		super();
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

	public String getMer_price_id() {
		return mer_price_id;
	}

	public void setMer_price_id(String mer_price_id) {
		this.mer_price_id = mer_price_id;
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

	public String getIs_rights() {
		return is_rights;
	}

	public void setIs_rights(String is_rights) {
		this.is_rights = is_rights;
	}

	public String getPiece_id() {
		return piece_id;
	}

	public void setPiece_id(String piece_id) {
		this.piece_id = piece_id;
	}

}
