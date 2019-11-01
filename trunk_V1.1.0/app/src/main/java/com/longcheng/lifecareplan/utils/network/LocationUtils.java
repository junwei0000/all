package com.longcheng.lifecareplan.utils.network;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;


import java.io.IOException;
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

    public double[] getLngAndLatWithNetwork(Context context) {
        double latitude = 0.0;
        double longitude = 0.0;
        if (!getNetWorkStatus(context)) {
            return new double[]{latitude, longitude};
        }
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return new double[]{latitude, longitude};
        }
        if (locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {


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
                Log.e("getLngAndLat", +latitude + "  " + longitude);
            }
        }
        return new double[]{latitude, longitude};
    }

    /**
     * 获取网络状态 TRUE 有网
     *
     * @param context
     * @return
     */
    public boolean getNetWorkStatus(Context context) {
        boolean status = false;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        boolean isWifiConn = networkInfo.isConnected();
        networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean isMobileConn = networkInfo.isConnected();
        if (isMobileConn || isWifiConn) {
            status = true;
        }
        return status;
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
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            if (geocoder == null) {
                return "";
            }
            List<Address> addresses = geocoder.getFromLocation(latitude,
                    longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                Log.e("getLngAndLat", "" + address.getAddressLine(0));
                return address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据经纬度获取地理位置
     *
     * @param mContext
     * @return
     */
    public String getAddressCity(Context mContext) {
        try {
            double[] mLngAndLat = getLngAndLatWithNetwork(mContext);
            double latitude = mLngAndLat[0];
            double longitude = mLngAndLat[1];
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            if (geocoder == null) {
                return "";
            }
            List<Address> addresses = geocoder.getFromLocation(latitude,
                    longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                return addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}

