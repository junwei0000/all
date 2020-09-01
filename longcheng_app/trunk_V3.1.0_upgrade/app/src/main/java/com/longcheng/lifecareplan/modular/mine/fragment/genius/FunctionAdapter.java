package com.longcheng.lifecareplan.modular.mine.fragment.genius;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class FunctionAdapter extends BaseAdapterHelper<FunctionGVItemBean> {
    ViewHolder mHolder = null;
    Context context;

    public FunctionAdapter(Context context, List<FunctionGVItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<FunctionGVItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_mycenter_gongnenggv_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        FunctionGVItemBean mHelpItemBean = list.get(position);
        mHolder.item_iv_img.setBackgroundResource(mHelpItemBean.getImgId());
        mHolder.item_tv_name.setText(mHelpItemBean.getName());
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;
        private TextView item_tv_name;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_tv_name = view.findViewById(R.id.item_tv_name);
        }
    }
}
