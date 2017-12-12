package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesListInfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecordListAdapter extends BaseAdapter {

	private ArrayList<RecordInfo> list;
	private Activity context;

	public RecordListAdapter(Activity context, ArrayList<RecordInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public ArrayList<RecordInfo> getList() {
		return list;
	}

	public void setList(ArrayList<RecordInfo> list) {
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
		RecordInfo stadiumObj = (RecordInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.venues_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.line = (LinearLayout) convertView.findViewById(R.id.line);
			viewHolder.listitem_tv_name = (TextView) convertView.findViewById(R.id.listitem_tv_name);
			viewHolder.listitem_tv_dian = (TextView) convertView.findViewById(R.id.listitem_tv_dian);
			viewHolder.listitem_tv_status = (TextView) convertView.findViewById(R.id.listitem_tv_status);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		return convertView;
	}

	class ViewHolder {
		public LinearLayout line;
		public TextView listitem_tv_name;
		public TextView listitem_tv_dian;
		public TextView listitem_tv_status;
	}
}
