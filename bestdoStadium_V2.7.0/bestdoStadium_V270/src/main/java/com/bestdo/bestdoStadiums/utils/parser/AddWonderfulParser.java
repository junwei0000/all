package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author qyy
 * 
 */
public class AddWonderfulParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				JSONObject o = jsonObject.optJSONObject("data");
				String show_url = o.optString("wondful_show_url", "");
				mHashMap.put("wondful_show_url", show_url);

			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
