package com.zh.education.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zh.education.model.BoKeInfo;
import com.zh.education.model.PingLunInfo;
import com.zh.education.model.UserLoginInfo;

/**
 * @author qyy
 * @Description 类描述：发评论解析
 */
public class SendCommentsParser extends BaseParser<Object> {

	public SendCommentsParser() {
		super();
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {

			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
