package com.KiwiSports.control.activity;

import java.io.ByteArrayOutputStream;

import com.KiwiSports.R;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.Constants;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapScreenShotListener;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class VenuesAddActivity extends BaseActivity {

    private LinearLayout pagetop_layout_back;
    private TextView pagetop_tv_name;
    private TextView tv_left;
    private TextView tv_right;

    private TextView tv_next;
    private ImageView map_iv_covering;
    private LinearLayout layout_tishi;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pagetop_layout_back:
                doBack();
                break;
            case R.id.tv_next:
                if (lefttopLatLng != null) {
//                    mBaiduMap.getMapScreenShot(callback);
                    Intent intent = new Intent(context, VenuesAddNextActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("address", address + "");
                    intent.putExtra("top_left_x", lefttopLatLng.longitude + "");
                    intent.putExtra("top_left_y", lefttopLatLng.latitude + "");
                    intent.putExtra("bottom_right_x", rightbottomLatLng.longitude
                            + "");
                    intent.putExtra("bottom_right_y", rightbottomLatLng.latitude
                            + "");
                    startActivity(intent);
                    CommonUtils.getInstance().setPageIntentAnim(intent, context);
                } else if (lefttopLatLng == null) {
                    CommonUtils.getInstance().initToast(
                            getString(R.string.venues_add_locattishi));
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.venues_add);
        CommonUtils.getInstance().addActivity(this);
    }

    @Override
    protected void findViewById() {
        pagetop_layout_back = (LinearLayout) findViewById(R.id.pagetop_layout_back);
        pagetop_tv_name = (TextView) findViewById(R.id.pagetop_tv_name);
        map_iv_covering = (ImageView) findViewById(R.id.map_iv_covering);
        tv_left = (TextView) findViewById(R.id.tv_left);
        tv_right = (TextView) findViewById(R.id.tv_right);
        tv_next = (TextView) findViewById(R.id.tv_next);
        layout_tishi = (LinearLayout) findViewById(R.id.layout_tishi);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void setListener() {
        pagetop_layout_back.setOnClickListener(this);
        tv_next.setOnClickListener(this);
        layout_tishi.setVisibility(View.GONE);
        layout_tishi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layout_tishi.setVisibility(View.GONE);
                return false;
            }
        });
        pagetop_tv_name.setText(getString(R.string.venues_title));
    }

    @Override
    protected void processLogic() {
        initMapView();
    }

//	private ProgressDialog mDialog;
//
//	private void showDilag() {
//		try {
//			if (mDialog == null) {
//				mDialog = CommonUtils.getInstance().createLoadingDialog(this);
//			} else {
//				mDialog.show();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

    /**
     * =================定位======================
     */
    private MapView mMapView;
    private AMap mBaiduMap;
    private AMapLocationClient mLocClient;
    private AMapLocationClientOption mLocationOption;
    private float STARTZOOM = 17.0f;
    private boolean isFirstLoc = true;

    private double longitude_me;
    private double latitude_me;
    private LatLng lefttopLatLng;
    private LatLng rightbottomLatLng;

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

        geocoderSearch = new GeocodeSearch(context);
        geocoderSearch
                .setOnGeocodeSearchListener(new OnGeocodeSearchListener() {

                    // 逆地理转换：经纬度转换成地址
                    @Override
                    public void onRegeocodeSearched(RegeocodeResult result,
                                                    int rCode) {
                        // TODO Auto-generated method stub
                        address = result.getRegeocodeAddress()
                                .getFormatAddress();
                        Log.e("venusAdd", address);
                        getSiteLocation();
                    }

                    @Override
                    public void onGeocodeSearched(GeocodeResult arg0, int arg1) {
                        // TODO Auto-generated method stub
                        address = arg0.getGeocodeAddressList().get(0)
                                .getFormatAddress();
                        Log.e("venusAdd", "--onGeocodeSearched===" + address);
                    }
                });
        // 地图状态改变相关监听
        mBaiduMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                // VisibleRegion visibleRegion = mBaiduMap.getProjection()
                // .getVisibleRegion(); // 获取可视区域、
                // LatLng farLeft = visibleRegion.farLeft;//可视区域左上角
                // LatLng farRight = visibleRegion.farRight;//可视区域右上角
                // LatLng nearLeft = visibleRegion.nearLeft;//可视区域左下角
                // LatLng nearRight = visibleRegion.nearRight;//可视区域右下角
                // LatLngBounds latLngBounds = visibleRegion.latLngBounds;//
                // 获取可视区域的Bounds
                Log.e("venusAdd", "cameraPosition----" + cameraPosition.bearing + "  " + cameraPosition.tilt + "  " + cameraPosition.describeContents());
                LatLng target = cameraPosition.target;
                if (target != null) {
                    LatLonPoint myLatLng = new LatLonPoint(target.latitude,
                            target.longitude);
                    setGeocoderSearch(myLatLng);
                }

            }
        });
    }

    //    Bitmap temBitmap_BG;
