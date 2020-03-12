package com.longcheng.lifecareplan.modular.helpwith.medalrank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.medalrank.bean.ItemBean;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.List;

/**
 * 作者：MarkShuai
 * 时间：2017/11/22 14:33
 * 邮箱：MarkShuai@163.com
 * 意图：
 */

public class MyAdapter extends BaseAdapterHelper<ItemBean> {
    ViewHolder mHolder = null;

    Context context;

    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public MyAdapter(Context context, List<ItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_medalrank_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        ItemBean mActionItemBean = list.get(position);
        mHolder.item_tv_sortnum.setText(mActionItemBean.getSort_rank());
        GlideDownLoadImage.getInstance().loadCircleImage(mActionItemBean.getAvatar(), mHolder.item_iv_thumb);
        if (type == 1) {
            mHolder.item_tv_name.setVisibility(View.VISIBLE);
            mHolder.item_tv_shequ.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        } else {
            mHolder.item_tv_name.setVisibility(View.GONE);
            mHolder.item_tv_shequ.setTextColor(context.getResources().getColor(R.color.text_biaoTi_color));
        }
        mHolder.item_tv_name.setText(mActionItemBean.getUser_name());
        mHolder.item_tv_shequ.setText(mActionItemBean.getGroup_name());
        mHolder.item_tv_renci.setText("互祝人次：" + mActionItemBean.getCount());
        mHolder.item_tv_engerynum.setText("互祝能量：" + mActionItemBean.getAbility());
        mHolder.iv_top.setVisibility(View.VISIBLE);
        if (position == 0) {
            mHolder.item_layout_thumb.setBackgroundResource(R.drawable.corners_bg_rank1);
            mHolder.iv_top.setBackgroundResource(R.mipmap.wisheachdetails_champion);
        } else if (position == 1) {
            mHolder.item_layout_thumb.setBackgroundResource(R.drawable.corners_bg_rank2);
            mHolder.iv_top.setBackgroundResource(R.mipmap.wisheachdetails_firstrunner);
        } else if (position == 2) {
            mHolder.item_layout_thumb.setBackgroundResource(R.drawable.corners_bg_rank3);
            mHolder.iv_top.setBackgroundResource(R.mipmap.wisheachdetails_thebronze);
        } else {
            mHolder.item_layout_thumb.setBackgroundResource(R.drawable.corners_bg_write);
            mHolder.iv_top.setVisibility(View.INVISIBLE);
        }


        return convertView;
    }


    private class ViewHolder {
        private TextView item_tv_sortnum;
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_shequ;
        private TextView item_tv_renci;
        private TextView item_tv_engerynum;
        private ImageView iv_top;
        private LinearLayout item_layout_thumb;

        public ViewHolder(View view) {
            item_layout_thumb = (LinearLayout) view.findViewById(R.id.item_layout_thumb);
            item_tv_sortnum = (TextView) view.findViewById(R.id.item_tv_sortNum);
            iv_top = (ImageView) view.findViewById(R.id.iv_top);
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_shequ = (TextView) view.findViewById(R.id.item_tv_shequ);
            item_tv_renci = (TextView) view.findViewById(R.id.item_tv_renci);
            item_tv_engerynum = (TextView) view.findViewById(R.id.item_tv_engerynum);
        }
    }
}
