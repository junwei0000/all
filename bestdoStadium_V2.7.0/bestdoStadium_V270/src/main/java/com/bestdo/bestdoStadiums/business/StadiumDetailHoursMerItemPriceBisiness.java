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
import com.bestdo.bestdoStadiums.utils.parser.StadiumDetailGetOneDayHourMerItemPriceParser;
import com.bestdo.bestdoStadiums.utils.parser.StadiumDetailMerItemPriceParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-24 下午8:13:25
 * @Description 类描述：某天指定小时商品明细的价格API(高尔夫练习场游泳健身)
 */
public class StadiumDetailHoursMerItemPriceBisiness {

	public interface GetHoursMerItemPriceCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetHoursMerItemPriceCallback mGetDataCallback;
	HashMap<String, String> mHashMap;
	Context mContext;

	public StadiumDetailHoursMerItemPriceBisiness(Context mContext, HashMap<String, String> mHashMap,
			GetHoursMerItemPriceCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mHashMap = mHashMap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.GETONEDAYHOURSMERITEMPRICE,
				new Listener<String>() {
					public void onResponse(String response) {
						Log.e("JsonObjectRequest----", response.toString());
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						StadiumDetailGetOneDayHourMerItemPriceParser mParser = new StadiumDetailGetOneDayHourMerItemPriceParser();
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
