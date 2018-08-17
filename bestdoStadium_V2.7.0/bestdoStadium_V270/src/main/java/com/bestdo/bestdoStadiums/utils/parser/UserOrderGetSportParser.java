package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-26 上午10:28:07
 * @Description 类描述：运动类型
 */
public class UserOrderGetSportParser {

	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		ArrayList<SportTypeInfo> mList = new ArrayList<SportTypeInfo>();
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			}
			if (status.equals("200")) {
				// "merid":"1020320",
				// "name":"客户端v2.2测试户外",
				// "cid":"122",
				// "alias":"mix",
				// "sport":"户外",
				// "imgurl":"http://test.bd.app.bestdo.com/2.2.0/static/images/outdoors.png",
				// "vip_price_id":"1356",
				// "default_price_id":1356,
				// "check":false

				// thumbnails
				JSONArray jsonOb = jsonObject.getJSONArray("data");
				for (int i = -1; i < jsonOb.length(); i++) {
					String cid = "";
					String name = "";
					String merid = "";
					boolean check = true;
					String thumbnails = "";
					if (i == -1) {
						cid = "";
						name = "全部";
						merid = "";
						check = true;
						thumbnails = "";
					} else {
						JSONObject jsonObj = jsonOb.optJSONObject(i);
						cid = jsonObj.optString("cid", "");
						name = jsonObj.optString("name", "");
						merid = jsonObj.optString("merid", "");
						check = jsonObj.optBoolean("check", false);
						thumbnails = jsonObj.optString("thumbnails");
					}
					SportTypeInfo mInfo = new SportTypeInfo(cid, name, merid, thumbnails, false);

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
