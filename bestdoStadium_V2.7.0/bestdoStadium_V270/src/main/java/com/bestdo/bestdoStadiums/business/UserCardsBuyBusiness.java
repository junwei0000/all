package com.bestdo.bestdoStadiums.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.model.UserCardsBuyInfo;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserCardsBuyParser;
import com.bestdo.bestdoStadiums.utils.parser.UserCardsParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-5-5 下午5:05:06
 * @Description 类描述：百动卡购买
 */
public class UserCardsBuyBusiness {

	public interface GetUserCardsCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserCardsCallback mGetDataCallback;
	private ArrayList<UserCardsBuyInfo> mList;
	HashMap<String, String> mhashmap;
	Context mContext;

	public UserCardsBuyBusiness(Context mContext, HashMap<String, String> mhashmap, ArrayList<UserCardsBuyInfo> mList,
			GetUserCardsCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mList = mList;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String url = Constans.USERCARDSBUY;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, url, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("JsonObjectRequest----", response.toString());
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				UserCardsBuyParser mParser = new UserCardsBuyParser(mList);
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
