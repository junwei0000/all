package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.jpush.android.data.l;

import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.ClubEventsInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class ClubEventsParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				ArrayList<CampaignListInfo> mList = new ArrayList<CampaignListInfo>();
				try {
					JSONObject Object = jsonObject.optJSONObject("data");
					JSONArray listArray = Object.optJSONArray("list");
					for (int i = 0; i < listArray.length(); i++) {
						JSONObject itemObject = listArray.optJSONObject(i);
						int start_time = itemObject.optInt("start_time");
						int end_time = itemObject.optInt("end_time");

						String name = itemObject.optString("name", "");
						String act_status = itemObject.optString("act_status", "");
						String week = itemObject.optString("week", "");
						String day = itemObject.optString("day", "");
						String status_txt = itemObject.optString("status_text", "");
						String activity_id = itemObject.optString("activity_id", "");
						String icon = itemObject.optString("icon");
						String situs = itemObject.optString("situs");
						CampaignListInfo mStadiumInfo = new CampaignListInfo();
						mStadiumInfo.setStart_time(start_time);
						mStadiumInfo.setEnd_time(end_time);
						mStadiumInfo.setName(name);
						mStadiumInfo.setAct_status(act_status);
						mStadiumInfo.setWeek(week);
						mStadiumInfo.setDay(day);
						mStadiumInfo.setActivity_id(activity_id);
						mStadiumInfo.setStatus_txt(status_txt);
						mStadiumInfo.setSitus(situs);
						mStadiumInfo.setLogo(icon);
						// ClubEventsInfo mCampaignGetClubInfo = new
						// ClubEventsInfo(
						// start_time, name, act_status, week, day,
						// status_text, activity_id);
						mList.add(mStadiumInfo);
						mStadiumInfo = null;
					}
				} catch (Exception e) {
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
