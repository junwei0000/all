package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonArrayRequest;
import com.bestdo.bestdoStadiums.model.CashbookListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2017-1-3 下午2:43:16
 * @Description 类描述：
 */
public class CashbookListParser extends BaseParser<Object> {

	ArrayList<CashbookListInfo> mList;

	public CashbookListParser(ArrayList<CashbookListInfo> mList) {
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				JSONObject jdate = jsonObject.optJSONObject("data");
				int total = jdate.optInt("count");
				mHashMap.put("total", total);
				String payMoney = jdate.optString("payMoney");
				mHashMap.put("payMoney", payMoney);
				String incomeMoney = jdate.optString("incomeMoney");
				mHashMap.put("incomeMoney", incomeMoney);
				String budgetSurplus = jdate.optString("budgetSurplus");
				mHashMap.put("budgetSurplus", "" + budgetSurplus);
				String yearBudge = jdate.optString("yearBudge");
				mHashMap.put("yearBudge", yearBudge);
				String yearBudgeStarts = jdate.optString("yearBudgeStarts");
				mHashMap.put("yearBudgeStarts", "" + yearBudgeStarts);
				JSONArray stadiumArray = jdate.getJSONArray("list");
				for (int i = 0; i < stadiumArray.length(); i++) {
					JSONObject stadiumObj = stadiumArray.optJSONObject(i);
					String id = stadiumObj.optString("id");
					String money = stadiumObj.optString("money");
					String use_time = stadiumObj.optString("use_time");
					String type = stadiumObj.optString("type");
					String class_name = stadiumObj.optString("class_name");
					String description = stadiumObj.optString("description");
					String img = stadiumObj.optString("img");
					String select_img = stadiumObj.optString("select_img");

					CashbookListInfo mStadiumInfo = new CashbookListInfo(id, money, use_time, type, class_name,
							description, img, select_img);
					mList.add(mStadiumInfo);
					mStadiumInfo = null;
				}
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
			mHashMap.put("mList", mList);
		} catch (Exception e) {
		}
		return mHashMap;
	}

}
