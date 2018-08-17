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
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserLoginParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:06:33
 * @Description 类描述：用户登录
 */
public class UserLoginBusiness {

	public interface GetLoginCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetLoginCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
	boolean fastLoginStatus;

	public UserLoginBusiness(Context mContext, boolean fastLoginStatus, HashMap<String, String> mhashmap,
			GetLoginCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.fastLoginStatus = fastLoginStatus;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path;
		if (fastLoginStatus) {
			path = Constans.FASTLOGIN;
		} else {
			path = Constans.LOGIN;
		}
		// HTTPSTrustManager.allowAllSSL();
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("JsonObjectRequest----", response);
				Log.e("decrypt----", response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				UserLoginParser mParser = new UserLoginParser();
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
			protected HashMap<String, String> getParams() {
				return mhashmap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
