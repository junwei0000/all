package com.longcheng.lifecareplan.modular.im.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.im.activity.QuickListActivity;
import com.longcheng.lifecareplan.modular.im.bean.PionImMoBanDataBean;
import com.longcheng.lifecareplan.modular.mine.userinfo.bean.UserSetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MoBanAdapter extends BaseAdapterHelper<PionImMoBanDataBean.PionImMoBanItemBean> {
    Handler mHandler;

    public MoBanAdapter(Context context, ArrayList<PionImMoBanDataBean.PionImMoBanItemBean> list, Handler mHandler) {
        super(context, list);
        this.mHandler = mHandler;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionImMoBanDataBean.PionImMoBanItemBean> list, LayoutInflater inflater) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.im_quicklist_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.tv_cont.setText(list.get(position).getMain());
        if (position == 0) {
            mHolder.tv_num.setText("模板一");
        } else if (position == 1) {
            mHolder.tv_num.setText("模板二");
        } else if (position == 2) {
            mHolder.tv_num.setText("模板三");
        } else if (position == 3) {
            mHolder.tv_num.setText("模板四");
        } else if (position == 4) {
            mHolder.tv_num.setText("模板五");
        }
        mHolder.tv_del.setTag(list.get(position).getIm_user_temp_id());
        mHolder.tv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String im_user_temp_id = (String) v.getTag();
                Message message = Message.obtain();
                message.what = QuickListActivity.SkipEDIT;
                message.obj = im_user_temp_id;
                mHandler.sendMessage(message);
            }
        });
        return convertView;
    }


    private class ViewHolder {
        private TextView tv_num;
        private TextView tv_del;
        private TextView tv_cont;


        public ViewHolder(View view) {
            tv_num = view.findViewById(R.id.tv_num);
            tv_del = view.findViewById(R.id.tv_del);
            tv_cont = view.findViewById(R.id.tv_cont);
        }
    }
}
