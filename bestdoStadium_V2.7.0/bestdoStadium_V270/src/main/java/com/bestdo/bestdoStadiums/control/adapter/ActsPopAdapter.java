package com.bestdo.bestdoStadiums.control.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.ActInfoMapper;
import com.bestdo.bestdoStadiums.model.ActInfoMapper.ActInfo;
import com.bestdo.bestdoStadiums.utils.ImageLoader;

import java.util.List;

/**
 * Created by YuHua on 2017/5/23 19:07 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class ActsPopAdapter extends BaseAdapter {
	private Context context;
	private List<ActInfo> actInfos;
	private ImageLoader mImageLoader;

	public ActsPopAdapter(Context context, List<ActInfo> actInfos) {
		this.context = context;
		this.actInfos = actInfos;
		mImageLoader = new ImageLoader(context, "headImg");
	}

	@Override
	public int getCount() {
		return actInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return actInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_pop_walk, parent, false);
			holder = new ViewHolder();
			initHolder(holder, convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (actInfos != null && !actInfos.isEmpty()) {
			ActInfoMapper.ActInfo actInfo = actInfos.get(position);
			loadImage(holder.imageView, actInfo.url);
			holder.tvName.setText(actInfo.name);
			holder.tvState.setText(actInfo.state);
			holder.tvTime.setText(actInfo.time);
			holder.tvCount.setText(actInfo.count);
		}
		return convertView;
	}

	private void loadImage(ImageView imageView, String url) {
		if (TextUtils.isEmpty(url)) {
			url = "";
		}
		mImageLoader.DisplayImage(url, imageView);
	}

	private void initHolder(ViewHolder holder, View convertView) {
		holder.imageView = (ImageView) convertView.findViewById(R.id.img_act_icon);
		holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
		holder.tvState = (TextView) convertView.findViewById(R.id.tv_state);
		holder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
		holder.tvCount = (TextView) convertView.findViewById(R.id.tv_count);
	}

	class ViewHolder {
		ImageView imageView;
		TextView tvName;
		TextView tvState;
		TextView tvTime;
		TextView tvCount;
	}
}
