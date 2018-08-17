package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;
import java.util.Map;

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
import com.bestdo.bestdoStadiums.utils.parser.UserLoginParser;
import com.bestdo.bestdoStadiums.utils.parser.UserLoginUmengParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 用户第三方登录
 * 
 * @author qyy
 * 
 */
public class UserLoginUMengBusiness {

	public interface GetUserLoginUMengCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserLoginUMengCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public UserLoginUMengBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetUserLoginUMengCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.UMENGLOGIN;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("JsonObjectRequest----", response.toString());
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				UserLoginUmengParser mParser = new UserLoginUmengParser();
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				dataMap = mParser.parseJSON(jsonObject);
				mGetDataCallback.afterDataGet(dataMap);
				mParser = null;
				jsonObject = null;
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				mGetDataCallback.afterDataGet(null);
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				return mhashmap;
			}

		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
