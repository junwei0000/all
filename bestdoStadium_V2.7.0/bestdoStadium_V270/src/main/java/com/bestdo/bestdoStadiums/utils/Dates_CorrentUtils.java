package com.bestdo.bestdoStadiums.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 矫正系统当前时间
 * 
 * @author jun
 * 
 */
public class Dates_CorrentUtils {
	private static Dates_CorrentUtils mUtils;

	public int cha_time;

	public synchronized static Dates_CorrentUtils getInstance(String starttime, String endtime, String format) {
		if (mUtils == null) {
			mUtils = new Dates_CorrentUtils(starttime, endtime, format);
		}
		return mUtils;
	}

	public Dates_CorrentUtils(String starttime, String endtime, String format) {
		cha_time = timeDiff(starttime, endtime, format);
	}

	/**
	 * 返回同一天两个时间相差的时间（miao）
	 * 
	 * @param starttime
	 *            例如：8:30 2013-12-11 8:30
	 * @param endtime
	 *            例如： 9:00 2013-12-11 9:00
	 * @param format
	 *            例如： HH:mm yyyy-MM-dd HH:mm
	 * @return
	 */
	private static int timeDiff(String starttime, String endtime, String format) {
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
			secondStr = (int) (day * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60 + second);
			System.out.println("starttime 与  endtime 相差" + secondStr + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secondStr;
	}
}
