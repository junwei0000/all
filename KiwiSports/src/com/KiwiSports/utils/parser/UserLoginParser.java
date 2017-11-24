package com.KiwiSports.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class UserLoginParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject jsonOb = jsonObject.getJSONObject("data");
				String uid = jsonOb.optString("uid", "");
				String sid = jsonOb.optString("sid", "");
				String mobile = jsonOb.optString("telephone", "");

				// mHashMap.put("loginInfo", loginInfo);
				// loginInfo = null;
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
