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
import com.bestdo.bestdoStadiums.model.UserMemberSuccessInfo;

/**
 * @author qyy
 * 
 */
public class GetMeMberSuccessParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("msg");
				mHashMap.put("msg", data);
			} else if (status.equals("200")) {

				JSONObject obj = jsonObject.optJSONObject("data");

				// 当前用户信息
				String uid = obj.optString("uid");// 用户uid
				String nickName = obj.optString("nickName");// 用户昵称
				String note = obj.optString("note");// 鼓励语
				String memberNo = obj.optString("memberNo");// 会员编号
				String identityId = obj.optString("identityId");// 会员身份id
				String identityName = obj.optString("identityName");// 会员身份名称
				String buyTime = obj.optString("buyTime");// 会员购买日期
				String useStartTime = obj.optString("useStartTime");// 会员有效期起始时间
				String useEndTime = obj.optString("useEndTime");// 会员有效期结束时间
				String iconUrl = obj.optString("iconUrl");// 会员icon
				String successMsg = obj.optString("successMsg");// 提示语
				String validDays = obj.optString("validDays");// 会员有效期
				String endTimeMsg = obj.optString("endTimeMsg");// 会员到期时间
				String shopUrl = obj.optString("shopUrl");// 会员到期时间
				UserMemberSuccessInfo userMemberSuccessInfo = new UserMemberSuccessInfo(uid, nickName, note, memberNo,
						identityId, identityName, buyTime, useStartTime, useEndTime, iconUrl, successMsg, validDays,
						endTimeMsg, shopUrl);

				mHashMap.put("UserMemberSuccessInfo", userMemberSuccessInfo);

			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
