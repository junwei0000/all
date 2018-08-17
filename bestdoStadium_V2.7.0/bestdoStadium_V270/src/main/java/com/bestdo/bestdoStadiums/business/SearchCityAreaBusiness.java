package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.SearchCityAreaParser;
import com.bestdo.bestdoStadiums.utils.parser.SearchCityParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-7-21 下午3:56:19
 * @Description 类描述：城市区域json文件
 */

public class SearchCityAreaBusiness {

	public interface GetCityAreaCallback {
		public void afterDataGet(String jsonAreaStr);
	}

	HashMap<String, String> mhashmap;
	private GetCityAreaCallback mGetDataCallback;
	Context mContext;

	public SearchCityAreaBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetCityAreaCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		this.mhashmap = mhashmap;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.CITYLISTAREA,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("/getCityData", response);
						mGetDataCallback.afterDataGet(response);
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						mGetDataCallback.afterDataGet(null);
					}
				}) {

			@Override
			protected HashMap<String, String> getParams() {
				return mhashmap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
