package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午4:27:48
 * @Description 类描述：
 */
public class CampaignListParser extends BaseParser<Object> {
	ArrayList<CampaignListInfo> mList;

	public CampaignListParser() {
		super();
	}

	public CampaignListParser(ArrayList<CampaignListInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", status);
			if (status.equals("200")) {
				JSONObject job = jsonObject.getJSONObject("data");
				int total = job.optInt("count");
				mHashMap.put("total", total);
				JSONArray stadiumArray = job.getJSONArray("list");
				for (int i = 0; i < stadiumArray.length(); i++) {
					JSONObject itemobj = stadiumArray.getJSONObject(i);
					String activity_id = itemobj.optString("activity_id");
					String club_id = itemobj.optString("club_id");
					String club_name = itemobj.optString("club_name");
					double longitude = itemobj.optDouble("longitude");
					double latitude = itemobj.optDouble("latitude");
					String name = itemobj.optString("name", "");
					int start_time = itemobj.optInt("start_time");
					int end_time = itemobj.optInt("end_time");
					String gps = itemobj.optString("gps");
					String max_peo = itemobj.optString("max_peo");
					String situs = itemobj.optString("situs");
					String rule = itemobj.optString("rule");
					String logo = itemobj.optString("icon");
					String act_status = itemobj.optString("act_status");
					String status_txt = itemobj.optString("status_txt");
					String week = itemobj.optString("week");
					String day = itemobj.optString("day");
					String is_edit = itemobj.optString("is_edit");
					String is_sign = itemobj.optString("is_sign");
					CampaignListInfo mStadiumInfo = new CampaignListInfo(activity_id, name, start_time, end_time, gps,
							max_peo, situs, rule, logo, act_status, status_txt, week, day);
					mStadiumInfo.setIs_sign(is_sign);
					mStadiumInfo.setClub_id(club_id);
					mStadiumInfo.setClub_name(club_name);
					mStadiumInfo.setLatitude(latitude);
					mStadiumInfo.setLongitude(longitude);
					mStadiumInfo.setIs_edit(is_edit);
					mList.add(mStadiumInfo);
					mStadiumInfo = null;
				}

			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}
		mHashMap.put("mList", mList);

		return mHashMap;
	}

}
