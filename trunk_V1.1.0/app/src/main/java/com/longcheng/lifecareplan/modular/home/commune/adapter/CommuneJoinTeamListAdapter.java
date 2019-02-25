package com.longcheng.lifecareplan.modular.home.commune.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.commune.activity.CommuneJoinTeamListActivity;
import com.longcheng.lifecareplan.modular.home.commune.bean.CommuneItemBean;
import com.longcheng.lifecareplan.utils.ToastUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;
import com.longcheng.lifecareplan.utils.glide.ImageLoader;
import com.longcheng.lifecareplan.utils.myview.CircleImageView;

import java.util.List;


public class CommuneJoinTeamListAdapter extends BaseAdapterHelper<CommuneItemBean> {
    private final ImageLoader asyncImageLoader;
    ViewHolder mHolder = null;
    Context context;

    public CommuneJoinTeamListAdapter(Context context, List<CommuneItemBean> list) {
        super(context, list);
        this.context = context;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CommuneItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_engry_rank_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.layout_top.setVisibility(View.GONE);
        mHolder.layout_bottom.setVisibility(View.VISIBLE);
        mHolder.item_tv_num.setVisibility(View.GONE);

        CommuneItemBean mRankItemBean = list.get(position);
        String User_name = mRankItemBean.getUser_name();
        String Team_name = mRankItemBean.getTeam_name();
        String engerynum = mRankItemBean.getCount();
        String thumb = mRankItemBean.getAvatar();
        if (TextUtils.isEmpty(User_name)) {
            User_name = "暂无大队长";
        }
        if (TextUtils.isEmpty(Team_name)) {
            Team_name = "";
        }
        if (TextUtils.isEmpty(engerynum)) {
            engerynum = "";
        }
        mHolder.item_tv_name.setText(Team_name);
        mHolder.item_tv_shequ.setText(User_name);
        mHolder.item_tv_engerynum.setText(engerynum + "成员");
        asyncImageLoader.DisplayImage(thumb, mHolder.item_iv_thumb);
        return convertView;
    }

    private class ViewHolder {


        private TextView item_tv_num;
        private CircleImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_shequ;
        private TextView item_tv_engerynum;

        private LinearLayout layout_top;
        private LinearLayout layout_bottom;


        public ViewHolder(View convertView) {
            layout_top = (LinearLayout) convertView.findViewById(R.id.layout_top);
            layout_bottom = (LinearLayout) convertView.findViewById(R.id.layout_bottom);
            item_tv_num = (TextView) convertView.findViewById(R.id.item_tv_num);
            item_iv_thumb = (CircleImageView) convertView.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) convertView.findViewById(R.id.item_tv_name);
            item_tv_shequ = (TextView) convertView.findViewById(R.id.item_tv_shequ);
            item_tv_engerynum = (TextView) convertView.findViewById(R.id.item_tv_engerynum);

        }
    }
}
