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
import com.bestdo.bestdoStadiums.utils.parser.CampaignDetailParser;
import com.bestdo.bestdoStadiums.utils.parser.CampaignGetClubParser;
import com.bestdo.bestdoStadiums.utils.parser.CampaignPublishParser;
import com.bestdo.bestdoStadiums.utils.parser.ClubGetManageParser;
import com.bestdo.bestdoStadiums.utils.parser.GetVersonParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:50:03
 * @Description 类描述：获取管理员管理的俱乐部API
 */
public class ClubGetManageBusiness {

	public interface ClubGetManageCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private ClubGetManageCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public ClubGetManageBusiness(Context mContext, HashMap<String, String> mhashmap,
			ClubGetManageCallback mGetDataCallback) {
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.mGetDataCallback = mGetDataCallback;
		getDate();

	}

	private void getDate() {
		System.err.println(mhashmap);
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.CLUBGETMANEGE,
				new Listener<String>() {
					public void onResponse(String response) {
						System.err.println(response);
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						ClubGetManageParser mParser = new ClubGetManageParser();
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
