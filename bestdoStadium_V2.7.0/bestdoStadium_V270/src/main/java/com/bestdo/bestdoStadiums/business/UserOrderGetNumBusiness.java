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
import com.bestdo.bestdoStadiums.utils.parser.UserOrderDetailsParser;
import com.bestdo.bestdoStadiums.utils.parser.UserOrderGetNumParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-18 下午5:01:20
 * @Description 类描述：获取订单数量
 */
public class UserOrderGetNumBusiness {

	public interface GetUserOrderGetNumCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserOrderGetNumCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public UserOrderGetNumBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetUserOrderGetNumCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {

		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.GETORDERNUM, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				UserOrderGetNumParser mParser = new UserOrderGetNumParser();
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
