package com.KiwiSports.control.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.control.calendar.DateEntity;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.CornerView;

import java.util.HashMap;
import java.util.List;


/**
 * @author daij
 * @version 1.0 日历适配器
 */
public class RecordCalendarItemAdapter extends BaseAdapter {
    HashMap<String, DateEntity> msportmap;
    private Resources mRes;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 日期实体集合
     */
    private List<DateEntity> mDataList;
    /**
     * 因为position是从0开始的，所以用当做一个中间者，用来加1.以达到判断除数时，为哪个星期
     */
    private int temp;

    public RecordCalendarItemAdapter(Context context, Resources res) {
        this.mContext = context;
        this.mRes = res;
    }

    public void setMsportmap(HashMap<String, DateEntity> msportmap) {
        this.msportmap = msportmap;
    }

    /**
     * 设置日期数据
     */
    public void setDateList(List<DateEntity> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 通过传递过来的MenuItem值给每一个item设置数据
        View layout = LayoutInflater.from(mContext)
                .inflate(R.layout.record_topcalendar_item, null);
        CornerView calendar_relat = (CornerView) layout
                .findViewById(R.id.calendar_relat);
        TextView textView = (TextView) layout
                .findViewById(R.id.calendar_item_tv_day);
        if (mDataList == null) {
            return layout;
        }
        CommonUtils.getInstance().setTextViewTypeface(mContext,
                textView);
        textView.setText(mDataList.get(position).day + "");
        if (mDataList.get(position).isNowDate) {
            textView.setTextColor(mRes.getColor(R.color.red));
        }
        int month = mDataList.get(position).month;
        String showMonth;
        if (month < 10) {
            showMonth = "-0" + month;
        } else {
            showMonth = "-" + month;
        }
        int day = mDataList.get(position).day;
        String showDay;
        if (day < 10) {
            showDay = "-0" + day;
        } else {
            showDay = "-" + day;
        }
        String date = "" + mDataList.get(position).year + showMonth + showDay;
        if (msportmap != null && msportmap.containsKey(date)&&mDataList.get(position).isSelfMonthDate) {
            Log.e("LOGTESTdate",date);
            String sportindex = msportmap.get(date).sportIndex;
            long runingTimestamp = msportmap.get(date).runtime;
            if (runingTimestamp < 0) {
                runingTimestamp = 0;
            }
            int runtimeminute = (int) (runingTimestamp / 60);
            calendar_relat.setBackgroundColor(setColorBg(runtimeminute));
            textView.setTextColor(mRes.getColor(R.color.white));
        }
        if (!mDataList.get(position).isSelfMonthDate) {// 是否为本月的号数，不是本月号数显示白色，及不显示
            textView.setTextColor(mRes.getColor(R.color.records_toprili_color));
        }

        return layout;
    }


    /**
     * 分别是alpha、红(red)、绿(green)、蓝(blue)四个颜色值(ARGB)。
     * 每个数字取值0-255，因此一个颜色可以用一个整数来表示。为了运行效率，
     * Android编码时用整数Color类实例来表示颜色。任何一种颜色的值范围都是 0到 255（00到 ff）。
     */
    private int setColorBg(int runtime) {
        runtime = runtime > (6 * 60) ? (6 * 60) : runtime;
        int red = 255 - runtime * 150/ 360 ;//红(
        if (red < 0) {
            red = 100;
        }
        int alpha = 255;//透明度
        int green = 0;//绿
        int blue = 0;//蓝
        int colorbg = Color.rgb(red, 0, 0);
        return colorbg;
    }
}
