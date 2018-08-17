package com.KiwiSports.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class VenuesInfoByLocationParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject Ob = jsonObject.getJSONObject("data");
				String posid = Ob.optString("posid", "");
				String sportsType = Ob.optString("sportsType", "");
				String field_name = Ob.optString("field_name", "");
				mHashMap.put("sportsType", sportsType);
				mHashMap.put("field_name", field_name);
				mHashMap.put("posid", posid);
			} else {
				String msg = jsonObject.optString("data");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
