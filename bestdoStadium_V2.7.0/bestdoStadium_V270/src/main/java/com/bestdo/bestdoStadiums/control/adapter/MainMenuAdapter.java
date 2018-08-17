package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.BusinessBannerInfo;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainMenuAdapter extends BaseAdapter {

	private ArrayList<BusinessBannerInfo> mList;
	private ImageLoader asyncImageLoader;
	private Activity context;
	ViewHolder viewHolder = null;
	private SharedPreferences welcomeSPF;

	public MainMenuAdapter(Activity context, ArrayList<BusinessBannerInfo> sportTypeList) {
		this.context = context;
		mList = sportTypeList;
		asyncImageLoader = new ImageLoader(context, "listitem");
		String welcomeSPFKey = Constans.getInstance().welcomeSharedPrefsKey;
		welcomeSPF = context.getSharedPreferences(welcomeSPFKey, 0);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.main_sport_item, null);
			viewHolder.stadiummaplist_sport_item_lay = (LinearLayout) view
					.findViewById(R.id.stadiummaplist_sport_item_lay);
			viewHolder.stadiummaplist_sport_item_new = (LinearLayout) view
					.findViewById(R.id.stadiummaplist_sport_item_new);
			viewHolder.stadiummaplist_sport_item_name = (TextView) view
					.findViewById(R.id.stadiummaplist_sport_item_name);
			viewHolder.stadiummaplist_sport_item_img = (ImageView) view
					.findViewById(R.id.stadiummaplist_sport_item_img);
			view.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		BusinessBannerInfo sportTypeInfo = mList.get(position);
		String name = sportTypeInfo.getName();
		String thumb = sportTypeInfo.getImgPath();
		viewHolder.stadiummaplist_sport_item_name.setText(name);
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.stadiummaplist_sport_item_img);
		}
		return view;
	}

	class ViewHolder {
		TextView stadiummaplist_sport_item_name;
		ImageView stadiummaplist_sport_item_img;
		LinearLayout stadiummaplist_sport_item_lay;
		LinearLayout stadiummaplist_sport_item_new;

	}
}
