package com.KiwiSports.control.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.KiwiSports.R;
import com.KiwiSports.control.activity.RecordCalendarActivity;
import com.KiwiSports.control.activity.RecordStatisticsActivity;
import com.KiwiSports.control.calendar.CalendarGridView;
import com.KiwiSports.control.calendar.CalendarTool;
import com.KiwiSports.control.calendar.DateEntity;
import com.KiwiSports.model.DistanceCountInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.db.RecordCalenderDBOpenHelper;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.PriceUtils;
import com.KiwiSports.utils.parser.RecordCalenderParser;
import com.KiwiSports.utils.volley.RequestUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

public class RecordListAdapter extends BaseAdapter {

    private ArrayList<RecordInfo> list;
    private Activity context;
    String totalDistances = "";
    DistanceCountInfo mDistanceCountInfo;
    String uid;
    RecordCalenderDBOpenHelper mRecordCalenderDB;

    public RecordListAdapter(Activity context, ArrayList<RecordInfo> list, String uid, RecordCalenderDBOpenHelper mRecordCalenderDB) {
        super();
        this.context = context;
        this.uid = uid;
        this.mRecordCalenderDB = mRecordCalenderDB;
        this.list = list;
    }


    public void setmDistanceCountInfo(DistanceCountInfo mDistanceCountInfo) {
        this.mDistanceCountInfo = mDistanceCountInfo;
    }

    public void setTotalDistances(String totalDistances) {
        this.totalDistances = totalDistances;
    }

    public ArrayList<RecordInfo> getList() {
        return list;
    }

