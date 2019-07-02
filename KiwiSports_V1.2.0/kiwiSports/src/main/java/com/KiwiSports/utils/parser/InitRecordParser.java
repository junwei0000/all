package com.KiwiSports.utils.parser;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class InitRecordParser extends BaseParser<Object> {
    @Override
    public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
        HashMap<String, Object> mHashMap = null;
        try {
            mHashMap = new HashMap<String, Object>();
            String status = jsonObject.getString("status");
            mHashMap.put("status", status);
            if (status.equals("200")) {
                JSONObject ob = jsonObject.optJSONObject("data");
                String record_data_id = ob.optString("record_data_id");
                mHashMap.put("record_data_id", record_data_id);
            } else {
                String msg = jsonObject.getString("data");
                mHashMap.put("msg", msg);
            }
        } catch (Exception e) {
        }

        return mHashMap;
    }

}
