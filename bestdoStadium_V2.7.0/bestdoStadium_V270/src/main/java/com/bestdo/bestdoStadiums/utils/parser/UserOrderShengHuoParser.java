package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.UserOrderDetailsInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author Qyy @
 */
public class UserOrderShengHuoParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);

			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
