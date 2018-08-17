package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.StadiumInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * <pre>
 * 业务名:
 * 功能说明: 搜索历史适配
 * 编写日期:	2015-1-5
 * 作者:	 乔阳阳
 * 
 * 历史记录
 * 1、修改日期：1-7
 *    修改人：jun
 *    修改内容：
 * </pre>
 */
public class SearchKeyWordAdapter extends BaseAdapter {

	Context context;
	ArrayList<StadiumInfo> aList;

	public SearchKeyWordAdapter(Context context, ArrayList<StadiumInfo> aList) {
		this.aList = aList;
		this.context = context;
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
			view = LayoutInflater.from(context).inflate(R.layout.search_history_item, null);
			viewHolder.search_history_item_name = (TextView) view.findViewById(R.id.search_history_item_name);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.search_history_item_name.setText(aList.get(position).getName());
		return view;

	}

	class ViewHolder {
		TextView search_history_item_name;
	}

	public void setaList(ArrayList<StadiumInfo> aList) {
		this.aList = aList;
	}

}
