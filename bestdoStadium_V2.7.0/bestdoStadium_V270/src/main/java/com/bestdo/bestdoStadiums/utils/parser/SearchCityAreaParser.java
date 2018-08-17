package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.StadiumSelectJuliInfo;

/**
 * @author qyy
 * 
 */
public class SearchCityAreaParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				JSONArray Ob = jsonObject.getJSONArray("data");
				ArrayList<StadiumSelectJuliInfo> mCityAreaList = new ArrayList<StadiumSelectJuliInfo>();
				mCityAreaList.add(new StadiumSelectJuliInfo("全部", "0", "", true));
				for (int i = 0; i < Ob.length(); i++) {
					JSONObject hotCityOb = Ob.getJSONObject(i);
					String region_id = hotCityOb.optString("region_id");
					String name = hotCityOb.optString("name");
					String level = hotCityOb.optString("level");
					StadiumSelectJuliInfo mInfo = new StadiumSelectJuliInfo(name, region_id, level, false);
					mCityAreaList.add(mInfo);
					mInfo = null;
				}
				mHashMap.put("mCityAreaList", mCityAreaList);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
