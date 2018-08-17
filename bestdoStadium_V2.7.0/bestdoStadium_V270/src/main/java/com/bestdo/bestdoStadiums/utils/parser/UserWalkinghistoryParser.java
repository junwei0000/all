package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:28:45
 * @Description 类描述：
 */
public class UserWalkinghistoryParser extends BaseParser<Object> {

	ArrayList<UserWalkingHistoryInfo> mList;

	public UserWalkinghistoryParser(ArrayList<UserWalkingHistoryInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject dataObj = jsonObject.optJSONObject("data");

				int total = dataObj.optInt("total", 0);
				mHashMap.put("total", total);

				try {
					JSONObject max_dataObj = dataObj.getJSONObject("max_data");
					String max_km_num = max_dataObj.optString("max_km_num");
					String max_step_time = max_dataObj.optString("max_step_time");
					String max_v_num = max_dataObj.optString("max_v_num");
					UserWalkingHistoryInfo mMaxHistoryInfo = new UserWalkingHistoryInfo("", "", max_km_num, max_v_num,
							max_step_time, 0);
					mHashMap.put("mMaxHistoryInfo", mMaxHistoryInfo);
				} catch (Exception e) {
					// TODO: handle exception
				}

				JSONArray historyArray = dataObj.getJSONArray("list");
				for (int i = 0; i < historyArray.length(); i++) {
					JSONObject historyObj = historyArray.getJSONObject(i);
					String id = historyObj.optString("id");
					String step_num = historyObj.optString("step_num");
					String km_num = historyObj.optString("km_num");
					String v_num = historyObj.optString("v_num");
					String step_time = historyObj.optString("step_time");
					int timestamp = historyObj.optInt("uploaddate");
					UserWalkingHistoryInfo mUserWalkingHistoryInfo = new UserWalkingHistoryInfo(id, step_num, km_num,
							v_num, step_time, timestamp);
					mList.add(mUserWalkingHistoryInfo);
					mUserWalkingHistoryInfo = null;
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
