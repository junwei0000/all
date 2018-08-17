package com.KiwiSports.control.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.KiwiSports.R;
import com.KiwiSports.business.RecordDetailBusiness;
import com.KiwiSports.business.VenuesUsersBusiness;
import com.KiwiSports.business.RecordDetailBusiness.GetRecordDetailCallback;
import com.KiwiSports.business.VenuesUsersBusiness.GetVenuesUsersCallback;
import com.KiwiSports.control.adapter.MainPropertyAdapter;
import com.KiwiSports.control.adapter.RecordDetailColumnarAdapter;
import com.KiwiSports.control.locationService.GradientHelper;
import com.KiwiSports.control.mp.ChartDataAdapter;
import com.KiwiSports.control.mp.ChartItem;
import com.KiwiSports.control.mp.LineChartItem;
import com.KiwiSports.model.MacthSpeedInfo;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.model.db.RecordListDBOpenHelper;
import com.KiwiSports.model.db.TrackListDBOpenHelper;
import com.KiwiSports.utils.CircleImageView;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.GPSUtil;
import com.KiwiSports.utils.ImageLoader;
import com.KiwiSports.utils.MyGridView;
import com.KiwiSports.utils.MyListView;
import com.KiwiSports.utils.PriceUtils;
import com.KiwiSports.utils.TrackCacheUtils;
import com.KiwiSports.utils.parser.MainsportParser;
import com.KiwiSports.utils.parser.RecordDetailParser;
import com.KiwiSports.utils.volley.RequestUtils;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：
 */
public class RecordDetailActivity extends BaseActivity {

    private HashMap<String, String> mhashmap;
    private SharedPreferences bestDoInfoSharedPrefs;
    private String uid;
    private String token;
    private String access_token;
    private String nick_name;
    private String album_url;

    private String sportindex;
    private String posid;
    boolean mapStatus = true;
    private String record_id;
    private String sportname;
    private String runStartTime;

