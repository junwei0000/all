package com.KiwiSports.control.locationService;

import android.content.Intent;
import android.util.Log;

import com.KiwiSports.utils.App;
import com.KiwiSports.utils.Constants;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * <p>
 * 项目名称：LocationServiceDemo 类说明：后台服务定位
 * <p>
 * update: 1. 只有在由息屏造成的网络断开造成的定位失败时才点亮屏幕 2. 利用notification机制增加进程优先级
 * </p>
 */
public class LocationService extends NotiService {

	private AMapLocationClient mLocationClient;
	private AMapLocationClientOption mLocationOption;

	/**
	 * 处理息屏关掉wifi的delegate类
	 */
	private IWifiAutoCloseDelegate mWifiAutoCloseDelegate = new WifiAutoCloseDelegate();

	/**
	 * 记录是否需要对息屏关掉wifi的情况进行处理
	 */
	private boolean mIsWifiCloseable = false;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		applyNotiKeepMech(); // 开启利用notification提高进程优先级的机制
		Log.e("TESTLOG", "NotiService");
		if (mWifiAutoCloseDelegate.isUseful(getApplicationContext())) {
			mIsWifiCloseable = true;
			mWifiAutoCloseDelegate
					.initOnServiceStarted(getApplicationContext());
		}

		startLocation();

		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		unApplyNotiKeepMech();
		stopLocation();
		App.getInstance().savaInfoToSD("LocationService onDestroy");
		super.onDestroy();
	}

	/**
	 * 启动定位
	 */
	void startLocation() {
		stopLocation();

		if (null == mLocationClient) {
			mLocationClient = new AMapLocationClient(
					this.getApplicationContext());
		}

		mLocationOption = new AMapLocationClientOption();
		// 使用连续
		mLocationOption.setOnceLocation(false);
		mLocationOption.setLocationCacheEnable(false);
		// 每2秒定位一次
		mLocationOption.setInterval(4000);
		// 地址信息
		mLocationOption.setNeedAddress(true);
		mLocationClient.setLocationOption(mLocationOption);
		mLocationClient.setLocationListener(locationListener);
		mLocationClient.startLocation();
	}

	/**
	 * 停止定位
	 */
	void stopLocation() {
		if (null != mLocationClient) {
			mLocationClient.stopLocation();
		}
	}

	AMapLocationListener locationListener = new AMapLocationListener() {
		@Override
		public void onLocationChanged(AMapLocation aMapLocation) {
			if(aMapLocation!=null){
				sendLocationBroadcast(aMapLocation);
			}
			if (!mIsWifiCloseable) {
				return;
			}
			if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
				mWifiAutoCloseDelegate.onLocateSuccess(
						getApplicationContext(),
						PowerManagerUtil.getInstance().isScreenOn(
								getApplicationContext()), NetUtil.getInstance()
								.isMobileAva(getApplicationContext()));
			} else {
				mWifiAutoCloseDelegate.onLocateFail(
						getApplicationContext(),
						aMapLocation.getErrorCode(),
						PowerManagerUtil.getInstance().isScreenOn(
								getApplicationContext()), NetUtil.getInstance()
								.isWifiCon(getApplicationContext()));
			}

		}

		/**
		 * 记录信息并发送广播
		 * 
		 * @param aMapLocation
		 */
		private void sendLocationBroadcast(AMapLocation aMapLocation) {
			Intent intent = new Intent(Constants.RECEIVER_ACTION);
			int currentAccuracy = (int) aMapLocation.getAccuracy();
			double longitude_me = aMapLocation.getLongitude();
			double latitude_me = aMapLocation.getLatitude();
			String address = aMapLocation.getAddress();
			int mLocType = aMapLocation.getErrorCode();
			intent.putExtra("currentAccuracy", currentAccuracy);
			intent.putExtra("longitude_me", longitude_me);
			intent.putExtra("latitude_me", latitude_me);
			intent.putExtra("address", address);
			intent.putExtra("mLocType", mLocType);
			sendBroadcast(intent);
		}

	};

}
