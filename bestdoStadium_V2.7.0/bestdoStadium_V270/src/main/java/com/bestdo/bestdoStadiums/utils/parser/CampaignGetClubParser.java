package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class CampaignGetClubParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				ArrayList<CampaignGetClubInfo> mClubList = new ArrayList<CampaignGetClubInfo>();
				try {
					JSONArray clubArray = jsonObject.optJSONArray("data");
					for (int i = 0; i < clubArray.length(); i++) {
						JSONObject itemObject = clubArray.optJSONObject(i);
						String club_id = itemObject.optString("club_id");
						String club_name = itemObject.optString("club_name");
						String club_description = itemObject.optString("club_description");
						CampaignGetClubInfo mCampaignGetClubInfo = new CampaignGetClubInfo(club_id, club_name,
								club_description);
						mClubList.add(mCampaignGetClubInfo);
						mCampaignGetClubInfo = null;
					}
				} catch (Exception e) {
				}
				mHashMap.put("mClubList", mClubList);
			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}

		} catch (Exception e) {
		}
		return mHashMap;
	}

}
