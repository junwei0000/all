package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.model.VenuesRankTodayInfo;
import com.KiwiSports.utils.CircleImageView;
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

	public VenuesRankAdapter(Activity context, ArrayList<VenuesRankTodayInfo> list) {
		super();
		this.context = context;
		this.list = list;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.venues_rank_item, null);
			viewHolder = new ViewHolder();
			viewHolder.line = (LinearLayout) convertView.findViewById(R.id.line);
			viewHolder.rankitem_tv_num = (TextView) convertView.findViewById(R.id.rankitem_tv_num);
			viewHolder.rankitem_iv_head = (CircleImageView) convertView.findViewById(R.id.rankitem_iv_head);
			viewHolder.rankitem_tv_name = (TextView) convertView.findViewById(R.id.rankitem_tv_name);
			viewHolder.rankitem_tv_distance = (TextView) convertView.findViewById(R.id.rankitem_tv_distance);
			viewHolder.progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String name = mInfo.getNick_name();
		String diatance = mInfo.getDistanceTraveled();
		diatance = PriceUtils.getInstance().gteDividePrice(diatance, "1000");
		diatance = PriceUtils.getInstance().seePrice(diatance);
		String num = mInfo.getNum();
		String time = mInfo.getDate_time();
		String album_url = mInfo.getAlbum_url();
		viewHolder.rankitem_tv_num.setText(num);
		viewHolder.rankitem_tv_distance.setText(diatance + "km");
		if (position == 0) {
			viewHolder.rankitem_tv_name.setText("我");
			viewHolder.line.setVisibility(View.VISIBLE);
		} else {
			viewHolder.rankitem_tv_name.setText(name);
			viewHolder.line.setVisibility(View.INVISIBLE);
		}
		if (!TextUtils.isEmpty(album_url)) {
			asyncImageLoader.DisplayImage(album_url, viewHolder.rankitem_iv_head);
		} else {
			Bitmap mBitmap = asyncImageLoader.readBitMap(context, R.drawable.user_default_icon);
			viewHolder.rankitem_iv_head.setImageBitmap(mBitmap);
		}
		viewHolder.progressBar.setProgress(Integer.parseInt(diatance));
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
