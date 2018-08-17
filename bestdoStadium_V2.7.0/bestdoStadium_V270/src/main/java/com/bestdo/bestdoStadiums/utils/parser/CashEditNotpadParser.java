package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.jpush.android.data.l;

import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.CashClubYearbudgetInfo;
import com.bestdo.bestdoStadiums.model.CashMyClubInfo;
import com.bestdo.bestdoStadiums.model.ClubEventsInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class CashEditNotpadParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
				JSONObject listArray = jsonObject.optJSONObject("data");
				String payMoney = listArray.optString("payMoney", "");
				String incomeMoney = listArray.optString("incomeMoney", "");
				String budgetSurplus = listArray.optString("budgetSurplus", "");
				mHashMap.put("payMoney", payMoney);
				mHashMap.put("incomeMoney", incomeMoney);
				mHashMap.put("budgetSurplus", budgetSurplus);
			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}

		} catch (Exception e) {
		}
		return mHashMap;
	}

}
