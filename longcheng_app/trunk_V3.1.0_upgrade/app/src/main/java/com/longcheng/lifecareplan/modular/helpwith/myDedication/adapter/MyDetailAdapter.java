package com.longcheng.lifecareplan.modular.helpwith.myDedication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.myDedication.bean.ItemBean;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyDetailAdapter extends BaseAdapterHelper<ItemBean> {
    ViewHolder mHolder = null;

    Context context;


    public MyDetailAdapter(Context context, List<ItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_mydedication_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.item_iv_thumb.setVisibility(View.GONE);
        ItemBean mActionItemBean = list.get(position);
        mHolder.item_tv_name.setText(mActionItemBean.getPay_time());
        mHolder.item_tv_shequ.setText(mActionItemBean.getGoods_x_name());
        mHolder.item_tv_engerynum.setText(mActionItemBean.getAbility_one_price());
        return convertView;
    }


    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_shequ;
        private TextView item_tv_engerynum;

        public ViewHolder(View view) {
            item_iv_thumb = view.findViewById(R.id.item_iv_thumb);
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_shequ = view.findViewById(R.id.item_tv_shequ);
            item_tv_engerynum = view.findViewById(R.id.item_tv_engerynum);

        }
    }
}
