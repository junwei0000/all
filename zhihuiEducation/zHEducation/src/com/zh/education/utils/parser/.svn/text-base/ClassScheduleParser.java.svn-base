package com.zh.education.utils.parser;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Color;
import android.text.TextUtils;

import com.zh.education.R;
import com.zh.education.model.ClassScheduleInfo;
import com.zh.education.utils.ConfigUtils;
import com.zh.education.utils.DatesUtils;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-20 下午12:06:00
 * @Description 类描述：
 */
public class ClassScheduleParser extends BaseParser<Object> {

	@Override
	public HashMap<String, Object> parseJSON(JSONObject jsonObject) {
		HashMap<String, Object> mHashMap = null;
		HashMap<String, String> mschedulecoursecolorMap = null;
		
		int oldDayI=-1;
		try {
			String status = jsonObject.getString("status");
			mHashMap = new HashMap<String, Object>();
			mschedulecoursecolorMap = new HashMap<String, String>();
			mHashMap.put("status", status);
			if (status.equals("200")) {
				ArrayList<ArrayList<ClassScheduleInfo>> mAllDayList = new ArrayList<ArrayList<ClassScheduleInfo>>();
				JSONArray NoticesArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < NoticesArray.length(); i++) {
					JSONObject noticesobj = NoticesArray.getJSONObject(i);
					String day = DatesUtils.getInstance().getTimeStampToDate(
							noticesobj.optLong("DayStamp"), "yyyy-MM-dd");
					/**
					 * 每天24小时
					 */
					JSONArray HourListArray = noticesobj
							.getJSONArray("HourList");
					ArrayList<ClassScheduleInfo> hourList = new ArrayList<ClassScheduleInfo>();
					for (int j = 0; j < HourListArray.length(); j++) {
						JSONObject hourObj = HourListArray.getJSONObject(j);
						String hourEnd = hourObj.optString("HourEnd");
						String hourStart = hourObj.optString("HourStart");

						/**
						 * 每个小时中的多个Courses
						 */
						ArrayList<ClassScheduleInfo> coursesOfTime = null;
						try {
							JSONArray CoursesArray = hourObj
									.getJSONArray("Courses");
							coursesOfTime = new ArrayList<ClassScheduleInfo>();
							for (int k = 0; k < CoursesArray.length(); k++) {
								JSONObject CoursesObj = CoursesArray
										.getJSONObject(k);
								String description = CoursesObj
										.optString("Description");
								long beginTime = CoursesObj
										.optLong("BeginTime");
								long endTime = CoursesObj.optLong("EndTime");
								String type = CoursesObj.optString("Type");
								String address = CoursesObj
										.optString("Address");
								String appointmentType = CoursesObj
										.optString("AppointmentType");
								String calendarId = CoursesObj
										.optString("CalendarId");
								String calendarName = CoursesObj
										.optString("CalendarName");
								ClassScheduleInfo mClassScheduleInfo = new ClassScheduleInfo(
										description, beginTime, endTime,
										calendarId, calendarName, address,
										type, appointmentType);
								coursesOfTime.add(mClassScheduleInfo);
								mClassScheduleInfo = null;
								/**
								 * 自定义颜色************************************************
								 */
								if (!TextUtils.isEmpty(calendarName)&&!mschedulecoursecolorMap
										.containsKey(i+">"+calendarName)) {
										String[] colorArray=ConfigUtils.colorArray;
										if(mschedulecoursecolorMap.size()<20)
										mschedulecoursecolorMap.put(i+">"+calendarName, colorArray[mschedulecoursecolorMap.size()]);
								System.err.println(mschedulecoursecolorMap.toString());
								System.err.println("************************************************");
								}

							}
						} catch (Exception e) {
						}

						ClassScheduleInfo mClassScheduleInfo = new ClassScheduleInfo(
								day, hourStart, hourEnd, coursesOfTime);
						hourList.add(mClassScheduleInfo);
						mClassScheduleInfo = null;
					}
					mAllDayList.add(hourList);
					oldDayI=i;
				}
				mHashMap.put("mschedulecoursecolorMap", mschedulecoursecolorMap);
				mHashMap.put("mList", mAllDayList);
			} else {
				String msg = jsonObject.optString("msg");
				mHashMap.put("msg", msg);
			}
		} catch (Exception e) {
		}

		return mHashMap;
	}

}
