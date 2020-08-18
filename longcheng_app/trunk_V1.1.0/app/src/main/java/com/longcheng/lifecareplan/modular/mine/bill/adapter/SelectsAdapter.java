package com.longcheng.lifecareplan.modular.mine.bill.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.bill.bean.BillItemBean;
import com.longcheng.lifecareplan.modular.mine.goodluck.activity.OpenRedEnvelopeActivity;
import com.longcheng.lifecareplan.modular.mine.goodluck.bean.GoodLuckBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class SelectsAdapter extends BaseAdapterHelper<BillItemBean> {
    ViewHolder mHolder = null;

    Activity context;

    int selectMoneyIndex;

    public SelectsAdapter(Activity context, List<BillItemBean> list) {
        super(context, list);
        this.context = context;
    }

    public int getSelectMoneyIndex() {
        return selectMoneyIndex;
    }

    public void setSelectMoneyIndex(int selectMoneyIndex) {
        this.selectMoneyIndex = selectMoneyIndex;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<BillItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_select_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int width = DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 40);
        mHolder.item_layout_cont.setLayoutParams(new RelativeLayout.LayoutParams(width / 3, DensityUtil.dip2px(context, 45)));
        mHolder.item_iv_select.setVisibility(View.GONE);
        BillItemBean mActionItemBean = list.get(position);
        mHolder.item_tv_cont.setText(mActionItemBean.getName());
        if (selectMoneyIndex == position) {
            mHolder.item_tv_cont.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_layout_cont.setBackgroundResource(R.drawable.corners_bg_login);
        } else {
            mHolder.item_tv_cont.setTextColor(context.getResources().getColor(R.color.text_biaoTi_color));
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
