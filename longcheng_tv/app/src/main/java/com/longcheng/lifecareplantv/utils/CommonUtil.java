package com.longcheng.lifecareplantv.utils;

import android.text.TextUtils;

/**
 * Created by Burning on 2018/9/5.
 */

public class CommonUtil {
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
