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
import com.bestdo.bestdoStadiums.utils.parser.SearchCityParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-7-21 下午3:56:19
 * @Description 类描述：城市json文件
 */

public class SearchCityFileBusiness {

	public interface GetCityFileCallback {
		public void afterDataGet(HashMap<String, Object> mCityMap);
	}

	HashMap<String, String> mhashmap;
	private GetCityFileCallback mGetDataCallback;
	Context mContext;

	public SearchCityFileBusiness(Context mContext, GetCityFileCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.CITYLIST, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("/getCityData", response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				SearchCityParser mSearchCityParser = new SearchCityParser();
				HashMap<String, Object> mCityMap = mSearchCityParser.parseJSON(jsonObject);
				mSearchCityParser = null;
				mGetDataCallback.afterDataGet(mCityMap);
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
