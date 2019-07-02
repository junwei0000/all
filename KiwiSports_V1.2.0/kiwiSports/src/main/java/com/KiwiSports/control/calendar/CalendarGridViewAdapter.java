package com.KiwiSports.control.calendar;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
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
import com.KiwiSports.control.activity.RecordDetailActivity;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.utils.CornerView;


/**
 * @author daij
 * @version 1.0 日历适配器
 */
public class CalendarGridViewAdapter extends BaseAdapter {
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
    RecordListDBOpenHelper mRecordListDBOpenHelper;

    public CalendarGridViewAdapter(Context context, Resources res, RecordListDBOpenHelper mRecordListDBOpenHelper) {
        this.mContext = context;
        this.mRes = res;
        this.mRecordListDBOpenHelper = mRecordListDBOpenHelper;
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
                .inflate(R.layout.calendar_item_layout, null);
        CornerView calendar_relat = (CornerView) layout
                .findViewById(R.id.calendar_relat);
        TextView textView = (TextView) layout
                .findViewById(R.id.calendar_item_tv_day);
        ImageView tv_tip = (ImageView) layout.findViewById(R.id.calendar_tip);
        if (mDataList == null) {
            return layout;
        }
        textView.setText(mDataList.get(position).day + "");
        if (mDataList.get(position).isNowDate) {// 是否为本月的号数，不是本月号数显示白色，及不显示
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
        if (msportmap != null && msportmap.containsKey(date) && mDataList.get(position).isSelfMonthDate) {
            Log.e("LOGTESTdate", date);
            String sportindex = msportmap.get(date).sportIndex;
            long runingTimestamp = msportmap.get(date).runtime;
            if (runingTimestamp < 0) {
                runingTimestamp = 0;
            }
            int runtimeminute = (int) (runingTimestamp / 60);
            showSportPic(sportindex, tv_tip);
            calendar_relat.setBackgroundColor(setColorBg(runtimeminute));
            textView.setTextColor(mRes.getColor(R.color.white));
            textView.setTag(msportmap.get(date));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateEntity mDateEntity = (DateEntity) v.getTag();
                    String posid = mDateEntity.posid;
                    String record_id = mDateEntity.record_id;
                    String sporttype = mDateEntity.sportIndex;
                    Intent intent;
                    intent = new Intent(mContext,
                            RecordDetailActivity.class);
                    RecordInfo mRecordInfo = mRecordListDBOpenHelper.getRecordInfoByDB(record_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("sporttype", sporttype);
                    intent.putExtra("posid", posid);
                    intent.putExtra("record_id", record_id);
                    if (mRecordInfo != null) {
                        intent.putExtra("runStartTime", mRecordInfo.getRunStartTime());
                        intent.putExtra("distanceTraveled", mRecordInfo.getDistanceTraveled());
                    } else {
                        intent.putExtra("runStartTime", "");
                        intent.putExtra("distanceTraveled", 0);
                    }
                    mContext.startActivity(intent);
                }
            });
        }
        if (!mDataList.get(position).isSelfMonthDate) {// 是否为本月的号数，不是本月号数显示白色，及不显示
            textView.setTextColor(mRes.getColor(R.color.white));
        }

        return layout;
    }

    private void showSportPic(String sportindex, ImageView iv_sportimg) {
        if (sportindex.equals("0")) {// 走路
            iv_sportimg.setBackgroundResource(R.drawable.calender_walk_img);
        } else if (sportindex.equals("1")) {// 跑步
            iv_sportimg.setBackgroundResource(R.drawable.calender_run_img);
        } else if (sportindex.equals("2")) {// 骑行
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_riding_img);
        } else if (sportindex.equals("13")) {// 单板滑雪
            iv_sportimg.setBackgroundResource(R.drawable.calender_sky_img);
        } else if (sportindex.equals("14")) {// 双板滑雪
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_skytwo_img);
        } else if (sportindex.equals("5")) {// 越野跑
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_runyueye_img);
        } else if (sportindex.equals("6")) {// 山地车越野
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_driveyueye_img);
        } else if (sportindex.equals("7")) {// 轮滑
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_lunhua_img);
        } else if (sportindex.equals("8")) {// 摩托车
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_autobike_img);
        } else if (sportindex.equals("11")) {// 长板
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_longboard_img);
        } else if (sportindex.equals("12")) {// 骑马
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_horseride_img);
        } else if (sportindex.equals("16")) {// 帆板
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_windsurfing_img);
        } else if (sportindex.equals("17")) {// 风筝冲浪
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_kitesurfing_img);
        } else if (sportindex.equals("3")) {// 开车
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_drive_img);
        } else {
            iv_sportimg
                    .setBackgroundResource(R.drawable.calender_other_img);
        }
    }

    /**
     * 分别是alpha、红(red)、绿(green)、蓝(blue)四个颜色值(ARGB)。
     * 每个数字取值0-255，因此一个颜色可以用一个整数来表示。为了运行效率，
     * Android编码时用整数Color类实例来表示颜色。任何一种颜色的值范围都是 0到 255（00到 ff）。
     */
    private int setColorBg(int runtime) {
        runtime = runtime > (6 * 60) ? (6 * 60) : runtime;
        int red = 255 - runtime * 150 / 360;//红(
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
