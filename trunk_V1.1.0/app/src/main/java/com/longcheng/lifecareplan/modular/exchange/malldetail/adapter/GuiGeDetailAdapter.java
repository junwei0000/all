package com.longcheng.lifecareplan.modular.exchange.malldetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class GuiGeDetailAdapter extends BaseAdapterHelper<DetailItemBean> {
    ViewHolder mHolder = null;

    Context context;
    int guigeSelectPosition;

    public GuiGeDetailAdapter(Context context, List<DetailItemBean> list) {
        super(context, list);
        this.context = context;
    }

    public void setGuigeSelectPosition(int guigeSelectPosition) {
        this.guigeSelectPosition = guigeSelectPosition;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DetailItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exchange_categorys_listitem, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        int height = DensityUtil.dip2px(context, 40);
        mHolder.item_tv_name.setHeight(height);
        DetailItemBean mJieQiItemBean = list.get(position);
        mHolder.item_tv_name.setText(mJieQiItemBean.getPrice_name());
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
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
        }
    }
}
