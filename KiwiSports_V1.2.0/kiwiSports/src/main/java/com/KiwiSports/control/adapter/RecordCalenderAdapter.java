package com.KiwiSports.control.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.control.activity.RecordCalendarActivity;
import com.KiwiSports.control.activity.RecordStatisticsActivity;
import com.KiwiSports.control.calendar.CalendarGridView;
import com.KiwiSports.control.calendar.CalendarGridViewAdapter;
import com.KiwiSports.control.calendar.CalendarTool;
import com.KiwiSports.control.calendar.DateEntity;
import com.KiwiSports.model.DistanceCountInfo;
import com.KiwiSports.model.RecordCalenderInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.PriceUtils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class RecordCalenderAdapter extends BaseAdapter {

    private Resources mRes;
    /**
     * 上下文
     */
    private Context mContext;
    ArrayList<RecordCalenderInfo> list;
    HashMap<String, DateEntity> msportmap;
    RecordListDBOpenHelper mRecordListDBOpenHelper;
    public RecordCalenderAdapter(Context context, Resources res,RecordListDBOpenHelper mRecordListDBOpenHelper) {
        super();
        this.mContext = context;
        this.mRes = res;
        this.mRecordListDBOpenHelper = mRecordListDBOpenHelper;
    }

    public void setMsportmap(HashMap<String, DateEntity> msportmap) {
        this.msportmap = msportmap;
    }

    public ArrayList<RecordCalenderInfo> getList() {
        return list;
    }

    public void setList(ArrayList<RecordCalenderInfo> list) {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.calendar, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_year = (TextView) convertView
                    .findViewById(R.id.tv_year);
            viewHolder.calendar_gridview = (CalendarGridView) convertView
                    .findViewById(R.id.calendar_gridview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommonUtils.getInstance().setTextViewTypeface(mContext,
                viewHolder.tv_year);
        RecordCalenderInfo mRecordCalenderInfo=list.get(position);
        CalendarGridViewAdapter mAdapter = new CalendarGridViewAdapter(mContext, mRes,mRecordListDBOpenHelper);
        mAdapter.setDateList(mRecordCalenderInfo.getmDateEntityList());
        mAdapter.setMsportmap(msportmap);
        viewHolder.calendar_gridview.setAdapter(mAdapter);
        viewHolder.tv_year.setText(mRecordCalenderInfo.getYear() + "年" + mRecordCalenderInfo.getMonth()
                + "月");
        return convertView;
    }


    class ViewHolder {
        public TextView tv_year;
        public CalendarGridView calendar_gridview;
    }
}
