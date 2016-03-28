package com.zh.education.control.adapter;

import java.util.ArrayList;

import com.zh.education.R;
import com.zh.education.control.activity.BoKeCommentsActivity;
import com.zh.education.model.BoKeInfo;
import com.zh.education.model.PingLunInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.ImageLoader;

import android.app.Activity;
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

public class BoKeCommentsAdapter extends BaseAdapter {
	private ImageLoader asyncImageLoader;
	private ArrayList<PingLunInfo> list;
	private Activity context;

	public BoKeCommentsAdapter(ArrayList<PingLunInfo> list, Activity context) {
		super();
		this.list = list;
		this.context = context;
		asyncImageLoader = new ImageLoader(context, "listitem");
	}

	public ArrayList<PingLunInfo> getList() {
		return list;
	}

	public void setList(ArrayList<PingLunInfo> list) {
		this.list = list;
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
					R.layout.fragment_boke_comments_item, null);
			viewHolder = new ViewHolder();
			viewHolder.pinlun_list_item_img_icon = (ImageView) convertView
					.findViewById(R.id.pinlun_list_item_img_icon);
			viewHolder.pinlun_list_item_name = (TextView) convertView
					.findViewById(R.id.pinlun_list_item_name);
			viewHolder.pinlun_list_item_time = (TextView) convertView
					.findViewById(R.id.pinlun_list_item_time);
			viewHolder.pinlun_list_item_text_context = (TextView) convertView
					.findViewById(R.id.pinlun_list_item_text_context);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		PingLunInfo boKeInfo = list.get(position);
		int id = boKeInfo.getId();
		String Content = boKeInfo.getContent();
		String Title = boKeInfo.getTitle();
		String CreateUser = boKeInfo.getCreateUser();
		String HeadImg = boKeInfo.getHeadImg();
		long CreateTime = boKeInfo.getCreateTime();

		viewHolder.pinlun_list_item_name.setText(CreateUser);
		String time = DatesUtils.getInstance().getTimeStampToDate(CreateTime,
				"yyyy-mm-dd HH:mm");
		viewHolder.pinlun_list_item_time.setText(time);
		viewHolder.pinlun_list_item_text_context.setText(Content);

		if (TextUtils.isEmpty(HeadImg) || HeadImg.equals("null")) {
			viewHolder.pinlun_list_item_img_icon
					.setBackgroundResource(R.drawable.usercenter_headbg);
		} else {
			asyncImageLoader.DisplayImage(HeadImg,
					viewHolder.pinlun_list_item_img_icon);
		}

		return convertView;
	}

	class ViewHolder {
		public ImageView pinlun_list_item_img_icon;
		public TextView pinlun_list_item_name;
		public TextView pinlun_list_item_time;
		public TextView pinlun_list_item_text_context;
	}
}
