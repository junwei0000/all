package com.KiwiSports.business;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.utils.Constans;
import com.KiwiSports.utils.parser.RecordListParser;
import com.KiwiSports.utils.parser.UserLoginParser;
import com.KiwiSports.utils.parser.VenuesListParser;
import com.KiwiSports.utils.volley.RequestUtils;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:06:33
 * @Description 类描述：轨迹列表
 */
public class RecordListBusiness {

	public interface GetRecordListCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}
	ArrayList<RecordInfo> mlist;
	private GetRecordListCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public RecordListBusiness(Context mContext,ArrayList<RecordInfo> mlist, HashMap<String, String> mhashmap, GetRecordListCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mlist = mlist;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.RECORDLIST;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("TESTLOG", "------------response------------" + response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				RecordListParser mParser = new RecordListParser(mlist);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				dataMap = mParser.parseJSON(jsonObject);

				mGetDataCallback.afterDataGet(dataMap);
				mParser = null;
				jsonObject = null;
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				Log.e("TESTLOG", "------------error------------" + error);
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
