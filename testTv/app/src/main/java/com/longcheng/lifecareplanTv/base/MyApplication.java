package com.longcheng.lifecareplanTv.base;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.longcheng.lifecareplanTv.utils.ConfigUtils;


/**
 * 作者：jun on
 * 时间：2018/3/20 16:55
 * 意图：Application全局的
 */

public class MyApplication extends Application {

    public static MyApplication exampleApplication;
    private static Context context;
    public static String token = "";
    private Typeface typeface;

    @Override
    public void onCreate() {
        super.onCreate();
        exampleApplication = this;
        context = getApplicationContext();
        token = ConfigUtils.getINSTANCE().getDeviceId(this);
        typeface = Typeface.createFromAsset(getAssets(), "kaiti.ttf");
    }


    public static Context getContext() {
        return context;
    }

    public static MyApplication getInstance() {
        return exampleApplication;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }
}
