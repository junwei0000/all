package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.StadiumDetailGetOneDayMerItemPriceParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-24 下午8:14:18
 * @Description 类描述：商品明细一天的报价API（pylw）
 */
public class StadiumDetailOneDayMerItemPriceBisiness {

	public interface OneDayMerItemPriceCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private OneDayMerItemPriceCallback mGetDataCallback;
	private HashMap<String, String> mHashMap;
	private Context mContext;
	private String inventory_type;

	public StadiumDetailOneDayMerItemPriceBisiness(Context mContext, HashMap<String, String> mHashMap,
			String inventory_type, OneDayMerItemPriceCallback mGetDataCallback) {
		this.mContext = mContext;
		this.mHashMap = mHashMap;
		this.inventory_type = inventory_type;
		this.mGetDataCallback = mGetDataCallback;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.GETONEDAYMERITEMPRICE,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("JsonObjectRequest----", response.toString());
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						StadiumDetailGetOneDayMerItemPriceParser mParser = new StadiumDetailGetOneDayMerItemPriceParser(
								inventory_type);
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
			protected Map<String, String> getParams() {
				return mHashMap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);
	}
}
