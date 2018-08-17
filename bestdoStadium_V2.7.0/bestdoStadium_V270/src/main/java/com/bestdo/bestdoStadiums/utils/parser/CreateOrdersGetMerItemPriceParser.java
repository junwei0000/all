package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.CreatOrderGetMerItemPriceInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-27 下午5:28:45
 * @Description 类描述：
 */
public class CreateOrdersGetMerItemPriceParser extends BaseParser<Object> {

	ArrayList<CreatOrderGetMerItemPriceInfo> mList;

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONArray oo = jsonObject.getJSONArray("data");
				mList = new ArrayList<CreatOrderGetMerItemPriceInfo>();
				for (int i = 0; i < oo.length(); i++) {
					JSONObject cardobj = oo.getJSONObject(i);

					String mer_item_price_id = cardobj.optString("mer_item_price_id");
					String mer_item_price_time_id = cardobj.optString("mer_item_price_time_id");
					String mer_price_id = cardobj.optString("mer_price_id");
					String mer_item_id = cardobj.optString("mer_item_id");
					String start_time = cardobj.optString("start_time");
					String end_time = cardobj.optString("end_time");
					String week = cardobj.optString("week");
					String start_hour = cardobj.optString("start_hour");
					String end_hour = cardobj.optString("end_hour");
					String prepay_price = cardobj.optString("prepay_price");
					prepay_price = PriceUtils.getInstance().gteDividePrice(prepay_price, "100");
					String type = cardobj.optString("type");
					CreatOrderGetMerItemPriceInfo mInfo = new CreatOrderGetMerItemPriceInfo(mer_item_price_id,
							mer_item_price_time_id, mer_price_id, mer_item_id, start_time, end_time, week, start_hour,
							end_hour, prepay_price, type, "", "", "", "", "", "", "", "", "");
					mList.add(mInfo);
					mInfo = null;
				}
				mHashMap.put("mList", mList);
				mList = null;
			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
