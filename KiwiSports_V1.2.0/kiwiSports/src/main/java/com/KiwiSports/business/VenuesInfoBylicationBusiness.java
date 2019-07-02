package com.KiwiSports.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.parser.VenuesInfoByLocationParser;
import com.KiwiSports.utils.volley.RequestUtils;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:26:10
 * @Description 类描述：
 */
public class VenuesInfoBylicationBusiness {

	public interface GetInfoBylicationCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetInfoBylicationCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public VenuesInfoBylicationBusiness(Context mContext,
			HashMap<String, String> mhashmap,
			GetInfoBylicationCallback mGetDataCallback) {
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.mGetDataCallback = mGetDataCallback;
		getDate();

	}

	private void getDate() {
		System.err.println(mhashmap);
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST,
				Constants.VENUESINFOBYLOCATION, new Listener<String>() {
					public void onResponse(String response) {
						Log.e("TESTLOG", "-------getPosid-business---------"
								+ response);
						JSONObject jsonObject = RequestUtils
								.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						VenuesInfoByLocationParser mParser = new VenuesInfoByLocationParser();
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
				return mhashmap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
