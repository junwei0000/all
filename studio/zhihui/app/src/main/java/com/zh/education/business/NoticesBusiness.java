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
import com.zh.education.model.NoticesInfo;
import com.zh.education.utils.Constans;
import com.zh.education.utils.RequestUtils;
import com.zh.education.utils.parser.NoticesParser;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:06:33
 * @Description 类描述：消息通知
 */
public class NoticesBusiness {

	public interface GetNoticesCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetNoticesCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	ArrayList<NoticesInfo> mList;
	Context mContext;

	public NoticesBusiness(Context mContext, ArrayList<NoticesInfo> mList,
			HashMap<String, String> mhashmap,
			GetNoticesCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mList = mList;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.NOTICES;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("JsonObjectRequest----", response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						NoticesParser mParser = new NoticesParser(mList);
						JSONObject jsonObject = RequestUtils
								.String2JSON(response);
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
