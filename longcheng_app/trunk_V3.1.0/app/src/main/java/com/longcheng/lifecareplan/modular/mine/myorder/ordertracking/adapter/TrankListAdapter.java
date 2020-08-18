package com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.myorder.ordertracking.bean.TrankItemBean;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class TrankListAdapter extends BaseAdapterHelper<TrankItemBean> {
    ViewHolder mHolder = null;
    Context context;
    List<TrankItemBean> list;

    public TrankListAdapter(Context context, List<TrankItemBean> list) {
        super(context, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<TrankItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_ordertrank_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        TrankItemBean mOrderItemBean = list.get(position);
        mHolder.item_tv_name.setText(mOrderItemBean.getAcceptStation());
        mHolder.item_tv_time.setText(mOrderItemBean.getAcceptTime());
        if (position == 0) {
            mHolder.item_iv_img.setBackgroundResource(R.mipmap.my_ordertracking_icon2);
        } else {
            mHolder.item_iv_img.setBackgroundResource(R.mipmap.my_ordertracking_icon3);
        }
        if (position + 1 == list.size()) {
            mHolder.item_tv_line.setVisibility(View.INVISIBLE);
        } else {
            mHolder.item_tv_line.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;
        private TextView item_tv_time;
        private TextView item_tv_line;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_line = view.findViewById(R.id.item_tv_line);
        }
    }
}
