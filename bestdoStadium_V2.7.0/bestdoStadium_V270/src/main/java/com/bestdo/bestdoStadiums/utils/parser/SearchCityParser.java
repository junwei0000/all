package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.bestdo.bestdoStadiums.model.SearchCityInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

/**
 * @author qyy
 * 
 */
public class SearchCityParser extends BaseParser<Object> {

	public HashMap<String, Object> parseJSON(Context context, String fileName) {
		JSONObject jsonObject = null;
		HashMap<String, Object> mMap = null;
		try {
			String jsondate = CommonUtils.getInstance().getFromAssets(context, fileName);
			jsonObject = new JSONObject(jsondate);
			mMap = parseJSON(jsonObject);
			jsonObject = null;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mMap;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				JSONObject Ob = jsonObject.getJSONObject("data");
				ArrayList<SearchCityInfo> mhotCityList = new ArrayList<SearchCityInfo>();
				ArrayList<SearchCityInfo> mordinaryCityList = new ArrayList<SearchCityInfo>();
				HashMap<String, String> mAllMap = new HashMap<String, String>();
				/**
				 * 热门城市
				 */
				JSONArray jcityArray = Ob.getJSONArray("hotCity");
				for (int i = 0; i < jcityArray.length(); i++) {
					JSONObject hotCityOb = jcityArray.getJSONObject(i);
					String city_id = hotCityOb.optString("city_id");
					String name = hotCityOb.optString("city");
					double longitude_center = hotCityOb.optDouble("lng");
					double latitude_center = hotCityOb.optDouble("lat");
					SearchCityInfo mInfo = new SearchCityInfo(city_id, name, name, longitude_center, latitude_center,
							"$ ", "热门城市");
					mhotCityList.add(mInfo);
					mAllMap.put(name, city_id);
					mInfo = null;
				}
				mHashMap.put("mhotCityList", mhotCityList);
				/**
				 * 普通城市
				 */
				JSONObject jsonob = Ob.optJSONObject("cityList");
				Iterator<String> itemkeys = jsonob.keys();
				while (itemkeys.hasNext()) {
					String itemkey = (String) itemkeys.next();
					JSONArray jsonArray = jsonob.optJSONArray(itemkey);
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject mCityOb = jsonArray.getJSONObject(i);
						String city_id = mCityOb.optString("city_id");
						String name = mCityOb.optString("city");
						double longitude_center = mCityOb.optDouble("lng");
						double latitude_center = mCityOb.optDouble("lat");
						String sortLetters = itemkey;
						String sortLettersShow = itemkey;
						SearchCityInfo mInfo = new SearchCityInfo(city_id, name, name, longitude_center,
								latitude_center, sortLetters, sortLettersShow);
						mordinaryCityList.add(mInfo);
						mAllMap.put(name, city_id);
					}
				}
				mHashMap.put("mordinaryCityList", mordinaryCityList);
				mHashMap.put("mAllMap", mAllMap);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
