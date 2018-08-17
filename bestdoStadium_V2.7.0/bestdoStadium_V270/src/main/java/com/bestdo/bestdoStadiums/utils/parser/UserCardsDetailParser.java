package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.UserCardsDetailInfo;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author qyy
 * 
 */
public class UserCardsDetailParser extends BaseParser<Object> {

	private String getCardDetailInfoFromStats;

	public UserCardsDetailParser(String getCardDetailInfoFromStats) {
		this.getCardDetailInfoFromStats = getCardDetailInfoFromStats;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			// 激活期限已过：402
			// 未到激活期限：403
			// 卡已被禁用：404
			// 401,卡已被激活

			// 406=>'不存在该激活信息'
			// 405=>'激活失败'
			// 404=>'卡已被禁用'
			// 403=>'未到激活期限'
			// 402=>'激活期限已过'
			// 401=>'卡已被激活'
			// 400=>'（其他系统或参数错误）'
			if (status.equals("401")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);

				JSONObject job = jsonObject.getJSONObject("data");
				String cardId = job.optString("card_id");
				mHashMap.put("cardId", cardId);
				String jihuouid = job.optString("uid");
				mHashMap.put("jihuouid", jihuouid);
			} else if (status.equals("200")) {
				if (getCardDetailInfoFromStats.equals(Constans.getInstance().getCardDetailInfoByAbstractPage)) {
					String data = jsonObject.getString("msg");
					mHashMap.put("data", data);

					JSONObject job = jsonObject.getJSONObject("data");
					String cardId = job.optString("card_id");
					String jihuouid = job.optString("uid");

					mHashMap.put("jihuouid", jihuouid);
					mHashMap.put("cardId", cardId);

				} else {
					JSONObject job = jsonObject.getJSONObject("data");
					String isShowTel = job.optString("isShowTel");
					String showTel = job.optString("showTel");
					mHashMap.put("isShowTel", isShowTel);
					mHashMap.put("showTel", showTel);
					String cardId = job.optString("cardId");
					String cardNo = job.optString("cardNo");
					String uid = job.optString("uid");
					String cardBatchId = job.optString("cardBatchId");
					String cardTypeName = "";
					String companyId = job.optString("companyId");
					String status2 = job.optString("status");
					int useStartTime = job.optInt("useStartTime");
					String useStartTime_ = DatesUtils.getInstance().getTimeStampToDate(useStartTime, "yyyy-MM-dd");

					int useEndTime = job.optInt("useEndTime");
					String useEndTime_ = DatesUtils.getInstance().getTimeStampToDate(useEndTime, "yyyy-MM-dd");

					int activeTime = job.optInt("activeTime");
					String activeTime_ = DatesUtils.getInstance().getTimeStampToDate(activeTime, "yyyy-MM-dd HH:mm");

					String amount = "";
					String balance = "";
					String accountNo = "";
					StringBuffer stringBuffer = null;
					String accountType = "";
					try {
						JSONArray array = job.optJSONArray("cardAccount");
						if (array != null) {
							for (int i = 0; i < 1; i++) {
								JSONObject o = array.optJSONObject(i);
								accountNo = o.optString("accountNo");
								cardTypeName = o.optString("accountName");
								balance = o.optString("balance");
								amount = o.optString("amount");
								accountType = o.optString("accountType");

								if (accountType.equals("TIMES")) {
									if (!TextUtils.isEmpty(balance)) {
										balance = PriceUtils.getInstance().gteDividePrice(balance, "10");
										amount = PriceUtils.getInstance().gteDividePrice(amount, "10");
									}
								} else {
									if (!TextUtils.isEmpty(balance)) {

										balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
										amount = PriceUtils.getInstance().gteDividePrice(amount, "100");
									}
								}
								balance = PriceUtils.getInstance().seePrice(balance);
								amount = PriceUtils.getInstance().seePrice(amount);
							}

						}
						JSONArray a = job.optJSONArray("buyableMerList");
						stringBuffer = new StringBuffer();
						if (a != null) {
							for (int j = 0; j < a.length(); j++) {
								String ss = a.optString(j);
								if (j == a.length() - 1) {
									stringBuffer.append(ss);
								} else {
									stringBuffer.append(ss + "、");
								}
							}
						}
					} catch (Exception e) {
					}
					UserCardsDetailInfo u = new UserCardsDetailInfo(cardId, cardNo, uid, cardBatchId, cardTypeName,
							companyId, useStartTime_, useEndTime_, status2, amount, stringBuffer + "", balance,
							activeTime_, accountNo, accountType);

					mHashMap.put("UserCardsDetailInfo", u);
				}
			} else {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			}
		} catch (Exception e) {
		}

		return mHashMap;

	}
}
