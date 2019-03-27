package com.longcheng.volunteer.modular.helpwith.energy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class SelectAdapter extends BaseAdapterHelper<String> {
    ViewHolder mHolder = null;

    int selectMoneyIndex;
    Context context;
    String unit;

    public void setSelectMoneyIndex(int selectMoneyIndex) {
        this.selectMoneyIndex = selectMoneyIndex;
    }

    public SelectAdapter(Context context, List<String> list, int selectMoneyIndex, String unit) {
        super(context, list);
        this.context = context;
        this.unit = unit;
        this.selectMoneyIndex = selectMoneyIndex;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<String> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_select_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int width = DensityUtil.screenWith(context) * 3 / 4 - DensityUtil.dip2px(context, 30);
        mHolder.item_layout_cont.setLayoutParams(new RelativeLayout.LayoutParams(width / 2, DensityUtil.dip2px(context, 45)));

        mHolder.item_tv_cont.setText(list.get(position) + unit);
        if (selectMoneyIndex == position) {
            mHolder.item_tv_cont.setTextColor(context.getResources().getColor(R.color.red));
            mHolder.item_layout_cont.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_iv_select.setBackgroundResource(R.mipmap.pay_selcet_icon_red);
            mHolder.item_iv_select.setVisibility(View.VISIBLE);
            mHolder.item_layout_cont.setPadding(0, 0, 0, 0);
        } else {
            mHolder.item_iv_select.setVisibility(View.GONE);
            mHolder.item_tv_cont.setTextColor(context.getResources().getColor(R.color.text_contents_color));
            mHolder.item_layout_cont.setBackgroundResource(R.drawable.corners_bg_selectnot);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_cont;
        private ImageView item_iv_select;
        private RelativeLayout item_layout_cont;

        public ViewHolder(View view) {
            item_tv_cont = (TextView) view.findViewById(R.id.item_tv_cont);
            item_iv_select = (ImageView) view.findViewById(R.id.item_iv_select);
            item_layout_cont = (RelativeLayout) view.findViewById(R.id.item_layout_cont);

        }
    }
}
