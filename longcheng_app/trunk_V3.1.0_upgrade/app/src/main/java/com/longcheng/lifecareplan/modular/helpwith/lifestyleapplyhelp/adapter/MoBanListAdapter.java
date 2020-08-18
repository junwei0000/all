package com.longcheng.lifecareplan.modular.helpwith.lifestyleapplyhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MoBanListAdapter extends BaseAdapterHelper<String> {
    ViewHolder mHolder = null;

    Context context;

    public MoBanListAdapter(Context context, List<String> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<String> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_apply_peoplelist_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        String str = list.get(position);

        mHolder.item_tv_name.setText(str);
        mHolder.item_tv_phone.setVisibility(View.GONE);
        mHolder.item_tv_calluser.setVisibility(View.GONE);
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_name;
        private TextView item_tv_phone;
        private TextView item_tv_calluser;

        public ViewHolder(View view) {
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_tv_phone = view.findViewById(R.id.item_tv_phone);
            item_tv_calluser = view.findViewById(R.id.item_tv_calluser);
        }
    }
}
