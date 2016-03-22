package com.zh.education.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zh.education.model.BoKeInfo;
import com.zh.education.model.PingLunInfo;
import com.zh.education.model.UserLoginInfo;

/**
 * @author qyy
 * @Description 类描述：全部评论解析
 */
public class PingLunParser extends BaseParser<Object> {

	ArrayList<PingLunInfo> mList;

	public PingLunParser(ArrayList<PingLunInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject obj = jsonObject.optJSONObject("data");
				int RecordCount = obj.optInt("RecordCount");
				mHashMap.put("total", RecordCount);
				JSONArray jSONArray = obj.optJSONArray("Comments");
				for (int i = 0; i < jSONArray.length(); i++) {
					JSONObject o = jSONArray.getJSONObject(i);
					int id = o.optInt("ID");// 总数量
					String CreateUser = o.optString("CreateUser");// 创建人
					String Content = o.optString("Content");// 详情
					String Title = o.optString("Title");// 名称
					long CreateTime = o.optLong("CreateTime");// 创建时间
					String HeadImg = o.optString("HeadImg", "");
					PingLunInfo pingLunInfo = new PingLunInfo(RecordCount, id,
							Title, Content, CreateUser, CreateTime, HeadImg);
					mList.add(pingLunInfo);
				}
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
