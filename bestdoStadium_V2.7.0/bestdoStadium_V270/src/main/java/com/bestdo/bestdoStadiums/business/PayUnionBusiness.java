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
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.PayParser;
import com.bestdo.bestdoStadiums.utils.parser.PayUnionParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 银联支付
 * 
 * @author jun
 * 
 */
public class PayUnionBusiness {

	public interface GetPayUnionCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetPayUnionCallback mGetDataCallback;
	private String url;
	HashMap<String, String> mhashmap;
	Context mContext;

	public PayUnionBusiness(Context mContext, HashMap<String, String> mhashmap, String url,
			GetPayUnionCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.url = url;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, url, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("response", response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				PayUnionParser mParser = new PayUnionParser();
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
