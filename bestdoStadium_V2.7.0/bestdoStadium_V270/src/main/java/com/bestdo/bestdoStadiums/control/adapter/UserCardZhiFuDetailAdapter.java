package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.UserCardsDetaiShouZhilInfo;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 卡券列表适配
 * 
 * @author qyy
 * 
 */
public class UserCardZhiFuDetailAdapter extends BaseAdapter {

	private Activity context;
	private ArrayList<UserCardsDetaiShouZhilInfo> list;

	public UserCardZhiFuDetailAdapter(Activity context, ArrayList<UserCardsDetaiShouZhilInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(ArrayList<UserCardsDetaiShouZhilInfo> list) {
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
		UserCardsDetaiShouZhilInfo stadiumObj = (UserCardsDetaiShouZhilInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.user_cards_zhifu_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.user_card_zhifu_detail_name = (TextView) convertView
					.findViewById(R.id.user_card_zhifu_detail_name);
			viewHolder.user_card_zhifu_detail_time = (TextView) convertView
					.findViewById(R.id.user_card_zhifu_detail_time);
			viewHolder.user_card_zhifu_detail_nub = (TextView) convertView
					.findViewById(R.id.user_card_zhifu_detail_nub);
			viewHolder.user_card_zhifu_detail_balance = (TextView) convertView
					.findViewById(R.id.user_card_zhifu_detail_balance);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String relationNo = stadiumObj.getRelationNo();
		String createTime = stadiumObj.getCreateTime();
		String typeName = stadiumObj.getTypeName();
		String balance = stadiumObj.getBalance();
		viewHolder.user_card_zhifu_detail_name.setText(typeName);
		viewHolder.user_card_zhifu_detail_time.setText(createTime);
		viewHolder.user_card_zhifu_detail_nub.setText("No." + relationNo);
		viewHolder.user_card_zhifu_detail_balance.setText(balance);

		return convertView;
	}

	class ViewHolder {
		public TextView user_card_zhifu_detail_name;
		public TextView user_card_zhifu_detail_time;
		public TextView user_card_zhifu_detail_nub;
		public TextView user_card_zhifu_detail_balance;
	}
}
