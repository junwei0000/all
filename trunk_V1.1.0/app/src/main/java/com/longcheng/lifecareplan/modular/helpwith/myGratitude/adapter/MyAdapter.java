package com.longcheng.lifecareplan.modular.helpwith.myGratitude.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.helpwith.energy.activity.HelpWithEnergyActivity;
import com.longcheng.lifecareplan.modular.helpwith.myGratitude.bean.ItemBean;
import com.longcheng.lifecareplan.utils.ConfigUtils;
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

    Activity context;


    public MyAdapter(Activity context, List<ItemBean> list) {
        super(context, list);
        this.context = context;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<ItemBean> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.helpwith_mydedication_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.item_tv_mingxi.setVisibility(View.VISIBLE);
        ItemBean mActionItemBean = list.get(position);
        mHolder.item_tv_name.setText(mActionItemBean.getUser_name());
        mHolder.item_tv_shequ.setText(mActionItemBean.getGroup_name());
        mHolder.item_tv_engerynum.setText(mActionItemBean.getHelp_ability());
        GlideDownLoadImage.getInstance().loadCircleHeadImage(context, mActionItemBean.getAvatar(), mHolder.item_iv_thumb);

        mHolder.layout_name.setTag(mActionItemBean);
        mHolder.item_layout_thumb.setTag(mActionItemBean);
        mHolder.layout_name.setOnClickListener(clickListener);
        mHolder.item_layout_thumb.setOnClickListener(clickListener);
        return convertView;
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ItemBean mActionItemBean = (ItemBean) v.getTag();
            Intent intent = new Intent(context, HelpWithEnergyActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("name", mActionItemBean.getUser_name());
            intent.putExtra("skiptype", "MyGratitudeActivity");
            context.startActivity(intent);
            ConfigUtils.getINSTANCE().setPageIntentAnim(intent, context);
        }
    };

    private class ViewHolder {
        private ImageView item_iv_thumb;
        private TextView item_tv_name;
        private TextView item_tv_shequ;
        private TextView item_tv_engerynum;
        private TextView item_tv_mingxi;
        private LinearLayout item_layout_thumb;
        private LinearLayout layout_name;

        public ViewHolder(View view) {
            item_layout_thumb = (LinearLayout) view.findViewById(R.id.item_layout_thumb);
            layout_name = (LinearLayout) view.findViewById(R.id.layout_name);
            item_iv_thumb = (ImageView) view.findViewById(R.id.item_iv_thumb);
            item_tv_name = (TextView) view.findViewById(R.id.item_tv_name);
            item_tv_shequ = (TextView) view.findViewById(R.id.item_tv_shequ);
            item_tv_engerynum = (TextView) view.findViewById(R.id.item_tv_engerynum);
            item_tv_mingxi = (TextView) view.findViewById(R.id.item_tv_mingxi);
        }
    }
}
