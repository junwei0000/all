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
import com.bestdo.bestdoStadiums.utils.parser.CreateOrdersParser;
import com.bestdo.bestdoStadiums.utils.volley.RequestUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-1 下午7:34:57
 * @Description 类描述：创建订单
 */
public class CreateOrdersBusiness {

	public interface GetCreateOrdersCallback {
		public void afterDataGet(HashMap<String, Object> dataMap);
	}

	private GetCreateOrdersCallback mGetDataCallback;
	HashMap<String, String> mhashmap;
	String cid;
	Context mContext;
	String merid;

	public CreateOrdersBusiness(Context mContext, String cid, String merid, HashMap<String, String> mhashmap,
			GetCreateOrdersCallback mGetDataCallback) {
		this.mGetDataCallback = mGetDataCallback;
		this.cid = cid;
		this.mhashmap = mhashmap;
		this.mContext = mContext;
		this.merid = merid;
		getDate();
	}

	private void getDate() {
		String path = null;
		if (cid.equals(Constans.getInstance().sportCidGolfrange)) {
			path = Constans.CREATEORDERSPRACTICE;
		} else if (cid.equals(Constans.getInstance().sportCidGolf)) {
			path = Constans.CREATEORDERSGOLF;
		} else if (cid.equals(Constans.getInstance().sportCidFitness)) {
			path = Constans.CREATEORDERSFITNESS;
		} else if (cid.equals(Constans.getInstance().sportCidSwim)) {
			path = Constans.CREATEORDERSSWIMMING;
		} else if (cid.equals(Constans.getInstance().sportCidBadminton)) {
			path = Constans.CREATEORDERBADMINTON;
		} else if (cid.equals(Constans.getInstance().sportCidTennis)) {
			path = Constans.CREATEORDERSSWIM;
		} else if (merid.equals(Constans.getInstance().sportMeridSkiing)) {
			path = Constans.CREATEORDERSSKI;
		} else if (merid.equals(Constans.getInstance().sportMeridHighswimming)) {
			path = Constans.CREATEORDERSSWIMBOD;
		} else if (merid.equals(Constans.getInstance().sportMeridBilliards)) {
			path = Constans.CREATEORDERSBILLIAR;
		} else if (merid.equals(Constans.getInstance().sportMeridHuWai)) {
			path = Constans.CREATEORDERSOUTDOORS;
		} else if (merid.equals(Constans.getInstance().sportMeridHuWai)) {
			path = Constans.CREATEORDERSOUTDOORS;
		} else if (cid.equals(Constans.getInstance().sportBasketball)) {
			path = Constans.CREATEBASKETBALLORDER;
		} else if (cid.equals(Constans.getInstance().sportTableTennis)) {
			path = Constans.CREATETABLETENNISORDER;
		} else if (merid.equals(Constans.getInstance().sportEquipment)) {
			path = Constans.CREATETEQUIPMENTORDER;
		} else if (merid.equals(Constans.getInstance().sportTicket)) {
			path = Constans.CREATETATICKETORDER;
		} else if (merid.equals(Constans.getInstance().sportWomen)) {
			path = Constans.CREATEWOMENORDER;
		} else if (merid.equals(Constans.getInstance().shenghuo)) {
			path = Constans.CREATELIFEORDER;
		}

		StringRequest mJsonObjectRequest = new StringRequest(Method.POST, path, new Listener<String>() {
			public void onResponse(String response) {
				System.err.println(response);
				JSONObject jsonObject = RequestUtils.String2JSON(response);
				HashMap<String, Object> dataMap = new HashMap<String, Object>();// 深拷贝（只赋值，重新在堆内存开辟新地址）
				CreateOrdersParser mParser = new CreateOrdersParser();
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
