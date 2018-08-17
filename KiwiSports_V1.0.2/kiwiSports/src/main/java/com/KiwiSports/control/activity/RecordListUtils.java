package com.KiwiSports.control.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.KiwiSports.business.RecordListBusiness;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.DatesUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 记录列表工具类
 */
public class RecordListUtils {
    private Context context;
    RecordListDBOpenHelper mDB;
    String uid;
    private String token;
    private String access_token;

    public RecordListUtils(Context mActivity, RecordListDBOpenHelper mRecordListDB_, String uid, String token, String access_token) {
        super();
        this.context = mActivity;
        this.mDB = mRecordListDB_;
        this.uid = uid;
        this.token = token;
        this.access_token = access_token;
    }


    private HashMap<String, String> mhashmap;
    protected ArrayList<RecordInfo> mList = new ArrayList<>();
    private int page = 1;
    private int page_size = 50;

    /**
     * 新装app缓存下载列表数据
     */
    public void getRecordListDataToDB() {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        mhashmap.put("page", page + "");
        mhashmap.put("page_size", page_size + "");
        Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
        new RecordListBusiness(context, mList, mhashmap,
                new RecordListBusiness.GetRecordListCallback() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void afterDataGet(HashMap<String, Object> dataMap) {
                        if (dataMap != null) {
                            String status = (String) dataMap.get("status");
                            if (status.equals("200")) {
                                int total = (Integer) dataMap.get("count");
                                mList = (ArrayList<RecordInfo>) dataMap
                                        .get("mlist");
                                Log.e("___listdb___", "-----------total----mList.size()--------" + total + "    " + mList.size());
                                if (mList != null && mList.size() > 0) {
                                    if (total > page * page_size) {
                                        mHandler.sendEmptyMessage(1);
                                    } else {
                                        mHandler.sendEmptyMessage(2);
                                    }
                                }
                            }
                        }
                        CommonUtils.getInstance().setClearCacheBackDate(
                                mhashmap, dataMap);
                    }
                });

    }

    private void addAllDB() {
        Log.e("___listdb___", "------------mList.size()------------" + mList.size());
        if (mList != null && mList.size() > 0) {
            for (int i = mList.size() - 1; i >= 0; i--) {
                RecordInfo mRecordInfo = mList.get(i);
                addDB(mRecordInfo);
            }
        }
    }

    public void addDB(RecordInfo mRecordInfo) {
        if (!mDB.hasrunStartTimeTampInfo(mRecordInfo.getUid(), mRecordInfo.getRunStartTimeTamp())) {
            mDB.addTableInfo(mRecordInfo);
        }else{
            mDB.updateTableInfo(mRecordInfo);
        }
    }

    public void delDB(RecordInfo mRecordInfo){
        if (mDB.hasrunStartTimeTampInfo(mRecordInfo.getUid(), mRecordInfo.getRunStartTimeTamp())) {
            mDB.deleteTableInfo(mRecordInfo.getRunStartTimeTamp());
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //这里用来获取到Service发来的数据
                case 1:
                    page++;
                    getRecordListDataToDB();
                    break;
                case 2:
                    addAllDB();
                    break;
            }
        }
    };

    int testid=48888;
    /**
     * 上传轨迹成功后，添加运动记录到数据库
     */
    public void TestAddRecordListDB() {
        String record_id = (testid++)+"";
        String uid = "20051";
        String posid = "0";
        double distanceTraveled = 60.0;
        long runingTimestamp = 12545;
        double verticalDistance = 0;
        double topSpeed = 7.2;
        double dropTraveled = 0;
        int nSteps = 61;
        String matchSpeed = "14'23\"";
        String maxMatchSpeed = "08'20\"";
        int _nMaxSlopeAngle = 0;
        int maxAltitude = 45;
        int currentAltitude = 43;
        String averageMatchSpeed = "09'26\"";
        double averageSpeed = 6.35;
        long pauseTimestamp = 3;
        String maxHoverDuration = "";
        String totalHoverDuration = "";
        int lapCount = 0;
        String wrestlingCount = "";
        String cableCarQueuingDuration = "";
        String address = "gps";
        int minAltidue = 43;
        double calorie = 4.0;
        int sportindex = 1;
        String latitudeOffset = "";
        String longitudeOffset = "";
        double upHillDistance = 0;
        double downHillDistance = 0;
        String startTrackTime = DatesUtils.getInstance().getNowTime(
                "yyyy-MM-dd HH:mm:ss");
        String minMatchSpeed = "";
        String date_time = DatesUtils.getInstance().getNowTime("yyyy-MM-dd");
        long startTrackTimeTamp=DatesUtils.getInstance().getDateToTimeStamp(startTrackTime,"yyyy-MM-dd HH:mm:ss");
        RecordInfo mRecordInfo = new RecordInfo(record_id, uid,
                posid, date_time, distanceTraveled, runingTimestamp / 1000,
                String.valueOf(verticalDistance), String.valueOf(topSpeed), String.valueOf(dropTraveled), String.valueOf(nSteps),
                matchSpeed, maxMatchSpeed, String.valueOf(_nMaxSlopeAngle), String.valueOf(maxAltitude),
                String.valueOf(currentAltitude), averageMatchSpeed, String.valueOf(averageSpeed),
                pauseTimestamp / 1000, maxHoverDuration,
                totalHoverDuration, String.valueOf(lapCount), wrestlingCount,
                cableCarQueuingDuration, address, String.valueOf(minAltidue),
                String.valueOf(calorie), String.valueOf(sportindex), latitudeOffset,
                longitudeOffset, String.valueOf(upHillDistance), String.valueOf(downHillDistance),
                "", "", "",
                startTrackTime, "1", "ceshi", startTrackTime,String.valueOf(startTrackTimeTamp), minMatchSpeed,
                RecordListDBOpenHelper.currentTrackBOVER);
        addDB(mRecordInfo);
        Constants.getInstance().addTrackStatus = true;
    }
}
