package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.UserCardsBuyInfo;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author jun
 * 
 */
public class UserCardsBuyParser extends BaseParser<Object> {

	ArrayList<UserCardsBuyInfo> mList;

	public UserCardsBuyParser(ArrayList<UserCardsBuyInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			mHashMap = new HashMap<String, Object>();
			String status = jsonObject.getString("code");
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONArray jobdata = jsonObject.getJSONArray("data");

				try {
					for (int i = 0; i < jobdata.length(); i++) {
						JSONObject kaitem = jobdata.getJSONObject(i);
						String productid = kaitem.optString("productid");
						String price = kaitem.optString("price");
						String name = kaitem.optString("name");
						String detail = kaitem.optString("detail");
						String card_batch_id = kaitem.optString("card_batch_id");

						UserCardsBuyInfo mUserCardsBuyInfo = new UserCardsBuyInfo(productid, price, name, detail,
								card_batch_id);
						mList.add(mUserCardsBuyInfo);
						mUserCardsBuyInfo = null;
					}
				} catch (Exception e) {
				}
			} else {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			}
		} catch (Exception e) {
		}
		mHashMap.put("mList", mList);
		return mHashMap;
	}

}
