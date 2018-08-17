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
import com.bestdo.bestdoStadiums.model.UserCollectInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-19 下午6:05:31
 * @Description 类描述：我的收藏列表适配
 */
public class UserCollectAdapter extends BaseAdapter {

	private ImageLoader asyncImageLoader;
	private ArrayList<UserCollectInfo> list;
	private Activity context;

	public UserCollectAdapter(Activity context, ArrayList<UserCollectInfo> list) {
		super();
		this.context = context;
		this.list = list;
		asyncImageLoader = new ImageLoader(context, "listitem");
	}

	public void setList(ArrayList<UserCollectInfo> list) {
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
		UserCollectInfo stadiumObj = (UserCollectInfo) list.get(position);
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
		String venuename = stadiumObj.getVenueName();
		String minprice = stadiumObj.getMinPrice();
		String maxprice = stadiumObj.getMaxPrice();
		String region = stadiumObj.getRegion();
		String thumb = stadiumObj.getThumb();

		viewHolder.stadiummaplist_item_name.setText(venuename);
		viewHolder.stadiummaplist_item_address.setText(region);
		if (!TextUtils.isEmpty(region) && region.contains("-")) {
			int firstindex = region.indexOf("-");
			region = region.substring(firstindex + 1);
			String[] dfStrings = region.split("-");
			if (dfStrings != null && dfStrings.length == 2) {
				String showString = "<font color='#333333'>" + "</font> " + dfStrings[1];
				viewHolder.stadiummaplist_item_address.setText(Html.fromHtml(showString));
			}
		}
		viewHolder.stadiummaplist_item_originprice.setText("会员价：¥" + maxprice);
		// int hei = ConfigUtils.getInstance().dip2px(context, 60);
		// int wh = ConfigUtils.getInstance().dip2px(context, 80);
		// viewHolder.stadiummaplist_item_img.setLayoutParams(new
		// LayoutParams(wh,
		// hei));
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.stadiummaplist_item_img);
		} else {
			viewHolder.stadiummaplist_item_img.setBackgroundResource(R.drawable.listitem_none);
		}
		return convertView;
	}

	public void clearCache() {
		asyncImageLoader.clearCache();
	}

	class ViewHolder {
		public ImageView stadiummaplist_item_img;
		public TextView stadiummaplist_item_name;
		public TextView stadiummaplist_item_address;
		public TextView stadiummaplist_item_distance;
		public TextView stadiummaplist_item_originprice;
	}
}
