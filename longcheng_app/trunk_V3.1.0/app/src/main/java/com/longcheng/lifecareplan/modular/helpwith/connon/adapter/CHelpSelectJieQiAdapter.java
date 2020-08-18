package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class CHelpSelectJieQiAdapter extends BaseAdapterHelper<CHelpItemBean> {
    ViewHolder mHolder = null;

    Context context;
    String jieqi_en;

    public void setJieqi_en(String jieqi_en) {
        this.jieqi_en = jieqi_en;
    }

    public CHelpSelectJieQiAdapter(Context context, List<CHelpItemBean> list, String jieqi_en) {
        super(context, list);
        this.context = context;
        this.jieqi_en = jieqi_en;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CHelpItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_list_selectall_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int width = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 70);
        mHolder.item_tv_name.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));

        CHelpItemBean mActionItemBean = list.get(position);
        String name = mActionItemBean.getSolar_terms_name();
        mHolder.item_tv_name.setText(name);
        mHolder.item_tv_name.setGravity(Gravity.CENTER);
        int wid = DensityUtil.dip2px(context, 4);
        mHolder.item_tv_name.setPadding(0, wid, 0, wid);
        if (jieqi_en.equals(mActionItemBean.getSolar_terms_en())) {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_yellow);
        } else {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.color_333333));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_selectnot);
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
