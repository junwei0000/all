package com.bestdo.bestdoStadiums.control.activity;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnEntityListener;
import com.baidu.trace.Trace;
import com.baidu.trace.TraceLocation;
import com.bestdo.bestdoStadiums.R;
import com.bestdo.bestdoStadiums.utils.CommonUtils;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * @author qyy 运动轨迹
 */
public class UserWalkingTrack extends BaseActivity {

	protected static MapView bmapView = null;
	protected static BaiduMap mBaiduMap = null;
	/**
	 * 轨迹服务
	 */
	protected static Trace trace = null;

	/**
	 * entity标识
	 */
	protected static String entityName = null;

	/**
	 * 鹰眼服务ID，开发者创建的鹰眼服务对应的服务ID
	 */
	protected static long serviceId = 123776;

	/**
	 * 轨迹服务类型（0 : 不建立socket长连接， 1 : 建立socket长连接但不上传位置数据，2 : 建立socket长连接并上传位置数据）
	 */
	private int traceType = 2;

	/**
	 * 轨迹服务客户端
	 */
	protected static LBSTraceClient client = null;

	/**
	 * Entity监听器
	 */
	protected static OnEntityListener entityListener = null;

	protected static Context mContext = null;

	@Override
	public void onClick(View arg0) {

	}

	@Override
	protected void loadViewLayout() {
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.user_walking_track);
		CommonUtils.getInstance().addActivity(this);
		mContext = getApplicationContext();
		// 初始化轨迹服务客户端
		client = new LBSTraceClient(mContext);

		// 设置定位模式
		client.setLocationMode(LocationMode.High_Accuracy);

		// 初始化entity标识
		entityName = "myTrace";

		// 初始化轨迹服务
		trace = new Trace(getApplicationContext(), serviceId, entityName, traceType);

		// 初始化EntityListener
		initOnEntityListener();
	}

	@Override
	protected void findViewById() {
		bmapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = bmapView.getMap();
		bmapView.showZoomControls(false);
	}

	@Override
	protected void setListener() {

	}

	@Override
	protected void processLogic() {

	}

	/**
	 * 初始化OnEntityListener
	 */
	private void initOnEntityListener() {
		entityListener = new OnEntityListener() {

			// 请求失败回调接口
			@Override
			public void onRequestFailedCallback(String arg0) {
				// TODO Auto-generated method stub
				// TrackApplication.showMessage("entity请求失败回调接口消息 : " + arg0);
				System.out.println("entity请求失败回调接口消息 : " + arg0);
			}

			// 添加entity回调接口
			public void onAddEntityCallback(String arg0) {
				// TODO Auto-generated method stub
				System.out.println("添加entity回调接口消息 : " + arg0);
				Toast.makeText(context, "添加entity回调接口消息 : " + arg0, Toast.LENGTH_LONG).show();
			}

			// 查询entity列表回调接口
			@Override
			public void onQueryEntityListCallback(String message) {
				// TODO Auto-generated method stub
				System.out.println("entityList回调消息 : " + message);
			}

			@Override
			public void onReceiveLocation(TraceLocation location) {
				// TODO Auto-generated method stub
				System.out.println("获取到实时位置:" + location.toString());
			}
		};
	}
}
