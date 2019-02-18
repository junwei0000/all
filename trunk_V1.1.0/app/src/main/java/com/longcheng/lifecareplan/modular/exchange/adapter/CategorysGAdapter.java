package com.longcheng.lifecareplan.modular.exchange.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.exchange.bean.JieQiItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class CategorysGAdapter extends BaseAdapterHelper<JieQiItemBean> {
    ViewHolder mHolder = null;

    Context context;
    int selectCatGCid;

    public void setSelectCatGCid(int selectCatGCid) {
        this.selectCatGCid = selectCatGCid;
    }

    public CategorysGAdapter(Context context, List<JieQiItemBean> list, int selectCatGCid) {
        super(context, list);
        this.context = context;
        this.selectCatGCid = selectCatGCid;

    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<JieQiItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.exchange_categorys_listitem, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        JieQiItemBean mJieQiItemBean = list.get(position);
        mHolder.item_tv_name.setText(mJieQiItemBean.getName());
        mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_goodgray);


        int height = DensityUtil.dip2px(context, 40);
        mHolder.item_tv_name.setHeight(height);
        if (mJieQiItemBean.getCid() == selectCatGCid) {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_login);
        } else {
            mHolder.item_tv_name.setTextColor(context.getResources().getColor(R.color.text_contents_color));
            mHolder.item_tv_name.setBackgroundResource(R.drawable.corners_bg_goodgray);
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
