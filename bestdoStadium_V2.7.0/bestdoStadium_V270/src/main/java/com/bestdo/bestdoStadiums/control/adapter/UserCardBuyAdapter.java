package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.control.activity.CreatOrdersBuyCardActivity;
import com.bestdo.bestdoStadiums.control.activity.UserCardDetailActivity;
import com.bestdo.bestdoStadiums.control.activity.UserCardsBuyActivity;
import com.bestdo.bestdoStadiums.model.UserCardsBuyInfo;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-5-5 下午5:49:22
 * @Description 类描述：
 */
public class UserCardBuyAdapter extends BaseAdapter {

	private ArrayList<UserCardsBuyInfo> list;
	private Activity context;

	public UserCardBuyAdapter(Activity context, ArrayList<UserCardsBuyInfo> list) {
		super();
		this.context = context;
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
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.user_cards_buyitem, null);
			viewHolder = new ViewHolder();
			viewHolder.usercards_buyitem_tv_name = (TextView) convertView.findViewById(R.id.usercards_buyitem_tv_name);
			viewHolder.usercards_buyitem_tv_detail = (TextView) convertView
					.findViewById(R.id.usercards_buyitem_tv_detail);
			viewHolder.usercards_buyitem_tv_price = (TextView) convertView
					.findViewById(R.id.usercards_buyitem_tv_price);
			viewHolder.usercards_buyitem_tv_buy = (TextView) convertView.findViewById(R.id.usercards_buyitem_tv_buy);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		UserCardsBuyInfo stadiumObj = (UserCardsBuyInfo) list.get(position);
		String name = stadiumObj.getName();
		String detail = stadiumObj.getDetail();
		String price = stadiumObj.getPrice();
		price = PriceUtils.getInstance().gteDividePrice(price, "100");
		price = PriceUtils.getInstance().seePrice(price);
		viewHolder.usercards_buyitem_tv_name.setText(name);
		viewHolder.usercards_buyitem_tv_detail.setText(detail);
		viewHolder.usercards_buyitem_tv_price
				.setText(context.getResources().getString(R.string.unit_fuhao_money) + price);

		viewHolder.usercards_buyitem_tv_buy.setTag(stadiumObj);
		viewHolder.usercards_buyitem_tv_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UserCardsBuyInfo mUserCardsBuyInfo = (UserCardsBuyInfo) arg0.getTag();
				Intent intent = new Intent(context, CreatOrdersBuyCardActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("mUserCardsBuyInfo", mUserCardsBuyInfo);
				// intent.putExtra("skipToCardDetailStatus",
				// Constans.getInstance().skipToCardDetailByListPage);
				context.startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, context);
			}
		});
		return convertView;
	}

	class ViewHolder {
		public TextView usercards_buyitem_tv_name;
		public TextView usercards_buyitem_tv_detail;
		public TextView usercards_buyitem_tv_price;
		public TextView usercards_buyitem_tv_buy;
	}
}
