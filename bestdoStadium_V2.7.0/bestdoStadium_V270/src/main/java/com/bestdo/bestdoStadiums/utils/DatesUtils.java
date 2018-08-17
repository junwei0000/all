package com.bestdo.bestdoStadiums.utils;

import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.StrictMode;
import android.text.TextUtils;

/**
 * 日期类工具类
 * 
 * @author jun
 * 
 */
public class DatesUtils {
	private static DatesUtils mUtils;

	public synchronized static DatesUtils getInstance() {
		if (mUtils == null) {
			mUtils = new DatesUtils();
		}
		return mUtils;
	}

	/**
	 * 获取月的最后一天
	 * 
	 * @param month
	 */
	public int getMonthLastDay(int month) {
		SimpleDateFormat format = new SimpleDateFormat("dd");
		// 获取当前月最后一天
		int noemonth = Integer.parseInt(getNowTime("MM"));
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.MONTH, month - noemonth);
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = format.format(ca.getTime());
		System.out.println("===============last:" + last);
		return Integer.parseInt(last);
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @Title:getLastDayOfMonth @Description: @param:@param year @param:@param
	 * month @param:@return @return:String @throws
	 */
	public int getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		// 设置年份
		cal.set(Calendar.YEAR, year);
		// 设置月份
		cal.set(Calendar.MONTH, month - 1);
		// 获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		// 设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String lastDayOfMonth = sdf.format(cal.getTime());

