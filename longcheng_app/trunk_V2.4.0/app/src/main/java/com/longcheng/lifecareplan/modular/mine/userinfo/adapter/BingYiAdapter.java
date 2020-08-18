package com.longcheng.lifecareplan.modular.mine.userinfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.UserSetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class BingYiAdapter extends BaseAdapterHelper<UserSetBean.UserSetInfoBean> {
    String political_status;

    public BingYiAdapter(Context context, ArrayList<UserSetBean.UserSetInfoBean> list, String political_status) {
        super(context, list);
        this.political_status = political_status;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<UserSetBean.UserSetInfoBean> list, LayoutInflater inflater) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_account_bingyi_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.item_tv_bingyi.setText(list.get(position).getName());
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_bingyi;

        public ViewHolder(View view) {
            item_tv_bingyi = view.findViewById(R.id.item_tv_bingyi);
        }
    }
}
