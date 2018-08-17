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
import com.bestdo.bestdoStadiums.utils.parser.BusinessBannerImgParser;
import com.bestdo.bestdoStadiums.utils.parser.PayParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-8-18 上午10:38:07
 * @Description 类描述：企业版首页Banner图片
 */
public class GetBusinessBannerImgBusiness {

	public interface GetBusinessBannerImgCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetBusinessBannerImgCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public GetBusinessBannerImgBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetBusinessBannerImgCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.GET, Constans.GETBUSINESSBANNERIMG,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.e("response", response);
						CommonUtils.getInstance().updateCacheFile(mContext, response, "BusinessBannerImgJsonCaChe");
						setPost(response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Log.i("aa", "get请求失败" + volleyError.toString());
						String responsecache = CommonUtils.getInstance().getCacheFile(mContext,
								"BusinessBannerImgJsonCaChe");
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
		BusinessBannerImgParser mParser = new BusinessBannerImgParser();
		dataMap = mParser.parseJSON(jsonObject);
		mGetDataCallback.afterDataGet(dataMap);
		mParser = null;
		response = null;
	}
}
