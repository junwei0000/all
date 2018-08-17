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
import com.bestdo.bestdoStadiums.model.StadiumDetailOneDayHourMerItemPriceInfo;
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
public class StadiumDetailGetOneDayHourMerItemPriceParser extends BaseParser<Object> {

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
				String visitor_price = JSONObject.optString("visitor_price", "0");
				String mer_item_id = JSONObject.optString("mer_item_id");
				String mer_price_id = JSONObject.optString("mer_price_id");
				String vip_price = JSONObject.optString("vip_price", "0");
				String end_hour = JSONObject.optString("end_hour");
				String start_hour = JSONObject.optString("start_hour");
				String end_time = JSONObject.optString("end_time");
				String prepay_price = JSONObject.optString("prepay_price", "0");
				String create_time = JSONObject.optString("create_time");
				String start_time = JSONObject.optString("start_time");
				String week = JSONObject.optString("week");
				String mer_item_price_time_id = JSONObject.optString("mer_item_price_time_id");
				vip_price = PriceUtils.getInstance().gteDividePrice(vip_price, "100");
				prepay_price = PriceUtils.getInstance().gteDividePrice(prepay_price, "100");
				visitor_price = PriceUtils.getInstance().gteDividePrice(visitor_price, "100");
				StadiumDetailOneDayHourMerItemPriceInfo mInfo = new StadiumDetailOneDayHourMerItemPriceInfo(
						visitor_price, mer_item_id, mer_price_id, vip_price, end_hour, start_hour, end_time,
						prepay_price, create_time, start_time, week, mer_item_price_time_id);
				mHashMap.put("mOneDayHourMerItemPriceInfo", mInfo);
			}
		} catch (Exception e) {
		}
		return mHashMap;
	}

}
