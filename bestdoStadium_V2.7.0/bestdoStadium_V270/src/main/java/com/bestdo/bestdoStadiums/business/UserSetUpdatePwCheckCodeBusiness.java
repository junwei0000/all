package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.business.UserRegistCheckCodeBusiness.GetUserRegistCheckCodeCallback;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserFindPWParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-16 下午2:25:52
 * @Description 类描述：用户设置-修改密码验证验码
 */
public class UserSetUpdatePwCheckCodeBusiness {

	public interface GetUserSetUpdatePwCheckCodeCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserSetUpdatePwCheckCodeCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
	private String path;

	public UserSetUpdatePwCheckCodeBusiness(Context mContext, HashMap<String, String> mhashmap, String path,
			GetUserSetUpdatePwCheckCodeCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.path = path;
		getDate();
	}

	private void getDate() {

		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				UserFindPWParser mParser = new UserFindPWParser();
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
