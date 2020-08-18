package com.longcheng.volunteer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Burning on 2018/9/3.
 */

public class DateUtil {
    public static String DATE_YMDhms = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_YMD_cn = "yyyy年MM月dd日";

    public static long getTimeStamp(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_YMDhms);
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }

    }

    public static long getTimeStamp(String time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(time);
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }

    }

    public static String getTimeStrByFormat(long time, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(time);
    }

    public static String getTimeStrDefault(long time) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_YMDhms);
        return df.format(time);
    }
}
