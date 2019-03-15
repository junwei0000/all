package com.longcheng.lifecareplan.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.longcheng.lifecareplan.base.ExampleApplication;

/**
 * 作者：MarkShuai on
 * 时间：2018/2/1 10:25
 * 邮箱：mark_mingshuai@163.com
 * 意图：
 */

public class MySharedPreferences {

    public static final String NAME = "preferenceLongCheng";
    private volatile static SharedPreferences sharedPreferences;
    private volatile static MySharedPreferences instance;

    private static final String ISFIRSTIN = "isFirstIn";

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
     * @param
     * @Name 获取是否第一次进入App
     * @Data 2018/2/1 10:31
     * @Author :MarkShuai
     */
    public boolean getIsFirstIn() {
        return sharedPreferences.getBoolean(ISFIRSTIN, true);
    }
}
