package com.KiwiSports.utils.parser;

import java.util.HashMap;
import org.json.JSONObject;

/**
 * 
 * @author qyy
 * 
 */
public class GrabTreasureParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			String msg = jsonObject.optString("data");
			mHashMap.put("msg", msg);

		} catch (Exception e) {
		}
		return mHashMap;
	}

}
