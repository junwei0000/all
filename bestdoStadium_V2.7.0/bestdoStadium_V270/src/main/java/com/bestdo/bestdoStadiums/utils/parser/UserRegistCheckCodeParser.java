package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-16 下午3:05:04
 * @Description 类描述：
 */
public class UserRegistCheckCodeParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.optString("data");
				mHashMap.put("data", data);
			} else if (status.equals("402")) {
				String data = jsonObject.optString("data");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.optString("data");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				String data = jsonObject.optString("data");
				mHashMap.put("data", data);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
