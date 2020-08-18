package com.longcheng.lifecareplan.utils;

import android.text.TextUtils;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;

/**
 * Created by Burning on 2018/9/5.
 */

public class CommonUtil {


    /**
     * 卡号显示
     *
     * @param bank_no
     * @return
     */
    public static String setBankNo(String bank_no) {
        StringBuffer n = new StringBuffer();
        if (!TextUtils.isEmpty(bank_no) && bank_no.length() > 4) {
            for (int i = 0; i < bank_no.length(); i++) {
                if (i != 0 && i % 4 == 0) {
                    n.append(" ");
                }
                n.append(bank_no.charAt(i));
            }
        } else {
            n.append(bank_no);
        }
        return n.toString();
    }

    /**
     * 名字截取显示
     *
     * @param name
     * @return
     */
    public static String setName(String name) {
        String n;
        if (!TextUtils.isEmpty(name) && name.length() >= 5) {
            n = name.substring(0, 3) + "…";
        } else {
            n = name;
        }
        return n;
    }
    public static String setName3(String name) {
        String n;
        if (!TextUtils.isEmpty(name) && name.length() >= 3) {
            n = name.substring(0, 3);
        } else {
            n = name;
        }
        return n;
    }
    /**
     * 名字截取显示
     *
     * @param name
     * @return
     */
    public static String setName(String name,int jiequindex) {
        String n;
        if (!TextUtils.isEmpty(name) && name.length() > 10) {
            n = name.substring(0, jiequindex) + "…";
        } else {
            n = name;
        }
        return n;
    }
    public static String removeTrim(String str) {
        if (str.indexOf(".") > 0) {
            str = str.replaceAll("0+?$", "");//去掉多余的0
            str = str.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return str;
    }
    public static String getHtmlContent(String color, String content) {
        return "<font color=" + color + ">" + content + "</font>";
    }
    public static String getHtmlContentBig(String color, String content) {
        return "<strong><font color=" + color + "><big>" + content + "</big></font></strong>";
    }
    public static String getHtmlContentStrong(String color, String content) {
        return "<strong><font color=" + color + ">" + content + "</font></strong>";
    }
    /**
     * 将整型数字转换成带逗号的格式
     *
     * @param num 1234567
     * @return 1, 234, 567
     */
    public static String intToStringNum(int num) {
        String divider = ",";
        StringBuffer temp = new StringBuffer();
        temp.append(num);
        StringBuffer result = new StringBuffer();
        int length = temp.length();
        if (length < 4) {
            return temp.toString();
        }
        temp.reverse();
        int repeatCount = length / 3;
        int rest = length % 3;
        for (int i = 0; i < repeatCount; i++) {
            CharSequence s = temp.subSequence(3 * i, 3 * (i + 1));
            result.append(s);
            result.append(divider);
        }
        if (rest != 0) {
            return result.append(temp.subSequence(length - rest, length)).reverse().toString();
        } else {
            String tempS = result.subSequence(0, result.length() - 1).toString();
            return new StringBuffer(tempS).reverse().toString();
        }
    }

    /**
     * 将字符串转换成整数
     *
     * @param num
     * @return
     */
    public static int stringToInt(String num) {
        if (TextUtils.isEmpty(num)) {
            return 0;
        }
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 0;
        }
    }

}
