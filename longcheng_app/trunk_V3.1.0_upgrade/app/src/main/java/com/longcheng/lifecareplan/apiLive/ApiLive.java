package com.longcheng.lifecareplan.apiLive;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longcheng.lifecareplan.api.ApiDns;
import com.longcheng.lifecareplan.api.TrackX509TrustManager;
import com.longcheng.lifecareplan.config.Config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 10:14
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ApiLive {
    public Retrofit retrofit;
    public ApiLiveService service;

    private static volatile ApiLive INSTANCE;

    public static ApiLive getInstance() {
        if (INSTANCE == null) {
            synchronized (ApiLive.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiLive();
                }
            }
        }
        return INSTANCE;
    }

    //构造方法私有
    private ApiLive() {
        Log.e("Api", "Api-----------");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.retryOnConnectionFailure(false);//
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        /**
         * 自定义okhttp中dns解析 防止https第一次请求过慢
         */
        httpClient.dns(new ApiDns());
//        httpClient.sslSocketFactory(TrackX509TrustManager.createSSLSocketFactory());
//        httpClient.hostnameVerifier(new TrackX509TrustManager.TrustAllHostnameVerifier());

        /**
         * 在这里获取到request 请求参数
         */
        httpClient.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                Log.e("ResponseBody", "request == " + request.toString() + "  【】   " + response.toString());
                return response;
            }
        });

        OkHttpClient client = httpClient.build();
        //启用Log日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(CustomGsonConverterFactoryL.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Config.LIVE_BASE_URL)
                .build();
        service = retrofit.create(ApiLiveService.class);
    }

    /**
     * @Params
     * @name Gson实例化（排除null字段）
     * @time 2017/11/22 10:43
     * @author MarkShuai
     */
    private Gson createGson() {
        Gson gson = new GsonBuilder()
                //序列化不为空
                .serializeNulls()
                .create();
        return gson;
    }

}
