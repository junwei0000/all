package com.longcheng.lifecareplanTv.home.dynamic.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplanTv.R;
import com.longcheng.lifecareplanTv.base.BaseAdapterHelper;
import com.longcheng.lifecareplanTv.home.dynamic.bean.DynamicInfo;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class DynamicAdapter extends BaseAdapterHelper<DynamicInfo> {
    private ViewHolder mHolder = null;
    private Context context;
    private static int selectItem = -1;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
        super.notifyDataSetChanged();
    }

    public DynamicAdapter(Context context, List<DynamicInfo> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DynamicInfo> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dynamic_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        if ((selectItem == position)) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.white));
            convertView.getBackground().setAlpha(35);
            Log.e("convertView", "  selectItem=  " + selectItem + "  ;position=  " + position);
        } else {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }
        return convertView;
    }


    private class ViewHolder {
        private ImageView iv_avatar;
        private TextView tv_content, tv_time;

        public ViewHolder(View view) {
            iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
            tv_content = (TextView) view.findViewById(R.id.tv_content);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }
    }
}
