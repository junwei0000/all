package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.CampaignRegularListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午4:27:48
 * @Description 类描述：
 */
public class CampaignRegularListParser extends BaseParser<Object> {
	ArrayList<CampaignRegularListInfo> mList;

	public CampaignRegularListParser() {
		super();
	}

	public CampaignRegularListParser(ArrayList<CampaignRegularListInfo> mList) {
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
					double longitude = itemobj.optDouble("longitude");
					double latitude = itemobj.optDouble("latitude");
					String id = itemobj.optString("id");
					String name = itemobj.optString("name");
					int start_time = itemobj.optInt("start_time");
					int end_time = itemobj.optInt("end_time");
					String max_peo = itemobj.optString("max_peo");
					String address = itemobj.optString("address");
					String rule = itemobj.optString("rule");
					String advance_time = itemobj.optString("advance_time");
					String campaginstatus = itemobj.optString("status");
					String auto_txt = itemobj.optString("auto_txt");
					String act_time = itemobj.optString("act_time");
					CampaignRegularListInfo mStadiumInfo = new CampaignRegularListInfo(id, name, start_time, end_time,
							max_peo, address, rule, advance_time, campaginstatus, auto_txt, act_time, longitude,
							latitude);
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
