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
public class UserLoginParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				JSONObject userObject = jsonObject.optJSONObject("data");
				String uid = userObject.optString("uid", "");
				String real_name = userObject.optString("real_name", "");
				String nick_name = userObject.optString("nick_name", "");
				String email = userObject.optString("email", "");
				String telephone = userObject.optString("telephone", "");
				String ablum_url = userObject.optString("ablum_url", "");
				String sex = userObject.optString("sex", "");
				UserLoginInfo loginInfo = new UserLoginInfo(uid, real_name,
						nick_name, email, telephone, ablum_url, sex);
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
