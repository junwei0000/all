package com.KiwiSports.business;

import java.util.HashMap;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.parser.RecordListParser;
import com.KiwiSports.utils.parser.UserLoginParser;
import com.KiwiSports.utils.parser.VenuesListParser;
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
 * @Description 类描述：场地列表
 */
public class VenuesBusiness {

	public interface GetVenuesCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetVenuesCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
	private SharedPreferences bestDoInfoSharedPrefs;
	private Editor mEdit;

	public VenuesBusiness(Context mContext, HashMap<String, String> mhashmap, GetVenuesCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		bestDoInfoSharedPrefs = CommonUtils.getInstance().getBestDoInfoSharedPrefs(mContext);
		mEdit = bestDoInfoSharedPrefs.edit();
		getDate();
	}

	private void getDate() {
		String path = Constans.VENUESLIST;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("TESTLOG", "------------response------------" + response);
				mEdit.putString("venuesresponsedata", response);
				mEdit.commit();
				dats(response);

			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				Log.e("TESTLOG", "------------error------------" + error);
				String response = bestDoInfoSharedPrefs.getString("venuesresponsedata", "");
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
		VenuesListParser mParser = new VenuesListParser();
		JSONObject jsonObject = RequestUtils.String2JSON(response);
		dataMap = mParser.parseJSON(jsonObject);

		mGetDataCallback.afterDataGet(dataMap);
		mParser = null;
		jsonObject = null;
	}
}
