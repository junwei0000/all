package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.UserLoginInfo;

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
			String status = jsonObject.optString("code");
			if (!TextUtils.isEmpty(status)) {
				mHashMap.put("status", status);
				if (status.equals("200")) {
					JSONObject jsonOb = jsonObject.getJSONObject("data");
					String fileUrl = jsonOb.optString("fileUrl");
					mHashMap.put("ablum", fileUrl);
				} else {
					String msg = jsonObject.optString("msg");
					mHashMap.put("msg", msg);
				}
			}

		} catch (Exception e) {
		}

		return mHashMap;
	}

}
