package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:28:45
 * @Description 类描述：
 */
public class UserOrderParser extends BaseParser<Object> {

	ArrayList<UserOrdersInfo> mList;

	public UserOrderParser(ArrayList<UserOrdersInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject dataObj = jsonObject.getJSONObject("data");

				int total = dataObj.getInt("total");
				mHashMap.put("total", total);

				JSONObject orderDiffNumObj = dataObj.getJSONObject("orderDiffNum");
				int unpay_num = orderDiffNumObj.optInt("unpay_num");
				int confirm_num = orderDiffNumObj.optInt("confirm_num");
				int off_num = orderDiffNumObj.optInt("off_num");
				UserOrdersInfo mOrdersNumInfo = new UserOrdersInfo(unpay_num, confirm_num, off_num);
				mHashMap.put("mOrdersNumInfo", mOrdersNumInfo);

				if (!dataObj.isNull("orders")) {

					JSONArray array = dataObj.getJSONArray("orders");
					for (int i = 0; i < array.length(); i++) {
						JSONObject orderslistobj = array.getJSONObject(i);
						String oid = orderslistobj.optString("oid");
						String uid = orderslistobj.optString("uid");
						String card_type_id = orderslistobj.optString("card_type_id");
						String card_batch_id = orderslistobj.optString("card_batch_id");
						String card_id = orderslistobj.optString("card_id");
						String account_rule_id = orderslistobj.optString("account_rule_id");
						String account_no = orderslistobj.optString("account_no");
						String cid = orderslistobj.optString("cid");
						String stadium_id = orderslistobj.optString("stadium_id");
						String stadium_name = orderslistobj.optString("stadium_name");
						String venue_id = orderslistobj.optString("venue_id");
						String merid = orderslistobj.optString("merid");
						String mer_item_id = orderslistobj.optString("mer_item_id");
						String mer_item_name = orderslistobj.optString("mer_item_name");
						String book_day = orderslistobj.optString("book_day");
						String order_money = orderslistobj.optString("order_money");
						String reduce_money = orderslistobj.optString("reduce_money");
						String pay_money = orderslistobj.optString("pay_money");
						String ordersstatus = orderslistobj.optString("status");
						String book_phone = orderslistobj.optString("book_phone");
						String life_type = orderslistobj.optString("life_type");
						String note = orderslistobj.optString("note");
						int create_time = orderslistobj.optInt("create_time");
						String book_number = orderslistobj.optString("book_number");
						String stadium_thumb = orderslistobj.optString("thumb");
						int current_time = orderslistobj.optInt("current_time");
						UserOrdersInfo mInfo = new UserOrdersInfo(oid, uid, card_type_id, card_batch_id, card_id,
								account_rule_id, account_no, cid, stadium_id, stadium_name, venue_id, merid,
								mer_item_id, mer_item_name, book_day, order_money, reduce_money, pay_money,
								ordersstatus, book_phone, note, create_time, book_number, current_time);
						mInfo.setStadium_thumb(stadium_thumb);
						mInfo.setLife_type(life_type);
						/**
						 * items
						 */
						ArrayList<UserOrdersInfo> itemList = new ArrayList<UserOrdersInfo>();
						try {
							JSONArray oritemarray = orderslistobj.getJSONArray("items");
							for (int j = 0; j < oritemarray.length(); j++) {
								JSONObject itemObj = oritemarray.getJSONObject(j);
								String order_item_id = itemObj.optString("order_item_id");
								String mer_price_id = itemObj.optString("mer_price_id");
								String price_type = itemObj.optString("price_type");
								String play_time = itemObj.optString("play_time");
								if (!TextUtils.isEmpty(play_time) && !play_time.contains(":")) {
									play_time = DatesUtils.getInstance().getDateGeShi(play_time, "HH", "HH:mm");
								}
								String play_person_name = itemObj.optString("play_person_name");
								String piece_id = itemObj.optString("piece_id");
								String start_hour;
								int starttime = itemObj.optInt("start_hour");
								start_hour = DatesUtils.getInstance().getDateGeShi("" + starttime, "HH", "HH:mm");
								String end_hour;
								int endtime = itemObj.optInt("end_hour");
								if (endtime < 24) {
									end_hour = DatesUtils.getInstance().getDateGeShi("" + endtime, "HH", "HH:mm");
								} else {
									end_hour = endtime + ":00";
								}
								String price_info = itemObj.optString("price_info");
								String price_info_type = itemObj.optString("price_info_type");
								String code = itemObj.optString("code");
								String account = itemObj.optString("account");
								String pwd = itemObj.optString("pwd");
								String account_text = itemObj.optString("account_text");
								String code_status = itemObj.optString("code_status");
								UserOrdersInfo mItemInfo = new UserOrdersInfo(order_item_id, mer_price_id, price_type,
										play_time, play_person_name, piece_id, start_hour, end_hour, price_info,
										price_info_type, code, code_status);
								mItemInfo.setAccount_text(account_text);
								mItemInfo.setAccount(account);
								mItemInfo.setPwd(pwd);
								itemList.add(mItemInfo);
								mItemInfo = null;
							}
						} catch (Exception e) {
						}
						mInfo.setItemList(itemList);
						mList.add(mInfo);
						mInfo = null;
					}
				}
				mHashMap.put("mList", mList);
			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}
}
