package com.zh.education.control.adapter;

import java.util.List;

import com.zh.education.R;
import com.zh.education.model.ChannelItemInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChannelItemOtherAdapter extends BaseAdapter {
	private Context context;
	public List<ChannelItemInfo> channelList;
	private TextView item_text;
	/** 是否可见 */
	boolean isVisible = true;
	/** 要删除的position */
	public int remove_position = -1;

	public ChannelItemOtherAdapter(Context context,
			List<ChannelItemInfo> channelList) {
		this.context = context;
		this.channelList = channelList;
	}

	@Override
	public int getCount() {
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public ChannelItemInfo getItem(int position) {
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.channel_item,
				null);
		item_text = (TextView) view.findViewById(R.id.channel_item_tv);
		ChannelItemInfo channel = getItem(position);
		item_text.setText(channel.getName());
		if (!isVisible && (position == -1 + channelList.size())) {
			item_text.setText("");
		}
		if (remove_position == position) {
			item_text.setText("");
		}
		return view;
	}

	/** 获取频道列表 */
	public List<ChannelItemInfo> getChannnelLst() {
		return channelList;
	}

	/** 添加频道列表 */
	public void addItem(ChannelItemInfo channel) {
		channelList.add(channel);
		notifyDataSetChanged();
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
		// notifyDataSetChanged();
	}

	/** 删除频道列表 */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		notifyDataSetChanged();
	}

	/** 设置频道列表 */
	public void setListDate(List<ChannelItemInfo> list) {
		channelList = list;
	}

	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}

	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
}