package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.VenuesTreasInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class VenuesTreasParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONArray listarray = jsonObject.optJSONArray("data");
				ArrayList<VenuesTreasInfo> mlist = new ArrayList<VenuesTreasInfo>();
				for (int i = 0; i < listarray.length(); i++) {
					JSONObject listOb = listarray.optJSONObject(i);
					String reward_item_id = listOb.optString("reward_item_id",
							"");
					String reward_id = listOb.optString("reward_id", "");
					String uid = listOb.optString("uid", "");
					String nick_name = listOb.optString("nick_name", "");
					String album_url = listOb.optString("album_url", "");
					double longitude = listOb.optDouble("longitude", 0);
					double latitude = listOb.optDouble("latitude", 0);
					String name = listOb.optString("name", "");
					String money = listOb.optString("money", "");
					String is_receive = listOb.optString("is_receive", "");
					String receive_time = listOb.optString("receive_time", "");
					String thumb = listOb.optString("thumb", "");
					String type = listOb.optString("type", "");
					String is_exchange = listOb.optString("is_exchange", "");
					String sequence= listOb.optString("sequence", "");
					VenuesTreasInfo venuesInfo = new VenuesTreasInfo(
							reward_item_id, reward_id, uid, nick_name,
							album_url, longitude, latitude, name, money,
							is_receive, receive_time, thumb, type,sequence);
					venuesInfo.setIs_exchange(is_exchange);
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
