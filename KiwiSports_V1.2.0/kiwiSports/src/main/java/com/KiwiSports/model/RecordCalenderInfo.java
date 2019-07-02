package com.KiwiSports.model;

import com.KiwiSports.control.calendar.DateEntity;

import java.util.List;

public class RecordCalenderInfo {


	int year;
	int month;
	List<DateEntity> mDateEntityList;

	public RecordCalenderInfo(int year, int month, List<DateEntity> mDateEntityList) {
		this.year = year;
		this.month = month;
		this.mDateEntityList = mDateEntityList;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public List<DateEntity> getmDateEntityList() {
		return mDateEntityList;
	}

	public void setmDateEntityList(List<DateEntity> mDateEntityList) {
		this.mDateEntityList = mDateEntityList;
	}
}
