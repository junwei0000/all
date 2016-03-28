package com.zh.education.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.zh.education.model.BoKeInfo;
import com.zh.education.model.UserLoginInfo;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class BoKeParser extends BaseParser<Object> {

	ArrayList<BoKeInfo> mList;

	public BoKeParser(ArrayList<BoKeInfo> mList) {
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
				JSONArray jSONArray = obj.optJSONArray("Blogs");
				for (int i = 0; i < jSONArray.length(); i++) {
					JSONObject o = jSONArray.getJSONObject(i);
					int id = o.optInt("ID");// 总数量
					String Title = o.optString("Title", "");// 名称
					String Content = o.optString("Content", "");// 详情
					String CreateUser = o.optString("CreateUser", "");// 创建人
					long CreateTime = o.optLong("CreateTime");// 创建时间
					String Categroy = o.optString("Categroy", "");// 文字的分类
					String CommentCount = o.optString("CommentCount", "");// 评论数目
					String LikeCount = o.optString("LikeCount", "");// 点赞的数目
					String HeadImg = o.optString("HeadImg", "");// 头像
					String Cover = o.optString("Cover", "");

					BoKeInfo boKeInfo = new BoKeInfo(RecordCount, id, Title,
							Content, CreateUser, CreateTime, Categroy,
							CommentCount, LikeCount, HeadImg, Cover);
					mList.add(boKeInfo);
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
