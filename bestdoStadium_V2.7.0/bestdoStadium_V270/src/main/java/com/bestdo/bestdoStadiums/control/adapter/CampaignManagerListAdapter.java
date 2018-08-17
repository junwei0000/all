package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.control.activity.CampaignCancelActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignPublishActivity;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.CommonUtils;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class CampaignManagerListAdapter extends BaseAdapter {

	private ImageLoader asyncImageLoader;
	private ArrayList<CampaignListInfo> list;
	private Activity context;
	String listType;

	public CampaignManagerListAdapter(Activity context, ArrayList<CampaignListInfo> list, String listType) {
		super();
		this.context = context;
		this.list = list;
		this.listType = listType;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.campaign_manager_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.campagindetail_iv_clubicon = (ImageView) convertView
					.findViewById(R.id.campagindetail_iv_clubicon);
			viewHolder.campagindetail_tv_clubname = (TextView) convertView
					.findViewById(R.id.campagindetail_tv_clubname);
			viewHolder.campaginlistitem_tv_status = (TextView) convertView
					.findViewById(R.id.campaginlistitem_tv_status);
			viewHolder.campagindetail_tv_date = (TextView) convertView.findViewById(R.id.campagindetail_tv_date);
			viewHolder.campagindetail_tv_name = (TextView) convertView.findViewById(R.id.campagindetail_tv_name);
			viewHolder.campagindetail_tv_address = (TextView) convertView.findViewById(R.id.campagindetail_tv_address);
			viewHolder.campaginlistitem_layout_bianji = (LinearLayout) convertView
					.findViewById(R.id.campaginlistitem_layout_bianji);
			viewHolder.campaginlistitem_tv_edit = (TextView) convertView.findViewById(R.id.campaginlistitem_tv_edit);
			viewHolder.campaginlistitem_tv_del = (TextView) convertView.findViewById(R.id.campaginlistitem_tv_del);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		CampaignListInfo mInfo = (CampaignListInfo) list.get(position);
		String day = mInfo.getDay();
		String week = mInfo.getWeek();
		String name = mInfo.getName();
		int starttime = mInfo.getStart_time();
		int endtiem = mInfo.getEnd_time();
		String starthour = DatesUtils.getInstance().getTimeStampToDate(starttime, "HH:mm");
		String endhour = DatesUtils.getInstance().getTimeStampToDate(endtiem, "HH:mm");
		viewHolder.campagindetail_tv_date.setText(day + "  " + week + "  " + starthour + " ~ " + endhour);
		viewHolder.campagindetail_tv_name.setText(name);
		viewHolder.campagindetail_tv_address.setText(mInfo.getSitus());
		String status = mInfo.getAct_status();
		viewHolder.campagindetail_tv_clubname.setText(mInfo.getClub_name());
		String club_icon = mInfo.getLogo();
		if (TextUtils.isEmpty(club_icon)) {
			viewHolder.campagindetail_iv_clubicon.setBackgroundResource(R.drawable.campaigndetail_img_manage);
		} else {
			asyncImageLoader.DisplayImage(club_icon, viewHolder.campagindetail_iv_clubicon);
		}
		// 0进行中1已结束2取消3报名中
		viewHolder.campaginlistitem_layout_bianji.setVisibility(View.GONE);
		viewHolder.campaginlistitem_tv_status.setText(mInfo.getStatus_txt());
		if (listType.equals("baoming")) {
			viewHolder.campaginlistitem_tv_status.setVisibility(View.GONE);
		} else {
			viewHolder.campaginlistitem_tv_status.setVisibility(View.VISIBLE);
		}
		if (status.equals("0") || status.equals("3")) {
			if (listType.equals("zuzhi")) {
				viewHolder.campaginlistitem_layout_bianji.setVisibility(View.VISIBLE);
			}
			String is_edit = mInfo.getIs_edit();
			if (TextUtils.isEmpty(is_edit)) {
				is_edit = "";
			}
			if (is_edit.equals("0")) {
				// viewHolder.campaginlistitem_tv_status.setText("进行中");
				viewHolder.campaginlistitem_tv_edit.setVisibility(View.GONE);
				viewHolder.campaginlistitem_tv_del.setVisibility(View.GONE);
				viewHolder.campaginlistitem_layout_bianji.setVisibility(View.GONE);
			} else {
				viewHolder.campaginlistitem_tv_del.setVisibility(View.VISIBLE);
				// viewHolder.campaginlistitem_tv_status.setText("报名中");
				viewHolder.campaginlistitem_tv_edit.setVisibility(View.VISIBLE);
			}
			viewHolder.campaginlistitem_tv_status.setTextColor(context.getResources().getColor(R.color.ching));
		} else if (status.equals("1")) {
			// viewHolder.campaginlistitem_tv_status.setText("已结束");
			viewHolder.campaginlistitem_tv_status.setTextColor(context.getResources().getColor(R.color.red));
		} else if (status.equals("2")) {
			// viewHolder.campaginlistitem_tv_status.setText("已取消");
			viewHolder.campaginlistitem_tv_status
					.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
		}
		viewHolder.campaginlistitem_tv_edit.setTag(mInfo);
		viewHolder.campaginlistitem_tv_del.setTag(mInfo);
		viewHolder.campaginlistitem_tv_edit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				CampaignListInfo mCampaignListInfo = (CampaignListInfo) arg0.getTag();
				Intent intent = new Intent(context, CampaignPublishActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("skipFrom", "edit");
				intent.putExtra("mCampaignListInfo", mCampaignListInfo);
				context.startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, context);

			}
		});
		viewHolder.campaginlistitem_tv_del.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				CampaignListInfo mCampaignListInfo = (CampaignListInfo) arg0.getTag();
				Intent intent = new Intent(context, CampaignCancelActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("activity_id", mCampaignListInfo.getActivity_id());
				context.startActivity(intent);
				CommonUtils.getInstance().setPageIntentAnim(intent, context);
			}
		});
		return convertView;
	}

	public void setList(ArrayList<CampaignListInfo> list) {
		this.list = list;
	}

	public void clearCache() {
		asyncImageLoader.clearCache();
	}

	class ViewHolder {
		public ImageView campagindetail_iv_clubicon;
		public TextView campagindetail_tv_clubname;
		public TextView campaginlistitem_tv_status;
		public TextView campagindetail_tv_date;
		public TextView campagindetail_tv_name;
		public TextView campagindetail_tv_address;

		public LinearLayout campaginlistitem_layout_bianji;
		public TextView campaginlistitem_tv_edit;
		public TextView campaginlistitem_tv_del;
	}
}
