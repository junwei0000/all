package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.BannerInfo;
import com.bestdo.bestdoStadiums.model.ServersInfo;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberListInfo;
import com.bestdo.bestdoStadiums.model.UserMemberBuyNoteInfo;
import com.bestdo.bestdoStadiums.model.UserMemberInfo;

/**
 * @author qyy
 * 
 */
public class GetMeMberListParser extends BaseParser<Object> {

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

				JSONObject obj = jsonObject.optJSONObject("data");
				// 当前用户信息
				JSONObject user_data_obj = obj.optJSONObject("user_data");
				String uid = user_data_obj.optString("uid");
				String nick_name = user_data_obj.optString("nick_name");
				String note = user_data_obj.optString("note");
				String ablumUrl = user_data_obj.optString("ablumUrl");
				String member_note = user_data_obj.optString("member_note");
				String member_icon = user_data_obj.optString("member_icon");
				String member_level = user_data_obj.optString("member_level");
				String member_name = user_data_obj.optString("member_name");
				UserMemberInfo userMemberInfo = new UserMemberInfo(uid, nick_name, note, ablumUrl, member_note,
						member_icon, member_level, member_name);

				// 可购买的会员列表

				JSONArray array = obj.optJSONArray("member_data");
				ArrayList<UserBuyMeberInfo> imgList = new ArrayList<UserBuyMeberInfo>();

				for (int i = 0; i < array.length(); i++) {

					JSONObject o = array.optJSONObject(i);
					String id = o.optString("id", "");
					String name = o.optString("name", "");
					String useNotice = o.optString("useNotice", "");
					String is_show_more = o.optString("is_show_more", "");
					String show_more_url = o.optString("show_more_url", "");

					UserBuyMeberInfo userBuyMeberInfo = new UserBuyMeberInfo();
					userBuyMeberInfo.setId(id);
					userBuyMeberInfo.setName(name);
					userBuyMeberInfo.setUseNotice(useNotice);
					userBuyMeberInfo.setIs_show_more(is_show_more);
					userBuyMeberInfo.setShow_more_url(show_more_url);

					// 会员特权说明列表
					JSONArray member_buy_note_JsonArray = o.optJSONArray("member_buy_note");
					ArrayList<UserMemberBuyNoteInfo> userMemberBuyNoteInfoList = new ArrayList<UserMemberBuyNoteInfo>();

					for (int j = 0; j < member_buy_note_JsonArray.length(); j++) {
						JSONObject member_buy_note_Jsonobj = member_buy_note_JsonArray.optJSONObject(j);
						String name_ = member_buy_note_Jsonobj.optString("name", "");
						String img = member_buy_note_Jsonobj.optString("img", "");
						String note_ = member_buy_note_Jsonobj.optString("note", "");
						String discount = member_buy_note_Jsonobj.optString("discount", "");
						UserMemberBuyNoteInfo userMemberBuyNoteInfo = new UserMemberBuyNoteInfo(name_, img, note_,
								discount);
						userMemberBuyNoteInfoList.add(userMemberBuyNoteInfo);
					}
					userBuyMeberInfo.setUserMemberBuyNoteInfoList(userMemberBuyNoteInfoList);
					// 可购买会员列表
					JSONArray buy_list_JsonArray = o.optJSONArray("buy_list");
					ArrayList<UserBuyMeberListInfo> userBuyMeberListInfoList = new ArrayList<UserBuyMeberListInfo>();

					for (int k = 0; k < buy_list_JsonArray.length(); k++) {
						JSONObject buy_list_Jsonobj = buy_list_JsonArray.optJSONObject(k);

						String id1 = buy_list_Jsonobj.optString("id", "");
						String identityId = buy_list_Jsonobj.optString("identityId", "");
						String name2 = buy_list_Jsonobj.optString("name", "");
						String priceBase = buy_list_Jsonobj.optString("priceBase", "");
						String validDays = buy_list_Jsonobj.optString("validDays", "");
						String priceMonthly = buy_list_Jsonobj.optString("priceMonthly", "");
						String repertory = buy_list_Jsonobj.optString("repertory", "");
						String balance = buy_list_Jsonobj.optString("balance", "");
						String buy_msg = buy_list_Jsonobj.optString("buy_msg", "");
						String amazing = buy_list_Jsonobj.optString("amazing", "");

						UserBuyMeberListInfo userBuyMeberListInfo = new UserBuyMeberListInfo();
						userBuyMeberListInfo.setId(id1);
						userBuyMeberListInfo.setIdentityId(identityId);
						userBuyMeberListInfo.setName(name2);
						userBuyMeberListInfo.setPriceBase(priceBase);
						userBuyMeberListInfo.setValidDays(validDays);
						userBuyMeberListInfo.setPriceMonthly(priceMonthly);
						userBuyMeberListInfo.setRepertory(repertory);
						userBuyMeberListInfo.setBalance(balance);
						userBuyMeberListInfo.setBuy_msg(buy_msg);
						userBuyMeberListInfo.setAmazing(amazing);
						if (k == 0) {
							userBuyMeberListInfo.setSelect(true);
						} else
							userBuyMeberListInfo.setSelect(false);
						userBuyMeberListInfoList.add(userBuyMeberListInfo);
					}

					userBuyMeberInfo.setUserMemberBuyNoteInfoList(userMemberBuyNoteInfoList);
					userBuyMeberInfo.setUserBuyMeberListInfoList(userBuyMeberListInfoList);
					imgList.add(userBuyMeberInfo);
					mHashMap.put("UserMemberInfo", userMemberInfo);
					mHashMap.put("imgList", imgList);

				}

			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
