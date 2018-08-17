package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserGetCodeParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午4:31:49
 * @Description 类描述：获取验证码-快速登录、注册、找回密码、第三方绑定账号
 */
public class UserGetCodeBusiness {

	public interface GetGetCodeCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetGetCodeCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
	String invokingType;

	public UserGetCodeBusiness(Context mContext, HashMap<String, String> mhashmap, String invokingType,
			GetGetCodeCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.invokingType = invokingType;
		this.mContext = mContext;

		getDate();
	}

	private void getDate() {

		String path = "";
		if (invokingType.equals(Constans.getInstance().invokingTypeByFastLogin)) {
			path = Constans.FASTLOGINGETCODE;
		} else if (invokingType.equals(Constans.getInstance().invokingTypeByRegist)) {
			path = Constans.REGISTGETCODE;
		} else if (invokingType.equals(Constans.getInstance().invokingTypeByFindphone)) {
			path = Constans.FINDPASSWORDGETCODE;
		} else if (invokingType.equals(Constans.getInstance().invokingTypeByBindMobile)) {
			path = Constans.BINDMOBILEGETCODE;
		} else if (invokingType.equals(Constans.getInstance().invokingTypeByBindMobileByOldUser)) {
			path = Constans.BINDMOBILEGETCODEBYOLD;
		}
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println("response==========" + response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				UserGetCodeParser mParser = new UserGetCodeParser();
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
