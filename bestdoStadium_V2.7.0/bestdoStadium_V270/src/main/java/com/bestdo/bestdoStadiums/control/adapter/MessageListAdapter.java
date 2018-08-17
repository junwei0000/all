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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.bestdo.bestdoStadiums.control.adapter.MessageAdapter.ViewHolder;
import com.bestdo.bestdoStadiums.model.CampaignListInfo;
import com.bestdo.bestdoStadiums.model.MessageInfo;
import com.bestdo.bestdoStadiums.model.MessageListInfo;
import com.bestdo.bestdoStadiums.model.StadiumInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.DatesUtils;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.utils.PriceUtils;
import com.bestdo.bestdoStadiums.R;

public class MessageListAdapter extends BaseAdapter {

	private ImageLoader asyncImageLoader;
	private ArrayList<MessageListInfo> list;
	private Activity context;

	public MessageListAdapter(Activity context, ArrayList<MessageListInfo> list) {
		super();
		this.context = context;
		this.list = list;
		asyncImageLoader = new ImageLoader(context, "headImg");
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
			convertView = LayoutInflater.from(context).inflate(R.layout.message_list_item, null);
			viewHolder = new ViewHolder();
			viewHolder.messageitem_iv_img = (CircleImageView) convertView.findViewById(R.id.messageitem_iv_img);
			viewHolder.messageitem_tv_imgdian = (TextView) convertView.findViewById(R.id.messageitem_tv_imgdian);
			viewHolder.messageitem_tv_title = (TextView) convertView.findViewById(R.id.messageitem_tv_title);
			viewHolder.messageitem_tv_content = (TextView) convertView.findViewById(R.id.messageitem_tv_content);
			viewHolder.messageitem_layout_detail = (LinearLayout) convertView
					.findViewById(R.id.messageitem_layout_detail);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MessageListInfo mInfo = (MessageListInfo) list.get(position);
		viewHolder.messageitem_tv_title.setText(mInfo.getTitle());

		String content = mInfo.getContent();
		// if(!TextUtils.isEmpty(content)&&content.length()>40){
		// content=content.substring(0, 30)+"...";
		// }
		viewHolder.messageitem_tv_content.setText(content);

		String thumb = mInfo.getIcon();

		if (!TextUtils.isEmpty(thumb)) {
			asyncImageLoader.DisplayImage(thumb, viewHolder.messageitem_iv_img);
		} else {
			viewHolder.messageitem_iv_img.setBackgroundResource(R.drawable.user_default_icon);
		}
		/**
		 * 0是未读，1是已读
		 */
		String is_read = mInfo.getIs_read();
		if (is_read.equals("0")) {
			viewHolder.messageitem_tv_imgdian.setVisibility(View.VISIBLE);
		} else {
			viewHolder.messageitem_tv_imgdian.setVisibility(View.GONE);
		}

		String status = mInfo.getActivity_status();
		if (status.equals("0")) {
			viewHolder.messageitem_layout_detail.setVisibility(View.VISIBLE);
		} else {
			viewHolder.messageitem_layout_detail.setVisibility(View.GONE);
		}

		return convertView;
	}

	public void setList(ArrayList<MessageListInfo> list) {
		this.list = list;
	}

	public void clearCache() {
		asyncImageLoader.clearCache();
	}

	class ViewHolder {
		public CircleImageView messageitem_iv_img;
		public TextView messageitem_tv_imgdian;
		public TextView messageitem_tv_title;
		public TextView messageitem_tv_content;
		public LinearLayout messageitem_layout_detail;
	}
}
