package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.model.StadiumSelectJuliInfo;
import com.bestdo.bestdoStadiums.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 选择距离适配器
 * 
 * @author qyy
 * 
 */
public class StadiumSelectJuliAdapter extends BaseAdapter {

	Context context;
	ArrayList<StadiumSelectJuliInfo> aList;

	public StadiumSelectJuliAdapter(Context context, ArrayList<StadiumSelectJuliInfo> aList) {
		this.aList = aList;
		this.context = context;
	}

	public ArrayList<StadiumSelectJuliInfo> getaList() {
		return aList;
	}

	public void setaList(ArrayList<StadiumSelectJuliInfo> aList) {
		this.aList = aList;
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
		String name = aList.get(position).getName();
		viewHolder.stadiumlist_pup_item_text.setText(name);
		if (aList.get(position).getCheck()) {
			viewHolder.stadiumlist_pup_item_text.setTextColor(context.getResources().getColor(R.color.blue));
			viewHolder.stadiumlist_pup_item_iv_check.setVisibility(View.VISIBLE);
		} else {
			viewHolder.stadiumlist_pup_item_text
					.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
			viewHolder.stadiumlist_pup_item_iv_check.setVisibility(View.INVISIBLE);
		}
		return view;

	}

	class ViewHolder {
		TextView stadiumlist_pup_item_text;
		ImageView stadiumlist_pup_item_iv_check;
		// ImageView usercollect_classify_line;
	}
}