    private FrameLayout layout_map;
    private ImageView map_iv_back;
    private TextView map_tv_time;
    private CircleImageView map_iv_head;
    private TextView map_tv_name;
    private TextView map_tv_remain;
    private TextView map_tv_distance;
    private TextView map_tv_rundate;
    private TextView map_tv_slowps;
    private TextView map_tv_averageps;
    private TextView map_tv_fastps;
    private TextView tv_map;
    private TextView tv_mapline;
    private TextView tv_date;
    private TextView tv_dateline;
    private LinearLayout layout_date;
    private LinearLayout date_layout_back;
    private TextView date_tv_distance, date_tv_name, date_tv_you;
    private TextView date_tv_rundate;
    private MyGridView date_property;
    private MyListView listView;
    private TextView map_tv_slowpstitle;
    private TextView map_tv_averagepstitle;
    private TextView map_tv_fastpstitle;
    private ImageView map_iv_sporticon;
    private TextView map_tv_sporttype;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_layout_back:
            case R.id.map_iv_back:
                doBack();
                break;
            case R.id.date_tv_you:
                Intent intent = new Intent(this, RecordDetailYouActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("posid", posid);
                startActivity(intent);
                CommonUtils.getInstance().setPageIntentAnim(intent, this);
                break;
            case R.id.tv_map:
                mapStatus = true;
                initBottom();
                break;
            case R.id.tv_date:
                mapStatus = false;
                initBottom();
                break;
            default:
                break;
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.record_detail);
        CommonUtils.getInstance().addActivity(this);
    }

    @Override
    protected void findViewById() {

        layout_map = (FrameLayout) findViewById(R.id.layout_map);
        map_iv_back = (ImageView) findViewById(R.id.map_iv_back);
        map_tv_time = (TextView) findViewById(R.id.map_tv_time);
        map_iv_head = (CircleImageView) findViewById(R.id.map_iv_head);
        map_tv_name = (TextView) findViewById(R.id.map_tv_name);
        map_tv_remain = (TextView) findViewById(R.id.map_tv_remain);
        map_tv_distance = (TextView) findViewById(R.id.map_tv_distance);
        map_tv_rundate = (TextView) findViewById(R.id.map_tv_rundate);

        map_iv_sporticon = (ImageView) findViewById(R.id.map_iv_sporticon);
        map_tv_sporttype = (TextView) findViewById(R.id.map_tv_sporttype);

        map_tv_slowps = (TextView) findViewById(R.id.map_tv_slowps);
        map_tv_averageps = (TextView) findViewById(R.id.map_tv_averageps);
        map_tv_fastps = (TextView) findViewById(R.id.map_tv_fastps);
        map_tv_slowpstitle = (TextView) findViewById(R.id.map_tv_slowpstitle);
        map_tv_averagepstitle = (TextView) findViewById(R.id.map_tv_averagepstitle);
        map_tv_fastpstitle = (TextView) findViewById(R.id.map_tv_fastpstitle);
        CommonUtils.getInstance().setTextViewTypeface(this, map_tv_time);
        CommonUtils.getInstance().setTextViewTypeface(this, map_tv_remain);
        CommonUtils.getInstance().setTextViewTypeface(this, map_tv_distance);
        CommonUtils.getInstance().setTextViewTypeface(this, map_tv_rundate);
        CommonUtils.getInstance().setTextViewTypeface(this, map_tv_slowps);
        CommonUtils.getInstance().setTextViewTypeface(this, map_tv_averageps);
        CommonUtils.getInstance().setTextViewTypeface(this, map_tv_fastps);
        //---
        layout_date = (LinearLayout) findViewById(R.id.layout_date);
        date_layout_back = (LinearLayout) findViewById(R.id.date_layout_back);
        date_tv_name = (TextView) findViewById(R.id.date_tv_name);
        date_tv_you = (TextView) findViewById(R.id.date_tv_you);
        date_tv_distance = (TextView) findViewById(R.id.date_tv_distance);
        date_tv_rundate = (TextView) findViewById(R.id.date_tv_rundate);
        date_property = (MyGridView) findViewById(R.id.date_property);
        CommonUtils.getInstance().setTextViewTypeface(this, date_tv_distance);
        CommonUtils.getInstance().setTextViewTypeface(this, date_tv_rundate);
        listView = (MyListView) findViewById(R.id.listView);
        //---
        tv_map = (TextView) findViewById(R.id.tv_map);
        tv_mapline = (TextView) findViewById(R.id.tv_mapline);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_dateline = (TextView) findViewById(R.id.tv_dateline);
        initBottom();
    }

    @SuppressLint("NewApi")
    @Override
    protected void setListener() {
        map_iv_back.setOnClickListener(this);
        date_layout_back.setOnClickListener(this);
        date_tv_you.setOnClickListener(this);
        tv_map.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        bestDoInfoSharedPrefs = CommonUtils.getInstance()
                .getBestDoInfoSharedPrefs(this);
        uid = bestDoInfoSharedPrefs.getString("uid", "");
        nick_name = bestDoInfoSharedPrefs.getString("nick_name", "");
        album_url = bestDoInfoSharedPrefs.getString("album_url", "");
        token = bestDoInfoSharedPrefs.getString("token", "");
        access_token = bestDoInfoSharedPrefs.getString("access_token", "");
        Intent intent = getIntent();
        posid = intent.getExtras().getString("posid", "");
        record_id = intent.getExtras().getString("record_id", "");
        sportindex = intent.getExtras().getString("sporttype", "");
        if (sportindex.equals("13") || sportindex.equals("14")) {
            date_tv_you.setVisibility(View.VISIBLE);
        } else {
            date_tv_you.setVisibility(View.GONE);
        }
        runStartTime = intent.getExtras().getString("runStartTime", "");
        distanceTraveled = intent.getExtras().getDouble("distanceTraveled");
    }

    private void initBottom() {
        tv_map.setTextColor(getResources().getColor(R.color.white_light));
        tv_date.setTextColor(getResources().getColor(R.color.white_light));
        tv_mapline.setVisibility(View.INVISIBLE);
        tv_dateline.setVisibility(View.INVISIBLE);
        if (mapStatus) {
            tv_map.setTextColor(getResources().getColor(R.color.white));
            tv_mapline.setVisibility(View.VISIBLE);
            layout_date.setVisibility(View.GONE);
            layout_map.setVisibility(View.VISIBLE);
        } else {
            tv_date.setTextColor(getResources().getColor(R.color.white));
            tv_dateline.setVisibility(View.VISIBLE);
            layout_date.setVisibility(View.VISIBLE);
            layout_map.setVisibility(View.GONE);
        }
    }

    private ProgressDialog mDialog;
    protected RecordInfo mRecordInfo;
    protected List<MainLocationItemInfo> allpointChartList;
    private ArrayList<MacthSpeedInfo> allmatchSpeedList;
    private Long maxmatchSpeedTimestamp;
    private ArrayList<MainSportInfo> mMpropertyList;
    private String duration;
    private double distanceTraveled;
    private String freezeDuration;
    private String nSteps;
    private String maxMatchSpeed, minMatchSpeed;
    private String averageMatchSpeed;
    private String currentAltitude;
    private String averageSpeed;
    private String lapCount;
    private String topSpeed;
    private String downHillDistance;
    private String verticalDistance;
    private String maxSlope;

    private void showDilag() {
        try {
            if (mDialog == null) {
                mDialog = CommonUtils.getInstance().createLoadingDialog(this);
            } else {
                mDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void processLogic() {
        initMapView();
        mHandler.sendEmptyMessageDelayed(2, 1);
    }

    RecordListDBOpenHelper mRecordListDBOpenHelper;
    TrackListDBOpenHelper mTrackListDBOpenHelper;

    protected void processLogicLLLL() {
        if (mRecordListDBOpenHelper == null) {
            mRecordListDBOpenHelper = new RecordListDBOpenHelper(this);
        }
        if (mTrackListDBOpenHelper == null) {
            mTrackListDBOpenHelper = new TrackListDBOpenHelper(this);
        }

        if (TextUtils.isEmpty(runStartTime)) {
            getDats();
        } else {
            /**
             * 当db保存所有数据时才读取，否则从后台网络读取
             */
            boolean lastDisStatus = false;
            if (mTrackListDBOpenHelper.hasInfo(uid, runStartTime)) {
                Double lastdistance = mTrackListDBOpenHelper.getHistoryDBLastDis(uid, runStartTime);
                String SubtractSum = PriceUtils.getInstance().gteSubtractSumPrice(String.valueOf(lastdistance), String.valueOf(distanceTraveled));
                if (Double.valueOf(SubtractSum) < 0.1 * 1000) {
                    lastDisStatus = true;
                }
            }
            Log.e("map", "lastDisStatus= " + lastDisStatus);
            long startTrackTimeTamp = DatesUtils.getInstance().getDateToTimeStamp(runStartTime, "yyyy-MM-dd HH:mm:ss");
            if (mRecordListDBOpenHelper.hasrunStartTimeTampInfo(uid, String.valueOf(startTrackTimeTamp))
                    && mTrackListDBOpenHelper.hasInfo(uid, runStartTime) && lastDisStatus) {
                TrackCacheUtils.getInstance().updateTrackCacheDBStatus(uid + "_" + runStartTime, false);
                mRecordInfo = mRecordListDBOpenHelper.getRecordInfoByDB(uid, runStartTime);
                HashMap<String, Object> dataMap = mTrackListDBOpenHelper.getHistoryDBList(uid, runStartTime);
                if (dataMap != null) {
                    if (mRecordInfo != null) {
                        String topSpeed_ = (String) dataMap.get("topSpeed");
                        topSpeed = mRecordInfo.getTopSpeed();
                        if (!topSpeed_.equals(topSpeed)) {
                            mRecordInfo.setTopSpeed(topSpeed_);
                            mRecordListDBOpenHelper.updateTableInfo(mRecordInfo);
                        }
                    }
                    dataMap.put("mRecordInfo", mRecordInfo);
                    dataMap.put("status", "200");
                }
                Message msg = new Message();
                msg.what = 4;
                msg.obj = dataMap;
                mHandler.sendMessage(msg);
                msg = null;
            } else {
                getDats();
            }
        }
    }

    private void getDats() {
        showDilag();
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        mhashmap.put("record_id", record_id);
        Log.e("map", mhashmap.toString());
        new RecordDetailBusiness(this, mhashmap, new GetRecordDetailCallback() {
            @SuppressWarnings("unchecked")
            @Override
            public void afterDataGet(HashMap<String, Object> dataMap) {
                Message msg = new Message();
                msg.what = 4;
                msg.obj = dataMap;
                mHandler.sendMessage(msg);
                msg = null;
                mHandler.sendEmptyMessageDelayed(3, 1000);

            }
        });
    }


    private void cacheToDB() {
        if (mRecordInfo != null) {
            String topSpeed = mRecordListDBOpenHelper.getRecordInfoByDB(uid, runStartTime).getTopSpeed();
            if (!topSpeed.equals(mRecordInfo.getTopSpeed())) {
                mRecordListDBOpenHelper.updateTableInfo(mRecordInfo);
            }
        }
        boolean mTrackCacheDBStatus = TrackCacheUtils.getInstance().getTrackCacheDBStatus(uid + "_" + runStartTime);
        int beforeCacheSum = mTrackListDBOpenHelper.getHistoryDBSum(uid, runStartTime);
        Log.e("cacheToDB", "beforeCacheSum>>>>>>" + beforeCacheSum);
        Log.e("cacheToDB", "mTrackCacheDBStatus>>>>>>" + mTrackCacheDBStatus);
        if (!mTrackCacheDBStatus && allpointChartList != null && allpointChartList.size() > 0 && beforeCacheSum < allpointChartList.size()) {
            TrackCacheUtils.getInstance().updateTrackCacheDBStatus(uid + "_" + runStartTime, true);
            //中断时续传
            for (int i = beforeCacheSum; i < allpointChartList.size(); i++) {
                long duration = allpointChartList.get(i).getDuration();
                if (!mTrackListDBOpenHelper.hasInfo(uid, runStartTime, String.valueOf(duration))) {
                    mTrackListDBOpenHelper.addTableInfo(uid, runStartTime, allpointChartList.get(i));
                } else {
                    Log.e("map", "+++++++++++++++++++++++++++++++++++" + runStartTime + "_" + duration);
                }
            }
        }
    }

    private void showDatas(HashMap<String, Object> dataMap) {
        if (dataMap != null) {
            String status = (String) dataMap.get("status");
            if (status.equals("200")) {
                mRecordInfo = (RecordInfo) dataMap.get("mRecordInfo");
                allpointChartList = (ArrayList<MainLocationItemInfo>) dataMap
                        .get("allpointList");
                allmatchSpeedList = (ArrayList<MacthSpeedInfo>) dataMap
                        .get("allmatchSpeedList");
                maxmatchSpeedTimestamp = (Long) dataMap
                        .get("maxmatchSpeedTimestamp");
            } else {
                String msg = (String) dataMap.get("msg");
                CommonUtils.getInstance().initToast(msg);
            }
        } else {
            CommonUtils.getInstance().initToast(
                    getString(R.string.net_tishi));
        }
        if (mRecordInfo != null) {
            setSportPropertyList();
        }
        if (allpointChartList != null && allpointChartList.size() > 0) {
            addMarker();
            if (!sportindex.equals("1")) {
                initDateView();
            }
        }
        if (sportindex.equals("1")) {// 跑步
            if (allmatchSpeedList != null && allmatchSpeedList.size() > 0) {
                RecordDetailColumnarAdapter mAdapter = new RecordDetailColumnarAdapter(context, allmatchSpeedList, maxmatchSpeedTimestamp, distanceTraveled);
                listView.setAdapter(mAdapter);
            }
        }
    }

    private void initDateView() {
        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        list.add(new LineChartItem(generateADataLine(), getApplicationContext()));
        list.add(new LineChartItem(generateSDataLine(), getApplicationContext()));
        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(),
                list);
        TextView mTextView = new TextView(context);
        mTextView.setHeight(50);
        listView.setAdapter(cda);
        listView.addFooterView(mTextView);
    }

    /**
     * generates a random ChartData object with just one DataSet 海拔
     *
     * @return
     */
    private LineData generateADataLine() {
        ArrayList<Entry> e1 = new ArrayList<Entry>();
        for (int i = 0; i < allpointChartList.size(); i++) {
            e1.add(new Entry((float) allpointChartList.get(i).getAltitude(), i));
        }

        LineDataSet d1 = new LineDataSet(e1, "海拔（m）");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(0);
        d1.setHighLightColor(getResources().getColor(R.color.ching));
        d1.setDrawValues(false);

        LineData cd = new LineData(getMonths(), d1);
        return cd;
    }

    /**
     * 速度
     *
     * @return
     */
    private LineData generateSDataLine() {
        ArrayList<Entry> e1 = new ArrayList<Entry>();
        for (int i = 0; i < allpointChartList.size(); i++) {
            e1.add(new Entry((float) allpointChartList.get(i).getSpeed(), i));
        }

        LineDataSet d1 = new LineDataSet(e1, "速度（km/h）");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(0);
        d1.setHighLightColor(getResources().getColor(R.color.ching));
        d1.setDrawValues(false);
        LineData cd = new LineData(getMonths(), d1);
        return cd;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        String time = null;
        for (int i = 0; i < allpointChartList.size(); i++) {
            int timestamp = (int) allpointChartList.get(i).getDuration();
            time = DatesUtils.getInstance().formatTimes(timestamp * 1000);
            m.add(" " + time + " ");
        }
        return m;
    }

    private void setSportPropertyList() {
        MainsportParser mMainsportParser = new MainsportParser();
        ArrayList<MainSportInfo> mallsportList = mMainsportParser
                .parseJSON(this);
        sportindex = mRecordInfo.getSportsType();
        for (int i = 0; i < mallsportList.size(); i++) {
            MainSportInfo mMainSportInfo = mallsportList.get(i);
            if (sportindex.equals(mMainSportInfo.getIndex() + "")) {
                mMpropertyList = mMainSportInfo.getMpropertyList();
                sportname = mMainSportInfo.getCname();
                date_tv_name.setText(sportname);
                break;
            }
        }
        // 没找到，默认“其他” 最后一个
        if (mMpropertyList == null) {
            mMpropertyList = mallsportList.get(mallsportList.size() - 1)
                    .getMpropertyList();
            sportname = mallsportList.get(mallsportList.size() - 1)
                    .getCname();
            date_tv_name.setText(sportname);
        }
        setSportIcon();
        showCurrentPropertyValue();
    }

    private void setSportIcon() {
        map_tv_sporttype.setText(sportname);
        if (sportindex.equals("0")) {// 走路
            map_iv_sporticon.setBackgroundResource(R.drawable.mainstart_walk_img);
        } else if (sportindex.equals("1")) {// 跑步
            map_iv_sporticon.setBackgroundResource(R.drawable.mainstart_run_img);
        } else if (sportindex.equals("2")) {// 骑行
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_bike_img);
        } else if (sportindex.equals("13")) {// 单板滑雪
            map_iv_sporticon.setBackgroundResource(R.drawable.mainstart_snowboard_img);
        } else if (sportindex.equals("14")) {// 双板滑雪
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_ski_img);
        } else if (sportindex.equals("5")) {// 越野跑
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_cross_country_run_img);
        } else if (sportindex.equals("6")) {// 山地车越野
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_mountainbike_img);
        } else if (sportindex.equals("7")) {// 轮滑
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_skate_img);
        } else if (sportindex.equals("8")) {// 摩托车
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_motorbike_img);
        } else if (sportindex.equals("11")) {// 长板
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_longboard_img);
        } else if (sportindex.equals("12")) {// 骑马
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_horseride_img);
        } else if (sportindex.equals("16")) {// 帆板
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_windsurfing_img);
        } else if (sportindex.equals("17")) {// 风筝冲浪
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_kitesurfing_img);
        } else if (sportindex.equals("3")) {// 开车
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_drive_img);
        } else {
            map_iv_sporticon
                    .setBackgroundResource(R.drawable.mainstart_other_img);
        }
    }

    /**
     * 显示属性值
     */
    private void showCurrentPropertyValue() {
        distanceTraveled = mRecordInfo.getDistanceTraveled();
        distanceTraveled = distanceTraveled / 1000;
        long runingTimestamp = mRecordInfo.getDuration();
        duration = DatesUtils.getInstance().formatTimes(runingTimestamp * 1000);
        long freezeTimestamp = mRecordInfo.getFreezeDuration();
        freezeDuration = DatesUtils.getInstance().formatTimes(
                freezeTimestamp * 1000);
        nSteps = mRecordInfo.getnSteps();
        minMatchSpeed = mRecordInfo.getMinMatchSpeed();
        if(TextUtils.isEmpty(minMatchSpeed)){
            minMatchSpeed="--";
        }
        maxMatchSpeed = mRecordInfo.getMaxMatchSpeed();
        averageMatchSpeed = mRecordInfo.getAverageMatchSpeed();
        currentAltitude = mRecordInfo.getCurrentAltitude();
        averageSpeed = mRecordInfo.getAverageSpeed();
        averageSpeed = PriceUtils.getInstance().getPriceTwoDecimal(
                Double.valueOf(averageSpeed), 2);
        lapCount = mRecordInfo.getLopCount();
        topSpeed = mRecordInfo.getTopSpeed();
        topSpeed = PriceUtils.getInstance().getPriceTwoDecimal(
                Double.valueOf(topSpeed), 2);
        downHillDistance = mRecordInfo.getDownHillDistance();
        verticalDistance = mRecordInfo.getVerticalDistance();
        maxSlope = mRecordInfo.getMaxSlope();
        //map
        ImageLoader asyncImageLoader = new ImageLoader(context, "headImg");
        if (!TextUtils.isEmpty(album_url)) {
            asyncImageLoader.DisplayImage(album_url, map_iv_head);
        } else {
            Bitmap mBitmap = asyncImageLoader.readBitMap(this,
                    R.drawable.ic_launcher);
            map_iv_head.setImageBitmap(mBitmap);
        }
        String create_time = mRecordInfo.getRunStartTime();
        map_tv_time.setText(DatesUtils.getInstance().getDateGeShi(create_time, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm"));
        map_tv_name.setText(nick_name);
        map_tv_remain.setText("0");
        map_tv_distance.setText(PriceUtils.getInstance().formatFloatNumber(distanceTraveled)
                + "");
        map_tv_rundate.setText(duration);
        setMapProperty();
        //-------------------------date-------------------------
        if (sportindex.equals("1")) {//跑步
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(averageMatchSpeed + "");
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(maxMatchSpeed);
                        break;
                    case 2:
                        mMpropertyList.get(i).setValue(nSteps + "");
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(freezeDuration);
                        break;
                    case 4:
                        mMpropertyList.get(i).setValue(minMatchSpeed + "");
                        break;
                    default:
                        break;
                }
            }

        } else if (sportindex.equals("2") || sportindex.equals("6")) {//骑行
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(averageMatchSpeed);
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(maxMatchSpeed + "");
                        break;
                    case 2:
                        mMpropertyList.get(i).setValue(freezeDuration);
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(currentAltitude + "");
                        break;
                    default:
                        break;
                }
            }
        } else if (sportindex.equals("0")) {//走路
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(averageSpeed + "");
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(freezeDuration);
                        break;
                    case 2:
                        mMpropertyList.get(i).setValue(nSteps + "");
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(currentAltitude + "");
                        break;
                    default:
                        break;
                }
            }
        } else if (sportindex.equals("13") || sportindex.equals("14")) {//滑雪
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(duration);
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(downHillDistance);
                        break;
                    case 2:
                        mMpropertyList.get(i).setValue(verticalDistance + "");
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(topSpeed + "");
                        break;
                    case 4:
                        mMpropertyList.get(i).setValue(maxSlope);
                        break;
                    case 5:
                        mMpropertyList.get(i).setValue(currentAltitude + "");
                        break;
                    default:
                        break;
                }
            }
        } else {
            for (int i = 0; i < mMpropertyList.size(); i++) {
                switch (i) {
                    case 0:
                        mMpropertyList.get(i).setValue(averageSpeed + "");
                        break;
                    case 1:
                        mMpropertyList.get(i).setValue(freezeDuration);
                        break;
                    case 2:
                        mMpropertyList.get(i).setValue(topSpeed + "");
                        break;
                    case 3:
                        mMpropertyList.get(i).setValue(averageSpeed + "");
                        break;
                    case 4:
                        mMpropertyList.get(i).setValue(currentAltitude + "");
                        break;
                    default:
                        break;
                }
            }
        }
        date_tv_distance.setText(PriceUtils.getInstance().formatFloatNumber(distanceTraveled)
                + "");
        date_tv_rundate.setText(duration);
        MainPropertyAdapter mdate_propertyAdapter = new MainPropertyAdapter(
                this, mMpropertyList);
        date_property.setAdapter(mdate_propertyAdapter);
    }

    /**
     * 设置运动类型 map属性
     */
    private void setMapProperty() {
        String title1 = "";
        String title2 = "";
        String title3 = "";
        String cont1 = "";
        String cont2 = "";
        String cont3 = "";
        if (sportindex.equals("0")) {//走路
            title1 = "平均速度";
            title2 = "步数";
            title3 = "休息时间";
            cont1 = averageSpeed + "km/h";
            cont2 = nSteps;
            cont3 = freezeDuration;
        } else if (sportindex.equals("1")) {//跑步
            title1 = "最慢配速";
            title2 = "平均配速";
            title3 = "最快配速";
            cont1 = minMatchSpeed;
            cont2 = averageMatchSpeed;
            cont3 = maxMatchSpeed;
        } else if (sportindex.equals("2") || sportindex.equals("6")) {//骑行
            title1 = "平均速度";
            title2 = "最高速度";
            title3 = "休息时间";
            cont1 = averageSpeed + "km/h";
            cont2 = topSpeed + "km/h";
            cont3 = freezeDuration;
        } else if (sportindex.equals("13") || sportindex.equals("14")) {//滑雪
            title1 = "滑行距离";
            title2 = "最大速度";
            title3 = "趟数";
            cont1 = downHillDistance + "m";
            cont2 = topSpeed + "km/h";
            cont3 = lapCount;
        } else {//其他
            title1 = "最高时速";
            title2 = "平均速度";
            title3 = "休息时间";
            cont1 = topSpeed + "km/h";
            cont2 = averageSpeed + "km/h";
            cont3 = freezeDuration;
        }
        map_tv_slowps.setText(cont1);
        map_tv_averageps.setText(cont2);
        map_tv_fastps.setText(cont3);
        map_tv_slowpstitle.setText(title1);
        map_tv_averagepstitle.setText(title2);
        map_tv_fastpstitle.setText(title3);
    }

    public void addMarker() {
        showBubbleView();
    }

    @SuppressLint("UseSparseArrays")
    HashMap<Integer, Integer> mBubbleMap = new HashMap<Integer, Integer>();
