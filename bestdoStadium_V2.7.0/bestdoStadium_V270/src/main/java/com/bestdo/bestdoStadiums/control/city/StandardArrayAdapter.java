package com.bestdo.bestdoStadiums.control.city;

import java.util.ArrayList;
import java.util.List;

import com.bestdo.bestdoStadiums.model.SearchCityInfo;

import android.content.Context;
import android.widget.ArrayAdapter;

public class StandardArrayAdapter extends ArrayAdapter<SearchCityInfo> {
	List<SearchCityInfo> mhotCityList;
	ArrayList<SearchCityInfo> SourceDateList;
	String cityid_select;
	String cityid_current;
	String myselectcurrentcitystatus;
	String myLocationokstatus;
	Context context;

	public StandardArrayAdapter(Context context, int textViewResourceId, ArrayList<SearchCityInfo> SourceDateList,
			List<SearchCityInfo> mhotCityList, String cityid_select, String cityid_current,
			String myselectcurrentcitystatus, String myLocationokstatus) {
		super(context, textViewResourceId, SourceDateList);
		this.SourceDateList = SourceDateList;
		this.context = context;
		this.mhotCityList = mhotCityList;
		this.cityid_select = cityid_select;
		this.cityid_current = cityid_current;
		this.myLocationokstatus = myLocationokstatus;
		this.myselectcurrentcitystatus = myselectcurrentcitystatus;
	}

}