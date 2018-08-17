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
import com.bestdo.bestdoStadiums.model.UserCardsDetaiShouZhilInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.UserCardsDetailLoadShouZhiParser;
import com.bestdo.bestdoStadiums.utils.parser.UserCardsDetailParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-10 上午11:49:10
 * @Description 类描述：用户卡详情加载收支记录
 */
public class UserCardsDetailLoadShouZhiBusiness {

	public interface GetUserCardsDetailLoadShouZhiCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetUserCardsDetailLoadShouZhiCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;
	private ArrayList<UserCardsDetaiShouZhilInfo> mList;

	public UserCardsDetailLoadShouZhiBusiness(Context mContext, HashMap<String, String> mhashmap,
			ArrayList<UserCardsDetaiShouZhilInfo> mList, GetUserCardsDetailLoadShouZhiCallback mGetDataCallback

	) {
		this.mGetDataCallback = mGetDataCallback;
		this.mContext = mContext;
		this.mhashmap = mhashmap;
		this.mList = mList;
		getDate();
	}

	private void getDate() {
		String path = Constans.CARDDETAILLOADSHOUZHI;
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				UserCardsDetailLoadShouZhiParser mParser = new UserCardsDetailLoadShouZhiParser(mList);
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
