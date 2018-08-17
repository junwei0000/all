package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class CampaignDetailParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.optString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);

				JSONObject object = jsonObject.optJSONObject("data");

				JSONObject headobject = object.optJSONObject("head");
				String activity_id = headobject.optString("activity_id");
				String name = headobject.optString("name");
				String logo = headobject.optString("logo");
				String time_str = headobject.optString("time_str");

				JSONObject contentobject = object.optJSONObject("content");
				String situs = contentobject.optString("situs");
				String rule = contentobject.optString("rule");
				String contenttime_str = contentobject.optString("time_str");
				String max_peo = contentobject.optString("max_peo");
				double longitude = contentobject.optDouble("longitude");
				double latitude = contentobject.optDouble("latitude");

				JSONObject otherobject = object.optJSONObject("other");
				String is_join = otherobject.optString("is_join");
				String is_edit = otherobject.optString("is_edit");
				String join_num = otherobject.optString("join_num");
				String join_rate = otherobject.optString("join_rate");
				String share_url = otherobject.optString("share_url");
				CampaignDetailInfo mCampaignDetailInfo = new CampaignDetailInfo(activity_id, name, logo, time_str,
						situs, rule, contenttime_str, max_peo, is_join, latitude, longitude);
				mCampaignDetailInfo.setIs_edit(is_edit);
				mCampaignDetailInfo.setJoin_num(join_num);
				mCampaignDetailInfo.setJoin_rate(join_rate);
				mCampaignDetailInfo.setShare_url(share_url);
				mHashMap.put("mCampaignDetailInfo", mCampaignDetailInfo);

				JSONObject authorobject = object.optJSONObject("author");
				String user_name = authorobject.optString("user_name");
				String user_mobile = authorobject.optString("user_mobile");
				String user_role = authorobject.optString("user_role");
				String club_icon = authorobject.optString("club_icon");
				String club_name = authorobject.optString("club_name");
				CampaignDetailInfo mauthorInfo = new CampaignDetailInfo();
				mauthorInfo.setUser_name(user_name);
				mauthorInfo.setUser_mobile(user_mobile);
				mauthorInfo.setUser_role(user_role);
				mauthorInfo.setClub_icon(club_icon);
				mauthorInfo.setClub_name(club_name);
				mHashMap.put("mauthorInfo", mauthorInfo);

				ArrayList<CampaignDetailInfo> mbaomingrenList = new ArrayList<CampaignDetailInfo>();
				JSONArray footaray = object.optJSONArray("foot");
				for (int i = 0; i < footaray.length(); i++) {
					JSONObject footobject = footaray.optJSONObject(i);
					String footname = footobject.optString("name");
					String all_nums = footobject.optString("all_nums");
					String footavatar = footobject.optString("avatar");
					CampaignDetailInfo mInfo = new CampaignDetailInfo();
					mInfo.setFootavatar(footavatar);
					mInfo.setFootname(footname);
					mInfo.setAll_nums(all_nums);
					mbaomingrenList.add(mInfo);
					mInfo = null;
				}
				mHashMap.put("mbaomingrenList", mbaomingrenList);
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}

		} catch (Exception e) {
		}
		return mHashMap;
	}

}
