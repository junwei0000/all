package com.longcheng.lifecareplan.modular.helpwith.applyhelp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.modular.helpwith.applyhelp.bean.ExplainItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class GuiGeAdapter extends BaseAdapterHelper<ExplainItemBean> {
    ViewHolder mHolder = null;

    Context context;
    int guigeSelectPosition;

    public GuiGeAdapter(Context context, List<ExplainItemBean> list) {
        super(context, list);
        this.context = context;
    }

    public void setGuigeSelectPosition(int guigeSelectPosition) {
        this.guigeSelectPosition = guigeSelectPosition;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ExplainItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exchange_categorys_listitem, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int height = DensityUtil.dip2px(context, 40);
        mHolder.item_tv_name.setHeight(height);
        ExplainItemBean mJieQiItemBean = list.get(position);
        mHolder.item_tv_name.setText(mJieQiItemBean.getGoods_specs_name());
        if (guigeSelectPosition == position) {
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_redbian);
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.blue));
        } else {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.text_contents_color));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_black);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_name;

        public ViewHolder(View view) {
            item_tv_name = view.findViewById(R.id.item_tv_name);
        }
    }
}
