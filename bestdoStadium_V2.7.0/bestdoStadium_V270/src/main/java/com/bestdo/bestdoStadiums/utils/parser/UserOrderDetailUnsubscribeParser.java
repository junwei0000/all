package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author qyy
 * 
 */
public class UserOrderDetailUnsubscribeParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			String msg;
			if (status.equals("200")) {
				msg = jsonObject.getString("data");
			} else {
				msg = jsonObject.getString("msg");
			}
			mHashMap.put("msg", msg);
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
