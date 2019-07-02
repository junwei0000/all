package com.KiwiSports.business;

import android.content.Context;
import android.util.Log;

import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.parser.InitRecordParser;
import com.KiwiSports.utils.parser.RecordDelParser;
import com.KiwiSports.utils.volley.RequestUtils;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:06:33
 * @Description 类描述：用户本地开始记录轨迹时,必须向服务器申请record_data_id,
 * 后期在上传轨迹数据时需要上传record_data_id
 */
public class InitRecordBusiness {

	public interface GetInitRecordCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetInitRecordCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public InitRecordBusiness(Context mContext,
                              HashMap<String, String> mhashmap,
							  GetInitRecordCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constants.initRecord;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("TESTLOG", "------------response------------"
								+ response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						InitRecordParser mParser = new InitRecordParser();
						JSONObject jsonObject = RequestUtils
								.String2JSON(response);
						dataMap = mParser.parseJSON(jsonObject);

						mGetDataCallback.afterDataGet(dataMap);
						mParser = null;
						jsonObject = null;
					}
				}, new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						Log.e("TESTLOG", "------------error------------"
								+ error);
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
