package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
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
import com.bestdo.bestdoStadiums.model.UserBalanceDetailInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class UserBalanceDetailAdapter extends BaseAdapter {

	private ArrayList<UserBalanceDetailInfo> list;
	private Activity context;
	String consumeTotal;
	public UserBalanceDetailAdapter(Activity context,String consumeTotal, ArrayList<UserBalanceDetailInfo> list) {
		super();
		this.context = context;
		this.list = list;
		this.consumeTotal = consumeTotal;
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
		UserBalanceDetailInfo stadiumObj = (UserBalanceDetailInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.user_balance_detail_item, null);
			viewHolder = new ViewHolder();
			viewHolder.item_layouttop = (LinearLayout) convertView.findViewById(R.id.item_layouttop);
			viewHolder.item_alloutput = (TextView) convertView.findViewById(R.id.item_alloutput);
			viewHolder.item_layoutlist = (LinearLayout) convertView.findViewById(R.id.item_layoutlist);
			viewHolder.item_name = (TextView) convertView.findViewById(R.id.item_name);
			viewHolder.item_date = (TextView) convertView.findViewById(R.id.item_date);
			viewHolder.item_balance = (TextView) convertView.findViewById(R.id.item_balance);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if(position==0){
			viewHolder.item_layouttop.setVisibility(View.VISIBLE);
			viewHolder.item_layoutlist.setVisibility(View.GONE);
			viewHolder.item_alloutput.setText("¥"+consumeTotal);
		}else{
			viewHolder.item_layouttop.setVisibility(View.GONE);
			viewHolder.item_layoutlist.setVisibility(View.VISIBLE);
			String orderDesc = stadiumObj.getOrderDesc();
			String time = stadiumObj.getFormatterTime();
			String balance = stadiumObj.getBalance();
			String type = stadiumObj.getType();
			viewHolder.item_name.setText(orderDesc);
			if (!TextUtils.isEmpty(type) && type.equals("consume_PAY")) {
				// 支出
				viewHolder.item_balance.setText("-" + balance);
			} else {
				viewHolder.item_balance.setText("+" + balance);
			}

			viewHolder.item_date.setText(time);
		}
		
		return convertView;
	}

	class ViewHolder {
		public LinearLayout item_layouttop;
		public TextView item_alloutput;
		public LinearLayout item_layoutlist;
		public TextView item_name;
		public TextView item_date;
		public TextView item_balance;
	}
}
