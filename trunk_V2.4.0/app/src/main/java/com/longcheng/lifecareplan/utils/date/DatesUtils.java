package com.longcheng.lifecareplan.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;
import android.util.Log;

import com.nanchen.calendarview.LunarCalendar;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:28:06
 * @Description 类描述：日期类工具类
 */
public class DatesUtils {
    private static DatesUtils instance;

    private DatesUtils() {
    }

    public static synchronized DatesUtils getInstance() {
        if (instance == null) {
            instance = new DatesUtils();
        }
        return instance;
    }

    public int[] companyTimeNoSecond(long time) {
        int[] timeCount = new int[3];

        time = time / 1000;
        int second = (int) (time % 60);
        int minute = (int) ((time % 3600) / 60);
        int hour = (int) (time / 3600);
        timeCount[0] = hour;
        timeCount[1] = minute;
        timeCount[2] = second;

        return timeCount;
    }

    /**
     * 根据开始结束时间获取时间段
     *
     * @param itemtimeBuffer
     * @param starthour
     * @param endhour
     * @param teetimejiange
     */
    public void setplayTimes(Boolean doequeday,
                             ArrayList<String> itemtimeBuffer, int starthour, int endhour,
                             int teetimejiange) {
        String timenext = getDateGeShi(starthour + "", "H", "HH:mm");
        String nowHour = DatesUtils.getInstance().getNowTime("HH:mm");
        boolean timestaus = DatesUtils.getInstance().doCheck2Date(
                "" + timenext, nowHour, "HH:mm");
        if (doequeday) {
            if (!timestaus)
                itemtimeBuffer.add(timenext);
        } else {
            itemtimeBuffer.add(timenext);
        }

        for (int i = starthour; i < endhour; i++) {
            timenext = addTime(timenext, teetimejiange, "HH:mm");
            if (!itemtimeBuffer.contains(timenext)) {
                timestaus = DatesUtils.getInstance().doCheck2Date(
                        "" + timenext, nowHour, "HH:mm");

                String endtimenext = getDateGeShi(endhour + "", "H", "HH:mm");
                boolean timestausend = DatesUtils.getInstance().doCheck2Date(
                        "" + timenext, endtimenext, "HH:mm");
                if (timestausend) {
                    if (doequeday) {
                        if (!timestaus)
                            itemtimeBuffer.add(timenext);
                    } else {
                        itemtimeBuffer.add(timenext);
                    }
                }
            }
            String timenextofhour = getDateGeShi(timenext + "", "HH:mm", "H");
            if (timenextofhour.equals(i + "")) {
                i--;
            }
        }
    }

    /**
     * 得到teetime的【小时】选择范围 8-9
     *
     * @param starttime 8:00
     * @param endtime   9:30
     * @return
     */
    public String[] getIntervalHoursTeeTime(String starttime, String endtime) {
        String[] hours = null;
        try {
            if (!TextUtils.isEmpty(starttime) && !TextUtils.isEmpty(endtime)) {
                starttime = getMaoHao(starttime);
                endtime = getMaoHao(endtime);
                int starthour = Integer.parseInt(getDateGeShi(starttime,
                        "HH:mm", "HH"));
                int endhour = Integer.parseInt(getDateGeShi(endtime, "HH:mm",
                        "HH"));
                int hournum = endhour - starthour + 1;
                hours = new String[hournum];
                for (int i = 0; i < hournum; i++) {
                    int sh = starthour + i;
                    if (sh < 10) {
                        hours[i] = "0" + sh + "";
                    } else {
                        hours[i] = sh + "";
                    }

                    System.out.println(hours[i]);
                }
            } else {
                hours = null;
            }
            return hours;
        } catch (Exception e) {
            return hours;
        }
    }

