package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.CreatOrderGetDefautCardInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-27 下午5:28:45
 * @Description 类描述：
 */
public class CreateOrdersGetDefautCardParser extends BaseParser<Object> {

	ArrayList<CreatOrderGetDefautCardInfo> mList;

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			int timestamp = jsonObject.getInt("timestamp");
			mHashMap.put("timestamp", timestamp);
			if (status.equals("200")) {
				JSONObject oo = jsonObject.getJSONObject("data");
				String total = oo.optString("totalRows");
				String default_price_id = oo.optString("default_price_id", "");
				mHashMap.put("default_price_id", default_price_id);
				mHashMap.put("total", total);
				JSONArray cardarray = oo.getJSONArray("list");
				mList = new ArrayList<CreatOrderGetDefautCardInfo>();
				for (int i = 0; i < cardarray.length(); i++) {
					JSONObject cardobj = cardarray.getJSONObject(i);

					String accountNo = cardobj.optString("accountNo");
					String accountName = cardobj.optString("accountName");
					String usableBalance = cardobj.optString("usableBalance");
					String accountType = cardobj.optString("accountType");
					String balancePriceId = cardobj.optString("balancePriceId");

					String noBalancePriceId = cardobj.optString("noBalancePriceId");
					String amountType = cardobj.optString("amountType");
					String amount = cardobj.optString("amount");
					String balance = cardobj.optString("balance");
					if (accountType.equals("TIMES")) {
						balance = PriceUtils.getInstance().gteDividePrice(balance, "10");
					} else {
						balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
					}
					amount = PriceUtils.getInstance().gteDividePrice(amount, "100");
					String isReduced = cardobj.optString("isReduced");
					String accountRuleId = cardobj.optString("accountRuleId");
					String accountBuyNumber = cardobj.optString("accountBuyNumber");
					String cardId = cardobj.optString("cardId");
					String cardNo = cardobj.optString("cardNo");
					int useStartTime = cardobj.optInt("useStartTime");
					int useEndTime = cardobj.optInt("useEndTime");
					String useStartTimes = DatesUtils.getInstance().getTimeStampToDate(useStartTime, "yyyy-MM-dd");
					String useEndTimes = DatesUtils.getInstance().getTimeStampToDate(useEndTime, "yyyy-MM-dd");
					CreatOrderGetDefautCardInfo mInfo = new CreatOrderGetDefautCardInfo(accountNo, accountName,
							usableBalance, balancePriceId, noBalancePriceId, amountType, amount, balance, isReduced,
							accountRuleId, accountBuyNumber, cardId, cardNo, useStartTimes, useEndTimes,
							default_price_id, accountType);
					mList.add(mInfo);
					mInfo = null;
				}
				mHashMap.put("mList", mList);
				mList = null;
			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
