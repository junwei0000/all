package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午4:27:48
 * @Description 类描述：
 */
public class UserCollectParser extends BaseParser<Object> {
	ArrayList<UserCollectInfo> mList;

	public UserCollectParser() {
		super();
	}

	public UserCollectParser(ArrayList<UserCollectInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject job = jsonObject.getJSONObject("data");
				int total = job.optInt("totalRows");
				mHashMap.put("total", total);
				JSONArray stadiumArray = job.getJSONArray("list");
				for (int i = 0; i < stadiumArray.length(); i++) {
					JSONObject itemobj = stadiumArray.getJSONObject(i);
					String relationNo = itemobj.optString("relationNo");
					String vip_price_id = itemobj.optString("vip_price_id", "");
					String minPrice = itemobj.optString("minPrice");
					String maxPrice = itemobj.optString("maxPrice");
					minPrice = PriceUtils.getInstance().gteDividePrice(minPrice, "100");
					maxPrice = PriceUtils.getInstance().gteDividePrice(maxPrice, "100");
					JSONObject collectOBJ = itemobj.getJSONObject("collectInfo");
					String venueName = collectOBJ.optString("venueName");
					String mer_item_name = collectOBJ.optString("mer_item_name");
					if (TextUtils.isEmpty(mer_item_name)) {
						mer_item_name = venueName;
					}
					String thumb = collectOBJ.optString("thumb");
					String region = collectOBJ.optString("region");

					UserCollectInfo mStadiumInfo = new UserCollectInfo(relationNo, venueName, mer_item_name, minPrice,
							maxPrice, thumb, region, vip_price_id);
					mList.add(mStadiumInfo);
					mStadiumInfo = null;
				}

			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}
		mHashMap.put("mList", mList);

		return mHashMap;
	}

}
