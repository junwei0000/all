package com.KiwiSports.control.step;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.KiwiSports.utils.CommonUtils;
import com.KiwiSports.utils.Constants;

/**
 * 计步传感器
 */
public class StepSensorPedometer extends StepSensorBase {
    private final String TAG = "StepSensorPedometer";
    private int sensorMode = 0; // 计步传感器类型

    //是否有当天的记录
    private boolean hasRecord;
    //未记录之前的步数
    private int hasStepCount;
    //下次记录之前的步数
    private int previousStepCount;
    private final SharedPreferences bestDoInfoSharedPrefs;
    Context context;

    public StepSensorPedometer(Context context) {
        super(context);
        this.context = context;
        bestDoInfoSharedPrefs = CommonUtils.getInstance()
                .getBestDoInfoSharedPrefs(context);
    }

    @Override
    protected void registerStepListener() {
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensorManager.registerListener(this, detectorSensor, SensorManager.SENSOR_DELAY_UI)) {
            isAvailable = true;
            sensorMode = 0;
            Log.i(TAG, "计步传感器Detector可用！");
        } else if (sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI)) {
            isAvailable = true;
            sensorMode = 1;
            Log.i(TAG, "计步传感器Counter可用！");
        } else {
            isAvailable = false;
            Log.i(TAG, "计步传感器不可用！");
        }
    }

    @Override
    public void unregisterStep() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        boolean haveCachingDBAndLoad = bestDoInfoSharedPrefs.getBoolean("haveCachingDBAndLoad", false);
        if (haveCachingDBAndLoad) {
            StepSensorBase.CURRENT_SETP = bestDoInfoSharedPrefs.getInt("step", 0);
            Log.e("stepssteps", "haveCachingDBAndLoad=" + StepSensorBase.CURRENT_SETP);
            SharedPreferences.Editor medit = bestDoInfoSharedPrefs.edit();
            medit.putBoolean("haveCachingDBAndLoad", false);
            medit.commit();
        }
        mStepHandler.sendEmptyMessage(11);
        String stepStatus = bestDoInfoSharedPrefs.getString("stepStatus", "");


        int liveStep = (int) event.values[0];
        if (sensorMode == 0) {
            if (stepStatus.equals("pause")) {
            } else if (stepStatus.equals("end")) {
                StepSensorBase.CURRENT_SETP = 0;
            } else if (stepStatus.equals("start")) {
                StepSensorBase.CURRENT_SETP += liveStep;
            }
            Log.i("stepssteps", "onSensorChanged==" + StepSensorBase.CURRENT_SETP + "      liveStep=" + liveStep);
        } else if (sensorMode == 1) {
//			StepSensorBase.CURRENT_SETP = liveStep;
            if (stepStatus.equals("pause")) {
                hasRecord = true;
                hasStepCount = liveStep;
                previousStepCount = liveStep - hasStepCount;
            } else if (stepStatus.equals("end")) {
                hasRecord = true;
                hasStepCount = liveStep;
                previousStepCount = liveStep - hasStepCount;
                StepSensorBase.CURRENT_SETP = 0;
            } else if (stepStatus.equals("start")) {
            }
            if (!hasRecord) {
                hasRecord = true;
                hasStepCount = liveStep;
            } else {
                int thisStepCount = liveStep - hasStepCount;
                StepSensorBase.CURRENT_SETP += (thisStepCount - previousStepCount);
                previousStepCount = thisStepCount;
            }
            Log.e("stepssteps", "stepSensor == 1；；liveStep=" + liveStep + "   ;;CURRENT_STEP==" + StepSensorBase.CURRENT_SETP + ";;hasStepCount==" + hasStepCount + "  " + previousStepCount);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @SuppressLint("HandlerLeak")
    Handler mStepHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //这里用来获取到Service发来的数据
                case 11:
                    String stepStatus = bestDoInfoSharedPrefs.getString("stepStatus", "");
                    Log.e("stepssteps", "mStepHandler mStepHandler========" + stepStatus);
                    if (stepStatus.equals("end")) {
                        StepSensorBase.CURRENT_SETP = 0;
                    }
                    Intent intent = new Intent(Constants.RECEIVER_ACTION_STEP);
                    intent.putExtra("CURRENT_STEP_NOW", StepSensorBase.CURRENT_SETP);
                    context.sendBroadcast(intent);
                    break;
            }
        }
    };
}