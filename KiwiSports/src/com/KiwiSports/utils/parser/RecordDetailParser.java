package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesListInfo;
import com.baidu.mapapi.model.LatLng;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class RecordDetailParser extends BaseParser<Object> {
	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject ob = jsonObject.optJSONObject("data");
				JSONObject listOb = ob.optJSONObject("record");
				String record_id = listOb.optString("record_id", "");
				String uid = listOb.optString("uid", "");
				String posid = listOb.optString("posid", "");
				String date_time = listOb.optString("date_time", "");
				String distanceTraveled = listOb.optString("distanceTraveled", "");
				long duration = listOb.optLong("duration", 0);
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
				RecordInfo mRecordInfo = new RecordInfo(record_id, uid, posid, date_time, distanceTraveled, duration,
						verticalDistance, topSpeed, dropTraveled, nSteps, matchSpeed, maxMatchSpeed, maxSlope,
						maxAltitude, currentAltitude, averageMatchSpeed, averageSpeed, freezeDuration, maxHoverDuration,
						totalHoverDuration, lapCount, wrestlingCount, cableCarQueuingDuration, address, minAltidue,
						calorie, sportsType, latitudeOffset, longitudeOffset, upHillDistance, downHillDistance,
						extendedField1, extendedField2, extendedField3, create_time, cstatus, pos_name);

				mHashMap.put("mRecordInfo", mRecordInfo);

				try {
					ArrayList<MainLocationItemInfo> allpointList = new ArrayList<MainLocationItemInfo>();
					ArrayList<LatLng> allpointLngList = new ArrayList<LatLng>();
					JSONObject record_infoOb = ob.optJSONObject("record_info");
					JSONArray infoArray = record_infoOb.optJSONArray("info");
					for (int i = 0; i < infoArray.length(); i++) {
						JSONObject infoOb = infoArray.optJSONObject(i);
						double latitude = infoOb.optDouble("latitude", 0);
						double longitude = infoOb.optDouble("longitude", 0);
						double speed = infoOb.optDouble("speed", 0);
						double altitude = infoOb.optDouble("altitude", 0);
						double accuracy = infoOb.optDouble("accuracy", 0);
						String nStatus = infoOb.optString("nStatus", "");
						String nLapPoint = infoOb.optString("nLapPoint", "");
						String nLapTime = infoOb.optString("nLapTime", "");
						long durations = infoOb.optLong("duration", 0);
						double distance = infoOb.optDouble("distance", 0);
						String latitudeOffsets = infoOb.optString("latitudeOffset", "");
						String longitudeOffsets = infoOb.optString("longitudeOffset", "");
						MainLocationItemInfo mMainLocationItemInfo = new MainLocationItemInfo(latitude, longitude,
								speed, altitude, accuracy, nStatus, nLapPoint, nLapTime, durations, distance,
								latitudeOffsets, longitudeOffsets);
						allpointList.add(mMainLocationItemInfo);
						allpointLngList.add(new LatLng(latitude, longitude));
						mMainLocationItemInfo = null;
					}
					mHashMap.put("allpointList", allpointList);
					mHashMap.put("allpointLngMapList", allpointLngList);
				} catch (Exception e) {
				}

			} else {
				String msg = jsonObject.optString("data");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
