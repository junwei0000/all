package com.KiwiSports.control.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.model.DistanceCountInfo;
import com.KiwiSports.model.MacthSpeedInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.PriceUtils;

import java.util.ArrayList;

public class RecordStaticsAdapter extends BaseAdapter {

    private ArrayList<DistanceCountInfo> list;
    private Activity context;

    public RecordStaticsAdapter(Activity context, ArrayList<DistanceCountInfo> list) {
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
        DistanceCountInfo info = (DistanceCountInfo) list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.record_statistics_item, null);
            viewHolder = new ViewHolder();
            viewHolder.layout_sportimg = (LinearLayout) convertView
                    .findViewById(R.id.layout_sportimg);
            viewHolder.iv_sportimg = (ImageView) convertView
                    .findViewById(R.id.iv_sportimg);
            viewHolder.tv_sportname = (TextView) convertView
                    .findViewById(R.id.tv_sportname);
            viewHolder.tv_distance = (TextView) convertView
                    .findViewById(R.id.tv_distance);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(position==0){
            viewHolder.layout_sportimg.setBackgroundResource(R.drawable.corners_statistics1);
        }else if(position==1){
            viewHolder.layout_sportimg.setBackgroundResource(R.drawable.corners_statistics2);
        }else if(position==2){
            viewHolder.layout_sportimg.setBackgroundResource(R.drawable.corners_statistics3);
        }else {
            viewHolder.layout_sportimg.setBackgroundResource(R.drawable.corners_statistics4);
        }
        int sportindex=info.getSportsType();
        if (sportindex == 0) {// 走路
            viewHolder.iv_sportimg.setBackgroundResource(R.drawable.mainstart_walk_img);
        } else if (sportindex == 1) {// 跑步
            viewHolder.iv_sportimg.setBackgroundResource(R.drawable.mainstart_run_img);
        } else if (sportindex == 2) {// 骑行
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_bike_img);
        } else if (sportindex == 3) {// 开车
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_drive_img);
        } else if (sportindex == 5) {// 越野跑
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_cross_country_run_img);
        } else if (sportindex == 6) {// 山地车越野
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_mountainbike_img);
        } else if (sportindex == 7) {// 轮滑
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_skate_img);
        } else if (sportindex == 8) {// 摩托车
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_motorbike_img);
        } else if (sportindex == 11) {// 长板
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_longboard_img);
        } else if (sportindex == 12) {// 骑马
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_horseride_img);
        } else if (sportindex == 13) {// 单板滑雪
            viewHolder.iv_sportimg.setBackgroundResource(R.drawable.mainstart_snowboard_img);

        } else if (sportindex == 14) {// 双板滑雪
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_ski_img);
        } else if (sportindex == 16) {// 帆板
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_windsurfing_img);
        } else if (sportindex == 17) {// 风筝冲浪
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_kitesurfing_img);
        } else {
            viewHolder.iv_sportimg
                    .setBackgroundResource(R.drawable.mainstart_other_img);
        }
        viewHolder.tv_sportname.setText(info.getSportname());
        String mDistances = PriceUtils.getInstance().gteDividePrice(info.getDistance(), "1000");
        mDistances = PriceUtils.getInstance().getPriceTwoDecimal(Double.valueOf(mDistances),2);
        mDistances = PriceUtils.getInstance().seePrice(mDistances);
        viewHolder.tv_distance.setText(mDistances+"km");
        CommonUtils.getInstance().setTextViewTypeface(context, viewHolder.tv_distance);
        return convertView;
    }

    class ViewHolder {
        public LinearLayout layout_sportimg;
        public ImageView iv_sportimg;
        public TextView tv_sportname;
        public TextView tv_distance;

    }
}
