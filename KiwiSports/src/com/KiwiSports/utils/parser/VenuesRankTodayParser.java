package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.model.VenuesRankTodayInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class VenuesRankTodayParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject Ob = jsonObject.getJSONObject("data");
				JSONObject jsonObs = Ob.getJSONObject("data");

				JSONObject myRankObs = jsonObs.optJSONObject("myRank");
				String distanceTraveled = myRankObs.optString("distanceTraveled", "");
				String uid = myRankObs.optString("uid", "");
				String posid = myRankObs.optString("posid", "");
				String date_time = myRankObs.optString("date_time", "");
				String num = myRankObs.optString("num", "");
				String nick_name = myRankObs.optString("nick_name", "");
				VenuesRankTodayInfo myRankInfo = new VenuesRankTodayInfo(distanceTraveled, uid, posid, date_time, num,
						nick_name);
				mHashMap.put("myRankInfo", myRankInfo);
				ArrayList<VenuesRankTodayInfo> mList = new ArrayList<VenuesRankTodayInfo>();
				JSONArray dayRanka = jsonObs.optJSONArray("dayRank");
				for (int i = 0; i < dayRanka.length(); i++) {
					JSONObject dayRankObs = dayRanka.optJSONObject(i);
					distanceTraveled = dayRankObs.optString("distanceTraveled", "");
					uid = dayRankObs.optString("uid", "");
					posid = dayRankObs.optString("posid", "");
					date_time = dayRankObs.optString("date_time", "");
					num = dayRankObs.optString("num", "");
					nick_name = dayRankObs.optString("nick_name", "");
					VenuesRankTodayInfo dayRankInfo = new VenuesRankTodayInfo(distanceTraveled, uid, posid, date_time,
							num, nick_name);
					mList.add(dayRankInfo);
					dayRankInfo = null;
				}

				mHashMap.put("mList", mList);
			} else {
				String msg = jsonObject.optString("data");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
