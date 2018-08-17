package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.PayInfo;

/**
 * @author qyy
 * 
 */
public class PayWXParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("403")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("401")) {
				String data = jsonObject.getString("data");
				mHashMap.put("data", data);
			} else if (status.equals("200")) {
				JSONObject obj = jsonObject.getJSONObject("data");
				JSONObject oo = obj.optJSONObject("info");
				String sign = oo.optString("sign", "");
				String timestamp = oo.optString("timestamp", "");
				String nonce_str = oo.optString("noncestr", "");
				String prepay_id = oo.optString("prepayid");
				String appid = oo.optString("appid");
				String mch_id = oo.optString("partnerid");
				String packages = oo.optString("package");
				PayInfo mPayInfo = new PayInfo(appid, nonce_str, mch_id, prepay_id, sign, timestamp, packages);
				mHashMap.put("mPayInfo", mPayInfo);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
