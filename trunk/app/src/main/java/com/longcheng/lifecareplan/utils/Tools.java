package com.longcheng.lifecareplan.utils;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：MarkShuai
 * 时间：2017/11/21 15:14
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class Tools {

    /**
     * @params
     * @name 电话号码正则 判断
     * @time 2017/11/21 15:14
     * @author MarkShuai
     */
    public static boolean isPhoneNum(EditText et) {

        String regExp = "^[1][3-8]+\\d{9}";

        Pattern p = Pattern.compile(regExp);

        Matcher m = p.matcher(et.getText().toString());

        if (et.getText().toString().getBytes().length == 11 && m.find()) {

            return true;
        } else {

            return false;
        }

    }
}
