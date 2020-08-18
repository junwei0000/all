package com.nanchen.calendarview;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * 作者：jun on
 * 时间：2019/5/30 14:00
 * 意图：
 */
public class LunarSolarConverter {
    /**
     * 农历
     */

    static class Lunar {
        public boolean isleap;//是否闰月
        public int lunarDay;
        public int lunarMonth;
        public int lunarYear;

        @Override
        public String toString() {
            return "农历==" + lunarYear + "-" + lunarMonth + "-" + lunarDay;
        }
    }

    /**
     * 阳历
     */

    static class Solar {
        public int solarDay;
        public int solarMonth;
        public int solarYear;

        @Override
        public String toString() {
            return "阳历==" + solarYear + "-" + solarMonth + "-" + solarDay;
        }
    }
    /*
     * |----4位闰月|-------------13位1为30天，0为29天|
	 */

    public static int[] lunar_month_days = {1887, 0x1694, 0x16aa, 0x4ad5,
            0xab6, 0xc4b7, 0x4ae, 0xa56, 0xb52a, 0x1d2a, 0xd54, 0x75aa, 0x156a,
            0x1096d, 0x95c, 0x14ae, 0xaa4d, 0x1a4c, 0x1b2a, 0x8d55, 0xad4,
            0x135a, 0x495d, 0x95c, 0xd49b, 0x149a, 0x1a4a, 0xbaa5, 0x16a8,
            0x1ad4, 0x52da, 0x12b6, 0xe937, 0x92e, 0x1496, 0xb64b, 0xd4a,
            0xda8, 0x95b5, 0x56c, 0x12ae, 0x492f, 0x92e, 0xcc96, 0x1a94,
            0x1d4a, 0xada9, 0xb5a, 0x56c, 0x726e, 0x125c, 0xf92d, 0x192a,
            0x1a94, 0xdb4a, 0x16aa, 0xad4, 0x955b, 0x4ba, 0x125a, 0x592b,
            0x152a, 0xf695, 0xd94, 0x16aa, 0xaab5, 0x9b4, 0x14b6, 0x6a57,
            0xa56, 0x1152a, 0x1d2a, 0xd54, 0xd5aa, 0x156a, 0x96c, 0x94ae,
            0x14ae, 0xa4c, 0x7d26, 0x1b2a, 0xeb55, 0xad4, 0x12da, 0xa95d,
            0x95a, 0x149a, 0x9a4d, 0x1a4a, 0x11aa5, 0x16a8, 0x16d4, 0xd2da,
            0x12b6, 0x936, 0x9497, 0x1496, 0x1564b, 0xd4a, 0xda8, 0xd5b4,
            0x156c, 0x12ae, 0xa92f, 0x92e, 0xc96, 0x6d4a, 0x1d4a, 0x10d65,
            0xb58, 0x156c, 0xb26d, 0x125c, 0x192c, 0x9a95, 0x1a94, 0x1b4a,
            0x4b55, 0xad4, 0xf55b, 0x4ba, 0x125a, 0xb92b, 0x152a, 0x1694,
            0x96aa, 0x15aa, 0x12ab5, 0x974, 0x14b6, 0xca57, 0xa56, 0x1526,
            0x8e95, 0xd54, 0x15aa, 0x49b5, 0x96c, 0xd4ae, 0x149c, 0x1a4c,
            0xbd26, 0x1aa6, 0xb54, 0x6d6a, 0x12da, 0x1695d, 0x95a, 0x149a,
            0xda4b, 0x1a4a, 0x1aa4, 0xbb54, 0x16b4, 0xada, 0x495b, 0x936,
            0xf497, 0x1496, 0x154a, 0xb6a5, 0xda4, 0x15b4, 0x6ab6, 0x126e,
            0x1092f, 0x92e, 0xc96, 0xcd4a, 0x1d4a, 0xd64, 0x956c, 0x155c,
            0x125c, 0x792e, 0x192c, 0xfa95, 0x1a94, 0x1b4a, 0xab55, 0xad4,
            0x14da, 0x8a5d, 0xa5a, 0x1152b, 0x152a, 0x1694, 0xd6aa, 0x15aa,
            0xab4, 0x94ba, 0x14b6, 0xa56, 0x7527, 0xd26, 0xee53, 0xd54, 0x15aa,
            0xa9b5, 0x96c, 0x14ae, 0x8a4e, 0x1a4c, 0x11d26, 0x1aa4, 0x1b54,
            0xcd6a, 0xada, 0x95c, 0x949d, 0x149a, 0x1a2a, 0x5b25, 0x1aa4,
            0xfb52, 0x16b4, 0xaba, 0xa95b, 0x936, 0x1496, 0x9a4b, 0x154a,
            0x136a5, 0xda4, 0x15ac};

