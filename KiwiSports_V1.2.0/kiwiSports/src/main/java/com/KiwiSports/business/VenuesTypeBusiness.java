package com.KiwiSports.business;

import java.util.HashMap;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.parser.VenuesTypeParser;
import com.KiwiSports.utils.volley.RequestUtils;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:06:33
 * @Description 类描述：场地运动类型
 */
public class VenuesTypeBusiness {

	public interface GetVenuesTypeCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetVenuesTypeCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor mEdit;

	public VenuesTypeBusiness(Context mContext,
			HashMap<String, String> mhashmap,
			GetVenuesTypeCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		bestDoInfoSharedPrefs = CommonUtils.getInstance()
				.getBestDoInfoSharedPrefs(mContext);
		mEdit = bestDoInfoSharedPrefs.edit();
		getDate();
	}

	private void getDate() {
		String path = Constants.VENUESTYPE;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("TESTLOG", "------------response------------"
								+ response);
						mEdit.putString("venuesTyperesponsedata", response);
						mEdit.commit();
						dats(response);
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						Log.e("TESTLOG", "------------error------------"
								+ error);
						String response = bestDoInfoSharedPrefs.getString(
								"venuesTyperesponsedata", "");
						if (!TextUtils.isEmpty(response)) {
							dats(response);
						} else {
							mGetDataCallback.afterDataGet(null);
						}
					}
				}) {
			@Override
			protected HashMap<String, String> getParams() {
				return mhashmap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}

	private void dats(String response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		VenuesTypeParser mParser = new VenuesTypeParser();
		JSONObject jsonObject = RequestUtils.String2JSON(response);
		dataMap = mParser.parseJSON(jsonObject);

		mGetDataCallback.afterDataGet(dataMap);
		mParser = null;
		jsonObject = null;
	}
}
