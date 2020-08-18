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

    /**
     * 引导页
     */
    private static final String ISFIRSTIN = "isFirstIn";
    /**
     * 是否已退出登录
     */
    private static final String ISLOGOUT = "isLogout";
    /**
     * 我家的常用功能状态
     */
    private static final String MINE_FUUNCTIONTYPEList = "MINE_FUUNCTIONTYPEList";


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
    public void saveIsFirstIn(boolean isFirstIn) {
        sharedPreferences.edit().putBoolean(ISFIRSTIN, isFirstIn).commit();
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

    /**
     * 是否已退出
     *
     * @param isLogout
     */
    public void saveIsLogout(boolean isLogout) {
        sharedPreferences.edit().putBoolean(ISLOGOUT, isLogout).commit();
    }

    public boolean getIsLogout() {
        return sharedPreferences.getBoolean(ISLOGOUT, true);
    }

    /**
     * 我家的常用功能状态 默认 list true
     *
     * @param MineFuunctiontypeList
     */
    public void saveMineFuunctiontypeList(boolean MineFuunctiontypeList) {
        sharedPreferences.edit().putBoolean(MINE_FUUNCTIONTYPEList, MineFuunctiontypeList).commit();
    }

    public boolean getMineFuunctiontypeList() {
        return sharedPreferences.getBoolean(MINE_FUUNCTIONTYPEList, true);
    }


}
