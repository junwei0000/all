package com.longcheng.lifecareplan.modular.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.home.bean.HomeItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 */

public class IconAdapter extends BaseAdapterHelper<HomeItemBean> {
    ViewHolder mHolder = null;
    Context context;

    public IconAdapter(Context context, List<HomeItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<HomeItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_home_icon_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HomeItemBean mHelpItemBean = list.get(position);
        GlideDownLoadImage.getInstance().loadCircleImageCommune(context, mHelpItemBean.getPic(), mHolder.item_iv_img);
        mHolder.item_tv_name.setText(mHelpItemBean.getName());
        int width = DensityUtil.screenWith(context) / 5 - DensityUtil.dip2px(context, 30);
        mHolder.item_iv_img.setLayoutParams(new LinearLayout.LayoutParams(width, width));
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