//	private double sum_distance;

    /**
     * 每隔一公里显示气泡
     */
    private void showBubbleView() {
        GradientHelper mGradientHelper = new GradientHelper();

        double maxLng = allpointChartList.get(0).getLongitude();
        double minLng = allpointChartList.get(0).getLongitude();
        double maxLat = allpointChartList.get(0).getLatitude();
        double minLat = allpointChartList.get(0).getLatitude();
        LatLng nowlatLng;


        int numn = allpointChartList.size() % 5000;
        int snum = allpointChartList.size() / 5000;
        int polylinenum = (numn == 0 ? snum : snum + 1);
        Log.e("size----", "polylinenum=" + polylinenum);

        for (int j = 0; j < polylinenum; j++) {
            PolylineOptions polyline = new PolylineOptions();

            int starti = 5000 * j;
            int endi = 5000 * (j + 1);
            if (endi > allpointChartList.size()) {
                endi = allpointChartList.size();
            }
            for (int i = starti; i < endi; i++) {
                nowlatLng = allpointChartList.get(i).getmLatLng();
                if (nowlatLng == null
                        || (nowlatLng != null && nowlatLng.latitude == 0)) {
                    break;
                }
                if (nowlatLng.longitude > maxLng) {
                    maxLng = nowlatLng.longitude;
                }
                if (nowlatLng.longitude < minLng) {
                    minLng = nowlatLng.longitude;
                }
                if (nowlatLng.latitude > maxLat) {
                    maxLat = nowlatLng.latitude;
                }
                if (nowlatLng.latitude < minLat) {
                    minLat = nowlatLng.latitude;
                }
                double sum_distance = allpointChartList.get(i).getDistance();
                sum_distance = sum_distance / 1000;
                if (i == 0) {
                    addMarkerBubble(nowlatLng, i, "0");
                    mBubbleMap.put(0, 0);
                } else if (i == allpointChartList.size() - 1) {
                    addMarkerBubble(nowlatLng, i, "0");
                } else {
                    int juliSt = (int) sum_distance;
                    if (!mBubbleMap.containsKey(juliSt)) {
                        mBubbleMap.put(juliSt, juliSt);
                        addMarkerBubble(nowlatLng, i, "" + juliSt);
                    }
                }
                double speed = allpointChartList.get(i).getSpeed();
                mGradientHelper.speedColor(speed);
                polyline.add(nowlatLng);
            }
            polyline.width(15);
            polyline.useGradient(true);
            List<Integer> trackColorList = new ArrayList<Integer>();
            trackColorList = mGradientHelper.getTrackColorList();
            Log.e("size----", "" + allpointChartList.size() + "     " + trackColorList.size());
            Log.e("size----", "" + allpointChartList.get(allpointChartList.size() - 1).getmLatLng());
            polyline.colorValues(trackColorList);
            polyline.zIndex(10);
            mBaiduMap.addPolyline(polyline);
        }

        Double cenLng = (maxLng + minLng) / 2;
        Double cenLat = (((maxLat + minLat) / 2 + minLat) / 2 + minLat) / 2;
        LatLng cenpoint = new LatLng(cenLat, cenLng);
        STARTZOOM = GPSUtil.getZoom(maxLng, minLng, maxLat, minLat);
        CameraUpdate u = CameraUpdateFactory.newLatLngZoom(cenpoint, STARTZOOM);
        if (u != null && mBaiduMap != null) {
            mBaiduMap.moveCamera(u);// 以动画方式更新地图状态，动画耗时 300 ms
        }
    }


    /**
     * 显示气泡
     *
     * @param point
     * @param index
     */
    @SuppressLint("NewApi")
    private void addMarkerBubble(LatLng point, int index, String juliSt) {

        // 只有跑步模式显示每公里打点
        if (index == 0 || index == allpointChartList.size() - 1
                || sportindex.equals("1")) {

            View convertView = LayoutInflater.from(context).inflate(
                    R.layout.venues_map_bubble, null);
            TextView tv_bubble = (TextView) convertView
                    .findViewById(R.id.tv_bubble);
            if (index == 0) {
                tv_bubble.setText("");
                tv_bubble.setBackgroundResource(R.drawable.track_start);
            } else if (index == allpointChartList.size() - 1) {
                tv_bubble.setText("");
                tv_bubble.setBackgroundResource(R.drawable.track_end);
            } else {
                tv_bubble.setText("" + juliSt + "km");
                // 只有跑步模式显示打点
                if (!sportindex.equals("1")) {
                    return;
                }
            }
            Bitmap markerIcon = GPSUtil.convertViewToBitmap(convertView);
            if (markerIcon != null) {
                MarkerOptions ooA = new MarkerOptions().position(point)
                        .icon(BitmapDescriptorFactory.fromBitmap(markerIcon))
                        .draggable(false);
                mBaiduMap.addMarker(ooA);
            }
            markerIcon.recycle();
            markerIcon = null;
        }
    }

    /**
     * =================定位======================
     */
    private MapView mMapView;
    private float STARTZOOM = 17.0f;
    private boolean isFirstLoc = true;

    private double longitude_me;
    private double latitude_me;
    protected userThumbShoaUtils muserThumbShoaUtils;
    private AMap mBaiduMap;
    private AMapLocationClient mLocClient;
    private AMapLocationClientOption mLocationOption;
    private BitmapDescriptor realtimeBitmap;
    private Marker overlay;

    /**
     * 初始化定位的SDK
     */
    private void initMapView() {
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.mMapView);
        // 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        mMapView.onCreate(paramBundle);
        mBaiduMap = mMapView.getMap();
        /*
         * 百度地图 UI 控制器
		 */
        UiSettings mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setCompassEnabled(false);// 隐藏指南针
        mUiSettings.setRotateGesturesEnabled(false);// 阻止旋转
        mUiSettings.setZoomControlsEnabled(false);// 隐藏的缩放按钮
        // 设置显示缩放比例
        CameraUpdate msu = CameraUpdateFactory.zoomTo(STARTZOOM);
        mBaiduMap.moveCamera(msu);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapType(Constants.BaiduMapTYPE_NORMAL);

        // 定位初始化
        mLocClient = new AMapLocationClient(this);
        mLocClient.setLocationListener(mLocationListener);

        // 创建一个定位客户端的参数
        // 初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption
                .setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        // 设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        // 设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        // 设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        // 给定位客户端对象设置定位参数
        mLocClient.setLocationOption(mLocationOption);
        // 启动定位
        mLocClient.startLocation();
    }

    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation location) {
            Log.e("map", location.getErrorCode() + "");
            if (location != null && location.getErrorCode() == 0) {

                try {
                    System.err.println("*********************************");
                    if (isFirstLoc) {
                        // --------------------*定自己的位置信息-----------------------------------------------
                        longitude_me = location.getLongitude();
                        latitude_me = location.getLatitude();
                        moveToCenter();
                        mHandler.sendEmptyMessage(1);
                        isFirstLoc = false;
                    }
                } catch (Exception e) {
                }

            }

        }
    };
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //这里用来获取到Service发来的数据
                case 1:
                    getVenuesUsers();
                    break;
                case 2:
                    processLogicLLLL();
                    break;
                case 3:
                    if (!TextUtils.isEmpty(runStartTime)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //需要在子线程中处理的逻辑
                                cacheToDB();
                            }
                        }).start();
                    }
                    CommonUtils.getInstance().setOnDismissDialog(mDialog);
                    break;
                case 4:
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) msg.obj;
                    showDatas(dataMap);
                    break;
            }
        }
    };

    /**
     * 设置中心点的焦点
     */
    private void moveToCenter() {

        LatLng mypoint = new LatLng(latitude_me, longitude_me);
//        CameraUpdate u = CameraUpdateFactory.newLatLngZoom(mypoint, STARTZOOM);
//        if (u != null && mBaiduMap != null) {
//            mBaiduMap.moveCamera(u);// 以动画方式更新地图状态，动画耗时 300 ms
//        }

        // 自定义图标
        if (null == realtimeBitmap) {
            realtimeBitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),
                            R.drawable.icon_myposition_map));
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mypoint);
        markerOptions.icon(realtimeBitmap);
        if (null != overlay) {
            overlay.remove();
        }
        overlay = mBaiduMap.addMarker(markerOptions);

    }

    protected void getVenuesUsers() {
        mhashmap = new HashMap<String, String>();
        mhashmap.put("uid", uid);
        mhashmap.put("token", token);
        mhashmap.put("access_token", access_token);
        mhashmap.put("posid", posid);
        Log.e("TESTLOG", "------------mhashmap------------" + mhashmap);
        new VenuesUsersBusiness(this, mhashmap, new GetVenuesUsersCallback() {
            @Override
            public void afterDataGet(HashMap<String, Object> dataMap) {
                if (dataMap != null) {
                    String status = (String) dataMap.get("status");
                    if (status.equals("200")) {
                        @SuppressWarnings("unchecked")
                        ArrayList<VenuesUsersInfo> mMapList = (ArrayList<VenuesUsersInfo>) dataMap
                                .get("mlist");
                        if (muserThumbShoaUtils == null) {
                            muserThumbShoaUtils = new userThumbShoaUtils(
                                    context, mBaiduMap);
                        }
                        muserThumbShoaUtils.initMyOverlay(mMapList);
                    }
                }
                CommonUtils.getInstance().setClearCacheBackDate(mhashmap,
                        dataMap);

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        try {
            if (mMapView != null) {
                mMapView.onPause();
            }
            super.onPause();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        try {
            if (mMapView != null) {
                mMapView.onResume();
            }
            super.onResume();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLocClient != null)
            mLocClient.stopLocation();// 停止定位
    }

    @Override
    protected void onDestroy() {
        try {
            // 退出时销毁定位
            if (mLocClient != null)
                mLocClient.onDestroy();
            // 关闭定位图层
            mBaiduMap.setMyLocationEnabled(false);
            mBaiduMap.clear();
            mBaiduMap = null;
            mMapView.removeAllViews();
            mMapView.onDestroy();
            mMapView = null;
            mLocationOption = null;
            if (mRecordListDBOpenHelper != null) {
                mRecordListDBOpenHelper.close();
            }
//            if (mTrackListDBOpenHelper != null) {
//                mTrackListDBOpenHelper.close();
//            }
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    private void doBack() {
        finish();
        CommonUtils.getInstance().setPageBackAnim(this);
    }

    /**
     * 重写onkeydown 用于监听返回键
     */
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            doBack();
        }
        return false;
    }
}
