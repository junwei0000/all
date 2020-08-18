package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpDetailDataBean;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CreateBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class CreateTopUserAdapter extends BaseAdapterHelper<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> {
    ViewHolder mHolder = null;

    Context context;
    int selectPosition;

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    public CreateTopUserAdapter(Context context, List<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_create_top_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int width = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 50);
        mHolder.item_tv_name.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));
        mHolder.item_tv_name.setPadding(0, DensityUtil.dip2px(context, 4), 0, DensityUtil.dip2px(context, 4));
        CHelpDetailDataBean.CHelpDetailAfterBean.DetailItemBean mActionItemBean = list.get(position);
        String name = mActionItemBean.getUser_name();
        mHolder.item_tv_name.setText(CommonUtil.setName3(name));
        mHolder.item_tv_name.setGravity(Gravity.CENTER);
        if (position == selectPosition) {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_zong_shi);
        } else {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.yellow_E95D1B));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_zong_bian);
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
