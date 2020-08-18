package com.longcheng.volunteer.modular.helpwith.applyhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.modular.helpwith.applyhelp.bean.PeopleItemBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class PeopleListAdapter extends BaseAdapterHelper<PeopleItemBean> {
    ViewHolder mHolder = null;

    Context context;

    public PeopleListAdapter(Context context, List<PeopleItemBean> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PeopleItemBean> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_apply_peoplelist_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        PeopleItemBean mHelpWithInfo = list.get(position);

        mHolder.item_tv_name.setText(mHelpWithInfo.getUser_name());
        mHolder.item_tv_phone.setText(mHelpWithInfo.getPhone());
        mHolder.item_tv_calluser.setText(mHelpWithInfo.getCall_user());
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_name;
        private TextView item_tv_phone;
        private TextView item_tv_calluser;

        public ViewHolder(View view) {
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_phone = (TextView) view.findViewById(R.id.item_tv_phone);
            item_tv_calluser = (TextView) view.findViewById(R.id.item_tv_calluser);
        }
    }
}
