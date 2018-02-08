package com.KiwiSports.control.view;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.KiwiSports.R;
import com.KiwiSports.control.activity.MainStartActivity;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.ConfigUtils;
import com.KiwiSports.utils.PriceUtils;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.OnStopTraceListener;

/**
 * 轨迹追踪
 */
@SuppressLint("NewApi")
public class TrackUploadFragment extends Fragment {

	/**
	 * 开启轨迹服务监听器
	 */
	public static OnStartTraceListener startTraceListener = null;

	/**
	 * 停止轨迹服务监听器
	 */
	public static OnStopTraceListener stopTraceListener = null;

	/**
	 * 采集周期（单位 : 秒）
	 */
	private int gatherInterval = 10;

	/**
	 * 打包周期（单位 : 秒）
	 */
	private int packInterval = 15;

	/**
	 * 图标
	 */
	private BitmapDescriptor realtimeBitmap;

	public Overlay overlay = null;
	public Overlay polylineoverlay;
	// 覆盖物
	public OverlayOptions overlayOptions;
	// 路线覆盖物
	public PolylineOptions polyline = null;

	public List<LatLng> showpointList = new ArrayList<LatLng>();

	protected boolean isTraceStart = false;

	/**
	 * 刷新地图线程(获取实时点)
	 */
	public RefreshThread refreshThread = null;

	private View view = null;

	public boolean isInUploadFragment = true;

	// public static boolean isRegister = false;

	// public static PowerManager pm = null;
	//
	// public static WakeLock wakeLock = null;

	// private PowerReceiver powerReceiver = new PowerReceiver();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_trackupload, container, false);

		// 初始化监听器
		initListener();

		// 设置采集周期
		setInterval();

		// 设置http请求协议类型
		setRequestType();

