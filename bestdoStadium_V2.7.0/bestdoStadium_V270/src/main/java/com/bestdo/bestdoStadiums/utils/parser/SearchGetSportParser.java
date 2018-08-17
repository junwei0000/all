package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-26 上午10:28:07
 * @Description 类描述：运动类型
 */
public class SearchGetSportParser {
	private static SearchGetSportParser mUtilParser;

	public synchronized static SearchGetSportParser getInstance() {
		if (mUtilParser == null) {
			mUtilParser = new SearchGetSportParser();
		}
		return mUtilParser;
	}

	public ArrayList<SportTypeInfo> parseSportJSON(Context context) {
		JSONObject jsonObject = null;
		try {
			String jsondate = CommonUtils.getInstance().getFromAssets(context, "order_sportlist.json");
			jsonObject = new JSONObject(jsondate);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return parseJSON(jsonObject);
	}

	public ArrayList<SportTypeInfo> parseJSON(JSONObject jsonObject) {
		ArrayList<SportTypeInfo> mList = new ArrayList<SportTypeInfo>();
		try {
			String status = jsonObject.getString("code");
			if (status.equals("200")) {

				JSONArray jsonOb = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonOb.length(); i++) {
					JSONObject jsonObj = jsonOb.optJSONObject(i);
					String cid = jsonObj.optString("cid");
					String name = jsonObj.optString("sport");
					String merid = jsonObj.optString("merid");
					boolean check = jsonObj.optBoolean("check");
					SportTypeInfo mInfo = new SportTypeInfo(cid, name, merid, check);
					mList.add(mInfo);
					mInfo = null;
				}
			}
		} catch (Exception e) {
		}
		return mList;
	}

	public ArrayList<SportTypeInfo> sportTypeParseJSON(Context context) {
		ArrayList<SportTypeInfo> mList = new ArrayList<SportTypeInfo>();
		try {
			String jsondate = CommonUtils.getInstance().getFromAssets(context, "main_sportlist.json");
			JSONObject jsonObject = new JSONObject(jsondate);
			String status = jsonObject.getString("code");
			if (status.equals("200")) {

				JSONArray jsonOb = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonOb.length(); i++) {
					JSONObject jsonObj = jsonOb.getJSONObject(i);
					String cid = jsonObj.optString("cid");
					String name = jsonObj.optString("name");
					String merid = jsonObj.optString("merid");
					String alias = jsonObj.optString("alias");
					String sport = jsonObj.optString("sport");// 运动类型名称
					String imgurl = jsonObj.optString("imgurl");
					String defult_price_id = jsonObj.optString("defult_price_id");
					String banlance_price_id = jsonObj.optString("vip_price_id");

					SportTypeInfo mInfo = new SportTypeInfo(cid, name, merid, alias, sport, imgurl, defult_price_id,
							banlance_price_id, "0");
					mList.add(mInfo);
					mInfo = null;
				}
			}
		} catch (Exception e) {
		}
		return mList;
	}

}