    public static int[] solar_1_1 = {1887, 0xec04c, 0xec23f, 0xec435, 0xec649,
            0xec83e, 0xeca51, 0xecc46, 0xece3a, 0xed04d, 0xed242, 0xed436,
            0xed64a, 0xed83f, 0xeda53, 0xedc48, 0xede3d, 0xee050, 0xee244,
            0xee439, 0xee64d, 0xee842, 0xeea36, 0xeec4a, 0xeee3e, 0xef052,
            0xef246, 0xef43a, 0xef64e, 0xef843, 0xefa37, 0xefc4b, 0xefe41,
            0xf0054, 0xf0248, 0xf043c, 0xf0650, 0xf0845, 0xf0a38, 0xf0c4d,
            0xf0e42, 0xf1037, 0xf124a, 0xf143e, 0xf1651, 0xf1846, 0xf1a3a,
            0xf1c4e, 0xf1e44, 0xf2038, 0xf224b, 0xf243f, 0xf2653, 0xf2848,
            0xf2a3b, 0xf2c4f, 0xf2e45, 0xf3039, 0xf324d, 0xf3442, 0xf3636,
            0xf384a, 0xf3a3d, 0xf3c51, 0xf3e46, 0xf403b, 0xf424e, 0xf4443,
            0xf4638, 0xf484c, 0xf4a3f, 0xf4c52, 0xf4e48, 0xf503c, 0xf524f,
            0xf5445, 0xf5639, 0xf584d, 0xf5a42, 0xf5c35, 0xf5e49, 0xf603e,
            0xf6251, 0xf6446, 0xf663b, 0xf684f, 0xf6a43, 0xf6c37, 0xf6e4b,
            0xf703f, 0xf7252, 0xf7447, 0xf763c, 0xf7850, 0xf7a45, 0xf7c39,
            0xf7e4d, 0xf8042, 0xf8254, 0xf8449, 0xf863d, 0xf8851, 0xf8a46,
            0xf8c3b, 0xf8e4f, 0xf9044, 0xf9237, 0xf944a, 0xf963f, 0xf9853,
            0xf9a47, 0xf9c3c, 0xf9e50, 0xfa045, 0xfa238, 0xfa44c, 0xfa641,
            0xfa836, 0xfaa49, 0xfac3d, 0xfae52, 0xfb047, 0xfb23a, 0xfb44e,
            0xfb643, 0xfb837, 0xfba4a, 0xfbc3f, 0xfbe53, 0xfc048, 0xfc23c,
            0xfc450, 0xfc645, 0xfc839, 0xfca4c, 0xfcc41, 0xfce36, 0xfd04a,
            0xfd23d, 0xfd451, 0xfd646, 0xfd83a, 0xfda4d, 0xfdc43, 0xfde37,
            0xfe04b, 0xfe23f, 0xfe453, 0xfe648, 0xfe83c, 0xfea4f, 0xfec44,
            0xfee38, 0xff04c, 0xff241, 0xff436, 0xff64a, 0xff83e, 0xffa51,
            0xffc46, 0xffe3a, 0x10004e, 0x100242, 0x100437, 0x10064b, 0x100841,
            0x100a53, 0x100c48, 0x100e3c, 0x10104f, 0x101244, 0x101438,
            0x10164c, 0x101842, 0x101a35, 0x101c49, 0x101e3d, 0x102051,
            0x102245, 0x10243a, 0x10264e, 0x102843, 0x102a37, 0x102c4b,
            0x102e3f, 0x103053, 0x103247, 0x10343b, 0x10364f, 0x103845,
            0x103a38, 0x103c4c, 0x103e42, 0x104036, 0x104249, 0x10443d,
            0x104651, 0x104846, 0x104a3a, 0x104c4e, 0x104e43, 0x105038,
            0x10524a, 0x10543e, 0x105652, 0x105847, 0x105a3b, 0x105c4f,
            0x105e45, 0x106039, 0x10624c, 0x106441, 0x106635, 0x106849,
            0x106a3d, 0x106c51, 0x106e47, 0x10703c, 0x10724f, 0x107444,
            0x107638, 0x10784c, 0x107a3f, 0x107c53, 0x107e48};

    public static int GetBitInt(int data, int length, int shift) {
        return (data & (((1 << length) - 1) << shift)) >> shift;
    }

    // WARNING: Dates before Oct. 1582 are inaccurate
    public static long SolarToInt(int y, int m, int d) {
        m = (m + 9) % 12;
        y = y - m / 10;
        return 365 * y + y / 4 - y / 100 + y / 400 + (m * 306 + 5) / 10
                + (d - 1);
    }

