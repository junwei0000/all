package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.UserLoginInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-16 下午2:27:39
 * @Description 类描述：
 */
public class UserFindPWParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject jsonOb = jsonObject.getJSONObject("data");
				String result = jsonOb.optString("result");
				mHashMap.put("result", result);
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
