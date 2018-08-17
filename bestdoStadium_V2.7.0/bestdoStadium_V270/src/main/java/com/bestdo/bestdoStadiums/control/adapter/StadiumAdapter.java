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

import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class StadiumAdapter extends BaseAdapter {

	public ImageLoader asyncImageLoader;
	private ArrayList<StadiumInfo> list;
	private Activity context;
	String unnit;

	public StadiumAdapter(Activity context, ArrayList<StadiumInfo> list, String unnit) {
		super();
		this.context = context;
		this.list = list;
		this.unnit = unnit;
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
		StadiumInfo stadiumObj = (StadiumInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.stadiumlist_item, null);
			viewHolder = new ViewHolder();
			viewHolder.stadiummaplist_item_img = (ImageView) convertView.findViewById(R.id.stadiummaplist_item_img);
			viewHolder.stadiummaplist_item_name = (TextView) convertView.findViewById(R.id.stadiummaplist_item_name);
			viewHolder.stadiummaplist_item_address = (TextView) convertView
					.findViewById(R.id.stadiummaplist_item_address);
			viewHolder.stadiummaplist_item_distance = (TextView) convertView
					.findViewById(R.id.stadiummaplist_item_distance);
			viewHolder.stadiummaplist_item_originprice = (TextView) convertView
					.findViewById(R.id.stadiummaplist_item_originprice);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String stadium_name = stadiumObj.getName();
		String region = stadiumObj.getRegion();
		String thumb = stadiumObj.getThumb();
		String price = stadiumObj.getPrice();
		// 默认价格
		viewHolder.stadiummaplist_item_originprice.setText("会员价：¥" + price);
		viewHolder.stadiummaplist_item_name.setText(stadium_name);
		viewHolder.stadiummaplist_item_address.setText(region);
		if (!TextUtils.isEmpty(region) && region.contains("-")) {
			int firstindex = region.indexOf("-");
			region = region.substring(firstindex + 1);
			String[] dfStrings = region.split("-");
			if (dfStrings != null && dfStrings.length == 2) {
				// String showString = "<font color='#333333'>" + dfStrings[0]
				// + "</font> " + dfStrings[1];
				// viewHolder.stadiummaplist_item_address.setText(Html
				// .fromHtml(showString));
				viewHolder.stadiummaplist_item_address.setText(dfStrings[1]);
			}
		}
		String diss = stadiumObj.getGeodist();
		if (!TextUtils.isEmpty(diss)) {
			diss = PriceUtils.getInstance().gteDividePrice(diss, "1000");
			String disss = PriceUtils.getInstance().getPriceTwoDecimal(Double.valueOf(diss), 2);
			viewHolder.stadiummaplist_item_distance.setText(disss + "km");
		} else {
			viewHolder.stadiummaplist_item_distance.setText("");
		}
		// int he = ConfigUtils.getInstance().dip2px(context, 60);
		// int wh = ConfigUtils.getInstance().dip2px(context, 80);
		// viewHolder.stadiummaplist_item_img.setLayoutParams(new
		// LayoutParams(wh,
		// he));
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.stadiummaplist_item_img);
		} else {
			viewHolder.stadiummaplist_item_img.setImageBitmap(asyncImageLoader.bitmap_orve);
		}
		return convertView;
	}

	public void setList(ArrayList<StadiumInfo> list) {
		this.list = list;
	}

	public void clearCache() {
		asyncImageLoader.clearCache();
	}

	class ViewHolder {
		public ImageView stadiummaplist_item_img;
		public TextView stadiummaplist_item_name;
		public TextView stadiummaplist_item_address;
		public TextView stadiummaplist_item_jieshao;
		public TextView stadiummaplist_item_distance;
		public TextView stadiummaplist_item_originprice;
	}
}
