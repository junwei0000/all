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
import com.bestdo.bestdoStadiums.utils.parser.StadiumDetailMerItemPriceGolfParser;
import com.bestdo.bestdoStadiums.utils.parser.StadiumDetailMerItemPriceParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-24 下午8:12:00
 * @Description 类描述：商品明细七天价格和库存汇总(golf )
 */
public class StadiumDetailMerItemPriceGolfBisiness {

	public interface GetMerItemPriceGolfCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetMerItemPriceGolfCallback mGetDataCallback;
	private HashMap<String, String> mHashMap;
	private Context mContext;
	private int teetimejiange;
	private String cid;

	public StadiumDetailMerItemPriceGolfBisiness(Context mContext, HashMap<String, String> mHashMap, int teetimejiange,
			String cid, GetMerItemPriceGolfCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mHashMap = mHashMap;
		this.mContext = mContext;
		this.teetimejiange = teetimejiange;
		this.cid = cid;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.MERITEMPRICEGOLF,
				new Listener<String>() {

					public void onResponse(String response) {
						Log.e("JsonObjectRequest----", response.toString());
						HashMap<String, Object> dataMap = new HashMap<String, Object>();
						StadiumDetailMerItemPriceGolfParser mParser = new StadiumDetailMerItemPriceGolfParser(
								teetimejiange, cid);
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
