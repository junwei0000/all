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
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.CampaignRegularListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.parser.CampaignListParser;
import com.bestdo.bestdoStadiums.utils.parser.CampaignRegularListParser;
import com.bestdo.bestdoStadiums.utils.parser.UserCollectParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午4:27:57
 * @Description 类描述：
 */
public class CampaignRegularListBusiness {

	public interface GetCampaignRegularListCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	ArrayList<CampaignRegularListInfo> mList;
	private GetCampaignRegularListCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public CampaignRegularListBusiness(Context mContext, ArrayList<CampaignRegularListInfo> mList,
			HashMap<String, String> mhashmap, GetCampaignRegularListCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.mhashmap = mhashmap;
		this.mList = mList;
		this.mContext = mContext;
		getDate();
	}

	private void getDate() {
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.CAMPAIGNREGULARLIST,
				new Listener<String>() {
					public void onResponse(String response) {
						System.err.println(response);
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						CampaignRegularListParser mParser = new CampaignRegularListParser(mList);
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
