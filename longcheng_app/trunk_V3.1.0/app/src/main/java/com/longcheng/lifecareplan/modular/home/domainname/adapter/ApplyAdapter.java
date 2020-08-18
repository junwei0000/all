package com.longcheng.lifecareplan.modular.home.domainname.adapter;

import android.content.Context;
import android.text.Html;
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
 * 意图：热推互助
 */

public class ApplyAdapter extends BaseAdapterHelper<String> {
    ViewHolder mHolder = null;
    Context context;

    public ApplyAdapter(Context context, List<String> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<String> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.domain_menu_applyitem, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        String mHelpItemBean = list.get(position);
        int width = (DensityUtil.screenWith(context) - DensityUtil.dip2px(context, 10)) / 2;
        int height = (int) (width * 0.63);
        mHolder.item_iv_img.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        mHolder.item_iv_img.setScaleType(ImageView.ScaleType.FIT_XY);
        if (position == 0) {
            mHolder.item_iv_img.setBackgroundResource(R.mipmap.apply_gongshe);
        } else {
            mHolder.item_iv_img.setBackgroundResource(R.mipmap.apply_dadui);
        }
        return convertView;
    }

    private class ViewHolder {
        private ImageView item_iv_img;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
        }
    }
}
