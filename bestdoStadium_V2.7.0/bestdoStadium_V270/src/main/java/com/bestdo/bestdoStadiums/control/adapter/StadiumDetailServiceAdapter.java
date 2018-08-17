package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.StadiumDetailInfo;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

/**
 * 场馆详情--场馆设施适配
 * 
 * @author jun
 * 
 */
public class StadiumDetailServiceAdapter extends BaseAdapter {

	private ArrayList<StadiumDetailInfo> list;
	private Activity context;
	private ImageLoader asyncImageLoader;

	public StadiumDetailServiceAdapter(Activity context, ArrayList<StadiumDetailInfo> list) {
		super();
		this.context = context;
		this.list = list;
		asyncImageLoader = new ImageLoader(context, "");
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
		StadiumDetailInfo stadiumObj = (StadiumDetailInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.stadium_detail_services, null);
			viewHolder = new ViewHolder();
			viewHolder.stadium_detail_services_name = (TextView) convertView
					.findViewById(R.id.stadium_detail_services_name);
			viewHolder.textView_uri = (ImageView) convertView.findViewById(R.id.stadium_detail_services_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String name = stadiumObj.getServices_name();
		String uri = stadiumObj.getServices_uri();
		viewHolder.stadium_detail_services_name.setText(name);

		asyncImageLoader.DisplayImage(uri, viewHolder.textView_uri);
		// new LoadImgBusiness(uri, viewHolder.textView_uri, new
		// GetImgCallback() {
		// public void afterImgDataGet(Bitmap bitmap) {
		// if (bitmap != null)
		// viewHolder.textView_uri.setImageBitmap(bitmap);
		// }
		// });
		return convertView;
	}

	class ViewHolder {
		public TextView stadium_detail_services_name;
		public ImageView textView_uri;

	}
}
