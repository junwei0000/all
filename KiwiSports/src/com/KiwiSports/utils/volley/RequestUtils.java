package com.KiwiSports.utils.volley;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:04:03
 * @Description 类描述：volley数据请求初始化
 */
public class RequestUtils {

	private static RequestQueue mRequestQueue;

	public static void init(Context context) {
		mRequestQueue = Volley.newRequestQueue(context, new MultiPartStack());
	}

	public static RequestQueue getRequestQueue() {
		if (mRequestQueue != null) {
			return mRequestQueue;
		} else {
			throw new IllegalStateException("RequestQueue not initialized");
		}
	}

	/**
	 * 开启请求时添加标示
	 * 
	 * @param request
	 * @param tag
	 */
	public static void addRequest(Request<?> request, Object tag) {
		/**
		 * 防止因为网络超时进行两次请求。 其实是volley在第一次访问的时候，由于超时时间到了，进行了第二次访问。
		 */
		request.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		if (tag != null) {
			request.setTag(tag);
		}
		Log.e("******mRequestQueue********", request.toString());
		mRequestQueue.add(request);
	}

	/**
	 * 根据tag取消某个请求
	 * 
	 * @param tag
	 */
	public static void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
	}

	/**
	 * String转换为json类型
	 * 
	 * @param result
	 * @return
	 */
	public static JSONObject String2JSON(String result) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 返回get请求的url
	 * 
	 * @param path
	 * @param params
	 * @return
	 */
	public static String reponseGetUrl(String path, HashMap<String, String> params) {
		StringBuilder sb = new StringBuilder(path);
		try {
			sb.append('?');
			// ?method=save&title=435435435&timelength=89&
			// 把Map中的数据迭代附加到StringBuilder中
			for (Map.Entry<String, String> entry : params.entrySet()) {
				// URLEncoder.encode对字符串中文进行编码，防止乱码
				sb.append(entry.getKey()).append('=').append(URLEncoder.encode("" + entry.getValue(), "UTF-8"))
						.append('&');
			}
			// 去掉最后一个字符&
			sb.deleteCharAt(sb.length() - 1);
		} catch (Exception e) {
		}
		Log.e("get----------------", sb.toString());
		return sb.toString();
	}

}
