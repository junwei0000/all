package com.KiwiSports.control.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.KiwiSports.R;
import com.KiwiSports.control.activity.MainStartActivity;
import com.KiwiSports.control.locationService.GradientHelper;
import com.KiwiSports.utils.GPSUtil;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;

/**
 * 轨迹追踪
 */
@SuppressLint({"NewApi", "UseSparseArrays", "SimpleDateFormat"})
public class TrackUploadFragment extends Fragment {


    private View view = null;

    public boolean isInUploadFragment = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater
                .inflate(R.layout.fragment_trackupload, container, false);
        return view;
    }


    /**
     * 每次轨迹后初始化
     */
    public void initDates() {
        sum_distance = 0.0;
        temdistance = 0;
        isFirstLoc = true;
        nowlatLng = null;
        beforelatLng = null;

        mpolylineList.clear();
        mshowPolylineList.clear();
        polylineOptions = new PolylineOptions();
        mGradientHelper.getTrackColorList().clear();
        mBubbleMap.clear();
        isInUploadFragment = false;
        // 每次清除后没初始化当前位置
        if (overlay != null) {
            overlay.remove();
            overlay = null;
        }

    }

    public int sportindex = 0;
    public double temdistance;// 两点之间的距离
    public double sum_distance = 0.0;// 总距离
    public boolean isFirstLoc = true;//
    public LatLng beforelatLng;
    LatLng nowlatLng;
    public LatLng mEndpoint;
    HashMap<Integer, Integer> mBubbleMap = new HashMap<Integer, Integer>();
    public float STARTZOOM = 18.0f;
    /**
     * 标记开始图标位置
     */
    public LatLng startlatLng;

    /**
     * 显示实时轨迹
     *
     * @param userslatLng
     * @param latLngDashedStatus 该坐标是否是虚点
     */
    public void showRealtimeTrack(LatLng userslatLng, String latLngDashedStatus, double speed_) {

        if (isFirstLoc) {
            beforelatLng = userslatLng;
            startlatLng = userslatLng;
            showBubbleView(userslatLng, 0);
        } else {
            nowlatLng = userslatLng;
            showBubbleView(userslatLng, 1);
        }

        if (isFirstLoc || rebookstatus(userslatLng)) {
            double juliString = GPSUtil.DistanceOfTwoPoints(
                    beforelatLng.latitude, beforelatLng.longitude,
                    userslatLng.latitude, userslatLng.longitude);
            /**
             * ************ 虚线不记录距离，但是划线*************
             */
            if (!latLngDashedStatus.equals("latLngDashedStatus_true")) {
                temdistance = juliString;
                sum_distance = sum_distance + juliString;
            }
            drawRealtimePoint(userslatLng, speed_);
        }
        if (!isFirstLoc) {
            beforelatLng = nowlatLng;
        }
        isFirstLoc = false;
    }

    /**
     * 同一个定位不绘制
     *
     * @param userslatLng
     * @return
     */
    private boolean rebookstatus(LatLng userslatLng) {
        boolean status = false;
        if (isInUploadFragment) {
            if (beforelatLng != userslatLng) {
                status = true;
            }
        }
        return status;
    }


    ArrayList<PolylineOptions> mpolylineList = new ArrayList<PolylineOptions>();
    ArrayList<Polyline> mshowPolylineList = new ArrayList<Polyline>();
    GradientHelper mGradientHelper = new GradientHelper();
    PolylineOptions polylineOptions = new PolylineOptions();

    /**
     * 绘制实时点
     *
     * @param userslatLng
     * @param speed_      速度
     */
    public void drawRealtimePoint(LatLng userslatLng, double speed_) {
        /**
         * 轨迹点大于5000时重新生成PolylineOptions，防止轨迹点个数大于高德规定的界限无法显示问题
         */
        List<LatLng> mlist = polylineOptions.getPoints();
        if (mlist != null && mlist.size() > 5000) {
            polylineOptions = new PolylineOptions();
            mpolylineList.add(polylineOptions);
        }
        /**
         * 第一次添加
         */
        if (mpolylineList != null && mpolylineList.size() == 0) {
            mpolylineList.add(polylineOptions);
        }
        double speed = speed_;
        mGradientHelper.speedColor(speed);
        polylineOptions.add(userslatLng);
        polylineOptions.width(15);
        polylineOptions.useGradient(true);
        List<Integer> trackColorList = new ArrayList<Integer>();
        trackColorList = mGradientHelper.getTrackColorList();
        polylineOptions.colorValues(trackColorList);
        polylineOptions.zIndex(10);
        /**
         * 每次清除后重新添加轨迹，防止多次添加
         */
        if (mshowPolylineList != null && mshowPolylineList.size() > 0) {
            for (Polyline iterable_element : mshowPolylineList) {
                iterable_element.remove();
                iterable_element = null;
            }
            mshowPolylineList.clear();
        }
        if (mpolylineList != null && mpolylineList.size() > 0) {
            for (int i = 0; i < mpolylineList.size(); i++) {
                Polyline d = MainStartActivity.mBaiduMap
                        .addPolyline(mpolylineList.get(i));
                mshowPolylineList.add(d);
            }
        }
        addMarker(userslatLng);
    }

    public void settoFollowStatus(boolean toFollowStatus_) {
        toFollowStatus = toFollowStatus_;
    }

    /**
     * 是否跟随轨迹
     */
    private boolean toFollowStatus = true;


    private BitmapDescriptor realtimeBitmap;

    public Marker overlay;

    public void setMapupdateStatus(LatLng nowpoint) {
        // 设置中心点
        if (nowpoint != null && toFollowStatus) {
            toFollowStatus = false;
            CameraUpdate u = CameraUpdateFactory.newLatLngZoom(nowpoint,
                    STARTZOOM);
            if (u != null && MainStartActivity.mBaiduMap != null) {
                MainStartActivity.mBaiduMap.moveCamera(u);// 以动画方式更新地图状态，动画耗时
                // 300 ms
            }
        }
    }

    /**
     * 添加地图覆盖物
     */
    public void addMarker(LatLng userslatLng) {

        setMapupdateStatus(userslatLng);
        // 自定义图标
        if (null == realtimeBitmap) {
            realtimeBitmap = BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(),
                            R.drawable.icon_myposition_map));
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(userslatLng);
        markerOptions.visible(true);
        markerOptions.icon(realtimeBitmap);
        if (null != overlay) {
            overlay.remove();
        }
        overlay = MainStartActivity.mBaiduMap.addMarker(markerOptions);

    }

    /**
     * 每隔一公里显示气泡
     */
    public void showBubbleView(LatLng nowlatLng, int i) {
        if (i == 0) {
            addMarkerBubble(nowlatLng, i, "0");
            mBubbleMap.put(0, 0);
        } else {
            int juliSt = (int) sum_distance;
            if (!mBubbleMap.containsKey(juliSt)) {
                mBubbleMap.put(juliSt, juliSt);
                addMarkerBubble(nowlatLng, i, "" + juliSt);
            }
        }
    }

    /**
     * 显示气泡
     *
     * @param point
     * @param index
     */
    @SuppressLint("NewApi")
    public void addMarkerBubble(LatLng point, int index, String juliSt) {

        // 只有跑步模式显示每公里打点
        if (index == 0 || sportindex == 1) {
            View convertView = LayoutInflater.from(MainStartActivity.mActivity)
                    .inflate(R.layout.venues_map_bubble, null);
            TextView tv_bubble = (TextView) convertView
                    .findViewById(R.id.tv_bubble);
            if (index == 0) {
                tv_bubble.setText("");
                tv_bubble.setBackgroundResource(R.drawable.track_start);
            } else {
                tv_bubble.setText("" + juliSt);
            }
            BitmapDescriptor mmorenMarker = BitmapDescriptorFactory
                    .fromView(convertView);
            MarkerOptions ooA = new MarkerOptions().position(point)
                    .icon(mmorenMarker).draggable(false);
            MainStartActivity.mBaiduMap.addMarker(ooA);
        }
    }

    /**
     * 根据地图的限制拆分数据
     *
     * @param targe
     * @param size
     * @return
     */
    public List<List<LatLng>> createList(List<LatLng> targe, int size) {
        List<List<LatLng>> listArr = new ArrayList<List<LatLng>>();
        // 获取被拆分的数组个数
        int arrSize = targe.size() % size == 0 ? targe.size() / size : targe
                .size() / size + 1;
        for (int i = 0; i < arrSize; i++) {
            List<LatLng> sub = new ArrayList<LatLng>();
            // 把指定索引数据放入到list中
            for (int j = i * size; j <= size * (i + 1) - 1; j++) {
                if (j <= targe.size() - 1) {
                    sub.add(targe.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

}
