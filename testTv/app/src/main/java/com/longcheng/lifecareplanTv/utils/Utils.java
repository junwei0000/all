package com.longcheng.lifecareplanTv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.widget.Toast;


import com.longcheng.lifecareplanTv.R;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 意图：效验规则
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

    /**
     * 是否满足数字、字母、下划线 Pattern p = Pattern.compile("^\\w+$");
     *
     * @param password
     * @return
     */
    public static boolean isPasswordNO(String password) {
        Pattern p = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 是否满足数字、字母
     *
     * @param password
     * @return
     */
    public static boolean isCardNum(String password) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 是否为邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile(
                "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * @params
     * @name 电话号码正则 判断
     * @time 2017/11/21 15:14
     * @author MarkShuai
     */
//    public static boolean isPhoneNum(String phone) {
//        String regExp = "^[1][3-8]+\\d{9}";
//        Pattern p = Pattern.compile(regExp);
//        Matcher m = p.matcher(phone);
//        if (phone.getBytes().length == 11 && m.find()) {
//            return true;
//        } else {
//            return false;
//        }
//    }

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

    /**
     * 判断手机号
     *
     * @param phone
     * @return
     */
    public static boolean isCheckPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast(R.string.account_hint);
            return false;
        }
        return true;
    }
}
