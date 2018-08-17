package com.KiwiSports.control.step;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 计步模式分为加速度传感器、计步传感器
 */
public abstract class StepSensorBase implements SensorEventListener {
	private Context context;
	public SensorManager sensorManager;
	public static int CURRENT_SETP = 0;
	public boolean isAvailable = false;

	public StepSensorBase(Context context) {
		this.context = context;
	}

	/**
	 * 开启计步
	 */
	public boolean registerStep() {
		if (sensorManager != null) {
			sensorManager.unregisterListener(this);
			sensorManager = null;
		}
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		registerStepListener();
		return isAvailable;
	}

	/**
	 * 注册计步监听器
	 */
	protected abstract void registerStepListener();

	/**
	 * 注销计步监听器
	 */
	public abstract void unregisterStep();
}
