package com.longcheng.lifecareplan.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longcheng.lifecareplan.config.Config;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 10:14
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class Api {
    public Retrofit retrofit;
    public ApiService service;

    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

    //构造方法私有
    private Api() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(30, TimeUnit.SECONDS);

        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://t.asdyf.com/api/")
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
