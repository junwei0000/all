package com.KiwiSports.control.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.KiwiSports.utils.Constants;

/**
 * 
 * @author Administrator
 * @date 创建时间：2017年6月15日 上午10:09:18
 * @Description 网络轨迹 感应器校对 有震动就计算
 */
public class StepDetector implements SensorEventListener {

	// 存放三轴数据
	private float[] oriValues = new float[3];
	private final int valueNum = 4;
	// 用于存放计算阈值的波峰波谷差值
	private float[] tempValue = new float[valueNum];
	private int tempCount = 0;
	// 是否上升的标志位
	private boolean isDirectionUp = false;
	// 持续上升次数
	private int continueUpCount = 0;
	// 上一点的持续上升的次数，为了记录波峰的上升次数
	private int continueUpFormerCount = 0;
	// 上一点的状态，上升还是下降
	private boolean lastStatus = false;
	// 波峰值
	private float peakOfWave = 0;
	// 波谷值
	private float valleyOfWave = 0;
	// 此次波峰的时间
	private long timeOfThisPeak = 0;
	// 上次波峰的时间
	private long timeOfLastPeak = 0;
	// 当前的时间
	private long timeOfNow = 0;
	// 当前传感器的值
	private float gravityNew = 0;
	// 上次传感器的值
	private float gravityOld = 0;
	// 动态阈值需要动态的数据，这个值用于这些动态数据的阈值
	private final float initialValue = (float) 1.3;
	// 初始阈值
	private float ThreadValue = (float) 2.0;
	@SuppressWarnings("rawtypes")
	private ArrayList stepList = new ArrayList();

	Context context;
	public static int CURRENT_SETP = 0;

	public StepDetector(Context context) {
		super();
		this.context = context;
	}

	/*
	 * 注册了G-Sensor后一只会调用这个函数 对三轴数据进行平方和开根号的处理 调用DetectorNewStep检测步子
	 */
	@SuppressLint("NewApi")
	@Override
	public void onSensorChanged(SensorEvent event) {
		Constants.getInstance().mSensorState = true;
		DecimalFormat df = new DecimalFormat("0.00");
		df.getRoundingMode();
		for (int i = 0; i < 3; i++) {
			oriValues[i] = event.values[i];
		}
		gravityNew = (float) Math.sqrt(oriValues[0] * oriValues[0]
				+ oriValues[1] * oriValues[1] + oriValues[2] * oriValues[2]);
		DetectorNewStep(gravityNew);
	}

	/*
	 * 检测步子，并开始计步 1.传入sersor中的数据 2.如果检测到了波峰，并且符合时间差以及阈值的条件，则判定为1步
	 * 3.符合时间差条件，波峰波谷差值大于initialValue，则将该差值纳入阈值的计算中
	 */
	public void DetectorNewStep(float values) {
		if (gravityOld == 0) {
			gravityOld = values;
		} else {
			if (DetectorPeak(values, gravityOld)) {
				timeOfLastPeak = timeOfThisPeak;
				timeOfNow = System.currentTimeMillis();
				if (timeOfNow - timeOfLastPeak >= 220
						&& (peakOfWave - valleyOfWave >= ThreadValue)) {
					timeOfThisPeak = timeOfNow;
					if (DetectorEffectCalculate()) {
						if (CURRENT_SETP > 200000) {
							CURRENT_SETP = 1;
						} else {
							CURRENT_SETP++;
						}
					}
				}
				if (timeOfNow - timeOfLastPeak >= 220
						&& (peakOfWave - valleyOfWave >= initialValue)) {
					timeOfThisPeak = timeOfNow;
					ThreadValue = Peak_Valley_Thread(peakOfWave - valleyOfWave);
				}
			}
		}
		gravityOld = values;
	}

	/*
	 * 更新界面的处理，不涉及到算法 一般在通知更新界面之前，增加下面处理，为了处理无效运动： 1.连续记录10才开始计步
	 * 2.例如记录的9步用户停住超过3秒，则前面的记录失效，下次从头开始 3.连续记录了9步用户还在运动，之前的数据才有效
	 */
	@SuppressWarnings("unchecked")
	private boolean DetectorEffectCalculate() {
		boolean isEffect = false;
		if (timeOfThisPeak - timeOfLastPeak < 2000) {
			stepList.add(timeOfThisPeak);
			isEffect = stepList.size() >= 8;
		} else {
			stepList.clear();
		}
		return isEffect;
	}

	/*
	 * 检测波峰 以下四个条件判断为波峰： 1.目前点为下降的趋势：isDirectionUp为false
	 * 2.之前的点为上升的趋势：lastStatus为true 3.到波峰为止，持续上升大于等于2次 4.波峰值大于20 记录波谷值
	 * 1.观察波形图，可以发现在出现步子的地方，波谷的下一个就是波峰，有比较明显的特征以及差值 2.所以要记录每次的波谷值，为了和下次的波峰做对比
	 */
	public boolean DetectorPeak(float newValue, float oldValue) {
		lastStatus = isDirectionUp;
		if (newValue >= oldValue) {
			isDirectionUp = true;
			continueUpCount++;
		} else {
			continueUpFormerCount = continueUpCount;
			continueUpCount = 0;
			isDirectionUp = false;
		}

		if (!isDirectionUp && lastStatus
				&& (continueUpFormerCount >= 2 || oldValue >= 20)) {
			peakOfWave = oldValue;
			return true;
		} else if (!lastStatus && isDirectionUp) {
			valleyOfWave = oldValue;
			return false;
		} else {
			return false;
		}
	}

	/*
	 * 阈值的计算 1.通过波峰波谷的差值计算阈值 2.记录4个值，存入tempValue[]数组中
	 * 3.在将数组传入函数averageValue中计算阈值
	 */
	public float Peak_Valley_Thread(float value) {
		float tempThread = ThreadValue;
		if (tempCount < valueNum) {
			tempValue[tempCount] = value;
			tempCount++;
		} else {
			tempThread = averageValue(tempValue, valueNum);
			for (int i = 1; i < valueNum; i++) {
				tempValue[i - 1] = tempValue[i];
			}
			tempValue[valueNum - 1] = value;
		}
		return tempThread;

	}

	/*
	 * 梯度化阈值 1.计算数组的均值 2.通过均值将阈值梯度化在一个范围里
	 */
	public float averageValue(float value[], int n) {
		float ave = 0;
		for (int i = 0; i < n; i++) {
			ave += value[i];
		}
		ave = ave / valueNum;
		if (ave >= 8)
			ave = (float) 4.3;
		else if (ave >= 7 && ave < 8)
			ave = (float) 3.3;
		else if (ave >= 4 && ave < 7)
			ave = (float) 2.3;
		else if (ave >= 3 && ave < 4)
			ave = (float) 2.0;
		else {
			ave = (float) 1.8;
		}
		return ave;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i) {

	}
}
