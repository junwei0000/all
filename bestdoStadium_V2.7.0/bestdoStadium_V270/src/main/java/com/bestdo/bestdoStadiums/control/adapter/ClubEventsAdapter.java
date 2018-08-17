package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.ClubEventsInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class ClubEventsAdapter extends BaseAdapter {

	private ImageLoader asyncImageLoader;
	private ArrayList<ClubEventsInfo> list;
	private Activity context;

	public ClubEventsAdapter(Activity context, ArrayList<ClubEventsInfo> list) {
		super();
		this.context = context;
		this.list = list;
		asyncImageLoader = new ImageLoader(context, "listitem");
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
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.campaign_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.campaginlistitem_tv_date = (TextView) convertView.findViewById(R.id.campaginlistitem_tv_date);
			viewHolder.campaginlistitem_tv_centent = (TextView) convertView
					.findViewById(R.id.campaginlistitem_tv_centent);
			viewHolder.campaginlistitem_tv_date2 = (TextView) convertView.findViewById(R.id.campaginlistitem_tv_date2);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ClubEventsInfo mInfo = (ClubEventsInfo) list.get(position);
		String day = mInfo.getDay();
		String week = mInfo.getWeek();
		String name = mInfo.getName();
		String status_text = mInfo.getStatus_text();
		String status = mInfo.getAct_status();
		viewHolder.campaginlistitem_tv_date2.setVisibility(View.VISIBLE);
		if (status.equals("0") || status.equals("3")) {
			viewHolder.campaginlistitem_tv_date2.setTextColor(context.getResources().getColor(R.color.ching));
		} else if (status.equals("1")) {
			viewHolder.campaginlistitem_tv_date2.setTextColor(context.getResources().getColor(R.color.red));
		} else if (status.equals("2")) {
			viewHolder.campaginlistitem_tv_date2
					.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
		}
		viewHolder.campaginlistitem_tv_date2.setText(status_text);
		viewHolder.campaginlistitem_tv_date.setText(day + "  " + week + "  ");
		viewHolder.campaginlistitem_tv_centent.setText(name);

		return convertView;
	}

	public void setList(ArrayList<ClubEventsInfo> list) {
		this.list = list;
	}

	public void clearCache() {
		asyncImageLoader.clearCache();
	}

	class ViewHolder {
		public TextView campaginlistitem_tv_date;
		public TextView campaginlistitem_tv_centent;
		public TextView campaginlistitem_tv_date2;
	}
}
