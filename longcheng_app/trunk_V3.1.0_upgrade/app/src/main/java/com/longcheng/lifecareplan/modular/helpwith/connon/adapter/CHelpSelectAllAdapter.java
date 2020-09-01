package com.longcheng.lifecareplan.modular.helpwith.connon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.connon.bean.CHelpItemBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：热推互助
 */

public class CHelpSelectAllAdapter extends BaseAdapterHelper<CHelpItemBean> {
    ViewHolder mHolder = null;
    Context context;

    int selectIndex;

    public CHelpSelectAllAdapter(Context context, List<CHelpItemBean> list, int selectIndex) {
        super(context, list);
        this.context = context;
        this.selectIndex = selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<CHelpItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.connon_list_selectall_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        CHelpItemBean mHelpItemBean = list.get(position);
        mHolder.item_tv_name.setText(CommonUtil.setName(mHelpItemBean.getName()));
        if (position == selectIndex) {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.yellow_E95D1B));
            mHolder.item_iv_select.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.color_333333));
            mHolder.item_iv_select.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_name;
        private ImageView item_iv_select;


        public ViewHolder(View view) {
            item_tv_name = view.findViewById(R.id.item_tv_name);
            item_iv_select = view.findViewById(R.id.item_iv_select);
        }
    }
}
