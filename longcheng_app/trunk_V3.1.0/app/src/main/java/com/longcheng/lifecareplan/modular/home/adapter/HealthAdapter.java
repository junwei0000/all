package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.home.healthydelivery.detail.activity.HealthyDeliveryDetailAct;
import com.longcheng.lifecareplan.modular.index.login.activity.UserLoginSkipUtils;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.sharedpreferenceutils.SharedPreferencesHelper;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/24 13:27
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HealthAdapter extends BaseAdapterHelper<HomeItemBean> {

    private Context mContext;
    ViewHolder mHolder;

    public HealthAdapter(Context mContext, List<HomeItemBean> list) {
        super(mContext, list);
        this.mContext = mContext;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HomeItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_healthy_deliverry_list2, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        RoundedImageView avatar = convertView.findViewById(R.id.ivavatar);
        HomeItemBean item = list.get(position);
        mHolder.tvTitle.setText(item.getNew_name());
        mHolder.tvContent.setText(item.getDes());
        mHolder.tvTypeDes.setText(item.getAdd_time());
        mHolder.tvTime.setText(item.getNew_num());
        mHolder.tv_zan.setText(item.getNew_zan());
        GlideDownLoadImage.getInstance().loadCircleImageLive(item.getPic(), R.mipmap.moren_new, avatar, ConstantManager.image_angle);
        return convertView;
    }

    private class ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        TextView tvTypeDes;
        TextView tvTime;
        TextView tv_zan;

        public ViewHolder(View convertView) {
            tvTitle = convertView.findViewById(R.id.tvtitle);
            tvContent = convertView.findViewById(R.id.tvNewContent);
            tvTypeDes = convertView.findViewById(R.id.tvtypedes);
            tvTime = convertView.findViewById(R.id.tvtime);
            tv_zan = convertView.findViewById(R.id.tv_zan);
        }
    }
}
