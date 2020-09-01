package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.MineFubaoDetailActivity;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao.MineFubaoSendDetailActivity;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.PriceUtils;
import com.longcheng.lifecareplan.utils.date.DatesUtils;

import java.util.List;

public class MineFubaoDetailAdapter extends BaseAdapterHelper<FuListBean> {
    private Context context;
    ViewHolder mHolder = null;

    public MineFubaoDetailAdapter(Context context, List<FuListBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<FuListBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fubao_minefubao_item_detail, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        FuListBean fuListBean = list.get(position);
        mHolder.item_tv_name.setText(fuListBean.getReceive_user_name());
        mHolder.item_tv_time.setText(fuListBean.getReceive_user_phone());
        int status = fuListBean.getStatus();
        if (status >= 2) {
            mHolder.tv_status.setText("已领取");
            mHolder.tv_status.setBackgroundResource(R.drawable.pioneer_zong_gradient_shape20);
        } else if (status == -1) {
            mHolder.tv_status.setText("已退回");
            mHolder.tv_status.setBackgroundResource(R.drawable.pioneer_zong_gradient_shape20);
        } else {
            mHolder.tv_status.setText("未领取");
            mHolder.tv_status.setBackgroundResource(R.drawable.pioneer_zong_gradient_shape20);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_name;
        private TextView item_tv_time;
        private TextView tv_status;
        private RelativeLayout layout_item;

        public ViewHolder(View convertView) {
            item_tv_name = convertView.findViewById(R.id.item_tv_name);
            item_tv_time = convertView.findViewById(R.id.item_tv_time);
            layout_item = convertView.findViewById(R.id.layout_item);
            tv_status = convertView.findViewById(R.id.tv_status);
        }
    }
}
