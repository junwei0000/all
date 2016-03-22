package com.zh.education.model;

import java.io.Serializable;
import java.util.ArrayList;

public class EventsInfo implements Serializable{

	private long DayStamp;
	
	private String Description;
	
	private long BeginTime;
	private String Type;
	private String Address;
	private String AppointmentType;
	private String CalendarId;
	private long EndTime;
	private String CalendarName;
	
	private ArrayList<EventsInfo> eventsDetailInfoList;
	

	public ArrayList<EventsInfo> getEventsDetailInfoList() {
		return eventsDetailInfoList;
	}

	public void setEventsDetailInfoList(ArrayList<EventsInfo> eventsDetailInfoList) {
		this.eventsDetailInfoList = eventsDetailInfoList;
	}

	public long getDayStamp() {
		return DayStamp;
	}

	public void setDayStamp(long dayStamp) {
		DayStamp = dayStamp;
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

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getAppointmentType() {
		return AppointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		AppointmentType = appointmentType;
	}

	public String getCalendarId() {
		return CalendarId;
	}

	public void setCalendarId(String calendarId) {
		CalendarId = calendarId;
	}

	public long getEndTime() {
		return EndTime;
	}

	public void setEndTime(long endTime) {
		EndTime = endTime;
	}

	public String getCalendarName() {
		return CalendarName;
	}

	public void setCalendarName(String calendarName) {
		CalendarName = calendarName;
	}


	
	
}
