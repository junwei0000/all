package com.longcheng.volunteer.modular.helpwith.energydetail.rank.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.modular.helpwith.applyhelp.activity.SkipHelpUtils;
import com.longcheng.volunteer.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.volunteer.modular.helpwith.energydetail.rank.bean.RankItemBean;
import com.longcheng.volunteer.utils.glide.ImageLoader;
import com.longcheng.volunteer.utils.myview.CircleImageView;

import java.util.HashMap;
import java.util.List;

/**
 */

public class RankAdapter extends BaseAdapterHelper<RankItemBean> {
    private final ImageLoader asyncImageLoader;
    ViewHolder mHolder = null;
    List<RankItemBean> rankingsTop;
    HashMap<String, Integer> supplierUserMap;
    Activity context;

    public RankAdapter(Activity context, List<RankItemBean> list, List<RankItemBean> rankingsTop, HashMap<String, Integer> supplierUserMap) {
        super(context, list);
        this.context = context;
        this.rankingsTop = rankingsTop;
        this.supplierUserMap = supplierUserMap;
        asyncImageLoader = new ImageLoader(context, "headImg");
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<RankItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_engry_rank_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            mHolder.layout_top.setVisibility(View.VISIBLE);
            mHolder.layout_bottom.setVisibility(View.GONE);
            showTop();
        } else {
            RankItemBean mRankItemBean = list.get(position);
            String num = mRankItemBean.getId();
            String User_name = mRankItemBean.getUser_name();
            String Group_name = mRankItemBean.getGroup_name();
            String engerynum = mRankItemBean.getPrice();
            String thumb = mRankItemBean.getAvatar();
            if (TextUtils.isEmpty(User_name)) {
                User_name = "暂无";
            }
            if (TextUtils.isEmpty(Group_name)) {
                Group_name = "暂无";
            }
            if (TextUtils.isEmpty(engerynum)) {
                engerynum = "暂无";
            }
            mHolder.layout_top.setVisibility(View.GONE);
            mHolder.layout_bottom.setVisibility(View.VISIBLE);
            mHolder.item_tv_num.setText(num);
            mHolder.item_tv_name.setText(User_name);
            mHolder.item_tv_shequ.setText(Group_name);
            mHolder.item_tv_engerynum.setText(engerynum);
            asyncImageLoader.DisplayImage(thumb, mHolder.item_iv_thumb);
            mHolder.item_iv_thumb.setTag(mRankItemBean);
            mHolder.item_iv_thumb.setOnClickListener(mclick);
        }
        return convertView;
    }

    private void showTop() {
        if (rankingsTop.size() == 3) {
            RankItemBean mRankItemBean = rankingsTop.get(2);
            String User_name = mRankItemBean.getUser_name();
            String Group_name = mRankItemBean.getGroup_name();
            String engerynum = mRankItemBean.getPrice();
            String thumb = mRankItemBean.getAvatar();
            if (TextUtils.isEmpty(User_name)) {
                User_name = "暂无";
            }
            if (TextUtils.isEmpty(Group_name)) {
                Group_name = "暂无";
            }
            if (TextUtils.isEmpty(engerynum)) {
                engerynum = "暂无";
            }
            mHolder.rankitemtop_tv_name3.setText(User_name);
            mHolder.rankitemtop_tv_shequ3.setText(Group_name);
            mHolder.rankitemtop_tv_num3.setText(engerynum);
            asyncImageLoader.DisplayImage(thumb, mHolder.rankitemtop_iv_head3);
            mHolder.rankitemtop_iv_head3.setTag(mRankItemBean);
            mHolder.rankitemtop_iv_head3.setOnClickListener(mclick);
        }
        if (rankingsTop.size() >= 1) {
            RankItemBean mRankItemBean = rankingsTop.get(0);
            String User_name = mRankItemBean.getUser_name();
            String Group_name = mRankItemBean.getGroup_name();
            String engerynum = mRankItemBean.getPrice();
            String thumb = mRankItemBean.getAvatar();
            if (TextUtils.isEmpty(User_name)) {
                User_name = "暂无";
            }
            if (TextUtils.isEmpty(Group_name)) {
                Group_name = "暂无";
            }
            if (TextUtils.isEmpty(engerynum)) {
                engerynum = "暂无";
            }
            mHolder.rankitemtop_tv_name1.setText(User_name);
            mHolder.rankitemtop_tv_shequ1.setText(Group_name);
            mHolder.rankitemtop_tv_num1.setText(engerynum);
            asyncImageLoader.DisplayImage(thumb, mHolder.rankitemtop_iv_head1);
            mHolder.rankitemtop_iv_head1.setTag(mRankItemBean);
            mHolder.rankitemtop_iv_head1.setOnClickListener(mclick);
        }
        if (rankingsTop.size() >= 2) {
            RankItemBean mRankItemBean = rankingsTop.get(1);
            String User_name = mRankItemBean.getUser_name();
            String Group_name = mRankItemBean.getGroup_name();
            String engerynum = mRankItemBean.getPrice();
            String thumb = mRankItemBean.getAvatar();
            if (TextUtils.isEmpty(User_name)) {
                User_name = "暂无";
            }
            if (TextUtils.isEmpty(Group_name)) {
                Group_name = "暂无";
            }
            if (TextUtils.isEmpty(engerynum)) {
                engerynum = "暂无";
            }
            mHolder.rankitemtop_tv_name2.setText(User_name);
            mHolder.rankitemtop_tv_shequ2.setText(Group_name);
            mHolder.rankitemtop_tv_num2.setText(engerynum);
            asyncImageLoader.DisplayImage(thumb, mHolder.rankitemtop_iv_head2);
            mHolder.rankitemtop_iv_head2.setTag(mRankItemBean);
            mHolder.rankitemtop_iv_head2.setOnClickListener(mclick);
        }
    }

    View.OnClickListener mclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RankItemBean mRankItemBean = (RankItemBean) v.getTag();
            String user_id = mRankItemBean.getUser_id();
            if (supplierUserMap.containsKey(user_id)) {
                SkipHelpUtils.getInstance().skipIntent(context, supplierUserMap.get(user_id));
            } else {
                Intent intent = new Intent(context, HelpWithEnergyActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("name", mRankItemBean.getUser_name());
                context.startActivity(intent);
            }
        }
    };

    private class ViewHolder {
        private CircleImageView rankitemtop_iv_head2;
        private TextView rankitemtop_tv_name2;
        private TextView rankitemtop_tv_shequ2;
        private TextView rankitemtop_tv_num2;

        private CircleImageView rankitemtop_iv_head1;
        private TextView rankitemtop_tv_name1;
        private TextView rankitemtop_tv_shequ1;
        private TextView rankitemtop_tv_num1;

        private CircleImageView rankitemtop_iv_head3;
        private TextView rankitemtop_tv_name3;
        private TextView rankitemtop_tv_shequ3;
        private TextView rankitemtop_tv_num3;


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
            rankitemtop_iv_head2 = (CircleImageView) convertView
                    .findViewById(R.id.rankitemtop_iv_head2);
            rankitemtop_tv_name2 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_name2);
            rankitemtop_tv_shequ2 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_shequ2);
            rankitemtop_tv_num2 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_engerynum2);

            rankitemtop_iv_head1 = (CircleImageView) convertView
                    .findViewById(R.id.rankitemtop_iv_head1);
            rankitemtop_tv_name1 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_name1);
            rankitemtop_tv_shequ1 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_shequ1);
            rankitemtop_tv_num1 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_engerynum1);

            rankitemtop_iv_head3 = (CircleImageView) convertView
                    .findViewById(R.id.rankitemtop_iv_head3);
            rankitemtop_tv_name3 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_name3);
            rankitemtop_tv_shequ3 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_shequ3);
            rankitemtop_tv_num3 = (TextView) convertView
                    .findViewById(R.id.rankitemtop_tv_engerynum3);
        }
    }
}
