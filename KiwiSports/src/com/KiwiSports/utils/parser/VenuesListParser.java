package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.VenuesListInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class VenuesListParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject jsonOb = jsonObject.getJSONObject("data");
				int count = jsonOb.optInt("count", 0);
				mHashMap.put("count", count);
				JSONArray listarray = jsonOb.optJSONArray("data");
				ArrayList<VenuesListInfo> mlist = new ArrayList<VenuesListInfo>();
				for (int i = 0; i < listarray.length(); i++) {
					JSONObject listOb = listarray.optJSONObject(i);
					String posid = listOb.optString("posid", "");
					String uid = listOb.optString("uid", "");
					String field_name = listOb.optString("field_name", "");
					String sportsType = listOb.optString("sportsType", "");
					String thumb = listOb.optString("thumb", "");
					String venuestatus = listOb.optString("status", "");
					String audit_status = listOb.optString("audit_status", "");

					double top_left_x = listOb.optDouble("top_left_x", 0);
					double top_left_y = listOb.optDouble("top_left_y", 0);
					double bottom_right_x = listOb.optDouble("bottom_right_x", 0);
					double bottom_right_y = listOb.optDouble("bottom_right_y", 0);
					VenuesListInfo venuesInfo = new VenuesListInfo(posid, uid, field_name, sportsType, thumb,
							venuestatus, audit_status, top_left_x, top_left_y, bottom_right_x, bottom_right_y);
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