    public String[][] getIntervalminsTeeTime_(String starttime, String endtime,
                                              String priceRuleType) {
        String[][] mins = null;
        try {
            if (!TextUtils.isEmpty(starttime) && !TextUtils.isEmpty(endtime)) {
                starttime = getMaoHao(starttime);
                endtime = getMaoHao(endtime);
                int starthour = Integer.parseInt(getDateGeShi(starttime,
                        "HH:mm", "HH"));
                int endhour = Integer.parseInt(getDateGeShi(endtime, "HH:mm",
                        "HH"));
                int hournum = endhour - starthour + 1;
                mins = new String[hournum][];
                for (int i = 0; i < hournum; i++) {
                    int minsnum = 2;
                    if (priceRuleType.equals("noBanTimeRule")
                            || (i == hournum - 1)) {
                        minsnum = 1;
                    }
                    mins[i] = new String[minsnum];
                    for (int j = 0; j < minsnum; j++) {
                        if (j == 0) {
                            mins[i][j] = "00";
                        } else {
                            mins[i][j] = "30";
                        }
                        System.out.println("mins[" + i + "][" + j + "]="
                                + mins[i][j]);
                    }
                }
            } else {
                mins = null;
            }
            return mins;
        } catch (Exception e) {
            return mins;
        }
    }

    /**
     * 得到teetime的【分钟】选择范围
     *
     * @param starttime
     * @param endtime
     * @return
     */
    public String[][] getIntervalminsTeeTime(String starttime, String endtime) {
        String[][] mins = null;
        try {
            if (!TextUtils.isEmpty(starttime) && !TextUtils.isEmpty(endtime)) {
                starttime = getMaoHao(starttime);
                endtime = getMaoHao(endtime);
                int starthour = Integer.parseInt(getDateGeShi(starttime,
                        "HH:mm", "HH"));
                int endhour = Integer.parseInt(getDateGeShi(endtime, "HH:mm",
                        "HH"));
                int hournum = endhour - starthour + 1;
                mins = new String[hournum][];
                int startmins = Integer.parseInt(getDateGeShi(starttime,
                        "HH:mm", "HH"));
                int endmins = Integer.parseInt(getDateGeShi(endtime, "HH:mm",
                        "HH"));
                for (int i = 0; i < hournum; i++) {
                    if (hournum == 1) {
                        // 则starthour=endhour, mins.length=1;
                        int minsnum = endmins - startmins + 1;
                        mins[i] = new String[minsnum];
                        for (int j = 0; j < minsnum; j++) {
                            if (startmins + j < 10) {
                                mins[i][j] = "0" + (startmins + j) + "";
                            } else {
                                mins[i][j] = startmins + j + "";
                            }
                            System.out.println("mins[" + i + "][" + j + "]="
                                    + mins[i][j]);
                        }
                    } else if (hournum == 2) {
                        // 则starthour-endhour=1, mins.length=2;
                        if (i == 0) {
                            int minsnum = 60 - startmins;
                            mins[i] = new String[minsnum];
                            for (int j = 0; j < minsnum; j++) {
                                if (startmins + j < 10) {
                                    mins[i][j] = "0" + (startmins + j) + "";
                                } else {
                                    mins[i][j] = startmins + j + "";
                                }
                                System.out.println("mins[" + i + "][" + j
                                        + "]=" + mins[i][j]);
                            }
                        } else {
                            int minsnum = endmins - 0 + 1;
                            mins[i] = new String[minsnum];
                            for (int j = 0; j < minsnum; j++) {
                                if (j < 10) {
                                    mins[i][j] = "0" + j + "";
                                } else {
                                    mins[i][j] = j + "";
                                }
                                System.out.println("mins[" + i + "][" + j
                                        + "]=" + mins[i][j]);
                            }
                        }
                    } else {
                        // hournum>2小时
                        if (i == 0) {
                            int minsnum = 60 - startmins;
                            mins[i] = new String[minsnum];
                            for (int j = 0; j < minsnum; j++) {
                                if (startmins + j < 10) {
                                    mins[i][j] = "0" + (startmins + j) + "";
                                } else {
                                    mins[i][j] = startmins + j + "";
                                }
                                System.out.println("mins[" + i + "][" + j
                                        + "]=" + mins[i][j]);
                            }
                        } else if (hournum == 1 + i) {
                            int minsnum = endmins - 0 + 1;
                            mins[i] = new String[minsnum];
                            for (int j = 0; j < minsnum; j++) {
                                if (j < 10) {
                                    mins[i][j] = "0" + j + "";
                                } else {
                                    mins[i][j] = j + "";
                                }
                                System.out.println("mins[" + i + "][" + j
                                        + "]=" + mins[i][j]);
                            }
                        } else {
                            int minsnum = 60;
                            mins[i] = new String[minsnum];
                            for (int j = 0; j < minsnum; j++) {
                                mins[i][j] = j + "";
                                if (j < 10) {
                                    mins[i][j] = "0" + j + "";
                                } else {
                                    mins[i][j] = j + "";
                                }
                                System.out.println("mins[" + i + "][" + j
                                        + "]=" + mins[i][j]);
                            }
                        }
                    }
                }
            } else {
                mins = null;
            }
            return mins;
        } catch (Exception e) {
            return mins;
        }
    }

