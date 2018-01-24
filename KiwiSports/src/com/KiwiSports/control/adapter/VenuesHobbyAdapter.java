package com.KiwiSports.control.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.KiwiSports.R;
import com.KiwiSports.model.HobbyInfo;
import com.KiwiSports.model.VenuesListInfo;
import com.KiwiSports.utils.LanguageUtil;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VenuesHobbyAdapter extends BaseAdapter {

	private ArrayList<HobbyInfo> list;
	private Activity context;
	HashMap<String, String> selecthobbyMap;

	public VenuesHobbyAdapter(Activity context,HashMap<String, String> selecthobbyMap, ArrayList<HobbyInfo> list) {
		super();
		this.context = context;
		this.list = list;
		this.selecthobbyMap = selecthobbyMap;
	}

	 

	public HashMap<String, String> getSelecthobbyMap() {
		return selecthobbyMap;
	}



	public void setSelecthobbyMap(HashMap<String, String> selecthobbyMap) {
		this.selecthobbyMap = selecthobbyMap;
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
		HobbyInfo stadiumObj = (HobbyInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.venues_hobby_item, null);
			viewHolder = new ViewHolder();

			viewHolder.useraccount_sex_tv_famale = (TextView) convertView.findViewById(R.id.useraccount_sex_tv_famale);
			viewHolder.useraccount_sex_iv_famale = (ImageView) convertView.findViewById(R.id.useraccount_sex_iv_famale);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String Ehobby = stadiumObj.getEhobby();
		String zhobby = stadiumObj.getZhobby();
		if(!LanguageUtil.idChLanguage(context)){
			zhobby = Ehobby;
		} 
		viewHolder.useraccount_sex_tv_famale.setText(zhobby);
		if (selecthobbyMap.size()>0&&selecthobbyMap.containsKey(Ehobby)) {
			viewHolder.useraccount_sex_iv_famale.setVisibility(View.VISIBLE);
		} else {
			viewHolder.useraccount_sex_iv_famale.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	class ViewHolder {
		public TextView useraccount_sex_tv_famale;
		public ImageView useraccount_sex_iv_famale;
	}
}
