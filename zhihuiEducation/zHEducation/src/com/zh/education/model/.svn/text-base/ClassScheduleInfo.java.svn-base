package com.zh.education.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-24 下午1:55:27
 * @Description 类描述：
 */
public class ClassScheduleInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String Day;
	String HourStart;
	String HourEnd;
	ArrayList<ClassScheduleInfo> CoursesOfTime=new ArrayList<ClassScheduleInfo>();
	String Description;
	long BeginTime;
	long EndTime;
	String CalendarId;
	String CalendarName;
	String Address;
	String Type;
	String AppointmentType;

	public ClassScheduleInfo() {
		super();
	}

	 
/**
 * 每天的24小时
 * @param day
 * @param hourStart
 * @param hourEnd
 * @param coursesOfTime
 */
	public ClassScheduleInfo(String day, String hourStart, String hourEnd,
			ArrayList<ClassScheduleInfo> coursesOfTime) {
		super();
		Day = day;
		HourStart = hourStart;
		HourEnd = hourEnd;
		CoursesOfTime = coursesOfTime;
	}


/**
 * 每个小时的
 * @param description
 * @param beginTime
 * @param endTime
 * @param calendarId
 * @param calendarName
 * @param address
 * @param type
 * @param appointmentType
 */
	public ClassScheduleInfo(String description, long beginTime, long endTime,
			String calendarId, String calendarName, String address,
			String type, String appointmentType) {
		super();
		Description = description;
		BeginTime = beginTime;
		EndTime = endTime;
		CalendarId = calendarId;
		CalendarName = calendarName;
		Address = address;
		Type = type;
		AppointmentType = appointmentType;
	}



	public String getDay() {
		return Day;
	}



	public void setDay(String day) {
		Day = day;
	}



	public String getHourStart() {
		return HourStart;
	}



	public void setHourStart(String hourStart) {
		HourStart = hourStart;
	}



	public String getHourEnd() {
		return HourEnd;
	}



	public void setHourEnd(String hourEnd) {
		HourEnd = hourEnd;
	}



	public ArrayList<ClassScheduleInfo> getCoursesOfTime() {
		return CoursesOfTime;
	}



	public void setCoursesOfTime(ArrayList<ClassScheduleInfo> coursesOfTime) {
		CoursesOfTime = coursesOfTime;
	}



	public String getCalendarId() {
		return CalendarId;
	}

	public void setCalendarId(String calendarId) {
		CalendarId = calendarId;
	}

	public String getCalendarName() {
		return CalendarName;
	}

	public void setCalendarName(String calendarName) {
		CalendarName = calendarName;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public long getBeginTime() {
		return BeginTime;
	}

	public void setBeginTime(long beginTime) {
		BeginTime = beginTime;
	}

	public long getEndTime() {
		return EndTime;
	}

	public void setEndTime(long endTime) {
		EndTime = endTime;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getAppointmentType() {
		return AppointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		AppointmentType = appointmentType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
