package com.zh.education.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zh.education.model.NoticesInfo;
import com.zh.education.model.UserLoginInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class NoticesParser extends BaseParser<Object> {
	ArrayList<NoticesInfo> mList;

	public NoticesParser(ArrayList<NoticesInfo> mList) {
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
				JSONObject jsonOb = jsonObject.getJSONObject("data");
				int total = jsonOb.optInt("RecordCount");
				JSONArray NoticesArray = jsonOb.getJSONArray("Notices");
				for (int i = 0; i < NoticesArray.length(); i++) {
					JSONObject noticesobj = NoticesArray.getJSONObject(i);
					String noticeType = noticesobj.optString("NoticeType");
					String content = noticesobj.optString("Content");
					String title = noticesobj.optString("Title");
					String id = noticesobj.optString("ID");
					long createTime = noticesobj.optLong("CreateTime");
					String showtime = DatesUtils.getInstance()
							.getTimeStampToDate(createTime, "yyyy-MM-dd");

					NoticesInfo mNoticesInfo = new NoticesInfo(id,noticeType,
							content, title, showtime);
					mList.add(mNoticesInfo);
					mNoticesInfo = null;
				}
				mHashMap.put("total", total);
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
