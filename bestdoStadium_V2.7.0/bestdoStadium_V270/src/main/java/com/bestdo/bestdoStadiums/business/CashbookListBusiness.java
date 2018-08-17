package com.bestdo.bestdoStadiums.business;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.model.CashbookListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.CashbookListParser;
import com.bestdo.bestdoStadiums.utils.parser.StadiumParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2017-1-3 下午2:40:33
 * @Description 类描述：
 */
public class CashbookListBusiness {

	public interface GetCashbookListCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetCashbookListCallback mGetDataCallback;
	HashMap<String, String> mHashMap;
	ArrayList<CashbookListInfo> mList;
	Context mContext;

	public CashbookListBusiness(Context mContext, HashMap<String, String> mHashMap, ArrayList<CashbookListInfo> mList,
			GetCashbookListCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mList = mList;
		this.mContext = mContext;
		this.mHashMap = mHashMap;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.GETCASHBOOKLIST,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("response", response);
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						CashbookListParser mParser = new CashbookListParser(mList);
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
				return mHashMap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
