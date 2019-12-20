package com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.mine.myorder.tanksgiving.bean.ThanksItemBean;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class ThanksListAdapter extends BaseAdapterHelper<ThanksItemBean> {
    ViewHolder mHolder = null;
    Context context;
    List<ThanksItemBean> list;
    int type;

    public ThanksListAdapter(Context context, List<ThanksItemBean> list, int type) {
        super(context, list);
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ThanksItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_thanks_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ThanksItemBean mOrderItemBean = list.get(position);

        mHolder.item_tv_num.setText(mOrderItemBean.getId());
        mHolder.item_tv_name.setText(mOrderItemBean.getUser_name());
        mHolder.item_tv_shequ.setText(mOrderItemBean.getGroup_name());
        mHolder.item_tv_engerynum.setText(mOrderItemBean.getPrice());
        GlideDownLoadImage.getInstance().loadCircleHeadImage(context, mOrderItemBean.getAvatar(), mHolder.item_iv_thumb);
        if (type == 2 || type == 4) {
            mHolder.item_iv_engerynum.setVisibility(View.VISIBLE);
            mHolder.item_iv_engerynum.setBackgroundResource(R.mipmap.theorder_gratitude_icon2);
        } else {
            mHolder.item_iv_engerynum.setVisibility(View.GONE);
            mHolder.item_iv_engerynum.setBackgroundResource(R.mipmap.theorder_gratitude_icon1);
        }
        return convertView;
    }

    private class ViewHolder {
        private TextView item_tv_num;
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_shequ;
        private TextView item_tv_engerynum;
        private ImageView item_iv_engerynum;

        public ViewHolder(View view) {
            item_tv_num = (TextView) view.findViewById(R.id.item_tv_num);
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_shequ = (TextView) view.findViewById(R.id.item_tv_shequ);
            item_tv_engerynum = (TextView) view.findViewById(R.id.item_tv_engerynum);
            item_iv_engerynum = (ImageView) view.findViewById(R.id.item_iv_engerynum);
        }
    }
}
