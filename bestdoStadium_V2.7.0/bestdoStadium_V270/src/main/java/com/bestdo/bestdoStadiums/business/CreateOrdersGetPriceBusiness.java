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
import com.bestdo.bestdoStadiums.utils.parser.CreateOrdersGetDefautCardParser;
import com.bestdo.bestdoStadiums.utils.parser.CreateOrdersGetMerItemPriceParser;
import com.bestdo.bestdoStadiums.utils.parser.CreateOrdersGetPriceParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author
 * @date
 * @Description 类描述：创建订单获取价格API
 */
public class CreateOrdersGetPriceBusiness {

	public interface CreateOrdersGetPriceCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private CreateOrdersGetPriceCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public CreateOrdersGetPriceBusiness(Context mContext, HashMap<String, String> mhashmap,
			CreateOrdersGetPriceCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.CREATEORDERGETPRICE;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				CreateOrdersGetPriceParser mParser = new CreateOrdersGetPriceParser();
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
