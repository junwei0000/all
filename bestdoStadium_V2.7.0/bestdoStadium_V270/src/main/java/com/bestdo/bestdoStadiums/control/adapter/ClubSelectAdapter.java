package com.bestdo.bestdoStadiums.control.adapter;

import java.util.ArrayList;

import com.bestdo.bestdoStadiums.model.CashMyClubInfo;
import com.bestdo.bestdoStadiums.model.MyJoinClubmenuInfo;
import com.bestdo.bestdoStadiums.model.SportTypeInfo;
import com.bestdo.bestdoStadiums.utils.ConfigUtils;
import com.bestdo.bestdoStadiums.utils.Constans;
import com.bestdo.bestdoStadiums.utils.ImageLoader;
import com.bestdo.bestdoStadiums.R;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author 作者：zoc
 * @date 创建时间：2016-1-21 下午6:19:11
 * @Description 类描述：
 */
public class ClubSelectAdapter extends BaseAdapter {
	String clubidBuffer;
	Context context;
	ArrayList<MyJoinClubmenuInfo> array;

	public ClubSelectAdapter(Context context, ArrayList<MyJoinClubmenuInfo> ordersStatusList, String clubidBuffer) {
		super();
		this.context = context;
		this.clubidBuffer = clubidBuffer;
		this.array = ordersStatusList;
	}

	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public Object getItem(int position) {
		return array.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.clubselect_item, null);
			viewHolder.choosedbt = (ImageView) convertView.findViewById(R.id.choosedbt);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		MyJoinClubmenuInfo myJoinClubmenuInfo = array.get(position);
		viewHolder.tv_name.setText(myJoinClubmenuInfo.getClub_name());
		myJoinClubmenuInfo.setChoosedbt(viewHolder.choosedbt);
		if (!TextUtils.isEmpty(clubidBuffer)) {
			if (clubidBuffer.contains(myJoinClubmenuInfo.getClub_id())) {
				myJoinClubmenuInfo.setCheck(true);
				viewHolder.choosedbt.setBackgroundResource(R.drawable.check_true);
			} else {
				myJoinClubmenuInfo.setCheck(false);
				viewHolder.choosedbt.setBackgroundResource(R.drawable.check_false);
			}
		}
		return convertView;
	}

	class ViewHolder {
		ImageView choosedbt;
		TextView tv_name;
	}

}
