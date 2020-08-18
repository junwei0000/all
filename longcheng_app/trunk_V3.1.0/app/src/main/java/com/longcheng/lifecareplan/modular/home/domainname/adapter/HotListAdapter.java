package com.longcheng.lifecareplan.modular.home.domainname.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.domainname.activity.CommuDetailActivity;
import com.longcheng.lifecareplan.modular.home.domainname.activity.DomainMenuActivity;
import com.longcheng.lifecareplan.modular.home.domainname.bean.HotListDataBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HotListAdapter extends BaseAdapterHelper<HotListDataBean.HotItemInfo> {
    ViewHolder mHolder = null;
    Activity context;

    int level;
    Handler mHandler;

    public HotListAdapter(Activity context, List<HotListDataBean.HotItemInfo> list, int level, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.level = level;
        this.mHandler = mHandler;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HotListDataBean.HotItemInfo> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.domain_mycomm_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HotListDataBean.HotItemInfo mOrderItemBean = list.get(position);

        if (level == DomainMenuActivity.level_commue) {
            mHolder.tv_dadui.setText(mOrderItemBean.getCommune_name());
            mHolder.tv_commune.setText(mOrderItemBean.getAddress());
            mHolder.tv_address.setVisibility(View.GONE);
        } else {
            mHolder.tv_address.setVisibility(View.VISIBLE);
            mHolder.tv_dadui.setText(mOrderItemBean.getBrigade_name());
            mHolder.tv_commune.setText(mOrderItemBean.getCommune_name());
            mHolder.tv_address.setText(mOrderItemBean.getAddress());
        }

        mHolder.tv_rank.setVisibility(View.VISIBLE);
        mHolder.tv_rank.setText("" + (position + 1));
        if (position < 3) {
            mHolder.iv_hoticon.setVisibility(View.VISIBLE);
            mHolder.tv_rank.setTextColor(context.getResources().getColor(R.color.yellow_E95D1B));
        } else {
            mHolder.iv_hoticon.setVisibility(View.GONE);
            mHolder.tv_rank.setTextColor(context.getResources().getColor(R.color.c9));
        }
        mHolder.item_layout.setTag(mOrderItemBean);
        mHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotListDataBean.HotItemInfo mOrderItemBean = (HotListDataBean.HotItemInfo) v.getTag();
                Message message = Message.obtain();
                message.what=1;
                if (level == DomainMenuActivity.level_commue) {
                    message.obj = mOrderItemBean.getCommune_name();
                } else {
                    message.obj = mOrderItemBean.getBrigade_name();
                }
                mHandler.sendMessage(message);
                Intent intent = new Intent(context, CommuDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("commune_id", mOrderItemBean.getCommune_id());
                intent.putExtra("level", mOrderItemBean.getLevel());
                context.startActivity(intent);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private TextView tv_dadui;
        private TextView tv_commune;
        private TextView tv_address;

        private TextView tv_rank;
        private ImageView iv_hoticon;
        private LinearLayout item_layout;

        public ViewHolder(View view) {
            tv_dadui = view.findViewById(R.id.tv_dadui);
            tv_commune = view.findViewById(R.id.tv_commune);
            tv_address = view.findViewById(R.id.tv_address);
            tv_rank = view.findViewById(R.id.tv_rank);
            iv_hoticon = view.findViewById(R.id.iv_hoticon);
            item_layout = view.findViewById(R.id.item_layout);
        }
    }
}
