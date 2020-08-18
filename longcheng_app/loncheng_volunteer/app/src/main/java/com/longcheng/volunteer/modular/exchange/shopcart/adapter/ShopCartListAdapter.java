package com.longcheng.volunteer.modular.exchange.shopcart.adapter;

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

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.volunteer.modular.exchange.shopcart.activity.ShopCartActivity;
import com.longcheng.volunteer.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ShopCartListAdapter extends BaseAdapterHelper<DetailItemBean> {
    ViewHolder mHolder = null;
    Context context;
    Handler mHandler;

    public ShopCartListAdapter(Context context, List<DetailItemBean> list, Handler mHandler) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DetailItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exchange_shopcart_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DetailItemBean mDetailItemBean = list.get(position);
        int goodsnum = mDetailItemBean.getGoodsNum();
        mHolder.item_tv_name1.setText(mDetailItemBean.getName());
        mHolder.item_tv_name2.setText(mDetailItemBean.getPrice_name());
        String Skb_price = mDetailItemBean.getSkb_price();
        mHolder.item_tv_skb.setText(Skb_price);
        mHolder.item_tv_goodsnum.setText("" + goodsnum);
        if (goodsnum > 1) {
            mHolder.item_layout_sub.setBackgroundResource(R.drawable.corners_bg_blackfang);
            mHolder.item_tv_sub.setBackgroundColor(context.getResources().getColor(R.color.text_biaoTi_color));
        } else {
            mHolder.item_layout_sub.setBackgroundResource(R.drawable.corners_bg_grayfang);
            mHolder.item_tv_sub.setBackgroundColor(context.getResources().getColor(R.color.text_noclick_color));
        }
        boolean check = mDetailItemBean.isCheck();
        if (check) {
            mHolder.item_tv_check.setBackgroundResource(R.mipmap.check_true_red);
        } else {
            mHolder.item_tv_check.setBackgroundResource(R.mipmap.check_false);
        }
        GlideDownLoadImage.getInstance().loadCircleImageRoleGoods(context, mDetailItemBean.getThumb(), mHolder.item_iv_thumb, 0);
        String Shop_goods_price_id = mDetailItemBean.getShop_goods_price_id();
        if (TextUtils.isEmpty(Shop_goods_price_id)) {
            Shop_goods_price_id = "";
        }
        String key = mDetailItemBean.getShop_goods_id() + "_" + Shop_goods_price_id;
        mHolder.item_layout_check.setTag(key);
        mHolder.item_layout_check.setOnClickListener(mL);
        mHolder.item_layout_del.setTag(key);
        mHolder.item_layout_del.setOnClickListener(mL);
        mHolder.item_layout_sub.setTag(key);
        mHolder.item_layout_sub.setOnClickListener(mL);
        mHolder.item_tv_add.setTag(key);
        mHolder.item_tv_add.setOnClickListener(mL);
        return convertView;
    }

    View.OnClickListener mL = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String key = (String) v.getTag();
            Message message = new Message();
            message.obj = key;
            switch (v.getId()) {
                case R.id.item_layout_check:
                    message.what = ShopCartActivity.SELECT;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                case R.id.item_layout_del:
                    message.what = ShopCartActivity.DEL;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                case R.id.item_layout_sub:
                    message.what = ShopCartActivity.subtract;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
                case R.id.item_tv_add:
                    message.what = ShopCartActivity.ADD;
                    mHandler.sendMessage(message);
                    message = null;
                    break;
            }
        }
    };


    private class ViewHolder {
        private LinearLayout item_layout_check;
        private TextView item_tv_check;
        private ImageView item_iv_thumb;
        private TextView item_tv_name1;
        private TextView item_tv_name2;
        private TextView item_tv_skb;
        private LinearLayout item_layout_del;

        private LinearLayout item_layout_sub;
        private TextView item_tv_goodsnum;
        private TextView item_tv_add;
        private LinearLayout item_tv_sub;

        public ViewHolder(View view) {
            item_layout_check = (LinearLayout) view.findViewById(R.id.item_layout_check);
            item_tv_check = (TextView) view.findViewById(R.id.item_tv_check);
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name1 = (TextView) view.findViewById(R.id.item_tv_name1);
            item_tv_skb = (TextView) view.findViewById(R.id.item_tv_skb);
            item_tv_name2 = (TextView) view.findViewById(R.id.item_tv_name2);
            item_layout_del = (LinearLayout) view.findViewById(R.id.item_layout_del);
            item_layout_sub = (LinearLayout) view.findViewById(R.id.item_layout_sub);
            item_tv_sub = (LinearLayout) view.findViewById(R.id.item_tv_sub);
            item_tv_goodsnum = (TextView) view.findViewById(R.id.item_tv_goodsnum);
            item_tv_add = (TextView) view.findViewById(R.id.item_tv_add);
        }
    }
}
