package com.longcheng.volunteer.modular.helpwith.myfamily.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.modular.helpwith.myfamily.bean.ItemBean;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyAdapter extends BaseAdapterHelper<ItemBean> {
    ViewHolder mHolder = null;

    Context context;
    Handler mHandler;
    int mHandlerID;

    public MyAdapter(Context context, List<ItemBean> list, Handler mHandler, int mHandlerID) {
        super(context, list);
        this.context = context;
        this.mHandler = mHandler;
        this.mHandlerID = mHandlerID;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_myfamily_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        ItemBean mActionItemBean = list.get(position);
        mHolder.item_tv_name.setText(mActionItemBean.getUser_name());
        mHolder.item_tv_shequ.setText(mActionItemBean.getCall_user());
        mHolder.layout_introduce.setTag(position);
        mHolder.layout_introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Message message = new Message();
                message.what = mHandlerID;
                message.arg1 = position;
                mHandler.sendMessage(message);
                message = null;
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_name;
        private TextView item_tv_shequ;
        private LinearLayout layout_introduce;

        public ViewHolder(View view) {
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_shequ = (TextView) view.findViewById(R.id.item_tv_shequ);
            layout_introduce = (LinearLayout) view.findViewById(R.id.layout_introduce);

        }
    }
}
