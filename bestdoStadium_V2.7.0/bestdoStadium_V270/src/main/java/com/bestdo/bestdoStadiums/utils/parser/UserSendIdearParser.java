package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author qyy
 * 
 */
public class UserSendIdearParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
