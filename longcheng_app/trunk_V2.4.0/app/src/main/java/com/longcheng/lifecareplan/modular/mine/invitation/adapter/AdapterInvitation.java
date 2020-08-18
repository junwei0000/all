package com.longcheng.lifecareplan.modular.mine.invitation.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.mine.invitation.bean.InvitationDetail;
import com.longcheng.lifecareplan.modular.mine.rewardcenters.adapter.AdapterRewardCenters;
import com.longcheng.lifecareplan.modular.mine.rewardcenters.bean.RewardDetail;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burning on 2018/9/5.
 */

public class AdapterInvitation extends BaseAdapter {
    List<InvitationDetail> data = new ArrayList<InvitationDetail>();
    private Context mContext;
    ImageLoader asyncImageLoader;

    public AdapterInvitation(Context mContext) {
        super();
        this.mContext = mContext;
        asyncImageLoader = new ImageLoader(mContext, "headImg");
    }

    public void refresh(List<InvitationDetail> list, boolean clear) {
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
    public InvitationDetail getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_invitation, null, false);
            holder = new ViewHolder();
            holder.tvUserName = convertView.findViewById(R.id.tvusername);
            holder.tvCHO = convertView.findViewById(R.id.tvCHO);
            holder.tvUserPhone = convertView.findViewById(R.id.tvuserphone);
            holder.tvDate = convertView.findViewById(R.id.tvtime);
            holder.tvUdentity = convertView.findViewById(R.id.tvUdentity);
            holder.avatar = convertView.findViewById(R.id.ivavatar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvCHO.setVisibility(View.GONE);
        InvitationDetail item = getItem(position);
        holder.tvUserName.setText(item.getUserName());
        holder.tvUserPhone.setText(TextUtils.isEmpty(item.getPhone()) ? mContext.getString(R.string.no_phone) : item.getPhone());
        holder.tvDate.setText(item.getDate());
        if (item.getIsCHO() == 1) {
            holder.tvUdentity.setTextColor(mContext.getResources().getColor(R.color.red_course));
        } else {
            holder.tvUdentity.setTextColor(mContext.getResources().getColor(R.color.text_contents_color));
        }
        holder.tvUdentity.setText(item.getUserUdentity());
        asyncImageLoader.DisplayImage(item.getAvatar(), holder.avatar);
//        GlideDownLoadImage.getInstance().loadCircleHeadImage(mContext, item.getAvatar(), holder.avatar);
        return convertView;
    }

    private class ViewHolder {
        public TextView tvUserName;
        public TextView tvCHO;
        public TextView tvUserPhone;
        public TextView tvDate;
        public TextView tvUdentity;
        public CircleImageView avatar;
    }
}
