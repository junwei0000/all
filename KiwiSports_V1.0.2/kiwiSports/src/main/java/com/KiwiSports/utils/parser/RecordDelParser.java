package com.KiwiSports.utils.parser;

import com.KiwiSports.model.MacthSpeedInfo;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.RecordInfo;
import com.amap.api.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class RecordDelParser extends BaseParser<Object> {
    @Override
    public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
        HashMap<String, Object> mHashMap = null;
        try {
            String status = jsonObject.getString("status");
            String msg = jsonObject.getString("data");
            mHashMap = new HashMap<String, Object>();
            mHashMap.put("status", status);
            mHashMap.put("msg", msg);
        } catch (Exception e) {
        }

        return mHashMap;
    }

}
