package com.bestdo.bestdoStadiums.business;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserAccountUpdateAblumParser;
import com.bestdo.bestdoStadiums.utils.volley.MultiPartStringRequest;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:06:33
 * @Description 类描述：用户 修改个人信息头像
 */
public class UserAccountUpdateAblumBusiness {

	public interface GetAccountUpdateAblumCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetAccountUpdateAblumCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
	File tempFile;
	private String path;
	private String key;

	public UserAccountUpdateAblumBusiness(Context mContext, HashMap<String, String> mhashmap, File tempFile,
			String path, String key, GetAccountUpdateAblumCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.tempFile = tempFile;
		this.key = key;
		this.path = path;
		getDate();
	}

	private void getDate() {
		// final String path = Constans.USERUPDATEABLUM;
		// final String path =
		// "http://test.h5.shop.bestdo.com/order/upload_json";
		// final String path = "http://yg.h5.shop.bestdo.com/order/upload_json";
		// path="http://test.h5.shop.bestdo.com/order/upload_json?from=bestdo&v=2.6.0";
		MultiPartStringRequest mJsonObjectRequest = new MultiPartStringRequest(Request.Method.POST, path,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.e("JsonObjectRequest----", response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						UserAccountUpdateAblumParser mParser = new UserAccountUpdateAblumParser();
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						dataMap = mParser.parseJSON(jsonObject);

						mGetDataCallback.afterDataGet(dataMap);
						mParser = null;
						jsonObject = null;
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						System.err.println(arg0);
						mGetDataCallback.afterDataGet(null);
					}
				}) {

			@Override
			public Map<String, File> getFileUploads() {
				Map<String, File> dMap = new HashMap<String, File>();
				// dMap.put("file", tempFile);
				Log.e("tempFile", tempFile.toString());
				dMap.put(key, tempFile);

				return dMap;
			}

			@Override
			public Map<String, String> getStringUploads() {
				return mhashmap;
			}

		};

		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
