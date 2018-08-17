package com.bestdo.bestdoStadiums.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailMerItemPriceGolfInfo;
import com.bestdo.bestdoStadiums.model.StadiumDetailMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesInfo;
import com.bestdo.bestdoStadiums.model.StadiumVenuesPricesInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-25 下午12:05:57
 * @Description 类描述：
 */
public class StadiumDetailMerItemPriceGolfParser extends BaseParser<Object> {

	private DatesUtils dateUtils;
	private int teetimejiange;
	private String cid;

	public StadiumDetailMerItemPriceGolfParser() {
		super();
	}

	public StadiumDetailMerItemPriceGolfParser(int teetimejiange, String cid) {
		super();
		this.teetimejiange = teetimejiange;
		this.cid = cid;
	}

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		try {
			String status = jsonObject.getString("code");
			mHashMap = new HashMap<String, Object>();
			mHashMap.put("status", status);
			if (status.equals("400")) {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			} else if (status.equals("402")) {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			} else if (status.equals("401")) {
				String msg = jsonObject.getString("msg");
				mHashMap.put("msg", msg);
			} else if (status.equals("200")) {
				JSONArray jsonArray = jsonObject.optJSONArray("data");
				dateUtils = DatesUtils.getInstance();
				ArrayList<Boolean> isorderList = new ArrayList<Boolean>();

				String nowday = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
				String nowHour = DatesUtils.getInstance().getNowTime("HH");
				/**
				 * 缓存没过期的天数
				 */
				ArrayList<JSONObject> mDayList = new ArrayList<JSONObject>();
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject o = jsonArray.optJSONObject(i);
					mDayList.add(o);
				}

				/**
				 * 赋值
				 */
				String[] days = null;
				String[] daysshow = null;
				String[][] hoursgolf = null;
				String[][] hoursgolfange = null;
				if (mDayList != null && mDayList.size() > 0) {
					days = new String[mDayList.size()];
					daysshow = new String[mDayList.size()];
					hoursgolf = new String[mDayList.size()][];
					hoursgolfange = new String[mDayList.size()][];
					for (int i = 0; i < mDayList.size(); i++) {
						JSONObject o = mDayList.get(i);
						int datetamp = o.optInt("date", 0);
						String backday = dateUtils.getTimeStampToDate(datetamp, "yyyy-MM-dd");
						boolean doequeday = DatesUtils.getInstance().doDateEqual(nowday, backday, "yyyy-MM-dd");
						days[i] = backday;
						daysshow[i] = dateUtils.getDateGeShi(backday, "yyyy-MM-dd", "MM月dd日 EE");
						if (daysshow[i].contains("星期")) {
							daysshow[i] = daysshow[i].replace("星期", "周");
						}

						ArrayList<JSONObject> mDayHourList = new ArrayList<JSONObject>();
						JSONArray hourArray = o.optJSONArray("hour");
						for (int j = 0; j < hourArray.length(); j++) {
							JSONObject hourobj = hourArray.optJSONObject(j);
							// int start_hour = hourobj.optInt("start_hour");
							// int end_hour = hourobj.optInt("end_hour");
							// 过了打球时段的开始时间不能进行预订
							// boolean timestaus;
							// if
							// (cid.equals(Constans.getInstance().sportCidGolf))
							// {
							// timestaus = DatesUtils.getInstance()
							// .doCheck2Date(nowHour, "" + end_hour,
							// "HH");
							// } else {
							// timestaus =true;//练习场不过滤
							// // timestaus = DatesUtils.getInstance()
							// // .doCheck2Date(nowHour, "" + start_hour,
							// // "HH");
							// }
							// if (timestaus) {
							mDayHourList.add(hourobj);
							// }
						}
						if (mDayHourList != null && mDayHourList.size() > 0) {
							ArrayList<String> itemhoursBuffer = new ArrayList<String>();
							hoursgolfange[i] = new String[mDayHourList.size()];
							for (int k = 0; k < mDayHourList.size(); k++) {
								JSONObject hourobj = mDayHourList.get(k);
								int start_hour = hourobj.optInt("start_hour");
								int end_hour = hourobj.optInt("end_hour");
								String starthourgolfange = dateUtils.getDateGeShi(start_hour + "", "H", "HH:mm");
								String endhourgolfange = end_hour + ":00";
								if (end_hour != 24) {
									endhourgolfange = dateUtils.getDateGeShi(end_hour + "", "H", "HH:mm");
								}
								boolean usernowtimestaus = DatesUtils.getInstance().doCheck2Date(nowHour,
										"" + start_hour, "HH");
								if (doequeday) {
									// 当天判断小时
									if (!usernowtimestaus) {
										dateUtils.setplayTimes(doequeday, itemhoursBuffer, Integer.valueOf(nowHour),
												end_hour, teetimejiange);
									} else {
										dateUtils.setplayTimes(doequeday, itemhoursBuffer, start_hour, end_hour,
												teetimejiange);
									}
								} else {
									dateUtils.setplayTimes(doequeday, itemhoursBuffer, start_hour, end_hour,
											teetimejiange);
								}

								hoursgolfange[i][k] = starthourgolfange + "-" + endhourgolfange;
							}
							hoursgolf[i] = new String[itemhoursBuffer.size()];
							for (int j = 0; j < itemhoursBuffer.size(); j++) {
								hoursgolf[i][j] = itemhoursBuffer.get(j);
							}
						}
						isorderList.add(true);
					}
				}

				StadiumDetailMerItemPriceGolfInfo minfo = new StadiumDetailMerItemPriceGolfInfo(daysshow, days,
						hoursgolf, hoursgolfange, isorderList);
				mHashMap.put("mMerItemPriceGolfInfo", minfo);

			}
		} catch (Exception e) {
		}
		return mHashMap;
	}
}
