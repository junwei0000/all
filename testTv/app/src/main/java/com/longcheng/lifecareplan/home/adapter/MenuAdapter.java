package com.longcheng.lifecareplan.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.home.bean.MenuInfo;
import com.longcheng.lifecareplan.utils.ConfigUtils;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MenuAdapter extends BaseAdapterHelper<MenuInfo> {
    ViewHolder mHolder = null;

    Context context;

    public MenuAdapter(Context context, List<MenuInfo> list) {
        super(context, list);
        this.context = context;
    }


    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<MenuInfo> list, LayoutInflater inflater) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.menu_item, parent);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MenuInfo mInfo = list.get(position);
        mHolder.bg.setBackgroundResource(mInfo.getBgImgId());
        ConfigUtils.getINSTANCE().setSelectFouseText(convertView);
        return convertView;
    }


    private class ViewHolder {
        private RelativeLayout bg;

        public ViewHolder(View view) {
            bg = (RelativeLayout) view.findViewById(R.id.bg);
        }
    }
}
