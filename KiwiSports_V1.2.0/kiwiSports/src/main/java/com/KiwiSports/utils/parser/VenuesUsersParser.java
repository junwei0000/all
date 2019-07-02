package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.VenuesUsersInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class VenuesUsersParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONArray listarray = jsonObject.optJSONArray("data");
				ArrayList<VenuesUsersInfo> mlist = new ArrayList<VenuesUsersInfo>();
				for (int i = 0; i < listarray.length(); i++) {
					JSONObject listOb = listarray.optJSONObject(i);
					String uid = listOb.optString("uid", "");
					String real_name = listOb.optString("real_name", "");
					String nick_name = listOb.optString("nick_name", "");
					String album_url = listOb.optString("album_url", "");
					String is_anonymous = listOb.optString("is_anonymous", "");
					double longitude = listOb.optDouble("current_longitude", 0);
					double latitude = listOb.optDouble("current_latitude", 0);
					String current_sports_type = listOb.optString("current_sports_type", "");
					VenuesUsersInfo venuesInfo = new VenuesUsersInfo(uid,
							real_name, nick_name, album_url, is_anonymous,
							longitude, latitude,current_sports_type);
					mlist.add(venuesInfo);
					venuesInfo = null;
				}
				mHashMap.put("mlist", mlist);
			} else {
				String msg = jsonObject.optString("data");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
