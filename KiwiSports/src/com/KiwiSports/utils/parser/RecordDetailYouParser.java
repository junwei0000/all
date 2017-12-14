package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesListInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class RecordDetailYouParser extends BaseParser<Object> {
	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				ArrayList<RecordInfo> mlist=new ArrayList<RecordInfo>();
				JSONArray listarray = jsonObject.optJSONArray("data");
				for (int i = 0; i < listarray.length(); i++) {
					JSONObject listOb = listarray.optJSONObject(i);
					String record_id = listOb.optString("record_id", "");
					String uid = listOb.optString("uid", "");
					String posid = listOb.optString("posid", "");
					String date_time = listOb.optString("date_time", "");
					String distanceTraveled = listOb.optString("distanceTraveled", "");
					long duration = listOb.optLong("duration",0);
					String verticalDistance = listOb.optString("verticalDistance", "");
					
					
					String topSpeed = listOb.optString("topSpeed", "");
					String dropTraveled = listOb.optString("dropTraveled", "");
					String nSteps = listOb.optString("nSteps", "");
					String matchSpeed = listOb.optString("matchSpeed", "");
					String maxMatchSpeed = listOb.optString("maxMatchSpeed", "");
					String maxSlope = listOb.optString("maxSlope", "");
					String maxAltitude = listOb.optString("maxAltitude", "");
					String currentAltitude = listOb.optString("currentAltitude", "");

					String averageMatchSpeed = listOb.optString("averageMatchSpeed", "");
					String averageSpeed = listOb.optString("averageSpeed", "");
					long freezeDuration = listOb.optLong("freezeDuration", 0);
					String maxHoverDuration = listOb.optString("maxHoverDuration", "");
					String totalHoverDuration = listOb.optString("totalHoverDuration", "");
					String lapCount = listOb.optString("lapCount", "");
					String wrestlingCount = listOb.optString("wrestlingCount", "");
					String cableCarQueuingDuration = listOb.optString("cableCarQueuingDuration", "");
					String address = listOb.optString("address", "");
					String minAltidue = listOb.optString("minAltidue", "");
					String calorie = listOb.optString("calorie", "");
					String sportsType = listOb.optString("sportsType", "");
					String latitudeOffset = listOb.optString("latitudeOffset", "");
					String longitudeOffset = listOb.optString("longitudeOffset", "");
					String upHillDistance = listOb.optString("upHillDistance", "");
					String downHillDistance = listOb.optString("downHillDistance", "");
					String extendedField1 = listOb.optString("extendedField1", "");
					String extendedField2 = listOb.optString("extendedField2", "");
					String extendedField3 = listOb.optString("extendedField3", "");
					String create_time = listOb.optString("create_time", "");
					String cstatus = listOb.optString("status", "");
					String pos_name = listOb.optString("pos_name", "");
					RecordInfo mRecordInfo = new RecordInfo(record_id, uid, posid, date_time, distanceTraveled,
							duration, verticalDistance, topSpeed, dropTraveled, nSteps, matchSpeed, maxMatchSpeed,
							maxSlope, maxAltitude, currentAltitude, averageMatchSpeed, averageSpeed, freezeDuration,
							maxHoverDuration, totalHoverDuration, lapCount, wrestlingCount, cableCarQueuingDuration,
							address, minAltidue, calorie, sportsType, latitudeOffset, longitudeOffset, upHillDistance,
							downHillDistance, extendedField1, extendedField2, extendedField3, create_time, cstatus,pos_name);

					mlist.add(mRecordInfo);
					mRecordInfo = null;
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
