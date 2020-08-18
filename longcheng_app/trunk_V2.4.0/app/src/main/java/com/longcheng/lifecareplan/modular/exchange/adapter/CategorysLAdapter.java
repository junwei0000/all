package com.longcheng.lifecareplan.modular.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.exchange.bean.JieQiItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class CategorysLAdapter extends BaseAdapterHelper<JieQiItemBean> {
    ViewHolder mHolder = null;

    Context context;
    int selectPoistion;

    public void setSelectPoistion(int selectPoistion) {
        this.selectPoistion = selectPoistion;
    }

    public CategorysLAdapter(Context context, List<JieQiItemBean> list, int selectPoistion) {
        super(context, list);
        this.context = context;
        this.selectPoistion = selectPoistion;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<JieQiItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exchange_categorys_listitem, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        JieQiItemBean mJieQiItemBean = list.get(position);
        mHolder.item_tv_name.setText(mJieQiItemBean.getName());
        int top = DensityUtil.dip2px(context, 6);
        mHolder.item_tv_name.setPadding(0, top, 0, top);
        if (position == selectPoistion) {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_redprogress);
            ColorChangeByTime.getInstance().changeDrawableToClolor(context, mHolder.item_tv_name, R.color.red);
        } else {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.text_biaoTi_color));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_write);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_name;

        public ViewHolder(View view) {
            item_tv_name = view.findViewById(R.id.item_tv_name);
        }
    }
}
