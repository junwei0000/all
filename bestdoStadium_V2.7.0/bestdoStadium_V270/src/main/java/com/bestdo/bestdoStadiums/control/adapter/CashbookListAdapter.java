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
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.CashbookListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class CashbookListAdapter extends BaseAdapter {

	public ImageLoader asyncImageLoader;
	private ArrayList<CashbookListInfo> list;
	private Activity context;

	public CashbookListAdapter(Activity context, ArrayList<CashbookListInfo> list) {
		super();
		this.context = context;
		this.list = list;
		asyncImageLoader = new ImageLoader(context, "listitem");
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
		CashbookListInfo stadiumObj = (CashbookListInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.cashbook_item, null);
			viewHolder = new ViewHolder();
			viewHolder.item_iv_icon = (ImageView) convertView.findViewById(R.id.item_iv_icon);
			viewHolder.item_iv_classname = (TextView) convertView.findViewById(R.id.item_iv_classname);
			viewHolder.item_iv_money = (TextView) convertView.findViewById(R.id.item_iv_money);
			viewHolder.item_iv_date = (TextView) convertView.findViewById(R.id.item_iv_date);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String classname = stadiumObj.getClass_name();
		String money = stadiumObj.getMoney();
		String date = stadiumObj.getUse_time();
		String type = stadiumObj.getType();
		String description = stadiumObj.getDescription();
		// 默认价格
		if (!TextUtils.isEmpty(description)) {
			classname = description;
		}
		viewHolder.item_iv_classname.setText(classname);
		money = PriceUtils.getInstance().seePrice(money);
		if (type.equals("1")) {
			viewHolder.item_iv_money.setText("-" + money);
			viewHolder.item_iv_money.setTextColor(context.getResources().getColor(R.color.ching));
		} else {
			viewHolder.item_iv_money.setText("+" + money);
			viewHolder.item_iv_money.setTextColor(context.getResources().getColor(R.color.red));
		}
		viewHolder.item_iv_date.setText(date);
		String thumb = stadiumObj.getSelect_img();
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.item_iv_icon);
		}
		return convertView;
	}

	public void setList(ArrayList<CashbookListInfo> list) {
		this.list = list;
	}

	public void clearCache() {
		asyncImageLoader.clearCache();
	}

	class ViewHolder {
		public ImageView item_iv_icon;
		public TextView item_iv_classname;
		public TextView item_iv_money;
		public TextView item_iv_date;
	}
}
