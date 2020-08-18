package com.nanchen.calendarview;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 农历算法
 *
 * @author nanchen
 * @fileName CalendarViewDemo
 * @packageName com.nanchen.calendarview
 * @date 2016/12/08  10:43
 */

public class LunarCalendar {
    private int year; // 农历的年份
    private int month;
    private int day;

    final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
    final static long[] lunarInfo = new long[]{ //
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, //
            0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, //
            0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, //
            0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, //
            0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, //
            0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, //
            0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, //
            0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, //
            0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, //
            0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, //
            0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, //
            0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, //
            0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, //
            0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, //
            0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, //
            0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, //
            0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0};

    // 农历部分假日
    final static String[] lunarHoliday = new String[]{
            "0101 春节", "0115 元宵节", "0505 端午节", "0707 七夕", "0715 中元节",
            "0815 中秋节", "0909 重阳节", "1208 腊八", "1224 小年", "1230 除夕"};

    // 公历部分节假日
    final static String[] solarHoliday = new String[]{
            "0101 元旦", "0214 情人节", "0308 妇女节", "0312 植树节", "0315 消费者权益日",
            "0401 愚人节", "0405 清明节", "0501 劳动节", "0504 青年节",
            "0601 儿童节", "0701 建党节", "0801 建军节", "0909 毛泽东逝世纪念", "0910 教师节",
            "1001 国庆节", "1112 孙中山诞辰纪念", "1220 澳门回归纪念", "1224 平安夜", "1225 圣诞节", "1226 毛泽东诞辰纪念"};

    // ====== 传回农历 y年的总天数
    final private static int yearDays(int y) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((lunarInfo[y - 1900] & i) != 0)
                sum += 1;
        }
        return (sum + leapDays(y));
    }

    // ====== 传回农历 y年闰月的天数
    final private static int leapDays(int y) {
        if (leapMonth(y) != 0) {
            if ((lunarInfo[y - 1900] & 0x10000) != 0)
                return 30;
            else
                return 29;
        } else
            return 0;
    }

    // ====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
    final private static int leapMonth(int y) {
        int result = (int) (lunarInfo[y - 1900] & 0xf);
        return result;
    }

    // ====== 传回农历 y年m月的总天数
    final private static int monthDays(int y, int m) {
        if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
            return 29;
        else
            return 30;
    }

    // ====== 传回农历 y年的生肖
    final public String animalsYear(int year) {
        final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
        return Animals[(year - 4) % 12];
    }

    // ====== 传入 月日的offset 传回干支, 0=甲子
    final private static String cyclicalm(int num) {
        final String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        final String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        return (Gan[num % 10] + Zhi[num % 12]);
    }

    // ====== 传入 offset 传回干支, 0=甲子
    final public String cyclical(int year) {
        int num = year - 1900 + 36;
        return (cyclicalm(num));
    }

    public static String getChinaDayString(int day) {
        String chineseTen[] = {"初", "十", "廿", "卅"};
        int n = day % 10 == 0 ? 9 : day % 10 - 1;
        if (day > 30)
            return "";
        if (day == 10)
            return "初十";
        else
            return chineseTen[day / 10] + chineseNumber[n];
    }

    LunarSolarConverter.Solar solar = new LunarSolarConverter.Solar();
    SolarTermsUtil solarTermsUtil;

    public String getLunarDate(String day) {
        int year_c = Integer.parseInt(day.split("-")[0]);
        int month_c = Integer.parseInt(day.split("-")[1]);
        int day_c = Integer.parseInt(day.split("-")[2]);
        return getLunarDate(year_c, month_c, day_c, false);
    }

    /**
     * 传出y年m月d日对应的农历. yearCyl3:农历年与1864的相差数 ? monCyl4:从1900年1月31日以来,闰月数
     * dayCyl5:与1900年1月31日相差的天数,再加40 ?
     * <p>
     * isday: 这个参数为false---日期为节假日时，阴历日期就返回节假日 ，true---不管日期是否为节假日依然返回这天对应的阴历日期
     *
     * @return
     */
    public String getLunarDate(int year_log, int month_log, int day_log,
                               boolean isday) {
        /**
         * 防止在一月或12月中同时出现跨年的时间
         */
        if (month_log == 0) {
            year_log = year_log - 1;
            month_log = 12;
        } else if (month_log > 12) {
            year_log = year_log + 1;
            month_log = 1;
        }
        solar.solarYear = year_log;
        solar.solarMonth = month_log;
        solar.solarDay = day_log;
        LunarSolarConverter.Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        String odl = year_log + "-" + month_log + "-" + day_log;
        if (solarTermsUtil == null) {
            solarTermsUtil = new SolarTermsUtil();
            Log.e("getChinaDayString", " new  SolarTermsUtil");

        }
        String sol = solarTermsUtil.dataToSolar(odl, "yyyy-MM-dd");
        Log.e("getChinaDayString", odl + "   (" + sol + ")    -----------------  " + solar.toString() + "     -----------------    " + lunar.toString());
        if (!TextUtils.isEmpty(sol)) {
            return sol;
        }
        year = lunar.lunarYear;
        month = lunar.lunarMonth;
        day = lunar.lunarDay;
        if (!isday) {
            // 如果日期为节假日则阴历日期则返回节假日
            // setLeapMonth(leapMonth);
            for (int i = 0; i < solarHoliday.length; i++) {
                // 返回公历节假日名称
                String sd = solarHoliday[i].split(" ")[0]; // 节假日的日期
                String sdv = solarHoliday[i].split(" ")[1]; // 节假日的名称
                String smonth_v = month_log + "";
                String sday_v = day_log + "";
                String smd = "";
                if (month_log < 10) {
                    smonth_v = "0" + month_log;
                }
                if (day_log < 10) {
                    sday_v = "0" + day_log;
                }
                smd = smonth_v + sday_v;
                if (sd.trim().equals(smd.trim())) {
                    return sdv;
                }
            }

            for (int i = 0; i < lunarHoliday.length; i++) {
                // 返回农历节假日名称
                String ld = lunarHoliday[i].split(" ")[0]; // 节假日的日期
                String ldv = lunarHoliday[i].split(" ")[1]; // 节假日的名称
                String lmonth_v = month + "";
                String lday_v = day + "";
                String lmd = "";
                if (month < 10) {
                    lmonth_v = "0" + month;
                }
                if (day < 10) {
                    lday_v = "0" + day;
                }
                lmd = lmonth_v + lday_v;
                if (ld.trim().equals(lmd.trim())) {
                    return ldv;
                }
            }
        }
        if (day == 1)
            return chineseNumber[month - 1] + "月";
        else
            return getChinaDayString(day);

    }

    public String toString() {
        if (chineseNumber[month - 1] == "一" && getChinaDayString(day) == "初一")
            return "农历" + year + "年";
        else if (getChinaDayString(day) == "初一")
            return chineseNumber[month - 1] + "月";
        else
            return getChinaDayString(day);
        // return year + "年" + (leap ? "闰" : "") + chineseNumber[month - 1] +
        // "月" + getChinaDayString(day);
    }

    /**
     * 得到当前年对应的农历年份
     *
     * @return
     */
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}