package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.control.activity.CampaignBaoMingListActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignGoodMomentsActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignListActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignManagementActivity;
import com.bestdo.bestdoStadiums.control.activity.CampaignPublishActivity;
import com.bestdo.bestdoStadiums.control.activity.CashBookActivity;
import com.bestdo.bestdoStadiums.control.activity.MainPersonActivity;
import com.bestdo.bestdoStadiums.control.activity.MessageActivity;
import com.bestdo.bestdoStadiums.control.activity.StadiumListActivity;
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
 * 俱乐部菜单
 * 
 * @author
 * 
 */
public class CampaingQuartAdapter extends BaseAdapter {

	private ArrayList<ClubMenuInfo> mList;
	private ImageLoader asyncImageLoader;
	private Activity context;
	private Activity mHomeActivity;
	ViewHolder viewHolder = null;
	int page;
	private String roleid;

	public CampaingQuartAdapter(Activity context, Activity mHomeActivity, ArrayList<ClubMenuInfo> sportTypeList,
			int page, int APP_PAGE_SIZE, String roleid) {
		this.context = context;
		this.mHomeActivity = mHomeActivity;
		this.page = page;
		this.roleid = roleid;
		mList = new ArrayList<ClubMenuInfo>();
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
		ClubMenuInfo sportTypeInfo = mList.get(position);
		String name = sportTypeInfo.getTitle();
		String thumb = sportTypeInfo.getThumb();
		viewHolder.stadiummaplist_sport_item_name.setText(name);
		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.stadiummaplist_sport_item_img);

		}

		if (name.equals("精彩时刻")) {
			int w = ConfigUtils.getInstance().dip2px(context, 30);
			int h = ConfigUtils.getInstance().dip2px(context, 28);
			viewHolder.stadiummaplist_sport_item_img.setLayoutParams(new RelativeLayout.LayoutParams(w, h));
		} else {
			int w = ConfigUtils.getInstance().dip2px(context, 25);
			viewHolder.stadiummaplist_sport_item_img.setLayoutParams(new RelativeLayout.LayoutParams(w, w));
		}

		viewHolder.stadiummaplist_sport_item_new.setVisibility(View.GONE);
		sportTypeInfo.setPage(page);
		viewHolder.stadiummaplist_sport_item_lay.setTag(sportTypeInfo);
		viewHolder.stadiummaplist_sport_item_lay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = null;
				ClubMenuInfo sportTypeInfo = (ClubMenuInfo) arg0.getTag();
				String club_name = sportTypeInfo.getClub_name();
				if (club_name.equals("CampaignPublish")) {
					// 组织活动
					intent = new Intent(mHomeActivity, CampaignPublishActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("skipFrom", "publish");
					mHomeActivity.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
				} else if (club_name.equals("CampaignManagement")) {
					if (roleid.equals("1")) {

						// 活动管理
						intent = new Intent(mHomeActivity, CampaignManagementActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						mHomeActivity.startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, context);
					} else if (roleid.equals("2")) {
						// 活动管理
						intent = new Intent(mHomeActivity, CampaignBaoMingListActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						mHomeActivity.startActivity(intent);
						CommonUtils.getInstance().setPageIntentAnim(intent, context);
					}

				} else if (club_name.equals("Venuebooking")) {
					// 场地预定
					intent = new Intent(mHomeActivity, MainPersonActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					mHomeActivity.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
				} else if (club_name.equals("Classicmoments")) {
					// 精彩时刻
					intent = new Intent(mHomeActivity, CampaignGoodMomentsActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					mHomeActivity.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);

				} else if (club_name.equals("CampaignList")) {
					// 活动报名
					intent = new Intent(mHomeActivity, CampaignListActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("title", sportTypeInfo.getTitle());
					intent.putExtra("intentType", "CampaignList");
					mHomeActivity.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
				} else if (club_name.equals("CampaignSignin")) {
					// 活动签到
					intent = new Intent(mHomeActivity, CampaignListActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.putExtra("title", sportTypeInfo.getTitle());
					intent.putExtra("intentType", "CampaignSignin");
					mHomeActivity.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
				} else if (club_name.equals("MessageCenter")) {
					// 消息中心
					intent = new Intent(mHomeActivity, MessageActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					mHomeActivity.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
				} else if (club_name.equals("CashBook")) {
					// 记事本
					intent = new Intent(mHomeActivity, CashBookActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					mHomeActivity.startActivity(intent);
					CommonUtils.getInstance().setPageIntentAnim(intent, context);
				}
			}
		});

		return view;
	}

	class ViewHolder {
		TextView stadiummaplist_sport_item_name;
		ImageView stadiummaplist_sport_item_img;
		LinearLayout stadiummaplist_sport_item_lay;
		LinearLayout stadiummaplist_sport_item_new;

	}
}
