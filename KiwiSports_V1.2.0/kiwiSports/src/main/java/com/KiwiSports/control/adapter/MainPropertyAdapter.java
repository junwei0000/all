package com.KiwiSports.control.adapter;

import java.util.ArrayList;

import com.KiwiSports.R;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.LanguageUtil;
import com.KiwiSports.utils.PriceUtils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainPropertyAdapter extends BaseAdapter {

    private ArrayList<MainSportInfo> list;
    private Activity context;
    int sportindex;

    public MainPropertyAdapter(Activity context, ArrayList<MainSportInfo> list, int sportindex) {
        super();
        this.context = context;
        this.list = list;
        this.sportindex = sportindex;
    }

    public ArrayList<MainSportInfo> getList() {
        return list;
    }

    public void setList(ArrayList<MainSportInfo> list) {
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
        MainSportInfo stadiumObj = (MainSportInfo) list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.main_sportproperty_item, null);
            viewHolder = new ViewHolder();
            viewHolder.item_iv_img = (ImageView) convertView
                    .findViewById(R.id.item_iv_img);
            viewHolder.listitem_tv_cname = (TextView) convertView
                    .findViewById(R.id.listitem_tv_cname);
            viewHolder.listitem_tv_value = (TextView) convertView
                    .findViewById(R.id.listitem_tv_value);
            viewHolder.listitem_tv_unit = (TextView) convertView
                    .findViewById(R.id.listitem_tv_unit);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommonUtils.getInstance().setTextViewTypeface(context, viewHolder.listitem_tv_value);
        CommonUtils.getInstance().setTextViewTypeface(context, viewHolder.listitem_tv_unit);
        String name = stadiumObj.getCname();
        if (!LanguageUtil.idChLanguage(context)) {
            name = stadiumObj.getEname();
        }
        String value = stadiumObj.getValue();
        String unit = stadiumObj.getUnit();
        // 默认价格
        viewHolder.listitem_tv_cname.setText(name);
        viewHolder.listitem_tv_value.setText(value);
        viewHolder.listitem_tv_unit.setText(unit);


        int drawable = showCurrentPropertyValue(position);
        viewHolder.item_iv_img.setBackgroundResource(drawable);
        return convertView;
    }

    private int showCurrentPropertyValue(int position) {
        int drawable = R.drawable.property_avspeed;
        if (sportindex == 0) {// 走路
            switch (position) {
                case 0:
                    drawable = R.drawable.property_speed;
                    break;
                case 1:
                    drawable = R.drawable.property_time;
                    break;
                case 2:
                    drawable = R.drawable.property_steps;
                    break;
                case 3:
                    drawable = R.drawable.property_haiba;
                    break;
                default:
                    break;
            }
        } else if (sportindex == 1 || sportindex == 5) {// 跑步
            switch (position) {
                case 0:
                    drawable = R.drawable.property_speed;
                    break;
                case 1:
                    drawable = R.drawable.property_avspeed;
                    break;
                case 2:
                    drawable = R.drawable.property_steps;
                    break;
                case 3:
                    drawable = R.drawable.property_time;
                    break;
                case 4:
                    drawable = R.drawable.property_avspeed;
                    break;
                default:
                    break;
            }
        } else if (sportindex == 2) {// 骑行
            switch (position) {
                case 0:
                    drawable = R.drawable.property_speed;
                    break;
                case 1:
                    drawable = R.drawable.property_avspeed;
                    break;
                case 2:
                    drawable = R.drawable.property_time;
                    break;
                case 3:
                    drawable = R.drawable.property_haiba;
                    break;
                default:
                    break;
            }
        } else if (sportindex == 13 || sportindex == 14) {// 滑雪
            switch (position) {
                case 0:
                    drawable = R.drawable.property_speed;
                    break;
                case 1:
                    drawable = R.drawable.property_dis;
                    break;
                case 2:
                    drawable = R.drawable.property_lopcount;
                    break;
                case 3:
                    drawable = R.drawable.property_dis;
                    break;
                case 4:
                    drawable = R.drawable.property_angle;
                    break;
                case 5:
                    drawable = R.drawable.property_haiba;
                    break;
                default:
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    drawable = R.drawable.property_speed;
                    break;
                case 1:
                    drawable = R.drawable.property_time;
                    break;
                case 2:
                    drawable = R.drawable.property_speed;
                    break;
                case 3:
                    drawable = R.drawable.property_speed;
                    break;
                case 4:
                    drawable = R.drawable.property_haiba;
                    break;
                default:
                    break;
            }
        }
        return drawable;
    }

    class ViewHolder {
        public ImageView item_iv_img;
        public TextView listitem_tv_cname;
        public TextView listitem_tv_value;
        public TextView listitem_tv_unit;
    }
}
