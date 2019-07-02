package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import com.KiwiSports.model.HobbyInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class VenuesHobbyParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject jsonOb = jsonObject.getJSONObject("data");
				HashMap<String, String> mHobbyMap = new HashMap<String, String>();
				ArrayList<HobbyInfo> mList = new ArrayList<HobbyInfo>();
				@SuppressWarnings("rawtypes")
				Iterator iterator = jsonOb.keys();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String value = jsonOb.optString(key);
					HobbyInfo mHobbyInfo = new HobbyInfo(key, value);
					mList.add(mHobbyInfo);
					mHobbyInfo = null;
					mHobbyMap.put(key, value);
				}
				mHashMap.put("mHobbyMap", mHobbyMap);
				mHashMap.put("mList", mList);
			} else {
				String msg = jsonObject.optString("data");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
