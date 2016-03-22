package com.zh.education.utils.parser;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zh.education.model.UserLoginInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class UserLoginParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				UserLoginInfo loginInfo = new UserLoginInfo();
				try {
					JSONObject jsonOb = jsonObject.getJSONObject("data");
					String uid = jsonOb.optString("ID");
					String sid = jsonOb.optString("Sid");
					String LoginName = jsonOb.optString("LoginName");
					String Name = jsonOb.optString("Name");
					String Email = jsonOb.optString("Email");
					String PictureUrl = jsonOb.optString("PictureUrl");
					String TokenStr = jsonOb.optString("TokenStr");
					Boolean AllowBrowseUserInfo = jsonOb
							.optBoolean("AllowBrowseUserInfo");
					String groupsStringArray[];
					JSONArray groups = jsonOb.optJSONArray("Groups");
					if (groups.length() > 0) {
						groupsStringArray = new String[groups.length()];
						for (int i = 0; i < groups.length(); i++) {
							groupsStringArray[i] = groups.optString(i);
						}
					}
					Boolean IsApplicationPrincipal = jsonOb
							.optBoolean("IsApplicationPrincipal");
					Boolean IsDomainGroup = jsonOb.optBoolean("IsDomainGroup");
					Boolean IsSiteAdmin = jsonOb.optBoolean("IsSiteAdmin");
					Boolean IsSiteAuditor = jsonOb.optBoolean("IsSiteAuditor");
					String Notes = jsonOb.optString("Notes");

					String OwnedGroups = jsonOb.optString("OwnedGroups");
					String RawSid = jsonOb.optString("RawSid");
					String Roles = jsonOb.optString("Roles");
					String UserToken = jsonOb.optString("UserToken");
					String SchoolUrl = jsonOb.optString("SchoolUrl");
					String PersonalSiteUrl = jsonOb
							.optString("PersonalSiteUrl");
					String TeacherSpaceUrl = jsonOb
							.optString("TeacherSpaceUrl");
					String StudentSpaceUrl = jsonOb
							.optString("StudentSpaceUrl");
					String BlogUrl = jsonOb.optString("BlogUrl");
					String ClassSpaceList = jsonOb.optString("ClassSpaceList");

					loginInfo.setUid(uid);
					loginInfo.setSid(sid);
					loginInfo.setSchoolUrl(SchoolUrl);
					loginInfo.setUserToken(UserToken);
					loginInfo.setTokenStr(TokenStr);
					loginInfo.setName(Name);
					loginInfo.setNotes(Notes);
					loginInfo.setPictureUrl(PictureUrl);
					loginInfo.setBlogUrl(BlogUrl);
				} catch (Exception e) {
				}

				mHashMap.put("loginInfo", loginInfo);
				loginInfo = null;
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
