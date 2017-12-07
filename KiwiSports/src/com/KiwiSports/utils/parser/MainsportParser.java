package com.KiwiSports.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.utils.CommonUtils;

import android.content.Context;

public class MainsportParser extends BaseParser<Object> {

	public ArrayList<MainSportInfo> parseJSON(Context context) {
		JSONObject jsonObject = null;
		ArrayList<MainSportInfo> mallsportList = null;
		try {
			String jsondate = CommonUtils.getInstance().getFromAssets(context, "main_sport.java");
			jsonObject = new JSONObject(jsondate);
			mallsportList = parseJSON(jsonObject);
			jsonObject = null;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mallsportList;
	}

	@Override
	public ArrayList<MainSportInfo> parseJSON(JSONObject jsonObject) {
		ArrayList<MainSportInfo> mallList = new ArrayList<MainSportInfo>();
		try {
			JSONArray Ob = jsonObject.optJSONArray("data");
			for (int i = 0; i < Ob.length(); i++) {
				JSONObject hotOb = Ob.optJSONObject(i);
				String ename = hotOb.optString("ename");
				String cname = hotOb.optString("cname");
				ArrayList<MainSportInfo> mpropertytwnList = new ArrayList<MainSportInfo>();
				ArrayList<MainSportInfo> mpropertyList = new ArrayList<MainSportInfo>();
				JSONArray propertyOb = hotOb.optJSONArray("property");
				for (int j = 0; j < propertyOb.length(); j++) {
					JSONObject propOb = propertyOb.optJSONObject(j);
					String ename_ = propOb.optString("ename");
					String cname_ = propOb.optString("cname");
					String value = propOb.optString("value");
					String unit = propOb.optString("unit");
					MainSportInfo mMainSportInfo = new MainSportInfo(cname_, ename_, value, unit);
					if (j < 2) {
						mpropertytwnList.add(mMainSportInfo);
					} else {
						mpropertyList.add(mMainSportInfo);
					}
				}

				MainSportInfo mMainSportInfo = new MainSportInfo(cname, ename, mpropertytwnList, mpropertyList);
				mallList.add(mMainSportInfo);
			}
		} catch (Exception e) {
		}
		return mallList;
	}

}
