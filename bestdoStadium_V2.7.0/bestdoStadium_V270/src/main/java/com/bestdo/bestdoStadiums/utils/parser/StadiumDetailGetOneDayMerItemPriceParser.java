package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.CreatOrdersGetVenuePYLWInfo2;
import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailOneDayMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesPricesInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * @author jun
 * 
 */
public class StadiumDetailGetOneDayMerItemPriceParser extends BaseParser<Object> {

	private String inventory_type;

	public StadiumDetailGetOneDayMerItemPriceParser(String inventory_type) {
		super();
		this.inventory_type = inventory_type;
	}

	DatesUtils dateUtils;

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			} else if (status.equals("402")) {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			} else if (status.equals("401")) {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			} else if (status.equals("200")) {
				JSONObject JSONObject = jsonObject.optJSONObject("data");
				if (inventory_type.equals("1")) {

					JSONArray jSONArray = JSONObject.optJSONArray("inventory_info");
					if (jSONArray != null) {
						ArrayList<CreatOrdersGetVenuePYLWInfo2> changdiList = new ArrayList<CreatOrdersGetVenuePYLWInfo2>();

						for (int i = 0; i < jSONArray.length(); i++) {
							JSONObject jSONObject = jSONArray.optJSONObject(i);
							CreatOrdersGetVenuePYLWInfo2 venuePYLWInfo = new CreatOrdersGetVenuePYLWInfo2();

							String status_ = jSONObject.optString("status");
							String name = jSONObject.optString("name");
							String piece_id = jSONObject.optString("piece_id");
							String venue_id = jSONObject.optString("venue_id");

							venuePYLWInfo.setStatus(status_);
							venuePYLWInfo.setName(name);
							venuePYLWInfo.setPiece_id(piece_id);
							venuePYLWInfo.setVenue_id(venue_id);

							JSONArray array = jSONObject.optJSONArray("hour");

							if (array != null) {
								ArrayList<CreatOrdersGetVenuePYLWInfo2> venuesList = new ArrayList<CreatOrdersGetVenuePYLWInfo2>();
								for (int j = 0; j < array.length(); j++) {

									CreatOrdersGetVenuePYLWInfo2 venuePYLWInfo2 = new CreatOrdersGetVenuePYLWInfo2();

									JSONObject obj = array.optJSONObject(j);
									String start_hour = obj.optString("start_hour", "");
									String mer_item_id = obj.optString("mer_item_id", "");
									String end_time = obj.optString("end_time", "");
									String piecebookstatus = obj.optString("status", "");
									String prepay_price = obj.optString("prepay_price", "");
									if (!TextUtils.isEmpty(prepay_price)) {

										prepay_price = PriceUtils.getInstance().gteDividePrice(prepay_price, "100");
										prepay_price = PriceUtils.getInstance().seePrice(prepay_price);
									}
									String end_hour = obj.optString("end_hour", "");
									String start_time = obj.optString("start_time", "");
									String mer_price_id = obj.optString("mer_price_id", "");
									String mer_item_price_id = obj.optString("mer_item_price_id", "");
									String hour = obj.optString("hour", "");
									String type = obj.optString("type", "");
									String week = obj.optString("week", "");
									String mer_item_price_time_id = obj.optString("mer_item_price_time_id", "");
									if (!TextUtils.isEmpty(start_hour) && !TextUtils.isEmpty(end_hour)) {

										int starthour = Integer.parseInt(start_hour);
										int endhour = Integer.parseInt(end_hour);
										if (starthour < 10) {
											start_hour = "0" + start_hour;
										}
										if (endhour < 10) {
											end_hour = "0" + end_hour;
										}
										start_hour = DatesUtils.getInstance().getMaoHao(start_hour);
										end_hour = DatesUtils.getInstance().getMaoHao(end_hour);
									}
									venuePYLWInfo2.setPiece_id(piece_id);
									venuePYLWInfo2.setStart_hour(start_hour);
									venuePYLWInfo2.setMer_item_id(mer_item_id);
									venuePYLWInfo2.setEnd_time(end_time);
									venuePYLWInfo2.setPiecebookstatus(piecebookstatus);
									venuePYLWInfo2.setPrepay_price(prepay_price);
									venuePYLWInfo2.setEnd_hour(end_hour);
									venuePYLWInfo2.setStart_time(start_time);
									venuePYLWInfo2.setMer_price_id(mer_price_id);
									venuePYLWInfo2.setMer_item_price_id(mer_item_price_id);
									venuePYLWInfo2.setHour(hour);
									venuePYLWInfo2.setType(type);
									venuePYLWInfo2.setWeek(week);
									venuePYLWInfo2.setMer_item_price_time_id(mer_item_price_time_id);
									venuePYLWInfo2.setIs_select(false);
									venuesList.add(venuePYLWInfo2);
								}
								venuePYLWInfo.setVenuesList(venuesList);
							}
							changdiList.add(venuePYLWInfo);
						}
						mHashMap.put("changdiList", changdiList);
					}

				} else if (inventory_type.equals("2")) {

				} else if (inventory_type.equals("3")) {

				}

			}
		} catch (Exception e) {
		}
		return mHashMap;
	}

}
