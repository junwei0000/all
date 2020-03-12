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
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class JieQiAdapter extends BaseAdapterHelper<JieQiItemBean> {
    ViewHolder mHolder = null;

    String current_solar_en;
    Context context;

    public JieQiAdapter(Context context, List<JieQiItemBean> list, String current_solar_en) {
        super(context, list);
        this.context = context;
        this.current_solar_en = current_solar_en;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<JieQiItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dialog_mallgoods_jieqi_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        JieQiItemBean mJieQiItemBean = list.get(position);
        mHolder.item_tv_cn.setText(mJieQiItemBean.getSolar_terms_name());
        if (current_solar_en.equals(mJieQiItemBean.getSolar_terms_en())) {
            mHolder.item_tv_currentjieqi.setVisibility(View.VISIBLE);
        } else {
            mHolder.item_tv_currentjieqi.setVisibility(View.GONE);
        }
        if (position == 0) {
            mHolder.item_iv_img.setBackgroundResource(R.mipmap.mall_icon_24);
        } else {
            GlideDownLoadImage.getInstance().loadCircleImage(mJieQiItemBean.getLogo(), mHolder.item_iv_img);
        }
        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_currentjieqi;
        private ImageView item_iv_img;
        private TextView item_tv_cn;

        public ViewHolder(View view) {
            item_tv_currentjieqi = (TextView) view.findViewById(R.id.item_tv_currentjieqi);
            item_iv_img = (ImageView) view.findViewById(R.id.item_iv_img);
            item_tv_cn = (TextView) view.findViewById(R.id.item_tv_cn);

        }
    }
}
