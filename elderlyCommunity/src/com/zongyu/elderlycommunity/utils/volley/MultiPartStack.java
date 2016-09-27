package com.zongyu.elderlycommunity.utils.volley;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.HurlStack;

/**
 * @author ZhiCheng Guo
 * @version 2014年10月7日 上午11:00:52 这个Stack用于上传文件, 如果没有这个Stack, 则上传文件不成功
 */
public class MultiPartStack extends HurlStack {
	@SuppressWarnings("unused")
	private static final String TAG = MultiPartStack.class.getSimpleName();

	@Override
	public HttpResponse performRequest(Request<?> request,
			Map<String, String> additionalHeaders) throws IOException,
			AuthFailureError {

		if (!(request instanceof MultiPartRequest)) {
			return super.performRequest(request, additionalHeaders);
		} else {
			HttpResponse mHttpResponse = performMultiPartRequest(request);
			// HttpEntity resEntity = mHttpResponse.getEntity(); // DEBUG
			// String str = EntityUtils.toString(resEntity);
			return mHttpResponse;
		}
	}

	public HttpResponse performMultiPartRequest(Request<?> request)
			throws IOException, AuthFailureError {
		return postDataFromService(request);
	}

	public static HttpResponse postDataFromService(Request<?> request)
			throws IOException {
		// 设置请求超时,读取超时，防止出现设置超时时间无效的情况
		HttpResponse response = null;
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);
		// httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
		// 5000);
		// httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		// 5000);
		HttpClient httpclient = new DefaultHttpClient(params);
		httpclient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpPost httppost = new HttpPost(request.getUrl());
		MultipartEntity mpEntity = new MultipartEntity();
		Map<String, File> fileUpload = ((MultiPartRequest) request)
				.getFileUploads();
		for (Map.Entry<String, File> entry : fileUpload.entrySet()) {
			ContentBody cb = new FileBody((File) entry.getValue(), "image/jpg");
			mpEntity.addPart((String) entry.getKey(), cb);
		}
		Map<String, String> stringUpload = ((MultiPartRequest) request)
				.getStringUploads();
		for (Map.Entry<String, String> entry : stringUpload.entrySet()) {
			try {
				ContentBody cb = new StringBody((String) entry.getValue(),
						Charset.forName("UTF-8"));
				mpEntity.addPart(((String) entry.getKey()), cb);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		httppost.setEntity(mpEntity);
		response = httpclient.execute(httppost);
		return response;
	}

}
