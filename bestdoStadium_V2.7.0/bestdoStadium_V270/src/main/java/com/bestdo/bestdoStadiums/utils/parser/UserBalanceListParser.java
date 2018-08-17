package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.UserBalanceListInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:54:48
 * @Description 类描述：
 */
public class UserBalanceListParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String code = jsonObject.optString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", code);
			if (code.equals("200")) {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);

				JSONObject object = jsonObject.optJSONObject("data");
				String agreementurl = object.optString("agreement");
				String balance = object.optString("balance");
				balance = PriceUtils.getInstance().gteDividePrice(balance, "100");
				ArrayList<UserBalanceListInfo> mList = new ArrayList<UserBalanceListInfo>();
				JSONArray footaray = object.optJSONArray("list");
				for (int i = 0; i < footaray.length(); i++) {
					JSONObject footobject = footaray.optJSONObject(i);
					String id = footobject.optString("id");
					String rechargeMoney = footobject.optString("rechargeMoney");
					rechargeMoney = PriceUtils.getInstance().gteDividePrice(rechargeMoney, "100");
					String presentTitle = footobject.optString("presentTitle");
					String memo = footobject.optString("tip");
					boolean select = false;
					if (i == 0) {
						select = true;
					}
					UserBalanceListInfo mInfo = new UserBalanceListInfo(id, rechargeMoney, presentTitle, memo, select);
					mList.add(mInfo);
					mInfo = null;
				}
				mHashMap.put("agreementurl", agreementurl);
				mHashMap.put("balance", balance);
				mHashMap.put("mList", mList);
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}

		} catch (Exception e) {
		}
		return mHashMap;
	}

}
