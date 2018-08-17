package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.ClubMenuInfo;
import com.bestdo.bestdoStadiums.model.ClubOperateInfo;
import com.bestdo.bestdoStadiums.model.CreatOrderGetDefautCardInfo;
import com.bestdo.bestdoStadiums.model.CreatOrderGetMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-27 下午5:28:45
 * @Description 类描述：
 */
public class LodeCampaignQuartParser extends BaseParser<Object> {

	ArrayList<ClubMenuInfo> clubMenuInfoList;
	ArrayList<MyJoinClubmenuInfo> myJoinClubmenuInfoList;
	ArrayList<MyJoinClubmenuInfo> myNoJoinClubmenuInfoList;

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				JSONObject oo = jsonObject.getJSONObject("data");
				JSONArray arr = oo.optJSONArray("clubMenu");
				String myClubNum = oo.optString("myClubNum", "");
				String notJoinNum = oo.optString("notJoinNum", "");

				mHashMap.put("myClubNum", myClubNum);
				mHashMap.put("notJoinNum", notJoinNum);

				if (arr != null && arr.length() > 0) {
					clubMenuInfoList = new ArrayList<ClubMenuInfo>();
					for (int i = 0; i < arr.length(); i++) {
						JSONObject jsonob = (JSONObject) arr.get(i);
						String title = jsonob.optString("title", "");
						String thumb = jsonob.optString("thumb", "");
						String club_name = jsonob.optString("club_name", "");
						ClubMenuInfo clubMenuInfo = new ClubMenuInfo(title, thumb, club_name);
						clubMenuInfoList.add(clubMenuInfo);
						clubMenuInfo = null;
					}
					mHashMap.put("clubMenuInfoList", clubMenuInfoList);
				}
				JSONArray myClubDataJsonarr = oo.optJSONArray("myClubData");
				if (myClubDataJsonarr != null && myClubDataJsonarr.length() > 0) {
					myJoinClubmenuInfoList = new ArrayList<MyJoinClubmenuInfo>();
					for (int i = 0; i < myClubDataJsonarr.length(); i++) {
						JSONObject jsonob = (JSONObject) myClubDataJsonarr.get(i);
						String club_id = jsonob.optString("club_id", "");
						String icon = jsonob.optString("icon", "");
						String id = jsonob.optString("id", "");
						String is_has_regular_activity = jsonob.optString("is_has_regular_activity", "");
						String club_name = jsonob.optString("club_name", "");
						String member_count = jsonob.optString("member_count", "");
						String activity_count = jsonob.optString("activity_count", "");
						String club_text = jsonob.optString("club_text", "");
						String club_banner = jsonob.optString("club_banner", "");
						String club_description = jsonob.optString("club_description", "");
						MyJoinClubmenuInfo myJoinClubmenuInfo = new MyJoinClubmenuInfo(club_id, icon, id,
								is_has_regular_activity, club_name, member_count, activity_count, club_text, "", "",
								club_description, club_banner);
						myJoinClubmenuInfoList.add(myJoinClubmenuInfo);
						myJoinClubmenuInfo = null;
					}
					mHashMap.put("myJoinClubmenuInfoList", myJoinClubmenuInfoList);
				}
				JSONArray myNotClubDataJsonarr = oo.optJSONArray("notJoinClub");
				if (myNotClubDataJsonarr != null && myNotClubDataJsonarr.length() > 0) {
					myNoJoinClubmenuInfoList = new ArrayList<MyJoinClubmenuInfo>();
					for (int i = 0; i < myNotClubDataJsonarr.length(); i++) {
						JSONObject jsonob = (JSONObject) myNotClubDataJsonarr.get(i);
						String club_id = jsonob.optString("club_id", "");
						String icon = jsonob.optString("icon", "");
						String id = jsonob.optString("id", "");
						String is_has_regular_activity = jsonob.optString("is_has_regular_activity", "");
						String club_name = jsonob.optString("club_name", "");
						String member_count = jsonob.optString("member_count", "");
						String activity_count = jsonob.optString("activity_count", "");
						String club_text = jsonob.optString("club_text", "");
						String member_text = jsonob.optString("member_text", "");
						String activity_text = jsonob.optString("activity_text", "");
						String club_banner = jsonob.optString("club_banner", "");
						String club_description = jsonob.optString("club_description", "");

						MyJoinClubmenuInfo myJoinClubmenuInfo = new MyJoinClubmenuInfo(club_id, icon, id,
								is_has_regular_activity, club_name, member_count, activity_count, club_text,
								member_text, activity_text, club_banner, club_description);
						myNoJoinClubmenuInfoList.add(myJoinClubmenuInfo);
						myJoinClubmenuInfo = null;
					}
					mHashMap.put("myNoJoinClubmenuInfoList", myNoJoinClubmenuInfoList);
				}
				// clubMenuInfoList = null;
				// myJoinClubmenuInfoList=null;
				// myNoJoinClubmenuInfoList=null;

				JSONObject operateDataObj = oo.optJSONObject("operateData");
				if (operateDataObj != null) {
					JSONArray showDataJsonArry = operateDataObj.optJSONArray("showData");
					ArrayList<ClubOperateInfo> arrayClubOperateInfo = new ArrayList<ClubOperateInfo>();

					if (showDataJsonArry != null && showDataJsonArry.length() > 0) {
						for (int i = 0; i < showDataJsonArry.length(); i++) {
							JSONObject o = showDataJsonArry.optJSONObject(i);
							String description = o.optString("description");
							String num = o.optString("num");
							String unit = o.optString("unit");
							ClubOperateInfo clubOperateInfo = new ClubOperateInfo(description, num, unit);
							arrayClubOperateInfo.add(clubOperateInfo);
						}
					}
					mHashMap.put("arrayClubOperateInfo", arrayClubOperateInfo);
					JSONObject otherDataJsonObj = operateDataObj.optJSONObject("otherData");
					String footerDescription = otherDataJsonObj.optString("footerDescription");
					String moreUrl = otherDataJsonObj.optString("moreUrl");
					mHashMap.put("footerDescription", footerDescription);
					mHashMap.put("moreUrl", moreUrl);
				}

			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
