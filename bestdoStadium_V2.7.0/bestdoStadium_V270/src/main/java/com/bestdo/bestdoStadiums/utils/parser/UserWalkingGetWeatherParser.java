package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.model.UserWalkingWeatherInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-26 上午10:28:07
 * @Description 类描述：运动类型
 */
public class UserWalkingGetWeatherParser {

	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			}
			if (status.equals("200")) {

				JSONObject jsonOb = jsonObject.getJSONObject("data");
				String wendu = jsonOb.optString("wendu", "");
				String city = jsonOb.optString("city", "");
				String pm = jsonOb.optString("pm25", "");
				String bid = jsonOb.optString("bid", "");
				String bname = jsonOb.optString("bname", "");
				int step_num = jsonOb.optInt("step_num", 0);
				UserWalkingWeatherInfo mUserWalkingWeatherInfo = new UserWalkingWeatherInfo(wendu, city, pm, bid, bname,
						step_num);

				mHashMap.put("mUserWalkingWeatherInfo", mUserWalkingWeatherInfo);
			}
		} catch (Exception e) {
		}
		return mHashMap;
	}

}
