package com.longcheng.lifecareplan.modular.home.liveplay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.liveplay.bean.LiveDetailItemInfo;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class RankListAdapter extends BaseAdapterHelper<LiveDetailItemInfo> {
    ViewHolder mHolder = null;
    Context context;


    public RankListAdapter(Context context, List<LiveDetailItemInfo> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<LiveDetailItemInfo> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.live_rank_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        LiveDetailItemInfo mHelpItemBean = list.get(position);
        mHolder.item_tv_num.setText("" + (position + 1));
        mHolder.item_tv_name.setText(mHelpItemBean.getUser_name());
        mHolder.item_tv_skb.setText("" + mHelpItemBean.getSkb());
        if (position == 0) {
            mHolder.item_tv_num.setBackgroundResource(R.mipmap.live_guanjun_1);
        } else if (position == 1) {
            mHolder.item_tv_num.setBackgroundResource(R.mipmap.live_yajun_2);
        } else if (position == 2) {
            mHolder.item_tv_num.setBackgroundResource(R.mipmap.live_jijun_3);
        } else {
            mHolder.item_tv_num.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_num;
        private TextView item_tv_name;
        private TextView item_tv_skb;

        public ViewHolder(View view) {
            item_tv_num = view.findViewById(R.id.item_tv_num);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_skb = view.findViewById(R.id.item_tv_skb);
        }
    }
}
