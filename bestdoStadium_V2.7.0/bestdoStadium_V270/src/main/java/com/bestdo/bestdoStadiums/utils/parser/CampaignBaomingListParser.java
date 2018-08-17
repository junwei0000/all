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
public class CampaignBaomingListParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				ArrayList<CampaignDetailInfo> mList = new ArrayList<CampaignDetailInfo>();
				try {
					JSONObject Object = jsonObject.optJSONObject("data");
					JSONArray listArray = Object.optJSONArray("list");
					for (int i = 0; i < listArray.length(); i++) {
						JSONObject itemObject = listArray.optJSONObject(i);
						String avatar = itemObject.optString("avatar");
						String name = itemObject.optString("name");
						CampaignDetailInfo mCampaignGetClubInfo = new CampaignDetailInfo();
						mCampaignGetClubInfo.setFootavatar(avatar);
						mCampaignGetClubInfo.setFootname(name);
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
