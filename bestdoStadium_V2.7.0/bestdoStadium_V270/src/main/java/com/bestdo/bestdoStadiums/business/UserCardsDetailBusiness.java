package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserCardsDetailParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 用户卡详情
 * 
 * @author qyy
 * 
 */
public class UserCardsDetailBusiness {

	public interface GetUserCardsDetailCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserCardsDetailCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	String getCardDetailInfoFromStats;
	Context mContext;

	public UserCardsDetailBusiness(Context mContext, HashMap<String, String> mhashmap,
			String getCardDetailInfoFromStats, GetUserCardsDetailCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.getCardDetailInfoFromStats = getCardDetailInfoFromStats;
		this.mContext = mContext;
		this.mhashmap = mhashmap;
		getDate();
	}

	private void getDate() {
		String path;
		if (getCardDetailInfoFromStats.equals(Constans.getInstance().getCardDetailInfoByAbstractPage)) {
			path = Constans.CARDJIHUO;
		} else {
			path = Constans.CARDDETAIL;
		}
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				UserCardsDetailParser mParser = new UserCardsDetailParser(getCardDetailInfoFromStats);
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
