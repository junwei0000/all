package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.UserOrderDetailsInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author Qyy @
 */
public class UserOrderDetailsParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				JSONObject oo = jsonObject.getJSONObject("data");
				UserOrderDetailsInfo userOrderDetailsInfo = new UserOrderDetailsInfo();
				userOrderDetailsInfo.setUid(oo.optString("uid"));
				userOrderDetailsInfo.setStats(oo.optString("status"));// 订单状态
				userOrderDetailsInfo.setVip_price_id(oo.optString("vip_price_id"));
				String is_set_supplier = oo.optString("is_set_supplier");
				String process_type = oo.optString("process_type");

				String ordermoney = oo.optString("order_money");
				ordermoney = PriceUtils.getInstance().gteDividePrice(ordermoney, "100");
				ordermoney = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(ordermoney));
				String reduce_money = oo.optString("reduce_money");
				reduce_money = PriceUtils.getInstance().gteDividePrice(reduce_money, "100");
				reduce_money = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(reduce_money));
				String pay_money = oo.optString("pay_money");
				pay_money = PriceUtils.getInstance().gteDividePrice(pay_money, "100");
				pay_money = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(pay_money));
				String balance = oo.optString("balance_reduce_money");
				balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
				balance = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(balance));
				String member_reduce_money = oo.optString("member_reduce_money");
				member_reduce_money = PriceUtils.getInstance().gteDividePrice(member_reduce_money, "100");
				member_reduce_money = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(member_reduce_money));
				userOrderDetailsInfo.setMember_reduce_money(member_reduce_money);
				userOrderDetailsInfo.setIs_set_supplier(is_set_supplier);
				userOrderDetailsInfo.setProcess_type(process_type);
				userOrderDetailsInfo.setOrder_money(ordermoney);
				userOrderDetailsInfo.setReduce_money(reduce_money);
				userOrderDetailsInfo.setPay_money(pay_money);
				userOrderDetailsInfo.setBalance(balance);
				JSONObject stadiumobj = oo.getJSONObject("stadium");
				String stadiumtel = stadiumobj.optString("phone");
				userOrderDetailsInfo.setStadiumtel(stadiumtel);
				/**
				 * 订单信息
				 */
				userOrderDetailsInfo.setCid(oo.optString("cid"));
				if (!oo.isNull("items")) {
					ArrayList<UserOrderDetailsInfo> itemOrderList;
					JSONArray oo2 = oo.optJSONArray("items");
					if (oo2 != null) {
						itemOrderList = new ArrayList<UserOrderDetailsInfo>();
						StringBuffer players = new StringBuffer();
						for (int i = 0; i < oo2.length(); i++) {
							JSONObject obj2 = oo2.getJSONObject(i);
							String start_time;
							int starttime = obj2.optInt("start_hour");
							start_time = DatesUtils.getInstance().getDateGeShi("" + starttime, "HH", "HH:mm");
							String end_time;
							int endtime = obj2.optInt("end_hour");
							if (endtime < 24) {
								end_time = DatesUtils.getInstance().getDateGeShi("" + endtime, "HH", "HH:mm");
							} else {
								end_time = endtime + ":00";
							}
							String play_time = obj2.optString("play_time");
							if (!TextUtils.isEmpty(play_time) && !play_time.contains(":")) {
								play_time = DatesUtils.getInstance().getDateGeShi(play_time, "HH", "HH:mm");
							}
							String code = obj2.optString("code");
							String code_status = obj2.optString("code_status");
							UserOrderDetailsInfo mInfo = new UserOrderDetailsInfo(start_time, end_time, play_time, code,
									code_status);
							itemOrderList.add(mInfo);
							String play_person_name = obj2.optString("play_person_name");
							if (!TextUtils.isEmpty(play_person_name)) {
								if (i != oo2.length() - 1) {
									players.append(play_person_name + ",");
								} else {
									players.append(play_person_name);
								}
							}
						}
						userOrderDetailsInfo.setMer_price_id(oo2.getJSONObject(0).optString("mer_price_id"));
						userOrderDetailsInfo.setOid(oo2.getJSONObject(0).optString("oid"));// 订单号
						userOrderDetailsInfo.setContain(oo2.getJSONObject(0).optString("price_info_type_name"));
						userOrderDetailsInfo.setItemOrderList(itemOrderList);
						userOrderDetailsInfo.setPlayers(players + "");
					}
				}
				/**
				 * 场馆信息
				 */
				userOrderDetailsInfo.setNumber(oo.optString("book_number"));
				userOrderDetailsInfo.setPhone(oo.optString("book_phone"));
				userOrderDetailsInfo.setDay(oo.optString("book_day"));
				userOrderDetailsInfo.setMer_item_name(oo.optString("mer_item_name"));
				userOrderDetailsInfo.setMer_item_id(oo.optString("mer_item_id"));
				userOrderDetailsInfo.setMerid(oo.optString("merid"));
				userOrderDetailsInfo.setCurrent_time(oo.optInt("current_time"));// 当前时间
				userOrderDetailsInfo.setCreate_time(oo.optInt("create_time"));

				JSONObject ooStadium = oo.getJSONObject("stadium");
				userOrderDetailsInfo.setAddress(ooStadium.optString("address"));

				mHashMap.put("userOrderDetailsInfo", userOrderDetailsInfo);
				userOrderDetailsInfo = null;
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
