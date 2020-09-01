package com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.bean.contactbean.PhoneBean;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.adapter.viewholder.ContectHolder;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.adapter.viewholder.LetterHolder;
import com.longcheng.lifecareplan.modular.helpwith.reportbless.contacts.pinyinutil.PyEntity;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

public class ContactAdapter extends PyAdapter<RecyclerView.ViewHolder> {
    private Context context;
    LayoutInflater layoutInflater;

    public ContactAdapter(Activity context, List<? extends PyEntity> entities) {
        super(entities);
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateLetterHolder(ViewGroup parent, int viewType) {
        return new LetterHolder(layoutInflater.inflate(R.layout.item_letter, parent, false));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ContectHolder(layoutInflater.inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, PyEntity entity, int position) {
        ContectHolder vh = (ContectHolder) holder;
        final PhoneBean phoneBean = (PhoneBean) entity;
        vh.tvName.setText(phoneBean.getName());
        vh.tvCode.setText(phoneBean.getPhone());
        if (phoneBean.isIsadd()) {
            ((ContectHolder) holder).selectbt.setBackgroundResource(R.mipmap.select_fu);
        } else {
            ((ContectHolder) holder).selectbt.setBackgroundResource(R.mipmap.un_select);
        }
        int is_new = phoneBean.getIs_new();//1没有注册平台
        if (is_new == 1) {
            vh.item_layout.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            vh.item_layout.setBackgroundColor(context.getResources().getColor(R.color.color_f9f9f9));
        }
    }

    @Override
    public void onBindLetterHolder(RecyclerView.ViewHolder holder, LetterEntity entity, int position) {
        ((LetterHolder) holder).textView.setText(entity.letter.toUpperCase());
    }
}
