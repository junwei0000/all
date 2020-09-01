package com.longcheng.lifecareplan.modular.helpwith.energy.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.energy.bean.ActionItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ActionSelectAdapter extends BaseAdapterHelper<ActionItemBean> {
    ViewHolder mHolder = null;

    int selectMoneyIndex;
    Context context;
    String unit;

    public void setSelectMoneyIndex(int selectMoneyIndex) {
        this.selectMoneyIndex = selectMoneyIndex;
    }

    public ActionSelectAdapter(Context context, List<ActionItemBean> list, int selectMoneyIndex, String unit) {
        super(context, list);
        this.context = context;
        this.unit = unit;
        this.selectMoneyIndex = selectMoneyIndex;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ActionItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_select_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int width = DensityUtil.screenWith(context) * 3 / 4 - DensityUtil.dip2px(context, 30);
        mHolder.item_layout_cont.setLayoutParams(new RelativeLayout.LayoutParams(width / 2, DensityUtil.dip2px(context, 45)));

        ActionItemBean mActionItemBean = list.get(position);
        String name = mActionItemBean.getName();
        if (!TextUtils.isEmpty(name) && name.contains("：")) {
            name = name.substring(name.indexOf("：") + 1);
        }
        mHolder.item_tv_cont.setText(name);
        mHolder.item_tv_num.setText("(进行中" + mActionItemBean.getIng_count() + ")");
        if (position == 0) {
            mHolder.item_tv_num.setVisibility(View.GONE);
        } else {
            mHolder.item_tv_num.setVisibility(View.VISIBLE);
        }
        if (selectMoneyIndex == position) {
            mHolder.item_tv_cont.setTextColor(context.getResources().getColor(R.color.red));
            mHolder.item_tv_num.setTextColor(context.getResources().getColor(R.color.red));
            mHolder.item_layout_cont.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_iv_select.setVisibility(View.VISIBLE);
            mHolder.item_iv_select.setBackgroundResource(R.mipmap.pay_selcet_icon_red);
            mHolder.item_layout_cont.setPadding(0, 0, 0, 0);
        } else {
            mHolder.item_iv_select.setVisibility(View.GONE);
            mHolder.item_tv_cont.setTextColor(context.getResources().getColor(R.color.text_contents_color));
            mHolder.item_tv_num.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
            mHolder.item_layout_cont.setBackgroundResource(R.drawable.corners_bg_selectnot);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_cont;
        private TextView item_tv_num;
        private ImageView item_iv_select;
        private RelativeLayout item_layout_cont;

        public ViewHolder(View view) {
            item_tv_cont = view.findViewById(R.id.item_tv_cont);
            item_tv_num = view.findViewById(R.id.item_tv_num);
            item_iv_select = view.findViewById(R.id.item_iv_select);
            item_layout_cont = view.findViewById(R.id.item_layout_cont);

        }
    }
}
