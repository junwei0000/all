package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.UserOrderDetailsInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-1 下午7:42:40
 * @Description 类描述：
 */
public class CreateMemberOrdersParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject dataobj = jsonObject.getJSONObject("data");
				String oid = dataobj.optString("oid");
				mHashMap.put("oid", oid);
			} else if (status.equals("400")) {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
