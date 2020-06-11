package com.longcheng.lifecareplan.modular.mine.message.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.message.bean.MessageItemBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MessageAdapter extends BaseAdapterHelper<MessageItemBean> {
    ViewHolder mHolder = null;
    Activity context;

    public MessageAdapter(Activity context, List<MessageItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MessageItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_message_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MessageItemBean mActionItemBean = list.get(position);
        mHolder.item_tv_date.setText("" + mActionItemBean.getDate());
        mHolder.item_tv_title.setText(mActionItemBean.getTitle());
        mHolder.item_tv_content.setText(mActionItemBean.getContent());
        List<MessageItemBean> infoList = mActionItemBean.getInfo();
        mHolder.item_listview_info.removeAllViews();
        if (infoList != null && infoList.size() > 0) {
            for (int i = 0; i < infoList.size(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.text_item, null);
                TextView textView = view.findViewById(R.id.tv_cont);
                textView.setText(infoList.get(i).getKey() + infoList.get(i).getValue());
                mHolder.item_listview_info.addView(view);
            }
        }
        int is_read = mActionItemBean.getIs_read();
        if (is_read == 1) {
            mHolder.iv_read.setVisibility(View.GONE);
        } else {
            mHolder.iv_read.setVisibility(View.VISIBLE);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_date;
        private TextView item_tv_title;
        private TextView item_tv_content;
        private LinearLayout item_listview_info;
        private ImageView iv_read;

        public ViewHolder(View view) {
            item_tv_date = view.findViewById(R.id.item_tv_date);
            item_tv_title = view.findViewById(R.id.item_tv_title);
            item_tv_content = view.findViewById(R.id.item_tv_content);
            item_listview_info = view.findViewById(R.id.item_listview_info);
            iv_read = view.findViewById(R.id.iv_read);
        }
    }
}
