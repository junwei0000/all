package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author jun
 * 
 */
public class UserCardsParser extends BaseParser<Object> {

	ArrayList<UserCardsInfo> mList;

	public UserCardsParser(ArrayList<UserCardsInfo> mList) {
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
				JSONObject job = jsonObject.getJSONObject("data");

				int totalRows = job.optInt("totalRows");
				mHashMap.put("totalRows", totalRows);

				try {
					JSONArray array = job.optJSONArray("list");
					for (int i = 0; i < array.length(); i++) {
						JSONObject oo = array.optJSONObject(i);
						int useStartTime = oo.optInt("useStartTime");
						int useEndTime = oo.optInt("useEndTime");
						String cardTypeName = oo.optString("cardTypeName", "");
						String accountName = oo.optString("accountName", "");

						String projectId = oo.optString("projectId", "");
						String cardTypeId = oo.optString("cardTypeId", "");
						String balance = oo.optString("balance", "");
						String cardNo = oo.optString("cardNo", "");
						String cardId = oo.optString("cardId", "");
						int isExpire = oo.optInt("isExpire");
						String accountType = oo.optString("accountType");

						if (accountType.equals("TIMES")) {
							if (!TextUtils.isEmpty(balance)) {
								balance = PriceUtils.getInstance().gteDividePrice(balance, "10");
							}

						} else {
							if (!TextUtils.isEmpty(balance)) {

								balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
							}
						}
						balance = PriceUtils.getInstance().seePrice(balance);

						String useStartTimes = DatesUtils.getInstance().getTimeStampToDate(useStartTime, "yyyy-MM-dd");
						String useEndTimes = DatesUtils.getInstance().getTimeStampToDate(useEndTime, "yyyy-MM-dd");
						String day = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
						int da = DatesUtils.getInstance().getDateToTimeStamp(day, "yyyy-MM-dd");
						if (useEndTime <= da) {
							isExpire = 1;
						}
						UserCardsInfo userCards = new UserCardsInfo(useStartTimes, useEndTimes, cardTypeName, projectId,
								cardTypeId, balance, cardNo, cardId, isExpire, accountType, accountName);

						mList.add(userCards);
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
		mHashMap.put("mList", mList);

		return mHashMap;
	}

}
