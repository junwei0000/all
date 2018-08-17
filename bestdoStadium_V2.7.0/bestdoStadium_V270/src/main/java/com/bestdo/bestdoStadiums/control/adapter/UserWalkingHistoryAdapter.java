package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.UserWalkingHistoryInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class UserWalkingHistoryAdapter extends BaseAdapter {

	private ArrayList<UserWalkingHistoryInfo> list;
	private Activity context;

	public UserWalkingHistoryAdapter(Activity context, ArrayList<UserWalkingHistoryInfo> list) {
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
		UserWalkingHistoryInfo mHistoryInfo = (UserWalkingHistoryInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.user_walking_history_item, null);
			viewHolder = new ViewHolder();
			viewHolder.history_item_layout_otherinfo = (LinearLayout) convertView
					.findViewById(R.id.history_item_layout_otherinfo);
			viewHolder.history_item_tv_date = (TextView) convertView.findViewById(R.id.history_item_tv_date);
			viewHolder.history_item_tv_distance = (TextView) convertView.findViewById(R.id.history_item_tv_distance);
			viewHolder.history_item_tv_speed = (TextView) convertView.findViewById(R.id.history_item_tv_speed);
			viewHolder.history_item_tv_time = (TextView) convertView.findViewById(R.id.history_item_tv_time);
			viewHolder.history_item_tv_calories = (TextView) convertView.findViewById(R.id.history_item_tv_calories);

			viewHolder.history_item_layout_firstinfo = (LinearLayout) convertView
					.findViewById(R.id.history_item_layout_firstinfo);
			viewHolder.first_tv_speed = (TextView) convertView.findViewById(R.id.first_tv_speed);
			viewHolder.first_tv_time = (TextView) convertView.findViewById(R.id.first_tv_time);
			viewHolder.first_tv_distance = (TextView) convertView.findViewById(R.id.first_tv_distance);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (position == 0) {
			viewHolder.history_item_layout_firstinfo.setVisibility(View.VISIBLE);
			viewHolder.history_item_layout_otherinfo.setVisibility(View.GONE);
		} else {
			viewHolder.history_item_layout_firstinfo.setVisibility(View.GONE);
			viewHolder.history_item_layout_otherinfo.setVisibility(View.VISIBLE);
		}

		String hh = DatesUtils.getInstance().getTimeStampToDate(mHistoryInfo.getTimestamp(), "HH:mm");
		String distance = mHistoryInfo.getKm_num();
		viewHolder.history_item_tv_distance.setText(hh + " 跑步" + distance + "公里");
		String date = DatesUtils.getInstance().getTimeStampToDate(mHistoryInfo.getTimestamp(), "MM月dd日");
		viewHolder.history_item_tv_date.setText(date);
		String speed = mHistoryInfo.getV_num();
		if (TextUtils.isEmpty(speed)) {
			speed = "00";
		}
		viewHolder.history_item_tv_speed.setText(speed);
		String time = mHistoryInfo.getStep_time();
		viewHolder.history_item_tv_time.setText(time);

		String calories = mHistoryInfo.getStep_num();
		if (TextUtils.isEmpty(calories)) {
			calories = "00";
		}
		viewHolder.history_item_tv_calories.setText(calories);

		viewHolder.first_tv_distance.setText(distance);
		viewHolder.first_tv_time.setText(time);
		viewHolder.first_tv_speed.setText(speed);
		return convertView;
	}

	public void setList(ArrayList<UserWalkingHistoryInfo> list) {
		this.list = list;
	}

	class ViewHolder {
		public LinearLayout history_item_layout_otherinfo;
		public TextView history_item_tv_date;
		public TextView history_item_tv_distance;
		public TextView history_item_tv_speed;
		public TextView history_item_tv_time;
		public TextView history_item_tv_calories;

		public LinearLayout history_item_layout_firstinfo;
		public TextView first_tv_speed;
		public TextView first_tv_time;
		public TextView first_tv_distance;
	}
}
