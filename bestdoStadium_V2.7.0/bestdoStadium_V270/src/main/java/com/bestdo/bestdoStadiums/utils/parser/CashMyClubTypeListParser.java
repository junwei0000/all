package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.jpush.android.data.l;

import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.CashMyClubInfo;
import com.bestdo.bestdoStadiums.model.CashMyClubTypeInfo;
import com.bestdo.bestdoStadiums.model.ClubEventsInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class CashMyClubTypeListParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				ArrayList<CashMyClubTypeInfo> mList = new ArrayList<CashMyClubTypeInfo>();
				try {
					JSONArray listArray = jsonObject.optJSONArray("data");
					for (int i = 0; i < listArray.length(); i++) {
						JSONObject itemObject = listArray.optJSONObject(i);
						String id = itemObject.optString("id", "");
						String name = itemObject.optString("name", "");
						String img = itemObject.optString("img", "");
						String select_img = itemObject.optString("select_img", "");
						int type = itemObject.optInt("type", 1);
						int addtime = itemObject.optInt("addtime", 0);
						int update_time = itemObject.optInt("update_time", 0);
						CashMyClubTypeInfo mStadiumInfo = new CashMyClubTypeInfo(id, name, img, select_img, type,
								addtime, update_time);
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
