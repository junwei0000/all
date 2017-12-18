package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.model.VenuesListInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class VenuesInfoByLocationParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject Ob = jsonObject.getJSONObject("data");
				String posid = Ob.optString("posid", "");
				mHashMap.put("posid", posid);
			} else {
				String msg = jsonObject.optString("data");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}