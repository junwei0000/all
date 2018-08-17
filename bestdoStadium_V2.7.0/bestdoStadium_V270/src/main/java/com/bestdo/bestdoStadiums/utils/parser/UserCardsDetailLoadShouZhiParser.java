package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.UserCardsDetaiShouZhilInfo;
import com.bestdo.bestdoStadiums.model.UserCardsDetailInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-10 下午12:00:57
 * @Description 类描述：
 */
public class UserCardsDetailLoadShouZhiParser extends BaseParser<Object> {
	ArrayList<UserCardsDetaiShouZhilInfo> mList;

	public UserCardsDetailLoadShouZhiParser(ArrayList<UserCardsDetaiShouZhilInfo> mList) {
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
				int totalRows = job.optInt("totalRows");
				mHashMap.put("totalRows", totalRows);
				try {
					JSONArray array = job.optJSONArray("list");

					for (int i = 0; i < array.length(); i++) {
						JSONObject oo = array.optJSONObject(i);
						String relationNo = oo.optString("relationNo", "");
						int createTime_ = oo.optInt("createTime");
						String accountType = oo.optString("accountType", "");
						String createTime = DatesUtils.getInstance().getTimeStampToDate(createTime_,
								"yyyy-MM-dd HH:mm");
						String recordType = oo.optString("recordType", "");
						String typeName;
						String balance;
						balance = oo.optString("balance", "");
						if (accountType.equals("TIMES")) {
							balance = PriceUtils.getInstance().gteDividePrice(balance, "10");
							balance = PriceUtils.getInstance().seePrice(balance);
							balance = balance + "次";

						} else {
							balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
							balance = PriceUtils.getInstance().seePrice(balance);
							balance = "¥" + balance;
						}
						if (recordType.equals("USE")) {
							typeName = oo.optString("useTypeName", "");
							balance = "-" + balance;

						} else {
							typeName = oo.optString("rechargeTypeName", "");
							balance = "+" + balance;
						}
						UserCardsDetaiShouZhilInfo u = new UserCardsDetaiShouZhilInfo(relationNo, createTime,
								recordType, typeName, balance);
						mList.add(u);
						u = null;

					}
				} catch (Exception e) {
				}

				mHashMap.put("mList", mList);

			} else {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			}

		} catch (Exception e) {

		}

		return mHashMap;

	}
}
