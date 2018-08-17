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

import com.bestdo.bestdoStadiums.model.CashClubYearbudgetInfo;
import com.bestdo.bestdoStadiums.model.CashbookListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class CashYearBudgetAdapter extends BaseAdapter {

	private ArrayList<CashClubYearbudgetInfo> list;
	private Activity context;

	public CashYearBudgetAdapter(Activity context, ArrayList<CashClubYearbudgetInfo> list) {
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
		CashClubYearbudgetInfo stadiumObj = (CashClubYearbudgetInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.cashbook_setyearlybudget_item, null);
			viewHolder = new ViewHolder();
			viewHolder.item_tv_year = (TextView) convertView.findViewById(R.id.item_tv_year);
			viewHolder.item_tv_name = (TextView) convertView.findViewById(R.id.item_tv_name);
			viewHolder.item_tv_money = (TextView) convertView.findViewById(R.id.item_tv_money);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String classname = stadiumObj.getClub_name();
		String money = stadiumObj.getBudget();
		String year = stadiumObj.getYear();
		viewHolder.item_tv_year.setText(year + "年度预算");
		viewHolder.item_tv_name.setText(classname);
		money = PriceUtils.getInstance().seePrice(money);
		viewHolder.item_tv_money.setText("¥" + money + "元");
		return convertView;
	}

	class ViewHolder {
		public TextView item_tv_year;
		public TextView item_tv_name;
		public TextView item_tv_money;
	}
}
