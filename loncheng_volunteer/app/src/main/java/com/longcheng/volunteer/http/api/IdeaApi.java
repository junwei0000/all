package com.longcheng.volunteer.http.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longcheng.volunteer.base.ExampleApplication;
import com.longcheng.volunteer.config.Config;
import com.longcheng.volunteer.utils.LogUtils;
import com.longcheng.volunteer.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.longcheng.volunteer.utils.network.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by markShuai on 2017/12/7.
 */

public class IdeaApi {
    private Retrofit retrofit;
    private IdeaApiService service;

    private IdeaApi() {
        //拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message -> {
            try {
                String text = URLDecoder.decode(message, "utf-8");
                LogUtils.e("OKHttp-----", text);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                LogUtils.e("OKHttp-----", message);
            }
        });
        //用来设置日志打印的级别 BODY 请求/响应行 + 头 + 体
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        //设置缓存
        File cacheFile = new File(ExampleApplication.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        //配置 OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Config.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(Config.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .cache(cache)
                .addInterceptor(loggingInterceptor)
                //添加token
//                .addInterceptor(new HttpHeaderInterceptor())
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .build();

        //初始化Retrofit并配置
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Config.BASE_URL)
                .build();
        service = retrofit.create(IdeaApiService.class);
    }

    //  创建单例
    private static class SingletonHolder {
        private static final IdeaApi INSTANCE = new IdeaApi();
    }

    public static IdeaApiService getApiService() {
        return SingletonHolder.INSTANCE.service;
    }

    //  添加请求头的拦截器
    private class HttpHeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //  将token统一放到请求头
            String token = (String) SharedPreferencesHelper.get(ExampleApplication.getContext(), SharedPreferencesHelper.TOKEN, "");
            //  也可以统一配置用户名
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("token", token)
                    .build();
        }
    }

    /**
     * @Params
     * @name Gson实例化（排除null字段）
     * @time 2017/11/22 10:43
     * @author MarkShuai
     */
    private Gson createGson() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .serializeNulls()
                .create();
        return gson;
    }

    //  配置缓存的拦截器
    private class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {  //没网强制从缓存读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtils.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();

                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
