package com.KiwiSports.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author qyy
 */
public class GetVersonParser extends BaseParser<Object> {

    @Override
    public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
        HashMap<String, Object> mHashMap = null;
        try {
            String status = jsonObject.getString("status");
            mHashMap = new HashMap<String, Object>();
            mHashMap.put("status", status);
            if (status.equals("200")) {
                try {
                    JSONObject oo = jsonObject.optJSONObject("data");
                    String url = oo.optString("url", "");
                    String description = oo.optString("description", "");
                    String version = oo.optString("version", "");
                    String level = oo.optString("level", "");
                    mHashMap.put("url", url);
                    mHashMap.put("description", description);
                    mHashMap.put("version", version);
                    mHashMap.put("level", level);
                } catch (Exception e) {
                }

            } else {
                String msg = jsonObject.getString("data");
                mHashMap.put("msg", msg);
            }

        } catch (Exception e) {
        }
        return mHashMap;
    }

}
