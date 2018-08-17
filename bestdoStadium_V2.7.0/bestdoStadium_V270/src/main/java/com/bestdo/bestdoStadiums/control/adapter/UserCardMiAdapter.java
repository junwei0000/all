package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.text.ClipboardManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.UserCardsInfo;
import com.bestdo.bestdoStadiums.model.UserCardsMiInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * 卡密包列表适配
 * 
 * @author qyy
 * 
 */
public class UserCardMiAdapter extends BaseAdapter implements OnTouchListener {

	private ArrayList<UserCardsMiInfo> list;
	private Activity context;

	public UserCardMiAdapter(Activity context, ArrayList<UserCardsMiInfo> list) {
		super();
		this.context = context;
		this.list = list;
	}

	public void setList(ArrayList<UserCardsMiInfo> list) {
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
		UserCardsMiInfo stadiumObj = (UserCardsMiInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.user_cards_mi_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.user_cards_listitem_text_money = (TextView) convertView
					.findViewById(R.id.user_cards_listitem_text_money);
			viewHolder.user_cards_listitem_text_cardnam = (TextView) convertView
					.findViewById(R.id.user_cards_listitem_text_cardnam);
			viewHolder.user_cards_listitem_text_cardno = (TextView) convertView
					.findViewById(R.id.user_cards_listitem_text_cardno);
			viewHolder.user_cards_listitem_text_time = (EditText) convertView
					.findViewById(R.id.user_cards_listitem_text_time);
			viewHolder.user_cards_listitem_img = (ImageView) convertView.findViewById(R.id.user_cards_listitem_img);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String card_name = stadiumObj.getCard_name();
		String card_no = stadiumObj.getCard_no();
		String card_pass = stadiumObj.getCard_pass();
		String card_status = "  (" + stadiumObj.getCard_status() + ")";
		String amount = stadiumObj.getAmount();
		convertView.setOnTouchListener(this);
		viewHolder.user_cards_listitem_text_time.setOnTouchListener(this);
		viewHolder.user_cards_listitem_text_cardnam.setText(card_name);
		String str = "卡密:  " + "<font color='#cccccc'>" + card_pass + "</font>" + card_status;
		viewHolder.user_cards_listitem_text_time.setText(Html.fromHtml(str));
		viewHolder.user_cards_listitem_text_money.setText(amount);
		viewHolder.user_cards_listitem_text_cardno.setText(card_no);
		return convertView;

	}

	class ViewHolder {
		public TextView user_cards_listitem_text_money;
		public TextView user_cards_listitem_text_cardnam;
		public TextView user_cards_listitem_text_cardno;
		public EditText user_cards_listitem_text_time;
		public ImageView user_cards_listitem_img;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (v instanceof EditText) {
			EditText et = (EditText) v;
			et.setFocusable(true);
			et.setFocusableInTouchMode(true);
		} else {
			ViewHolder holder = (ViewHolder) v.getTag();
			holder.user_cards_listitem_text_time.setFocusable(false);
			holder.user_cards_listitem_text_time.setFocusableInTouchMode(false);

		}
		return false;
	}
}
