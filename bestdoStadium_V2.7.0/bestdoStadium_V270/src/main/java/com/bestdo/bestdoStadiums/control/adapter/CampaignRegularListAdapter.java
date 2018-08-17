package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.CampaignRegularListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class CampaignRegularListAdapter extends BaseAdapter {

	private ImageLoader asyncImageLoader;
	private ArrayList<CampaignRegularListInfo> list;
	private Activity context;
	Handler mHandler;
	int mHandlerID;

	public CampaignRegularListAdapter(Activity context, ArrayList<CampaignRegularListInfo> list, Handler mHandler,
			int mHandlerID) {
		super();
		this.context = context;
		this.list = list;
		this.mHandler = mHandler;
		this.mHandlerID = mHandlerID;
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
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.campaign_regularlist_item, null);
			viewHolder = new ViewHolder();
			viewHolder.campaginlistitem_tv_date = (TextView) convertView.findViewById(R.id.campaginlistitem_tv_date);
			viewHolder.campaginlistitem_tv_name = (TextView) convertView.findViewById(R.id.campaginlistitem_tv_name);
			viewHolder.campaginlistitem_tv_address = (TextView) convertView
					.findViewById(R.id.campaginlistitem_tv_address);
			viewHolder.campaginlistitem_tv_centent = (TextView) convertView
					.findViewById(R.id.campaginlistitem_tv_centent);
			viewHolder.campaginlistitem_tv_del = (TextView) convertView.findViewById(R.id.campaginlistitem_tv_del);
			viewHolder.campaginlistitem_tv_startorstop = (TextView) convertView
					.findViewById(R.id.campaginlistitem_tv_startorstop);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CampaignRegularListInfo mInfo = (CampaignRegularListInfo) list.get(position);
		String act_time = mInfo.getAct_time();
		String name = mInfo.getName();
		String address = mInfo.getAddress();
		String auto_txt = mInfo.getAuto_txt();
		viewHolder.campaginlistitem_tv_date.setText(act_time);
		viewHolder.campaginlistitem_tv_name.setText(name);
		viewHolder.campaginlistitem_tv_address.setText(address);

		/**
		 * 1启用状态 2停用状态 3删除状态
		 */
		String status = mInfo.getStatus();
		if (status.equals("1")) {
			// 启用状态展示 “停用”按钮
			viewHolder.campaginlistitem_tv_centent.setText(auto_txt);
			viewHolder.campaginlistitem_tv_startorstop.setText("停用");
			viewHolder.campaginlistitem_tv_startorstop
					.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
			viewHolder.campaginlistitem_tv_startorstop.setBackgroundResource(R.drawable.corners_btnbg);
		} else if (status.equals("2")) {
			viewHolder.campaginlistitem_tv_centent.setText("已停用");
			viewHolder.campaginlistitem_tv_startorstop.setText("启用");
			viewHolder.campaginlistitem_tv_startorstop.setTextColor(context.getResources().getColor(R.color.blue));
			viewHolder.campaginlistitem_tv_startorstop.setBackgroundResource(R.drawable.corners_selectbtnbg);
		}
		viewHolder.campaginlistitem_tv_del.setTag(mInfo);
		viewHolder.campaginlistitem_tv_startorstop.setTag(mInfo);
		viewHolder.campaginlistitem_tv_del.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				CampaignRegularListInfo mInfo = (CampaignRegularListInfo) arg0.getTag();
				int status = 3;
				String activity_id = mInfo.getId();
				Message msg = new Message();
				msg.arg1 = status;
				msg.what = mHandlerID;
				msg.obj = activity_id;
				mHandler.sendMessage(msg);
				msg = null;
			}
		});
		viewHolder.campaginlistitem_tv_startorstop.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				CampaignRegularListInfo mInfo = (CampaignRegularListInfo) arg0.getTag();
				String status_ = mInfo.getStatus();
				int status = 1;
				if (status_.equals("1")) {
					status = 2;
				} else if (status_.equals("2")) {
					status = 1;
				}
				String activity_id = mInfo.getId();
				Message msg = new Message();
				msg.arg1 = status;
				msg.what = mHandlerID;
				msg.obj = activity_id;
				mHandler.sendMessage(msg);
				msg = null;

			}
		});
		return convertView;
	}

	public void setList(ArrayList<CampaignRegularListInfo> list) {
		this.list = list;
	}

	public void clearCache() {
		asyncImageLoader.clearCache();
	}

	class ViewHolder {
		public TextView campaginlistitem_tv_date;
		public TextView campaginlistitem_tv_name;
		public TextView campaginlistitem_tv_address;
		public TextView campaginlistitem_tv_centent;
		public TextView campaginlistitem_tv_del;
		public TextView campaginlistitem_tv_startorstop;
	}
}
