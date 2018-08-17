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
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 首页Banner图片
 * 
 * @author qyy
 * 
 */
public class GetBannerImgBusiness {

	public interface GetBannerImgCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetBannerImgCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public GetBannerImgBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetBannerImgCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.GET, Constans.GETBANNERIMG,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.e("response", response);
						CommonUtils.getInstance().updateCacheFile(mContext, response, "BannerImgJsonCaChe");
						setPost(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("aa", "get请求失败" + volleyError.toString());
						String responsecache = CommonUtils.getInstance().getCacheFile(mContext, "BannerImgJsonCaChe");
						if (TextUtils.isEmpty(responsecache)) {
							mGetDataCallback.afterDataGet(null);
						} else {
							setPost(responsecache);
						}
					}
				});
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}

	private void setPost(String response) {
		JSONObject jsonObject = RequestUtils.String2JSON(response);
		HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
		BannerImgParser mParser = new BannerImgParser();
		dataMap = mParser.parseJSON(jsonObject);
		mGetDataCallback.afterDataGet(dataMap);
		mParser = null;
		response = null;
	}
}
