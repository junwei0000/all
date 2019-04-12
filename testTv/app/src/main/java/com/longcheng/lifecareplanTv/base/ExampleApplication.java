package com.longcheng.lifecareplanTv.base;

import android.app.Application;
import android.content.Context;

import com.longcheng.lifecareplanTv.utils.ConfigUtils;


/**
 * 作者：jun on
 * 时间：2018/3/20 16:55
 * 意图：Application全局的
 */

public class ExampleApplication extends Application {

    public static ExampleApplication exampleApplication;
    private static Context context;
    public static String token = "";

    @Override
    public void onCreate() {
        super.onCreate();
        exampleApplication = this;
        context = getApplicationContext();
        token = ConfigUtils.getINSTANCE().getDeviceId(this);
    }


    public static Context getContext() {
        return context;
    }

    public static ExampleApplication getInstance() {
        return exampleApplication;
    }


}
