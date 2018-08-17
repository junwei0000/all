package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.MessageListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午4:27:48
 * @Description 类描述：
 */
public class MessageListParser extends BaseParser<Object> {
	ArrayList<MessageListInfo> mList;

	public MessageListParser() {
		super();
	}

	public MessageListParser(ArrayList<MessageListInfo> mList) {
		super();
		this.mList = mList;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", status);
			if (status.equals("200")) {
				JSONObject job = jsonObject.getJSONObject("data");
				int total = job.optInt("count");
				mHashMap.put("total", total);
				JSONArray stadiumArray = job.getJSONArray("list");
				for (int i = 0; i < stadiumArray.length(); i++) {
					JSONObject itemobj = stadiumArray.getJSONObject(i);

					String id = itemobj.optString("id");
					String bestdo_uid = itemobj.optString("bestdo_uid");
					String type = itemobj.optString("type");
					String title = itemobj.optString("title");
					String content = itemobj.optString("content");
					String sport_id = itemobj.optString("sport_id");
					String source = itemobj.optString("source");
					String is_read = itemobj.optString("is_read");
					int add_time = itemobj.optInt("add_time");
					String icon = itemobj.optString("icon");
					String activity_id = itemobj.optString("activity_id");
					String activity_name = itemobj.optString("activity_name");
					String activity_status = itemobj.optString("activity_status");
					MessageListInfo mStadiumInfo = new MessageListInfo(id, bestdo_uid, type, title, content, sport_id,
							source, is_read, add_time, icon, activity_id, activity_name, activity_status);
					mList.add(mStadiumInfo);
					mStadiumInfo = null;
				}

			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}
		mHashMap.put("mList", mList);

		return mHashMap;
	}

}