		return view;
	}

	public OverlayOptions getOverlayOptions() {
		return overlayOptions;
	}

	public void setOverlayOptions(OverlayOptions overlayOptions) {
		this.overlayOptions = overlayOptions;
	}

	public PolylineOptions getPolyline() {
		return polyline;
	}

	public void setPolyline(PolylineOptions polyline) {
		this.polyline = polyline;
	}

	/**
	 * 初始化监听器
	 */
	private void initListener() {
		// 初始化开启轨迹服务监听器
		initOnStartTraceListener();

		// 初始化停止轨迹服务监听器
		initOnStopTraceListener();
	}

	/**
	 * 开启轨迹服务
	 * 
	 */
	public void startTrace() {
		// 通过轨迹服务客户端client开启轨迹服务
		MainStartActivity.client.startTrace(MainStartActivity.trace, startTraceListener);

		if (!MonitorService.isRunning) {
			// 开启监听service
			MonitorService.isCheck = true;
			MonitorService.isRunning = true;
		}
		// if (!isRegister) {
		// if (null == pm) {
		// pm = (PowerManager) MainStartActivity.mContext
		// .getSystemService(Context.POWER_SERVICE);
		// }
		// if (null == wakeLock) {
		// wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
		// "track upload");
		// }
		// IntentFilter filter = new IntentFilter();
		// filter.addAction(Intent.ACTION_SCREEN_OFF);
		// filter.addAction(Intent.ACTION_SCREEN_ON);
		// MainStartActivity.mContext.registerReceiver(powerReceiver, filter);
		// isRegister = true;
		// }
	}

	/**
	 * 停止轨迹服务
	 * 
	 */
	public void stopTrace() {

		// 停止监听service
		MonitorService.isCheck = false;
		MonitorService.isRunning = false;

		// 通过轨迹服务客户端client停止轨迹服务

		MainStartActivity.client.stopTrace(MainStartActivity.trace, stopTraceListener);
		// if (isRegister) {
		// try {
		// MainStartActivity.mContext.unregisterReceiver(powerReceiver);
		// isRegister = false;
		// } catch (Exception e) {
		// // TODO: handle
		// }
		//
		// }
	}

	/**
	 * 设置采集周期和打包周期
	 * 
	 */
	private void setInterval() {
		if (MainStartActivity.client != null)
			MainStartActivity.client.setInterval(gatherInterval, packInterval);
	}

	/**
	 * 设置请求协议
	 */
	protected static void setRequestType() {
		int type = 0;
		if (MainStartActivity.client != null)
			MainStartActivity.client.setProtocolType(type);
	}

	/**
	 * 查询实时轨迹
	 * 
	 * @param v
	 */
	private void queryRealtimeLoc() {
		if (MainStartActivity.client != null)
			MainStartActivity.client.queryRealtimeLoc(MainStartActivity.serviceId, MainStartActivity.entityListener);
	}

	/**
	 * 查询entityList
	 */
	@SuppressWarnings("unused")
	private void queryEntityList() {
		// // entity标识列表（多个entityName，以英文逗号"," 分割）
		String entityNames = MainStartActivity.entityName;
		// 属性名称（格式为 : "key1=value1,key2=value2,....."）
		String columnKey = "key1=value1,key2=value2";
		// 返回结果的类型（0 : 返回全部结果，1 : 只返回entityName的列表）
		int returnType = 0;
		// 活跃时间（指定该字段时，返回从该时间点之后仍有位置变动的entity的实时点集合）
		// int activeTime = (int) (System.currentTimeMillis() / 1000 - 30);
		int activeTime = 0;
		// 分页大小
		int pageSize = 10;
		// 分页索引
		int pageIndex = 1;

		MainStartActivity.client.queryEntityList(MainStartActivity.serviceId, entityNames, columnKey, returnType,
				activeTime, pageSize, pageIndex, MainStartActivity.entityListener);
	}

	/**
	 * 初始化OnStartTraceListener
	 */
	private void initOnStartTraceListener() {
		// 初始化startTraceListener
		startTraceListener = new OnStartTraceListener() {

			// 开启轨迹服务回调接口（arg0 : 消息编码，arg1 : 消息内容，详情查看类参考）
			public void onTraceCallback(int arg0, String arg1) {
				// TODO Auto-generated method stub
				// showMessage("开启轨迹服务回调接口消息 [消息编码 : " + arg0 + "，消息内容 : " +
				// arg1 + "]", Integer.valueOf(arg0));
				if (0 == arg0 || 10006 == arg0 || 10008 == arg0 || 10009 == arg0) {
					isTraceStart = true;
					// startRefreshThread(true);
				}
			}

			// 轨迹服务推送接口（用于接收服务端推送消息，arg0 : 消息类型，arg1 : 消息内容，详情查看类参考）
			public void onTracePushCallback(byte arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("推送消息 : " + arg1);
				if (0x03 == arg0 || 0x04 == arg0) {
					try {
						JSONObject dataJson = new JSONObject(arg1);
						if (null != dataJson) {
							String mPerson = dataJson.getString("monitored_person");
							String action = dataJson.getInt("action") == 1 ? "进入" : "离开";
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
							String date = sdf.format(new Date(dataJson.getInt("time") * 1000));
							long fenceId = dataJson.getLong("fence_id");
							// showMessage("监控对象[" + mPerson + "]于" + date +
							// " [" + action + "][" + fenceId + "号]围栏",
							// null);
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						// showMessage("轨迹服务推送接口消息 [消息类型 : " + arg0 + "，消息内容 : "
						// + arg1 + "]", null);
					}
				} else {
					// showMessage("轨迹服务推送接口消息 [消息类型 : " + arg0 + "，消息内容 : " +
					// arg1 + "]", null);
				}
			}

		};
	}

	/**
	 * 初始化OnStopTraceListener
	 */
	private void initOnStopTraceListener() {
		// 初始化stopTraceListener
		stopTraceListener = new OnStopTraceListener() {

			// 轨迹服务停止成功
			public void onStopTraceSuccess() {
				// TODO Auto-generated method stub
				// showMessage("停止轨迹服务成功", Integer.valueOf(1));
				isTraceStart = false;
				startRefreshThread(false);
				MainStartActivity.client.onDestroy();
			}

			// 轨迹服务停止失败（arg0 : 错误编码，arg1 : 消息内容，详情查看类参考）
			public void onStopTraceFailed(int arg0, String arg1) {
				// TODO Auto-generated method stub
				// showMessage("停止轨迹服务接口消息 [错误编码 : " + arg0 + "，消息内容 : " + arg1
				// + "]", null);
				startRefreshThread(false);
			}
		};
	}

	protected class RefreshThread extends Thread {

		protected boolean refresh = true;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Looper.prepare();
			while (refresh) {
				// 查询实时位置
				queryRealtimeLoc();

				try {
					Thread.sleep(gatherInterval * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("线程休眠失败");
				}
			}
			Looper.loop();
		}
	}

	// -------------------------计算距离--------------------------

	public void initDates() {
		sum_distance = 0.0;
		temdistance = 0;
		isFirstLoc = true;
		nowlatLng = null;
		beforelatLng = null;
		showpointList.clear();
		mBubbleMap.clear();
		isInUploadFragment = false;
		// 初始化路线
		if (polylineoverlay != null) {
			polylineoverlay.remove();
			polylineoverlay = null;
		}
		// 每次清除后没初始化当前位置
		if (overlay != null) {
			overlay.remove();
			overlay = null;
		}

	}

	public double temdistance;// 两点之间的距离
	public double sum_distance = 0.0;// 总距离
	public boolean isFirstLoc = true;//
	public LatLng beforelatLng;
	LatLng nowlatLng;
	public LatLng mEndpoint;
	HashMap<Integer, Integer> mBubbleMap = new HashMap<Integer, Integer>();
	public float STARTZOOM = 19.0f;
	/**
	 * 每隔一分钟初始化一下缩放比例
	 */
	public boolean zoomstaus = true;
	/**
	 * 标记开始图标位置
	 */
	public LatLng startlatLng;

	/**
	 * 显示实时轨迹
	 * 
	 * @param realtimeTrack
	 */
	public void showRealtimeTrack(LatLng userslatLng) {

		if (null == refreshThread || !refreshThread.refresh) {
			return;
		}
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("//-----------------------------------------------" + "\n");
		stringBuffer.append("CoordinateConverter beforelatLng-----beforelatLng=" + beforelatLng + "\n");

		if (isFirstLoc) {
			beforelatLng = userslatLng;
			startlatLng = userslatLng;
			showBubbleView(userslatLng, 0);
			stringBuffer.append("isFirstLoc-----userslatLng=" + beforelatLng + "\n");
		} else {
			nowlatLng = userslatLng;
			showBubbleView(userslatLng, 1);
			stringBuffer.append("nowlatLng=" + nowlatLng + "\n");
		}

		if (isFirstLoc || rebookstatus(userslatLng)) {
			double juliString = ConfigUtils.DistanceOfTwoPoints(beforelatLng.latitude, beforelatLng.longitude,
					userslatLng.latitude, userslatLng.longitude);
			if (haveUserLocStatus(juliString)) {

				temdistance = juliString;
				sum_distance = sum_distance + juliString;
				stringBuffer.append("juliString=" + juliString + ";   sum_distance=" + sum_distance + "\n");
				showpointList.add(userslatLng);
				drawRealtimePoint(userslatLng);
				// savaInfoToSD(MainStartActivity.mActivity, stringBuffer);
			} else {
				return;
			}
		}
		if (!isFirstLoc) {
			beforelatLng = nowlatLng;
		}
		isFirstLoc = false;
		stringBuffer.append("//-------------------------------------------------------------------" + "\n");
	}

	/**
	 * 是否有效坐标：根据两点坐标差判断,大于2km视为 无效跳动坐标
	 * 
	 * @param beforelatLng
	 * @param userslatLng
	 * @return
	 */
	public static boolean haveUserLocStatus(double juliString) {
		boolean status = true;
		if (juliString >= 2) {
			status = false;
		}
		return status;

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

	/**
	 * 保存获取的 软件信息，设备信息和出错信息保存在SDcard中
	 * 
	 * @param context
	 * @param ex
	 * @return
	 */
	private String savaInfoToSD(Context context, StringBuffer sb) {
		String fileName = null;
		// 判断SD卡是否存在
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File dir = new File(
					Environment.getExternalStorageDirectory() + File.separator + "kiwi_gps" + File.separator);
			if (!dir.exists()) {
				dir.mkdir();
			}

			try {
				fileName = dir.toString() + File.separator + paserTime(System.currentTimeMillis()) + ".txt";
				FileOutputStream fos = new FileOutputStream(fileName);
				fos.write(sb.toString().getBytes());
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		// 写入手机内存
		else {
			FileOutputStream phone_outStream;
			try {
				fileName = paserTime(System.currentTimeMillis()) + "kiwi_log.txt";
				// openFileOutput()方法的第一参数用于指定文件名称，不能包含路径分隔符“/” ，
				// 如果文件不存在，Android 会自动创建它。
				// 创建的文件保存在/data/data/<package name>/files目录，
				phone_outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
				phone_outStream.write(sb.toString().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fileName;

	}

	/**
	 * 将毫秒数转换成yyyy-MM-dd-HH-mm-ss的格式
	 * 
	 * @param milliseconds
	 * @return
	 */
	private String paserTime(long milliseconds) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(tz);
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String times = format.format(new Date(milliseconds));

		return times;
	}

	/**
	 * 绘制实时点
	 * 
	 * @param points
	 */

	public void drawRealtimePoint(LatLng point) {
		if (showpointList.size() >= 2 && showpointList.size() <= 1000000) {
			// 添加路线（轨迹）
			polyline = new PolylineOptions().width(10)
					.color(MainStartActivity.mActivity.getResources().getColor(R.color.blue)).points(showpointList);
		}
		addMarker();
	}

	public void setMapupdateStatus(LatLng nowpoint) {
		// 设置中心点
		if (nowpoint != null) {
			MapStatus mMapStatus;
			// if (zoomstaus) {
			// zoomstaus = false;
			// mMapStatus = new
			// MapStatus.Builder().target(nowpoint).zoom(STARTZOOM).build();
			// } else {
			// mMapStatus = new MapStatus.Builder().target(nowpoint).build();
			// }
			mMapStatus = new MapStatus.Builder().target(nowpoint).zoom(STARTZOOM).build();
			MapStatusUpdate msUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
			MainStartActivity.mBaiduMap.animateMapStatus(msUpdate);
		}
	}

	/**
	 * 添加地图覆盖物
	 */
	public void addMarker() {

		// 路线覆盖物
		if (null != polyline && showpointList.size() >= 2) {
			polylineoverlay = MainStartActivity.mBaiduMap.addOverlay(polyline);
			mEndpoint = showpointList.get(showpointList.size() - 1);
			showpointList.clear();
			showpointList.add(mEndpoint);
		} else if (showpointList.size() == 1) {
			mEndpoint = showpointList.get(0);
		}
		setMapupdateStatus(mEndpoint);

		if (null != overlay) {
			overlay.remove();
		}

		if (null == realtimeBitmap) {
			realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_myposition_map);
		}
		overlayOptions = new MarkerOptions().position(mEndpoint).icon(realtimeBitmap).zIndex(8).draggable(true);

		if (null != overlayOptions) {
			overlay = MainStartActivity.mBaiduMap.addOverlay(overlayOptions);
		}

	}

	/**
	 * 每隔一公里显示气泡
	 */
	private void showBubbleView(LatLng nowlatLng, int i) {
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
	private void addMarkerBubble(LatLng point, int index, String juliSt) {

		View convertView = LayoutInflater.from(MainStartActivity.mActivity).inflate(R.layout.venues_map_bubble, null);
		TextView tv_bubble = (TextView) convertView.findViewById(R.id.tv_bubble);
		if (index == 0) {
			tv_bubble.setText("");
			tv_bubble.setBackgroundResource(R.drawable.track_start);
		} else {
			tv_bubble.setText("" + juliSt);
		}
		BitmapDescriptor mmorenMarker = BitmapDescriptorFactory.fromView(convertView);
		OverlayOptions ooA = new MarkerOptions().position(point).icon(mmorenMarker).zIndex(5).draggable(false);
		Marker mMarker = (Marker) MainStartActivity.mBaiduMap.addOverlay(ooA);
	}

	public void startRefreshThread(boolean isStart) {
		if (null == refreshThread) {
			refreshThread = new RefreshThread();
		}
		refreshThread.refresh = isStart;
		if (isStart) {
			if (!refreshThread.isAlive()) {
				refreshThread.start();
			}
		} else {
			refreshThread = null;
		}
	}

}
