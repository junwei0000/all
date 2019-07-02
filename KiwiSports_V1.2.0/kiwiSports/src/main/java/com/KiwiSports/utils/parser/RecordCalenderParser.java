package com.KiwiSports.utils.parser;

import com.KiwiSports.control.calendar.DateEntity;
import com.KiwiSports.model.DistanceCountInfo;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.utils.App;
import com.KiwiSports.utils.PriceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class RecordCalenderParser extends BaseParser<Object> {
    HashMap<String, DateEntity> msportmap;

    public RecordCalenderParser(HashMap<String, DateEntity> msportmap) {
        super();
        this.msportmap = msportmap;
    }

    @Override
    public HashMap<String, DateEntity> parseJSON(JSONObject jsonObject) {
        try {
            String status = jsonObject.getString("status");
            if (status.equals("200")) {
                JSONArray listarray = jsonObject.optJSONArray("data");
                for (int i = 0; i < listarray.length(); i++) {
                    JSONObject listOb = listarray.optJSONObject(i);
                    String date_time = listOb.optString("date_time", "");
                    String sportsType = listOb.optString("sportsType", "");
                    String uid = listOb.optString("uid", "");
                    long runtime = listOb.optLong("duration", 0);
                    String record_id = listOb.optString("record_id", "");
                    String posid = listOb.optString("posid", "");

                    DateEntity mDateEntity = new DateEntity();
                    mDateEntity.sportIndex = sportsType;
                    mDateEntity.record_id = record_id;
                    mDateEntity.posid = posid;
                    mDateEntity.runtime = runtime;
                    msportmap.put(date_time, mDateEntity);
                }
            }
        } catch (Exception e) {
        }

        return msportmap;
    }

}
