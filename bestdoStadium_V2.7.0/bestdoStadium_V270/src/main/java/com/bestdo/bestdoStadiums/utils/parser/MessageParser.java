package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.MessageInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午4:27:48
 * @Description 类描述：
 */
public class MessageParser extends BaseParser<Object> {
	ArrayList<MessageInfo> mList;

	public MessageParser() {
		super();
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("code", status);
			if (status.equals("200")) {
				JSONArray Array = jsonObject.getJSONArray("data");
				mList = new ArrayList<MessageInfo>();
				for (int i = 0; i < Array.length(); i++) {
					JSONObject itemobj = Array.getJSONObject(i);

					String title = itemobj.optString("title");
					String thumb = itemobj.optString("thumb");
					String type = itemobj.optString("type");
					String is_read = itemobj.optString("is_read", "");
					String message_title = itemobj.optString("message_title");

					MessageInfo mStadiumInfo = new MessageInfo(title, thumb, type, is_read, message_title);
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
