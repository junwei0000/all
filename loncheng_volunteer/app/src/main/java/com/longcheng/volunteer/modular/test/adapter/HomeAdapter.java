package com.longcheng.volunteer.modular.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class HomeAdapter extends BaseAdapterHelper<String> {

    public HomeAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<String> list, LayoutInflater inflater) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_layout, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.mTextVeiw.setText(list.get(position));
        return convertView;
    }

    private class ViewHolder {
        private TextView mTextVeiw;

        public ViewHolder(View view) {
            mTextVeiw = (TextView) view.findViewById(R.id.tv_item);
        }
    }
}
