package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.UserOrderDetailsInfo;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author Qyy @
 */
public class UserOrderGetNumParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
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
				JSONObject dataObj = jsonObject.getJSONObject("data");
				int unpay_num = dataObj.optInt("unpay_num");
				int confirm_num = dataObj.optInt("confirm_num");
				int off_num = dataObj.optInt("off_num");
				String order_list = dataObj.optString("order_list");
				String address_list = dataObj.optString("address_list");

				UserOrdersInfo mOrdersNumInfo = new UserOrdersInfo(unpay_num, confirm_num, off_num, order_list,
						address_list);
				mHashMap.put("mOrdersNumInfo", mOrdersNumInfo);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
