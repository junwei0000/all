package com.KiwiSports.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

import android.text.TextUtils;


/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class UserAccountUpdateAblumParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("jsonObject", jsonObject.toString());
			String status = jsonObject.optString("status");
				mHashMap.put("status", status);
				if (status.equals("200")) {
					String fileUrl = jsonObject.optString("data");
					mHashMap.put("ablum", fileUrl);
				} else {
					String msg = jsonObject.optString("data");
					mHashMap.put("msg", msg);
				}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
