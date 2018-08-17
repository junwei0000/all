package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:19:11
 * @Description 类描述：
 */
public class UserOrderSportAdapter extends BaseAdapter {

	Context context;
	ArrayList<SportTypeInfo> array;
	String checkedcid;
	String checkedmerid;
	private ImageLoader asyncImageLoader;

	public UserOrderSportAdapter(Context context, ArrayList<SportTypeInfo> ordersStatusList, String checkedcid,
			String checkedmerid) {
		super();
		this.context = context;
		this.array = ordersStatusList;
		this.checkedcid = checkedcid;
		this.checkedmerid = checkedmerid;
		asyncImageLoader = new ImageLoader(context, "listitem");
	}

	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		return array.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.user_orderall_sport_item, null);
			viewHolder.user_orderall_status_iv_icon = (ImageView) convertView
					.findViewById(R.id.user_orderall_status_iv_icon);
			viewHolder.user_orderall_status_tv_name = (TextView) convertView
					.findViewById(R.id.user_orderall_status_tv_name);
			viewHolder.user_orderall_status_iv_check = (ImageView) convertView
					.findViewById(R.id.user_orderall_status_iv_check);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.user_orderall_status_tv_name.setText(array.get(position).getName());
		if (position == 0) {
			viewHolder.user_orderall_status_iv_icon.setVisibility(View.GONE);
		} else {
			viewHolder.user_orderall_status_iv_icon.setVisibility(View.VISIBLE);
		}
		String cid = array.get(position).getCid();
		String merid = array.get(position).getMerid();
		if (checkedcid.equals(cid) && merid.equals(checkedmerid)) {
			viewHolder.user_orderall_status_iv_check.setVisibility(View.VISIBLE);
			viewHolder.user_orderall_status_tv_name.setTextColor(context.getResources().getColor(R.color.blue));
		} else {
			viewHolder.user_orderall_status_iv_check.setVisibility(View.GONE);
			viewHolder.user_orderall_status_tv_name
					.setTextColor(context.getResources().getColor(R.color.text_contents_color));
		}
		String thumb = array.get(position).getThumbnails();
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.user_orderall_status_iv_icon);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView user_orderall_status_iv_icon;
		TextView user_orderall_status_tv_name;
		ImageView user_orderall_status_iv_check;
	}

}
