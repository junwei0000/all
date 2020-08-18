package com.longcheng.volunteer.modular.mine.bill.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.modular.mine.bill.bean.BillItemBean;
import com.longcheng.volunteer.modular.mine.bill.widget.BillHeaderView;
import com.longcheng.volunteer.modular.mine.bill.widget.BillListDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Burning on 2018/8/31.
 */

public class AdapterBillListAdapter extends BaseAdapter {
    List<BillItemBean> data = new ArrayList<BillItemBean>();
    private Context mContext;

    public AdapterBillListAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void refresh(List<BillItemBean> list, boolean clear) {
        if (list != null && !list.isEmpty()) {
            if (clear) {
                data.clear();
            }
            data.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public BillItemBean getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_bill_list, null, false);
            holder = new ViewHolder();
            holder.billItem = convertView.findViewById(R.id.billitem);
            holder.billMonthInfo = convertView.findViewById(R.id.billmonthinfo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BillItemBean item = getItem(position);
        boolean isMonth = item.getIs_month() == 1;
        if (isMonth) {
            holder.billItem.setVisibility(View.GONE);
            holder.billMonthInfo.refreshMainView(item);
        } else {
            holder.billMonthInfo.setVisibility(View.GONE);
            holder.billItem.refresh(item);
        }
        return convertView;
    }

    private class ViewHolder {
        public BillHeaderView billMonthInfo;
        public BillListDetail billItem;
    }
}
