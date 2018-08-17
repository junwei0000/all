package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.CircleBean;

import android.text.TextUtils;

/**
 * @author jun
 * 
 */
public class CampaignGoodMonmentsParser extends BaseParser<Object> {

	ArrayList<CircleBean> mList;

	public CampaignGoodMonmentsParser(ArrayList<CircleBean> mList) {
		super();
		this.mList = mList;
	}

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
				JSONArray job = jsonObject.optJSONArray("data");

				for (int i = 0; i < job.length(); i++) {
					ArrayList<String> imgList = new ArrayList<String>();
					ArrayList<String> thumb_urlList = new ArrayList<String>();
					ArrayList<String> widthList = new ArrayList<String>();
					ArrayList<String> heightList = new ArrayList<String>();
					JSONObject oo = job.optJSONObject(i);
					String wonderful_id = oo.optString("wonderful_id");
					String theme = oo.optString("theme");
					String content = oo.optString("content");
					String laud_count = oo.optString("laud_count");
					String share_count = oo.optString("share_count");
					String create_time = oo.optString("create_time");
					String bestdo_uid = oo.optString("bestdo_uid");
					String name = oo.optString("name");
					String img_video_type = oo.optString("img_video_type");
					String avatar = oo.optString("avatar");
					Boolean isClickGood = false;
					String wondful_show_url = oo.optString("wondful_show_url");
					JSONArray aa = oo.optJSONArray("img_video");
					if (aa != null && aa.length() > 0) {
						for (int j = 0; j < aa.length(); j++) {
							JSONObject jsonobject = aa.optJSONObject(j);
							String img = jsonobject.optString("img");
							String thumb_url = jsonobject.optString("thumb_url");
							String width = jsonobject.optString("thumb_width");
							String height = jsonobject.optString("thumb_height");
							imgList.add(img);
							thumb_urlList.add(thumb_url);
							widthList.add(width);
							heightList.add(height);
						}
					}

					CircleBean c = new CircleBean(isClickGood, imgList, thumb_urlList, wonderful_id, theme, content,
							laud_count, create_time, share_count, bestdo_uid, name, widthList, heightList,
							img_video_type, avatar, wondful_show_url);
					mList.add(c);
				}

			}
		} catch (Exception e) {
		}
		mHashMap.put("mList", mList);

		return mHashMap;
	}

}
