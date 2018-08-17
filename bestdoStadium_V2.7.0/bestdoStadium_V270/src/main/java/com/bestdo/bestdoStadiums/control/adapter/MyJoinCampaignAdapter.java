package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

/**
 * @author qyy 我加入的俱乐部
 */
public class MyJoinCampaignAdapter extends BaseAdapter {

	private ImageLoader asyncImageLoader;
	private ArrayList<MyJoinClubmenuInfo> list;
	private Activity context;
	private String type;

	public MyJoinCampaignAdapter(Activity context, String type, ArrayList<MyJoinClubmenuInfo> list) {
		super();
		this.context = context;
		this.type = type;
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
		MyJoinClubmenuInfo stadiumObj = (MyJoinClubmenuInfo) list.get(position);
		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.myjoin_campaign_list_item, null);

			viewHolder = new ViewHolder();
			viewHolder.myjoin_campaign_list_item_img = (ImageView) convertView
					.findViewById(R.id.myjoin_campaign_list_item_img);
			viewHolder.myjoin_campaign_list_item_name = (TextView) convertView
					.findViewById(R.id.myjoin_campaign_list_item_name);
			viewHolder.myjoin_campaign_list_item_name2 = (TextView) convertView
					.findViewById(R.id.myjoin_campaign_list_item_name2);
			viewHolder.myjoin_campaign_list_item_name3 = (TextView) convertView
					.findViewById(R.id.myjoin_campaign_list_item_name3);
			viewHolder.myjoin_campaign_list_item_lin = (LinearLayout) convertView
					.findViewById(R.id.myjoin_campaign_list_item_lin);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		String club_name = stadiumObj.getClub_name();
		String club_text = stadiumObj.getClub_text();
		String thumb = stadiumObj.getIcon();
		viewHolder.myjoin_campaign_list_item_name.setText(club_name);

		if (type.equals("loadeMyJoinClubmenu")) {
			convertView.setBackground(context.getResources().getDrawable(R.drawable.list_selector));
			viewHolder.myjoin_campaign_list_item_name2.setText(club_text);
			viewHolder.myjoin_campaign_list_item_lin.setGravity(Gravity.CENTER_VERTICAL);
		} else {
			String member_text = stadiumObj.getMember_text();
			String activity_text = stadiumObj.getActivity_text();
			viewHolder.myjoin_campaign_list_item_name2.setText(member_text + "\n" + activity_text);
		}
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.myjoin_campaign_list_item_img);
		} else {
			viewHolder.myjoin_campaign_list_item_img.setImageBitmap(asyncImageLoader.bitmap_orve);
		}
		return convertView;
	}

	public void setList(ArrayList<MyJoinClubmenuInfo> list) {
		this.list = list;
	}

	public void clearCache() {
		asyncImageLoader.clearCache();
	}

	class ViewHolder {
		public ImageView myjoin_campaign_list_item_img;
		public TextView myjoin_campaign_list_item_name;
		public TextView myjoin_campaign_list_item_name2;
		public TextView myjoin_campaign_list_item_name3;
		public LinearLayout myjoin_campaign_list_item_lin;
	}
}
