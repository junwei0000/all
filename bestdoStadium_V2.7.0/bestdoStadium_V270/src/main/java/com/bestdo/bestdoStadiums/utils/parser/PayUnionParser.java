package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author qyy
 * 
 */
public class PayUnionParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject obj = jsonObject.getJSONObject("data");
				String tn = obj.optString("tn");
				mHashMap.put("tn", tn);
			} else {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
