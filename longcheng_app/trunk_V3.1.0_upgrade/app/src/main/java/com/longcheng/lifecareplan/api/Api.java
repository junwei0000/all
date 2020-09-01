package com.longcheng.lifecareplan.api;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longcheng.lifecareplan.base.ActivityManager;
import com.longcheng.lifecareplan.config.Config;
import com.longcheng.lifecareplan.modular.mine.set.activity.NotServiceActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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
        sendRequestWithHttpClient();
        return INSTANCE;
    }

    /**
     * 是否重定向，跳转提示页  在里面开启线程
     */
    private static void sendRequestWithHttpClient() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = Config.BASE_HEAD_URL + "home/upgrade/index";
                //第一步：创建HttpClient对象
                HttpClient httpCient = new DefaultHttpClient();
                //第二步：创建代表请求的对象,参数是访问的服务器地址
                HttpGet httpGet = new HttpGet(url);
                try {
                    //第三步：执行请求，获取服务器发还的相应对象
                    HttpResponse httpResponse = httpCient.execute(httpGet);
                    //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                    int code = httpResponse.getStatusLine().getStatusCode();
                    Log.e("getModifyDomainName", "code=" + code);
                    if (code == 200) {
                        Activity mActivity = ActivityManager.getScreenManager().getCurrentActivity();
                        if (mActivity != null && mActivity.getClass() != NotServiceActivity.class) {
                            Intent intent = new Intent(mActivity, NotServiceActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("html_url", url);
                            mActivity.startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();//这个start()方法不要忘记了
    }

    //构造方法私有
    private Api() {
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
