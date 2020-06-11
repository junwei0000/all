package com.longcheng.lifecareplan.modular.helpwith.reportbless.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.bean.ReportItemBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class FuLiListAdapter extends BaseAdapterHelper<ReportItemBean> {
    ViewHolder mHolder = null;
    Activity context;
    List<ReportItemBean> list;

    Handler mHandler;
    int type;

    public FuLiListAdapter(Activity context, List<ReportItemBean> list, Handler mHandler, int type) {
        super(context, list);
        this.context = context;
        this.list = list;
        this.mHandler = mHandler;
        this.type = type;
    }

    public List<ReportItemBean> getList() {
        return list;
    }

    public void setList(List<ReportItemBean> list) {
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ReportItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.report_fuli_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ReportItemBean mOrderItemBean = list.get(position);
        mHolder.item_tv_jieqi.setText(mOrderItemBean.getSolar_term_cn() + "福券 " + mOrderItemBean.getBless_card_number());
        mHolder.item_tv_money.setText(mOrderItemBean.getUser_money() + "元");
        //1 在途  ； 2 未领   ；  3 已领
        //status = 3时 用cash_time =2时 用over_time
        int status = mOrderItemBean.getStatus();
        mHolder.item_tv_time.setVisibility(View.GONE);
        mHolder.item_tv_money.setVisibility(View.VISIBLE);
        mHolder.item_tv_status.setVisibility(View.VISIBLE);
        mHolder.item_tv_get.setVisibility(View.GONE);
        if (status == 1) {
            mHolder.item_tv_status.setText("在途");
            if (type == 2) {
                mHolder.item_tv_status.setVisibility(View.GONE);
            }
        } else if (status == 2) {
            if (!TextUtils.isEmpty(mOrderItemBean.getTime())) {
                mHolder.item_tv_time.setText(mOrderItemBean.getTime());
                mHolder.item_tv_time.setVisibility(View.VISIBLE);
            }
            mHolder.item_tv_money.setVisibility(View.GONE);
            mHolder.item_tv_status.setVisibility(View.GONE);
            mHolder.item_tv_get.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_tv_status.setText("已领");
            if (!TextUtils.isEmpty(mOrderItemBean.getTime())) {
                mHolder.item_tv_time.setText(mOrderItemBean.getTime());
                mHolder.item_tv_time.setVisibility(View.VISIBLE);
            }
        }

        mHolder.item_tv_get.setTag(mOrderItemBean.getBless_id());
        mHolder.item_tv_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bless_id = (String) v.getTag();
                Message message = Message.obtain();
                message.what = 1;
                message.obj = bless_id;
                mHandler.sendMessage(message);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_jieqi;
        private TextView item_tv_time;
        private TextView item_tv_money;
        private TextView item_tv_status;
        private TextView item_tv_get;

        public ViewHolder(View view) {
            item_tv_jieqi = view.findViewById(R.id.item_tv_jieqi);
            item_tv_time = view.findViewById(R.id.item_tv_time);
            item_tv_money = view.findViewById(R.id.item_tv_money);
            item_tv_status = view.findViewById(R.id.item_tv_status);
            item_tv_get = view.findViewById(R.id.item_tv_get);
        }
    }
}
