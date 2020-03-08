package com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailAfterBean;
import com.longcheng.lifecareplan.modular.helpwith.lifestyledetail.bean.LifeStyleDetailItemBean;
import com.longcheng.lifecareplan.utils.DensityUtil;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class DetailJieQiItemAdapter extends BaseAdapter {

    Context context;
    List<LifeStyleDetailAfterBean> list;

    public DetailJieQiItemAdapter(Context context, List<LifeStyleDetailAfterBean> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.liffedetail_jieqi_name_item, parent, false);
            mHolder = new ViewHolder();
            mHolder.item_iv_name = (ImageView) convertView.findViewById(R.id.item_iv_name);
            mHolder.item_tv_name = (TextView) convertView.findViewById(R.id.item_tv_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        LifeStyleDetailAfterBean mEnergyItemBean = list.get(position);
        mHolder.item_tv_name.setText(mEnergyItemBean.getSponsor_user_name());
        GlideDownLoadImage.getInstance().loadCircleImage(mEnergyItemBean.getAvatar(), mHolder.item_iv_name);
        return convertView;
    }


    private class ViewHolder {
        private ImageView item_iv_name;
        private TextView item_tv_name;

    }
}
