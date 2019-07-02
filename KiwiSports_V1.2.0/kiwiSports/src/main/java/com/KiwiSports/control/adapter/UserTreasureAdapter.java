package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.model.VenuesTreasInfo;
import com.KiwiSports.utils.ImageLoader;
import com.KiwiSports.utils.PriceUtils;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserTreasureAdapter extends BaseAdapter {

	private ArrayList<VenuesTreasInfo> list;
	private Activity context;
	private ImageLoader asyncImageLoader;

	public UserTreasureAdapter(Activity context, ArrayList<VenuesTreasInfo> list) {
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
		VenuesTreasInfo stadiumObj = (VenuesTreasInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.user_treasure_item, null);
			viewHolder = new ViewHolder();
			viewHolder.iv_thumb = (ImageView) convertView
					.findViewById(R.id.iv_thumb);
			viewHolder.iv_name = (TextView) convertView
					.findViewById(R.id.iv_name);
			viewHolder.iv_type = (TextView) convertView
					.findViewById(R.id.iv_type);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String thumb = stadiumObj.getThumb();
		String name = stadiumObj.getName();
		String money = stadiumObj.getMoney();
		money = PriceUtils.getInstance().gteDividePrice(money, "100");
		String type = stadiumObj.getType();
		String is_exchange = stadiumObj.getIs_exchange();
		if (type.equals("3")) {// 实物
			viewHolder.iv_name.setText(name);
		} else {
			viewHolder.iv_name.setText(name + money + "元");
		}
		if (is_exchange.equals("0")) {// 未兑换
			viewHolder.iv_type.setText("未领取");
		} else {
			viewHolder.iv_type.setText("已兑换");
		}
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.iv_thumb);
		} else {
			viewHolder.iv_thumb
					.setBackgroundResource(R.drawable.map_treasure_img);
		}
		return convertView;
	}

	class ViewHolder {
		public TextView iv_name;
		public TextView iv_type;
		public ImageView iv_thumb;
	}
}
