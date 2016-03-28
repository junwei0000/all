package com.zh.education.control.adapter;

import java.util.ArrayList;

import com.zh.education.R;
import com.zh.education.control.activity.BoKeCommentsActivity;
import com.zh.education.control.activity.EventsDetailActivity;
import com.zh.education.model.BoKeInfo;
import com.zh.education.model.EventsInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.ImageLoader;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventsAdapter extends BaseAdapter {
	private ImageLoader asyncImageLoader;
	private ArrayList<EventsInfo> list;
	private Activity context;

	public EventsAdapter(ArrayList<EventsInfo> list, Activity context) {
		super();
		this.list = list;
		this.context = context;
		asyncImageLoader = new ImageLoader(context, "");
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fragment_events_listview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.events_item_text_time = (TextView) convertView
					.findViewById(R.id.events_item_text_time);
			viewHolder.events_item_linear = (LinearLayout) convertView
					.findViewById(R.id.events_item_linear);
					
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		EventsInfo eventsInfo=list.get(position);
		long DayStamp=eventsInfo.getDayStamp();
		String s=DatesUtils.getInstance().getTimeStampToDate(DayStamp,"yyyy-MM-dd");
		viewHolder.events_item_text_time.setText(s);
		
		ArrayList<EventsInfo> eventsDetailInfoList=eventsInfo.getEventsDetailInfoList();
		
		if(eventsDetailInfoList!=null){
			viewHolder.events_item_linear.removeAllViews();
			
			for (int i = 0; i < eventsDetailInfoList.size(); i++) {
				EventsInfo eventsDetailInfo=eventsDetailInfoList.get(i);
				View childView=LayoutInflater.from(context).inflate(
						R.layout.fragment_events_item_linears_text, null);
				TextView events_item_text_calendarName=(TextView) childView.findViewById(R.id.events_item_text_calendarName);
				TextView events_item_text_calendarName2=(TextView) childView.findViewById(R.id.events_item_text_calendarName2);
				ImageView events_item_linears_img=(ImageView) childView.findViewById(R.id.events_item_linears_img);
				events_item_linears_img.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.event_item_sel_img));
				
				events_item_text_calendarName.setText(eventsDetailInfo.getCalendarName());
				long BeginTime=eventsDetailInfo.getBeginTime();
				String ss=DatesUtils.getInstance().getTimeStampToDate(BeginTime,"HH:mm");
				
				long EndTime=eventsDetailInfo.getBeginTime();
				String ss2=DatesUtils.getInstance().getTimeStampToDate(EndTime,"HH:mm");
				
				events_item_text_calendarName2.setText(ss+"-"+ss2);
				viewHolder.events_item_linear.addView(childView);
				
				childView.setTag(eventsDetailInfo);
				childView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						EventsInfo eventsInfo=(EventsInfo)arg0.getTag();
						
						Intent in = new Intent(context,
								EventsDetailActivity.class);
						in.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						Bundle bundle = new Bundle();
						bundle.putSerializable("eventsInfo", eventsInfo);
						in.putExtras(bundle);
						context.startActivity(in);
						context.overridePendingTransition(
								R.anim.slide_in_right, R.anim.slide_out_left);
					}
				});
			}
			
			
			
		}
			

		return convertView;
	}

	class ViewHolder {
		public TextView events_item_text_time;
		public LinearLayout events_item_linear;
	}
}
