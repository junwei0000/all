package com.KiwiSports.utils.parser;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.MacthSpeedInfo;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.utils.DatesUtils;
import com.amap.api.maps.model.LatLng;

/**
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
                double distanceTraveled = listOb.optDouble("distanceTraveled",
                        0);
                long duration = listOb.optLong("duration", 0);
                String verticalDistance = listOb.optString("verticalDistance",
                        "");

                double topSpeed = listOb.optDouble("topSpeed", 0.0);
                topSpeed = 0.0;
                String dropTraveled = listOb.optString("dropTraveled", "");
                String nSteps = listOb.optString("nSteps", "");
                String matchSpeed = listOb.optString("matchSpeed", "");
                String maxMatchSpeed = listOb.optString("maxMatchSpeed", "");
                String maxSlope = listOb.optString("maxSlope", "");
                String maxAltitude = listOb.optString("maxAltitude", "");
                String currentAltitude = listOb
                        .optString("currentAltitude", "");
                String runStartTime = listOb.optString(
                        "runStartTime", "");
                String minMatchSpeed = listOb.optString(
                        "minMatchSpeed", "");
                String averageMatchSpeed = listOb.optString(
                        "averageMatchSpeed", "");
                String averageSpeed = listOb.optString("averageSpeed", "");
                long freezeDuration = listOb.optLong("freezeDuration", 0);
                String maxHoverDuration = listOb.optString("maxHoverDuration",
                        "");
                String totalHoverDuration = listOb.optString(
                        "totalHoverDuration", "");
                String lapCount = listOb.optString("lapCount", "");
                String wrestlingCount = listOb.optString("wrestlingCount", "");
                String cableCarQueuingDuration = listOb.optString(
                        "cableCarQueuingDuration", "");
                String address = listOb.optString("address", "");
                String minAltidue = listOb.optString("minAltidue", "");
                String calorie = listOb.optString("calorie", "");
                String sportsType = listOb.optString("sportsType", "");
                String latitudeOffset = listOb.optString("latitudeOffset", "");
                String longitudeOffset = listOb
                        .optString("longitudeOffset", "");
                String upHillDistance = listOb.optString("upHillDistance", "");
                String downHillDistance = listOb.optString("downHillDistance",
                        "");
                String extendedField1 = listOb.optString("extendedField1", "");
                String extendedField2 = listOb.optString("extendedField2", "");
                String extendedField3 = listOb.optString("extendedField3", "");
                String create_time = listOb.optString("create_time", "");
                String cstatus = listOb.optString("status", "");
                String pos_name = listOb.optString("pos_name", "");
                if (TextUtils.isEmpty(runStartTime)) {
                    runStartTime = create_time;
                }
                long startTrackTimeTamp = DatesUtils.getInstance().getDateToTimeStamp(runStartTime, "yyyy-MM-dd HH:mm:ss");

                int beforedistance = 0;
                long beforeDuration = 0;
                long maxmatchSpeedTimestamp = 0;
                try {
                    ArrayList<MacthSpeedInfo> allmatchSpeedList = new ArrayList<MacthSpeedInfo>();
                    ArrayList<MainLocationItemInfo> allpointList = new ArrayList<MainLocationItemInfo>();
                    JSONObject record_infoOb = ob.optJSONObject("record_info");
                    JSONArray infoArray = record_infoOb.optJSONArray("info");
                    for (int i = 0; i < infoArray.length(); i++) {
                        JSONObject infoOb = infoArray.optJSONObject(i);
                        double latitude = infoOb.optDouble("latitude", 0);
                        double longitude = infoOb.optDouble("longitude", 0);
                        double speed = infoOb.optDouble("speed", 0);
                        if(speed>topSpeed){
                            topSpeed=speed;
                        }
                        int altitude = infoOb.optInt("altitude", 0);
                        int accuracy = infoOb.optInt("accuracy", 0);
                        int nStatus = infoOb.optInt("nStatus", 0);
                        int nLapPoint = infoOb.optInt("nLapPoint", 1);
                        String nLapTime = infoOb.optString("nLapTime", "");
                        long durations = infoOb.optLong("duration", 0);
                        double distance = infoOb.optDouble("distance", 0);
                        long matchSpeedTimestamp = 0;
                        if (distance - beforedistance > 1000) {
                            matchSpeedTimestamp = durations - beforeDuration;
                            if (maxmatchSpeedTimestamp < matchSpeedTimestamp) {
                                maxmatchSpeedTimestamp = matchSpeedTimestamp;
                            }
                            beforeDuration = durations;
                            beforedistance = (int) distance;
                            MacthSpeedInfo minfo = new MacthSpeedInfo((int) distance / 1000, matchSpeedTimestamp);
                            allmatchSpeedList.add(minfo);
                            minfo = null;
                        }

                        if (i == infoArray.length() - 1) {
                            if (beforedistance != (int) distance && distance > 0) {
                                matchSpeedTimestamp = durations - beforeDuration;
                                if (maxmatchSpeedTimestamp < matchSpeedTimestamp) {
                                    maxmatchSpeedTimestamp = matchSpeedTimestamp;
                                }
                                MacthSpeedInfo minfo = new MacthSpeedInfo((int) ((distance / 1000 + 1)), matchSpeedTimestamp);
                                allmatchSpeedList.add(minfo);
                                minfo = null;
                            }
                        }
                        String latitudeOffsets = infoOb.optString(
                                "latitudeOffset", "");
                        String longitudeOffsets = infoOb.optString(
                                "longitudeOffset", "");
                        LatLng mLatLng = new LatLng(latitude, longitude);
                        MainLocationItemInfo mMainLocationItemInfo = new MainLocationItemInfo(
                                latitude, longitude, speed, altitude, accuracy,
                                nStatus, nLapPoint, nLapTime, "", durations,
                                distance, latitudeOffsets, longitudeOffsets, mLatLng);
                        allpointList.add(mMainLocationItemInfo);
                        mMainLocationItemInfo = null;
                    }
                    mHashMap.put("maxmatchSpeedTimestamp", maxmatchSpeedTimestamp);
                    mHashMap.put("allmatchSpeedList", allmatchSpeedList);
                    mHashMap.put("allpointList", allpointList);
                } catch (Exception e) {
                }
                RecordInfo mRecordInfo = new RecordInfo(record_id, uid, posid,
                        date_time, distanceTraveled, duration,
                        verticalDistance, String.valueOf(topSpeed), dropTraveled, nSteps,
                        matchSpeed, maxMatchSpeed, maxSlope, maxAltitude,
                        currentAltitude, averageMatchSpeed, averageSpeed,
                        freezeDuration, maxHoverDuration, totalHoverDuration,
                        lapCount, wrestlingCount, cableCarQueuingDuration,
                        address, minAltidue, calorie, sportsType,
                        latitudeOffset, longitudeOffset, upHillDistance,
                        downHillDistance, extendedField1, extendedField2,
                        extendedField3, create_time, cstatus, pos_name,
                        runStartTime, String.valueOf(startTrackTimeTamp), minMatchSpeed, RecordListDBOpenHelper.currentTrackBOVER);

                mHashMap.put("mRecordInfo", mRecordInfo);
            } else {
                String msg = jsonObject.optString("data");
                mHashMap.put("msg", msg);
            }
        } catch (Exception e) {
        }

        return mHashMap;
    }

}
