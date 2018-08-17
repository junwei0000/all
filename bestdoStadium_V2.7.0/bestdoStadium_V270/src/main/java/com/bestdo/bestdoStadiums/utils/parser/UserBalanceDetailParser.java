package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.UserBalanceDetailInfo;
import com.bestdo.bestdoStadiums.model.UserBalanceListInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class UserBalanceDetailParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.optString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				JSONObject object = jsonObject.optJSONObject("data");
				String consumeTotal= object.optString("consumeTotal");
				String totalRows = object.optString("totalRows");
				consumeTotal=PriceUtils.getInstance().gteDividePrice(consumeTotal, "100");
				consumeTotal = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(consumeTotal));
				ArrayList<UserBalanceDetailInfo> mList = new ArrayList<UserBalanceDetailInfo>();
				JSONArray footaray = object.optJSONArray("list");
				for (int i = 0; i < footaray.length(); i++) {
					JSONObject footobject = footaray.optJSONObject(i);
					String id = footobject.optString("id");
					String serviceId = footobject.optString("serviceId");
					String relationNo = footobject.optString("relationNo");
					int rechargeConsumeTime = footobject.optInt("rechargeConsumeTime");
					String balance = footobject.optString("balance");
					balance=PriceUtils.getInstance().gteDividePrice(balance, "100");
					balance = PriceUtils.getInstance().formatFloatNumber(Double.parseDouble(balance));
					String orderNo = footobject.optString("orderNo");
					String orderDesc = footobject.optString("orderDesc");
					String type = footobject.optString("type");
					String	formatterTime= footobject.optString("formatterTime");
					UserBalanceDetailInfo mInfo = new UserBalanceDetailInfo(id, serviceId, relationNo,
							rechargeConsumeTime, balance, orderNo, orderDesc, type,formatterTime);
					mList.add(mInfo);
					mInfo = null;
				}
				mHashMap.put("mList", mList);
				mHashMap.put("totalRows", totalRows);
				mHashMap.put("consumeTotal", consumeTotal);
				
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}

		} catch (Exception e) {
		}
		return mHashMap;
	}

}
