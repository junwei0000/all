package com.longcheng.lifecareplan.utils.sharedpreferenceutils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.longcheng.lifecareplan.base.ExampleApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 作者：jun on
 * 时间：2018/3/15 10:25
 * 意图：是否开启引导页状态的存储
 */

public class MySharedPreferences {

    public static final String NAME = "preferenceLongCheng";
    private volatile static SharedPreferences sharedPreferences;
    private volatile static MySharedPreferences instance;

    private static final String ISFIRSTIN = "isFirstIn";
    private static final String ISLOGOUT = "isLogout";

    public static MySharedPreferences getInstance() {
        if (instance == null) {
            synchronized (MySharedPreferences.class) {
                if (instance == null) {
                    instance = new MySharedPreferences();
                }
            }
        }
        return instance;
    }

    private MySharedPreferences() {
        sharedPreferences = ExampleApplication.getContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /**
     * @param
     * @Name 存储是否第一次进入App
     * @Data 2018/2/1 10:27
     * @Author :MarkShuai
     */
    @SuppressLint("ApplySharedPref")
    public void saveIsFirstIn(boolean isFirstIn) {
        sharedPreferences.edit().putBoolean(ISFIRSTIN, isFirstIn).commit();
    }

    /**
     * 是否已退出
     *
     * @param isLogout
     */
    @SuppressLint("ApplySharedPref")
    public void saveIsLogout(boolean isLogout) {
        sharedPreferences.edit().putBoolean(ISLOGOUT, isLogout).commit();
    }

    public boolean getIsLogout() {
        return sharedPreferences.getBoolean(ISLOGOUT, true);
    }

    /**
     * @param
     * @Name 获取是否第一次进入App
     * @Data 2018/2/1 10:31
     * @Author :MarkShuai
     */
    public boolean getIsFirstIn() {
        return sharedPreferences.getBoolean(ISFIRSTIN, true);
    }
}
