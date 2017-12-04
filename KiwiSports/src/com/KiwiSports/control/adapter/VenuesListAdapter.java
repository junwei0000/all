package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesListInfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VenuesListAdapter extends BaseAdapter {

	private ArrayList<VenuesListInfo> list;
	private Activity context;

	public VenuesListAdapter(Activity context, ArrayList<VenuesListInfo> list) {
		super();
		this.context = context;
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
		VenuesListInfo stadiumObj = (VenuesListInfo) list.get(position);
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
		String name = stadiumObj.getField_name();
		String Venuestatus = stadiumObj.getVenuestatus();
		String Audit_status = stadiumObj.getAudit_status();
		// 默认价格
		viewHolder.listitem_tv_name.setText(name);
		if (position == 0) {
			viewHolder.line.setVisibility(View.INVISIBLE);
		} else {
			viewHolder.line.setVisibility(View.VISIBLE);
		}
		String tiltle = "";
		viewHolder.listitem_tv_dian.setVisibility(View.GONE);
		viewHolder.listitem_tv_status.setVisibility(View.GONE);
		if (Venuestatus.equals("0")) {
			tiltle = "场地已停用...";
			viewHolder.listitem_tv_dian.setVisibility(View.VISIBLE);
			viewHolder.listitem_tv_status.setVisibility(View.VISIBLE);
		}
		if (Audit_status.equals("0")) {
			tiltle = "正在审核中...";
			viewHolder.listitem_tv_dian.setVisibility(View.VISIBLE);
			viewHolder.listitem_tv_status.setVisibility(View.VISIBLE);
		}
		if (Audit_status.equals("-1")) {
			tiltle = "审核失败";
			viewHolder.listitem_tv_dian.setVisibility(View.VISIBLE);
			viewHolder.listitem_tv_status.setVisibility(View.VISIBLE);
		}
		viewHolder.listitem_tv_status.setText(tiltle);
		return convertView;
	}

	class ViewHolder {
		public LinearLayout line;
		public TextView listitem_tv_name;
		public TextView listitem_tv_dian;
		public TextView listitem_tv_status;
	}
}
