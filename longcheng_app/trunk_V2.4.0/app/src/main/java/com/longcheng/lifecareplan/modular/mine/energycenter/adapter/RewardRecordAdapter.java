package com.longcheng.lifecareplan.modular.mine.energycenter.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.energycenter.activity.TiXianRecordActivity;
import com.longcheng.lifecareplan.modular.mine.energycenter.bean.DaiFuItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;


public class RewardRecordAdapter extends BaseAdapterHelper<DaiFuItemBean> {
    ViewHolder mHolder = null;
    Context context;
    int type;

    public RewardRecordAdapter(Context context, List<DaiFuItemBean> list, int type) {
        super(context, list);
        this.context = context;
        this.type = type;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DaiFuItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.energycenter_reward_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DaiFuItemBean mDaiFuItemBean = list.get(position);
        mHolder.item_tv_rank.setText(mDaiFuItemBean.getRanking_date() + "排名第" + mDaiFuItemBean.getRanking() + "名");
        mHolder.item_tv_time.setText("申请奖励时间：" + mDaiFuItemBean.getCreate_date());
        mHolder.item_tv_num.setText("申请奖励数量：" + mDaiFuItemBean.getUser_zhufubao_number() + "祝福宝");
        mHolder.item_tv_money.setText(mDaiFuItemBean.getTotal_price() + "元");
        if (type == 0) {
            mHolder.item_iv_img.setVisibility(View.GONE);
        } else {
            mHolder.item_iv_img.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_rank;
        private TextView item_tv_time;
        private TextView item_tv_num;
        private TextView item_tv_money;

        public ViewHolder(View convertView) {
            item_iv_img = convertView.findViewById(R.id.item_iv_img);
            item_tv_rank = convertView.findViewById(R.id.item_tv_rank);
            item_tv_time = convertView.findViewById(R.id.item_tv_time);
            item_tv_num = convertView.findViewById(R.id.item_tv_num);
            item_tv_money = convertView.findViewById(R.id.item_tv_money);
        }
    }
}
