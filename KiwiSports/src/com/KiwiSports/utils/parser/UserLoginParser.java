package com.KiwiSports.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

import com.KiwiSports.model.UserLoginInfo;

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
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject jsonOb = jsonObject.getJSONObject("data");
				String uid = jsonOb.optString("uid", "");
				String nick_name = jsonOb.optString("nick_name", "");
				String album_url = jsonOb.optString("album_url", "");
				String hobby = jsonOb.optString("hobby", "");
				int sex = jsonOb.optInt("sex", 1);
				String token = jsonOb.optString("token", "");
				String access_token = jsonOb.optString("access_token", "");
				UserLoginInfo loginInfo = new UserLoginInfo(uid, nick_name, album_url, hobby, sex, token, access_token);
				mHashMap.put("loginInfo", loginInfo);
				loginInfo = null;
			} else {
				String msg = jsonObject.optString("data");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
