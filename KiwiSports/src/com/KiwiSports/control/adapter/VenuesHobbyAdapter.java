package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.model.VenuesListInfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VenuesHobbyAdapter extends BaseAdapter {

	private ArrayList<HobbyInfo> list;
	private Activity context;
	String selecthobby;

	public VenuesHobbyAdapter(Activity context, String selecthobby, ArrayList<HobbyInfo> list) {
		super();
		this.context = context;
		this.list = list;
		this.selecthobby = selecthobby;
	}

	public String getSelecthobby() {
		return selecthobby;
	}

	public void setSelecthobby(String selecthobby) {
		this.selecthobby = selecthobby;
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
		HobbyInfo stadiumObj = (HobbyInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.venues_hobby_item, null);
			viewHolder = new ViewHolder();

			viewHolder.useraccount_sex_tv_famale = (TextView) convertView.findViewById(R.id.useraccount_sex_tv_famale);
			viewHolder.useraccount_sex_iv_famale = (ImageView) convertView.findViewById(R.id.useraccount_sex_iv_famale);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String Ehobby = stadiumObj.getEhobby();
		String zhobby = stadiumObj.getZhobby();
		// 默认价格
		viewHolder.useraccount_sex_tv_famale.setText(zhobby);
		if (selecthobby.contains(Ehobby)) {
			viewHolder.useraccount_sex_iv_famale.setVisibility(View.VISIBLE);
		} else {
			viewHolder.useraccount_sex_iv_famale.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	class ViewHolder {
		public TextView useraccount_sex_tv_famale;
		public ImageView useraccount_sex_iv_famale;
	}
}
