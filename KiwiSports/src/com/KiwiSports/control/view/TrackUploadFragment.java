package com.KiwiSports.control.view;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.KiwiSports.utils.ConfigUtils;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
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

	private Button btnStartTrace = null;

	private Button btnStopTrace = null;


	protected TextView tvEntityName = null;

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
	private static BitmapDescriptor realtimeBitmap;

	public static Overlay overlay = null;
	public static   Overlay polylineoverlay;
	// 覆盖物
	public static OverlayOptions overlayOptions;
	// 路线覆盖物
	public static PolylineOptions polyline = null;

	public static List<LatLng> showpointList = new ArrayList<LatLng>();
	public static List<LatLng> uploadpointList = new ArrayList<LatLng>();
	public static List<LatLng> allpointList = new ArrayList<LatLng>();

	protected boolean isTraceStart = false;

	/**
	 * 刷新地图线程(获取实时点)
	 */
	public RefreshThread refreshThread = null;

	private View view = null;


	public static boolean isInUploadFragment = true;

	// public static boolean isRegister = false;

	// public static PowerManager pm = null;
	//
	// public static WakeLock wakeLock = null;

	// private PowerReceiver powerReceiver = new PowerReceiver();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_trackupload, container, false);

		// 初始化
		init();

		// 初始化监听器
		initListener();

		// 设置采集周期
		setInterval();

		// 设置http请求协议类型
		setRequestType();

		return view;
	}

	/**
	 * @return the allpointList
	 */
	public List<LatLng> getuploadPointList() {
		return uploadpointList;
	}

	/**
	 * @param allpointList
	 *            the allpointList to set
	 */
	public void setuploadpointList(List<LatLng> uploadpointList) {
		this.uploadpointList = uploadpointList;
	}

	public static OverlayOptions getOverlayOptions() {
		return overlayOptions;
	}

	public static void setOverlayOptions(OverlayOptions overlayOptions) {
		TrackUploadFragment.overlayOptions = overlayOptions;
	}

	public static PolylineOptions getPolyline() {
		return polyline;
	}

	public static void setPolyline(PolylineOptions polyline) {
		TrackUploadFragment.polyline = polyline;
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 */
	private void init() {

		btnStartTrace = (Button) view.findViewById(R.id.btn_starttrace);

		btnStopTrace = (Button) view.findViewById(R.id.btn_stoptrace);

		// btnOperator = (Button) view.findViewById(R.id.btn_operator);

		tvEntityName = (TextView) view.findViewById(R.id.tv_entityName);

		btnStartTrace.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Toast.makeText(getActivity(), "正在开启轨迹服务，请稍候",
				// Toast.LENGTH_LONG).show();
				startTrace();

				// if (!isRegister) {
				// if (null == pm) {
				// pm = (PowerManager) MainStartActivity.mContext
				// .getSystemService(Context.POWER_SERVICE);
				// }
				// if (null == wakeLock) {
				// wakeLock = pm.newWakeLock(
				// PowerManager.PARTIAL_WAKE_LOCK, "track upload");
				// }
				// IntentFilter filter = new IntentFilter();
				// filter.addAction(Intent.ACTION_SCREEN_OFF);
				// filter.addAction(Intent.ACTION_SCREEN_ON);
				// MainStartActivity.mContext
				// .registerReceiver(powerReceiver, filter);
				// isRegister = true;
				// }

			}
		});

		btnStopTrace.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

				// Toast.makeText(getActivity(), "正在停止轨迹服务，请稍候",
				// Toast.LENGTH_SHORT).show();
				stopTrace();
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
		});

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

	/**
	 * 公里
	 */
	public double sum_distance = 0.00;
	public boolean isFirstLoc = true;
	LatLng beforelatLng;
	LatLng nowlatLng;

	

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
		if (userslatLng == null || userslatLng.latitude == 0) {
			// showMessage("当前查询无轨迹点", null);
		} else {
			stringBuffer.append("CoordinateConverter beforelatLng-----beforelatLng=" + beforelatLng + "\n");

			if (isFirstLoc) {
				beforelatLng = userslatLng;
				stringBuffer.append("isFirstLoc-----userslatLng=" + beforelatLng + "\n");
			} else {
				nowlatLng = userslatLng;
				stringBuffer.append("nowlatLng-----nowlatLng=" + nowlatLng + "\n");
				double juliString = ConfigUtils.DistanceOfTwoPoints(beforelatLng.latitude, beforelatLng.longitude,
						nowlatLng.latitude, nowlatLng.longitude);
				if (juliString > 5000) {
					// 当两点坐标大于一万公里
					juliString = 0;
				}
				stringBuffer.append("juliString-----juliString=" + juliString + "\n");
				if (rebookstatus(userslatLng)) {
					sum_distance = sum_distance + juliString;
				}
				stringBuffer.append("sum_distance-----sum_distance=" + sum_distance + "\n");

			}
			stringBuffer.append("//-------------------------------------------------------------------" + "\n");

			if (rebookstatus(userslatLng)) {
				showpointList.add(userslatLng);
				uploadpointList.add(userslatLng);
				allpointList.add(userslatLng);
				// 绘制实时点
				drawRealtimePoint(userslatLng);
				savaInfoToSD(MainStartActivity.mHomeActivity, stringBuffer);
			}
			if (!isFirstLoc) {
				beforelatLng = nowlatLng;
			}
			isFirstLoc = false;
		}

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
			if (beforelatLng != nowlatLng) {
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

		if (null != overlay) {
			overlay.remove();
		}

		if (null == realtimeBitmap) {
			realtimeBitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_myposition_map);
		}

		overlayOptions = new MarkerOptions().position(point).icon(realtimeBitmap).zIndex(8).draggable(true);

		if (showpointList.size() >= 2 && showpointList.size() <= 100000) {
			// 添加路线（轨迹）
			polyline = new PolylineOptions().width(10)
					.color(MainStartActivity.mHomeActivity.getResources().getColor(R.color.blue)).points(showpointList);
		}
		addMarker();
	}

	public void setMapupdateStatus(float zooms, LatLng nowpoint) {
		System.err.println(nowpoint + "   dd   " + zooms);
		// 设置中心点
		if (nowpoint != null) {
			MapStatus mMapStatus = new MapStatus.Builder().target(nowpoint).zoom(zooms).build();
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
			polylineoverlay=MainStartActivity.mBaiduMap.addOverlay(polyline);
			mEndpoint = showpointList.get(showpointList.size() - 1);
			showpointList.clear();
			showpointList.add(mEndpoint);
		} else if (showpointList.size() == 1) {
			mEndpoint = showpointList.get(0);
		}
		setMapupdateStatus(17, mEndpoint);
		// 实时点覆盖物
		if (null != overlayOptions) {
			overlay = MainStartActivity.mBaiduMap.addOverlay(overlayOptions);
		}

	}

	public static LatLng mEndpoint;

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

	private void showMessage(final String message, final Integer errorNo) {

		new Handler(MainStartActivity.mHomeActivity.getMainLooper()).post(new Runnable() {
			public void run() {
				Toast.makeText(MainStartActivity.mHomeActivity, message, Toast.LENGTH_LONG).show();

				if (null != errorNo) {
					if (0 == errorNo.intValue() || 10006 == errorNo.intValue() || 10008 == errorNo.intValue()
							|| 10009 == errorNo.intValue()) {
						btnStartTrace.setBackgroundColor(Color.rgb(0x99, 0xcc, 0xff));
						btnStartTrace.setTextColor(Color.rgb(0x00, 0x00, 0xd8));
					} else if (1 == errorNo.intValue() || 10004 == errorNo.intValue()) {
						btnStartTrace.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
						btnStartTrace.setTextColor(Color.rgb(0x00, 0x00, 0x00));
					}
				}
			}
		});

	}
}
