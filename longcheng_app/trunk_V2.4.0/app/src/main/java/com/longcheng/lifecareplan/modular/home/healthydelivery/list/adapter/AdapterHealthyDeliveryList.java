package com.longcheng.lifecareplan.modular.home.healthydelivery.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.home.healthydelivery.list.bean.HealthyDeliveryBean;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.youzan.mobile.zanim.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burning on 2018/9/5.
 */

public class AdapterHealthyDeliveryList extends BaseAdapter {
    List<HealthyDeliveryBean> data = new ArrayList<HealthyDeliveryBean>();
    private Context mContext;

    public AdapterHealthyDeliveryList(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void refresh(List<HealthyDeliveryBean> list, boolean clear) {
        if (list != null && !list.isEmpty()) {
            if (clear) {
                data.clear();
            }
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public HealthyDeliveryBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_healthy_deliverry_list, null, false);
            holder = new ViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tvtitle);
            holder.tvContent = convertView.findViewById(R.id.tvNewContent);
            holder.tvTypeDes = convertView.findViewById(R.id.tvtypedes);
            holder.tvTime = convertView.findViewById(R.id.tvtime);
            holder.avatar = convertView.findViewById(R.id.ivavatar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HealthyDeliveryBean item = getItem(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvContent.setText(item.getDes());
        holder.tvTypeDes.setText(item.getTypeName());
        holder.tvTime.setText(DatesUtils.getInstance().getTimeStampToDate((int)item.getUpdateTime(), "yyy-MM-dd"));
        GlideDownLoadImage.getInstance().loadCircleImageRoleREf(mContext, item.getPic(), holder.avatar, 0);
        return convertView;
    }

    private class ViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvTypeDes;
        public TextView tvTime;
        public ImageView avatar;
    }
}
