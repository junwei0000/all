package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.model.UserCardsMiInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

/**
 * @author jun
 * 
 */
public class UserCardsMiParser extends BaseParser<Object> {

	ArrayList<UserCardsMiInfo> mList;

	public UserCardsMiParser(ArrayList<UserCardsMiInfo> mList) {
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
				JSONObject job = jsonObject.getJSONObject("data");
				try {
					int totalRows = job.optInt("totalRows");
					mHashMap.put("totalRows", totalRows);
					JSONArray array = job.optJSONArray("cardList");
					String validNote = job.optString("validNote", "");
					String backGroundColor = job.optString("backGroundColor", "");
					String camiNote = job.optString("camiNote", "");
					String ada = (String) job.optString("useNoteUrl");
					for (int i = 0; i < array.length(); i++) {
						JSONObject oo = array.optJSONObject(i);

						// card_name: "百动高尔夫尊享卡",//卡名称
						// •card_no: "16w164133749",//卡号
						// •card_pass: "582016574680",//卡密
						// •card_status: "未激活",//卡状态
						// •amount: "20次"//卡次数

						String card_name = oo.optString("card_name", "");
						String card_no = oo.optString("card_no", "");
						String card_pass = oo.optString("card_pass", "");
						String card_status = oo.optString("card_status", "");
						String amount = oo.optString("amount", "");
						String card_img = oo.optString("card_img", "");
						String share_url = oo.optString("share_url", "");

						UserCardsMiInfo userCards = new UserCardsMiInfo(card_name, card_no, card_pass, card_status,
								amount, card_img, share_url);
						userCards.setBackGroundColor(backGroundColor);
						userCards.setCamiNote(camiNote);
						userCards.setValidNote(validNote);
						mList.add(userCards);
					}

				} catch (Exception e) {
				}
			}
			mHashMap.put("mList", mList);
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
