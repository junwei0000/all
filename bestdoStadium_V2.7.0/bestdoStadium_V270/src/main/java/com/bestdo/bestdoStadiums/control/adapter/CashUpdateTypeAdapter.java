package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.model.CashMyClubInfo;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:19:11
 * @Description 类描述：
 */
public class CashUpdateTypeAdapter extends BaseAdapter {

	Context context;
	ArrayList<CashMyClubInfo> array;
	String selecttype;
	private ImageLoader asyncImageLoader;

	public CashUpdateTypeAdapter(Context context, ArrayList<CashMyClubInfo> ordersStatusList, String selecttype) {
		super();
		this.context = context;
		this.array = ordersStatusList;
		this.selecttype = selecttype;
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
		viewHolder.user_orderall_status_iv_icon
				.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		viewHolder.user_orderall_status_tv_name.setText(array.get(position).getClub_name());
		String type = array.get(position).getClub_id();
		if (selecttype.equals(type)) {
			viewHolder.user_orderall_status_iv_check.setVisibility(View.VISIBLE);
		} else {
			viewHolder.user_orderall_status_iv_check.setVisibility(View.GONE);
		}
		if (type.equals("2")) {
			viewHolder.user_orderall_status_iv_icon.setBackgroundResource(R.drawable.img_income);
		} else if (type.equals("1")) {
			viewHolder.user_orderall_status_iv_icon.setBackgroundResource(R.drawable.img_pay);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView user_orderall_status_iv_icon;
		TextView user_orderall_status_tv_name;
		ImageView user_orderall_status_iv_check;
	}

}
