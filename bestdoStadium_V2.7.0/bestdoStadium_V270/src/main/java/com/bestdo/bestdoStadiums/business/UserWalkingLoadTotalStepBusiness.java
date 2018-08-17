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
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.StadiumParser;
import com.bestdo.bestdoStadiums.utils.parser.UserWalkingRankParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-27 下午5:49:05
 * @Description 类描述：上传步数
 */
public class UserWalkingLoadTotalStepBusiness {

	public interface GetUserWalkingLoadTotalStepCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserWalkingLoadTotalStepCallback mGetDataCallback;
	HashMap<String, String> mHashMap;
	Context mContext;

	public UserWalkingLoadTotalStepBusiness(Context mContext, HashMap<String, String> mHashMap,
			GetUserWalkingLoadTotalStepCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		this.mHashMap = mHashMap;
		getDate();
	}

	private void getDate() {
		String pathString = Constans.USERWALKINGLOADSTEP;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, pathString, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("response", response);
				mGetDataCallback.afterDataGet(null);
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
