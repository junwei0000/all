package com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;


/**
 * Created by gerry on 2018/9/11.
 */

public class ContectHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public TextView tvCode;
    public ImageView selectbt;
    public TextView itemBtn;
    public LinearLayout item_layout;
    public ContectHolder(View itemView) {
        super(itemView);
        tvName =  itemView.findViewById(R.id.contact_name);
        tvCode =   itemView.findViewById(R.id.contact_number);
        selectbt = itemView.findViewById(R.id.select_btn);
        itemBtn = itemView.findViewById(R.id.item_btn);
        item_layout = itemView.findViewById(R.id.item_layout);
    }
}
