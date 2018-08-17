package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:05:02
 * @Description 类描述：
 */
public class UserGetCodeParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject data = jsonObject.optJSONObject("data");
				String validId = data.optString("validId");
				mHashMap.put("validId", validId);
			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
