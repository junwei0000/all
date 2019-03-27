package com.longcheng.volunteer.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longcheng.volunteer.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 10:14
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class Api {
    public Retrofit retrofit;
    public ApiService service;

    private static volatile Api INSTANCE;

    public static Api getInstance() {
        if (INSTANCE == null) {
            synchronized (Api.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Api();
                }
            }
        }
        return INSTANCE;
    }

    //构造方法私有
    private Api() {
        Log.e("Api", "Api-----------");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();
        //启用Log日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Config.BASE_URL)
                .build();
        service = retrofit.create(ApiService.class);
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
