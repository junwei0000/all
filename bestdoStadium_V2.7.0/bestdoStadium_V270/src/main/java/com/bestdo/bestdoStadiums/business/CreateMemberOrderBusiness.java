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
import com.bestdo.bestdoStadiums.utils.parser.CreateMemberOrdersParser;
import com.bestdo.bestdoStadiums.utils.parser.CreateOrdersBuyCardParser;
import com.bestdo.bestdoStadiums.utils.parser.CreateOrdersParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-1 下午7:34:57
 * @Description 类描述：创建购买会员订单
 */
public class CreateMemberOrderBusiness {

	public interface CreateMemberOrderCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private CreateMemberOrderCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public CreateMemberOrderBusiness(Context mContext, HashMap<String, String> mhashmap,
			CreateMemberOrderCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		String path = Constans.CREATEMEMBERORDER;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				CreateMemberOrdersParser mParser = new CreateMemberOrdersParser();
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
