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
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.StadiumDetailParser;
import com.bestdo.bestdoStadiums.utils.parser.UserAccountUpdateAblumParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-23 下午5:56:07
 * @Description 类描述：上传视频地址
 */
public class UploadVedioBusiness {

	public interface GetUploadVedioCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUploadVedioCallback mGetDataCallback;
	HashMap<String, String> mHashMap;
	Context mContext;

	public UploadVedioBusiness(Context mContext, HashMap<String, String> mHashMap,
			GetUploadVedioCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mHashMap = mHashMap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.UPLOADVEDIO, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("JsonObjectRequest----", response.toString());
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				UserAccountUpdateAblumParser mParser = new UserAccountUpdateAblumParser();
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
				return mHashMap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