    /**
     * 返回同一天两个时间相差的时间（miao）
     *
     * @param starttime 例如：8:30 2013-12-11 8:30
     * @param endtime   例如： 9:00 2013-12-11 9:00
     * @param format    例如： HH:mm yyyy-MM-dd HH:mm
     * @return
     */
    public int timeDiff(String starttime, String endtime, String format) {
        SimpleDateFormat sdweek = new SimpleDateFormat(format);
        Date date = null;
        Date date2 = null;
        int secondStr = 0;
        try {
            date = sdweek.parse(starttime);
            date2 = sdweek.parse(endtime);
            long time = (date2.getTime() - date.getTime()) / 1000; // 相差秒数
            long day = time / (24 * 60 * 60);
            long hours = (time % (24 * 60 * 60)) / (60 * 60);
            long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
            long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
            secondStr = (int) (day * 24 * 60 * 60 + hours * 60 * 60 + minutes
                    * 60 + second);
            System.out.println("starttime 与  endtime 相差" + secondStr + "秒");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secondStr;
    }

    /**
     * 返回两个时间相差的时间（miao）
     *
     * @param starttime 例如：8:30 2013-12-11 8:30
     * @param endtime   例如： 9:00 2013-12-11 9:00
     * @param format    例如： HH:mm yyyy-MM-dd HH:mm
     * @return
     */
    public long[] timeDiff2(String starttime, String endtime, String format) {
        SimpleDateFormat sdweek = new SimpleDateFormat(format);
        Date date = null;
        Date date2 = null;
        try {
            date = sdweek.parse(starttime);
            date2 = sdweek.parse(endtime);
            long time = (date2.getTime() - date.getTime()) / 1000 + 4; // 相差秒数
            // long day = time / (24 * 60 * 60);
            // long hours = (time % (24 * 60 * 60)) / (60 * 60);
            long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
            long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
            long[] s = {minutes, second};
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            long[] s = {0, 4};
            return s;
        }

    }

    /**
     * time时间由中文"："-->英文":" 并改为时间格式
     *
     * @param time
     * @return
     */
    public String getMaoHao(String time) {
        if (!TextUtils.isEmpty(time)) {
            if (time.contains("：") || time.contains(":")) {
                time = time.replace("：", ":");
            } else {
                time = time + ":00";
            }
        }
        return time;
    }

    /**
     * str 例：由中文oldStr"："-->英文newStr":" 并改为时间格式
     *
     * @param str
     * @param oldStr
     * @param newStr
     * @return
     */
    public String getZhuanHuan(String str, String oldStr, String newStr) {
        if (!TextUtils.isEmpty(str)
                && (str.contains(oldStr) || str.contains(newStr))) {
            str = str.replace(oldStr, newStr);
        }
        return str;
    }

    /**
     * 注意的是在对日期进行加减的时候请使用DAY_OF_YEAR 不要使用DAY_OF_MONTH
     *
     * @param time   例如：13:11 2014-5-8 13:11
     * @param format 例如：HH:mm yyyy-MM-dd HH:mm
     * @param adds   所加的分钟
     * @return
     */
    public String addTime(String time, int adds, String format) {
        String newtime = "";
        try {
            SimpleDateFormat sdweek = new SimpleDateFormat(format);
            Date date = sdweek.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, adds);
            newtime = sdweek.format(cal.getTime());
            System.out.println("newtime ==" + newtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newtime;
    }

    /**
     * 加 秒
     *
     * @param time
     * @param adds
     * @param format
     * @return
     */
    public String addSecondTime(String time, int adds, String format) {
        String newtime = "";
        try {
            SimpleDateFormat sdweek = new SimpleDateFormat(format);
            Date date = sdweek.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.SECOND, adds);
            newtime = sdweek.format(cal.getTime());
            System.out.println("newtime ==" + newtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newtime;
    }

    /**
     * 时间戳转换成--日期
     *
     * @return
     */
    public String getTimeStampToDate(int timestamp, String format) {
        String day = null;
        try {
            SimpleDateFormat sf = new SimpleDateFormat(format);
            long timestamp_ = (long) timestamp;
            timestamp_ *= 1000;
            Date d = new Date(timestamp_);
            day = sf.format(d);
        } catch (Exception e) {
        }
        return day;
    }

    /**
     * 获取配速
     *
     * @param totalSec
     */
    public String formatMatchspeed(long totalSec) {
        long second = totalSec % 60;
        long minute = (totalSec % 3600) / 60;
        long hour = totalSec / 3600;
        // 秒显示两位
        String strSecond = ("00" + second)
                .substring(("00" + second).length() - 2);
        // 分显示两位
        String strMinute = ("00" + minute)
                .substring(("00" + minute).length() - 2);
        // 时显示两位
        String strHour = ("00" + hour).substring(("00" + hour).length() - 2);
        String speed;
        if (strHour.equals("00")) {
            speed = strMinute + "'" + strSecond + "\"";
        } else {
            speed = "--";
        }
        return speed;

    }

    /**
     * 计算 最大 平均 配速值
     *
     * @param time
     * @param distance
     * @return
     */
    public long computeMatchspeed(long time, double distance) {
        time = time / 1000;
        long second_ = time % 60;
        long minute_ = (time % 3600) / 60;
        long hour_ = time / 3600;
        long totalsecond = hour_ * 3600 + minute_ * 60 + second_;
        long totalSec = (long) (totalsecond / distance);
        return totalSec;

    }

    /**
     * 得到一个格式化的时间
     *
     * @param time 时间 毫秒
     * @return 时：分：秒：毫秒
     */
    public String formatTimes(long time) {
        if (time < 0) {
            time = 0;
        }
        time = time / 1000;
        long second = time % 60;
        long minute = (time % 3600) / 60;
        long hour = time / 3600;

        // 毫秒秒显示两位
        // String strMillisecond = "" + (millisecond / 10);
        // 秒显示两位
        String strSecond = ("00" + second)
                .substring(("00" + second).length() - 2);
        // 分显示两位
        String strMinute = ("00" + minute)
                .substring(("00" + minute).length() - 2);
        // 时显示两位
        String strHour = ("00" + hour).substring(("00" + hour).length() - 2);

        return strHour + ":" + strMinute + ":" + strSecond;
        // + strMillisecond;
    }

    /**
     * 将日期转为--时间戳
     *
     * @param day
     * @return
     */
    public int getDateToTimeStamp(String day, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date();

        try {
            date = sdf.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int timestamp = (int) (date.getTime() / 1000);
        return timestamp;
    }

    /**
     * 得到当天前后的时间
     *
     * @param i      （0为当天，1为第二天）
     * @param format
     * @return
     */
    public String getEDate_y(int i, String format) {
        SimpleDateFormat sdweek = new SimpleDateFormat("yyyy/MM/dd EE");
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String datetime = "";
        try {
            Date date = new Date();
            String str = sdweek.format(date);
            Calendar c = Calendar.getInstance();
            c.setTime(sdweek.parse(str));
            c.add(Calendar.DAY_OF_MONTH, i);// 得到前5
            datetime = formatter.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datetime;
    }

    /**
     * 时间格式的转换,转换显示类型
     *
     * @param day        2014-11-11
     * @param fromformat yyyy-MM-dd
     * @param toformat   MM/dd EE
     * @return
     */
    public String getDateGeShi(String day, String fromformat, String toformat) {
        SimpleDateFormat sdweek = new SimpleDateFormat(fromformat);
        SimpleDateFormat formatter = new SimpleDateFormat(toformat);
        String datetime = "";
        try {
            if (TextUtils.isEmpty(day)) {
                return datetime;
            }
            Date date = sdweek.parse(day);
            datetime = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datetime;
    }

    /**
     * 获取手机当前时间
     *
     * @param format
     * @return
     */
    public String getNowTime(String format) {
        SimpleDateFormat nowsdf = new SimpleDateFormat(format);
        Date date = new Date();
        String time = nowsdf.format(date);
        return time;
    }

    /**
     * 判断一个时间是否在一个时间段之间
     *
     * @param day
     * @param starttime
     * @param endtime
     * @return
     */
    public boolean doCheckDate(String day, String starttime, String endtime) {
        boolean sta = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dday = sdf.parse(day);
            Date dstarttime = sdf.parse(starttime);
            Date dendtime = sdf.parse(endtime);
            if (dstarttime.equals(dendtime)) {
                // 同一天
                if (dstarttime.equals(dday)) {
                    sta = true;
                }
            } else {
                // 不同一天并且开始时间在结束时间之前
                if (dstarttime.before(dendtime)) {
                    if ((dday.before(dendtime) && dday.after(dstarttime))
                            || dendtime.equals(dday) || dstarttime.equals(dday)) {
                        sta = true;
                    }
                }
            }
        } catch (Exception e) {
        }
        return sta;
    }

    /**
     * 两个时间大小 dstarttime<=day 为TRUE
     *
     * @param starttime
     * @param day
     * @return
     */
    public boolean doCheck2Date(String starttime, String day, String format) {
        boolean sta = false;
        try {
            if (!TextUtils.isEmpty(starttime) && !TextUtils.isEmpty(day)) {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                Date dday = sdf.parse(day);
                Date dstarttime = sdf.parse(starttime);
                if (dstarttime.compareTo(dday) <= 0) {
                    sta = true;
                }
            }
        } catch (Exception e) {
        }
        return sta;
    }

    /**
     * 判断两个时间是否是同一天
     *
     * @param sdate
     * @param odate
     * @return
     */
    public boolean doDateEqual(String sdate, String odate, String format) {
        boolean status = false;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        Date date1 = null;
        try {
            if (!TextUtils.isEmpty(sdate) && !TextUtils.isEmpty(odate)) {
                date = sdf.parse(sdate);
                date1 = sdf.parse(odate);
                if (date.compareTo(date1) == 0) {
                    status = true;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return status;
    }


    /**
     * 获取当前日期一周的日期
     *
     * @param date
     * @return
     */
    public ArrayList<DateEntity> getWeek(String date) {
        ArrayList<DateEntity> result = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); //获取本周一的日期
        mLunarCalendar = new LunarCalendar();
        for (int i = 0; i < 7; i++) {
            DateEntity entity = new DateEntity();
            entity.date = getValue(cal.get(cal.YEAR)) + "-" + getValue(cal.get(cal.MONTH) + 1) + "-" + getValue(cal.get(cal.DATE));
            entity.million = cal.getTimeInMillis();
            entity.day = getValue(cal.get(cal.DATE));
            entity.weekNum = cal.get(Calendar.DAY_OF_WEEK);
            entity.weekName = getWeekName(entity.weekNum);
//            entity.isToday = isToday(entity.date, dateFormat);
            //新增
            if (entity.date.equals(date)) {
                entity.isToday = true;
            }else{
                entity.isToday = false;
            }
            entity.luna = mLunarCalendar.getLunarDate(entity.date);
            cal.add(Calendar.DATE, 1);
            result.add(entity);
            Log.e("getWeek", "" + entity.toString());
        }

        return result;

    }

    LunarCalendar mLunarCalendar;

    /**
     * 是否是今天
     *
     * @param sdate
     * @return
     */
    public boolean isToday(String sdate, SimpleDateFormat dateFormat) {
        boolean b = false;
        Date time = null;
        try {
            time = dateFormat.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater.get().format(today);
            String timeDate = dateFormater.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    private final ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 个位数补0操作
     *
     * @param num
     * @return
     */
    public String getValue(int num) {
        return String.valueOf(num > 9 ? num : ("0" + num));
    }

    /**
     * 根据美式周末到周一 返回
     *
     * @param weekNum
     * @return
     */
    private String getWeekName(int weekNum) {
        String name = "";
        switch (weekNum) {
            case 1:
                name = "星期日";
                break;
            case 2:
                name = "星期一";
                break;
            case 3:
                name = "星期二";
                break;
            case 4:
                name = "星期三";
                break;
            case 5:
                name = "星期四";
                break;
            case 6:
                name = "星期五";
                break;
            case 7:
                name = "星期六";
                break;
            default:
                break;
        }
        return name;
    }

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 切换周的时候用
     * 获取前/后 几天的一个日期
     *
     * @param currentData
     * @param dayNum
     * @return
     */
    public String getSomeDays(String currentData, int dayNum) {
        Calendar c = Calendar.getInstance();

        //过去七天
        try {
            c.setTime(dateFormat.parse(currentData));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, dayNum);
        Date d = c.getTime();
        String day = dateFormat.format(d);
        return day;
    }
}
