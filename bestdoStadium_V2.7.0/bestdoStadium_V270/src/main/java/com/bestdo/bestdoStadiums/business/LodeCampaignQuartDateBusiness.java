package com.bestdo.bestdoStadiums.business;

import java.util.HashMap;

import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.bestdo.bestdoStadiums.business.CreateOrdersGetPriceBusiness.CreateOrdersGetPriceCallback;
import com.bestdo.bestdoStadiums.business.PayBusiness.GetPayCallback;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.BannerImgParser;
import com.bestdo.bestdoStadiums.utils.parser.CreateOrdersGetPriceParser;
import com.bestdo.bestdoStadiums.utils.parser.LodeCampaignQuartParser;
import com.bestdo.bestdoStadiums.utils.parser.PayParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 运动圈数据
 * 
 * @author qyy
 * 
 */
public class LodeCampaignQuartDateBusiness {

	public interface LodeCampaignQuartCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private LodeCampaignQuartCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public LodeCampaignQuartDateBusiness(Context mContext, HashMap<String, String> mhashmap,
			LodeCampaignQuartCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.CLUBSERVICE;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				LodeCampaignQuartParser mParser = new LodeCampaignQuartParser();
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
