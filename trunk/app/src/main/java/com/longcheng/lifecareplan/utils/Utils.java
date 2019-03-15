package com.longcheng.lifecareplan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：MarkShuai on
 * 时间：2017/11/20 16:41
 * 邮箱：mark_mingshuai@163.com
 * 意图：
 */

public class Utils {

    /*
    * @params reference
    * @name 判断是否为空的方法
    * @data 2017/11/20 15:46
    * @author :MarkShuai
    */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    /**
     * @param
     * @name 校验 String 只能是数字,英文字母和中文
     * @time 2017/11/29 18:01
     * @author MarkShuai
     */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }


    public static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }

    /**
     * @param
     * @name 获取当前系统的语言
     * @time 2017/12/6 15:02
     * @author MarkShuai
     */
    public static String getPhoneLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

}
