/**
 * 
 */
package com.bestdo.bestdoStadiums.control.map;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

/**
 * @author 作者：zoc
 * @date 创建时间：2017-4-9 上午11:43:29
 * @Description 类描述：
 */
public class LocationUtils {

	private LocationClient mLocationClient;
	private LocationClientOption option;
	private BDLocationListener myListener = new MyLocationListener();

	Handler mHandler;
	int mHandlerId;

	public LocationUtils(Context mContext, Handler mHandler, int mHandlerId) {
		super();
		this.mHandler = mHandler;
		this.mHandlerId = mHandlerId;
		mLocationClient = new LocationClient(mContext.getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		new Thread(new Runnable() {

			@Override
			public void run() {
				InitLocation();// 设置定位参数
			}
		}).start();
	}

	private void InitLocation() {
		option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// setScanSpan < 1000 则为 app主动请求定位；
		// setScanSpan>=1000,则为定时定位模式（setScanSpan的值就是定时定位的时间间隔））
		// option.disableCache(true);// 禁止启用缓存定位
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			String cityString = "";
			try {
				cityString = location.getCity();
				if (!TextUtils.isEmpty(cityString)) {
					cityString = cityString.substring(0, cityString.length() - 1);
				}
				System.err.println("cityString==" + cityString);
			} catch (Exception e) {
			}
			if (TextUtils.isEmpty(cityString)) {
				cityString = "北京";
			}
			Message message = new Message();
			message.what = mHandlerId;
			message.obj = cityString;
			mHandler.sendMessage(message);
			message = null;
		}

		public void onReceivePoi(BDLocation arg0) {

		}

	}

	public void onDestory() {
		try {
			// 退出时销毁定位
			if (mLocationClient != null)
				mLocationClient.stop();
		} catch (Exception e) {
		}
		option = null;
		myListener = null;
	}
}
