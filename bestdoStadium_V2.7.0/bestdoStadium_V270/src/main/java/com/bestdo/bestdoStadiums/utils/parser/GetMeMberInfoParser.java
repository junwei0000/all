package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.model.ServersInfo;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberListInfo;
import com.bestdo.bestdoStadiums.model.UserCenterMemberInfo;
import com.bestdo.bestdoStadiums.model.UserMemberBuyNoteInfo;
import com.bestdo.bestdoStadiums.model.UserMemberInfo;

/**
 * @author qyy
 * 
 */
public class GetMeMberInfoParser extends BaseParser<Object> {

	private String tip;

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

				JSONObject objda = jsonObject.optJSONObject("data");
				try {
					JSONObject obj = objda.optJSONObject("memeber_info");
					// 当前用户信息
					String uid = obj.optString("uid");
					String memberNo = obj.optString("memberNo");//
					String identityId = obj.optString("identityId");// 会员身份id
					String identityName = obj.optString("identityName");// 会员身份名称
					String buyTime = obj.optString("buyTime");// 会员购买日期
					String useStartTime = obj.optString("useStartTime");// 会员有效期起始时间
					String useEndTime = obj.optString("useEndTime");// 会员有效期结束时间
					String iconUrl = obj.optString("iconUrl");// 会员icon
					String discountBook = obj.optString("discountBook");// 会员订场折扣
					String discountShop = obj.optString("discountShop");// 会员上传折扣
					String defaultCount = obj.optString("defaultCount");
					String isExpired = obj.optString("isExpired");
					String levelName = obj.optString("levelName");
					String levelUrl= obj.optString("levelUrl");
					  tip= obj.optString("tip");
					UserCenterMemberInfo userCenterMemberInfo = new UserCenterMemberInfo(uid, memberNo, identityId,
							identityName, buyTime, useStartTime, useEndTime, iconUrl, discountBook, discountShop,
							defaultCount, isExpired, levelName,levelUrl);
					mHashMap.put("userCenterMemberInfo", userCenterMemberInfo);
				} catch (Exception e) {
				}

				try {
					JSONObject objban = objda.optJSONObject("balance_info");
					String balance = objban.optString("balance");
					String cardNumber = objban.optString("cardNumber");
					String kmNumber = objban.optString("kmNumber");
					UserCenterMemberInfo userCenterBalanceInfo = new UserCenterMemberInfo(balance, cardNumber,
							kmNumber,tip);
					mHashMap.put("userCenterBalanceInfo", userCenterBalanceInfo);
				} catch (Exception e) {
				}

			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
