package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonArrayRequest;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 场馆列表
 * 
 * @author qyy
 * 
 */
public class StadiumParser extends BaseParser<Object> {

	ArrayList<StadiumInfo> mList;

	public StadiumParser(ArrayList<StadiumInfo> mList) {
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
				JSONObject jdate = jsonObject.optJSONObject("data");
				int total = jdate.optInt("total");
				mHashMap.put("total", total);
				JSONArray stadiumArray = jdate.getJSONArray("items");
				for (int i = 0; i < stadiumArray.length(); i++) {
					JSONObject stadiumObj = stadiumArray.optJSONObject(i);
					String mer_item_id = stadiumObj.optString("mer_item_id");
					String mer_price_id = stadiumObj.optString("mer_price_id");
					String merid = stadiumObj.optString("merid");
					String cid = stadiumObj.optString("cid");
					String name = stadiumObj.optString("name");
					String position = stadiumObj.optString("position");
					String price_info = stadiumObj.optString("price_info");
					String thumb = stadiumObj.optString("thumb");
					String province = stadiumObj.optString("province");
					String city = stadiumObj.optString("city");
					String district = stadiumObj.optString("district");
					String region = stadiumObj.optString("region");
					String latitude = stadiumObj.optString("latitude");
					String longitude = stadiumObj.optString("longitude");
					String lat = stadiumObj.optString("lat");
					String lng = stadiumObj.optString("lng");
					String price = stadiumObj.optString("price_default");
					String price_vip = stadiumObj.optString("price_vip");
					String geodist = stadiumObj.optString("@geodist");
					price_vip = PriceUtils.getInstance().gteDividePrice(price_vip, "100");
					price = PriceUtils.getInstance().gteDividePrice(price, "100");
					String vip_price_id = stadiumObj.optString("vip_price_id", "");
					String address = stadiumObj.optString("address");

					StadiumInfo mStadiumInfo = new StadiumInfo(mer_item_id, mer_price_id, merid, cid, name, position,
							price_info, thumb, province, city, district, region, latitude, longitude, lat, lng, price,
							price_vip, geodist, vip_price_id, address);
					mList.add(mStadiumInfo);
					mStadiumInfo = null;
				}
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
			mHashMap.put("mList", mList);
		} catch (Exception e) {
		}
		return mHashMap;
	}

}
