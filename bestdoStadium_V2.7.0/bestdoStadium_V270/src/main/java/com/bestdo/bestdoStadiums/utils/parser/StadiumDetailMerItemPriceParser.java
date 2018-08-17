package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesPricesInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-25 下午12:05:57
 * @Description 类描述：
 */
public class StadiumDetailMerItemPriceParser extends BaseParser<Object> {

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
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				dateUtils = DatesUtils.getInstance();
				ArrayList<StadiumDetailMerItemPriceInfo> m7DaysPriceList = new ArrayList<StadiumDetailMerItemPriceInfo>();
				String mer_item_price_summary_id = "";
				String mer_price_id = "";
				String mer_item_id = "";
				String day = "";
				String min_price = "";
				String max_price = "";
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject o = jsonArray.optJSONObject(i);
					int datetamp = o.optInt("date", 0);
					Boolean is_order = false;
					int inventory_summaray = o.optInt("inventory_summary", 0);
					try {
						JSONObject obj = o.optJSONObject("price_summaray");
						if (obj != null) {
							mer_item_price_summary_id = obj.optString("mer_item_price_summary_id", "");
							mer_price_id = obj.optString("mer_price_id", "");
							mer_item_id = obj.optString("mer_item_id", "");
							day = obj.optString("day", "");
							min_price = obj.optString("min_price", "");
							max_price = obj.optString("max_price", "");
							if (!TextUtils.isEmpty(min_price) && !TextUtils.isEmpty(max_price)) {
								min_price = PriceUtils.getInstance().gteDividePrice(min_price, "100");
								min_price = PriceUtils.getInstance().seePrice(min_price);
								max_price = PriceUtils.getInstance().gteDividePrice(max_price, "100");
								max_price = PriceUtils.getInstance().seePrice(max_price);
							}
							is_order = true;
						} else {
							mer_item_price_summary_id = "";
							mer_price_id = "";
							mer_item_id = "";
							day = "";
							min_price = "";
							max_price = "";
							is_order = false;
						}
					} catch (Exception e) {
					}
					// 库存为0不可以预定
					if (inventory_summaray <= 0) {
						is_order = false;
					}
					Boolean is_select;
					if (i == 0) {
						is_select = true;
					} else {
						is_select = false;
					}
					StadiumDetailMerItemPriceInfo merItemPriceInfo = new StadiumDetailMerItemPriceInfo(datetamp,
							inventory_summaray, is_select, mer_item_price_summary_id, mer_price_id, mer_item_id, day,
							min_price, max_price, is_order);
					m7DaysPriceList.add(merItemPriceInfo);
					merItemPriceInfo = null;
				}
				mHashMap.put("m7DaysPriceList", m7DaysPriceList);

			}
		} catch (Exception e) {
		}
		return mHashMap;
	}
}
