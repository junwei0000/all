package com.longcheng.volunteer.modular.mine.rewardcenters.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.modular.mine.rewardcenters.bean.RewardDetail;
import com.longcheng.volunteer.utils.glide.ImageLoader;
import com.longcheng.volunteer.utils.myview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burning on 2018/9/4.
 */

public class AdapterRewardCenters extends BaseAdapter {

    List<RewardDetail> data = new ArrayList<RewardDetail>();
    private Context mContext;
    ImageLoader asyncImageLoader;

    public AdapterRewardCenters(Context mContext) {
        super();
        this.mContext = mContext;
        asyncImageLoader = new ImageLoader(mContext, "headImg");
    }

    public void refresh(List<RewardDetail> list, boolean clear) {
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
    public RewardDetail getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_rewardcenter, null, false);
            holder = new ViewHolder();
            holder.tvUserName = convertView.findViewById(R.id.tvusername);
            holder.tvUserPhone = convertView.findViewById(R.id.tvuserphone);
            holder.tvDate = convertView.findViewById(R.id.tvtime);
            holder.tvReward = convertView.findViewById(R.id.tvreward);
            holder.tvRewardContent = convertView.findViewById(R.id.tvrewardcontent);
            holder.avatar = convertView.findViewById(R.id.ivavatar);
            holder.tvCHO = convertView.findViewById(R.id.tvCHO);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RewardDetail item = getItem(position);
        holder.tvUserName.setText(item.getUserName());
        holder.tvUserPhone.setText(TextUtils.isEmpty(item.getPhone()) ? mContext.getString(R.string.no_phone) : item.getPhone());
        holder.tvDate.setText(item.getAddTime());
        holder.tvReward.setText(mContext.getString(R.string.reward, item.getRewardSkb() + item.getNum()));
        StringBuffer sbs = new StringBuffer();
        sbs.append(mContext.getString(R.string.reward_invitation, item.getNum()));
        if (item.getRewardSkb() > 0) {
            sbs.append("+").append(mContext.getString(R.string.reward_star, item.getRewardSkb()));
        }
        asyncImageLoader.DisplayImage(item.getAvatar(), holder.avatar);
        holder.tvRewardContent.setText(sbs.toString());
        holder.tvCHO.setVisibility(item.isCHO() ? View.VISIBLE : View.GONE);
        return convertView;
    }

    private class ViewHolder {
        public TextView tvUserName;
        public TextView tvUserPhone;
        public TextView tvDate;
        public TextView tvReward;
        public TextView tvRewardContent;
        public CircleImageView avatar;
        public TextView tvCHO;
    }


}
