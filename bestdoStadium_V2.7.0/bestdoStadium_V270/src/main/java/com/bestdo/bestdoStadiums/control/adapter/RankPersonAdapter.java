package com.bestdo.bestdoStadiums.control.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.RankPersonMapper.RankPerson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by YuHua on 2017/5/23 19:07 Copyright (c) 2017, www.saidian.com All
 * Rights Reserved. 描述：
 */

public class RankPersonAdapter extends BaseAdapter {
	private static final int VIEW_TYPE_COUNT = 2;
	private Context context;
	private List<RankPerson> rankPersons;
	private int VIEW_TYPE_TOP = 0;
	private int VIEW_TYPE_NORMAL = 1;

	public RankPersonAdapter(Context context, List<RankPerson> rankPersons) {
		this.context = context;
		this.rankPersons = rankPersons;
	}

	@Override
	public int getCount() {
		return rankPersons.size();
	}

	@Override
	public Object getItem(int position) {
		return rankPersons.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return isTopItem(position) ? VIEW_TYPE_TOP : VIEW_TYPE_NORMAL;
	}

	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE_COUNT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BaseViewHolder holder;
		int type = getItemViewType(position);
		if (convertView == null) {
			if (type == VIEW_TYPE_TOP) {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_walk_personal_top, parent, false);
				holder = new ViewHolderTop();
				initHolderTop((ViewHolderTop) holder, convertView);
				convertView.setTag(holder);
			} else {
				convertView = LayoutInflater.from(context).inflate(R.layout.item_walk_personal_n, parent, false);
				holder = new ViewHolderNormal();
				initHolderNormal((ViewHolderNormal) holder, convertView);
				convertView.setTag(holder);
			}
		} else {
			if (type == VIEW_TYPE_TOP) {
				holder = (ViewHolderTop) convertView.getTag();
			} else {
				holder = ((ViewHolderNormal) convertView.getTag());
			}
		}
		if (rankPersons != null && !rankPersons.isEmpty()) {
			RankPerson person = rankPersons.get(position);
			if (type == VIEW_TYPE_TOP) {
				holder.tvStep.setTextColor(Color.parseColor("#fc821b"));
				((ViewHolderTop) holder).imageTop.setImageResource(getTopImage(position));
			} else {
				holder.tvStep.setTextColor(Color.parseColor("#656565"));
				((ViewHolderNormal) holder).tvNo.setText(String.valueOf(person.no));
			}
			loadImage(holder.imageView, person.url);
			holder.tvName.setText(person.name);
			holder.tvCompany.setText(person.department);
			holder.tvStep.setText(person.steps);
		}
		return convertView;
	}

	private int getTopImage(int position) {
		return position == 0 ? R.drawable.rank_img_jin
				: position == 1 ? R.drawable.rank_img_yin : R.drawable.rank_img_tong;
	}

	private boolean isTopItem(int position) {
		return position <= 2;
	}

	private void loadImage(ImageView imageView, String url) {
		ImageLoader.getInstance().displayImage(url, imageView);
	}

	private void initHolderTop(ViewHolderTop holder, View convertView) {
		initHolderBase(holder, convertView);
		holder.imageTop = (ImageView) convertView.findViewById(R.id.img_top);
	}

	private void initHolderNormal(ViewHolderNormal holder, View convertView) {
		initHolderBase(holder, convertView);
		holder.tvNo = (TextView) convertView.findViewById(R.id.tv_per_no);
	}

	private void initHolderBase(BaseViewHolder holder, View convertView) {
		holder.imageView = (ImageView) convertView.findViewById(R.id.img_my);
		holder.tvName = (TextView) convertView.findViewById(R.id.tv_my);
		holder.tvCompany = (TextView) convertView.findViewById(R.id.tv_ranNo);
		holder.tvStep = (TextView) convertView.findViewById(R.id.tv_count);
	}

	class BaseViewHolder {
		ImageView imageView;
		TextView tvName;
		TextView tvCompany;
		TextView tvStep;
	}

	private class ViewHolderTop extends BaseViewHolder {
		ImageView imageTop;
	}

	private class ViewHolderNormal extends BaseViewHolder {
		TextView tvNo;
	}
}
