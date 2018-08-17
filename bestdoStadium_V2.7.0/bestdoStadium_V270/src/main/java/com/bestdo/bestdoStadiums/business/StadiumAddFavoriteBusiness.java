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
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.StadiumAddFavoriteParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 添加场馆收藏
 * 
 * @author jun
 * 
 */
public class StadiumAddFavoriteBusiness {

	public interface GetStadiumAddFavoriteCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetStadiumAddFavoriteCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public StadiumAddFavoriteBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetStadiumAddFavoriteCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.STADIUMADDFAVORITE;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("JsonObjectRequest----", response.toString());
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				StadiumAddFavoriteParser mParser = new StadiumAddFavoriteParser();
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
				return mhashmap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, mContext);

	}
}
