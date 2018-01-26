package com.KiwiSports.control.test;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.KiwiSports.R;
import com.KiwiSports.control.activity.BaseActivity;
import com.KiwiSports.model.MainLocationItemInfo;
import com.KiwiSports.utils.CommonUtils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class TestSkyActivity extends BaseActivity {

	private TextView test;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.testsky);
	}

	@Override
	protected void findViewById() {
		test = (TextView) findViewById(R.id.test);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	ArrayList<MainLocationItemInfo> allpointList = new ArrayList<MainLocationItemInfo>();
	private int _nMaxSlopeAngle;
	int ski_height = 50;// 起伏高度
	private int nskiStatus;
	private int beforeAltitude;
	private int currentAltitude;
	private int verticalDistance;
	private int maxAltitude;
	private int minAltidue;
	private int lapCount=0;
	private int dropTraveled;
	private int downHillDistance;
	private int upHillDistance;
	private int  minAltidueall;
	private int  maxAltidueall;
	private int _tmpAltitude;
	private int temdistance;
	private int befodistance;
	/**
	 * lapCount =6;
	 * minAltidue=114;
	 * maxAltitude=206
	 * _nMaxSlopeAngle=13
	 * upHillDistance=1776
	 * verticalDistance=92
	 */
	@Override
	protected void processLogic() {
		parseJSON(context);
		test.setText( "lapCount=" + lapCount +"\n"
				+"minAltidueall=" + minAltidueall +"\n"
				+"maxAltitudeall=" + maxAltidueall +"\n"
				+"_nMaxSlopeAngle=" + _nMaxSlopeAngle +"\n\n"
				+"upHillDistance=" + upHillDistance +"\n"
				+"verticalDistance=" + verticalDistance +"\n"
				+"downHillDistance=" + downHillDistance +"\n"
				);
	}

	public ArrayList<MainLocationItemInfo> parseJSON(Context context) {
		JSONObject jsonObject = null;
		ArrayList<MainLocationItemInfo> mallsportList = null;
		try {
			String jsondate = CommonUtils.getInstance().getFromAssets(context, "124.txt");
			jsonObject = new JSONObject(jsondate);
			mallsportList = parseJSON(jsonObject);
			jsonObject = null;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mallsportList;
	}

	public ArrayList<MainLocationItemInfo> parseJSON(JSONObject jsonObject) {
		try {
			JSONArray Ob = jsonObject.optJSONArray("data");
			for (int i = 0; i < Ob.length(); i++) {
				JSONObject hotOb = Ob.optJSONObject(i);
				double latitude = hotOb.optDouble("latitude");
				double longitude = hotOb.optDouble("longitude");
				double speed = hotOb.optDouble("speed");
				int altitude = hotOb.optInt("altitude");
				int accuracy = hotOb.optInt("accuracy");
				long duration = hotOb.optLong("duration");
				double distance = hotOb.optDouble("distance");
				int nStatus = hotOb.optInt("nStatus");
				int nLapPoint = hotOb.optInt("nLapPoint");
				String nLapTime = "";// 单圈用时
				String latitudeOffset = "";// 精度偏移
				String longitudeOffset = "";// 维度偏移

				MainLocationItemInfo mMainSportInfo = new MainLocationItemInfo(latitude, longitude, speed, altitude,
						accuracy, nStatus, nLapPoint, nLapTime, duration, distance, latitudeOffset, longitudeOffset);
				allpointList.add(mMainSportInfo);
				currentAltitude = (int) altitude;
				if(minAltidueall==0){
					minAltidueall=currentAltitude;
				}
				if(currentAltitude>0&&currentAltitude<minAltidueall){
					minAltidueall=currentAltitude;
				}
				if(currentAltitude>maxAltidueall){
					maxAltidueall=currentAltitude;
				}
				temdistance=(int) (distance-befodistance);
				befodistance=(int) distance;
				insmaxSlope();
				inskyHillDis();
			}
		} catch (Exception e) {
		}
		return allpointList;
	}

	/**
	 * 每次定位：计算最大坡度
	 */
	private void insmaxSlope() {
		if (allpointList != null && allpointList.size() > 0) {
			int tempDistance = 0;
			for (int index = allpointList.size() - 1; index > -1; index--) {
				tempDistance = (int) (allpointList.get(allpointList.size() - 1).getDistance()
						- allpointList.get(index).getDistance());
				if (tempDistance >= 200) {
					MainLocationItemInfo lastPoint = allpointList.get(index);
					MainLocationItemInfo currentPoint = allpointList.get(allpointList.size() - 1);
					if (lastPoint.getAltitude() > currentPoint.getAltitude()) {
						// 此种为下滑
						int tempVerticalDistance = (int) (lastPoint.getAltitude() - currentPoint.getAltitude());
						double tempAngle = Math.asin(tempVerticalDistance / (tempDistance * 1.0));
						if (((tempAngle * 180) / Math.PI) > _nMaxSlopeAngle) {
							_nMaxSlopeAngle = (int) ((tempAngle * 180) / Math.PI);
							if (_nMaxSlopeAngle > 45) {
								_nMaxSlopeAngle = 45;
							}
						}
					}

					break;

				}
			}

		}
	}

	/**
	 * 计算趟数
	 * 
	 * @param nowlatLng
	 */
	private void inskyHillDis() {
		// 计算落差
		if (nskiStatus == 2 && beforeAltitude - currentAltitude > 0) {
			int tempVerticalDistance = (beforeAltitude - currentAltitude);
			verticalDistance += tempVerticalDistance;
		}
		if (maxAltitude == 0 && minAltidue == 0) {
			maxAltitude = minAltidue=_tmpAltitude = currentAltitude;
		}
		if (nskiStatus == 0) {
			if ((currentAltitude - maxAltitude) > 0) {
				maxAltitude = currentAltitude;
			}
			if ((currentAltitude - minAltidue) < 0) {
				minAltidue = currentAltitude;
			}

			if (_tmpAltitude - minAltidue > ski_height) {
				nskiStatus = 2;
				setStatusDownWithDistance();
				maxAltitude = minAltidue = currentAltitude;
			}
			if (maxAltitude - _tmpAltitude > ski_height) {
				nskiStatus = 1;
				setStatusUpWithDistance();
				maxAltitude = minAltidue = currentAltitude;
			}
		}

		if (nskiStatus == 1) {
			if ((currentAltitude - maxAltitude) > 0) {
				minAltidue = maxAltitude = currentAltitude;
			}
			if ((currentAltitude - minAltidue) < 0) {
				minAltidue = currentAltitude;
			}

			if (maxAltitude - minAltidue > ski_height) {
				nskiStatus = 2;
				setStatusDownWithDistance();
				maxAltitude = minAltidue = currentAltitude;
			}
		}
		if (nskiStatus == 2) {
			if ((currentAltitude - maxAltitude) > 0) {
				maxAltitude = currentAltitude;
			}
			if ((currentAltitude - minAltidue) < 0) {
				minAltidue = maxAltitude = currentAltitude;
			}

			if (maxAltitude - minAltidue > ski_height) {
				nskiStatus = 1;
				setStatusUpWithDistance();
				minAltidue = maxAltitude = currentAltitude;
			}
		}
		beforeAltitude = currentAltitude;
	}

	private void setStatusDownWithDistance() {
		int nHighestPointIndex = allpointList.size() - 1; // 全局轨迹点记录数组最后一个的索引值
		double nHighestAltitude = allpointList.get(nHighestPointIndex).getAltitude(); // 声明最高点海拔局部变量
		for (int i = nHighestPointIndex; i >= 0; i--) {
			MainLocationItemInfo myLocation = allpointList.get(i);

			if ((myLocation.getnLapPoint() == lapCount)
					&& ((lapCount > 0 && myLocation.getnStatus() == 1) || lapCount == 0)) { // 当前点的滑行次数
																							// 等于
																							// 总的滑行次数
				if (myLocation.getAltitude() >= nHighestAltitude) { // 当前点海拔高于最高点海拔，更新最高点海拔值
					nHighestAltitude = myLocation.getAltitude();
					// 保存最高点索引
					nHighestPointIndex = i;
				}
			}  
		}
		double dis=allpointList.get(allpointList.size() - 1).getDistance();
		double disind=allpointList.get(nHighestPointIndex).getDistance();
		int skiDis = (int) (dis - disind);
		downHillDistance += skiDis+temdistance;
		 int downVerDistance = verticalDistance(nHighestPointIndex, 2);
		dropTraveled = verticalDistance+=downVerDistance;

		lapCount++;
	}

	public void setStatusUpWithDistance() {
		int nLowestPointIndex = allpointList.size() - 1; // 全局轨迹点记录数组最后一个的索引值
		double nLowestAltitude = allpointList.get(nLowestPointIndex).getAltitude();// 声明最低点海拔局部变量
		for (int i = nLowestPointIndex; i >= 0; i--) {
			MainLocationItemInfo myModel = allpointList.get(i);
			if ((myModel.getnLapPoint() == lapCount)
					&& ((myModel.getnLapPoint() > 0 && myModel.getnStatus() == 2) || myModel.getnLapPoint() == 0)) { // 当前点的滑行次数
																														// 等于
																														// 总的滑行次数
				if (myModel.getAltitude() <= nLowestAltitude) { // 当前点海拔低于最高点海拔，更新最高点海拔值
					nLowestAltitude = myModel.getAltitude();
					// 保存最低点索引
					nLowestPointIndex = i;
				}
			}
		}
		// 计算上升ski_height米后的扣除ski_height米的移动距离（前提：之前有滑行趟数）
		int upDistance = (int) (allpointList.get(allpointList.size() - 1).getDistance()
				- allpointList.get(nLowestPointIndex).getDistance()+temdistance);
		if (downHillDistance > upDistance) {
			downHillDistance -= upDistance;
		}
		upHillDistance = verticalDistance(nLowestPointIndex, nskiStatus);
		if (verticalDistance > upHillDistance) {
			verticalDistance -= upHillDistance; // 总滑降 - 上升过程中的滑降 （扣除上升40米的海拔差
		}
	}

	// 计算上升/下降垂直距离
	public int verticalDistance(int point, int status) {

		int verDistance = 0;
		int count = allpointList.size();
		for (int i = point; i < count; i++) {
			MainLocationItemInfo preMyLocation = allpointList.get(i);
			preMyLocation.setnStatus(status);

			for (int j = i; j < count; j++) {
				MainLocationItemInfo nextMyLocation = allpointList.get(j);

				if (nextMyLocation.getAltitude() < preMyLocation.getAltitude()) {
					verDistance += preMyLocation.getAltitude() - nextMyLocation.getAltitude();
				}
				break;
			}
		}
		return verDistance;
	}
}
