package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author qyy
 * 
 */
public class PayParser extends BaseParser<Object> {

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
				JSONObject obj = jsonObject.getJSONObject("data");
				String sign = obj.optString("sign");
				String body = obj.optString("body");
				String _input_charset = obj.optString("_input_charset");
				String subject = obj.optString("subject");
				String total_fee = obj.optString("total_fee");
				String sign_type = obj.optString("sign_type");
				String service = obj.optString("service");
				String notify_url = obj.optString("notify_url");
				String partner = obj.optString("partner");
				String seller_id = obj.optString("seller_id");
				String out_trade_no = obj.optString("out_trade_no");
				String payment_type = obj.optString("payment_type");
				String it_b_pay = obj.optString("it_b_pay");
				mHashMap.put("it_b_pay", it_b_pay);
				mHashMap.put("sign", sign);
				mHashMap.put("body", body);
				mHashMap.put("_input_charset", _input_charset);
				mHashMap.put("subject", subject);
				mHashMap.put("total_fee", total_fee);
				mHashMap.put("sign_type", sign_type);
				mHashMap.put("service", service);
				mHashMap.put("notify_url", notify_url);
				mHashMap.put("partner", partner);
				mHashMap.put("seller_id", seller_id);
				mHashMap.put("out_trade_no", out_trade_no);
				mHashMap.put("payment_type", payment_type);
			} else {
				String data = jsonObject.getString("msg");
				mHashMap.put("data", data);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
