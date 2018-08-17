package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.CreatOrderGetDefautCardInfo;
import com.bestdo.bestdoStadiums.model.CreatOrderGetMerItemPriceInfo;
import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.R;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-2-29 下午8:16:49
 * @Description 类描述：
 */
public class CreateOrderGetCardAdapter extends BaseAdapter {

	private ArrayList<CreatOrderGetDefautCardInfo> list;
	private Activity context;

	public CreateOrderGetCardAdapter(Activity context, ArrayList<CreatOrderGetDefautCardInfo> list) {
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
		CreatOrderGetDefautCardInfo stadiumObj = (CreatOrderGetDefautCardInfo) list.get(position);
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
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.user_cards_listitem_text_fuhao.setTextColor(context.getResources().getColor(R.color.btn_bg));
		viewHolder.user_cards_listitem_text_money.setTextColor(context.getResources().getColor(R.color.btn_bg));
		String CardTypeName = stadiumObj.getAccountName();
		String card_num = stadiumObj.getCardNo();
		String strat_time = stadiumObj.getUseStartTime();
		String end_time = stadiumObj.getUseEndTime();
		String money = stadiumObj.getBalance();

		if (stadiumObj.getAccountType().equals("TIMES")) {
			viewHolder.user_cards_listitem_text_money.setText(money + "次");
			viewHolder.user_cards_listitem_text_fuhao.setVisibility(View.GONE);
		} else {
			viewHolder.user_cards_listitem_text_fuhao.setVisibility(View.VISIBLE);
			viewHolder.user_cards_listitem_text_money.setText(money);
		}

		viewHolder.user_cards_listitem_text_cardnam.setText(card_num);
		viewHolder.user_cards_listitem_text_time.setText(strat_time + " 至 " + end_time);
		viewHolder.user_cards_listitem_text_cardnam.setText(CardTypeName);
		viewHolder.user_cards_listitem_text_cardno.setText(card_num);
		return convertView;
	}

	class ViewHolder {
		public TextView user_cards_listitem_text_fuhao;
		public TextView user_cards_listitem_text_money;
		public TextView user_cards_listitem_text_cardnam;
		public TextView user_cards_listitem_text_cardno;
		public TextView user_cards_listitem_text_time;
	}
}
