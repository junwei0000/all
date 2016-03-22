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
import com.zh.education.model.PingLunInfo;
import com.zh.education.utils.Constans;
import com.zh.education.utils.RequestUtils;
import com.zh.education.utils.parser.PingLunParser;

/**
 * 
 * @author 作者：qyy
 * @date 创建时间：2016-1-15 下午6:06:33
 * @Description 类描述：全部评论
 */
public class BoKeCommentsBusiness {

	public interface PingLunCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private PingLunCallback pingLunCallback;
	private ArrayList<PingLunInfo> mList;
	HashMap<String, String> mhashmap;
	Context mContext;

	public BoKeCommentsBusiness(Context mContext,
			HashMap<String, String> mhashmap, ArrayList<PingLunInfo> mList,
			PingLunCallback pingLunCallback2) {
		super();
		this.pingLunCallback = pingLunCallback2;
		this.mList = mList;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.GETCOMMENTS;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("JsonObjectRequest----", response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						PingLunParser mParser = new PingLunParser(mList);
						JSONObject jsonObject = RequestUtils
								.String2JSON(response);
						dataMap = mParser.parseJSON(jsonObject);

						pingLunCallback.afterDataGet(dataMap);
						mParser = null;
						jsonObject = null;
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						pingLunCallback.afterDataGet(null);
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
