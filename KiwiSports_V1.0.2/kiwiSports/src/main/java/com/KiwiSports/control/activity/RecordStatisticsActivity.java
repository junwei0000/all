package com.KiwiSports.control.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.business.RecordDetailBusiness;
import com.KiwiSports.business.RecordDetailBusiness.GetRecordDetailCallback;
import com.KiwiSports.business.VenuesUsersBusiness;
import com.KiwiSports.business.VenuesUsersBusiness.GetVenuesUsersCallback;
import com.KiwiSports.control.adapter.MainPropertyAdapter;
import com.KiwiSports.control.adapter.RecordStaticsAdapter;
import com.KiwiSports.control.mp.ChartDataAdapter;
import com.KiwiSports.control.mp.ChartItem;
import com.KiwiSports.control.mp.LineChartItem;
import com.KiwiSports.model.DistanceCountInfo;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.model.MainSportInfo;
import com.KiwiSports.model.RecordInfo;
import com.KiwiSports.model.VenuesUsersInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;
import com.KiwiSports.utils.DatesUtils;
import com.KiwiSports.utils.GPSUtil;
import com.KiwiSports.utils.MyGridView;
import com.KiwiSports.utils.MyListView;
import com.KiwiSports.utils.MyScrollView;
import com.KiwiSports.utils.PriceUtils;
import com.KiwiSports.utils.parser.MainsportParser;
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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author 作者：jun
 * @date 创建时间：2016-1-15 下午6:53:43
 * @Description 类描述：统计
 */
public class RecordStatisticsActivity extends BaseActivity {


    private LinearLayout pagetop_layout_back;
    private TextView pagetop_tv_name, tv_allDistance;
    private PieChart mChart;
    DistanceCountInfo mDistanceCountInfo;
    private ArrayList<DistanceCountInfo> mtotalDislist;
    private MyGridView gv_statics;

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
        setContentView(R.layout.record_statistics);
        CommonUtils.getInstance().addActivity(this);
    }

    @Override
    protected void findViewById() {
        pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
        pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
        mChart = (PieChart) findViewById(R.id.mPieChart);
        tv_allDistance = (TextView) findViewById(R.id.tv_allDistance);
        CommonUtils.getInstance().setTextViewTypeface(context, tv_allDistance);
        TextView tv_allDistanceunit = (TextView) findViewById(R.id.tv_allDistanceunit);
        CommonUtils.getInstance().setTextViewTypeface(context, tv_allDistanceunit);

        gv_statics = (MyGridView) findViewById(R.id.gv_statics);
    }

    @Override
    protected void setListener() {
        pagetop_layout_back.setOnClickListener(this);
        pagetop_tv_name.setText("KIWI运动统计");
        Intent intent = getIntent();
        mDistanceCountInfo = (DistanceCountInfo) intent.getExtras().getSerializable("mDistanceCountInfo");

        if (mDistanceCountInfo != null) {
            mtotalDislist = mDistanceCountInfo.getMtotalDislist();
            tv_allDistance.setText(mDistanceCountInfo.getTotalDistances());
        }

    }


    @Override
    protected void processLogic() {
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setCenterText("");
        mChart.setDrawCenterText(false);//是否显示中间内容

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

            }

            @Override
            public void onNothingSelected() {

            }
        });

        setData();

        mChart.animateY(800, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setTextColor(Color.WHITE);
        l.setEnabled(false);//图例不显示
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        showStatics();
    }

    private void showStatics() {
        RecordStaticsAdapter mAdapter = new RecordStaticsAdapter(context, mtotalDislist);
        gv_statics.setAdapter(mAdapter);
    }

    private void setData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        // drawn above each other.
        for (int i = 0; i < mtotalDislist.size(); i++) {
            String mDistances = PriceUtils.getInstance().gteDividePrice(mtotalDislist.get(i).getDistance(), "1000");
            mDistances = PriceUtils.getInstance().getPriceTwoDecimal(Double.valueOf(mDistances),2);
            mDistances = PriceUtils.getInstance().seePrice(mDistances);
            yVals1.add(new Entry(Float.valueOf(mDistances), i));
        }

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < mtotalDislist.size(); i++) {
            xVals.add(mtotalDislist.get(i).getSportname());
        }
        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(2f);//间隔
        Typeface face = CommonUtils.getInstance().getAssetTypeFace(context);
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTypeface(face);
        dataSet.setSelectionShift(0f);//点击扩散

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.recordstatics_1_color));
        colors.add(getResources().getColor(R.color.recordstatics_2_color));
        colors.add(getResources().getColor(R.color.recordstatics_3_color));
        colors.add(getResources().getColor(R.color.recordstatics_4_color));
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
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
