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

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MoBanHuiAdapter extends BaseAdapterHelper<PionImMoBanDataBean.PionImMoBanItemBean> {

    public MoBanHuiAdapter(Context context, ArrayList<PionImMoBanDataBean.PionImMoBanItemBean> list) {
        super(context, list);
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<PionImMoBanDataBean.PionImMoBanItemBean> list, LayoutInflater inflater) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.im_quicklisthui_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.tv_cont.setText(list.get(position).getMain());
        return convertView;
    }


    private class ViewHolder {
        private TextView tv_cont;


        public ViewHolder(View view) {
            tv_cont = view.findViewById(R.id.tv_cont);
        }
    }
}
