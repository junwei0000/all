package com.KiwiSports.control.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.KiwiSports.business.RecordCalenderBusiness;
import com.KiwiSports.control.calendar.CalendarTool;
import com.KiwiSports.control.calendar.DateEntity;
import com.KiwiSports.model.RecordCalenderInfo;
import com.KiwiSports.model.db.RecordCalenderDBOpenHelper;
import com.KiwiSports.model.db.BaseDBHelper;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.parser.RecordCalenderParser;
import com.KiwiSports.utils.volley.RequestUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 记录日历运动类型列表工具类
 */
public class RecordCalendarUtils {
    private Context context;
    int showMonthnum = Constants.getInstance().showMonthnum;
    List<DateEntity> mDateEntityList;
    private Point mNowCalendarPoint;
    private CalendarTool mCalendarTool;
    ArrayList<RecordCalenderInfo> list = new ArrayList<>();
    RecordCalenderDBOpenHelper mDB;
    String uid;
    private String token;
    private String access_token;

    public RecordCalendarUtils(Context mActivity, RecordCalenderDBOpenHelper mDB_, String uid, String token, String access_token) {
        super();
        this.context = mActivity;
        this.mDB = mDB_;
        this.uid = uid;
        this.token = token;
        this.access_token = access_token;
        if (mDB_ == null) {
            mDB = new RecordCalenderDBOpenHelper(context);
        }
        loadCaenderAll();
    }


    public void loadCaenderAll() {
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
    }

    /**
     * 加载前六个月数据
     */
    public void getAllCadenrdats() {
        ArrayList<String> mCalenderList = mDB
                .getHistoryDBList(uid);
        if (mCalenderList != null && mCalenderList.size() > 0 && mCalenderList.size() == list.size()) {
            i = 0;
            getRecordData(list.get(0).getYear(), list.get(0).getMonth());
            //更新当月
        } else {
            //更新6个月
            loadView();
        }
    }

    private void addDB(String uidyearmonthValue, String recordDatas) {
        if (!mDB.hasInfo(mDB.uidyearmonth, uidyearmonthValue,
                BaseDBHelper.TABLE_RECORDCALENDER)) {
            mDB.addTableCalenderInfo(uid, uidyearmonthValue, recordDatas);
        } else {
            mDB.updateTableCalenderInfo(recordDatas, uidyearmonthValue);
        }
    }


    int i = Constants.getInstance().showMonthnum;

    /**
     * 加载6个月数据
     */
    private void loadView() {
        i--;
        if (0 <= i && i < list.size()) {
            mHandler.sendEmptyMessage(1);
        }
    }

    /**
     * @return
     */
    public HashMap<String, DateEntity> getDBDate() {
        ArrayList<String> mCalenderList = mDB
                .getHistoryDBList(uid);
        if (mCalenderList != null && mCalenderList.size() > 0) {

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

    private HashMap<String, String> mhashmap;
    private HashMap<String, DateEntity> msportmap = new HashMap<>();

    private void getRecordData(final int year, final int month) {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token + "");
        mhashmap.put("access_token", access_token);
        mhashmap.put("year", year + "");
        mhashmap.put("month", month + "");
        Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
        new RecordCalenderBusiness(context, mhashmap, new RecordCalenderBusiness.GetRecordListCallback() {
            @Override
            public void afterDataGet(String response) {
                if (response != null) {
                    String uidyearmonthValue = uid + "_" + year + "_" + month;
                    addDB(uidyearmonthValue, response);
                }
                loadView();
            }
        });
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //这里用来获取到Service发来的数据
                case 1:
                    getRecordData(list.get(i).getYear(), list.get(i).getMonth());
                    break;
            }
        }
    };
}
