package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.utils.LanguageUtil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainPropertyAdapter extends BaseAdapter {

	private ArrayList<MainSportInfo> list;
	private Activity context;

	public MainPropertyAdapter(Activity context, ArrayList<MainSportInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public ArrayList<MainSportInfo> getList() {
		return list;
	}

	public void setList(ArrayList<MainSportInfo> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MainSportInfo stadiumObj = (MainSportInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.main_sportproperty_item, null);
			viewHolder = new ViewHolder();
			viewHolder.listitem_tv_cname = (TextView) convertView.findViewById(R.id.listitem_tv_cname);
			viewHolder.listitem_tv_value = (TextView) convertView.findViewById(R.id.listitem_tv_value);
			viewHolder.listitem_tv_unit = (TextView) convertView.findViewById(R.id.listitem_tv_unit);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String name = stadiumObj.getCname();
		if(!LanguageUtil.idChLanguage(context)){
			name = stadiumObj.getEname();
		}
		String value = stadiumObj.getValue();
		String unit = stadiumObj.getUnit();
		// 默认价格
		viewHolder.listitem_tv_cname.setText(name);
		viewHolder.listitem_tv_value.setText(value);
		viewHolder.listitem_tv_unit.setText(unit);
		return convertView;
	}

	class ViewHolder {
		public TextView listitem_tv_cname;
		public TextView listitem_tv_value;
		public TextView listitem_tv_unit;
	}
}
