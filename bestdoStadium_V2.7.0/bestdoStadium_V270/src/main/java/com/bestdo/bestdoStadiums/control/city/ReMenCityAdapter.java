package com.bestdo.bestdoStadiums.control.city;

import java.util.ArrayList;
import java.util.List;

import com.bestdo.bestdoStadiums.model.SearchCityInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.R;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-3-17 下午4:21:40
 * @Description 类描述：热门城市
 */
public class ReMenCityAdapter extends BaseAdapter {

	Context context;
	List<SearchCityInfo> aList;
	String cityid_select;
	String cityid_current;
	String myselectcurrentcitystatus;

	public ReMenCityAdapter(Context context, String myselectcurrentcitystatus, List<SearchCityInfo> aList,
			String cityid_select, String cityid_current) {
		this.aList = aList;
		this.context = context;
		this.myselectcurrentcitystatus = myselectcurrentcitystatus;
		this.cityid_select = cityid_select;
		this.cityid_current = cityid_current;
	}

	@Override
	public int getCount() {
		return aList.size();
	}

	@Override
	public Object getItem(int position) {
		return aList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.search_city_gridview_item, null);
			viewHolder.searchcity_griditem_tv_name = (TextView) view.findViewById(R.id.searchcity_griditem_tv_name);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		if (!TextUtils.isEmpty(cityid_select) && cityid_select.equals(aList.get(position).getCityid())) {
			viewHolder.searchcity_griditem_tv_name.setBackgroundResource(R.drawable.corners_selectbtnbg);
			// 当定到位置时，如果第一个同时选中 隐藏
			if (!TextUtils.isEmpty(myselectcurrentcitystatus) && !TextUtils.isEmpty(cityid_current)
					&& cityid_current.equals(aList.get(position).getCityid())) {
				viewHolder.searchcity_griditem_tv_name.setBackgroundResource(R.drawable.corners_btnbg);
			}
		} else {
			viewHolder.searchcity_griditem_tv_name.setBackgroundResource(R.drawable.corners_btnbg);
		}
		viewHolder.searchcity_griditem_tv_name.setPadding(ConfigUtils.getInstance().dip2px(context, 15),
				ConfigUtils.getInstance().dip2px(context, 8), ConfigUtils.getInstance().dip2px(context, 15),
				ConfigUtils.getInstance().dip2px(context, 8));
		viewHolder.searchcity_griditem_tv_name.setText(aList.get(position).getCitynameShow());
		return view;

	}

	class ViewHolder {
		TextView searchcity_griditem_tv_name;
	}

}
