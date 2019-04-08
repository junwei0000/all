package com.longcheng.lifecareplan.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.longcheng.lifecareplan.base.ExampleApplication;
import com.longcheng.lifecareplan.widget.jswebview.browse.CallBackFunction;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author 作者：zoc
 * @date 创建时间：2016-9-26 下午3:28:06
 * @Description 类描述：获取定位
 */
public class LocationUtils {

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
                Log.e("getLngAndLat", "" + location.toString());
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork(context);
            }
        } else { //从网络获取经纬度
            return getLngAndLatWithNetwork(context);
        }
        return new double[]{latitude, longitude};
    }

    private double[] getLngAndLatWithNetwork(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, new LocationListener() {
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
        });
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.e("getLngAndLat", "" + location.toString());
        }
        return new double[]{latitude, longitude};
    }

    /**
     * 根据经纬度获取地理位置
     *
     * @param mContext
     * @param latitude
     * @param longitude
     * @return
     */
    public String getAddress(Context mContext, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,
                    longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                Log.e("getLngAndLat", "" + address.getAddressLine(0));
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

