package com.bestdo.bestdoStadiums.business;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.StadiumParser;
import com.bestdo.bestdoStadiums.utils.parser.StadiumRecommendParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 首页推荐场馆
 * 
 * @author qyy
 * 
 */
public class StadiumRecommendBusiness {

	public interface GetStadiumCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetStadiumCallback mGetDataCallback;
	HashMap<String, String> mHashMap;
	ArrayList<StadiumInfo> mList;
	Context mContext;

	public StadiumRecommendBusiness(Context mContext, HashMap<String, String> mHashMap, ArrayList<StadiumInfo> mList,
			GetStadiumCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mList = mList;
		this.mContext = mContext;
		this.mHashMap = mHashMap;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.GETRECOMMENDSTADIUMLIST,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("response", response);
						setPost(response, mList);
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

	public void loadCacheData(ArrayList<StadiumInfo> mList, String responsecache) {
		if (TextUtils.isEmpty(responsecache)) {
			mGetDataCallback.afterDataGet(null);
		} else {
			setPost(responsecache, mList);
		}
	}

	/**
	 * @param response
	 */
	protected void setPost(String response, ArrayList<StadiumInfo> mList) {
		JSONObject jsonObject = RequestUtils.String2JSON(response);
		if (jsonObject != null && jsonObject.length() > 0) {
			CommonUtils.getInstance().updateCacheFile(mContext, response, "StadiumRecommendJsonCaChe");
		}
		HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
		StadiumRecommendParser mParser = new StadiumRecommendParser(mList);
		dataMap = mParser.parseJSON(jsonObject);
		mGetDataCallback.afterDataGet(dataMap);
		mParser = null;
		response = null;
	}
}
