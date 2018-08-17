package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.model.CampaignGetClubInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberInfo;
import com.bestdo.bestdoStadiums.model.UserBuyMeberListInfo;
import com.bestdo.bestdoStadiums.utils.PriceUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-11-25 下午4:24:05
 * @Description 类描述：
 */
public class UserBuyMberAdapter extends BaseAdapter {

	Context context;
	ArrayList<UserBuyMeberListInfo> aList;
	String type;

	public UserBuyMberAdapter(Context context, ArrayList<UserBuyMeberListInfo> userBuyMeberListInfoList, String type) {
		this.aList = userBuyMeberListInfoList;
		this.context = context;
		this.type = type;
	}

	public ArrayList<UserBuyMeberListInfo> getaList() {
		return aList;
	}

	public void setaList(ArrayList<UserBuyMeberListInfo> aList) {
		this.aList = aList;
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
			view = LayoutInflater.from(context).inflate(R.layout.user_buymeber_listitem, null);
			viewHolder.user_putong_meber_price1 = (TextView) view.findViewById(R.id.user_putong_meber_price1);
			viewHolder.user_buymeber_title_tv = (TextView) view.findViewById(R.id.user_buymeber_title_tv);
			viewHolder.user_buymeber_detle_tv = (TextView) view.findViewById(R.id.user_buymeber_detle_tv);
			viewHolder.user_member_out_lin = (LinearLayout) view.findViewById(R.id.user_member_out_lin);
			viewHolder.amazing_icon = (ImageView) view.findViewById(R.id.amazing_icon);
			viewHolder.member_iv_select = (ImageView) view.findViewById(R.id.member_iv_select);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.member_iv_select.setVisibility(View.GONE);
		if (type.equals("1")) {
			if (aList.get(position).getSelect()) {
				viewHolder.member_iv_select.setVisibility(View.VISIBLE);
				viewHolder.user_member_out_lin.setBackgroundResource(R.drawable.corners_buymeber_btnbg_blue);
			} else {
				viewHolder.user_member_out_lin.setBackgroundResource(R.drawable.corners_btnbg);
			}
		} else {
			if (aList.get(position).getSelect()) {
				viewHolder.user_member_out_lin.setBackgroundResource(R.drawable.corners_buymeber_baijin_btnbg);
			} else {
				viewHolder.user_member_out_lin.setBackgroundResource(R.drawable.corners_btnbg_stroke_huang);
			}
		}
		if (aList.get(position).getAmazing().equals("1")) {
			viewHolder.amazing_icon.setVisibility(View.VISIBLE);
		} else {
			viewHolder.amazing_icon.setVisibility(View.GONE);
		}

		viewHolder.user_buymeber_title_tv.setText(aList.get(position).getName());

		String price = PriceUtils.getInstance().gteDividePrice(aList.get(position).getPriceBase(), "100");
		viewHolder.user_putong_meber_price1.setText(price);
		viewHolder.user_buymeber_detle_tv.setText(aList.get(position).getBuy_msg());
		return view;

	}

	class ViewHolder {
		TextView user_buymeber_title_tv;
		TextView user_putong_meber_price1;
		TextView user_buymeber_detle_tv;
		LinearLayout user_member_out_lin;
		ImageView amazing_icon;
		ImageView member_iv_select;
	}
}
