package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午4:24:05
 * @Description 类描述：
 */
public class CampaignClubAdapter extends BaseAdapter {

	Context context;
	ArrayList<CampaignGetClubInfo> aList;

	public CampaignClubAdapter(Context context, ArrayList<CampaignGetClubInfo> aList) {
		this.aList = aList;
		this.context = context;
	}

	public ArrayList<CampaignGetClubInfo> getaList() {
		return aList;
	}

	public void setaList(ArrayList<CampaignGetClubInfo> aList) {
		this.aList = aList;
		notifyDataSetChanged();
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
			view = LayoutInflater.from(context).inflate(R.layout.stadiumlist_pup_item, null);
			viewHolder.stadiumlist_pup_item_iv_check = (ImageView) view
					.findViewById(R.id.stadiumlist_pup_item_iv_check);
			viewHolder.stadiumlist_pup_item_text = (TextView) view.findViewById(R.id.stadiumlist_pup_item_text);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		String name = aList.get(position).getClub_name();
		viewHolder.stadiumlist_pup_item_text.setText(name);
		viewHolder.stadiumlist_pup_item_iv_check.setVisibility(View.GONE);
		return view;

	}

	class ViewHolder {
		TextView stadiumlist_pup_item_text;
		ImageView stadiumlist_pup_item_iv_check;
	}
}
