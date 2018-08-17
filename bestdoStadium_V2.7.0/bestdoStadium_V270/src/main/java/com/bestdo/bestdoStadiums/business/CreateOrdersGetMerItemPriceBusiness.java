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
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-27 下午5:21:38
 * @Description 类描述：创建订单获取商品明细的价格API
 */
public class CreateOrdersGetMerItemPriceBusiness {

	public interface GetMerItemPriceCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetMerItemPriceCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public CreateOrdersGetMerItemPriceBusiness(Context mContext, HashMap<String, String> mhashmap,
			GetMerItemPriceCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.CREATEORDERMERTEMPRICE;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				CreateOrdersGetMerItemPriceParser mParser = new CreateOrdersGetMerItemPriceParser();
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
