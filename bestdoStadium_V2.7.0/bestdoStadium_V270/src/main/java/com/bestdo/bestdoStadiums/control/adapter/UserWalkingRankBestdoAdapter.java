package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.graphics.Bitmap;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.model.UserWalkingRankInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class UserWalkingRankBestdoAdapter extends BaseAdapter {

	private ImageLoader asyncImageLoader;
	private ArrayList<UserWalkingRankInfo> list;
	private Activity context;
	String companyname;
	String ranked_show;

	public UserWalkingRankBestdoAdapter(Activity context, ArrayList<UserWalkingRankInfo> list, String companyname,
			String ranked_show) {
		super();
		this.context = context;
		this.list = list;
		this.companyname = companyname;
		this.ranked_show = ranked_show;
		asyncImageLoader = new ImageLoader(context, "headImg");
	}

	public void setList(ArrayList<UserWalkingRankInfo> list) {
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
		UserWalkingRankInfo mInfo = (UserWalkingRankInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.user_walking_rank_item, null);
			viewHolder = new ViewHolder();
			viewHolder.rank_item_iv_headportrait = (CircleImageView) convertView
					.findViewById(R.id.rank_item_iv_headportrait);
			viewHolder.rank_item_tv_name = (TextView) convertView.findViewById(R.id.rank_item_tv_name);
			viewHolder.rank_item_tv_company = (TextView) convertView.findViewById(R.id.rank_item_tv_company);
			viewHolder.rank_item_tv_step = (TextView) convertView.findViewById(R.id.rank_item_tv_step);
			viewHolder.rank_item_tv_ranking = (TextView) convertView.findViewById(R.id.rank_item_tv_ranking);
			viewHolder.rank_item_tv_kong = (TextView) convertView.findViewById(R.id.rank_item_tv_kong);

			viewHolder.rank_item_layout_ranking = (LinearLayout) convertView
					.findViewById(R.id.rank_item_layout_ranking);
			viewHolder.rank_item_tv_kong_line = (LinearLayout) convertView.findViewById(R.id.rank_item_tv_kong_line);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String url = mInfo.getAblum_url();
		String name = mInfo.getNick_name();
		String stepnum = mInfo.getStep_num();
		int num = mInfo.getNum();
		viewHolder.rank_item_tv_name.setText(name);
		viewHolder.rank_item_tv_step.setText(stepnum);
		if (!TextUtils.isEmpty(url)) {
			asyncImageLoader.DisplayImage(url, viewHolder.rank_item_iv_headportrait);
		} else {
			Bitmap mBitmap = asyncImageLoader.readBitMap(context, R.drawable.user_default_icon);
			viewHolder.rank_item_iv_headportrait.setImageBitmap(mBitmap);
		}
		viewHolder.rank_item_tv_step.setTextColor(context.getResources().getColor(R.color.text_click_color));
		if (position == 0) {
			viewHolder.rank_item_layout_ranking.setVisibility(View.GONE);
			if (stepnum.equals("0")) {

				viewHolder.rank_item_tv_company.setText("暂无排名");
			} else {
				viewHolder.rank_item_tv_company.setText("第" + num + "名");
			}
			viewHolder.rank_item_tv_kong_line.setVisibility(View.VISIBLE);

			viewHolder.rank_item_tv_kong.setVisibility(View.VISIBLE);
			viewHolder.rank_item_tv_kong.setText(companyname);
			if (!TextUtils.isEmpty(ranked_show) && !ranked_show.equals("2")) {
				viewHolder.rank_item_tv_kong.setText("");
			}
		} else {
			viewHolder.rank_item_layout_ranking.setVisibility(View.VISIBLE);
			viewHolder.rank_item_tv_kong.setVisibility(View.GONE);
			viewHolder.rank_item_tv_kong_line.setVisibility(View.GONE);
			String dept_name = mInfo.getDept_name();
			viewHolder.rank_item_tv_company.setText(dept_name);
			if (!TextUtils.isEmpty(dept_name)) {
				viewHolder.rank_item_tv_company.setVisibility(View.VISIBLE);
			} else {
				viewHolder.rank_item_tv_company.setVisibility(View.GONE);
			}

			viewHolder.rank_item_tv_ranking.setText("");
			if (num == 1) {
				viewHolder.rank_item_tv_step.setTextColor(context.getResources().getColor(R.color.btn_bg));
				viewHolder.rank_item_tv_ranking.setBackgroundResource(R.drawable.rank_item_img_jin);
			} else if (num == 2) {
				viewHolder.rank_item_tv_step.setTextColor(context.getResources().getColor(R.color.btn_bg));
				viewHolder.rank_item_tv_ranking.setBackgroundResource(R.drawable.rank_item_img_yin);
			} else if (num == 3) {
				viewHolder.rank_item_tv_step.setTextColor(context.getResources().getColor(R.color.btn_bg));
				viewHolder.rank_item_tv_ranking.setBackgroundResource(R.drawable.rank_item_img_tong);
			} else {
				viewHolder.rank_item_tv_ranking.setBackgroundResource(R.color.transparent);
				viewHolder.rank_item_tv_ranking.setText(num + "");
			}
		}
		return convertView;
	}

	class ViewHolder {
		public CircleImageView rank_item_iv_headportrait;
		public TextView rank_item_tv_name;
		public TextView rank_item_tv_company;
		public TextView rank_item_tv_step;
		public TextView rank_item_tv_ranking;
		public LinearLayout rank_item_layout_ranking;
		public TextView rank_item_tv_kong;
		public LinearLayout rank_item_tv_kong_line;
	}
}
