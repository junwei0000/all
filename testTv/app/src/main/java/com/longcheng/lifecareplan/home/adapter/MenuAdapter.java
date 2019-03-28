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
            convertView = inflater.inflate(R.layout.menu_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        MenuInfo mHelpWithInfo = list.get(position);
        mHolder.item_tv_cont.setText(mHelpWithInfo.getName());
        mHolder.item_tv_cont2.setText(mHelpWithInfo.getName2());
        mHolder.item_iv_select.setBackgroundResource(mHelpWithInfo.getBgColorId());

        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 30)) / 2;
        int height = (int) (width * 0.476);
        mHolder.item_iv_select.setLayoutParams(new RelativeLayout.LayoutParams(width, height));

        int imgwidth = (int) ((height - 10) * 1.68);
        mHolder.item_iv_img.setLayoutParams(new RelativeLayout.LayoutParams(imgwidth, height - 10));
        if (solarTermsEnsImg != null && solarTermsEnsImg.size() - 1 >= position) {
            GlideDownLoadImage.getInstance().loadCircleImageHelpIndex(context, solarTermsEnsImg.get(position), mHolder.item_iv_img);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_cont;
        private TextView item_tv_cont2;
        private ImageView item_iv_select;
        private ImageView item_iv_img;

        public ViewHolder(View view) {
            item_tv_cont = (TextView) view.findViewById(R.id.item_tv_cont);
            item_tv_cont2 = (TextView) view.findViewById(R.id.item_tv_cont2);
            item_iv_select = (ImageView) view.findViewById(R.id.item_iv_select);
            item_iv_img = (ImageView) view.findViewById(R.id.item_iv_img);
        }
    }
}
