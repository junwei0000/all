package com.KiwiSports.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;


/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class UserAccountUpdateParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			String data = jsonObject.getString("data");
			mHashMap.put("msg", data);
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
