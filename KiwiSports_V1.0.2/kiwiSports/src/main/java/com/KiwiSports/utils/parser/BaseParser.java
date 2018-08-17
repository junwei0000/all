package com.KiwiSports.utils.parser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:06:50
 * @Description 类描述：解析数据
 * @param <T>
 */
public abstract class BaseParser<T> {

	public abstract T parseJSON(JSONObject jsonObject) throws JSONException;

	public String checkResponse(String paramString) throws JSONException {
		if (paramString == null || "".equals(paramString.trim())) {
			return null;
		} else {
			JSONObject jsonObject = new JSONObject(paramString);
			String result = jsonObject.getString("response");
			if (result != null && !result.equals("error")) {
				return result;
			} else {
				return null;
			}

		}
	}
}