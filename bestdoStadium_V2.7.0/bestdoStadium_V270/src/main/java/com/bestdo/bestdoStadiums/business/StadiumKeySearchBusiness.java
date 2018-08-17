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
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 场馆列表
 * 
 * @author qyy
 * 
 */
public class StadiumKeySearchBusiness {

	public interface StadiumKeySearchBusinessCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private StadiumKeySearchBusinessCallback mGetDataCallback;
	HashMap<String, String> mHashMap;
	ArrayList<StadiumInfo> mList;
	Context mContext;

	public StadiumKeySearchBusiness(Context mContext, HashMap<String, String> mHashMap, ArrayList<StadiumInfo> mList,
			StadiumKeySearchBusinessCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mList = mList;
		this.mContext = mContext;
		this.mHashMap = mHashMap;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.SEARCHKEYWORD,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("response", response);
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						StadiumParser mParser = new StadiumParser(mList);
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
