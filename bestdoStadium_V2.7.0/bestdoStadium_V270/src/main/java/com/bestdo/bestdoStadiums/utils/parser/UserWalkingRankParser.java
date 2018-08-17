package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.model.UserWalkingRankInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:28:45
 * @Description 类描述：
 */
public class UserWalkingRankParser extends BaseParser<Object> {

	ArrayList<UserWalkingRankInfo> mList;

	public UserWalkingRankParser(ArrayList<UserWalkingRankInfo> mList) {
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
			if (status.equals("200")) {
				JSONObject dataObj = jsonObject.optJSONObject("data");
				int total = dataObj.optInt("total");
				String company_name = dataObj.optString("company_name");
				String dept_name = dataObj.optString("dept_name");
				JSONObject userinfoObj = dataObj.optJSONObject("user_info");
				String uid = userinfoObj.optString("uid");
				String step_num = userinfoObj.optString("step_num");
				String nick_name = userinfoObj.optString("nick_name");
				String ablum_url = userinfoObj.optString("ablum_url");
				int num = userinfoObj.optInt("num", 0);
				UserWalkingRankInfo mUserWalkingRankInfo = new UserWalkingRankInfo(uid, step_num, nick_name, ablum_url,
						num);
				mHashMap.put("mUserWalkingRankInfo", mUserWalkingRankInfo);

				try {
					JSONArray historyArray = dataObj.getJSONArray("list");
					for (int i = 0; i < historyArray.length(); i++) {
						JSONObject historyObj = historyArray.getJSONObject(i);
						setList(historyObj);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				mHashMap.put("company_name", company_name);
				mHashMap.put("dept_name", dept_name);
				mHashMap.put("total", total);
				mHashMap.put("mList", mList);
			} else {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

	private void setList(JSONObject historyObj) {
		String uid = historyObj.optString("uid");
		String step_num = historyObj.optString("step_num");
		String nick_name = historyObj.optString("nick_name");
		String ablum_url = historyObj.optString("ablum_url");
		String dept_name = historyObj.optString("dept_name");
		int num = historyObj.optInt("num", 0);
		UserWalkingRankInfo mUserWalkingRankInfo = new UserWalkingRankInfo(uid, step_num, nick_name, ablum_url, num);
		mUserWalkingRankInfo.setDept_name(dept_name);
		mList.add(mUserWalkingRankInfo);
		mUserWalkingRankInfo = null;
	}
}
