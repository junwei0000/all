package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.CampaignDetailInfo;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.utils.CircleImageView;
import com.bestdo.bestdoStadiums.utils.ImageLoader;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午4:24:05
 * @Description 类描述：
 */
public class CampaignBaominglistAdapter extends BaseAdapter {

	Context context;
	ArrayList<CampaignDetailInfo> aList;
	private ImageLoader asyncImageLoader;

	public CampaignBaominglistAdapter(Context context, ArrayList<CampaignDetailInfo> aList) {
		this.aList = aList;
		this.context = context;
		asyncImageLoader = new ImageLoader(context, "headImg");
	}

	public ArrayList<CampaignDetailInfo> getaList() {
		return aList;
	}

	public void setList(ArrayList<CampaignDetailInfo> aList) {
		this.aList = aList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return aList.size();
	}

	@Override
	public Object getItem(int position) {
		return aList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.campaign_baoming_item, null);
			viewHolder.sitem_iv_ablum = (CircleImageView) view.findViewById(R.id.sitem_iv_ablum);
			viewHolder.item_tv_nickname = (TextView) view.findViewById(R.id.item_tv_nickname);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		String name = aList.get(position).getFootname();
		viewHolder.item_tv_nickname.setText(name);
		String ablum = aList.get(position).getFootavatar();
		if (!TextUtils.isEmpty(ablum)) {
			asyncImageLoader.DisplayImage(ablum, viewHolder.sitem_iv_ablum);
		} else {
			viewHolder.sitem_iv_ablum.setBackgroundResource(R.drawable.user_default_icon);
		}
		return view;

	}

	class ViewHolder {
		CircleImageView sitem_iv_ablum;
		TextView item_tv_nickname;
	}
}
