package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.mapapi.model.LatLng;
import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:28:45
 * @Description 类描述：
 */
public class UserWalkingGetTrackParser extends BaseParser<Object> {

	ArrayList<LatLng> mList;

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject dataObj = jsonObject.optJSONObject("data");

				int step_num = dataObj.optInt("step_num", 0);
				Double km_num = dataObj.optDouble("km_num", 0);
				String v_num = dataObj.optString("v_num", "");
				String step_time = dataObj.optString("step_time", "");
				String endtime = dataObj.optString("endtime", "");
				mHashMap.put("step_num", step_num);
				mHashMap.put("km_num", km_num);
				mHashMap.put("v_num", v_num);
				mHashMap.put("step_time", step_time);
				mHashMap.put("endtime", endtime);

				mList = new ArrayList<LatLng>();
				JSONArray historyArray = dataObj.getJSONArray("loc_data");
				for (int i = 0; i < historyArray.length(); i++) {
					JSONObject historyObj = historyArray.getJSONObject(i);
					Double latitude = historyObj.optDouble("latitude");
					Double longitude = historyObj.optDouble("longitude");
					LatLng mLatLng = new LatLng(latitude, longitude);
					mList.add(mLatLng);
				}
				mHashMap.put("mList", mList);
			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}
}
