package com.zh.education.control.adapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.zh.education.R;
import com.zh.education.control.activity.UserLoginActivity;
import com.zh.education.model.NoticesInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;

public class NoticesAdapter extends BaseAdapter {

	private ArrayList<NoticesInfo> list;
	private Activity context;
	private ViewHolder viewHolder;

	public NoticesAdapter(Activity context, ArrayList<NoticesInfo> list) {
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

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NoticesInfo stadiumObj = (NoticesInfo) list.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fragment_notices_item, null);
			viewHolder = new ViewHolder();
			viewHolder.notices_iv_new = (ImageView) convertView
					.findViewById(R.id.notices_iv_new);
			viewHolder.notices_tv_title = (TextView) convertView
					.findViewById(R.id.notices_tv_title);
			viewHolder.notices_tv_time = (TextView) convertView
					.findViewById(R.id.notices_tv_time);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String title = stadiumObj.getTitle();
		final String timeString = stadiumObj.getCreateTime();
		final String id = stadiumObj.getId();
		viewHolder.notices_tv_title.setText(title);
		viewHolder.notices_tv_time.setText(timeString);
				String noetime = DatesUtils.getInstance().getNowTime("yyyy-MM-dd HH:mm");
				long day = DatesUtils.getInstance().timeDiff(timeString,
						noetime, "yyyy-MM-dd HH:mm");
				System.err.println(day);
				if (day > 10) {
					viewHolder.notices_iv_new.setVisibility(View.GONE);
				} else {
					SharedPreferences zhedu_spf = CommonUtils.getInstance()
							.getTextSizeSharedPrefs(context);
					Set<String> set = zhedu_spf.getStringSet("noticeSet",
							new HashSet<String>());
					Iterator<String> it = set.iterator();
					//判断是否存入
					boolean haveAdd=false;
					String keyString=id + "";
					while (it.hasNext()) {
						String str = it.next();
						if (str.contains(keyString)) {
							haveAdd=true;
							break;
						}
					}
					if(!haveAdd){
						viewHolder.notices_iv_new.setVisibility(View.VISIBLE);
					}else{
						viewHolder.notices_iv_new.setVisibility(View.GONE);
					}
					
				}
		return convertView;
	}

	public void setList(ArrayList<NoticesInfo> list) {
		this.list = list;
	}

	class ViewHolder {
		public TextView notices_tv_title;
		public ImageView notices_iv_new;
		public TextView notices_tv_time;
	}
}
