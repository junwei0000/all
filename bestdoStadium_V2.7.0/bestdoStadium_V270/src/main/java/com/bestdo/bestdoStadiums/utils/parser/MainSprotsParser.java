package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.SportTypeInfo;

/**
 * @author qyy
 * 
 */
public class MainSprotsParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				ArrayList<SportTypeInfo> mList = new ArrayList<SportTypeInfo>();
				JSONArray jsonOb = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonOb.length(); i++) {
					JSONObject jsonObj = jsonOb.getJSONObject(i);
					String cid = jsonObj.optString("cid");
					String name = jsonObj.optString("name");
					String merid = jsonObj.optString("merid");
					String alias = jsonObj.optString("alias");
					String sport = jsonObj.optString("sport");// 运动类型名称
					String imgurl = jsonObj.optString("imgurl");
					String defult_price_id = jsonObj.optString("default_price_id");
					String banlance_price_id = jsonObj.optString("vip_price_id");
					String isNew = jsonObj.optString("isNew");// 1表示新增运动类型

					if (!TextUtils.isEmpty(sport) && sport.equals("高尔夫练习场")) {
						sport = "练习场";
					}
					SportTypeInfo mInfo = new SportTypeInfo(cid, name, merid, alias, sport, imgurl, defult_price_id,
							banlance_price_id, isNew);
					mList.add(mInfo);
					mInfo = null;
				}

				mHashMap.put("mList", mList);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
