package com.bestdo.bestdoStadiums.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserCardsParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 用户卡列表
 * 
 * @author qyy
 * 
 */
public class UserCardsBusiness {

	public interface GetUserCardsCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserCardsCallback mGetDataCallback;
	private ArrayList<UserCardsInfo> mList;
	HashMap<String, String> mhashmap;
	Context mContext;
	private String url;

	public UserCardsBusiness(Context mContext, HashMap<String, String> mhashmap, ArrayList<UserCardsInfo> mList,
			String url, GetUserCardsCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mList = mList;
		this.mContext = mContext;
		this.url = url;
		getDate();
	}

	private void getDate() {
		// String url = Constans.USERCARDSLIST;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, url, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("JsonObjectRequest----", response.toString());
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				UserCardsParser mParser = new UserCardsParser(mList);
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
