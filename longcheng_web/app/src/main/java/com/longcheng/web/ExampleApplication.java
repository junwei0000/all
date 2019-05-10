package com.longcheng.web;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


/**
 * 作者：jun on
 * 时间：2018/3/20 16:55
 * 意图：Application全局的
 */

public class ExampleApplication extends MultiDexApplication {

    public static ExampleApplication exampleApplication;
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        exampleApplication = this;
        context = getApplicationContext();
        setCrash();
    }




    public static Context getContext() {
        return context;
    }

    public static ExampleApplication getInstance() {
        return exampleApplication;
    }


    private void setCrash() {
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        // 异常捕捉
        try {
            CustomCrashHandler mCustomCrashHandler = CustomCrashHandler.getInstance();
            mCustomCrashHandler.setCustomCrashHanler(getApplicationContext());
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
