package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

import android.util.Log;

import com.bestdo.bestdoStadiums.model.UserLoginInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：得到用户信息
 */
public class UserGetInfoParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		Log.e("jsonObject--", "" + jsonObject);
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject jsonOb = jsonObject.getJSONObject("data");
				UserLoginInfo loginInfo = new UserLoginInfo();
				String uid = jsonOb.optString("uid");
				String sex = jsonOb.optString("sex");
				loginInfo.setUid(uid);
				loginInfo.setSex(sex);
				mHashMap.put("loginInfo", loginInfo);
				loginInfo = null;
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
