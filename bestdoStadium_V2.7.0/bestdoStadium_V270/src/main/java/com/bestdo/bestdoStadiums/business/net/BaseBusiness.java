package com.bestdo.bestdoStadiums.business.net;

import java.util.Arrays;
import java.util.Map;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.utils.Config;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;
import com.google.gson.Gson;

public class BaseBusiness {

	private Function function;
	private IBusiness callBack;
	private Map<String, String> mhashmap;

	public BaseBusiness(Map<String, String> mhashmap, Function function, IBusiness callBack) {
		this.mhashmap = mhashmap;
		this.function = function;
		this.callBack = callBack;
		getDate(function.getUrl());
	}

	private void getDate(String url) {
		Config.d(url, mhashmap);
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, url, new Listener<String>() {
			public void onResponse(String response) {
				try {
					Config.d("back=" + response);
					Object json = new Gson().fromJson(response, function.getResponceTypeToken().getType());
					if (callBack != null)
						callBack.onSuccess((BaseResponse) json);
				} catch (Exception e) {
					e.printStackTrace();
					if (callBack != null)
						callBack.onError(new VolleyError("接口异常"));
				}
			}
		}, new Response.ErrorListener() {
			public void onErrorResponse(VolleyError error) {
				Config.d("backError=" + error.getMessage());
				if (callBack != null)
					callBack.onError(error);
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				return mhashmap;
			}
		};
		RequestUtils.addRequest(mJsonObjectRequest, null);
	}
}
