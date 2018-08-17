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
import com.bestdo.bestdoStadiums.utils.parser.CampaignBaomingListParser;
import com.bestdo.bestdoStadiums.utils.parser.CampaignGetClubParser;
import com.bestdo.bestdoStadiums.utils.parser.CashMyClubParser;
import com.bestdo.bestdoStadiums.utils.parser.ClubEventsParser;
import com.bestdo.bestdoStadiums.utils.parser.GetVersonParser;
import com.bestdo.bestdoStadiums.utils.parser.StadiumAddFavoriteParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午3:50:03
 * @Description 类描述：
 */
public class CashSetYearBudgetBusiness {

	public interface CashSetYearBudgetCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private CashSetYearBudgetCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	Context mContext;

	public CashSetYearBudgetBusiness(Context mContext, HashMap<String, String> mhashmap,
			CashSetYearBudgetCallback mGetDataCallback) {
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.mGetDataCallback = mGetDataCallback;
		getDate();

	}

	private void getDate() {
		System.err.println(mhashmap);
		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, Constans.SETYEARBUDGETLIST,
				new Listener<String>() {
					public void onResponse(String response) {
						System.err.println(response);
						JSONObject jsonObject = RequestUtils.String2JSON(response);
						HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
						StadiumAddFavoriteParser mParser = new StadiumAddFavoriteParser();
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
