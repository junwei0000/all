package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.business.RecordDetailYouBusiness.GetRecordDetailYouCallback;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.PriceUtils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecordListAdapter extends BaseAdapter {

	private ArrayList<RecordInfo> list;
	private Activity context;
	boolean lopCointStatus;

	public RecordListAdapter(Activity context, ArrayList<RecordInfo> list, boolean lopCointStatus) {
		super();
		this.context = context;
		this.list = list;
		this.lopCointStatus = lopCointStatus;
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
		RecordInfo mInfo = (RecordInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.record_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.line = (LinearLayout) convertView.findViewById(R.id.line);
			viewHolder.listitem_tv_name = (TextView) convertView.findViewById(R.id.listitem_tv_name);
			viewHolder.listitem_tv_lopcount = (TextView) convertView.findViewById(R.id.listitem_tv_lopcount);

			viewHolder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);
			viewHolder.tv_runtime = (TextView) convertView.findViewById(R.id.tv_runtime);
			viewHolder.tv_speed = (TextView) convertView.findViewById(R.id.tv_speed);
			viewHolder.item_iv_sporttype = (ImageView) convertView.findViewById(R.id.item_iv_sporttype);
			viewHolder.listitem_tv_createtime = (TextView) convertView.findViewById(R.id.listitem_tv_createtime);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CommonUtils.getInstance().setTextViewTypeface(context, viewHolder.tv_distance);
		CommonUtils.getInstance().setTextViewTypeface(context, viewHolder.tv_runtime);
		CommonUtils.getInstance().setTextViewTypeface(context, viewHolder.tv_speed);
		String name = mInfo.getPos_name();
		String distance = mInfo.getDistanceTraveled();
		String speed = mInfo.getAverageSpeed();
		String createtime = mInfo.getCreate_time();
		long runtime = mInfo.getDuration();
		String time = DatesUtils.getInstance().formatTimes(runtime*1000);
		viewHolder.tv_distance.setText(distance);
		viewHolder.tv_speed.setText(PriceUtils.getInstance().getPriceTwoDecimal(Double.valueOf(speed), 2));
		viewHolder.tv_runtime.setText(time);
		viewHolder.listitem_tv_createtime.setText(createtime);

		String sportsType = mInfo.getSportsType();

		if (sportsType.equals("run")) {
			viewHolder.item_iv_sporttype.setBackgroundResource(R.drawable.record_img_run);
		} else if (sportsType.equals("riding")) {
			viewHolder.item_iv_sporttype.setBackgroundResource(R.drawable.record_img_riding);
		} else if (sportsType.equals("walk")) {
			viewHolder.item_iv_sporttype.setBackgroundResource(R.drawable.record_img_walk);
		} else if (sportsType.equals("sky")) {
			viewHolder.item_iv_sporttype.setBackgroundResource(R.drawable.record_img_sky);
		} else {
			viewHolder.item_iv_sporttype.setBackgroundResource(R.drawable.record_img_drive);
		}
		String lopCoint = mInfo.getLopCount();
		viewHolder.listitem_tv_lopcount.setText(context.getResources().getString(R.string.di) + lopCoint
				+ context.getResources().getString(R.string.quan));
		if (lopCointStatus&&!TextUtils.isEmpty(lopCoint)) {
			viewHolder.listitem_tv_lopcount.setVisibility(View.VISIBLE);
			viewHolder.listitem_tv_name.setText(name+" #"+lopCoint);
		} else {
			viewHolder.listitem_tv_name.setText(name);
			viewHolder.listitem_tv_lopcount.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		public LinearLayout line;
		public TextView listitem_tv_name;
		public TextView listitem_tv_lopcount;

		public TextView tv_distance;
		public TextView tv_runtime;
		public TextView tv_speed;
		public TextView listitem_tv_createtime;
		public ImageView item_iv_sporttype;
	}
}
