package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.BannerImgParser;
import com.bestdo.bestdoStadiums.utils.parser.MainSprotsParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 首页加载运动类型
 * 
 * @author qyy
 * 
 */
public class GetMainSportsBusiness {

	public interface GetMainSportsCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetMainSportsCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public GetMainSportsBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetMainSportsCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.GET, Constans.GETMAINSPORTS,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.e("GetMainSportsBusiness", response);
						CommonUtils.getInstance().updateCacheFile(mContext, response, "MainSportsJsonCaChe");
						setPost(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("aa", "get请求失败" + volleyError.toString());
						String responsecache = CommonUtils.getInstance().getCacheFile(mContext, "MainSportsJsonCaChe");
						if (TextUtils.isEmpty(responsecache)) {
							mGetDataCallback.afterDataGet(null);
						} else {
							setPost(responsecache);
						}
					}
				});
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}

	/**
	 * @param response
	 */
	protected void setPost(String response) {
		JSONObject jsonObject = RequestUtils.String2JSON(response);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
		MainSprotsParser mParser = new MainSprotsParser();
		dataMap = mParser.parseJSON(jsonObject);
		mGetDataCallback.afterDataGet(dataMap);
		mParser = null;
		response = null;
	}
}
