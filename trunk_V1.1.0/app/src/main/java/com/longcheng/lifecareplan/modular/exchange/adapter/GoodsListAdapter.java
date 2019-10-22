package com.longcheng.lifecareplan.modular.exchange.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.exchange.bean.GoodsItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderAfterBean;
import com.longcheng.lifecareplan.modular.mine.myorder.bean.OrderItemBean;
import com.longcheng.lifecareplan.utils.ConstantManager;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class GoodsListAdapter extends BaseAdapterHelper<GoodsItemBean> {
    ViewHolder mHolder = null;
    Context context;

    public GoodsListAdapter(Context context, List<GoodsItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<GoodsItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_exchange_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        GoodsItemBean mHelpItemBean = list.get(position);
        mHolder.item_tv_name1.setText(mHelpItemBean.getName());
        mHolder.item_tv_skb.setText(mHelpItemBean.getSkb_price());
        mHolder.item_tv_num.setText("已兑换" + mHelpItemBean.getSale_number() + "件");

        int is_new = mHelpItemBean.getIs_new();
        int is_hot = mHelpItemBean.getIs_hot();

        if (is_new == 1) {
            mHolder.item_iv_status.setVisibility(View.VISIBLE);
            mHolder.item_iv_status.setBackgroundResource(R.mipmap.mall_icon_new);
        } else {
            if (is_hot == 1) {
                mHolder.item_iv_status.setBackgroundResource(R.mipmap.mall_icon_hot);
                mHolder.item_iv_status.setVisibility(View.VISIBLE);
            } else {
                mHolder.item_iv_status.setVisibility(View.GONE);
            }
        }

        int is_selfmade = mHelpItemBean.getIs_selfmade();
        //是否自营 0：否 1：是
        if (is_selfmade == 1) {
            mHolder.item_iv_thelabel.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_iv_thelabel.setVisibility(View.GONE);
        }
        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 36)) / 2;
        int height = width;
        mHolder.item_iv_img.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        GlideDownLoadImage.getInstance().loadCircleImageRoleGoods(context, mHelpItemBean.getThumb(), mHolder.item_iv_img, 0);
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name1;
        private TextView item_tv_skb;
        private TextView item_tv_num;
        private ImageView item_iv_thelabel;
        private ImageView item_iv_status;

        public ViewHolder(View view) {
            item_iv_img = (ImageView) view.findViewById(R.id.item_iv_img);
            item_tv_name1 = (TextView) view.findViewById(R.id.item_tv_name1);
            item_tv_skb = (TextView) view.findViewById(R.id.item_tv_skb);
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
            item_iv_thelabel = (ImageView) view.findViewById(R.id.item_iv_thelabel);
            item_iv_status = (ImageView) view.findViewById(R.id.item_iv_status);
        }
    }
}
