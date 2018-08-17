package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import android.R.integer;
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

import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 卡券列表适配
 * 
 * @author qyy
 * 
 */
public class UserCardAdapter extends BaseAdapter {

	private ArrayList<UserCardsInfo> list;
	private Activity context;

	public UserCardAdapter(Activity context, ArrayList<UserCardsInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(ArrayList<UserCardsInfo> list) {
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
		UserCardsInfo stadiumObj = (UserCardsInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.user_cards_list_item2, null);
			viewHolder = new ViewHolder();
			viewHolder.user_cards_listitem_text_fuhao = (TextView) convertView
					.findViewById(R.id.user_cards_listitem_text_fuhao);
			viewHolder.user_cards_listitem_text_money = (TextView) convertView
					.findViewById(R.id.user_cards_listitem_text_money);
			viewHolder.user_cards_listitem_text_cardnam = (TextView) convertView
					.findViewById(R.id.user_cards_listitem_text_cardnam);
			viewHolder.user_cards_listitem_text_cardno = (TextView) convertView
					.findViewById(R.id.user_cards_listitem_text_cardno);
			viewHolder.user_cards_listitem_text_time = (TextView) convertView
					.findViewById(R.id.user_cards_listitem_text_time);
			viewHolder.user_cards_listitem_img = (ImageView) convertView.findViewById(R.id.user_cards_listitem_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String CardTypeName = stadiumObj.getCardTypeName();
		String card_num = stadiumObj.getCardNo();
		String strat_time = stadiumObj.getUseStartTime();
		String end_time = stadiumObj.getUseEndTime();
		String balance = stadiumObj.getBalance();
		String accountName = stadiumObj.getAccountName();
		String accountType = stadiumObj.getAccountType();

		viewHolder.user_cards_listitem_text_cardnam.setText(card_num);
		viewHolder.user_cards_listitem_text_time.setText(strat_time + " 至 " + end_time);
		if (!TextUtils.isEmpty(accountName)) {
			viewHolder.user_cards_listitem_text_cardnam.setText(accountName);
		} else {
			viewHolder.user_cards_listitem_text_cardnam.setText(CardTypeName);
		}
		if (accountType.equals("TIMES")) {
			viewHolder.user_cards_listitem_text_money.setText(balance + "次");
			viewHolder.user_cards_listitem_text_fuhao.setVisibility(View.GONE);
		} else {
			viewHolder.user_cards_listitem_text_fuhao.setVisibility(View.VISIBLE);
			viewHolder.user_cards_listitem_text_money.setText(balance);
		}

		viewHolder.user_cards_listitem_text_cardno.setText(card_num);
		int isExpire = stadiumObj.getIsExpire();

		if (String.valueOf(isExpire).equals("1")) {
			// 过期卡
			viewHolder.user_cards_listitem_img.setBackgroundResource(R.drawable.user_cards_listitem_img_gray);
			viewHolder.user_cards_listitem_text_money
					.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
			viewHolder.user_cards_listitem_text_fuhao
					.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
		} else {
			if (accountType.equals("TIMES")) {
				viewHolder.user_cards_listitem_img.setBackgroundResource(R.drawable.user_cards_listitem_img_blue);
				viewHolder.user_cards_listitem_text_money.setTextColor(context.getResources().getColor(R.color.ching));
				viewHolder.user_cards_listitem_text_fuhao.setTextColor(context.getResources().getColor(R.color.ching));
			} else {
				viewHolder.user_cards_listitem_img.setBackgroundResource(R.drawable.user_cards_listitem_img);
				viewHolder.user_cards_listitem_text_money.setTextColor(context.getResources().getColor(R.color.btn_bg));
				viewHolder.user_cards_listitem_text_fuhao.setTextColor(context.getResources().getColor(R.color.btn_bg));
			}

		}

		return convertView;
	}

	class ViewHolder {
		public TextView user_cards_listitem_text_fuhao;
		public TextView user_cards_listitem_text_money;
		public TextView user_cards_listitem_text_cardnam;
		public TextView user_cards_listitem_text_cardno;
		public TextView user_cards_listitem_text_time;
		public ImageView user_cards_listitem_img;
	}
}
