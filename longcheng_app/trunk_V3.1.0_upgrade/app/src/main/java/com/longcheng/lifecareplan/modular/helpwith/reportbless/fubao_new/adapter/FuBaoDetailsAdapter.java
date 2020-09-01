package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.bean.fupackage.FuBaoDetailsListBean;
import com.longcheng.lifecareplan.bean.fupackage.FuListBean;

import java.util.List;

public class FuBaoDetailsAdapter extends BaseAdapterHelper<FuBaoDetailsListBean> {
    private Context context;
    ViewHolder mHolder = null;

    public FuBaoDetailsAdapter(Context context, List<FuBaoDetailsListBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<FuBaoDetailsListBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fudetails_adapter_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        FuBaoDetailsListBean fuListBean = list.get(position);
        mHolder.item_tv_live.setText(fuListBean.getTypeline());
        mHolder.item_tv_over.setText(new StringBuffer().append(fuListBean.getSongnum()).append("/").append(fuListBean.getAllnum()).toString());
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_live;
        private TextView item_tv_over;

        public ViewHolder(View convertView) {
            item_tv_live = convertView.findViewById(R.id.tv_live_num);
            item_tv_over = convertView.findViewById(R.id.tv_over_all);
        }
    }
}