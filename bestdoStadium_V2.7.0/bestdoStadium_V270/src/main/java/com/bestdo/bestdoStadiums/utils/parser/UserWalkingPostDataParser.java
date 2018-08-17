package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesPricesInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author jun
 * 
 */
public class UserWalkingPostDataParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("402")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("200")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			}
		} catch (Exception e) {
		}
		return mHashMap;
	}
}
