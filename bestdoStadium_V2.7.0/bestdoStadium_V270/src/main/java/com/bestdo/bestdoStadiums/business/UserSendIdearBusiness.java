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
import com.bestdo.bestdoStadiums.utils.parser.UserSendIdearParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 消息返馈
 * 编写日期:	2014-8-19
 * 作者:	 乔阳阳
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserSendIdearBusiness {

	public interface GetUserSendIdearCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserSendIdearCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public UserSendIdearBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetUserSendIdearCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {

		String path = Constans.USERSENDID;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				Log.e("JsonObjectRequest----", response.toString());
				HashMap<String, Object> dataMap = new HashMap<String, Object>();
				UserSendIdearParser mParser = new UserSendIdearParser();
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
