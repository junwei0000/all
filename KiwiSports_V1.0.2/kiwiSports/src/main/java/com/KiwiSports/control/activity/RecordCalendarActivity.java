package com.KiwiSports.control.activity;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.control.adapter.RecordCalenderAdapter;
import com.KiwiSports.control.calendar.CalendarTool;
import com.KiwiSports.control.calendar.DateEntity;
import com.KiwiSports.model.RecordCalenderInfo;
import com.KiwiSports.model.db.RecordCalenderDBOpenHelper;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：统计
 */
public class RecordCalendarActivity extends BaseActivity {


    private LinearLayout pagetop_layout_back;
    private TextView pagetop_tv_name;
    private MyListView gv_date;
    private String uid;
    private String token;
    private String access_token;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_back:
                doBack();
                break;
            default:
                break;
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.record_calendar);
        CommonUtils.getInstance().addActivity(this);
    }

    @Override
    protected void findViewById() {
        pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
        pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
        gv_date = (MyListView) findViewById(R.id.gv_date);
    }

    @Override
    protected void setListener() {
        pagetop_layout_back.setOnClickListener(this);
        pagetop_tv_name.setText("运动日历");
        SharedPreferences bestDoInfoSharedPrefs = CommonUtils.getInstance()
                .getBestDoInfoSharedPrefs(this);
        uid = bestDoInfoSharedPrefs.getString("uid", "");
        token = bestDoInfoSharedPrefs.getString("token", "");
        access_token = bestDoInfoSharedPrefs.getString("access_token", "");
        if (mDB == null) {
            mDB = new RecordCalenderDBOpenHelper(this);
        }
        mRecordListDBOpenHelper=new RecordListDBOpenHelper(this);
    }
    RecordListDBOpenHelper mRecordListDBOpenHelper;
    RecordCalenderDBOpenHelper mDB;
    List<DateEntity> mDateEntityList;
    private Point mNowCalendarPoint;
    private CalendarTool mCalendarTool;
    ArrayList<RecordCalenderInfo> list = new ArrayList<>();
    RecordCalenderAdapter mRecordCalenderAdapter;

    int showMonthnum = Constants.getInstance().showMonthnum;

    @Override
    protected void processLogic() {
        mCalendarTool = new CalendarTool(context);
        for (int i = 0; i < showMonthnum; i++) {
            if (mDateEntityList != null) {
                mDateEntityList.clear();
            }
            mNowCalendarPoint = mCalendarTool.getNowCalendar();
            if (i == 0) {
                mDateEntityList = mCalendarTool.getDateEntityList(mNowCalendarPoint.x,
                        mNowCalendarPoint.y);
            } else {
                if (mNowCalendarPoint.y - 1 <= 0) {
                    mDateEntityList = mCalendarTool.getDateEntityList(
                            mNowCalendarPoint.x - 1, 12);
                } else {
                    mDateEntityList = mCalendarTool.getDateEntityList(
                            mNowCalendarPoint.x, mNowCalendarPoint.y - 1);
                }
            }
            List<DateEntity> mDateEntityList_ = new ArrayList<>();
            mDateEntityList_.addAll(mDateEntityList);
            mNowCalendarPoint = mCalendarTool.getNowCalendar();
            RecordCalenderInfo mRecordCalenderInfo = new RecordCalenderInfo(mNowCalendarPoint.x, mNowCalendarPoint.y, mDateEntityList_);
            list.add(mRecordCalenderInfo);
            mRecordCalenderInfo = null;
        }
        RecordCalendarUtils mRecordCalendarUtils = new RecordCalendarUtils(context, null, uid, token, access_token);
        HashMap<String, DateEntity> msportmap = mRecordCalendarUtils.getDBDate();
        mRecordCalenderAdapter = new RecordCalenderAdapter(this, getResources(),mRecordListDBOpenHelper);
        mRecordCalenderAdapter.setList(list);
        mRecordCalenderAdapter.setMsportmap(msportmap);
        gv_date.setAdapter(mRecordCalenderAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDB != null) {
            mDB.close();
        }
        if (mRecordListDBOpenHelper != null) {
            mRecordListDBOpenHelper.close();
        }

    }

    private void doBack() {
        finish();
        CommonUtils.getInstance().setPageBackAnim(this);
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doBack();
        }
        return false;
    }
}