    /**
     * @param lunarYear 农历年份
     * @return String of Ganzhi: 甲子年
     * Tiangan:甲乙丙丁戊己庚辛壬癸<br/>Dizhi: 子丑寅卯辰巳无为申酉戌亥
     */
    public static String lunarYearToGanZhi(int lunarYear) {
        final String[] tianGan = {"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        final String[] diZhi = {"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        return tianGan[(lunarYear - 4) % 10] + diZhi[(lunarYear - 4) % 12] + "年";
    }

    // ====== 传回农历 y年的生肖
    private static String animalsYear(int year) {
        final String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};
        return Animals[(year - 4) % 12];
    }

    // ====== 传入 月日的offset 传回干支, 0=甲子
    private static String cyclicalm(int num) {
        final String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
        final String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};
        return (Gan[num % 10] + Zhi[num % 12]);
    }

    // ====== 传入 offset 传回干支, 0=甲子
    public static String cyclical(int year) {
        int num = year - 1900 + 36;
        return (cyclicalm(num));
    }

    private static Solar SolarFromInt(long g) {
        long y = (10000 * g + 14780) / 3652425;
        long ddd = g - (365 * y + y / 4 - y / 100 + y / 400);
        if (ddd < 0) {
            y--;
            ddd = g - (365 * y + y / 4 - y / 100 + y / 400);
        }
        long mi = (100 * ddd + 52) / 3060;
        long mm = (mi + 2) % 12 + 1;
        y = y + (mi + 2) / 12;
        long dd = ddd - (mi * 306 + 5) / 10 + 1;
        Solar solar = new Solar();
        solar.solarYear = (int) y;
        solar.solarMonth = (int) mm;
        solar.solarDay = (int) dd;
        return solar;
    }

    /**
     * 农历转阳历
     *
     * @param lunar
     * @return
     */
    public static Solar LunarToSolar(Lunar lunar) {
        int days = lunar_month_days[lunar.lunarYear - lunar_month_days[0]];
        int leap = GetBitInt(days, 4, 13);
        int offset = 0;
        int loopend = leap;
        if (!lunar.isleap) {
            if (lunar.lunarMonth <= leap || leap == 0) {
                loopend = lunar.lunarMonth - 1;
            } else {
                loopend = lunar.lunarMonth;
            }
        }
        for (int i = 0; i < loopend; i++) {
            offset += GetBitInt(days, 1, 12 - i) == 1 ? 30 : 29;
        }
        offset += lunar.lunarDay;

        int solar11 = solar_1_1[lunar.lunarYear - solar_1_1[0]];

        int y = GetBitInt(solar11, 12, 9);
        int m = GetBitInt(solar11, 4, 5);
        int d = GetBitInt(solar11, 5, 0);

        return SolarFromInt(SolarToInt(y, m, d) + offset - 1);
    }


    /**
     * 阳历转农历
     *
     * @param solar
     * @return
     */
    public static Lunar SolarToLunar(Solar solar) {
        Lunar lunar = new Lunar();
        int index = solar.solarYear - solar_1_1[0];
        int data = (solar.solarYear << 9) | (solar.solarMonth << 5)
                | (solar.solarDay);
        int solar11 = 0;
        if (solar_1_1[index] > data) {
            index--;
        }
        solar11 = solar_1_1[index];
        int y = GetBitInt(solar11, 12, 9);
        int m = GetBitInt(solar11, 4, 5);
        int d = GetBitInt(solar11, 5, 0);
        long offset = SolarToInt(solar.solarYear, solar.solarMonth,
                solar.solarDay) - SolarToInt(y, m, d);

        int days = lunar_month_days[index];
        int leap = GetBitInt(days, 4, 13);

        int lunarY = index + solar_1_1[0];
        int lunarM = 1;
        int lunarD = 1;
        offset += 1;

        for (int i = 0; i < 13; i++) {
            int dm = GetBitInt(days, 1, 12 - i) == 1 ? 30 : 29;
            if (offset > dm) {
                lunarM++;
                offset -= dm;
            } else {
                break;
            }
        }
        lunarD = (int) (offset);
        lunar.lunarYear = lunarY;
        lunar.lunarMonth = lunarM;
        lunar.isleap = false;
        if (leap != 0 && lunarM > leap) {
            lunar.lunarMonth = lunarM - 1;
            if (lunarM == leap + 1) {
                lunar.isleap = true;
            }
        }

        lunar.lunarDay = lunarD;
        return lunar;
    }

    /**
     * 农历大写
     *
     * @param birthday
     * @return
     */
    public static String LunarBlockLetter(String birthday) {
        int year = Integer.parseInt(birthday.split("-")[0]);
        int month = Integer.parseInt(birthday.split("-")[1]);
        int day = Integer.parseInt(birthday.split("-")[2]);
        Solar solar = new Solar();
        solar.solarYear = year;
        solar.solarMonth = month;
        solar.solarDay = day;
        Log.e("getChinaDayString", "dump(solar)=" + solar.toString());
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        return "农历" + numberToChinese(lunar.lunarYear) + "年" + chineseNumber[lunar.lunarMonth - 1] + "月" + getChinaDayString(lunar.lunarDay);
    }

    public static String getLunarDayShow(int year_log, int month_log, int day_log,
                                         boolean isday) {
        Solar solar = new Solar();
        solar.solarYear = year_log;
        solar.solarMonth = month_log;
        solar.solarDay = day_log;
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        int month = lunar.lunarMonth;
        int day = lunar.lunarDay;
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

    private static String dump(Object o) {
        StringBuffer buffer = new StringBuffer();
        Class<?> oClass = o.getClass();
        if (oClass.isArray()) {
            buffer.append("[");
            for (int i = 0; i < Array.getLength(o); i++) {
                if (i > 0)
                    buffer.append(",");
                Object value = Array.get(o, i);
                buffer.append(value.getClass().isArray() ? dump(value) : value);
            }
            buffer.append("]");
        } else {
            buffer.append("{");
            while (oClass != null) {
                Field[] fields = oClass.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    if (buffer.length() > 1)
                        buffer.append(",");
                    fields[i].setAccessible(true);
                    buffer.append(fields[i].getName());
                    buffer.append("=");
                    try {
                        Object value = fields[i].get(o);
                        if (value != null) {
                            buffer.append(value.getClass().isArray() ? dump(value)
                                    : value);
                        }
                    } catch (IllegalAccessException e) {
                    }
                }
                oClass = oClass.getSuperclass();
            }
            buffer.append("}");
        }
        return buffer.toString();
    }

    private static String nums[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
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

    /**
     * 数字转汉字【新】
     *
     * @param num
     * @return
     */
    private static String numberToChinese(int num) {
        if (num == 0) {
            return "零";
        }
        int weigth = 0;//节权位
        String chinese = "";
        String chinese_section = "";
        boolean setZero = false;//下一小节是否需要零，第一次没有上一小节所以为false
        while (num > 0) {
            int section = num % 10000;//得到最后面的小节
            if (setZero) {//判断上一小节的千位是否为零，是就设置零
                chinese = nums[0] + chinese;
            }
            chinese_section = sectionTrans(section);
            chinese = chinese_section + chinese;
            chinese_section = "";

            setZero = (section < 1000) && (section > 0);
            num = num / 10000;
            weigth++;
        }
        if ((chinese.length() == 2 || (chinese.length() == 3)) && chinese.contains("一十")) {
            chinese = chinese.substring(1, chinese.length());
        }
        if (chinese.indexOf("一十") == 0) {
            chinese = chinese.replaceFirst("一十", "十");
        }

        return chinese;
    }

    /**
     * 将每段数字转汉子
     *
     * @param section
     * @return
     */
    private static String sectionTrans(int section) {
        StringBuilder section_chinese = new StringBuilder();
        int pos = 0;//小节内部权位的计数器
        boolean zero = true;//小节内部的置零判断，每一个小节只能有一个零。
        while (section > 0) {
            int v = section % 10;//得到最后一个数
            if (v == 0) {
                if (!zero) {
                    zero = true;//需要补零的操作，确保对连续多个零只是输出一个
                    section_chinese.insert(0, nums[0]);
                } else {
                    section_chinese.insert(0, nums[v]);
                }
            } else {
                zero = false;//有非零数字就把置零打开
                section_chinese.insert(0, nums[v]);
            }
            pos++;
            section = section / 10;
        }

        return section_chinese.toString();
    }

    public static void main(String[] args) {

    }

    public static void Test(String birthday) {
        int year = Integer.parseInt(birthday.split("-")[0]);
        int month = Integer.parseInt(birthday.split("-")[1]);
        int day = Integer.parseInt(birthday.split("-")[2]);
        Solar solar = new Solar();
        solar.solarYear = year;
        solar.solarMonth = month;
        solar.solarDay = day;
        Log.e("getChinaDayString", "dump(solar)=" + solar.toString());
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        Log.e("getChinaDayString", "dump(lunar)=" + lunar.toString());
        solar = LunarSolarConverter.LunarToSolar(lunar);
        Log.e("getChinaDayString", "dump(solar)=" + solar.toString());
    }
}