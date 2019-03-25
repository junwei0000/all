package com.longcheng.lifecareplantv.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:28:06
 * @Description 类描述：获取定位
 */
public class LocationUtils {
    private static LocationUtils instance;

    private LocationUtils() {
    }

    public static synchronized LocationUtils getInstance() {
        if (instance == null) {
            instance = new LocationUtils();
        }
        return instance;
    }

    /**
     * 获取经纬度
     *
     * @param context
     * @return
     */
    public double[] getLngAndLat(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) { //从gps获取经纬度
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork(context);
            }
        } else { //从网络获取经纬度
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        return new double[]{latitude, longitude};
    } //从网络获取经纬度

    private double[] getLngAndLatWithNetwork(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        return new double[]{latitude, longitude};
    }

    LocationListener locationListener = new LocationListener() { // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        } // Provider被enable时触发此函数，比如GPS被打开

        @Override
        public void onProviderEnabled(String provider) {
        } // Provider被disable时触发此函数，比如GPS被关闭

        @Override
        public void onProviderDisabled(String provider) {
        } //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发

        @Override
        public void onLocationChanged(Location location) {
        }
    };
}
