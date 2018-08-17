package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class ClubGetManageParser extends BaseParser<Object> {

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

				JSONArray dataarray = jsonObject.optJSONArray("data");
				ArrayList<MyJoinClubmenuInfo> arrayList = new ArrayList<MyJoinClubmenuInfo>();
				for (int i = 0; i < dataarray.length(); i++) {
					JSONObject otherobject = dataarray.optJSONObject(i);
					String club_id = otherobject.optString("club_id");
					String club_name = otherobject.optString("club_name");
					MyJoinClubmenuInfo mMyJoinClubmenuInfo = new MyJoinClubmenuInfo();
					mMyJoinClubmenuInfo.setClub_id(club_id);
					mMyJoinClubmenuInfo.setClub_name(club_name);
					arrayList.add(mMyJoinClubmenuInfo);
					mMyJoinClubmenuInfo = null;
				}

				mHashMap.put("clubList", arrayList);
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}

		} catch (Exception e) {
		}
		return mHashMap;
	}

}
