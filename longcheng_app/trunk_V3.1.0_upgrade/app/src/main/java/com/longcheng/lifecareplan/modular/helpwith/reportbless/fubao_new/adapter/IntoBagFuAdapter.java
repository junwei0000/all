package com.longcheng.lifecareplan.modular.helpwith.reportbless.fubao_new.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.bean.contactbean.PhoneBean;
import com.longcheng.lifecareplan.bean.contactbean.SelectPhone;
import com.longcheng.lifecareplan.bean.fupackage.FuBaoDetailsListBean;
import com.longcheng.lifecareplan.utils.Utils;

import java.util.List;

public class IntoBagFuAdapter extends BaseAdapterHelper<PhoneBean> {
    private Context context;
    ViewHolder mHolder = null;
    String contentbody;
    public IntoBagFuAdapter(Context context, List<PhoneBean> list,String contentbody) {
        super(context, list);
        this.context = context;
        this.contentbody = contentbody;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PhoneBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.select_adapter_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PhoneBean fuListBean = list.get(position);
        mHolder.tvName.setText(fuListBean.getName());
        mHolder.tvCode.setText(fuListBean.getPhone());

        mHolder.itemBtn.setTag(fuListBean);
        mHolder.itemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneBean fuListBean = (PhoneBean) view.getTag();
                new Utils().doSendSMSTo(context,fuListBean.getPhone(),contentbody);
            }

        });
        return convertView;
    }

    public class ViewHolder {
        public TextView tvName;
        public TextView tvCode;
        public ImageView selectbt;
        public TextView itemBtn;
        public ViewHolder(View itemView) {
            tvName = (TextView) itemView.findViewById(R.id.contact_name);
            tvCode = (TextView) itemView.findViewById(R.id.contact_number);
            selectbt = itemView.findViewById(R.id.select_btn);
            itemBtn = itemView.findViewById(R.id.item_btn);
        }
    }

}