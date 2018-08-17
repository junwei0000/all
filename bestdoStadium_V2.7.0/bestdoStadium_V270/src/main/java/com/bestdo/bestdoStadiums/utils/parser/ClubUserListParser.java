package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class ClubUserListParser extends BaseParser<Object> {

	ArrayList<CampaignDetailInfo> mList;

	public ClubUserListParser() {
		super();
	}

	public ClubUserListParser(ArrayList<CampaignDetailInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				JSONObject Object = jsonObject.optJSONObject("data");
				ArrayList<CampaignDetailInfo> master_list = new ArrayList<CampaignDetailInfo>();
				try {
					JSONArray masterlistArray = Object.optJSONArray("master_list");
					int member_count = Object.optInt("member_count");
					mHashMap.put("member_count", member_count);
					for (int i = 0; i < masterlistArray.length(); i++) {
						JSONObject itemObject = masterlistArray.optJSONObject(i);
						String avatar = itemObject.optString("avatar");
						String name = itemObject.optString("name", "");
						String rulestatusname = "管理员";
						String rulestatus = "hide";
						if (i == 0) {
							rulestatus = "show";
						} else {
							rulestatus = "hide";
						}

						CampaignDetailInfo mCampaignGetClubInfo = new CampaignDetailInfo();
						mCampaignGetClubInfo.setFootavatar(avatar);
						mCampaignGetClubInfo.setFootname(name);
						mCampaignGetClubInfo.setRulestatus(rulestatus);
						mCampaignGetClubInfo.setRulestatusname(rulestatusname);
						master_list.add(mCampaignGetClubInfo);
						mHashMap.put("master_list", master_list);
						mCampaignGetClubInfo = null;
					}
				} catch (Exception e) {
				}
				try {
					JSONArray masterlistArray = Object.optJSONArray("member_list");
					for (int i = 0; i < masterlistArray.length(); i++) {
						JSONObject itemObject = masterlistArray.optJSONObject(i);
						String avatar = itemObject.optString("avatar");
						String name = itemObject.optString("name", "");

						String rulestatusname = "已加入的成员";
						String rulestatus = "hide";
						if (i == 0) {
							rulestatus = "show";
						} else {
							rulestatus = "hide";
						}
						if (mList.size() > 0) {
							rulestatus = "hide";
						}
						CampaignDetailInfo mCampaignGetClubInfo = new CampaignDetailInfo();
						mCampaignGetClubInfo.setFootavatar(avatar);
						mCampaignGetClubInfo.setFootname(name);
						mCampaignGetClubInfo.setRulestatus(rulestatus);
						mCampaignGetClubInfo.setRulestatusname(rulestatusname);
						mList.add(mCampaignGetClubInfo);
						mCampaignGetClubInfo = null;
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