		System.err.println(lastDayOfMonth);
		return Integer.parseInt(lastDayOfMonth);
	}

	/**
	 * 根据开始结束时间获取时间段
	 * 
	 * @param itemtimeBuffer
	 * @param starthour
	 * @param endhour
	 * @param teetimejiange
	 */
	public void setplayTimes(Boolean nowdaystaus, ArrayList<String> itemtimeBuffer, int starthour, int endhour,
			int teetimejiange) {
		String timenext = getDateGeShi(starthour + "", "H", "HH:mm");
		String nowHour = DatesUtils.getInstance().getNowTime("HH:mm");
		boolean timestaus = DatesUtils.getInstance().doCheck2Date(nowHour, "" + timenext, "HH:mm");
		if (nowdaystaus) {
			if (timestaus)
				itemtimeBuffer.add(timenext);
		} else {
			itemtimeBuffer.add(timenext);
		}
		for (int i = starthour; i < endhour; i++) {
			timenext = addTime(timenext, teetimejiange, "HH:mm");
			if (!itemtimeBuffer.contains(timenext)) {
				timestaus = DatesUtils.getInstance().doCheck2Date(nowHour, "" + timenext, "HH:mm");
				if (nowdaystaus) {
					if (timestaus)
						itemtimeBuffer.add(timenext);
				} else {
					itemtimeBuffer.add(timenext);
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
	 * @param starttime
	 *            8:00
	 * @param endtime
	 *            9:30
	 * @return
	 */
	public String[] getIntervalHoursTeeTime(String starttime, String endtime) {
		String[] hours = null;
		try {
			if (!TextUtils.isEmpty(starttime) && !TextUtils.isEmpty(endtime)) {
				starttime = getMaoHao(starttime);
				endtime = getMaoHao(endtime);
				int starthour = Integer.parseInt(getDateGeShi(starttime, "HH:mm", "HH"));
				int endhour = Integer.parseInt(getDateGeShi(endtime, "HH:mm", "HH"));
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

	public String[][] getIntervalminsTeeTime_(String starttime, String endtime, String priceRuleType) {
		String[][] mins = null;
		try {
			if (!TextUtils.isEmpty(starttime) && !TextUtils.isEmpty(endtime)) {
				starttime = getMaoHao(starttime);
				endtime = getMaoHao(endtime);
				int starthour = Integer.parseInt(getDateGeShi(starttime, "HH:mm", "HH"));
				int endhour = Integer.parseInt(getDateGeShi(endtime, "HH:mm", "HH"));
				int hournum = endhour - starthour + 1;
				mins = new String[hournum][];
				for (int i = 0; i < hournum; i++) {
					int minsnum = 2;
					if (priceRuleType.equals("noBanTimeRule") || (i == hournum - 1)) {
						minsnum = 1;
					}
					mins[i] = new String[minsnum];
					for (int j = 0; j < minsnum; j++) {
						if (j == 0) {
							mins[i][j] = "00";
						} else {
							mins[i][j] = "30";
						}
						System.out.println("mins[" + i + "][" + j + "]=" + mins[i][j]);
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
				int starthour = Integer.parseInt(getDateGeShi(starttime, "HH:mm", "HH"));
				int endhour = Integer.parseInt(getDateGeShi(endtime, "HH:mm", "HH"));
				int hournum = endhour - starthour + 1;
				mins = new String[hournum][];
				int startmins = Integer.parseInt(getDateGeShi(starttime, "HH:mm", "HH"));
				int endmins = Integer.parseInt(getDateGeShi(endtime, "HH:mm", "HH"));
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
							System.out.println("mins[" + i + "][" + j + "]=" + mins[i][j]);
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
								System.out.println("mins[" + i + "][" + j + "]=" + mins[i][j]);
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
								System.out.println("mins[" + i + "][" + j + "]=" + mins[i][j]);
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
								System.out.println("mins[" + i + "][" + j + "]=" + mins[i][j]);
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
								System.out.println("mins[" + i + "][" + j + "]=" + mins[i][j]);
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
								System.out.println("mins[" + i + "][" + j + "]=" + mins[i][j]);
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
	 * @param starttime
	 *            例如：8:30 2013-12-11 8:30
	 * @param endtime
	 *            例如： 9:00 2013-12-11 9:00
	 * @param format
	 *            例如： HH:mm yyyy-MM-dd HH:mm
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
			secondStr = (int) (day * 24 * 60 * 60 + hours * 60 * 60 + minutes * 60 + second);
			System.out.println("starttime 与  endtime 相差" + secondStr + "秒");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return secondStr;
	}

	/**
	 * 返回两个时间相差的时间（miao）
	 * 
	 * @param starttime
	 *            例如：8:30 2013-12-11 8:30
	 * @param endtime
	 *            例如： 9:00 2013-12-11 9:00
	 * @param format
	 *            例如： HH:mm yyyy-MM-dd HH:mm
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
			long hours = (time % (24 * 60 * 60)) / (60 * 60);
			long minutes = ((time % (24 * 60 * 60)) % (60 * 60)) / 60;
			long second = ((time % (24 * 60 * 60)) % (60 * 60)) % 60;
			long[] s = { hours, minutes, second };
			return s;
		} catch (Exception e) {
			e.printStackTrace();
			long[] s = { 0, 4, 0 };
			return s;
		}

	}

	public long[] getCountdownTimesArray(int servicecurrenttimestamp, int createtimestamp) {
		String servicecurrent_time = DatesUtils.getInstance().getTimeStampToDate(servicecurrenttimestamp,
				"yyyy-MM-dd HH:mm:ss");
		String create_time = DatesUtils.getInstance().getTimeStampToDate(createtimestamp, "yyyy-MM-dd HH:mm:ss");
		String serviceexpire_time = DatesUtils.getInstance().addTime(create_time, Constans.getInstance().TIMES,
				"yyyy-MM-dd HH:mm:ss");

		String now_time = DatesUtils.getInstance().getNowTime("yyyy-MM-dd HH:mm:ss");
		int cha_time = Dates_CorrentUtils.getInstance(now_time, servicecurrent_time, "yyyy-MM-dd HH:mm:ss").cha_time;
		String servicenowtime = DatesUtils.getInstance().addSecondTime(now_time, cha_time, "yyyy-MM-dd HH:mm:ss");

		long[] shengYuTime = DatesUtils.getInstance().timeDiff2(servicenowtime, serviceexpire_time,
				"yyyy-MM-dd HH:mm:ss");
		return shengYuTime;
	}

	public String getCountdownTimes(long[] shengYuTime) {
		String mminfen;
		String mminmiao;
		String mminhour;
		if (shengYuTime[0] <= 0) {
			mminhour = "00:";
		} else {
			mminhour = "0" + shengYuTime[0] + ":";
		}
		if (shengYuTime[1] < 10) {
			mminfen = "0" + shengYuTime[1] + ":";
		} else {
			mminfen = shengYuTime[1] + ":";
		}
		if (shengYuTime[2] < 10) {
			mminmiao = "0" + shengYuTime[2];
		} else {
			mminmiao = "" + shengYuTime[2];
		}
		return mminhour + mminfen + mminmiao;
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
		if (!TextUtils.isEmpty(str) && (str.contains(oldStr) || str.contains(newStr))) {
			str = str.replace(oldStr, newStr);
		}
		return str;
	}

	/**
	 * 注意的是在对日期进行加减的时候请使用DAY_OF_YEAR 不要使用DAY_OF_MONTH
	 * 
	 * @param time
	 *            例如：13:11 2014-5-8 13:11
	 * @param format
	 *            例如：HH:mm yyyy-MM-dd HH:mm
	 * @param adds
	 *            所加的分钟
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
	 * @param time
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
	 * @param i
	 *            （0为当天，1为第二天）
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
	 * 
	 * @param i
	 *            -1过去一月,-3过去3月
	 * @param format
	 * @return
	 */
	public String getBeforeDate(int i, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, i);
		Date m = c.getTime();
		String mon = sdf.format(m);
		System.out.println("过去一个月：" + mon);
		return mon;

	}

	/**
	 * 时间格式的转换,转换显示类型
	 * 
	 * @param day
	 *            2014-11-11
	 * @param fromformat
	 *            yyyy-MM-dd
	 * @param toformat
	 *            MM/dd EE
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
	 * 在主线程里访问网络--强制的
	 */
	public void setMainThread() {
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	/**
	 * 当前的时间 format "HH"返回小时，yyyy-MM-dd HH:mm:ss 返回具体时间
	 * 
	 * @return
	 */
	public String getNowTime(String time, String format) {
		SimpleDateFormat nowsdf = new SimpleDateFormat(format);
		String nowtime = "";
		try {
			/**
			 * 得到网络当前时间格式， 时time传""
			 */
			try {
				URL url = new URL("http://www.baidu.com");
				URLConnection uc = url.openConnection();// 生成连接对象
				uc.connect(); // 发出连接
				long ld = uc.getDate(); // 取得网站日期时间
				Date date = new Date(ld); // 转换为标准时间对象
				// 分别取得时间中的小时，分钟和秒，并输出
				nowtime = nowsdf.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
		}
		return nowtime;
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
					if ((dday.before(dendtime) && dday.after(dstarttime)) || dendtime.equals(dday)
							|| dstarttime.equals(dday)) {
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
}
