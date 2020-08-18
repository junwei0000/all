package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CreateBean;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class SelectPeoAdapter extends BaseAdapterHelper<CreateBean> {
    ViewHolder mHolder = null;

    Context context;
    int selectPosition;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public SelectPeoAdapter(Context context, List<CreateBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CreateBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_list_selectall_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int width = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 20);
        mHolder.item_tv_name.setLayoutParams(new LinearLayout.LayoutParams(width / 4, DensityUtil.dip2px(context, 45)));

        CreateBean mActionItemBean = list.get(position);
        String name = mActionItemBean.getTitle();
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_name.setGravity(Gravity.CENTER);
        int wid = DensityUtil.dip2px(context, 4);
        mHolder.item_tv_name.setPadding(0, wid, 0, wid);
        if (position == selectPosition) {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_tv_name.setBackgroundResource(R.mipmap.create_select);
        } else {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.color_666666));
            mHolder.item_tv_name.setBackgroundResource(R.mipmap.create_selectnot);
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
