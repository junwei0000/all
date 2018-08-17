package com.bestdo.bestdoStadiums.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.control.adapter.BestDoSportAdapter.ViewHolder;

import java.util.List;

/**
 * poi适配器
 */
public class PoiAdapter extends BaseAdapter {
	private Context context;
	private List<PoiInfo> pois;
	private LinearLayout linearLayout;
	private ImageView lacation_select_img;
	private int selectIndex;

	public int getSelectIndex() {
		return selectIndex;
	}

	public void setSelectIndex(int selectIndex) {
		this.selectIndex = selectIndex;
	}

	public PoiAdapter(Context context, List<PoiInfo> pois) {
		this.context = context;
		this.pois = pois;
	}

	@Override
	public int getCount() {
		return pois.size();
	}

	@Override
	public Object getItem(int position) {
		return pois.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.locationpois_item, null);
			holder.locationpoi_name = (TextView) convertView.findViewById(R.id.locationpois_name);
			holder.lacation_select_img = (ImageView) convertView.findViewById(R.id.lacation_select_img);
			holder.locationpoi_address = (TextView) convertView.findViewById(R.id.locationpois_address);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		PoiInfo poiInfo = pois.get(position);
		String address = poiInfo.address;
		if (selectIndex == position) {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.page_bg));
			holder.lacation_select_img.setVisibility(View.VISIBLE);
		} else {
			convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
			holder.lacation_select_img.setVisibility(View.GONE);
		}
		if (position == 0) {
			holder.locationpoi_name.setText("[位置]");
			// ImageView imageView = new ImageView(context);
			// ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(32,
			// 32);
			// imageView.setLayoutParams(params);
			// imageView.setBackgroundColor(Color.TRANSPARENT);
			// imageView.setImageResource(R.drawable.baidumap_ico_poi_on);
			// imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			// linearLayout.addView(imageView, 0, params);
			// holder.locationpoi_name.setTextColor(Color.parseColor("#FF5722"));
		} else {
			holder.locationpoi_name.setText(poiInfo.name);
		}
		if (TextUtils.isEmpty(address) || address.equals(" ")) {
			holder.locationpoi_address.setText(poiInfo.name);
		} else {
			holder.locationpoi_address.setText(address);
		}
		Log.e("poiInfo==",
				"position=" + position + "    " + "name=" + poiInfo.name + "    ;address=" + poiInfo.address);
		return convertView;
	}

	class ViewHolder {
		TextView locationpoi_name;
		TextView locationpoi_address;
		ImageView lacation_select_img;
	}
}
