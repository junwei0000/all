package com.KiwiSports.utils.parser;

import com.KiwiSports.model.VenuesListInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class VenuesItemUserParser extends BaseParser<Object> {

    @Override
    public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
        HashMap<String, Object> mHashMap = null;
        try {
            String status = jsonObject.getString("status");
            mHashMap = new HashMap<String, Object>();
            mHashMap.put("status", status);
            if (status.equals("200")) {
                JSONArray listarray = jsonObject.optJSONArray("data");
                ArrayList<VenuesListInfo> mlist = new ArrayList<VenuesListInfo>();
                HashMap<String, ArrayList<String>> mMap = new HashMap<>();
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
                    double bottom_right_x = listOb.optDouble("bottom_right_x",
                            0);
                    double bottom_right_y = listOb.optDouble("bottom_right_y",
                            0);
                    ArrayList<String> mUserList = new ArrayList<>();
                    try {
                        JSONArray listusersarray = listOb.optJSONArray("users");
                        int sun=listusersarray.length();
                        if(listusersarray.length()>21){
                            sun=21;
                        }
                        for (int j = 0; j < sun; j++) {
                            JSONObject listusersOb = listusersarray.optJSONObject(j);
                            String pouid = listusersOb.optString("uid", "");
                            String nick_name = listusersOb.optString("nick_name", "");
                            String album_url = listusersOb.optString("album_url", "");
                            mUserList.add(album_url);
                        }
                    } catch (Exception e) {
                    }
                    if (mUserList != null && mUserList.size() > 0) {
                        mMap.put(posid, mUserList);
                    }
//                    else if (posid.equals("938")) {
//                        for (int f = 0; f < 20; f++) {
//                            mUserList.add("http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKXOhEO7Z5bqaRgOyR7VRT8qAnh6pOxPKZDXqmqYA2xKZIwRjOCnRU63N5YfANSEfiaQIRuW4nCIiag/132");
//                        }
//                        mMap.put(posid, mUserList);
//                    }
                    VenuesListInfo venuesInfo = new VenuesListInfo(posid, uid,
                            field_name, sportsType, thumb, venuestatus,
                            audit_status, top_left_x, top_left_y,
                            bottom_right_x, bottom_right_y);
                    mlist.add(venuesInfo);
                    venuesInfo = null;
                }
                mHashMap.put("mMap", mMap);
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
