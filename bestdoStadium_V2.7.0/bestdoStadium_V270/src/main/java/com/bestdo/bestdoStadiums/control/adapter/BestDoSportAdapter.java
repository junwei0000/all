package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.control.activity.MainPersonActivity;
import com.bestdo.bestdoStadiums.control.activity.StadiumListActivity;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 首页运动类型列表适配
 * 
 * @author jun
 * 
 */
public class BestDoSportAdapter extends BaseAdapter {

	private ArrayList<SportTypeInfo> mList;
	private ImageLoader asyncImageLoader;
	private Activity context;
	private Activity mHomeActivity;
	ViewHolder viewHolder = null;
	int page;

	public BestDoSportAdapter(Activity context, Activity mHomeActivity, ArrayList<SportTypeInfo> sportTypeList,
			int page, int APP_PAGE_SIZE) {
		this.context = context;
		this.mHomeActivity = mHomeActivity;
		this.page = page;
		mList = new ArrayList<SportTypeInfo>();
		asyncImageLoader = new ImageLoader(context, "listitem");
		int i = page * APP_PAGE_SIZE;
		int iEnd = i + APP_PAGE_SIZE;
		System.out.println("i=" + i);
		System.out.println("iEnd=" + iEnd);
		while ((i < sportTypeList.size()) && (i < iEnd)) {
			mList.add(sportTypeList.get(i));
			i++;
		}

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
		SportTypeInfo sportTypeInfo = mList.get(position);
		String name = sportTypeInfo.getSport();
		String thumb = sportTypeInfo.getImgurl();
		viewHolder.stadiummaplist_sport_item_name.setText(name);
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.stadiummaplist_sport_item_img);
		}
		if (sportTypeInfo.getIsNew().equals("1")) {
			viewHolder.stadiummaplist_sport_item_new.setVisibility(View.VISIBLE);
		} else {
			viewHolder.stadiummaplist_sport_item_new.setVisibility(View.GONE);
		}
		sportTypeInfo.setPage(page);
		viewHolder.stadiummaplist_sport_item_lay.setTag(sportTypeInfo);
		viewHolder.stadiummaplist_sport_item_lay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SportTypeInfo sportTypeInfo = (SportTypeInfo) arg0.getTag();
				String cid = sportTypeInfo.getCid();
				String name = sportTypeInfo.getSport();
				String merid = sportTypeInfo.getMerid();
				String defult_price_id = sportTypeInfo.getDefult_price_id();
				String banlance_price_id = sportTypeInfo.getBanlance_price_id();
				skipToStadiumList(cid, name, merid, defult_price_id, banlance_price_id);
			}
		});

		return view;
	}

	private String cityid_select;
	private double longitude_select;
	private double latitude_select;
	private String myselectcurrentcitystatus;
	private String address;
	private String city_select;
	private String jsonAreaStr;

	public void setInitData(String cityid_select, double longitude_select, double latitude_select,
			String myselectcurrentcitystatus, String address, String city_select, String jsonAreaStr) {
		this.cityid_select = cityid_select;
		this.longitude_select = longitude_select;
		this.latitude_select = latitude_select;
		this.myselectcurrentcitystatus = myselectcurrentcitystatus;
		this.address = address;
		this.city_select = city_select;
		this.jsonAreaStr = jsonAreaStr;
	}

	public void skipToStadiumList(String cid, String name, String merid, String defult_price_id,
			String banlance_price_id) {

		Intent intent = new Intent(mHomeActivity, StadiumListActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.putExtra("sportname", name);
		intent.putExtra("merid", merid);
		intent.putExtra("defult_price_id", defult_price_id);
		intent.putExtra("banlance_price_id", banlance_price_id);
		intent.putExtra("cityid_select", cityid_select);
		intent.putExtra("longitude_select", longitude_select);
		intent.putExtra("latitude_select", latitude_select);
		intent.putExtra("myselectcurrentcitystatus", myselectcurrentcitystatus);
		intent.putExtra("jsonAreaStr", jsonAreaStr);
		intent.putExtra("address", address);
		intent.putExtra("city_select", city_select);
		mHomeActivity.startActivity(intent);
		CommonUtils.getInstance().setPageIntentAnim(intent, mHomeActivity);
	}

	class ViewHolder {
		TextView stadiummaplist_sport_item_name;
		ImageView stadiummaplist_sport_item_img;
		LinearLayout stadiummaplist_sport_item_lay;
		LinearLayout stadiummaplist_sport_item_new;

	}
}