//    OnMapScreenShotListener callback = new OnMapScreenShotListener() {
//        /**
//         * 地图截屏回调接口
//         *
//         * @param snapshot
//         *            截屏返回的 bitmap 数据
//         */
//        @Override
//        public void onMapScreenShot(Bitmap snapshot) {
//            // TODO Auto-generated method stub
//            temBitmap_BG = snapshot;
//            minitHandler.sendEmptyMessage(SET);
//        }
//
//    };
    private final int SET = 2;
    @SuppressLint("HandlerLeak")
    Handler minitHandler = new Handler() {
        @SuppressLint("NewApi")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SET:
//				byte[] bitmapByte = null;
//				try {
//					ByteArrayOutputStream baos = new ByteArrayOutputStream();
//					temBitmap_BG.compress(Bitmap.CompressFormat.JPEG, 70, baos);
//					bitmapByte = baos.toByteArray();
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//				CommonUtils.getInstance().setOnDismissDialog(mDialog);
                    break;
            }
        }
    };
    private String address;
    private BitmapDescriptor realtimeBitmap;
    private Marker overlay;
    private GeocodeSearch geocoderSearch;
    // 声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {

        @Override
        public void onLocationChanged(AMapLocation location) {
            if (location != null && location.getErrorCode() == 0) {
                try {
                    System.err.println("*********************************");
                    if (isFirstLoc) {
                        // --------------------*定自己的位置信息-----------------------------------------------
                        longitude_me = location.getLongitude();
                        latitude_me = location.getLatitude();
                        address = location.getAddress();
                        if (TextUtils.isEmpty(address)) {
                            address = location.getCity();
                        }
                        // ---------1.首先判断当前城市是否有，没有则默认距离北京坐标，我的位置为当前定位-----------------------------
                        moveToCenter();
                        LatLonPoint myLatLng = new LatLonPoint(latitude_me,
                                longitude_me);
                        setGeocoderSearch(myLatLng);
                        isFirstLoc = false;
                        layout_tishi.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                } finally {
                }
            }

        }
    };

    private void setGeocoderSearch(LatLonPoint myLatLng) {
        if (geocoderSearch != null) {
            // 发起反地理编码请求(经纬度->地址信息)
            RegeocodeQuery rQuery = new RegeocodeQuery(myLatLng, 200,
                    GeocodeSearch.AMAP);
            // 设置反地理编码位置坐标
            geocoderSearch.getFromLocationAsyn(rQuery);
        }
    }

    private void getSiteLocation() {
        lefttopLatLng = getSite(1);
        if (lefttopLatLng != null) {
            Log.e("TESTLOG", "左上角经度 x:" + lefttopLatLng.longitude + " 左上角纬度 y:"
                    + lefttopLatLng.latitude);
            tv_left.setText(lefttopLatLng.latitude + ","
                    + lefttopLatLng.longitude);
        }
        rightbottomLatLng = getSite(4);
        if (rightbottomLatLng != null) {
            Log.e("TESTLOG", "右下角经度 x:" + rightbottomLatLng.longitude
                    + "右下角纬度 y:" + rightbottomLatLng.latitude);
            tv_right.setText(rightbottomLatLng.latitude + ","
                    + rightbottomLatLng.longitude);
        }
    }

    /**
     * 获取坐标点
     *
     * @param type
     * @return
     */
    private LatLng getSite(int type) {
        Point pt = new Point();
        int imgwidth = (map_iv_covering.getMeasuredWidth() - ConfigUtils.getInstance().dip2px(context, 30));
        int imgheight = (map_iv_covering.getMeasuredHeight() - ConfigUtils.getInstance().dip2px(context, 15));
        if (type == 1) {
            // 左上角
            pt.x = mMapView.getMeasuredWidth() / 2 - imgwidth / 2;
            pt.y = mMapView.getMeasuredHeight() / 2 - imgheight / 2;
        }
        if (type == 2) {
            // 左下角
            pt.x = mMapView.getMeasuredWidth() / 2 - imgwidth / 2;
            pt.y = mMapView.getMeasuredHeight() / 2 + imgheight / 2;
        }
        if (type == 3) {
            // 右上角
            pt.x = mMapView.getMeasuredWidth() / 2 + imgwidth / 2;
            pt.y = mMapView.getMeasuredHeight() / 2 - imgheight / 2;
        }
        if (type == 4) {
            // 右下角
            pt.x = mMapView.getMeasuredWidth() / 2 + imgwidth / 2;
            pt.y = mMapView.getMeasuredHeight() / 2 + imgheight / 2;
        }
        LatLng ll = null;
        if (mBaiduMap != null && mBaiduMap.getProjection() != null)
            ll = mBaiduMap.getProjection().fromScreenLocation(pt);

        return ll;

    }

    /**
     * 设置中心点的焦点
     */
    private void moveToCenter() {
        LatLng mypoint = new LatLng(latitude_me, longitude_me);
        CameraUpdate u = CameraUpdateFactory.newLatLngZoom(mypoint, STARTZOOM);
        if (u != null && mBaiduMap != null) {
            mBaiduMap.moveCamera(u);// 以动画方式更新地图状态，动画耗时 300 ms
        }
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

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState
        // (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
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
