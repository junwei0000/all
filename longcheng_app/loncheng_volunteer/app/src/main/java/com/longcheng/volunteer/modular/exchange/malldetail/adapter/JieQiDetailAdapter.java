package com.longcheng.volunteer.modular.exchange.malldetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.volunteer.R;
import com.longcheng.volunteer.base.BaseAdapterHelper;
import com.longcheng.volunteer.modular.exchange.malldetail.bean.DetailItemBean;
import com.longcheng.volunteer.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class JieQiDetailAdapter extends BaseAdapterHelper<DetailItemBean> {
    ViewHolder mHolder = null;

    Context context;

    public JieQiDetailAdapter(Context context, List<DetailItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DetailItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dialog_mallgoods_jieqi_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DetailItemBean mJieQiItemBean = list.get(position);
        mHolder.item_tv_cn.setText(mJieQiItemBean.getSolar_terms_name());
        mHolder.item_tv_currentjieqi.setVisibility(View.GONE);
        GlideDownLoadImage.getInstance().loadCircleHeadImage(context, mJieQiItemBean.getPic(), mHolder.item_iv_img);
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
