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
import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserOrderParser;
import com.bestdo.bestdoStadiums.utils.parser.UserWalkingGetTrackParser;
import com.bestdo.bestdoStadiums.utils.parser.UserWalkinghistoryParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:28:51
 * @Description 类描述：获取轨迹
 */
public class UserWalkingGetTrackBusiness {

	public interface GetUserWalkingGetTrackCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserWalkingGetTrackCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public UserWalkingGetTrackBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetUserWalkingGetTrackCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		System.err.println(mhashmap);
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.USERWALKINGGETTRCKE,
				new Listener<String>() {
					public void onResponse(String response) {
						System.err.println(response);
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						UserWalkingGetTrackParser mParser = new UserWalkingGetTrackParser();
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
