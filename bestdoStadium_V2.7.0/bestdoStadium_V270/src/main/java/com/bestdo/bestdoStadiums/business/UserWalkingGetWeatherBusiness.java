package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.BannerImgParser;
import com.bestdo.bestdoStadiums.utils.parser.PayParser;
import com.bestdo.bestdoStadiums.utils.parser.UserOrderGetSportParser;
import com.bestdo.bestdoStadiums.utils.parser.StadiumParser;
import com.bestdo.bestdoStadiums.utils.parser.UserWalkingGetWeatherParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 个人中心切换运动类型
 * 
 * @author qyy
 * 
 */
public class UserWalkingGetWeatherBusiness {

	public interface UserWalkingGetWeatherCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private UserWalkingGetWeatherCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public UserWalkingGetWeatherBusiness(Context mContext, HashMap<String, String> mhashmap,
			UserWalkingGetWeatherCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		this.mhashmap = mhashmap;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.GETWEATHER,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.e("response", response);
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						UserWalkingGetWeatherParser mParser = new UserWalkingGetWeatherParser();
						dataMap = mParser.parseJSON(jsonObject);
						mGetDataCallback.afterDataGet(dataMap);
						mParser = null;
						response = null;

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

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
