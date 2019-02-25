package com.longcheng.lifecareplan.modular.exchange.shopcart.adapter;

import android.content.Context;
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
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.exchange.shopcart.activity.ShopCartActivity;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.ProgressUtils;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class OrderCartListAdapter extends BaseAdapterHelper<DetailItemBean> {
    ViewHolder mHolder = null;
    Context context;
    ProgressUtils mProgressUtils;

    public OrderCartListAdapter(Context context, List<DetailItemBean> list) {
        super(context, list);
        this.context = context;
        mProgressUtils = new ProgressUtils(context);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DetailItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exchange_submitorder_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DetailItemBean mDetailItemBean = list.get(position);
        int goodsnum = mDetailItemBean.getGoodsNum();
        mHolder.item_tv_name1.setText(mDetailItemBean.getName());
        mHolder.item_tv_name2.setText(mDetailItemBean.getPrice_name());
        mHolder.item_tv_skb.setText(mDetailItemBean.getSkb_price());
        mHolder.item_tv_goodsnum.setText("x" + goodsnum);
        GlideDownLoadImage.getInstance().loadCircleImageRoleGoods(context, mDetailItemBean.getThumb(), mHolder.item_iv_thumb, 0);
        return convertView;
    }


    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name1;
        private TextView item_tv_name2;
        private TextView item_tv_skb;
        private TextView item_tv_goodsnum;

        public ViewHolder(View view) {
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name1 = (TextView) view.findViewById(R.id.item_tv_name1);
            item_tv_skb = (TextView) view.findViewById(R.id.item_tv_skb);
            item_tv_name2 = (TextView) view.findViewById(R.id.item_tv_name2);
            item_tv_goodsnum = (TextView) view.findViewById(R.id.item_tv_goodsnum);
        }
    }
}
