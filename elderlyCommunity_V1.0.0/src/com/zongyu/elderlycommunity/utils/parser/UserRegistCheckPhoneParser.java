package com.zongyu.elderlycommunity.utils.parser;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zongyu.elderlycommunity.model.UserLoginInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class UserRegistCheckPhoneParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				// "data":"1" ：1可用 0：不可用
				String data = jsonObject.optString("data");
				mHashMap.put("data", data);
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
