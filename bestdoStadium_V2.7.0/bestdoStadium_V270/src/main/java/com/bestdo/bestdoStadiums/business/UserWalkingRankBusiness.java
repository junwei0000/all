package com.bestdo.bestdoStadiums.business;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserWalkingRankInfo;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.StadiumParser;
import com.bestdo.bestdoStadiums.utils.parser.UserWalkingRankParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-27 下午5:49:05
 * @Description 类描述：计步排行
 */
public class UserWalkingRankBusiness {

	public interface GetUserWalkingRankCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	ArrayList<UserWalkingRankInfo> mbestdoList;
	private GetUserWalkingRankCallback mGetDataCallback;
	HashMap<String, String> mHashMap;
	Context mContext;
	Boolean beatdoSelectStatus;

	public UserWalkingRankBusiness(Context mContext, Boolean beatdoSelectStatus, HashMap<String, String> mHashMap,
			ArrayList<UserWalkingRankInfo> mbestdoList, GetUserWalkingRankCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		this.mbestdoList = mbestdoList;
		this.beatdoSelectStatus = beatdoSelectStatus;
		this.mHashMap = mHashMap;
		getDate();
	}

	private void getDate() {
		String pathString;
		if (beatdoSelectStatus) {
			pathString = Constans.USERWALKINGRANK;
		} else {
			pathString = Constans.USERWALKINGRANKBUSINESS;
		}
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, pathString, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("response", response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				UserWalkingRankParser mParser = new UserWalkingRankParser(mbestdoList);
				dataMap = mParser.parseJSON(jsonObject);
				mGetDataCallback.afterDataGet(dataMap);
				mParser = null;
				response = null;
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				mGetDataCallback.afterDataGet(null);
			}
		}) {
			@Override
			protected HashMap<String, String> getParams() {
				return mHashMap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
