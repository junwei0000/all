package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesRankTodayInfo;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ImageLoader;
import com.KiwiSports.utils.PriceUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class VenuesRankAdapter extends BaseAdapter {

	private ArrayList<VenuesRankTodayInfo> list;
	private Activity context;
	private ImageLoader asyncImageLoader;
	private int max;

	public VenuesRankAdapter(Activity context,
			ArrayList<VenuesRankTodayInfo> list, int max) {
		super();
		this.context = context;
		this.list = list;
		this.max = max;
		if (max == 0) {
			max = 10000;
		}
		asyncImageLoader = new ImageLoader(context, "headImg");
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
		VenuesRankTodayInfo mInfo = (VenuesRankTodayInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.venues_rank_item, null);
			viewHolder = new ViewHolder();
			viewHolder.line = (LinearLayout) convertView
					.findViewById(R.id.line);
			viewHolder.rankitem_tv_num = (TextView) convertView
					.findViewById(R.id.rankitem_tv_num);
			viewHolder.rankitem_iv_head = (CircleImageView) convertView
					.findViewById(R.id.rankitem_iv_head);
			viewHolder.rankitem_tv_name = (TextView) convertView
					.findViewById(R.id.rankitem_tv_name);
			viewHolder.rankitem_tv_distance = (TextView) convertView
					.findViewById(R.id.rankitem_tv_distance);
			viewHolder.progressBar = (ProgressBar) convertView
					.findViewById(R.id.progressBar);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String name = mInfo.getNick_name();

		String num = mInfo.getNum();
		String album_url = mInfo.getAlbum_url();
		viewHolder.rankitem_tv_num.setText(num);
		/**
		 * 1：匿名； 0：实名
		 */
		int is_anonymous = mInfo.getIs_anonymous();
		if (is_anonymous == 1) {
			album_url = "";
			name = "匿名雪友";
		}
		if (position == 0) {
			viewHolder.rankitem_tv_name.setText(CommonUtils.getInstance()
					.getString(context, R.string.venues_rank_mi));
			viewHolder.line.setVisibility(View.VISIBLE);
		} else {
			viewHolder.rankitem_tv_name.setText(name);
			viewHolder.line.setVisibility(View.INVISIBLE);
		}
		if (!TextUtils.isEmpty(album_url)) {
			asyncImageLoader.DisplayImage(album_url,
					viewHolder.rankitem_iv_head);
		} else {
			Bitmap mBitmap = asyncImageLoader.readBitMap(context,
					R.drawable.ic_launcher);
			viewHolder.rankitem_iv_head.setImageBitmap(mBitmap);
		}
		double diatance = mInfo.getDistanceTraveled();
		viewHolder.progressBar.setMax(max);
		viewHolder.progressBar.setProgress((int) diatance);
		diatance = diatance / 1000;
		viewHolder.rankitem_tv_distance.setText(PriceUtils.getInstance()
				.formatFloatNumber(diatance) + "km");
		return convertView;
	}

	class ViewHolder {
		public LinearLayout line;
		public TextView rankitem_tv_num;
		public CircleImageView rankitem_iv_head;
		public TextView rankitem_tv_name;
		public TextView rankitem_tv_distance;
		public ProgressBar progressBar;
	}
}
