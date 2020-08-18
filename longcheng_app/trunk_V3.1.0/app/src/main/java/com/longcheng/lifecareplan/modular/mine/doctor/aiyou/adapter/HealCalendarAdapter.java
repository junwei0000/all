package com.longcheng.lifecareplan.modular.mine.doctor.aiyou.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.longcheng.lifecareplan.R;
import com.longcheng.lifecareplan.base.BaseAdapterHelper;
import com.longcheng.lifecareplan.modular.bottommenu.ColorChangeByTime;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.activity.HealTrackActivity;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackAfterBean;
import com.longcheng.lifecareplan.modular.mine.doctor.aiyou.bean.HealTeackItemBean;
import com.longcheng.lifecareplan.modular.mine.doctor.doctor.ziliao.bean.AYApplyListAfterBean;
import com.longcheng.lifecareplan.utils.CommonUtil;
import com.longcheng.lifecareplan.utils.date.DateEntity;
import com.longcheng.lifecareplan.utils.date.DatesUtils;
import com.longcheng.lifecareplan.utils.glide.GlideDownLoadImage;

import java.util.ArrayList;
import java.util.List;


public class HealCalendarAdapter extends BaseAdapterHelper<DateEntity> {
    ViewHolder mHolder = null;
    Activity context;
    ArrayList<HealTeackItemBean> validFollowItems;

    public HealCalendarAdapter(Activity context, List<DateEntity> list) {
        super(context, list);
        this.context = context;
    }

    public ArrayList<HealTeackItemBean> getValidFollowItems() {
        return validFollowItems;
    }

    public void setValidFollowItems(ArrayList<HealTeackItemBean> validFollowItems) {
        this.validFollowItems = validFollowItems;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent, List<DateEntity> list, LayoutInflater inflater) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.aiyou_calender_item, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        DateEntity mOrderItemBean = list.get(position);
        mHolder.item_yangli.setText(mOrderItemBean.day);
        mHolder.item_yinli.setText(mOrderItemBean.luna);
        boolean istoday = mOrderItemBean.isToday;
        if (istoday) {
            mHolder.item_layout.setBackgroundResource(R.drawable.corners_oval_red);
            mHolder.item_yangli.setTextColor(context.getResources().getColor(R.color.white));
            mHolder.item_yinli.setTextColor(context.getResources().getColor(R.color.white_tran));
        } else {
            mHolder.item_layout.setBackgroundColor(Color.TRANSPARENT);
            mHolder.item_yangli.setTextColor(context.getResources().getColor(R.color.text_biaoTi_color));
            mHolder.item_yinli.setTextColor(context.getResources().getColor(R.color.text_noclick_color));
        }

        mHolder.item_iv_img.setVisibility(View.INVISIBLE);
        if (validFollowItems != null && validFollowItems.size() > 0) {
            for (int i = 0; i < validFollowItems.size(); i++) {
                if (mOrderItemBean.date.equals(validFollowItems.get(i).getDay())) {
                    mHolder.item_iv_img.setVisibility(View.VISIBLE);
                    break;
                }
            }
        }
        return convertView;
    }

    private class ViewHolder {
        private LinearLayout item_layout;
        private TextView item_yangli;
        private TextView item_yinli;
        private ImageView item_iv_img;

        public ViewHolder(View view) {
            item_iv_img = view.findViewById(R.id.item_iv_img);
            item_layout = view.findViewById(R.id.item_layout);
            item_yangli = view.findViewById(R.id.item_yangli);
            item_yinli = view.findViewById(R.id.item_yinli);
        }
    }
}
