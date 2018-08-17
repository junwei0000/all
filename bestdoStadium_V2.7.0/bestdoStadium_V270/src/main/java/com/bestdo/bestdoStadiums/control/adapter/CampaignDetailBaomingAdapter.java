package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-15 下午7:18:58
 * @Description 类描述：
 */
public class CampaignDetailBaomingAdapter extends BaseAdapter {
	private ArrayList<CampaignDetailInfo> mList;
	private ViewHolder viewHolder;
	private Activity mContext;
	private ImageLoader asyncImageLoader;

	public CampaignDetailBaomingAdapter(Activity mContext, ArrayList<CampaignDetailInfo> mList) {
		this.mContext = mContext;
		this.mList = mList;
		asyncImageLoader = new ImageLoader(mContext, "headImg");
	}

	public int getCount() {
		return mList.size();
	}

	public Object getItem(int position) {
		return mList.get(position);
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(int position, View view, ViewGroup arg2) {
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.campaign_detail_baomingitem, null);
			viewHolder.sitem_iv_ablum = (CircleImageView) view.findViewById(R.id.sitem_iv_ablum);
			viewHolder.item_tv_nickname = (TextView) view.findViewById(R.id.item_tv_nickname);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		CampaignDetailInfo mInfo = mList.get(position);

		if (position >= 8) {
			viewHolder.item_tv_nickname.setText("...");
			viewHolder.item_tv_nickname.setTextSize(18);
			viewHolder.item_tv_nickname.setVisibility(View.VISIBLE);
			viewHolder.sitem_iv_ablum.setVisibility(View.GONE);
		} else {
			viewHolder.sitem_iv_ablum.setVisibility(View.VISIBLE);
			viewHolder.item_tv_nickname.setVisibility(View.GONE);
		}
		String ablum = mInfo.getFootavatar();
		if (!TextUtils.isEmpty(ablum)) {
			asyncImageLoader.DisplayImage(ablum, viewHolder.sitem_iv_ablum);
		} else {
			viewHolder.sitem_iv_ablum.setBackgroundResource(R.drawable.user_default_icon);
		}
		return view;
	}

	public ArrayList<CampaignDetailInfo> getmList() {
		return mList;
	}

	public void setmList(ArrayList<CampaignDetailInfo> mList) {
		this.mList = mList;
	}

	class ViewHolder {
		CircleImageView sitem_iv_ablum;
		TextView item_tv_nickname;
	}

}
