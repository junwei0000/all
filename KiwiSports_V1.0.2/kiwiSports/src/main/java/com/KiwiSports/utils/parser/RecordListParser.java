package com.KiwiSports.utils.parser;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.KiwiSports.model.DistanceCountInfo;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.utils.App;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.PriceUtils;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class RecordListParser extends BaseParser<Object> {
    ArrayList<RecordInfo> mlist;

    public RecordListParser(ArrayList<RecordInfo> mlist) {
        super();
        this.mlist = mlist;
    }

    @Override
    public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
        HashMap<String, Object> mHashMap = null;
        try {
            String status = jsonObject.getString("status");
            mHashMap = new HashMap<String, Object>();
            mHashMap.put("status", status);
            if (status.equals("200")) {
                JSONObject jsonOb = jsonObject.getJSONObject("data");
                int total_page = jsonOb.optInt("total_page", 0);
                mHashMap.put("total_page", total_page);
                int count = jsonOb.optInt("count", 0);
                mHashMap.put("count", count);

                String totalDistances = "0";
                String Distances3 = "0";
                ArrayList<DistanceCountInfo> mtotalDislist = new ArrayList<>();
                DistanceCountInfo mDistanceCountInfo = null;
                try {
                    JSONArray totalDistanceslistarray = jsonOb.optJSONArray("totalDistances");
                    mDistanceCountInfo = new DistanceCountInfo();
                    for (int h = 0; h < totalDistanceslistarray.length(); h++) {
                        JSONObject totalDislistOb = totalDistanceslistarray.optJSONObject(h);
                        String distance = totalDislistOb.optString("distance", "");
                        totalDistances = PriceUtils.getInstance().gteAddSumPrice(totalDistances, distance);
                    }
                    int shownum = totalDistanceslistarray.length();
                    if (3 < shownum) {
                        shownum = 3;
                    }
                    MainsportParser mMainsportParser = new MainsportParser();
                    HashMap<Integer, MainSportInfo> mallsportList = mMainsportParser.parseJSONHash(App.getInstance().getContext());
                    for (int h = 0; h < shownum; h++) {
                        JSONObject totalDislistOb = totalDistanceslistarray.optJSONObject(h);
                        int sportsType = totalDislistOb.optInt("sportsType", 0);
                        String distance = totalDislistOb.optString("distance", "");
                        String sportname = "";
                        if (mallsportList.containsKey(sportsType)) {
                            sportname = mallsportList.get(sportsType).getCname();
                        }else{
                            sportname="其他";
                        }
                        DistanceCountInfo mInfo = new DistanceCountInfo(sportsType, sportname, distance);
                        mtotalDislist.add(mInfo);
                        mInfo = null;
                        Distances3 = PriceUtils.getInstance().gteAddSumPrice(Distances3, distance);
                    }
                    if (3 < totalDistanceslistarray.length()) {
                        String distance = PriceUtils.getInstance().gteSubtractSumPrice(Distances3, totalDistances);
                        DistanceCountInfo mInfo = new DistanceCountInfo(mallsportList.size() - 1, "其他", distance);
                        mtotalDislist.add(mInfo);
                        mInfo = null;
                    }
                    totalDistances = PriceUtils.getInstance().gteDividePrice(totalDistances, "1000");
                    totalDistances = PriceUtils.getInstance().getPriceTwoDecimal(Double.valueOf(totalDistances),2);
                    totalDistances = PriceUtils.getInstance().seePrice(totalDistances);
                    mDistanceCountInfo.setTotalDistances(totalDistances);
                    mDistanceCountInfo.setMtotalDislist(mtotalDislist);
                } catch (Exception e) {
                }
                mHashMap.put("mDistanceCountInfo", mDistanceCountInfo);
                JSONArray listarray = jsonOb.optJSONArray("records");
                for (int i = 0; i < listarray.length(); i++) {
                    JSONObject listOb = listarray.optJSONObject(i);
                    String record_id = listOb.optString("record_id", "");
                    String uid = listOb.optString("uid", "");
                    String posid = listOb.optString("posid", "");
                    String date_time = listOb.optString("date_time", "");
                    double distanceTraveled = listOb.optDouble(
                            "distanceTraveled", 0);
                    long duration = listOb.optLong("duration", 0);
                    String verticalDistance = listOb.optString(
                            "verticalDistance", "");

                    String topSpeed = listOb.optString("topSpeed", "");
                    String dropTraveled = listOb.optString("dropTraveled", "");
                    String nSteps = listOb.optString("nSteps", "");
                    String matchSpeed = listOb.optString("matchSpeed", "");
                    String maxMatchSpeed = listOb
                            .optString("maxMatchSpeed", "");
                    String maxSlope = listOb.optString("maxSlope", "");
                    String maxAltitude = listOb.optString("maxAltitude", "");
                    String currentAltitude = listOb.optString(
                            "currentAltitude", "");

                    String averageMatchSpeed = listOb.optString(
                            "averageMatchSpeed", "");
                    String averageSpeed = listOb.optString("averageSpeed", "");
                    long freezeDuration = listOb.optLong("freezeDuration", 0);
                    String maxHoverDuration = listOb.optString(
                            "maxHoverDuration", "");
                    String totalHoverDuration = listOb.optString(
                            "totalHoverDuration", "");
                    String lapCount = listOb.optString("lapCount", "");
                    String wrestlingCount = listOb.optString("wrestlingCount",
                            "");
                    String cableCarQueuingDuration = listOb.optString(
                            "cableCarQueuingDuration", "");
                    String address = listOb.optString("address", "");
                    String minAltidue = listOb.optString("minAltidue", "");
                    String calorie = listOb.optString("calorie", "");
                    String sportsType = listOb.optString("sportsType", "");
                    String latitudeOffset = listOb.optString("latitudeOffset",
                            "");
                    String longitudeOffset = listOb.optString(
                            "longitudeOffset", "");
                    String upHillDistance = listOb.optString("upHillDistance",
                            "");
                    String downHillDistance = listOb.optString(
                            "downHillDistance", "");
                    String extendedField1 = listOb.optString("extendedField1",
                            "");
                    String extendedField2 = listOb.optString("extendedField2",
                            "");
                    String extendedField3 = listOb.optString("extendedField3",
                            "");
                    String runStartTime = listOb.optString(
                            "runStartTime", "");
                    String minMatchSpeed = listOb.optString(
                            "minMatchSpeed", "");
                    String create_time = listOb.optString("create_time", "");
                    String cstatus = listOb.optString("status", "");
                    String pos_name = listOb.optString("pos_name", "");
                    if(TextUtils.isEmpty(runStartTime)){
                        runStartTime=create_time;
                    }
                    long startTrackTimeTamp= DatesUtils.getInstance().getDateToTimeStamp(runStartTime,"yyyy-MM-dd HH:mm:ss");
                    RecordInfo mRecordInfo = new RecordInfo(record_id, uid,
                            posid, date_time, distanceTraveled, duration,
                            verticalDistance, topSpeed, dropTraveled, nSteps,
                            matchSpeed, maxMatchSpeed, maxSlope, maxAltitude,
                            currentAltitude, averageMatchSpeed, averageSpeed,
                            freezeDuration, maxHoverDuration,
                            totalHoverDuration, lapCount, wrestlingCount,
                            cableCarQueuingDuration, address, minAltidue,
                            calorie, sportsType, latitudeOffset,
                            longitudeOffset, upHillDistance, downHillDistance,
                            extendedField1, extendedField2, extendedField3,
                            create_time, cstatus, pos_name,runStartTime,
                            String.valueOf(startTrackTimeTamp),minMatchSpeed, RecordListDBOpenHelper.currentTrackBOVER);

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
