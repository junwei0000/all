package com.zongyu.elderlycommunity.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.zongyu.elderlycommunity.utils.Constans;
import com.zongyu.elderlycommunity.utils.parser.GetVersonParser;
import com.zongyu.elderlycommunity.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:26:10
 * @Description 类描述：获取版本更新
 */
public class GetVersonBusiness {

	public interface GetVersonCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetVersonCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
//Context mContext;
//Context mContext;
	public GetVersonBusiness(Context mContext,
			HashMap<String, String> mhashmap, GetVersonCallback mGetDataCallback) {
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.mGetDataCallback = mGetDataCallback;
		getDate();

	}

	private void getDate() {
		System.err.println(mhashmap);
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST,
				Constans.GETVERSON, new Listener<String>() {
					public void onResponse(String response) {
						System.err.println(response);
						JSONObject jsonObject = RequestUtils
								.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						GetVersonParser mParser = new GetVersonParser();
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
