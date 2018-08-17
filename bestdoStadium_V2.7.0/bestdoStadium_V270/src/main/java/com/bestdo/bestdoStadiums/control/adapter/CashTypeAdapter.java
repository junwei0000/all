package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import cn.jpush.android.data.s;

import com.bestdo.bestdoStadiums.control.activity.CampaignBaoMingListActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignGoodMomentsActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignListActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignManagementActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignPublishActivity;
import com.bestdo.bestdoStadiums.control.activity.CashBookActivity;
import com.bestdo.bestdoStadiums.control.activity.MainPersonActivity;
import com.bestdo.bestdoStadiums.control.activity.MessageActivity;
import com.bestdo.bestdoStadiums.control.activity.StadiumListActivity;
import com.bestdo.bestdoStadiums.model.CashMyClubTypeInfo;
import com.bestdo.bestdoStadiums.model.ClubMenuInfo;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author
 * 
 */
public class CashTypeAdapter extends BaseAdapter {

	private ArrayList<CashMyClubTypeInfo> mList;
	private ImageLoader asyncImageLoader;
	private Activity context;
	ViewHolder viewHolder = null;
	int page;
	Handler mHandler;
	int mHandlerId;
	String selectid;

	public CashTypeAdapter(Activity context, ArrayList<CashMyClubTypeInfo> mList, int page, Handler mHandler,
			int mHandlerId, String selectid) {
		this.context = context;
		this.page = page;
		this.mList = mList;
		this.mHandler = mHandler;
		this.selectid = selectid;
		this.mHandlerId = mHandlerId;
		asyncImageLoader = new ImageLoader(context, "listitem");
	}

	public String getSelectPosition() {
		return selectid;
	}

	public void setSelectPosition(String selectid) {
		this.selectid = selectid;
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
		CashMyClubTypeInfo sportTypeInfo = mList.get(position);
		String name = sportTypeInfo.getName();
		viewHolder.stadiummaplist_sport_item_name.setText(name);
		int w = ConfigUtils.getInstance().dip2px(context, 25);
		viewHolder.stadiummaplist_sport_item_img.setLayoutParams(new RelativeLayout.LayoutParams(w, w));
		viewHolder.stadiummaplist_sport_item_new.setVisibility(View.GONE);
		sportTypeInfo.setPage(page);
		viewHolder.stadiummaplist_sport_item_lay.setTag(sportTypeInfo);
		viewHolder.stadiummaplist_sport_item_lay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CashMyClubTypeInfo sportTypeInfo = (CashMyClubTypeInfo) arg0.getTag();
				Message msg = new Message();
				msg.what = mHandlerId;
				msg.obj = sportTypeInfo;
				mHandler.sendMessage(msg);
				msg = null;
			}
		});
		String id = sportTypeInfo.getId();
		String thumb = sportTypeInfo.getImg();
		String select_img = sportTypeInfo.getSelect_img();
		if (id.equals(selectid)) {
			thumb = select_img;// 默认
		}
		System.err.println("page*position==" + (page + 1) * position + "  name=" + name);
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
