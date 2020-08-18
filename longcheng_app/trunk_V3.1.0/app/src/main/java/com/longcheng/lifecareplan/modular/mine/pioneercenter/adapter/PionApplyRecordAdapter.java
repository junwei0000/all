package com.longcheng.lifecareplan.modular.mine.pioneercenter.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.bean.PioneerDataListBean;
import com.longcheng.lifecareplan.modular.mine.pioneercenter.openPionSet.bean.PionOpenSetRecordDataBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class PionApplyRecordAdapter extends BaseAdapterHelper<PioneerDataListBean.RecordBean> {
    ViewHolder mHolder = null;
    Activity context;

    public PionApplyRecordAdapter(Activity context, List<PioneerDataListBean.RecordBean> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PioneerDataListBean.RecordBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pionner_applyrecord_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PioneerDataListBean.RecordBean mOrderItemBean = list.get(position);
        int status = mOrderItemBean.getStatus();
        if (status == 0) {
            mHolder.item_tv_status.setText("审核中");
            mHolder.item_tv_status.setTextColor(context.getResources().getColor(R.color.red));
        } else {
            mHolder.item_tv_status.setText("已完成");
            mHolder.item_tv_status.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        }
        mHolder.item_tv_money.setText(mOrderItemBean.getMoney());
        mHolder.item_tv_time.setText(mOrderItemBean.getCreate_time());
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_money;
        private TextView item_tv_time;
        private TextView item_tv_status;

        public ViewHolder(View view) {
            item_tv_money = view.findViewById(R.id.item_tv_money);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_status = view.findViewById(R.id.item_tv_status);
        }
    }
}
