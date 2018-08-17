package com.KiwiSports.control.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.model.MacthSpeedInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.PriceUtils;

import java.util.ArrayList;

public class RecordDetailColumnarAdapter extends BaseAdapter {

    private ArrayList<MacthSpeedInfo> list;
    private Activity context;
    Long maxmatchSpeedTimestamp;
    double dis;
    public RecordDetailColumnarAdapter(Activity context, ArrayList<MacthSpeedInfo> list,
                                       Long maxmatchSpeedTimestamp ,double dis) {
        super();
        this.context = context;
        this.list = list;
        this.dis = dis;
        this.maxmatchSpeedTimestamp = maxmatchSpeedTimestamp;
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
        MacthSpeedInfo info = (MacthSpeedInfo) list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.record_columnarlist_item, null);
            viewHolder = new ViewHolder();
            viewHolder.listitem_tv_bili = (TextView) convertView
                    .findViewById(R.id.listitem_tv_bili);
            viewHolder.listitem_tv_distance = (TextView) convertView
                    .findViewById(R.id.listitem_tv_distance);
            viewHolder.listitem_tv_matchspeed = (TextView) convertView
                    .findViewById(R.id.listitem_tv_matchspeed);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        long matchSpeedTimestamp= info.getMatchSpeedTimestamp();
        int showdis=info.getDistance();
        viewHolder.listitem_tv_distance.setText(showdis + "km");
        if(position+1 ==list.size()){
           if(dis<showdis){
               viewHolder.listitem_tv_distance.setText("<"+showdis+ "km");
           }
        }

        String matchSpeed = DatesUtils.getInstance().formatMatchspeed(
                matchSpeedTimestamp);
        viewHolder.listitem_tv_matchspeed.setText(matchSpeed);
        int width = ConfigUtils.getInstance().getPhoneWidHeigth(context).widthPixels;
        width=width*3/5;
        int ww=width;
        if(maxmatchSpeedTimestamp>0){
              ww=(int) (width * matchSpeedTimestamp/maxmatchSpeedTimestamp);
        }else{
            ww=5;
        }
        Log.e("listitem_tv_bili",maxmatchSpeedTimestamp+"   "+matchSpeedTimestamp+"  "+width+"   "+ww);
        viewHolder.listitem_tv_bili.setWidth(ww);
        return convertView;
    }

    class ViewHolder {
        public TextView listitem_tv_matchspeed;
        public TextView listitem_tv_distance;
        public TextView listitem_tv_bili;

    }
}
