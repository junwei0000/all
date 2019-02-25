package com.longcheng.lifecareplan.utils.sharedpreferenceutils;

import android.content.Context;

import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;

/**
 * 用户信息工具类
 * Created by Burning on 2018/9/4.
 */

public class UserUtils {
    /**
     * 获取用户id
     *
     * @param mContext
     * @return
     */
    public static String getUserId(Context mContext) {
        return (String) SharedPreferencesHelper.get(mContext, "user_id", "");
    }

    /**
     * 获取用户名字
     *
     * @param mContext
     * @return
     */
    public static String getUserName(Context mContext) {
        return (String) SharedPreferencesHelper.get(mContext, "user_name", "");
    }

    /**
     * 获取用户手机号
     *
     * @param mContext
     * @return
     */
    public static String getUserPhone(Context mContext) {
        return (String) SharedPreferencesHelper.get(mContext, "phone", "");
    }

    /**
     * 获取用户头像
     *
     * @param mContext
     * @return
     */
    public static String getUserAvatar(Context mContext) {
        return (String) SharedPreferencesHelper.get(mContext, "avatar", "");
    }

    /**
     * 获取用户的微信登录token
     *
     * @param mContext
     * @return
     */
    public static String getWXToken(Context mContext) {
        return (String) SharedPreferencesHelper.get(mContext, "wxToken", "");
    }

    /**
     * 保存用户的微信登录token
     *
     * @param mContext
     * @param wxToken
     */
    public static void saveWXToken(Context mContext, String wxToken) {
        SharedPreferencesHelper.put(mContext, "wxToken", wxToken);
    }


    /**
     * 获取用户token
     *
     * @return
     */
    public static String getToken() {
        return ExampleApplication.token;
    }
}
