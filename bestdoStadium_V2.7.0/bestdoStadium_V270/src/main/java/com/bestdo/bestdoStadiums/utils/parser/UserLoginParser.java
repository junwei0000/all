package com.bestdo.bestdoStadiums.utils.parser;

import java.util.HashMap;

import org.json.JSONObject;

import com.bestdo.bestdoStadiums.model.UserLoginInfo;

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
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				JSONObject jsonOb = jsonObject.getJSONObject("data");
				String uid = jsonOb.optString("uid", "");
				String sid = jsonOb.optString("sid", "");
				String mobile = jsonOb.optString("telephone", "");

				String ablum = jsonOb.optString("ablum", "");
				String loginName = jsonOb.optString("loginName", "");
				String nickName = jsonOb.optString("nickName", "");
				String realName = jsonOb.optString("realName", "");
				String sex = jsonOb.optString("sex", "");
				String qq = jsonOb.optString("qq", "");
				String email = jsonOb.optString("email", "");
				// String bid = jsonOb.optString("bid", "");
				// String deptid = jsonOb.optString("deptid", "");
				// String bname = jsonOb.optString("bname", "");
				String nameChangeTimes = jsonOb.optString("nameChangeTimes", "");

				// •bid: 332,//企业ID nobid
				// •bname: "新赛点健步走",//企业名称
				// •deptId: 332,//部门ID
				// •deptName: "新赛点北京分公司"//部门名称
				JSONObject companyInfoOBj = jsonOb.optJSONObject("companyInfo");
				String bid = companyInfoOBj.optString("bid", "");
				String bname = companyInfoOBj.optString("bname", "");
				String deptId = companyInfoOBj.optString("deptId", "");
				String deptName = companyInfoOBj.optString("deptName", "");

				JSONObject rankedInfoOBj = jsonOb.optJSONObject("rankedInfo");
				String ranked_show = rankedInfoOBj.optString("ranked_show", "");
				String active_url = rankedInfoOBj.optString("active_url", "");
				String company_title = "";
				String department_title = "";
				try {
					JSONObject ranked_titleOBj = rankedInfoOBj.optJSONObject("ranked_title");
					company_title = ranked_titleOBj.optString("company_title", "");
					department_title = ranked_titleOBj.optString("department_title", "");
				} catch (Exception e) {
					// TODO: handle exception
				}

				JSONObject clubInfoOBj = jsonOb.optJSONObject("clubInfo");
				String name = "";
				String department = "";
				String corp_id = "";
				String manage_office_name = "";
				String privilege_id = "";
				String corp_name = "";
				String corp_head_title = "";
				if (clubInfoOBj != null) {
					// corp_id: "4",//
					// •name: "赵将",//
					// •department: "产品研发中心",//
					// •mobile: "15510471106",//用户手机号
					// •city_id: "1",//所在城市
					// •corp_name: "百动企业",//企业名称
					// •manage_office_name: "CEO",//用户角色名称
					// •privilege_id: "1"//用户权限ID （1:管理员权限 2:财务权限 3:查看权限 。
					// 目前判断这个是否为1 就能判断用户是否是该协会的管理员，如果不存在权限id返回字符串 noRoleid）
					name = clubInfoOBj.optString("name", "");
					department = clubInfoOBj.optString("department", "");
					corp_id = clubInfoOBj.optString("corp_id", "");
					manage_office_name = clubInfoOBj.optString("manage_office_name", "");
					privilege_id = clubInfoOBj.optString("privilege_id", "");
					corp_name = clubInfoOBj.optString("corp_name", "");
					corp_head_title = clubInfoOBj.optString("corp_head_title", "");
				}

				UserLoginInfo loginInfo = new UserLoginInfo(uid, sid, mobile, ablum, loginName, nickName, realName, sex,
						qq, email, nameChangeTimes, bid, bname, name, department, corp_id, manage_office_name,
						privilege_id, corp_name, deptId, deptName);
				loginInfo.setCorp_head_title(corp_head_title);
				loginInfo.setRanked_show(ranked_show);
				loginInfo.setCompany_title(company_title);
				loginInfo.setActive_url(active_url);
				loginInfo.setDepartment_title(department_title);
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
