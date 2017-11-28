package com.KiwiSports.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.parser.UserAccountUpdateParser;
import com.KiwiSports.utils.volley.RequestUtils;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:06:33
 * @Description 类描述：用户 修改个人信息
 */
public class UserAccountUpdateBusiness {

	public interface GetAccountUpdateCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetAccountUpdateCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
	String updatetype;
	public UserAccountUpdateBusiness(Context mContext,String updatetype,  HashMap<String, String> mhashmap,
			GetAccountUpdateCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.updatetype = updatetype;
		getDate();
	}

	private void getDate() {
		String path;
		if(updatetype.equals("info")){
			path=Constans.USERUPDATEINFO;
		}else{
			path=Constans.USERUPDATEABLUMSAVE;
		}
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST,path , new Listener<String>() {
			public void onResponse(String response) {
				Log.e("decrypt----", response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				UserAccountUpdateParser mParser = new UserAccountUpdateParser();
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
			protected HashMap<String, String> getParams() {
				return mhashmap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
