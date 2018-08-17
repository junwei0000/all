package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.CreatOrderGetDefautCardInfo;
import com.bestdo.bestdoStadiums.model.CreatOrderGetMerItemPriceInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-27 下午5:28:45
 * @Description 类描述：
 */
public class CreateOrdersGetPriceParser extends BaseParser<Object> {

	ArrayList<CreatOrderGetMerItemPriceInfo> mList;

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				int timestamp = jsonObject.getInt("timestamp");
				mHashMap.put("timestamp", timestamp);
				JSONObject oo = jsonObject.getJSONObject("data");

				String cardsNumber = oo.optString("cardsNumber");
				String accountName = oo.optString("accountName");// 权益卡名称

				String balance = oo.optString("balance", "0.0");// 权益卡余额

				String accountNo = oo.optString("accountNo");// 权益卡好
				String cardId = oo.optString("cardId");// 权益卡id

				mHashMap.put("cardsNumber", cardsNumber);
				mHashMap.put("accountName", accountName);

				balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
				mHashMap.put("balance", balance);

				mHashMap.put("accountNo", accountNo);
				mHashMap.put("cardId", cardId);

				JSONArray arr = oo.getJSONArray("list");
				mList = new ArrayList<CreatOrderGetMerItemPriceInfo>();
				for (int i = 0; i < arr.length(); i++) {
					JSONObject cardobj = arr.getJSONObject(i);

					String mer_item_price_id = cardobj.optString("mer_item_price_id");
					String mer_item_price_time_id = cardobj.optString("mer_item_price_time_id");
					String mer_price_id = cardobj.optString("mer_price_id");
					String mer_item_id = cardobj.optString("mer_item_id");
					String start_time = cardobj.optString("start_time");
					String end_time = cardobj.optString("end_time");
					String week = cardobj.optString("week");
					String start_hour = cardobj.optString("start_hour");
					String end_hour = cardobj.optString("end_hour");
					String prepay_price = cardobj.optString("prepay_price");
					prepay_price = PriceUtils.getInstance().gteDividePrice(prepay_price, "100");
					// 次卡
					String reducedAfterprice = cardobj.optString("reducedAfterprice");
					reducedAfterprice = PriceUtils.getInstance().gteDividePrice(reducedAfterprice, "100");

					String main_deduct_time = cardobj.optString("main_deduct_time");
					main_deduct_time = PriceUtils.getInstance().gteDividePrice(main_deduct_time, "10");

					String type = cardobj.optString("type");
					String accountType = cardobj.optString("accountType", "");

					String isReduced = cardobj.optString("isReduced", "");

					String balance_price = cardobj.optString("balance_price");// 有余额价
					balance_price = PriceUtils.getInstance().gteDividePrice(balance_price, "100");
					String balance_mer_price_id = cardobj.optString("balance_mer_price_id");// 有余额价id
					String nobalance_price = cardobj.optString("nobalance_price");// 无余额价
					nobalance_price = PriceUtils.getInstance().gteDividePrice(nobalance_price, "100");
					String nobalance_mer_price_id = cardobj.optString("nobalance_mer_price_id");// 无余额价id
					String stadium_id = cardobj.optString("stadium_id", "");

					// nobalance_price=prepay_price;
					// balance_price=prepay_price;
					// balance_mer_price_id=mer_price_id;
					// nobalance_mer_price_id=mer_price_id;
					CreatOrderGetMerItemPriceInfo mInfo = new CreatOrderGetMerItemPriceInfo(mer_item_price_id,
							mer_item_price_time_id, mer_price_id, mer_item_id, start_time, end_time, week, start_hour,
							end_hour, prepay_price, type, accountType, balance_price, balance_mer_price_id,
							nobalance_price, nobalance_mer_price_id, reducedAfterprice, main_deduct_time, isReduced,
							stadium_id);
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
