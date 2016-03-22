package com.zh.education.business;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.zh.education.model.BoKeInfo;
import com.zh.education.model.EventsInfo;
import com.zh.education.utils.Constans;
import com.zh.education.utils.RequestUtils;
import com.zh.education.utils.parser.BoKeParser;
import com.zh.education.utils.parser.EventsParser;
import com.zh.education.utils.parser.UserLoginParser;

/**
 * 
 * @author 作者：qyy
 * @date 
 * @Description
 */
public class EventsBusiness {

	public interface EventsCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private EventsCallback mBoKeCallback;
	private ArrayList<EventsInfo>  mList;
	HashMap<String, String> mhashmap;
	Context mContext;

	public EventsBusiness(Context mContext, HashMap<String, String> mhashmap,
			ArrayList<EventsInfo> mList, EventsCallback mBoKeCallback) {
		this.mBoKeCallback = mBoKeCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.mList = mList;
		getDate();
	}

	private void getDate() {
		String path = Constans.EVENTS;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("JsonObjectRequest----", response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						EventsParser mParser = new EventsParser(mList);
						
						JSONObject jsonObject = RequestUtils
								.String2JSON(response);
						dataMap = mParser.parseJSON(jsonObject);

						mBoKeCallback.afterDataGet(dataMap);
						mParser = null;
						jsonObject = null;
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						mBoKeCallback.afterDataGet(null);
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
