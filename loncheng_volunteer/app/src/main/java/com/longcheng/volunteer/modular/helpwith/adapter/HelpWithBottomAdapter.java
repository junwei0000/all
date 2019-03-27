package com.longcheng.volunteer.modular.helpwith.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.modular.helpwith.bean.HelpWithInfo;
import com.longcheng.volunteer.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HelpWithBottomAdapter extends BaseAdapterHelper<HelpWithInfo> {
    ViewHolder mHolder = null;
    private String myBlessHelpCount, blessMeHelpCount;
    Context context;

    public HelpWithBottomAdapter(Context context, List<HelpWithInfo> list, String myBlessHelpCount, String blessMeHelpCount) {
        super(context, list);
        this.context = context;
        this.myBlessHelpCount = myBlessHelpCount;
        this.blessMeHelpCount = blessMeHelpCount;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HelpWithInfo> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_helpwith_bottomitem, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HelpWithInfo mHelpWithInfo = list.get(position);
        mHolder.item_tv_cont.setText(mHelpWithInfo.getName());
        int width = DensityUtil.screenWith(context) / 5 - DensityUtil.dip2px(context, 30);
        mHolder.item_iv_select.setLayoutParams(new FrameLayout.LayoutParams(width, width));
        mHolder.item_iv_select.setBackgroundResource(mHelpWithInfo.getImgId());
        if (position > 1) {
            mHolder.item_tv_num.setVisibility(View.GONE);
        } else {
            mHolder.item_tv_num.setVisibility(View.VISIBLE);
        }
        if (position == 0) {
            mHolder.item_tv_num.setText("" + myBlessHelpCount);
            if (myBlessHelpCount.equals("0")) {
                mHolder.item_tv_num.setVisibility(View.GONE);
            } else {
                mHolder.item_tv_num.setVisibility(View.VISIBLE);
            }
        } else if (position == 1) {
            mHolder.item_tv_num.setText("" + blessMeHelpCount);
            if (blessMeHelpCount.equals("0")) {
                mHolder.item_tv_num.setVisibility(View.GONE);
            } else {
                mHolder.item_tv_num.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_cont;
        private TextView item_tv_num;
        private ImageView item_iv_select;

        public ViewHolder(View view) {
            item_tv_cont = (TextView) view.findViewById(R.id.item_tv_cont);
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
            item_iv_select = (ImageView) view.findViewById(R.id.item_iv_select);
        }
    }
}
