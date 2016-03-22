package com.zh.education.control.adapter;

import java.util.ArrayList;

import com.zh.education.R;
import com.zh.education.control.activity.BoKeCommentsActivity;
import com.zh.education.model.BoKeInfo;
import com.zh.education.utils.CommonUtils;
import com.zh.education.utils.DatesUtils;
import com.zh.education.utils.ImageLoader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BoKeAdapter extends BaseAdapter {
	private ImageLoader asyncImageLoader;
	private ArrayList<BoKeInfo> list;
	private Activity context;

	public BoKeAdapter(ArrayList<BoKeInfo> list, Activity context) {
		super();
		this.list = list;
		this.context = context;
		asyncImageLoader = new ImageLoader(context, "");
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.fragment_boke_listiew_item, null);
			viewHolder = new ViewHolder();
			viewHolder.boke_list_item_img_icon = (ImageView) convertView
					.findViewById(R.id.boke_list_item_img_icon);
			viewHolder.boke_list_item_img_dianzan = (ImageView) convertView
					.findViewById(R.id.boke_list_item_img_dianzan);
			viewHolder.boke_list_item_img_pinglun = (ImageView) convertView
					.findViewById(R.id.boke_list_item_img_pinglun);
			viewHolder.bokelist_item_zan = (TextView) convertView
					.findViewById(R.id.bokelist_item_zan);
			viewHolder.bokelist_item_pinglun = (TextView) convertView
					.findViewById(R.id.bokelist_item_pinglun);
			viewHolder.bokelist_item_name = (TextView) convertView
					.findViewById(R.id.bokelist_item_name);
			viewHolder.bokelist_item_time = (TextView) convertView
					.findViewById(R.id.bokelist_item_time);
			viewHolder.bokelist_item_text_context = (TextView) convertView
					.findViewById(R.id.bokelist_item_text_context);
			viewHolder.bokelist_item_text_title = (TextView) convertView
					.findViewById(R.id.bokelist_item_text_title);
			viewHolder.boke_list_item_zan_layout = (LinearLayout) convertView
					.findViewById(R.id.boke_list_item_zan_layout);
			viewHolder.boke_list_item_pinglun_layout = (LinearLayout) convertView
					.findViewById(R.id.boke_list_item_pinglun_layout);
			viewHolder.bokelist_item_img_cover = (ImageView) convertView
					.findViewById(R.id.bokelist_item_img_cover);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		BoKeInfo boKeInfo = list.get(position);
		String Categroy = boKeInfo.getCategroy();
		int id = boKeInfo.getId();
		String Content = boKeInfo.getContent();
		String Title = boKeInfo.getTitle();
		String CreateUser = boKeInfo.getCreateUser();
		String CommentCount = boKeInfo.getCommentCount();
		String LikeCount = boKeInfo.getLikeCount();
		String HeadImg = boKeInfo.getHeadImg();
		String Cover = boKeInfo.getCover();
		long CreateTime = boKeInfo.getCreateTime();

		viewHolder.bokelist_item_zan.setText(LikeCount);
		viewHolder.bokelist_item_pinglun.setText(CommentCount);
		viewHolder.bokelist_item_name.setText(CreateUser);
		String time = DatesUtils.getInstance().getTimeStampToDate(CreateTime,
				"HH:mm");
		viewHolder.bokelist_item_time.setText(time);
		viewHolder.bokelist_item_text_title.setText(Title);
		viewHolder.bokelist_item_text_context.setText(Html.fromHtml(Content));

		viewHolder.boke_list_item_zan_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

					}
				});
		viewHolder.boke_list_item_pinglun_layout.setTag(position);
		viewHolder.boke_list_item_pinglun_layout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						int i = (Integer) arg0.getTag();
						Intent in = new Intent(context,
								BoKeCommentsActivity.class);
						Bundle bundle = new Bundle();
						bundle.putSerializable("boKeInfo", list.get(i));
						in.putExtras(bundle);
						context.startActivity(in);
						context.overridePendingTransition(
								R.anim.slide_in_right, R.anim.slide_out_left);
					}
				});

		if (TextUtils.isEmpty(Cover) || Cover.equals("null")) {
			viewHolder.bokelist_item_img_cover.setVisibility(View.GONE);
		} else {
			asyncImageLoader.DisplayImage(Cover,
					viewHolder.bokelist_item_img_cover);
			viewHolder.bokelist_item_img_cover.setVisibility(View.VISIBLE);
		}

		if (TextUtils.isEmpty(HeadImg) || HeadImg.equals("null")) {
			viewHolder.boke_list_item_img_icon
					.setBackgroundResource(R.drawable.usercenter_headbg);
		} else {
			asyncImageLoader.DisplayImage(HeadImg,
					viewHolder.boke_list_item_img_icon);
		}

		// private String RecordCount;
		// private int id;//总数量
		// private String Title ;//名称
		// private String Content ;//详情
		// private String CreateUser ;//创建人
		// private String CreateTime ;//创建时间
		// private String Categroy ;//文字的分类
		// private String CommentCount ;//评论数目
		// private String LikeCount ;//点赞的数目
		// private String HeadImg;

		return convertView;
	}

	class ViewHolder {
		public ImageView boke_list_item_img_icon;
		public ImageView boke_list_item_img_dianzan;
		public ImageView boke_list_item_img_pinglun;
		public TextView bokelist_item_zan;
		public TextView bokelist_item_pinglun;
		public TextView bokelist_item_name;
		public TextView bokelist_item_time;
		public TextView bokelist_item_text_context;
		public TextView bokelist_item_text_title;
		public LinearLayout boke_list_item_zan_layout;
		public LinearLayout boke_list_item_pinglun_layout;
		public ImageView bokelist_item_img_cover;
	}
}