    public void setList(ArrayList<RecordInfo> list) {
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
        RecordInfo mInfo = (RecordInfo) list.get(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.record_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.line = (LinearLayout) convertView
                    .findViewById(R.id.line);
            viewHolder.listitem_tv_name = (TextView) convertView
                    .findViewById(R.id.listitem_tv_name);
            viewHolder.listitem_tv_lopcount = (TextView) convertView
                    .findViewById(R.id.listitem_tv_lopcount);

            viewHolder.tv_distance = (TextView) convertView
                    .findViewById(R.id.tv_distance);
            viewHolder.tv_distanceunit = (TextView) convertView
                    .findViewById(R.id.tv_distanceunit);


            viewHolder.tv_runtime = (TextView) convertView
                    .findViewById(R.id.tv_runtime);
            viewHolder.tv_speed = (TextView) convertView
                    .findViewById(R.id.tv_speed);
            viewHolder.tv_speedunit = (TextView) convertView
                    .findViewById(R.id.tv_speedunit);
            viewHolder.item_iv_sporttype = (ImageView) convertView
                    .findViewById(R.id.item_iv_sporttype);
            viewHolder.listitem_tv_createtime = (TextView) convertView
                    .findViewById(R.id.listitem_tv_createtime);

            viewHolder.record_top = (LinearLayout) convertView
                    .findViewById(R.id.record_top);
            viewHolder.relat_allDistance = (RelativeLayout) convertView
                    .findViewById(R.id.relat_allDistance);
            viewHolder.tv_allDistance = (TextView) convertView
                    .findViewById(R.id.tv_allDistance);
            viewHolder.tv_allDistanceunit = (TextView) convertView
                    .findViewById(R.id.tv_allDistanceunit);
            viewHolder.calendarView = (CalendarGridView) convertView
                    .findViewById(R.id.calendarView);
            viewHolder.layout = (LinearLayout) convertView
                    .findViewById(R.id.layout);
            viewHolder.tv_year = (TextView) convertView
                    .findViewById(R.id.tv_year);
            viewHolder.tv_month = (TextView) convertView
                    .findViewById(R.id.tv_month);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.record_top.setVisibility(View.VISIBLE);
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_allDistance);
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_allDistanceunit);
            viewHolder.layout.setOnClickListener(mOnclick);
            viewHolder.relat_allDistance.setOnClickListener(mOnclick);
            if (!TextUtils.isEmpty(totalDistances)) {
                viewHolder.tv_allDistance.setText(totalDistances);
            }
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_year);
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_month);
            viewHolder.tv_year.setText(DatesUtils.getInstance().getNowTime("yyyy"));
            viewHolder.tv_month.setText(DatesUtils.getInstance().getNowTime("MM"));

            CalendarTool mCalendarTool = new CalendarTool(context);
            Point mNowCalendarPoint = mCalendarTool.getNowCalendar();
            List<DateEntity> mDateEntityList = mCalendarTool.getDateEntityList(mNowCalendarPoint.x,
                    mNowCalendarPoint.y);
            RecordCalendarItemAdapter mAdapter = new RecordCalendarItemAdapter(context, context.getResources());
            mAdapter.setDateList(mDateEntityList);
            mAdapter.setMsportmap(showdats());
            viewHolder.calendarView.setAdapter(mAdapter);

        } else {
            viewHolder.record_top.setVisibility(View.GONE);
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_distance);
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_distanceunit);
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_runtime);
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_speed);
            CommonUtils.getInstance().setTextViewTypeface(context,
                    viewHolder.tv_speedunit);
            CommonUtils.getInstance().setTextViewTypeface(context, viewHolder.listitem_tv_createtime);
            String name = mInfo.getPos_name();
            double distance = mInfo.getDistanceTraveled();
            String speed = mInfo.getAverageSpeed();
            String createtime = mInfo.getRunStartTime();//mInfo.getCreate_time();
            long runtime = mInfo.getDuration();
            String time = DatesUtils.getInstance().formatTimes(runtime * 1000);
            distance = distance / 1000;
            viewHolder.tv_distance.setText(PriceUtils.getInstance().formatFloatNumber(distance));
            viewHolder.tv_speed.setText(PriceUtils.getInstance()
                    .getPriceTwoDecimal(Double.valueOf(speed), 2));
            viewHolder.tv_runtime.setText(time);
            viewHolder.listitem_tv_createtime.setText(createtime);

            String sportindex = mInfo.getSportsType();

            if (sportindex.equals("0")) {// 走路
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_walk);
            } else if (sportindex.equals("1")) {// 跑步
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_run);
            } else if (sportindex.equals("2")) {// 骑行
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_riding);
            } else if (sportindex.equals("13")) {// 单板滑雪
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_sky);
            } else if (sportindex.equals("14")) {// 双板滑雪
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_skytwo);
            } else if (sportindex.equals("5")) {// 越野跑
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_runyueye);
            } else if (sportindex.equals("6")) {// 山地车越野
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_driveyueye);
            } else if (sportindex.equals("7")) {// 轮滑
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_lunhua);
            } else if (sportindex.equals("8")) {// 摩托车
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_autobike);
            } else if (sportindex.equals("11")) {// 长板
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_longboard);
            } else if (sportindex.equals("12")) {// 骑马
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_horseride);
            } else if (sportindex.equals("16")) {// 帆板
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_windsurfing);
            } else if (sportindex.equals("17")) {// 风筝冲浪
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_kitesurfing);
            } else if (sportindex.equals("3")) {// 开车
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_drive);
            } else {
                viewHolder.item_iv_sporttype
                        .setBackgroundResource(R.drawable.record_img_other);
            }
            viewHolder.listitem_tv_name.setText(name);
            viewHolder.listitem_tv_lopcount.setVisibility(View.GONE);
        }
        return convertView;
    }

    private HashMap<String, DateEntity> showdats() {
        HashMap<String, DateEntity> msportmap = null;
        ArrayList<String> mCalenderList = mRecordCalenderDB
                .getHistoryDBList(uid);
        if (mCalenderList != null && mCalenderList.size() > 0) {
            msportmap = new HashMap<>();
            for (int h = 0; h < mCalenderList.size(); h++) {
                String response = mCalenderList.get(h);
                Log.e("___db_____", "response-----" + response);
                RecordCalenderParser mParser = new RecordCalenderParser(msportmap);
                JSONObject jsonObject = RequestUtils.String2JSON(response);
                msportmap = mParser.parseJSON(jsonObject);
            }
        }
        return msportmap;
    }

    View.OnClickListener mOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.relat_allDistance:
                    if (mDistanceCountInfo != null && !TextUtils.isEmpty(totalDistances)) {
                        Intent intent = new Intent(context,
                                RecordStatisticsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("mDistanceCountInfo", mDistanceCountInfo);
                        context.startActivity(intent);
                        CommonUtils.getInstance().setPageIntentAnim(intent,
                                context);
                    }
                    break;
                case R.id.layout:
                    Intent intent = new Intent(context,
                            RecordCalendarActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    context.startActivity(intent);
                    CommonUtils.getInstance().setPageIntentAnim(intent,
                            context);
                    break;
            }


        }
    };

    public void show(View view) {
    }

    class ViewHolder {
        public LinearLayout line;
        public TextView listitem_tv_name;
        public TextView listitem_tv_lopcount;

        public TextView tv_distance;
        public TextView tv_distanceunit;
        public TextView tv_runtime;
        public TextView tv_speed;
        public TextView tv_speedunit;
        public TextView listitem_tv_createtime;
        public ImageView item_iv_sporttype;
        public LinearLayout record_top;

        public RelativeLayout relat_allDistance;
        public TextView tv_allDistance;
        public TextView tv_allDistanceunit;
        public CalendarGridView calendarView;
        public LinearLayout layout;
        public TextView tv_year;
        public TextView tv_month;
    }
}
