package com.bestdo.bestdoStadiums.business;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.model.UserOrdersInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserOrderParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:28:51
 * @Description 类描述：
 */
public class UserOrdersBusiness {

	public interface GetUserOrdersCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserOrdersCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	private ArrayList<UserOrdersInfo> list;
	Context mContext;

	public UserOrdersBusiness(Context mContext, HashMap<String, String> mhashmap, ArrayList<UserOrdersInfo> list,
			GetUserOrdersCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.list = list;
		getDate();
	}

	private void getDate() {
		System.err.println(mhashmap);
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.USEORDERLIST,
				new Listener<String>() {
					public void onResponse(String response) {
						System.err.println(response);
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						UserOrderParser mParser = new UserOrderParser(list);
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
